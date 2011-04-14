package File_Sync;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;


public class startup {

	public static void main(String[] args) {
	
	//	Color white;
		
		
    JFrame window = new Gui();
    
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
