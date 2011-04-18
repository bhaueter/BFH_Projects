package File_Sync.log4j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.Timer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


public class Log4j implements ActionListener{
	
	public static Logger logger = Logger.getRootLogger();
	
	private String logFilePath = "logs/MeineLogDatei.log";
	//Timer
	private Timer t;
	private boolean isStopped = false;
	
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
	    //Start Timer for refresh Logfile on Gui
		t = new Timer(500, this);
		startWatchLogFile();
	}
	
	public String getLogFilePath()
	{
		return logFilePath;
	}
	
	
	public String getLogFile() throws IOException
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
		  System.err.println( "Fehler beim Lesen der Datei!" );
		}
		finally {
		  try { reader.close(); } catch ( Exception e ) { }
		}
		return output;
	  }
	
	public void startWatchLogFile()
	{
		this.t.start();
		while(isStopped)
		{
			t.stop();
		}
	}
	public void stopWatchLogFile()
	{
		isStopped = false;
	}	
	
	public void actionPerformed(ActionEvent e) {
//		System.out.println(c++);
		try {
			System.out.println(getLogFile());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
	
	    
//	    logger.debug( "Meine Debug-Meldung" );
//	    logger.info(  "Meine Info-Meldung"  );
//	    logger.warn(  "Meine Warn-Meldung"  );
//	    logger.error( "Meine Error-Meldung" );
//	    logger.fatal( "Meine Fatal-Meldung" );
	  
}
