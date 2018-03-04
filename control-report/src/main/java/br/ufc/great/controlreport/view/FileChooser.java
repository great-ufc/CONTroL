/*
* File: FileChooser.java 
* Created: 2017-07-10
* Authors: 
*   - Ismayle de Sousa Santos
*   - Erick Barros dos Santos
*   - Rossana Maria de Castro Andrade
*   - Pedro de Alcântara dos Santos Neto
*/

package br.ufc.great.controlreport.view;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	private JFileChooser chooser;
	private FileNameExtensionFilter jsonFilter;
	private String filePath;
	private String directoryPath;
	
	public FileChooser() {
		chooser = new JFileChooser();
		jsonFilter = new FileNameExtensionFilter("JSON files", "json");
	}
	
	public void selectFile() {
		chooser.setFileFilter(jsonFilter);
		chooser.setDialogTitle("Select the file");	
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			setFilePath(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void selectDirectory() {
		chooser.setDialogTitle("Select the directory");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	setDirectoryPath(chooser.getSelectedFile().getAbsolutePath());
	    }
	}
	
	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
}
