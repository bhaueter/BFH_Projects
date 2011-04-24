package File_Sync.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.apache.log4j.Logger;

import File_Sync.StartUp;
import File_Sync.log4j.Log4j;
import File_Sync.synchronizer.FileOptions;

import java.io.*;

public class Gui extends JFrame {

	// Variable Definition

	// Gui Elements
	JTextField fromfileNameTF = new JTextField(30);
	JTextField tofileNameTF = new JTextField(30);
	JFileChooser fileChooser = new JFileChooser();

	final DefaultListModel Model = new DefaultListModel();
	String bs = "\\";

	File fromPath, fromFilename, toPath, toFilename;

	private Log4j log;
	public static Logger logger = Logger.getRootLogger();
	private FileOptions fOpt;

	JProgressBar pb = new JProgressBar();

	FileTree ft = new FileTree(this);

	// ================================================= constructor
	public Gui(Log4j log) {

		// Log4j
		this.log = log;

		log.logger.debug("Open Gui");

		// ... Create / set component characteristics.
		fromfileNameTF.setEditable(false);
		tofileNameTF.setEditable(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// ... Create elements Add listeners
		JButton BuFrom = new JButton("Path From");

		JButton BuTo = new JButton("Path To");
		JButton BuStart = new JButton("Start");
		JButton BuStopp = new JButton("Stopp");
		JButton BuProtocol = new JButton("Protocol");

		// .... Button Action Listeners
		BuFrom.addActionListener(new OpenAction());
		BuTo.addActionListener(new OpenAction());
		BuStart.addActionListener(new StartAction());

		JLabel infoLabel = new JLabel();
		infoLabel.setPreferredSize(new Dimension(750, 270));

		// BuStopp.addActionListener(new StopAction());

		// ... Create content pane, layout components

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

		JList fileList = new JList(Model);
		JScrollPane scrol = new JScrollPane(fileList);
		scrol.setBorder(BorderFactory
				.createTitledBorder("Selected Dirs & Files"));

		JPanel protPanel = new JPanel();
		LogPanel logPanel = new LogPanel(log);
		protPanel = logPanel.getPanel();
		protPanel.setBorder(BorderFactory
				.createTitledBorder("Infos & Protocol"));
		protPanel.add(infoLabel);
		protPanel.setLayout(new GridBagLayout());
		protPanel.setBackground(Color.CYAN);

		fOpt = new FileOptions(null, null, null, null, this);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

		infoPanel.add(this.ft.getTreePanel());
		infoPanel.add(protPanel);

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
		actionPanel.add(BuStart);
		actionPanel.add(BuStopp);
		actionPanel.add(BuProtocol);

		// Main Display Panel
		JPanel display = new JPanel();
		display.setLayout(new BorderLayout());
		display.add(mergePanel, BorderLayout.CENTER);
		display.add(actionPanel, BorderLayout.SOUTH);

		/*
		 * //... Assemble the menu menubar.add(fileMenu);
		 * fileMenu.add(openItem);
		 */
		// ... Set window characteristics
		// this.setJMenuBar(menubar);
		this.getContentPane().add(display);
		this.setTitle("File Snychronization");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack(); // Layout components.
		this.setLocationRelativeTo(null); // Center window.
	}

	// OpenAction
	class OpenAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			// ... Open a file dialog.
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
					// FileTree tree = new FileTree(file);
					// setFileTree(tree);
					updateJlist();
				} else
					tofileNameTF.setText(path.getAbsolutePath() + bs
							+ file.getName());
				setToPath(path);
				setToFilename(file);
			}
		}

	}

	// StartAction
	class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (getFromFilename() != null && getToFilename() != null) {
				try {
					StartUp.sync(fromPath, fromFilename, toPath, toFilename,
							Gui.this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

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

	public void updateJlist() {
		File f = getFromPath();
		String[] elements = f.list();

		for (int i = 0; i < elements.length; i++) {
			Model.addElement(elements[i]);
		}
	}

	public void setMaxProgressBar(int max) {
		this.pb.setValue(0);
		this.pb.setMaximum(max);
	}

	public void updateProgressBar(int val) {
		this.pb.setValue(val);
	}

}