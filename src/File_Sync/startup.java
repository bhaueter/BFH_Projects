package src.File_Sync;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import src.File_Sync.gui.Gui;
import src.File_Sync.synchronizer.FileOptions;


public class StartUp {
	public static void main(String[] args) {
		
    JFrame window = new Gui();
    window.setSize(500, 400);
    window.setVisible(true);
        

        
	} 
        
      public static void sync(File fromPath, File fromFilename, File toPath, File toFilename) throws IOException {
    
    	  FileOptions sync = new FileOptions(fromPath, fromFilename, toPath, toFilename);
    	  
    	  sync.syncDirectory(fromFilename, toFilename);
    	  
      }
     
}
