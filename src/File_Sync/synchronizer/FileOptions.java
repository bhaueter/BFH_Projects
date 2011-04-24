package File_Sync.synchronizer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.log4j.Logger;


import File_Sync.gui.Gui;
import File_Sync.log4j.Log4j;

public class FileOptions extends Thread {
	
	private Log4j log;
	public static Logger logger = Logger.getRootLogger();

	private File rootfromPath, roottoPath, rootfromFile, roottoName;
	Gui gui;
	
	long sizeMB = 0;
	static int  sizeIntMB = 0;
	
	
	public FileOptions(File fromPath, File fromFilename, File toPath, File toFilename, Gui g) 
	{
		
		log = new Log4j(); 
		logger = log.logger;
		
		rootfromPath  = fromPath;
		rootfromFile  = fromFilename;
		roottoPath    = toPath;
		roottoName    = toFilename;
		
		this.gui = g;
		
	}
	public void run()
	{
	    System.out.println("Run!!!");
	    
	    
	    syncDirectory(rootfromFile, roottoName);
	    
	}
	
	public void syncDirectory(File fromFilename, File toFilename) {
		int len;
		String filePath = toFilename.getAbsolutePath();
		File[] fileList;
		// Directory
		if (isDirectory(fromFilename) == true) {
			if (isExist(toFilename) == true) {
				// do nothing
			} else {
				createDirectory(toFilename);
				
				logger.info("Directory created " + toFilename.getPath());
			}
		}
		// File
		else {

			if (isExist(toFilename) == true) {
				if (isSameSize(fromFilename, toFilename) == true) {
					// do nothing
				} else {
					// delete existing

					// copy
				}
			} else {
				
				
				try {
					
					//Update ProgressBar
					sizeMB = getSize(fromFilename) / 1048576;    //1024 ^2
					setFileSizeMB((int) sizeMB);
					gui.setMaxProgressBar(100);//((int)sizeMB);
				
					
					copyFile(fromFilename, toFilename, this.gui);
					
					logger.info("Copy File " + fromFilename.getName() + " to " + toFilename.getPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		fileList = fromFilename.listFiles();
		if (fileList != null) {
			len = fileList.length;
			if (len > 0) {
				for (int i = 0; i < len; i++) {
				
					if(i > 0)filePath = toFilename.getParent();
					
					fromFilename = fileList[i];
					toFilename = fromFilename;
	
					filePath = filePath + "/" + fromFilename.getName();

					toFilename = new File(filePath);

					
					System.out.println("fileList[i] "+ i + " : "+ fileList[i]);
					
					FileOptions sync = new FileOptions(fileList[i], fileList[i], toFilename, toFilename, this.gui);
					sync.start();
					
					System.out.println("fileList[i] "+ i + " - Finish : "+ fileList[i]);
					
//					syncDirectory(fileList[i], toFilename);
				}
			}
		}

		
	}
	private void setFileSizeMB(int size){
		this.sizeIntMB = size;
	}
	private static int getFileSizeMB(){
		return sizeIntMB;
	}
	

	public long getSize(File f) {
		long len = f.length();

		return len;
	}

	public boolean isSameSize(File f1, File f2) {
		if (getSize(f1) == getSize(f2))
			return true;
		else
			return false;
	}

	/*
	 * Method: isFile true, wenn es sich um eine »normale« Datei handelt (kein
	 * Verzeichnis und keine Datei, die vom zugrunde liegenden Betriebssystem
	 * als besonders markiert wird; Blockdateien, Links unter Unix). Es kann gut
	 * sein, dass für eine File weder isDirectory() noch isFile() die Rückgabe
	 * true liefern. In Java können nur normale Dateien erzeugt werden.
	 */
	public boolean isFile(File f) {
		try {
			// Check if its a directory and if it exists
			boolean success = (f.isFile());  // && f.exists()
			if (success)
				return true;
			else
				return false;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Method: isDirectory
	 */
	public boolean isDirectory(File f) {
		try {
			// Check if its a directory and if it exists
			boolean success = (f.isDirectory());  // && f.exists()
			if (success)
				return true;
			else
				return false;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Method: isExist path is optionally
	 */
	public boolean isExist(File f) {
		try {
			// Check if its exists
			boolean success = (f.exists());
			if (success)
				return true;
			else
				return false;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Method: createDirectory path is optionally
	 */
	public void createDirectory(File f) {
		try {
			// Create one directory
			boolean success = f.mkdir();
			if (success) {
				System.out.println("Directory: " + f.getPath() + " created");
			}
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	/*
	 * Method: createDirectories path is optionally
	 */
	public void createDirectories(File f) {
		try {
			// Create multiple directories
			boolean success = f.mkdirs();
			if (success) {
				System.out.println("Directories: " + f.getPath() + " created");
			}
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	static void copy(InputStream fis, OutputStream fos, Gui g) {
		try {
			byte[] buffer = new byte[1048576];  // 1MB
			int bytesRead = 0, readPercent = 0, current = 0;
						
			while ((bytesRead = fis.read(buffer)) != -1){
				fos.write(buffer, 0, bytesRead);
				current++;
				readPercent = (current / getFileSizeMB())*100;
				g.updateProgressBar(readPercent);
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	static void copyFile(File src, File dest, Gui g) throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;
		Byte[] len ;
				
		try {
			
			
			copy(new FileInputStream(src), new FileOutputStream(dest), g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public String getLogFile( String logFile ) throws IOException
//	  {
//		String output = "";
//		Reader reader = null;
//		
//		try
//		{
//		  reader = new FileReader( logFile );
//
//		  for ( int c; ( c = reader.read() ) != -1; )
//		    output = output + Character.toString((char) c);;
//		}
//		catch ( IOException e ) {
//		  System.err.println( "Fehler beim Lesen der Datei!" );
//		}
//		finally {
//		  try { reader.close(); } catch ( Exception e ) { }
//		}
//		return output;
//	  }

	



	public File[] getsubDirectories(File dir) {
		File[] subDirs = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File d) {
				return d.isDirectory();
			}
		});

		System.out.println(Arrays.asList(subDirs));
		return subDirs;

	}

}