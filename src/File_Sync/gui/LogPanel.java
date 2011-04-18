package File_Sync.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import File_Sync.log4j.Log4j;

public class LogPanel implements ActionListener {
	
	private static final int INTERVALL_4_TIMER = 1000;
	
//	public JTextArea tf;
	JTextArea tf;
	private Log4j log;
	
	private Timer t;
	private boolean isNotStopped = false;
	
	private File logFile;

	public LogPanel(Log4j log) {
		this.log = log;
	}

	public JPanel getPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		
		panel.setBorder(new LineBorder(Color.BLACK, 1));
		
		tf = new JTextArea();
		tf.setEditable(false);
				
		panel.add(tf);	
		
		JScrollPane scrollPane = new JScrollPane(tf);
		panel.add(scrollPane);
		
		panel.setBackground(Color.PINK);
		
		//Timer & start Display LogFile
		t = new Timer(INTERVALL_4_TIMER, this);
		startWatchLogFile();
		
		
		return panel;		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		try {
			tf.setText(log.getStringLogFile());
			
			logFile = log.getLogFile();
			
		} catch (IOException e1) {
			log.logger.error("Log4j(): Couldn't get the Text from the LogFile to show it in TextField: "+ tf.getName());
		}
	}
	
	public void startWatchLogFile()
	{
		this.t.start();
		while(isNotStopped)
		{
			t.stop();
		}
	}
	public void stopWatchLogFile()
	{
		isNotStopped = false;
	}	
}
