package File_Sync;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import File_Sync.gui.Gui;
import File_Sync.log4j.Log4j;
import File_Sync.synchronizer.FileOptions;
import File_Sync.synchronizer.SyncFile;

public class StartUp {
	
	public static void main(String[] args) {

		// Color white;

		Log4j log = new Log4j();

		JFrame window = new Gui(log);

		// window.setBackground(white);
		window.setSize(800, 500);
		window.setVisible(true);

	}

	public static void sync(File fromPath, File fromFilename, File toPath,
			File toFilename, Gui g) throws IOException {

		FileOptions tp = new FileOptions( g );
				
		tp.runTask(new SyncFile(fromPath, fromFilename, toPath,toFilename, g, tp ));

	}

	// if(fromFilename != null && getToFilename() )

}
