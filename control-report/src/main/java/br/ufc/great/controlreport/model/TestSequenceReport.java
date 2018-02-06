package br.ufc.great.controlreport.model;

import java.util.ArrayList;

/**
 * Created by Erick on 01/07/2017.
 */

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

