package src.File_Sync.testClasses;

import java.io.File;

import javax.swing.JFrame;

import src.File_Sync.exceptions.FileOptionEx;
import src.File_Sync.gui.Gui;
import src.File_Sync.log4j.Log4j;
import src.File_Sync.synchronizer.FileOptions;
import src.File_Sync.synchronizer.SyncFile;



public class TestSynchronizer {

	public static void main(String[] args) 
	{
		//Static paths
		File fromFilename = new File("/Users/sspaeti/Desktop/Sync1");
		File toFilename = new File("/Users/sspaeti/Desktop/Sync2");

		File fromPath = new File("/Users/sspaeti/Desktop/Sync1");
		File toPath = new File("/Users/sspaeti/Desktop/Sync2");		

  	  		  	
		Log4j log = new Log4j(false);
		
		//Test Gui without showing the Gui!
		Gui g = new Gui(log);



		FileOptions tp = new FileOptions( g );		
		try {
			tp.runTask(new SyncFile(fromPath, fromFilename, toPath,toFilename, g, tp ));
		} catch (FileOptionEx e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	
	}
}
