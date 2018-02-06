package br.ufc.great.controlreport.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.ufc.great.controlreport.controller.ReportManager;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Event;

public class MainWindow {

	protected Shell shlControlReport;
	private Thread reportThread;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlControlReport.open();
		shlControlReport.layout();
		while (!shlControlReport.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlControlReport = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shlControlReport.setSize(450, 300);
		shlControlReport.setText("CONTRoL - Report Generator");
		shlControlReport.setLayout(new FormLayout());
		
		final Text txtFdSource = new Text(shlControlReport, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.top = new FormAttachment(0, 65);
		fd_text.left = new FormAttachment(0, 10);
		fd_text.right = new FormAttachment(0, 397);
		txtFdSource.setLayoutData(fd_text);
		
		final Text txtFdDestiny = new Text(shlControlReport, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(txtFdSource, 0, SWT.RIGHT);
		fd_text_1.left = new FormAttachment(txtFdSource, 0, SWT.LEFT);
		txtFdDestiny.setLayoutData(fd_text_1);
		
		Button btnSourceFile = new Button(shlControlReport, SWT.NONE);
		FormData fd_button = new FormData();
		fd_button.top = new FormAttachment(txtFdSource, -2, SWT.TOP);
		fd_button.right = new FormAttachment(100, -10);
		btnSourceFile.setLayoutData(fd_button);
		btnSourceFile.setText("...");
		
		Button btnDestinyDir = new Button(shlControlReport, SWT.NONE);
		fd_text_1.top = new FormAttachment(btnDestinyDir, 2, SWT.TOP);
		btnDestinyDir.setText("...");
		FormData fd_button_1 = new FormData();
		fd_button_1.top = new FormAttachment(btnSourceFile, 44);
		fd_button_1.right = new FormAttachment(btnSourceFile, 0, SWT.RIGHT);
		btnDestinyDir.setLayoutData(fd_button_1);
		
		Button btnReportGenerator = new Button(shlControlReport, SWT.NONE);
		FormData fd_btnGenerateReport_1 = new FormData();
		fd_btnGenerateReport_1.bottom = new FormAttachment(100, -40);
		fd_btnGenerateReport_1.right = new FormAttachment(100, -168);
		btnReportGenerator.setLayoutData(fd_btnGenerateReport_1);
		btnReportGenerator.setText("Generate Report");
		
		Label lblJsonFilePath = new Label(shlControlReport, SWT.NONE);
		FormData fd_lblJsonFilePath = new FormData();
		fd_lblJsonFilePath.bottom = new FormAttachment(txtFdSource, -6);
		fd_lblJsonFilePath.left = new FormAttachment(txtFdSource, 0, SWT.LEFT);
		lblJsonFilePath.setLayoutData(fd_lblJsonFilePath);
		lblJsonFilePath.setText("Json file path:");
		
		Label lblReportDirectoryPath = new Label(shlControlReport, SWT.NONE);
		FormData fd_lblReportDirectoryPath = new FormData();
		fd_lblReportDirectoryPath.bottom = new FormAttachment(txtFdDestiny, -6);
		fd_lblReportDirectoryPath.left = new FormAttachment(txtFdSource, 0, SWT.LEFT);
		lblReportDirectoryPath.setLayoutData(fd_lblReportDirectoryPath);
		lblReportDirectoryPath.setText("Report directory path:");
		
		
		btnSourceFile.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				FileChooser fileChooser = new FileChooser();
            	fileChooser.selectFile();
            	if (fileChooser.getFilePath() != null) {
            		txtFdSource.setText(fileChooser.getFilePath());
            	}
			}
		});
		
		btnDestinyDir.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event) {
				FileChooser fileChooser = new FileChooser();
            	fileChooser.selectDirectory();
            	if (fileChooser.getDirectoryPath() != null) {
            		txtFdDestiny.setText(fileChooser.getDirectoryPath());
            	}
			}
		});
		
		
		btnReportGenerator.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
            	
            	
            	if (txtFdSource.getText() == null){
            		createWarningMessage("Error", "Fill the source field.");
                } else if (txtFdDestiny.getText().isEmpty()) {
                	createWarningMessage("Error", "Fill the destiny field.");
                } else {
                	ReportManager reportManager = new ReportManager(txtFdSource.getText(), 
                			txtFdDestiny.getText());
                	
                	try {
//                		ProgressWindow progressWindow = new ProgressWindow();
    					reportManager.readReportFromJson();
//    					progressWindow.open();
//    					reportManager.setProgressBar(progressWindow.getProgressBarComponent());
    					reportThread = new Thread(reportManager);
    					reportThread.start();
//    					reportManager.setProgressBar(progressWindow.getProgressBarComponent());
//    					progressWindow.open();
                    	
    				} catch (JsonParseException e) {
    					createWarningMessage("Error while reading Json file.", e.getMessage());
    				} catch (JsonMappingException e) {
    					createWarningMessage("Error while reading Json file.", e.getMessage());
    				} catch (IOException e) {
    					createWarningMessage("Error while reading Json file.", e.getMessage());
    				}
                	
                	
                	
                }
            }
        });
	}
	
	protected void createWarningMessage(String text, String message) {
		MessageBox messageBox = new MessageBox(shlControlReport, SWT.OK | SWT.ICON_ERROR);
		messageBox.setText(text);
		messageBox.setMessage(message);
		messageBox.open();
	}
}