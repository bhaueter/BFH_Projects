
package src.File_Sync.test;


import java.io.File;
import java.io.IOException;



import src.File_Sync.log4j.Log4j;
import src.File_Sync.synchronizer.FileOptions;


public class TestReadLogFile {

	
	

	public static void main(String[] args) 
	{
		

		
		File fromFilename = new File("/Users/sspaeti/Desktop/Sync1/1.txt");
		File toFilename = new File("/Users/sspaeti/Desktop/Sync2");

		File fromPath = new File("/Users/sspaeti/Desktop/Sync1/1.txt");
		File toPath = new File("/Users/sspaeti/Desktop/Sync2");		
		
  	  	FileOptions sync = new FileOptions(fromPath, fromFilename, toPath, toFilename);
  	  	
  	  	
  	  	
  
////		logger = log.logger;
//
//  	  	try {
//  	  		
////			sync.getLogFile("/Users/sspaeti/Desktop/Sync1/1.txt");
//  	  		
//  	  	//sync.getLogFile("/Users/sspaeti/Simon/Business/09_BFH/02_FŠcher/7051_Java/3_Java_Workspace/BFH_2_Semester_Bšru_git/File_Sync5/src/File_Sync/logs/MeineLogDatei.log");
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	  
	
		
		
	}
}
