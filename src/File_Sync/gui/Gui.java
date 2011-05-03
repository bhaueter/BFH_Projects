package File_Sync.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

import File_Sync.StartUp;
import File_Sync.log4j.Log4j;
import File_Sync.synchronizer.FileOptions;

import java.io.*;

public class Gui extends JFrame {

	// Variable Definition
	//-----------------------------------------------------------------------
	
	// Constants
	private static String bs = "\\";
	
	// Gui Elements
	JTextField fromfileNameTF = new JTextField(30);
	JTextField tofileNameTF = new JTextField(30);
	JFileChooser fileChooser = new JFileChooser();
	JProgressBar pb = new JProgressBar();
	FileTree ft = new FileTree(this);
		
	// File Variables
	private File fromPath, fromFilename, toPath, toFilename;

	// Log Elements
	private Log4j log;
	public static Logger logger = Logger.getRootLogger();
	
	// Status Elements
	boolean isStopped = false;
	//-----------------------------------------------------------------------
	
	
	
	// Gui Constructor
	public Gui(Log4j log) {

		// Log4j
		this.log = log;

		log.logger.debug("Open Gui");
		log.logger.info("SSP: 1.Messageeee");

		// Create / set component characteristics.
		fromfileNameTF.setEditable(false);
		tofileNameTF.setEditable(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Create Buttons
		JButton BuFrom = new JButton("Path From");
		JButton BuTo = new JButton("Path To");
		JButton BuStart = new JButton("Start");
		JButton BuStopp = new JButton("Stopp");

		// Button Action Listeners
		BuFrom.addActionListener(new OpenAction());
		BuTo.addActionListener(new OpenAction());
		BuStart.addActionListener(new StartAction());
		BuStopp.addActionListener(new StoppAction());

		// Create content pane, layout components

		// FromPanel
		JPanel pathFromPanel = new JPanel();
		pathFromPanel.setBorder(BorderFactory
				.createTitledBorder("From Destination"));
		pathFromPanel.setBackground(Color.WHITE);
		pathFromPanel.setLayout(new BoxLayout(pathFromPanel, BoxLayout.X_AXIS));
		pathFromPanel.add(BuFrom);
		pathFromPanel.add(fromfileNameTF);

		JPanel pathToPanel = new JPanel();
		pathToPanel.setBorder(BorderFactory
				.createTitledBorder("To Destination"));
		pathToPanel.setBackground(Color.WHITE);
		pathToPanel.setLayout(new BoxLayout(pathToPanel, BoxLayout.X_AXIS));
		pathToPanel.add(BuTo);
		pathToPanel.add(tofileNameTF);

		JPanel dirPanel = new JPanel();
		dirPanel.setLayout(new BoxLayout(dirPanel, BoxLayout.Y_AXIS));
		dirPanel.setBackground(Color.WHITE);
		dirPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		dirPanel.add(pathFromPanel);
		dirPanel.add(pathToPanel);

		LogPanel logPanel = new LogPanel(log);
		JScrollPane protPanel = new JScrollPane();
		protPanel = logPanel.getPanel();
		protPanel.setBorder(BorderFactory.createLineBorder(Color.black));


		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.add(this.ft.getTreePanel());
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));


		// Progress Bar Panel
		JPanel progressPanel = new JPanel();
		progressPanel.setBorder(BorderFactory.createTitledBorder("Progress"));
		progressPanel.setBackground(Color.WHITE);
		progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
		progressPanel.setSize(250, 50);
		pb.setVisible(true);
		pb.setValue(0);
		progressPanel.add(pb);

		// Merge Panel
		JPanel mergePanel = new JPanel();
		mergePanel.setLayout(new BoxLayout(mergePanel, BoxLayout.Y_AXIS));
		mergePanel.add(dirPanel);
		mergePanel.add(infoPanel);
		mergePanel.add(progressPanel);

		// Action Panel
		JPanel actionPanel = new JPanel();
		actionPanel.setBorder(BorderFactory.createTitledBorder("Action"));
		actionPanel.setBackground(Color.WHITE);
		actionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		actionPanel.add(BuStart);
		actionPanel.add(BuStopp);

		// Main Display Panel
		JPanel display = new JPanel();
		display.setLayout(new BorderLayout());
		display.add(mergePanel, BorderLayout.CENTER);
		display.add(actionPanel, BorderLayout.SOUTH);
		display.add(protPanel, BorderLayout.EAST);


		this.getContentPane().add(display);
		this.setTitle("File Snychronization");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack(); // Layout components.
		this.setLocationRelativeTo(null); // Center window.
	}

	// OpenAction
	class OpenAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// Open a file dialog.
			int retval = fileChooser.showOpenDialog(Gui.this);
			JButton BuTmp = (JButton) ae.getSource();

			if (retval == JFileChooser.APPROVE_OPTION) {
				// ... The user selected a file, get it, use it.
				File path = fileChooser.getCurrentDirectory();
				File file = fileChooser.getSelectedFile();

				if (BuTmp.getText() == "Path From") {
					fromfileNameTF.setText(path.getAbsolutePath() + bs
							+ file.getName());
					setFromPath(path);
					setFromFilename(file);
					updateTree();
				}
				else{
					tofileNameTF.setText(path.getAbsolutePath() + bs
							+ file.getName());
					setToPath(path);
					setToFilename(file);
					}
			}
		}
	}

	// StartAction
	class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (getFromFilename() != null && getToFilename() != null
				&& getFromFilename() != getToFilename()) {
				try {
					isStopped = false;
					StartUp.sync(fromPath, fromFilename, toPath, toFilename,
							Gui.this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				logger.warn("Missing path or equal path!");
			}
		}
	}

	// StoppAction
	class StoppAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
	
			isStopped = true;
			logger.info("Stop is clicked!");
		}
	}

	public boolean getIsStopped() {
		return isStopped;
	}

	public void setIsStopped(boolean status) {
		isStopped = status;
	}

	public void setFromPath(File p) {
		fromPath = p;
	}

	public void setFromFilename(File n) {
		fromFilename = n;
	}

	public File getFromPath() {
		return fromPath;
	}

	public File getFromFilename() {
		return fromFilename;
	}

	public void setToPath(File p) {
		toPath = p;
	}

	public void setToFilename(File n) {
		toFilename = n;
	}

	public File getToPath() {
		return toPath;
	}

	public File getToFilename() {
		return toFilename;
	}

	public void setFileTree(FileTree obj) {
		this.ft = obj;
		ft.getTreePanel();
	}

	public void updateTree() {
		this.ft.updateTreeModel(getFromFilename());
	}

	public void updatePath(File f) {
		fromfileNameTF.setText(f.getAbsolutePath() + bs + f.getName());
		setFromFilename(f);
		updateTree();
	}


	public void setMaxProgressBar(int max) {
		this.pb.setValue(0);
		this.pb.setMaximum(max);
	}

	public void updateProgressBar(int val) {
		this.pb.setValue(val);
	}

	public void wirteLogMsg(String str) {
		logger.info(str);
	}

}