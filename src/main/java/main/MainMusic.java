package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import bandMaster.MusicFileBandMaster;

public class MainMusic {

	/**
	 * the in music file directory
	 */
	private static final String DIRECTORY_IN = "Music\\inMusic";
	/**
	 * the out music file directory
	 */
	private static final String DIRECTORY_OUT = "Music\\outMusic";
	/**
	 * the sorted music file directory
	 */
	private static final String DIRECTORY_SORT = "Music\\sortedMusic";
	/**
	 * the back music file directory use on test to provide the in music file
	 */
	private static final String DIRECTORY_BACK = "Music\\back";
	/**
	 * the music Sorter
	 */
	private static MusicFileBandMaster musicSorter;
	/**
	 * the application tester object deprecied (unit test incomming )
	 */
	private static TestCode test;
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MainMusic.class.getName());

	/**
	 * init application sorter et les tests
	 */
	public static void initApplication() throws SecurityException, FileNotFoundException, IOException {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		test = new TestCode();
	}

	/** launch manual test deprecied (unit test incomming ) 
	 * @throws Exception */
	public static void runTest() throws Exception {
		LOGGER4J.info("demarage des tests");
		test.runTest();
		LOGGER4J.info("fin des tests");
	}

	/**
	 * init the directory in the function move all files in back directory to
	 * inMusic directory
	 */
	public static void initDirectory() throws IOException {
		LOGGER4J.info("demarage sequence initalisation ");
		musicSorter.initDirectorieIn(DIRECTORY_BACK);
		LOGGER4J.info("Fin initialisation ");
	}

	/**
	 * call sort functions
	 */
	public static void launchSort() throws IOException {
		LOGGER4J.info("demarage du tri");
		musicSorter.runSortFile();
		LOGGER4J.info("fin du tri");
	}

	/**
	 * clean directory use by application
	 */
	public static void ResetDirectory() throws IOException {
		LOGGER4J.info("demarage Reset");
		musicSorter.resetDirectories();
		LOGGER4J.info("fin Reset");
	}

	/**
	 * launch the sort operations
	 */
	public static void runProcces() throws IOException {
		ResetDirectory();
		initDirectory();
		launchSort();
	}

	/** Main */
	public static void main(String[] args) {
		LOGGER4J.info("demarage de l'application");
		try {
			initApplication();
			//runProcces();
			runTest();
		} catch (Exception e) {

			LOGGER4J.fatal("l'application c'est arrete de maniere inatendu ", e.getClass(), e.getMessage(),
					e.getStackTrace());
			LOGGER4J.fatal("message :" + e.getMessage());
			LOGGER4J.fatal("Eclass :" + e.getClass().getName());

		}
		LOGGER4J.info("fin de l'application ");
	}
}
