package File_Sync;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;


public class startup {
	public static void main(String[] args) {
		
    JFrame window = new Gui();
    window.setSize(500, 400);
    window.setVisible(true);
        
    //Test fork merge ssp!
        
	} 
        
      public static void sync(File fromPath, File fromFilename, File toPath, File toFilename) throws IOException {
    
    	  FileOptions sync = new FileOptions(fromPath, fromFilename, toPath, toFilename);
    	  
    	  sync.syncDirectory(fromFilename, toFilename);
    	  
      }

        
 
        
       // if(fromFilename != null && getToFilename() )
     
}
