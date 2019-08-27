package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bandmaster.MusicFileBandMaster;

public class ControlerSortMusic {

	/**
	 * the in music file directory
	 */
	private final String DIRECTORY_IN = "Music\\inMusic";
	/**
	 * the out music file directory
	 */
	private final String DIRECTORY_OUT = "Music\\outMusic";
	/**
	 * the sorted music file directory
	 */
	private final String DIRECTORY_SORT = "Music\\sortedMusic";
	/**
	 * the back music file directory use on test to provide the in music file
	 */
	private final String DIRECTORY_BACK = "Music\\back";
	/**
	 * the music Sorter
	 */
	private MusicFileBandMaster musicSorter;
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(ControlerSortMusic.class.getName());

	/**
	 * init application sorter et les tests
	 */
	public void initApplication() throws SecurityException, FileNotFoundException, IOException {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
	}
	
	public MusicFileBandMaster getMusicSorter() {
		return musicSorter;
	}
	

	/**
	 * init the directory in the function move all files in back directory to
	 * inMusic directory
	 */
	public void initDirectory() throws IOException {
		LOGGER4J.info("demarage sequence initalisation ");
		musicSorter.initDirectoryIn(DIRECTORY_BACK);
		LOGGER4J.info("Fin initialisation ");
	}

	/**
	 * call sort functions
	 */
	public void launchSort() throws IOException {
		LOGGER4J.info("demarage du tri");
		musicSorter.runSortFile();
		LOGGER4J.info("fin du tri");
	}

	/**
	 * clean directory use by application
	 */
	public void resetDirectory() throws IOException {
		LOGGER4J.info("demarage Reset");
		musicSorter.resetDirectories();
		LOGGER4J.info("fin Reset");
	}
}
