/*
* File: TestSequenceReport.java 
* Created: 2017-08-31
* Authors: Ismayle Santos, Erick Barros, Pedro Alcântara
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package great.ufc.br.control.report;

import java.util.ArrayList;

public class TestSequenceReport {
    private int id;
    private ArrayList<TestCaseReport> testCases;

    public TestSequenceReport(int id) {
        this.id = id;
        testCases = new ArrayList<TestCaseReport>();
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
