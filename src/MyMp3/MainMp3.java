package MyMp3;

import java.io.File;
import java.util.ArrayList;

public class MainMp3 {
	/*
	 * test unitaire stp voir
	 * http://www.junit.fr/2011/11/20/tutoriel-eclipse-junit-mon-premier-test-
	 * automatique/ https://www.jmdoudoux.fr/java/dejae/chap011.htm
	 * 
	 */

	public static void TestloadMp3Manuel() {
		String dirIn = "Music\\inMusic";
		String dirOut = "Music\\outMusic";
		String dirSorted = "Music\\sortedMusic";

		TestMp3 tMp3 = new TestMp3();
		MusicFileBandMaster bigBrother = new MusicFileBandMaster(dirIn, dirOut, dirSorted);

		ArrayList<String> listeFichiers = bigBrother.getListeFiles(dirIn);

		System.out.println("----------------------------------------");
		System.out.println("n0 " + listeFichiers.get(0));
		tMp3.loadMp3Mannuel(dirIn + File.separator + listeFichiers.get(0));
		System.out.println("----------------------------------------");
		System.out.println("n1 " + listeFichiers.get(1));
		tMp3.loadMp3Mannuel(dirIn + File.separator + listeFichiers.get(1));
		System.out.println("----------------------------------------");
		System.out.println("n2 " + listeFichiers.get(2));
		tMp3.loadMp3Mannuel(dirIn + File.separator + listeFichiers.get(2));
		System.out.println("----------------------------------------");
		System.out.println("n7 " + listeFichiers.get(7));
		tMp3.loadMp3Mannuel(dirIn + File.separator + listeFichiers.get(7));
		System.out.println("----------------------------------------");
		System.out.println("n8 " + listeFichiers.get(8));
		tMp3.loadMp3Mannuel(dirIn + File.separator + listeFichiers.get(8));

	}

	public static void MainRunSort() {
		String dirIn = "Music\\inMusic";
		String dirOut = "Music\\outMusic";
		String dirSorted = "Music\\sortedMusic";

		MusicFileBandMaster bigBrother = new MusicFileBandMaster(dirIn, dirOut, dirSorted);

		bigBrother.runSortFile();
	}

	public static void TestFileMethodeListFiltre() {

	}

	public static void testFileMethodeList() {
		String dirIn = "Music\\inMusic";
		File f = new File(dirIn);
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
	}

	public static void main(String[] args) {

		MainRunSort();
		// TestloadMp3Manuel();
		// testFileMethodeList();
	}

}
