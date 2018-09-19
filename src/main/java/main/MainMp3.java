package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bandMaster.MusicFileBandMaster;

public class MainMp3 {

	private static final String DIRECTORY_IN = "Music\\inMusic";
	private static final String DIRECTORY_OUT = "Music\\outMusic";
	private static final String DIRECTORY_SORT = "Music\\sortedMusic";
	private static final String DIRECTORY_BACK = "Music\\back";

	private static MusicFileBandMaster musicSorter;
	private static TestCode test;

	private static final Logger LOGGER4J = LogManager.getLogger(MainMp3.class.getName());

	public static void initApplication() throws SecurityException, FileNotFoundException, IOException {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		test = new TestCode();

	}

	public static void runTest() {
		LOGGER4J.info("demarage des tests");
		test.runTest();
		LOGGER4J.info("fin des tests");
	}

	public static void runProcces() throws IOException {

		LOGGER4J.info("demarage Reset");
		musicSorter.resetDirectories();
		LOGGER4J.info("fin Reset");

		LOGGER4J.info("demarage sequence initalisation ");
		musicSorter.initDirectorieIn(DIRECTORY_BACK);
		LOGGER4J.info("Fin initialisation ");

		LOGGER4J.info("demarage du tri");
		musicSorter.runSortFile();
		LOGGER4J.info("fin du tri");

	}

	public static void debugReset() throws IOException {

		musicSorter.resetDirectories();
	}

	public static void main(String[] args) {
		LOGGER4J.info("demarage de l'application");
		try {
			initApplication();
			runProcces();
			// runTest();
		} catch (SecurityException | IOException e) {

			LOGGER4J.fatal("l'application c'est arrete de maniere inatendu ", e.getClass(), e.getMessage(),
					e.getStackTrace());

		}
		LOGGER4J.info("fin de l'application ");
	}
}
