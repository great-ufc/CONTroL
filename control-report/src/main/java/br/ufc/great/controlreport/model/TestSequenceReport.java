/*
* File: TestSequenceReport.java 
* Created: 2017-07-10
* Authors: 
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package br.ufc.great.controlreport.model;

import java.util.ArrayList;

public class TestSequenceReport {
    private int id;
    private ArrayList<TestCaseReport> testCases;

    public TestSequenceReport() {

    }

    public ArrayList<TestCaseReport> getTestCases() {
        return testCases;
    }

    public void setTestCases(ArrayList<TestCaseReport> testCases) {
        this.testCases = testCases;
    }

    public void addTestCase(TestCaseReport testCase) {
        testCases.add(testCase);
    }

    public  void removeTestCase(TestCaseReport testCase) {
        testCases.remove(testCase);
    }

    public int getId() {
        return id;
    }
}

