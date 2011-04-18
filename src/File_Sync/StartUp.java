
package src.File_Sync;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import src.File_Sync.gui.Gui;
import src.File_Sync.log4j.Log4j;
import src.File_Sync.synchronizer.FileOptions;


public class StartUp {

	public static void main(String[] args) {
	
	//	Color white;
		
	Log4j log = new Log4j();
	
    JFrame window = new Gui(log);
    

    
    //window.setBackground(white);
    window.setSize(500,500);
    window.setVisible(true);
        
        
	} 
        
      public static void sync(File fromPath, File fromFilename, File toPath, File toFilename) throws IOException {
    
    	  FileOptions sync = new FileOptions(fromPath, fromFilename, toPath, toFilename);
    	  
    	  sync.syncDirectory(fromFilename, toFilename);
    	  
      }

        
 
        
       // if(fromFilename != null && getToFilename() )
     
}

