package br.ufc.great.controlreport.view;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by Erick Barros on 01/07/2017.
 */

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
