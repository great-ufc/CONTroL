/*
* File: ControlSetup.java 
* Created: 2017-08-31
* Authors: Ismayle Santos, Erick Barros, Pedro Alcântara
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control;

import java.util.ArrayList;

import great.ufc.br.control.models.ContextDomain;

public class ControlSetup {
    private static ControlSetup controlSetupInstance;

    private String testSequencePath;
    private String testReportPath;

    private int timeoutPeriod;

    private ArrayList<String> featuresAvaliable;
	private ContextDomain contextDomain;

    private ControlSetup() {
    }

    public static ControlSetup getInstance() {
        if (controlSetupInstance == null) {
            controlSetupInstance = ControlUtils.initializeControlSetup();
        }

        return controlSetupInstance;
    }

    public String getTestSequencePath() {
        return testSequencePath;
    }

    public void setTestSequencePath(String testSequencePath) {
        this.testSequencePath = testSequencePath;
    }

    public String getTestReportPath() {
        return testReportPath;
    }

    public void setTestReportPath(String testReportPath) {
        this.testReportPath = testReportPath;
    }

    public ArrayList<String> getFeaturesAvaliable() {
        return featuresAvaliable;
    }

    public void setFeaturesAvaliable(ArrayList<String> featuresAvaliable) {
        this.featuresAvaliable = featuresAvaliable;
    }

    public ContextDomain getContextDomain() {
        return contextDomain;
    }

    public long getTimeoutPeriod() {
        return timeoutPeriod * 1000;
    }
}