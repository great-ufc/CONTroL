/*
Description: Community License for ExtentAPI

ExtentAPI and Server – Community Edition
Copyright (C) 2016-2017 aventstack
All rights reserved

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

- Redistributions of source code must retain the above copyright notice, this list of 
conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright notice, this list of 
conditions and the following disclaimer in the documentation and/or other materials provided 
with the distribution.

- Neither the name of the copyright holder nor the names of its contributors may be used to 
endorse or promote products derived from this software without specific prior written 
permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY
EXPRESSOR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
SHALL THE COPYRIGHT HOLDER ORCONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHERIN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package br.ufc.great.controlreport.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.ufc.great.controlreport.model.Report;
import br.ufc.great.controlreport.model.TestCaseReport;
import br.ufc.great.controlreport.model.TestSequenceReport;

public class ReportManager implements Runnable {
	private String jsonFilePath;
	private String dirPath;
	private Report mReport;
	
	ExtentHtmlReporter htmlReporter;
	private ExtentReports extent;
	
	private JProgressBar progressBar;
	private int progressLevel;
	
	public ReportManager(String jsonFilePath, String dirPath) {
		this.jsonFilePath = jsonFilePath;
		this.dirPath = dirPath;
		progressLevel = 1;
		
		htmlReporter = new ExtentHtmlReporter(getDirPath()+"\\CONTRoL - Report.html");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("CONTRoL");
		htmlReporter.config().setDocumentTitle("CONTrol Report");
		
		extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
	}
	
	public void generateExtentReport() {
		ArrayList<TestSequenceReport> testSequences = mReport.getTestSequences();
		ExtentTest extentSequence;
		ExtentTest extentCase;
		
		for (TestSequenceReport testSeq : testSequences) {
			extentSequence = extent.createTest("Test Sequence: " + testSeq.getId());
			for (TestCaseReport testCase : testSeq.getTestCases()) {
				extentCase = extentSequence.createNode("Test Case: " + testCase.getId());
				
				// Result
				if (testCase.isPassed()) {
					extentCase.pass(testCase.getReason());
				} else {
					extentCase.fail(testCase.getReason());
				}
				
				extentCase.info("Context State: "+testCase.getContextState());
				extentCase.info("Expected Features: "+testCase.getExpectedFeatures());
				extentCase.info("Activated Features: "+testCase.getActualFeatures());
				
				if (testCase.haveFeaturesDeactivated()) {
					extentCase.info("Deactivated Expected Features: "+testCase.getFeaturesDeactivated());
				}
				
				// Warnings
				if (testCase.haveUnexpectedFeatures()) {
					extentCase.warning("Unexpected Activated Features: "+testCase.getUnexpectedFeatures());
				}
				
				if (testCase.haveUnvisitedContexts()) {
					extentCase.warning("Unvisited Contexts: "+testCase.getUnvisitedContexts());
				}
				
				if (testCase.haveWarnings()) {
					for (String warningMsg : testCase.getWarnings()) {
						extentCase.warning(warningMsg);
					}
				}
			}	
			extent.flush();
		}
	}
	
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	public void updateProgress() {
		progressLevel++;
		progressBar.setValue(progressLevel);
	}
	
	
	public void run() {
		generateExtentReport();
	}

	public void setJsonFilePath(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void readReportFromJson() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(getJsonFilePath());
        mReport = mapper.readValue(file, Report.class);
	}
}
