package br.ufc.great.controlreport.model;

import java.util.ArrayList;

/**
 * Created by Erick on 05/07/2017.
 */

public class Report {
    private ArrayList<TestSequenceReport> testSequences;

    public Report() {
        testSequences = new ArrayList<TestSequenceReport>();
    }

    public ArrayList<TestSequenceReport> getTestSequences() {
        return testSequences;
    }

    public void setTestSequences(ArrayList<TestSequenceReport> testSequences) {
        this.testSequences = testSequences;
    }

    public void addTestSequence(TestSequenceReport testSeq) {
        testSequences.add(testSeq);
    }

    public void removeTestSequence(TestSequenceReport testSeq) {
        testSequences.remove(testSeq);
    }
}
