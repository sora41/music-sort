package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import bandMaster.MusicFileBandMaster;

public class MainMp3 {

	private static final String DIRECTORY_IN = "Music\\inMusic";
	private static final String DIRECTORY_OUT = "Music\\outMusic";
	private static final String DIRECTORY_SORT = "Music\\sortedMusic";
	private static final String DIRECTORY_BACK = "Music\\back";

	private static MusicFileBandMaster musicSorter;
	private static TestCode test;

	private static Logger loggerMp3 = Logger.getLogger(MainMp3.class.getName());

	private static void initLog() throws SecurityException, FileNotFoundException, IOException {

		LogManager.getLogManager().readConfiguration(new FileInputStream("mp3logging.properties"));
		loggerMp3.setLevel(Level.ALL);
	}

	public static void initApplication() throws SecurityException, FileNotFoundException, IOException {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		test = new TestCode();
		initLog();
	}

	public static void runTest() {
		loggerMp3.log(Level.INFO, "lancement test");
		test.runTest();
	}

	public static void runProcces() throws IOException {

		loggerMp3.log(Level.INFO, "lancement reset");
		musicSorter.resetDirectories();
	
		loggerMp3.log(Level.INFO, "lancement initalisation");
		musicSorter.initDirectorieIn(DIRECTORY_BACK);
		
		loggerMp3.log(Level.INFO, "lancement tri");
		musicSorter.runSortFile();
		loggerMp3.log(Level.INFO, "fin du  tri");

	}

	public static void debugReset() throws IOException {

		loggerMp3.log(Level.INFO, "lancement reset");
		musicSorter.resetDirectories();
	}

	public static void main(String[] args) {

		try {
			initApplication();
			runProcces();
			// runTest();
		} catch (SecurityException | IOException e) {
			loggerMp3.log(Level.SEVERE, e.getClass() +e.getMessage());
			
		}

		loggerMp3.log(Level.INFO, "fin de l'application ");
	}
}
