/*
* File: TestCaseReport.java 
* Created: 2017-08-31
* Authors: Ismayle Santos, Erick Barros, Pedro Alcântara
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.report;

import java.util.ArrayList;

public class TestCaseReport {
    private int id;
    private boolean isPassed;
    private String reason;
    private String contextState;
    private String expectedFeatures;
    private String actualFeatures;
    private String featuresDeactivated;
    private String unexpectedFeatures;
    private String unvisitedContexts;
    private ArrayList<String> warnings;

    public TestCaseReport() {

    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void addWarning(String warningMessage) {
        this.warnings.add(warningMessage);
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public String getExpectedFeatures() {
        return expectedFeatures;
    }

    public void setExpectedFeatures(String expectedFeatures) {
        this.expectedFeatures = expectedFeatures;
    }

    public String getActualFeatures() {
        return actualFeatures;
    }

    public void setActualFeatures(String actualFeatures) {
        this.actualFeatures = actualFeatures;
    }

    public String getFeaturesDeactivated() {
        return featuresDeactivated;
    }

    public void setFeaturesDeactivated(String featuresDeactivated) {
        this.featuresDeactivated = featuresDeactivated;
    }

    public String getUnexpectedFeatures() {
        return unexpectedFeatures;
    }

    public void setUnexpectedFeatures(String unexpectedFeatures) {
        this.unexpectedFeatures = unexpectedFeatures;
    }

    public String getUnvisitedContexts() {
        return unvisitedContexts;
    }

    public void setUnvisitedContexts(String unvisitedContexts) {
        this.unvisitedContexts = unvisitedContexts;
    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(ArrayList<String> warnings) {
        this.warnings = warnings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContextState() {
        return contextState;
    }

    public void setContextState(String contextState) {
        this.contextState = contextState;
    }

    public boolean haveFeaturesDeactivated() {
        if (featuresDeactivated.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean haveUnexpectedFeatures() {
        if (unexpectedFeatures.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean haveUnvisitedContexts() {
        if (unvisitedContexts.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean haveWarnings() {
        if (warnings.size() > 0) {
            return true;
        }
        return false;
    }
}
