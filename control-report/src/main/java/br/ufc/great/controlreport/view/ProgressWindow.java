package br.ufc.great.controlreport.view;

//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JButton;
//import javax.swing.JProgressBar;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Listener;
//
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
//import com.jgoodies.forms.layout.FormSpecs;
//import java.awt.GridBagLayout;
//import java.awt.GridBagConstraints;
//import javax.swing.JDesktopPane;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.event.ActionEvent;
//import javax.swing.JLabel;

public class ProgressWindow {
//public class ProgressWindow extends JFrame {
//	JProgressBar progressBar;
//	ProgressWindow frame;
//	
//	public ProgressWindow(Thread reportThread) {
//	}
//	
//	public void open() {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					frame = new ProgressWindow();
//					frame.setVisible(true);
//					frame.addWindowListener(new WindowAdapter() {
//						  public void windowClosing(WindowEvent windowEvent){
//					            System.exit(0);
//					         }  
//					});
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	public ProgressWindow() {
//		setTitle("Generating report...");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 179);
//		
//		JDesktopPane desktopPane = new JDesktopPane();
//		desktopPane.setBackground(Color.WHITE);
//		getContentPane().add(desktopPane, BorderLayout.CENTER);
//		
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		btnCancel.setBounds(169, 94, 89, 23);
//		btnCancel.addActionListener(new ActionListener() {
//	         public void actionPerformed(ActionEvent e) {
//	        	 System.exit(0);
//	         }
//	      });
//		
//		desktopPane.add(btnCancel);
//		progressBar = new JProgressBar();
//		progressBar.setBounds(34, 35, 371, 29);
//		progressBar.setValue(0);
//	    progressBar.setStringPainted(true);
//	    progressBar.setMinimum(0);
//	    progressBar.setMaximum(100);
//	    
//	    desktopPane.add(progressBar);
//	}
//	
//	public JProgressBar getProgressBarComponent() {
//		return this.progressBar;
//	}
}
