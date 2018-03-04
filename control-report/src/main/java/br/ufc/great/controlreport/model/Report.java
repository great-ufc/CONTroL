/*
* File: Report.java 
* Created: 2017-07-10
* Authors:
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package br.ufc.great.controlreport.model;

import java.util.ArrayList;

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
