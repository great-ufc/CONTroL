/*
* File: FeatureAspect.java 
* Created: 2017-07-01
* Authors:
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import great.ufc.br.control.annotations.ControlFeature;
import great.ufc.br.control.models.TestCase;
import great.ufc.br.control.models.TestSequences;

@Aspect
public class FeatureAspect {

    @Pointcut("@annotation(controlFeatureAnnotation)")
    public void controlFeaturePointcut(ControlFeature controlFeatureAnnotation) {
    }

    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }

    @Around("controlFeaturePointcut(controlFeatureAnnotation) && atExecution()")
    public Object aroundAdvice(final ProceedingJoinPoint joinPoint, final ControlFeature controlFeatureAnnotation) throws Throwable {
        Object returnValue = null;
        TestSequences testSequences = TestSequences.getInstance();

        synchronized (testSequences) {
            try {
                returnValue = joinPoint.proceed();

                if (!testSequences.isFinished()) {
                    TestCase currentTestCase = testSequences.getCurrentTestCase();
                    String feature = controlFeatureAnnotation.feature();

                    if ((boolean) returnValue) {
                        currentTestCase.addActualFeatures(feature);
                    } else {
                        currentTestCase.removeActualFeatures(feature);
                    }

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }
}