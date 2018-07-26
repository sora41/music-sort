package main;

import java.io.FileInputStream;
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

	private static void initLog() {

		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("mp3logging.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}

		loggerMp3.setLevel(Level.WARNING);
	}

	public static void initApplication() {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		test = new TestCode();
		initLog();
	}

	public static void runTest() {

		test.runTest();
	}

	public static void runProcces() {
		//System.out.println("lancement reset");
		loggerMp3.log(Level.INFO, "lancement reset");
		musicSorter.resetDirectories();
		//System.out.println("lancement initalisation");
		loggerMp3.log(Level.INFO, "lancement initalisation");
		musicSorter.initDirectorieIn(DIRECTORY_BACK);
		//System.out.println("lancement tri");
		loggerMp3.log(Level.INFO, "lancement tri");
		musicSorter.runSortFile();
	}

	public static void main(String[] args) {

		initApplication();
		runProcces();
		// runTest();
	}
}
