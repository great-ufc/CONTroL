package great.ufc.br.control.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import great.ufc.br.control.ControlSetup;
import great.ufc.br.control.ControlUtils;
import great.ufc.br.control.annotations.ControlContext;
import great.ufc.br.control.annotations.ControlContextGroup;
import great.ufc.br.control.models.Reason;
import great.ufc.br.control.models.TestCase;
import great.ufc.br.control.models.TestSequences;

@Aspect
public class ContextAspect {

    @Pointcut("@annotation(controlContextAnnotation)")
    public void controlContextGroupPointcut(ControlContextGroup controlContextAnnotation) {
    }

    @Pointcut("@annotation(controlContextAnnotation)")
    public void controlContextPointcut(ControlContext controlContextAnnotation) {
    }

    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }

    @Around("controlContextPointcut(controlContextAnnotation) && atExecution()")
    public Object contextAdvice(final ProceedingJoinPoint joinPoint, final ControlContext controlContextAnnotation) throws Throwable {

        Object returnValue = null;

        TestSequences testSequences = TestSequences.getInstance();

        synchronized (testSequences) {
            TestCase currentTestCase = testSequences.getCurrentTestCase();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

            try {
                returnValue = joinPoint.proceed();

                if (!testSequences.isFinished()) {

                    if (!method.getReturnType().toString().equals("boolean")) {
                        throw new IllegalArgumentException("Type of method is not boolean.");
                    }

                    String contextName = controlContextAnnotation.contextName();

                    if (currentTestCase.containsContext(contextName)) {
                        returnValue = true;
                    } else {
                        returnValue = false;
                    }
                    currentTestCase.contextVisited(contextName);
                }
            } catch (IllegalArgumentException e) {
                currentTestCase.addWarning(Reason.RETURN_CONTEXT_FEATURE.toString() + method.getName());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }

    @Around("controlContextGroupPointcut(controlContextAnnotation) && atExecution()")
    public Object contextGroupAdvice(final ProceedingJoinPoint joinPoint, final ControlContextGroup controlContextAnnotation) throws InterruptedException {
        Object returnValue = null;

        TestSequences testSequences = TestSequences.getInstance();

        synchronized (testSequences) {
            TestCase currentTestCase = testSequences.getCurrentTestCase();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

            try {
                returnValue = joinPoint.proceed();

                if (!testSequences.isFinished()) {
                    String contextGroupName = controlContextAnnotation.contextGroupName();
                    ControlUtils.printLog("Context Group Name: " + contextGroupName);
                    String contextGroupValue = currentTestCase.getContextGroupNameValue(contextGroupName);
                    ControlUtils.printLog("Context Group Value: " + contextGroupValue);
                    String newValue = ControlSetup.getInstance().getContextDomain().getValue(contextGroupName, contextGroupValue);
                    ControlUtils.printLog("New Context value: " + newValue);

                    currentTestCase.contextVisited(contextGroupName+":"+contextGroupValue);

                    if (newValue.equals(Reason.UNDEFINED_CONTEXT.toString())) {
                        String contextName = controlContextAnnotation.contextGroupName();
                        String errorMsg = "The context " + contextName + " was defined in both \"keyValue\" and \"keyValueList\""+
                                " or was not defined at all.";

                        currentTestCase.addWarning(Reason.UNDEFINED_CONTEXT.toString() + contextName + "\n" + errorMsg);
                    } else {
                       returnValue = changeReturnValue(newValue, method.getReturnType().getSimpleName());
                    }
                }
            } catch (IllegalArgumentException e) {
                String errorMsg = "The method " + method.getName() + " was expecting " +
                        method.getReturnType().toString() + " from Context Domain. Annontation: "
                        + controlContextAnnotation.contextGroupName();

                currentTestCase.addWarning(Reason.CASTING_ERROR.toString() + errorMsg);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    private Object changeReturnValue(String newValue, String type) throws IllegalArgumentException {
        switch (type) {
            case "String":
                return newValue;
            case "char":
                return newValue.charAt(0);
            case "short":
                return Short.valueOf(newValue);
            case "long":
                return Long.valueOf(newValue);
            case "int":
                return Integer.valueOf(newValue);
            case "double":
                return Double.valueOf(newValue);
            case "float":
                return Float.valueOf(newValue);
            case "byte":
                return Byte.valueOf(newValue);
            case "boolean":
                return Boolean.valueOf(newValue);
            default:
                return null;
        }
    }
}