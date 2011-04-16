package src.File_Sync.log4j;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class Log4j {
	
	public static Logger logger = Logger.getRootLogger();
	
	private String logFilePath = "File_Sync5/src/File_Sync/logs/MeineLogDatei.log";
	
	public Log4j()
	{
	    try {
	      SimpleLayout layout = new SimpleLayout();
	      ConsoleAppender consoleAppender = new ConsoleAppender( layout );
	      logger.addAppender( consoleAppender );
	      FileAppender fileAppender = new FileAppender( layout, logFilePath, false );
	      logger.addAppender( fileAppender );
	      
	      
	      //Login LEVEL	    
	      logger.setLevel( Level.ALL );			// ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
	    } 
	    catch( Exception ex ) 
	    {
	    	System.out.println( ex );
	    }
	}
	
	public String getLogFilePath()
	{
		return logFilePath;
	}
	
	
	    
//	    logger.debug( "Meine Debug-Meldung" );
//	    logger.info(  "Meine Info-Meldung"  );
//	    logger.warn(  "Meine Warn-Meldung"  );
//	    logger.error( "Meine Error-Meldung" );
//	    logger.fatal( "Meine Fatal-Meldung" );
	  
}
