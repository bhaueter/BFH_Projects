package File_Sync;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.*;

//////////////////////////////////////////////////////// CountWords
public class Gui extends JFrame {

    //====================================================== fields
    JTextField   fromfileNameTF  = new JTextField(30);
    JTextField   tofileNameTF  = new JTextField(30);
    JFileChooser fileChooser = new JFileChooser();

    final DefaultListModel Model = new DefaultListModel(); 
    String bs = "\\";
    
    File fromPath, fromFilename, toPath, toFilename;
    

    //================================================= constructor
    Gui() {
        //... Create / set component characteristics.
        fromfileNameTF.setEditable(false);
        tofileNameTF.setEditable(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //... Create elements Add listeners
        JButton BuFrom	= new JButton("Path From");
        BuFrom.setBounds(0, 0, 20, 5);
        JButton BuTo	= new JButton("Path To");
        BuTo.setBounds(0, 0, 20, 5);
        JButton BuStart	= new JButton("Start");
        JButton BuStopp	= new JButton("Stopp");
        JButton BuProtocol = new JButton("Protocol");
        BuFrom.addActionListener(new OpenAction());
        BuTo.addActionListener(new OpenAction());
        BuStart.addActionListener(new StartAction());
       // BuStopp.addActionListener(new StopAction());
        
        
        //... Create content pane, layout components

        
        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new GridLayout(2,2));
        pathPanel.add(BuFrom);
        pathPanel.add(fromfileNameTF);
        pathPanel.add(BuTo);
        pathPanel.add(tofileNameTF);
        
        JPanel listPanel = new JPanel();
        JList fileList = new JList(Model);  /*getPath().list()*/
        
        JScrollPane listScroller = new JScrollPane(fileList);
        fileList.setLayoutOrientation(JList.VERTICAL_WRAP);
        listScroller.setVisible(true);
        listScroller.setPreferredSize(new Dimension(250, 80));

        
        listPanel.add(fileList);
        
        JPanel perform = new JPanel();
        perform.add(BuStart);
        perform.add(BuStopp);
        perform.add(BuProtocol);
        
        JPanel display = new JPanel();
        display.setLayout(new BorderLayout());
        display.setBounds(0, 0, 500, 400);
        display.add(pathPanel,BorderLayout.NORTH);
        display.add(listPanel, BorderLayout.WEST);
        display.add(perform, BorderLayout.SOUTH);
        

/*        //... Assemble the menu
        menubar.add(fileMenu);
        fileMenu.add(openItem);
*/
        //... Set window characteristics
//        this.setJMenuBar(menubar);
        this.setContentPane(display);
        this.setTitle("File Snychronisation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();                      // Layout components.
        this.setLocationRelativeTo(null); // Center window.
    }


    ///////////////////////////////////////////////////// OpenAction
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

