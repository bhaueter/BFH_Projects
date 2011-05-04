
package src.File_Sync.log4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


public class Log4j {
	
	public static Logger logger = Logger.getRootLogger();
	

	private String logFilePath = "logs/MeineLogDatei.log";
	
	/*
	 * Log4j: Standard config and logging everything
	 * @param1: truncate: true = Logfile vorgängig nicht löschen, false: neues Logfile erstellen
	 */
	public Log4j(boolean truncate)
	{
	    try {
	      SimpleLayout layout = new SimpleLayout();
	      ConsoleAppender consoleAppender = new ConsoleAppender( layout );
	      logger.addAppender( consoleAppender );
	      FileAppender fileAppender = new FileAppender( layout, logFilePath, truncate );
	      logger.addAppender( fileAppender );	      
	      
	      //Login LEVEL	    
	      logger.setLevel( Level.ALL );			// ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
	    } 
	    catch( Exception ex ) 
	    {
	    	System.out.println( ex );
	    }
	}

	
	/*
	 * Gives the Path from Workspace to the LogFile
	 */
	public String getLogFilePath()
	{
		return logFilePath;
	}
	
	/*
	 * Gives Data of the Logfile as a String
	 */
	public String getStringLogFile() throws IOException
	  {
		String output = "";
		Reader reader = null;
		
		try
		{
		  reader = new FileReader( this.logFilePath );

		  for ( int c; ( c = reader.read() ) != -1; )
		    output = output + Character.toString((char) c);;
		}
		catch ( IOException e ) {
		  logger.error( "Log4J(): Fehler beim Lesen der Datei!" );
		}
		finally {
		  try { reader.close(); } catch ( Exception e ) { }
		}
		return output;
	  }
	
	/*
	 * Gives the LogFile as Typ of File()
	 */
	public File getLogFile()
	{
		return new File( logFilePath );
	}
		

	    
//	    logger.debug( "Meine Debug-Meldung" );
//	    logger.info(  "Meine Info-Meldung"  );
//	    logger.warn(  "Meine Warn-Meldung"  );
//	    logger.error( "Meine Error-Meldung" );
//	    logger.fatal( "Meine Fatal-Meldung" );
	  
}
