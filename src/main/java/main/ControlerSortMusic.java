package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bandMaster.MusicFileBandMaster;

public class ControlerSortMusic {

	/**
	 * the in music file directory
	 */
	private String DIRECTORY_IN = "Music\\inMusic";
	/**
	 * the out music file directory
	 */
	private String DIRECTORY_OUT = "Music\\outMusic";
	/**
	 * the sorted music file directory
	 */
	private String DIRECTORY_SORT = "Music\\sortedMusic";
	/**
	 * the back music file directory use on test to provide the in music file
	 */
	private String DIRECTORY_BACK = "Music\\back";
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

	public String getDIRECTORY_IN() {
		return DIRECTORY_IN;
	}

	public void setDIRECTORY_IN(String dIRECTORY_IN) {
		DIRECTORY_IN = dIRECTORY_IN;
	}

	public String getDIRECTORY_OUT() {
		return DIRECTORY_OUT;
	}

	public void setDIRECTORY_OUT(String dIRECTORY_OUT) {
		DIRECTORY_OUT = dIRECTORY_OUT;
	}

	public String getDIRECTORY_SORT() {
		return DIRECTORY_SORT;
	}

	public void setDIRECTORY_SORT(String dIRECTORY_SORT) {
		DIRECTORY_SORT = dIRECTORY_SORT;
	}

	public String getDIRECTORY_BACK() {
		return DIRECTORY_BACK;
	}

	public void setDIRECTORY_BACK(String dIRECTORY_BACK) {
		DIRECTORY_BACK = dIRECTORY_BACK;
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
