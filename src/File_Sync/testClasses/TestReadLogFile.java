package src.File_Sync.testClasses;

import java.io.File;
import java.io.IOException;

import src.File_Sync.exceptions.FileOptionEx;
import src.File_Sync.gui.Gui;
import src.File_Sync.log4j.Log4j;
import src.File_Sync.synchronizer.FileOptions;
import src.File_Sync.synchronizer.SyncFile;

public class TestReadLogFile {

	
	

	public static void main(String[] args) 
	{

		//static paths
		File fromFilename = new File("/Users/sspaeti/Desktop/Sync1/1.txt");
		File toFilename = new File("/Users/sspaeti/Desktop/Sync2");

		File fromPath = new File("/Users/sspaeti/Desktop/Sync1/1.txt");
		File toPath = new File("/Users/sspaeti/Desktop/Sync2");		
		
		Log4j log = new Log4j(true);
		
		
		//sysem output from the LogFile!
		try {
			System.out.println(log.getStringLogFile());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.exit(0);
		
	}
}
