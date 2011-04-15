package src.File_Sync;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import src.File_Sync.Gui_old;
import src.File_Sync.FileOptions_old;


public class startup_old {

	public static void main(String[] args) {
	
	//	Color white;
		
		
    JFrame window = new Gui_old();
    
    //window.setBackground(white);
    window.setSize(500,500);
    window.setVisible(true);
        

        
	} 
        
      public static void sync(File fromPath, File fromFilename, File toPath, File toFilename) throws IOException {
    
    	  FileOptions_old sync = new FileOptions_old(fromPath, fromFilename, toPath, toFilename);
    	  
    	  sync.syncDirectory(fromFilename, toFilename);
    	  
      }
     
}
