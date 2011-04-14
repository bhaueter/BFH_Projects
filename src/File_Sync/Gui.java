package File_Sync;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.*;

//////////////////////////////////////////////////////// CountWords Test
public class Gui extends JFrame {

	// Variable Definition
	
	// Gui Elements
    JTextField   fromfileNameTF  = new JTextField(30);
    JTextField   tofileNameTF  = new JTextField(30);
    JFileChooser fileChooser = new JFileChooser();

    final DefaultListModel Model = new DefaultListModel(); 
    String bs = "\\";
    
    File fromPath, fromFilename, toPath, toFilename;
    
    
    // Colors
    Color white, black;
    
    
    

    //================================================= constructor
    Gui() {
        //... Create / set component characteristics.
        fromfileNameTF.setEditable(false);
        //fromfileNameTF.setSize(100, 25);
        tofileNameTF.setEditable(false);
        //tofileNameTF.setSize(10, 50);

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //... Create elements Add listeners
        JButton BuFrom	= new JButton("Path From");
        JButton BuTo	= new JButton("Path To");
        JButton BuStart	= new JButton("Start");
        JButton BuStopp	= new JButton("Stopp");
        JButton BuProtocol = new JButton("Protocol");
        BuFrom.addActionListener(new OpenAction());
        BuTo.addActionListener(new OpenAction());
        BuStart.addActionListener(new StartAction());
       // BuStopp.addActionListener(new StopAction());
        
        
        //... Create content pane, layout components

        // FromPanel
        JPanel pathFromPanel = new JPanel();
        pathFromPanel.setBorder(BorderFactory.createTitledBorder("From Destination"));
        pathFromPanel.setLayout(new BoxLayout(pathFromPanel, BoxLayout.X_AXIS)); 
        pathFromPanel.add(BuFrom);
        pathFromPanel.add(fromfileNameTF);
        
        JPanel pathToPanel = new JPanel();
        pathToPanel.setBorder(BorderFactory.createTitledBorder("To Destination"));
        pathToPanel.setLayout(new BoxLayout(pathToPanel, BoxLayout.X_AXIS)); 
        pathToPanel.add(BuTo);
        pathToPanel.add(tofileNameTF);
        
        JPanel dirPanel = new JPanel();
        dirPanel.setLayout(new BoxLayout(dirPanel, BoxLayout.Y_AXIS));
        dirPanel.setBackground(white);
        dirPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        dirPanel.add(pathFromPanel);
        dirPanel.add(pathToPanel);
        dirPanel.setPreferredSize(getMinimumSize());
        
        
        //List Panel
        JPanel listPanel = new JPanel();
        JList fileList = new JList(Model);  /*getPath().list()*/
        fileList.setLayoutOrientation(JList.VERTICAL);
        fileList.setVisibleRowCount(-1);
        
        JScrollPane listScroller = new JScrollPane(fileList);
        listScroller.setPreferredSize(new Dimension(200, 200));
        
        listPanel.add(fileList);
        
        
        
        
        JPanel protPanel = new JPanel();
        
        
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
        infoPanel.setLayout(new GridLayout(1,2));
        infoPanel.setBackground(white);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        infoPanel.add(listPanel);
        infoPanel.add(protPanel);

        
        JPanel mergePanel = new JPanel();
        mergePanel.setLayout(new BoxLayout(mergePanel, BoxLayout.Y_AXIS));
        mergePanel.add(dirPanel);
        mergePanel.add(infoPanel);
        
        
        
        listPanel.add(fileList);
        
        JPanel perform = new JPanel();
        perform.add(BuStart);
        perform.add(BuStopp);
        perform.add(BuProtocol);
        
        JPanel display = new JPanel();
        display.setLayout(new BorderLayout());
        display.setBounds(0, 0, 500, 400);
        display.add(mergePanel,BorderLayout.CENTER);
        display.add(perform, BorderLayout.SOUTH);
        

/*        //... Assemble the menu
        menubar.add(fileMenu);
        fileMenu.add(openItem);
*/
        //... Set window characteristics
//        this.setJMenuBar(menubar);
        this.setContentPane(display);
        this.setTitle("File Snychronization");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();                      // Layout components.
        this.setLocationRelativeTo(null); // Center window.
    }


    // OpenAction
    class OpenAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
            //... Open a file dialog.
            int retval = fileChooser.showOpenDialog(Gui.this);
            JButton BuTmp = (JButton)ae.getSource();
            
            if (retval == JFileChooser.APPROVE_OPTION) {
                //... The user selected a file, get it, use it.
                File path = fileChooser.getCurrentDirectory();
                File file = fileChooser.getSelectedFile();
                
                if (BuTmp.getText() == "Path From"){
                fromfileNameTF.setText(path.getAbsolutePath() +bs +file.getName());
                setFromPath(path);
                setFromFilename(file);
                updateJlist();
                }
                else
                tofileNameTF.setText(path.getAbsolutePath() +bs +file.getName());
                setToPath(path);
                setToFilename(file);
            }
        }

    }
    
    ///////////////////////////////////////////////////// StartAction
    class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
            if (getFromFilename() != null && getToFilename() != null) {
            	try {
					startup.sync(fromPath, fromFilename, toPath, toFilename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

    }
    
    
    
    public void setFromPath(File p){
    	 fromPath = p;
    }
    public void setFromFilename(File n){
    	fromFilename = n;
   }
    
    public File getFromPath(){
    	return fromPath;
    }
    public File getFromFilename(){
    	return fromFilename;
    }
    public void setToPath(File p){
   	 toPath = p;
   }
   public void setToFilename(File n){
  	 toFilename = n;
  }
   
   public File getToPath(){
   	return toPath;
   }
   public File getToFilename(){
   	return toFilename;
   }
    
    
   
   
    
    public void updateJlist(){
    File f = getFromPath();
    String[] elements = f.list();

    
    for(int i = 0; i < elements.length; i++ ){
      Model.addElement(elements[i])	;
    }
    	
    	
    }
    
    
    
    
 /*   
    //============================================= Get List String 
    public Object[] getListData(File f){
    	
    	
    		f.
    	
            try {
                Scanner in = new Scanner(f);

                while (in.hasNext()) {
                    String word = in.next();  // Read a "token".
                    numberOfWords++;
                }
                in.close();        // Close Scanner's file.

            } catch (FileNotFoundException fnfex) {
                // ... We just got the file from the JFileChooser,
                //     so it's hard to believe there's problem, but...
                JOptionPane.showMessageDialog(Gui.this,
                            fnfex.getMessage());
            }
            return numberOfWords;
        }
    	
    	
    	return 
    }
    */
    /*
     * 
     */
}

