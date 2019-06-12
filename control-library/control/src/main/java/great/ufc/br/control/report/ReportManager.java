/*
* File: ReportManager.java 
* Created: 2017-07-01
* Authors:
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.report;

import great.ufc.br.control.ControlSetup;
import great.ufc.br.control.ControlUtils;
import great.ufc.br.control.models.Reason;
import great.ufc.br.control.models.TestCase;

import static android.os.Environment.getExternalStorageDirectory;

public class ReportManager {
    private Report report;
    private int currentTestSeqId;
    private TestSequenceReport currentTestSequence;
    private TestCaseReport currentTestCase;
    private static ReportManager reportManagerInstance;

    private ReportManager() {
        report = new Report();
        currentTestSeqId = 1;
        currentTestSequence = new TestSequenceReport(currentTestSeqId);
        currentTestCase = new TestCaseReport();
        report.addTestSequence(currentTestSequence);
        currentTestSequence.addTestCase(currentTestCase);
    }

    public static ReportManager getInstance() {
        if (reportManagerInstance == null) {
            reportManagerInstance = new ReportManager();
        }

        return reportManagerInstance;
    }

    public void appendTestSequence() {
        currentTestSequence = new TestSequenceReport(++currentTestSeqId);
        report.addTestSequence(currentTestSequence);
    }

    public void appendTestCase() {
        TestCaseReport newTestCase = new TestCaseReport();
        currentTestSequence.addTestCase(newTestCase);
        currentTestCase = newTestCase;

    }
    public void setFinalResult(boolean result) {
        this.currentTestCase.setPassed(result);
    }

    public void addWarnings(String warningMesage) {
        this.currentTestCase.addWarning(warningMesage);
    }

    public void setExpectedFeatures(String expectedFeatures) {
        this.currentTestCase.setExpectedFeatures(expectedFeatures);
    }

    public void setActualFeatures(String actualFeatures) {
        this.currentTestCase.setActualFeatures(actualFeatures);
    }

    public void saveResultsFromTestCase(TestCase testCase) {
        currentTestCase.setId(testCase.getId());
        currentTestCase.setPassed(testCase.isPassed());
        currentTestCase.setReason(testCase.getReason().toString());
        String contextState = testCase.getContextState().toString().replace("[", "").replace("]", "")
                .replace(", ", ",");
        currentTestCase.setContextState(contextState);
        currentTestCase.setExpectedFeatures(testCase.getExpectedFeatures());
        currentTestCase.setActualFeatures(testCase.getActualFeatures());
        currentTestCase.setFeaturesDeactivated(testCase.getFeaturesDeactivated());
        currentTestCase.setUnvisitedContexts(testCase.getUnvisitedContexts());
        currentTestCase.setUnexpectedFeatures(testCase.getUnexpectedFeatures());
        currentTestCase.setWarnings(testCase.getWarnings());
    }

    public void saveReportToFile() {
        ControlUtils.writeObjectToJsonFile(getExternalStorageDirectory() + ControlSetup.getInstance().getTestReportPath(), report);
    }

    public void removeLastTestCase() {
        currentTestSequence.removeTestCase(currentTestCase);
    }

    public void removeLastTestSequence() {
        report.removeTestSequence(currentTestSequence);
    }

    public TestCaseReport getCurrentTestCase() {
        return currentTestCase;
    }

    public void printCurrentTestCaseLog() {
        ControlUtils.printLog("------------------------------------------------------------------------");
        ControlUtils.printLog("TEST SEQUENCE: " + currentTestSeqId);
        ControlUtils.printLog("TEST CASE: " + currentTestCase.getId());

        if (currentTestCase.isPassed()) {
            ControlUtils.printLog("STATUS: PASS\nREASON: The expected features are equals to the actvated features");
        } else {
            ControlUtils.printLog("STATUS: FAIL\nREASON: "+currentTestCase.getReason());
        }

        if (!currentTestCase.getReason().equals(Reason.TIMEOUT.toString())) {
            ControlUtils.printLog("CONTEXT STATE: " + currentTestCase.getContextState());
            ControlUtils.printLog("EXPECTED FEATURES: " + currentTestCase.getExpectedFeatures());
            ControlUtils.printLog("ACTIVATED FEATURES: " + currentTestCase.getActualFeatures());

            if (currentTestCase.haveFeaturesDeactivated()) {
                ControlUtils.printLog("DEACTIVATED FEATURES: " + currentTestCase.getFeaturesDeactivated());
            }

            if (currentTestCase.haveUnexpectedFeatures()) {
                ControlUtils.printLog("UNEXPECTED FEATURES: " + currentTestCase.getUnexpectedFeatures());
            }

            if (currentTestCase.haveUnvisitedContexts()) {
                ControlUtils.printLog("UNVISITED CONTEXTS: " + currentTestCase.getUnvisitedContexts());
            }

            if (currentTestCase.haveWarnings()) {
                for (String warningMessage : currentTestCase.getWarnings()) {
                    ControlUtils.printLog("WARNING: " + warningMessage);
                }
            }
        }
    }
}
