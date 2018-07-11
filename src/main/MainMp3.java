package main;

import bandMaster.FileBandMaster;
import bandMaster.MusicFileBandMaster;

public class MainMp3 {

	private static final String DIRECTORY_IN = "Music\\inMusic";
	private static final String DIRECTORY_OUT = "Music\\outMusic";
	private static final String DIRECTORY_SORT = "Music\\sortedMusic";
	private static final String DIRECTORY_BACK = "Music\\back";

	private static MusicFileBandMaster musicSorter;

	public static void initApplication() {
		musicSorter = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
	}

	public static void main(String[] args) {

		initApplication();
		System.out.println("lancement reset");
		musicSorter.resetDirectories();
		System.out.println("lancement initalisation");
		musicSorter.initDirectorieIn(DIRECTORY_BACK);
		System.out.println("lancement tri");
		musicSorter.runSortFile();

	}

}
