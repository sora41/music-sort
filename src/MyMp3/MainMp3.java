package MyMp3;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ButtonGroup;

public class MainMp3 {

	// TODO 1.4 mettre en place un des test Unitaire 
	// TODO 1.0 netoyer les fonction initile dans la classe main  
	// TODO 1.1 .1 effet de born reset ne marche plus a cause de la modification de la fonction 
	// getlites sur le bandMaster
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

	public static void TestloadMp3Manuel() {

		TestMp3 tMp3 = new TestMp3();
		MusicFileBandMaster bigBrother = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);

		ArrayList<String> listeFichiers = bigBrother.getListeFilesMp3(DIRECTORY_IN);

		System.out.println("----------------------------------------");
		System.out.println("n0 " + listeFichiers.get(0));
		tMp3.loadMp3Mannuel(DIRECTORY_IN + File.separator + listeFichiers.get(0));
		System.out.println("----------------------------------------");
		System.out.println("n1 " + listeFichiers.get(1));
		tMp3.loadMp3Mannuel(DIRECTORY_IN + File.separator + listeFichiers.get(1));
		System.out.println("----------------------------------------");
		System.out.println("n2 " + listeFichiers.get(2));
		tMp3.loadMp3Mannuel(DIRECTORY_IN + File.separator + listeFichiers.get(2));
		System.out.println("----------------------------------------");
		System.out.println("n7 " + listeFichiers.get(7));
		tMp3.loadMp3Mannuel(DIRECTORY_IN + File.separator + listeFichiers.get(7));
		System.out.println("----------------------------------------");
		System.out.println("n8 " + listeFichiers.get(8));
		tMp3.loadMp3Mannuel(DIRECTORY_IN + File.separator + listeFichiers.get(8));

	}

	public static void MainRunSort() {
		MusicFileBandMaster bigBrother = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		bigBrother.runSortFile();
	}

/*
	public static void testFileMethodeList() {

		File f = new File(DIRECTORY_IN);
		String[] tabName = f.list();
		System.out.println("testFileMetodeList");
		afficheTab(tabName);

	}

	public static void afficheTab(String[] tab) {
		if (tab != null)
			if (tab.length > 0)
				for (int i = 0; i < tab.length; i++) {
					System.out.println(tab[i]);
				}
	}*/

	public static void initDirectory() {
		// System.out.println("initDirectori");
		MusicFileBandMaster bigBrother = new MusicFileBandMaster(DIRECTORY_IN, DIRECTORY_OUT, DIRECTORY_SORT);
		bigBrother.initDirectorieIn(DIRECTORY_BACK);
	}

	public static void resetDirectory() {
		//System.out.println("resetDirectori");
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

		// TestloadMp3Manuel();
		// testFileMethodeList();

	}

}
