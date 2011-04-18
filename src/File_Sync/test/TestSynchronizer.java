
package src.File_Sync.test;

import java.io.File;

import src.File_Sync.synchronizer.FileOptions;


public class TestSynchronizer {

	public static void main(String[] args) 
	{
	
		File fromFilename = new File("/Users/sspaeti/Desktop/Sync1");
		File toFilename = new File("/Users/sspaeti/Desktop/Sync2");

		File fromPath = new File("/Users/sspaeti/Desktop/Sync1");
		File toPath = new File("/Users/sspaeti/Desktop/Sync2");		
		
  	  	FileOptions sync = new FileOptions(fromPath, fromFilename, toPath, toFilename);
  	  	sync.syncDirectory(fromFilename, toFilename);
	  
	
	
	}
}
