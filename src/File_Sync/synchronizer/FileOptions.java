package File_Sync.synchronizer;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import File_Sync.gui.Gui;

public class FileOptions  {
	
	
    int poolSize = 2;
    int maxPoolSize = 2;
    long keepAliveTime = 10;
    ThreadPoolExecutor  threadPool = null;

   final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);
	
	
	
	public static Logger log;

	private File rootfromPath, roottoPath, rootfromFile, roottoName;
	private static int cntFile = 0;
	Gui gui;

	long sizeMB = 0;
	static int sizeIntMB = 0, actualMB = 0;

	public FileOptions(Gui g) {
		
		threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize, 
							keepAliveTime, TimeUnit.SECONDS, queue);
		log = g.logger; 
		this.gui = g;
	}

	public void runTask(Runnable task) {
		if(this.gui.getIsStopped() == true) stopTask();
		else{
	    threadPool.execute(task);
		System.out.println("Task count.." + queue.size());
		}
	}
	
	public void stopTask(){
		threadPool.shutdown();
	}



}