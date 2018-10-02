package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bandMaster.MusicFileBandMaster;

public class MainMp3 {

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
	 * the application tester deprecied (unit test incomming )
	 */
	private static TestCode test;
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MainMp3.class.getName());
	

    /**
     * init application sorter 
     */
	public static void initApplication() throws SecurityException, FileNotFoundException, IOException {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		test = new TestCode();

	}

	/* lance les test developpe manuellement pour le moment */
	public static void runTest() {
		LOGGER4J.info("demarage des tests");
		test.runTest();
		LOGGER4J.info("fin des tests");
	}

	/*
	 * initalise le repertoire in la fonction deplace tout les fichier contenu
	 * dans le repertoire back dans le repertoire in
	 */
	public static void initDirectory() throws IOException {
		LOGGER4J.info("demarage sequence initalisation ");
		musicSorter.initDirectorieIn(DIRECTORY_BACK);
		LOGGER4J.info("Fin initialisation ");
	}

	/*
	 * appel la fonction de tri
	 */
	public static void launchSort() throws IOException {
		LOGGER4J.info("demarage du tri");
		musicSorter.runSortFile();
		LOGGER4J.info("fin du tri");
	}

	/*
	 * vide les repetoire utiliser par l'application
	 */
	public static void ResetDirectory() throws IOException {
		LOGGER4J.info("demarage Reset");
		musicSorter.resetDirectories();
		LOGGER4J.info("fin Reset");
	}

	public static void runProcces() throws IOException {
		ResetDirectory();
		initDirectory();
		launchSort();
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
			LOGGER4J.fatal("message :" + e.getMessage());
			LOGGER4J.fatal("Eclass :" + e.getClass().getName());

		}
		LOGGER4J.info("fin de l'application ");
	}
}
