package src.File_Sync;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import src.File_Sync.exceptions.FileOptionEx;
import src.File_Sync.gui.Gui;
import src.File_Sync.log4j.Log4j;
import src.File_Sync.synchronizer.FileOptions;
import src.File_Sync.synchronizer.SyncFile;

public class StartUp {
	
	public static void main(String[] args) {

		// Color white;

		Log4j log = new Log4j(false);

		JFrame window = new Gui(log);

		// window.setBackground(white);
		window.setSize(800, 500);
		window.setVisible(true);

	}

	public static void sync(File fromPath, File fromFilename, File toPath,
			File toFilename, Gui g) throws FileOptionEx  {

		FileOptions tp = new FileOptions( g );
				
		tp.runTask(new SyncFile(fromPath, fromFilename, toPath,toFilename, g, tp ));

	}

	// if(fromFilename != null && getToFilename() )

}
