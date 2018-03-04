/*
* File: SystemStatusAspect.java 
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

import great.ufc.br.control.ControlUtils;
import great.ufc.br.control.annotations.ControlStatusAdapted;
import great.ufc.br.control.models.Reason;
import great.ufc.br.control.models.TestCase;
import great.ufc.br.control.models.TestSequences;
import great.ufc.br.control.report.ReportManager;

@Aspect
public class SystemStatusAspect {
    @Pointcut("@annotation(statusAdaptedAnnotation)")
    public void controlSystemStatusPointcut(ControlStatusAdapted statusAdaptedAnnotation) {
    }

    @Pointcut("execution(* *(..))")
    public void atExecution() {
    }

    @Around("controlSystemStatusPointcut(statusAdaptedAnnotation) && atExecution()")
    public Object aroundAdvice(final ProceedingJoinPoint joinPoint, ControlStatusAdapted statusAdaptedAnnotation) throws Throwable {
        Object returnValue = null;
        TestSequences testSequences = TestSequences.getInstance();

        synchronized (testSequences) {
            TestCase currentTestCase = testSequences.getCurrentTestCase();
            ReportManager reportManager = ReportManager.getInstance();

            try {
                returnValue = joinPoint.proceed();

                if (!testSequences.isFinished()) {
                    boolean isTestCaseTimeouted = currentTestCase.getTimeoutManager().isTimeouted();

                    if (returnValue != null && returnValue.toString().equals("ControlTestCaseTimeout") && isTestCaseTimeouted) {
                        currentTestCase.setPassed(false);
                        currentTestCase.setReason(Reason.TIMEOUT);
                    } else {
                        currentTestCase.assertFeatures();
                        currentTestCase.assertContext();
                    }

                    reportManager.saveResultsFromTestCase(currentTestCase);
                    reportManager.printCurrentTestCaseLog();
                    reportManager.appendTestCase();

                    testSequences.moveNextTestCase();

                    if (testSequences.isCurrentSeqFinished()) {
                        reportManager.removeLastTestCase();
                        reportManager.appendTestSequence();
                        reportManager.appendTestCase();

                        testSequences.moveNextTestSeq();

                        if (testSequences.isFinished()) {
                            reportManager.removeLastTestSequence();
                            ControlUtils.printLog("------------------------------------------------------------------------");
                            ControlUtils.printLog("CONTRol HAS FISNISHED.");
                            ControlUtils.setMessageIsPrinted(true);
                            reportManager.saveReportToFile();
                        }
                    }
                } else if (!ControlUtils.messageIsPrinted()) {
                    ControlUtils.printLog("------------------------------------------------------------------------");
                    ControlUtils.printLog("CONTRol HAS FISNISHED.");
                    ControlUtils.setMessageIsPrinted(true);
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return returnValue;
    }
}
