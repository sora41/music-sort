package MyMp3;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ButtonGroup;

public class MainMp3 {

	// TODO 1.4 mettre en place un des test Unitaire

	/*
	 * test unitaire stp voir
	 * http://www.junit.fr/2011/11/20/tutoriel-eclipse-junit-mon-premier-test-
	 * automatique/ https://www.jmdoudoux.fr/java/dejae/chap011.htm
	 * 
	 */

	private static final String DIRECTORY_IN = "Music\\inMusic";
	private static final String DIRECTORY_OUT = "Music\\outMusic";
	private static final String DIRECTORY_SORT = "Music\\sortedMusic";
	private static final String DIRECTORY_BACK = "Music\\back";

	public static void MainRunSort() {
		MusicFileBandMaster bigBrother = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		bigBrother.runSortFile();
	}

	public static void initDirectory() {
		// System.out.println("initDirectori");
		MusicFileBandMaster bigBrother = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		bigBrother.initDirectorieIn(DIRECTORY_BACK);
	}

	public static void resetDirectory() {
		// System.out.println("resetDirectori");
		FileBandMaster bigBrother = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		bigBrother.resetDirectories();
	}

	public static void main(String[] args) {

		System.out.println("lancement reset");
		resetDirectory();
		System.out.println("lancement initalisation");
		initDirectory();
		System.out.println("lancement tri");
		MainRunSort();	

	}

}
