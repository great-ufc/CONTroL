/*
* File: TestCase.java 
* Created: 2017-08-31
* Authors: Ismayle Santos, Erick Barros, Pedro Alcântara
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import great.ufc.br.control.ControlSetup;
import great.ufc.br.control.TimeoutManager;

public class TestCase {
    private int idTransitionDFTS;
    private int id;
    private ArrayList<String> contextState;
    private Set<String> visitedContexts;
    private ArrayList<String> expectedFeatureStatus;
    private ArrayList<String> actualFeatureStatus;

    private TimeoutManager timeoutManager;

    // REPORT GENERATION
    private boolean isPassed;
    private Reason reason;
    private String expectedFeatures;
    private String actualFeatures;
    private String featuresDeactivated;
    private String unexpectedFeatures;
    private String unvisitedContexts;
    private ArrayList<String> warnings;

    public TestCase() {
        expectedFeatures = "";
        actualFeatures = "";
        featuresDeactivated = "";
        unexpectedFeatures = "";
        unvisitedContexts = "";
        visitedContexts = new HashSet<String>();
        warnings = new ArrayList<String>();
        timeoutManager = new TimeoutManager(ControlSetup.getInstance().getTimeoutPeriod());
        timeoutManager.startTimer();
    }

    public boolean assertFeatures() {
        this.isPassed = true;
        this.reason = Reason.PASS;
        StringBuilder expectedBuilder = new StringBuilder();
        StringBuilder actualBuilder = new StringBuilder();
        StringBuilder featuresDeactivatedBuilder = new StringBuilder();
        StringBuilder unexpectedFeaturesBuilder = new StringBuilder();

        for (String expected : expectedFeatureStatus) {
            boolean actualExists = actualFeatureStatus.remove(expected);

            if (actualExists) {
                expectedBuilder.append(expected + ", ");
                actualBuilder.append(expected + ", ");
            } else {
                this.isPassed = false;
                this.reason = Reason.DIVERGENT_FEATURES;
                expectedBuilder.append(expected + ", ");
                featuresDeactivatedBuilder.append(expected + ", ");
            }
        }

        if (actualFeatureStatus.size() > 0) {
            this.isPassed = false;
            this.reason = Reason.DIVERGENT_FEATURES;

            for (String actual : actualFeatureStatus) {
                actualBuilder.append(actual + ", ");
                unexpectedFeaturesBuilder.append(actual + ", ");
            }
        }

        if (expectedBuilder.length() > 0) {
            expectedBuilder.setLength(expectedBuilder.length() - 2);
            setExpectedFeatures(expectedBuilder.toString());
        }

        if (actualBuilder.length() > 0) {
            actualBuilder.setLength(actualBuilder.length() - 2);
            setActualFeatures(actualBuilder.toString());
        }

        if (featuresDeactivatedBuilder.length() > 0) {
            featuresDeactivatedBuilder.setLength(featuresDeactivatedBuilder.length() - 2);
            setFeaturesDeactivated(featuresDeactivatedBuilder.toString());
        }

        if (unexpectedFeaturesBuilder.length() > 0) {
            unexpectedFeaturesBuilder.setLength(unexpectedFeaturesBuilder.length() - 2);
            setUnexpectedFeatures(unexpectedFeaturesBuilder.toString());
        }

        return this.isPassed;
    }

    public void assertContext() {
        StringBuilder unvisitedContextsBuilder = new StringBuilder();

        for (String context : contextState) {
            if (!visitedContexts.contains(context)) {
                unvisitedContextsBuilder.append(context + ", ");
            }
        }

        if (unvisitedContextsBuilder.length() > 0) {
            unvisitedContextsBuilder.setLength(unvisitedContextsBuilder.length() - 2);
            setUnvisitedContexts(unvisitedContextsBuilder.toString());
        }
    }

    public void contextVisited(String context) {
        visitedContexts.add(context);
    }

    public boolean isPassed() {
        return this.isPassed;
    }

    public boolean haveFeaturesDeactivated() {
        if (this.featuresDeactivated.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean haveUnexpectedFeatures() {
        if (this.unexpectedFeatures.length() > 0) {
            return true;
        }
        return false;
    }

    public boolean haveUnvisitedContexts() {
        if (this.unvisitedContexts.length() > 0) {
            return true;
        }
        return false;
    }

    public String getExpectedFeatures() {
        return expectedFeatures;
    }

    private void setExpectedFeatures(String expectedFeatures) {
        this.expectedFeatures = expectedFeatures;
    }

    public String getActualFeatures() {
        return actualFeatures;
    }

    private void setActualFeatures(String actualFeatures) {
        this.actualFeatures = actualFeatures;
    }

    public String getFeaturesDeactivated() {
        return featuresDeactivated;
    }

    private void setFeaturesDeactivated(String featuresDeactivated) {
        this.featuresDeactivated = featuresDeactivated;
    }

    public String getUnexpectedFeatures() {
        return unexpectedFeatures;
    }

    private void setUnexpectedFeatures(String unexpectedFeatures) {
        this.unexpectedFeatures = unexpectedFeatures;
    }

    public String getUnvisitedContexts() {
        return this.unvisitedContexts;
    }

    private void setUnvisitedContexts(String unvisitedContexts) {
        this.unvisitedContexts = unvisitedContexts;
    }

    public int getId() {
        return id;
    }

    public void addContextState(String contextName) {
        if (!this.contextState.contains(contextName)) {
            contextState.add(contextName);
        }
    }

    public void removeContextState(String contextName) {
        contextState.remove(contextName);
    }

    public void setContextState(ArrayList<String> contextState) {
        this.contextState = contextState;
    }

    public ArrayList<String> getContextState() {
        return this.contextState;
    }

    public boolean containsContext(String context) {
        return this.contextState.contains(context);
    }

    public void addExpectedFeatures(String featureName) {
        if (!this.expectedFeatureStatus.contains(featureName)) {
            this.expectedFeatureStatus.add(featureName);
        }
    }

    public void removeExpectedFeatures(String featureName) {
        this.expectedFeatureStatus.remove(featureName);
    }

    public ArrayList<String> getExpectedFeatureStatus() {
        return expectedFeatureStatus;
    }

    public void setExpectedFeatureStatus(ArrayList<String> expectedFeatureStatus) {
        this.expectedFeatureStatus = expectedFeatureStatus;
    }

    public void addActualFeatures(String featureName) {
        if (!this.actualFeatureStatus.contains(featureName)) {
            this.actualFeatureStatus.add(featureName);
        }
    }

    public void removeActualFeatures(String featureName) {
        this.actualFeatureStatus.remove(featureName);
    }

    public ArrayList<String> getActualFeatureStatus() {
        return actualFeatureStatus;
    }

    public void setActualFeatureStatus(ArrayList<String> actualFeatureStatus) {
        this.actualFeatureStatus = actualFeatureStatus;
    }

    public void startTimoutTimer() {
        timeoutManager.startTimer();
    }

    public void stopTimoutTimer() {
        timeoutManager.stopTimer();
    }

    public void addWarning(String msg) {
        warnings.add(msg);
    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public TimeoutManager getTimeoutManager() {
        return this.timeoutManager;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public void setPassed(boolean value) {
        this.isPassed = value;
    }

    public String getContextGroupNameValue(String groupName) {
        for (String context : this.contextState) {
            if (context.startsWith(groupName+":")) {
                return context.substring(context.lastIndexOf(":")+1);
            }
        }
        return null;
    }
}