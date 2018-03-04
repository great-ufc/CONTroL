/*
* File: TestSequence.java 
* Created: 2017-08-31
* Authors: Ismayle Santos, Erick Barros, Pedro Alcântara
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.models;

import java.util.ArrayList;

import great.ufc.br.control.ControlUtils;

public class TestSequences {
    private static TestSequences testSequencesInstance;

    private int currentPosTestCase;
    private int currentPosTestSequence;
    private TestCase currentTestCase;
    private ArrayList<TestCase> currentTestSequence;
    private ArrayList<ArrayList<TestCase>> testSequences;
    private boolean isCurrentSeqFinished;
    private boolean isFinished;

    public TestSequences() {
    }

    public static TestSequences getInstance() {
        if (testSequencesInstance == null) {
            testSequencesInstance = ControlUtils.initializeTestSequences();
            testSequencesInstance.setCurrentPosTestCase(0);
            testSequencesInstance.setCurrentPosTestSequence(0);
            testSequencesInstance.setCurrentTestSequence(testSequencesInstance.getTestSequences().get(0));
            testSequencesInstance.setCurrentTestCase(testSequencesInstance.getCurrentTestSequence().get(0));
//            testSequencesInstance.getCurrentTestCase().startTimoutTimer();
        }
        return testSequencesInstance;
    }

    public void setCurrentPosTestCase(int currentPosTestCase) {
        this.currentPosTestCase = currentPosTestCase;
    }

    public void setCurrentPosTestSequence(int currentPosTestSequence) {
        this.currentPosTestSequence = currentPosTestSequence;
    }

    public void setCurrentTestCase(TestCase currentTestCase) {
        this.currentTestCase = currentTestCase;
    }

    public void setCurrentTestSequence(ArrayList<TestCase> currentTestSequence) {
        this.currentTestSequence = currentTestSequence;
    }

    public void setCurrentSeqFinished(boolean currentSeqFinished) {
        isCurrentSeqFinished = currentSeqFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public TestCase getCurrentTestCase() {
        return currentTestCase;
    }

    public ArrayList<TestCase> getCurrentTestSequence() {
        return currentTestSequence;
    }

    public void moveNextTestCase() {
        if ((currentPosTestCase + 1) == currentTestSequence.size()) {
            isCurrentSeqFinished = true;
        } else {
            currentTestCase.stopTimoutTimer();

            TestCase previousTestCase = currentTestCase;
            currentPosTestCase++;
            currentTestCase = currentTestSequence.get(currentPosTestCase);
            currentTestCase.setActualFeatureStatus(
                    new ArrayList<String>(previousTestCase.getActualFeatureStatus())
            );

            //currentTestCase.startTimoutTimer();
        }
    }

    public void moveNextTestSeq() {
        if ((currentPosTestSequence + 1) == testSequences.size()) {
            isFinished = true;
        } else {
            TestCase previousTestCase = currentTestCase;

            currentPosTestSequence++;
            currentTestSequence = testSequences.get(currentPosTestSequence);

            currentPosTestCase = 0;
            isCurrentSeqFinished = false;
            currentTestCase = currentTestSequence.get(currentPosTestCase);

            currentTestCase.setActualFeatureStatus(
                    new ArrayList<String>(previousTestCase.getActualFeatureStatus())
            );
        }
    }

    public boolean isCurrentSeqFinished() {
        return isCurrentSeqFinished;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setTestSequences(ArrayList<ArrayList<TestCase>> testSequences) {
        this.testSequences = testSequences;
    }

    public ArrayList<ArrayList<TestCase>> getTestSequences() {
        return testSequences;
    }

}
