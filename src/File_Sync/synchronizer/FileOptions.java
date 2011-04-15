package src.File_Sync.synchronizer;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class FileOptions {
	
	public FileOptions(File fromPath, File fromFilename, File toPath, File toFilename) {
		File rootfromPath = fromPath;
		File roottoPath = toPath;
		File rootfromFile = fromFilename;
		File roottoName = toFilename;
	}

	public void  syncDirectory(File fromFilename, File toFilename){
		int len;
		String filePath = toFilename.getAbsolutePath();
		File[] fileList, dirList;
		// Directory
		if(isDirectory(fromFilename) == true && isDirectory(toFilename) == true){
			
			if(isExist(toFilename) == true){
				//do nothing
			}
			else{
				createDirectory(toFilename);
			}
		}
		// File
		else{
			
			if(isExist(toFilename) == true){
				if(isSameSize(fromFilename, toFilename)== true){
					//do nothing
				}
				else{
					//delete existing
					
					//copy
				}
			}
			else{
				try {
					copyFile(fromFilename, toFilename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}

		fileList = fromFilename.listFiles();
		len = fileList.length;
		if(len > 0){
		for(int i = 0; i<len; i++){
			fromFilename = fileList[i];
			toFilename = fromFilename;
			filePath = filePath +"\\"+ fromFilename.getName();
			
			toFilename = new File(filePath);
			
			syncDirectory(fileList[i],toFilename);
		}
		}
		
		
		
		
		
	}
	
	
	public long getSize(File f)
	{
		 long len = f.length();
		 
		 return len;
	}

	public boolean isSameSize(File f1, File f2)
	{
		if (getSize(f1) == getSize(f2))
			return true;
		else
			return false;
	}
	
	/*
	 * Method: isFile
	 * true, wenn es sich um eine »normale« Datei handelt (kein Verzeichnis und keine Datei, 
	 * die vom zugrunde liegenden Betriebssystem als besonders markiert wird; Blockdateien, 
	 * Links unter Unix). Es kann gut sein, dass für eine File weder isDirectory() noch isFile() 
	 * die Rückgabe true liefern. In Java können nur normale Dateien erzeugt werden.
	 */
	public boolean isFile (File f)
	{
	      try
	      {
	    	  	//Check if its a directory and if it exists
	    	    boolean success = (f.isFile() && f.exists());
	    	    if (success)
	    	    	return true;
	    	    else
	    	    	return false;
	      }
	      catch 
	      (
	    		Exception e){//Catch exception if any
	    	  	System.err.println("Error: " + e.getMessage());
	    	  	return false;
	      }
	}	
	
	/*
	 * Method: isDirectory
	 */
	public boolean isDirectory (File f)	
	{
	      try
	      {
	    	  	//Check if its a directory and if it exists
	    	    boolean success = (f.isDirectory() && f.exists());
	    	    if (success)
	    	    	return true;
	    	    else
	    	    	return false;
	      }
	      catch 
	      (
	    		Exception e){//Catch exception if any
	    	  	System.err.println("Error: " + e.getMessage());
	    	  	return false;
	      }
	}
	
	/*
	 * Method: isExist
	 * path is optionally
	 */
	public boolean isExist(File f)	
	{		
	      try
	      { 
	    	  	//Check if its exists
	    	    boolean success = (f.exists());
	    	    if (success)
	    	    	return true;
	    	    else
	    	    	return false;
	      }
	      catch 
	      (
	    		Exception e){//Catch exception if any
	    	  	System.err.println("Error: " + e.getMessage());
	    	  	return false;
	      }
	}	
	
	/*
	 * Method: createDirectory
	 * path is optionally
	 */
	public void createDirectory(File f)	
	{
	      try
	      {
	    	    // Create one directory
	    	    boolean success = f.mkdir();
	    	    if (success) 
	    	    {
	    	      System.out.println("Directory: " + f.getPath() + " created");
	    	    }    
	      }
	      catch 
	      (
	    		Exception e){//Catch exception if any
	    	  	System.err.println("Error: " + e.getMessage());
	      }
	}
	/*
	 * Method: createDirectories
	 * path is optionally
	 */
	public void createDirectories(File f)
	{
	      try
	      {
	    	    // Create multiple directories
	    	    boolean success = f.mkdirs();
	    	    if (success) 
	    	    {
	    	      System.out.println("Directories: " + f.getPath() + " created");
	    	    }
	      }
	      catch 
	      (
	    		Exception e){//Catch exception if any
	    	  	System.err.println("Error: " + e.getMessage());
	      }
	}	
	
	
	static void copy( InputStream fis, OutputStream fos )
	  {
	    try
	    {
	      byte[] buffer = new byte[ 0xFFFF ];
	      for ( int len; (len = fis.read(buffer)) != -1; )
	        fos.write( buffer, 0, len );
	    }
	    catch( IOException e ) {
	      System.err.println( e );
	    }
	    finally {
	      if ( fis != null )
	        try { fis.close(); } catch ( IOException e ) { e.printStackTrace(); }
	      if ( fos != null )
	        try { fos.close(); } catch ( IOException e ) { e.printStackTrace(); }
	    }
	  }
	  static void copyFile( File src, File dest ) throws IOException
	  {
		  FileInputStream in = null;
		  FileOutputStream out = null;
		  	  	  
	    try
	    {
	      copy( new FileInputStream( src ), new FileOutputStream( dest ) );
	    }
	    catch( IOException e ) {
	      e.printStackTrace();
	    }
	  }
	  
	  /*
	  public static void main( String[] args )
	  {
	    if ( args.length != 2 )
	      System.err.println( "Usage: java FileCopy <src> <dest>" );
	    else
	      copyFile( args[0], args[1] );
	  }
	  */
	  
	  
	  public File[] getsubDirectories(File dir)
	  {
		    File[] subDirs = dir.listFiles( new FileFilter() {
		      @Override public boolean accept( File d ) {
		        return d.isDirectory();
		      } } );
		    
		    System.out.println( Arrays.asList( subDirs ) );
		    return subDirs;
		    
	  }

	
	
}