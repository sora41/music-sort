package MyMp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;

public class MusicFileBandMaster extends FileBandMaster {

	// TODO 1.2 AJouter GEstion des log
	// voir
	// cree un class file a qui on ajoute les log
	// http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/CoursJava/fichierDeLogs.html
	// VOIR TESTLOG projet
	// voir logiciel https://korben.info/taguez-automatiquement-vos-mp3.html
	/// http://www.clubic.com/telecharger-fiche12753-mp3tag.html
	public MusicFileBandMaster(String dirIn, String dirOut, String dirSorted) {
		super(dirIn, dirOut, dirSorted);
	}

	public ArrayList<String> getListFiles_one(String dirName) {
		ArrayList<String> listFiles = super.getListeFiles(dirName);
		String fileNameItem = "";

		return listFiles;
	}

	@Override
	protected boolean validateDirectory(File dir) {
		// TODO Auto-generated method stub
		return super.validateDirectory(dir);
	}

	public ArrayList<String> getListeFiles(String dirName) {
		ArrayList<String> listeFichiers = super.getListeFiles(dirName);
		String fileNameItem = "";

		for (int i = 0; i < listeFichiers.size() - 1; i++) {
			fileNameItem = listeFichiers.get(i);
			// TODO 2.0 a complete par la liste de extention
			// remonter cette notion de filtre sur un enumere que l'on poura
			// complete
			// lie aux fichier musique
			if (!fileNameItem.endsWith(".mp3"))
				listeFichiers.remove(i);

		}
		return listeFichiers;
	}

	private void DoloadMyMpId3(String fileName) {
		TestMp3 mp3 = new TestMp3();
		mp3.loadMp3Mannuel(fileName);
	}

	private void sortedByAutor(MP3File song) throws IOException, TagNotFoundException {
		// TODO 5.0 test artiste Normaliser les chaine Artiste en Maj
		// test si le repertoire de l'artiste existe deja
		// si oui on l utilise sinon on le cree

		File autorDir = null;
		String songAuthor = song.getID3v1Tag().getArtist();
		String fileName = song.getFilenameTag().composeFilename();
		String pathFile = song.getMp3file().getPath();
		String pathSorted = dirSorted.getPath();
		String sortedTarget = "";
		String pathAutorDir = "";
		// System.out.println("Artiste: "+songAuthor);
		if ((songAuthor != "") && (!songAuthor.isEmpty())) {
			// System.out.println(dirSorted.getPath() + File.separator +
			// songAuthor);

			autorDir = new File(dirSorted.getPath() + File.separator + songAuthor);
			if (!validateDirectory(autorDir)) {
				if (!autorDir.mkdir()) {
					IOException e = new IOException("echec creation repertoire " + autorDir.getPath());
					throw e;
				}

			}
			pathAutorDir = autorDir.getPath();
			sortedTarget = pathAutorDir + File.separator + fileName;
			/*
			 * System.out.println("mp3 file name "+fileName);
			 * System.out.println("mp3 file paht "+pathFile);
			 * System.out.println("dir sorted paht "+pathSorted);
			 * 
			 * System.out.println("dir autor paht dir "+pathAutorDir);
			 * System.out.println("dir sorted paht "+sortedTarget);
			 */

			moveFile(pathFile, sortedTarget);
			// move to auto dir

		} else {
			// System.out.println("file: " +
			// song.getFilenameTag().composeFilename() + " ID3 not suported ");
			TagNotFoundException e = new TagNotFoundException("no artiste");
			throw e;
		}

		/*
		 * verifie si le repertoire de l auteur existe si oui deplace la musique
		 * dedans sinon cree le repetoire puis mes la musique dedans
		 */
	}

	private void loadMp3Id3(String fileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {

		// DoloadMyMpId3(fileName);
		try {
			MP3File mp3file = new MP3File(fileName);

			if (mp3file.hasID3v1Tag()) {

				sortedByAutor(mp3file);
				// TODO 1.6 test album
				// test si le repertoire de l'artiste existe deja
				// si oui on l utilise sinon on le cree

				// TODO 6.0 Mode de Tri
				// par artiste// album
				// genre // artiste// album
				// ect

				// TODO 1.5 premier essay tri Artiste// album

				// TODO 1.1 REfactorer class trop de methode exite deja dans
				// File

			} else {
				System.out.println("file: " + mp3file.getFilenameTag().composeFilename() + " ID3 not suported ");
				TagNotFoundException e = new TagNotFoundException("ID3 not suported ");
				throw e;
			}
		} catch (Exception e ) {
			System.out.println(e.getClass());
			e.printStackTrace();
		}

	}

	private void runSortMusicFile() {
		ArrayList<String> listeFichiersIn;
		String fileNameitem = "";
		String pahtFileItem = "";
		/*
		 * https://www.jmdoudoux.fr/java/dej/chap-nio2.htm
		 * https://docs.oracle.com/javase/7/docs/api/index.html?java/io/File.
		 * html
		 */

		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {

			// probleme rencomptrer sur lecture de
			// ficher mdr 3 sur la lecture de la taille de certain fichier
			// /!\ probleme venais du jeux de fichier corronpu
			// attention a trouver la source de cette corruption

			listeFichiersIn = getListeFiles(dirIn.getPath());
			System.out.println("Load dir" + dirIn);
			// tester si on a des fichier dans le repertoire in
			if (listeFichiersIn.size() > 0) {
				// afficherFileListe(listeFichiersIn);
				System.out.println("contains " + listeFichiersIn.size() + " files");
				for (int i = 0; i < listeFichiersIn.size(); i++) {
					fileNameitem = listeFichiersIn.get(i);
					pahtFileItem = dirIn + File.separator + fileNameitem;
					try {
						System.out.println("read:" + fileNameitem);
						loadMp3Id3(pahtFileItem);
					} catch (IOException | TagException | UnsupportedOperationException e) {
						// System.out.println(e.getMessage());
						// e.printStackTrace();
						try {
							System.out.println("move: " + pahtFileItem + " to dir" + dirOut);
							System.out.println(e.getClass() + e.getMessage());
							moveFile(pahtFileItem, dirOut + File.separator + fileNameitem);
						} catch (IOException e2) {
							System.err.println("imposible de deplacer le Fichier " + fileNameitem);
							System.err.println("du repertoir:" + dirIn + " vers le repertoire " + dirOut);
						}
					}
				}
			}
		}
	}

	public void runSortFile() {

		runSortMusicFile();
	}
}
