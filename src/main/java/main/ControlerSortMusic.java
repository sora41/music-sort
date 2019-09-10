package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bandmaster.MusicFileBandMaster;
import constant.MusicExtention;

public class ControlerSortMusic {

	/**
	 * the in music file directory
	 */
	private String directoryIn = "Music\\inMusic";
	/**
	 * the out music file directory
	 */
	private String directoryOut = "Music\\outMusic";
	/**
	 * the sorted music file directory
	 */
	private String directorySort = "Music\\sortedMusic";
	/**
	 * the back music file directory use on test to provide the in music file
	 */
	private String directoryBack = "Music\\back";
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
		musicSorter = new MusicFileBandMaster(directoryIn, directoryOut, directorySort);
	}

	public String getDirectoryIn() {
		return directoryIn;
	}

	public void setDirectoryIn(String directoryIn) {
		this.directoryIn = directoryIn;
	}

	public String getDirectoryOut() {
		return directoryOut;
	}

	public void setDirectoryOut(String directoryOut) {
		this.directoryOut = directoryOut;
	}

	public String getDirectorySort() {
		return directorySort;
	}

	public void setDirectorySort(String directorySort) {
		this.directorySort = directorySort;
	}

	public String getDirectoryBack() {
		return directoryBack;
	}

	public void setDirectoryBack(String directoryBack) {
		this.directoryBack = directoryBack;
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
		musicSorter.initDirectoryIn(directoryBack);
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
		LOGGER4J.info("demarage resetDirectory");
		musicSorter.resetDirectories();
		LOGGER4J.info("fin resetDirectory");
	}

	public int getCountFileDirIn() throws IOException {
		LOGGER4J.info("demarage getCountFileDirIn");
		
		MusicExtention[] filter = MusicExtention.values();
		 int countfiles =musicSorter.getCountFileDirIn(filter);
		LOGGER4J.info("fin getCountFileDirIn");
		return countfiles;
	}
}
