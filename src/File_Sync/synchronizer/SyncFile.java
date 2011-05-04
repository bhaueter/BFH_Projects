package src.File_Sync.synchronizer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import src.File_Sync.exceptions.CloseFileEx;
import src.File_Sync.exceptions.CopyFileEx;
import src.File_Sync.exceptions.FileOptionEx;
import src.File_Sync.exceptions.InvalidInputOutputStreamEx;
import src.File_Sync.exceptions.TestExceptionEx;
import src.File_Sync.gui.Gui;

public class SyncFile implements Runnable {

	public static Logger log;
	private File rootfromPath, roottoPath, rootfromFile, roottoName;
	private static int cntFile = 0;
	Gui gui;
	FileOptions threadPool;
	long sizeMB = 0;
	static int sizeIntMB = 0, actualMB = 0;

	public SyncFile(File fromPath, File fromFilename, File toPath,
			File toFilename, Gui g, FileOptions tp) {

		log = g.logger;
		threadPool = tp;
		rootfromPath = fromPath;
		rootfromFile = fromFilename;
		roottoPath = toPath;
		roottoName = toFilename;
		this.gui = g;
	}
	
	public void run(){
		try {
			syncDirectory(rootfromFile, roottoName);
		} catch (FileOptionEx e) {
			log.error("Method run() in SyncFile " + e.getMessage());
		}
	}
	
	
	public void syncDirectory(File fromFilename, File toFilename) throws FileOptionEx {

		int len;
		String filePath = toFilename.getAbsolutePath();
		File[] fileList;
		// Directory
		if (isDirectory(fromFilename) == true) {
			if (isExist(toFilename) == true) {
				// do nothing
				
				//test if exception comes up
				//throw new TestExceptionEx();
				
			} else {
				createDirectory(toFilename);
				log.info("Directory created " + toFilename.getPath());
			}
		}
		// File
		else {

			if (isExist(toFilename) == true) {
				if (isSameSize(fromFilename, toFilename) == true) {
					// do nothing
				} else {
					try {
						// delete existing
						toFilename.delete();
						log.info("Directory must be deleted "
								+ toFilename.getPath());

						// copy

						// Update ProgressBar
						sizeMB = getSize(fromFilename) / 1048576; // 1024 ^2
						setFileSizeMB((int) sizeMB);
						gui.setMaxProgressBar(100);// ((int)sizeMB);

						setFileAmount();

						copyFile(fromFilename, toFilename, this.gui);

						log.info("File copied -");
						log.info("From: " + fromFilename.getPath());
						log.info("To:   " + toFilename.getPath());

					} catch (FileOptionEx e) {
						log.error("Error in Method copyFile(): File couldn't copied!");
						log.error("From: " + fromFilename.getPath());
						log.error("To:   " + toFilename.getPath());
					} 
					catch (IOException e) {
						log.error("Error: File couldn't copied in Method syncDirectory()!");
						log.error("From: " + fromFilename.getPath());
						log.error("To:   " + toFilename.getPath());
					}
				}
			} else {

				try {

					// Update ProgressBar
					sizeMB = getSize(fromFilename) / 1048576; // 1024 ^2
					setFileSizeMB((int) sizeMB);
					gui.setMaxProgressBar(100);// ((int)sizeMB);

					setFileAmount();

					copyFile(fromFilename, toFilename, this.gui);

					log.info("File copied -");
					log.info("From: " + fromFilename.getPath());
					log.info("To:   " + toFilename.getPath());

				} catch (FileOptionEx e) {
					log.error("Error in Method copyFile(): File couldn't copied!");
					log.error("From: " + fromFilename.getPath());
					log.error("To:   " + toFilename.getPath());
				} 
				catch (IOException e) {
					log.error("Error: File couldn't copied in Method syncDirectory()!");
					log.error("From: " + fromFilename.getPath());
					log.error("To:   " + toFilename.getPath());
				}
			}
		}

		fileList = fromFilename.listFiles();
		if (fileList != null) {
			len = fileList.length;
			if (len > 0) {
				for (int i = 0; i < len; i++) {

					if (i > 0)
						filePath = toFilename.getParent();

					fromFilename = fileList[i];
					toFilename = fromFilename;

					filePath = filePath + "/" + fromFilename.getName();

					toFilename = new File(filePath);

					threadPool.runTask(new SyncFile(fileList[i],fileList[i], toFilename, toFilename, this.gui, this.threadPool));

				}
			}
		}
	}

	private void setFileAmount() {
		cntFile++;
	}

	private static int getFileAmount() {
		return cntFile;
	}

	private static void setCopiedMB() {
		actualMB++;
	}

	private static int getCopiedMB() {
		return actualMB;
	}

	private void setFileSizeMB(int size) {
		this.sizeIntMB += size;
	}

	private static int getFileSizeMB() {
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
			boolean success = (f.isFile()); // && f.exists()
			if (success)
				return true;
			else
				return false;
		} catch (Exception e) {// Catch exception if any
			log.error("Method isFile " + e.getMessage());
			return false;
		}
	}

	/*
	 * Method: isDirectory
	 */
	public boolean isDirectory(File f) {
		try {
			// Check if its a directory and if it exists
			boolean success = (f.isDirectory()); // && f.exists()
			if (success)
				return true;
			else
				return false;
		} catch (Exception e) {// Catch exception if any
			log.error("Method isDirectory " + e.getMessage());
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
			log.error("Method isExist " + e.getMessage());
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
				log.info("Directory: " + f.getPath() + " created");
			}
		} catch (Exception e) {// Catch exception if any
			log.error("Method createDirectory " + e.getMessage());
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
				log.info("Directories: " + f.getPath() + " created");
			}
		} catch (Exception e) {// Catch exception if any
			log.error("Method createDirectories " + e.getMessage());
		}
	}

	static void copy(InputStream fis, OutputStream fos, Gui g)  throws FileOptionEx{
		try {
			byte[] buffer = new byte[1048576]; // 1MB
			int bytesRead = 0, readPercent = 0, actual = 0, current = 0;
			Float percent;

			while ((bytesRead = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);

				setCopiedMB();

				percent = (float) getCopiedMB() / getFileSizeMB();
				percent *= 100;
				readPercent = Math.round(percent);

				g.updateProgressBar(readPercent);
			}
		} catch (IOException e) {
			log.error("Couldn't copy File in Method copy() " + e.getMessage());
			throw new InvalidInputOutputStreamEx();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					log.error("Couldn't copy File in Method copy() - fis.close(): " + e.getMessage());
					throw new CloseFileEx();
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					log.error("Couldn't copy File in Method copy() - fos.close(): " + e.getMessage());
					throw new CloseFileEx();
				}
		}
	}

	static void copyFile(File src, File dest, Gui g) throws FileOptionEx, FileNotFoundException {
		FileInputStream in = null;
		FileOutputStream out = null;
		Byte[] len;

		try {
			copy(new FileInputStream(src), new FileOutputStream(dest), g);
		} catch (InvalidInputOutputStreamEx e) {
			log.error("Method copyFile " + e.getMessage());
			throw new CopyFileEx();
		}
	}

	public File[] getsubDirectories(File dir) {
		File[] subDirs = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File d) {
				return d.isDirectory();
			}
		});
		return subDirs;
	}

}
