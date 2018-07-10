package bandMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;

import datatransfert.MusicDto;

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

	@Override
	protected boolean validateDirectory(File dir) {
		return super.validateDirectory(dir);
	}

	/**
	 * fonction qui recupere la liste des fichier a traite recupere tout la
	 * liste contenu dans le repertoire et suprime de la liste tout ce qui ne
	 * finis pas .mp3 (dans un premier temps )
	 */

	public ArrayList<String> getListeFilesMp3(String dirName) {
		// System.out.println("getlistefiles music band");
		ArrayList<String> listeFichiers = super.listeFilesOnDirectory(dirName);
		int fileNumber = listeFichiers.size();
		String fileNameItem = "";
		// System.out.println(fileNumber);
		for (int i = fileNumber - 1; i >= 0; i--) {
			// System.out.println(i);
			fileNameItem = listeFichiers.get(i);
			// System.out.println(fileNameItem);
			if (!fileNameItem.endsWith(".mp3"))
				listeFichiers.remove(i);
		}

		return listeFichiers;
	}

	private void doLoadMyMpId3(String fileName) {
		TestMp3 mp3 = new TestMp3();
		mp3.loadMp3Mannuel(fileName);
	}

	private void doLoadLibId3(String fileName) throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		
		MP3File mp3file = new MP3File(fileName);
		if (mp3file.hasID3v1Tag()) {

			// test tri par author
			// sortedByAutor(mp3file);

			// test tri album
			//sortedByAlbum(mp3file);
			// tri artiste album 
			sortedByAuthorAndAlbum(mp3file);
			// TODO 1.6 test album
			// test si le repertoire de l'artiste existe deja
			// si oui on l utilise sinon on le cree

			// TODO 6.0 Mode de Tri
			// par artiste// album
			// genre // artiste// album
			// ect


			// TODO 1.1 REfactorer class trop de methode exite deja dans File

		} else {
			System.out.println("file: " + mp3file.getFilenameTag().composeFilename() + " ID3 not suported ");
			TagNotFoundException e = new TagNotFoundException("ID3 not suported ");
			throw e;
		}
	}
	
private MusicDto doLoadLibId3Dto(String fileName) throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		
		MP3File mp3file = new MP3File(fileName);
		MusicDto SongDto = new MusicDto();
		if (mp3file.hasID3v1Tag()) {

			SongDto.setAlbum(mp3file.getID3v1Tag().getAlbum());
			SongDto.setFileName(mp3file.getFilenameTag().composeFilename());
			SongDto.setAuthor(mp3file.getID3v1Tag().getArtist());
			SongDto.setPathFile(mp3file.getMp3file().getPath());
			SongDto.setSongName(mp3file.getID3v1Tag().getTitle());
			
			return SongDto;
		} else {
			System.out.println("file: " + mp3file.getFilenameTag().composeFilename() + " ID3 not suported ");
			TagNotFoundException e = new TagNotFoundException("ID3 not suported ");
			throw e;
		}
	}

	private void sortedByAutor(MP3File song) throws IOException, TagNotFoundException {
		// TODO 5.0 test artiste Normaliser les chaine Artiste en Maj
		// test si le repertoire de l'artiste existe deja
		// si oui on l utilise sinon on le cree

		File autorDir = null;
		String songAuthor = song.getID3v1Tag().getArtist();
		String fileName = song.getFilenameTag().composeFilename();
		String pathFile = song.getMp3file().getPath();
		// String pathSorted = dirSorted.getPath();
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

	private void sortedByAuthorAndAlbum(MP3File song) throws IOException, TagNotFoundException {

		File autorDir = null;
		File albumDir = null;
		String songAuthor = song.getID3v1Tag().getArtist();
		String songAlbum = song.getID3v1Tag().getAlbum();
		String fileName = song.getFilenameTag().composeFilename();
		String pathFile = song.getMp3file().getPath();
		// String pathSorted = dirSorted.getPath();
		String sortedTarget = "";
		String pathAutorDir = "";
		String pathAlbumDir = "";

		if ((songAuthor != "") && (!songAuthor.isEmpty())) {
			if ((songAlbum != "") && (!songAlbum.isEmpty())) {
				System.out.println(songAlbum + "-" + songAuthor);
				autorDir = new File(dirSorted.getPath() + File.separator + songAuthor);
				if (!validateDirectory(autorDir)) {
					if (!autorDir.mkdir()) {
						IOException e = new IOException("echec creation repertoire " + autorDir.getPath());
						throw e;
					}
				}

				pathAutorDir = autorDir.getPath();
				// System.out.println("path autore dir = "+pathAutorDir);
				albumDir = new File(dirSorted.getPath() + File.separator + songAuthor + File.separator + songAlbum);
				// System.out.println("path album dir = "+albumDir);
				if (!validateDirectory(albumDir)) {
					if (!albumDir.mkdir()) {
						IOException e = new IOException("echec creation repertoire " + albumDir.getPath());
						throw e;
					}

				}
				pathAlbumDir = albumDir.getPath();
				sortedTarget = pathAlbumDir + File.separator + fileName;

				moveFile(pathFile, sortedTarget);
			} else {
				TagNotFoundException e = new TagNotFoundException("no album");
				throw e;
			}
		} else {

			TagNotFoundException e = new TagNotFoundException("no artiste");
			throw e;
		}
	}

	private void sortedByAlbum(MP3File song) throws IOException, TagNotFoundException {

		File albumDir = null;
		String songAlbum = song.getID3v1Tag().getAlbum();
		String fileName = song.getFilenameTag().composeFilename();
		String pathFile = song.getMp3file().getPath();
		// String pathSorted = dirSorted.getPath();
		String sortedTarget = "";
		String pathAlbumDir = "";

		if ((songAlbum != "") && (!songAlbum.isEmpty())) {

			albumDir = new File(dirSorted.getPath() + File.separator + songAlbum);
			if (!validateDirectory(albumDir)) {
				if (!albumDir.mkdir()) {
					IOException e = new IOException("echec creation repertoire " + albumDir.getPath());
					throw e;
				}

			}
			pathAlbumDir = albumDir.getPath();
			sortedTarget = pathAlbumDir + File.separator + fileName;
			moveFile(pathFile, sortedTarget);

		} else {
			TagNotFoundException e = new TagNotFoundException("no album");
			throw e;
		}
	}

	private void loadMp3Id3(String fileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {

		// doLoadMyMpId3(fileName);
		doLoadLibId3(fileName);
	}

	private void runSortMusicFile() {
		ArrayList<String> listeFichiersIn;
		String fileNameitem = "";
		String pahtFileItem = "";
		int tabSize = 0;
		/*
		 * https://www.jmdoudoux.fr/java/dej/chap-nio2.htm
		 * https://docs.oracle.com/javase/7/docs/api/index.html?java/io/File.
		 * html
		 */

		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {

			listeFichiersIn = getListeFilesMp3(dirIn.getPath());
			// System.out.println("Load dir" + dirIn);
			// tester si on a des fichier dans le repertoire in
			tabSize = listeFichiersIn.size();
			if (tabSize > 0) {
				// afficherFileListe(listeFichiersIn);
				System.out.println("contains " + tabSize + " files");
				for (int i = 0; i < tabSize; i++) {
					fileNameitem = listeFichiersIn.get(i);
					pahtFileItem = dirIn + File.separator + fileNameitem;
					try {
						System.out.println("sort" + i + " :: " + (tabSize - 1));
						// li faut separer l extraction d'information du
						// traitement

						loadMp3Id3(pahtFileItem);
					} catch (IOException | TagException | UnsupportedOperationException e) {
						System.out.println(e.getMessage());
						// e.printStackTrace();
						try {
							// System.out.println("move: " + pahtFileItem + " to
							// dir" + dirOut);
							// System.out.println(e.getClass() +
							// e.getMessage());
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
