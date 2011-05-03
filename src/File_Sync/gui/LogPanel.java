package File_Sync.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import File_Sync.log4j.Log4j;

public class LogPanel implements ActionListener {
	
	private static final int INTERVALL_4_TIMER = 5000;
	
	private JTextArea tf;
	private Log4j log;
	
	private Timer t;
	private boolean isNotStopped = false;

	private String initTxtVal = "";
	
	/*
	 * Show the Data of the Logfile
	 * @param: Log4j() Object, which includes the logFilePath
	 */
	public LogPanel(Log4j log) {
		this.log = log;
	}

	/*
	 * Returns a JPanel, within a Texarea-Field which includes the Data of
	 * the LogFile
	 */
	public JScrollPane getPanel() {
		
		tf = new JTextArea();
		tf.setBorder(BorderFactory.createTitledBorder("Infos & Protocol"));
		tf.setEditable(false);
		tf.setText(initTxtVal);	
			
		JScrollPane scrollPane = new JScrollPane(tf);
		scrollPane.setPreferredSize(new Dimension(400, 100));

		//Timer & start Display LogFile
		t = new Timer(INTERVALL_4_TIMER, this);
		startWatchLogFile();
		
		return scrollPane;		
	}
		
	/*
	 * Refreshes the Textarea
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			tf.setText(log.getStringLogFile());
			
			System.out.println("Timer every ("+INTERVALL_4_TIMER+") Text:\"" + log.getStringLogFile()+"\"");
			
		} catch (IOException e1) {
			log.logger.error("Log4j(): Couldn't get the Text from the LogFile to show it in TextField: "+ tf.getName());
		}
	}
	
	/*
	 * Starts the Time for refreshing the TextArea on Gui
	 */
	public void startWatchLogFile()
	{
		this.t.start();
		while(isNotStopped)
		{
			t.stop();
		}
	}
	/*
	 * Stops the Time for refreshing the TextArea on Gui
	 */
	public void stopWatchLogFile()
	{
		isNotStopped = false;
	}	
		
}
