package bandMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;

import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;
import repository.RepositoryMusicFile;
import repository.TestMp3;

public class MusicFileBandMaster extends FileBandMaster {

	private IRepositoryMusicFile repoMusic;
	public MusicFileBandMaster(String dirIn, String dirOut, String dirSorted) {
		super(dirIn, dirOut, dirSorted);
		repoMusic = new RepositoryMusicFile();
	}
	
	/**
	 * fonction qui recupere la liste des fichier a traite recupere tout la
	 * liste contenu dans le repertoire et suprime de la liste tout ce qui ne
	 * finis pas .mp3 (dans un premier temps )
	 */
	private ArrayList<String> getListeFilesMp3(String dirName) {
		// System.out.println("getlistefiles music band");
		ArrayList<String> listeFichiers = managerFile.listeFilesOnDirectory(dirName);
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

	private void doLoadDTO(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		// extraction d'information du fiçhier mp3 dans le dto 
		MusicDto dto  = repoMusic.getDataToMusicFile(pathFileName);
		
		if (null != dto) {

			// test tri par author
			 //sortedByAutor(dto);

			// test tri album
			//sortedByAlbum(dto);

			// tri artiste album
			sortedByAuthorAndAlbum(dto);

		} else {
			System.out.println("file: " + dto.getFileName() + " ID3 not suported ");
			TagNotFoundException e = new TagNotFoundException("ID3 not suported ");
			throw e;
		}
	}

	
	private void sortedByAutor(MusicDto song) throws IOException, TagNotFoundException {

		File autorDir = null;
		String songAuthor = song.getAuthor();
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAutorDir = "";
	
		if ((songAuthor != "") && (!songAuthor.isEmpty())) {
			

			autorDir = new File(dirSorted.getPath() + File.separator + songAuthor);
			if (!managerFile.validateDirectory(autorDir)) {
				if (!autorDir.mkdir()) {
					IOException e = new IOException("echec creation repertoire " + autorDir.getPath());
					throw e;
				}
			}
			pathAutorDir = autorDir.getPath();
			sortedTarget = pathAutorDir + File.separator + fileName;

			managerFile.move(pathFile, sortedTarget);

		} else {
			TagNotFoundException e = new TagNotFoundException("no artiste");
			throw e;
		}

	}

	private void sortedByAuthorAndAlbum(MusicDto song) throws IOException, TagNotFoundException {

		File autorDir = null;
		File albumDir = null;
		String songAuthor = song.getAuthor();
		String songAlbum = applyFormatRuleAlbum(song.getAlbum());
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAlbumDir = "";

		if ((songAuthor != "") && (!songAuthor.isEmpty())) {
			if ((songAlbum != "") && (!songAlbum.isEmpty())) {
				System.out.println(songAlbum + "-" + songAuthor);
				autorDir = new File(dirSorted.getPath() + File.separator + songAuthor);
				if (!managerFile.validateDirectory(autorDir)) {
					if (!autorDir.mkdir()) {
						IOException e = new IOException("echec creation repertoire " + autorDir.getPath());
						throw e;
					}
				}

				albumDir = new File(dirSorted.getPath() + File.separator + songAuthor + File.separator + songAlbum);
				if (!managerFile.validateDirectory(albumDir)) {
					if (!albumDir.mkdir()) {
						IOException e = new IOException("echec creation repertoire " + albumDir.getPath());
						throw e;
					}

				}
				pathAlbumDir = albumDir.getPath();
				sortedTarget = pathAlbumDir + File.separator + fileName;

				managerFile.move(pathFile, sortedTarget);
			} else {
				TagNotFoundException e = new TagNotFoundException("no album");
				throw e;
			}
		} else {

			TagNotFoundException e = new TagNotFoundException("no artiste");
			throw e;
		}
	}

	private void sortedByAlbum(MusicDto song) throws IOException, TagNotFoundException {

		File albumDir = null;
		String songAlbum =  applyFormatRuleAlbum(song.getAlbum());
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAlbumDir = "";

		if ((songAlbum != "") && (!songAlbum.isEmpty())) {

			albumDir = new File(dirSorted.getPath() + File.separator + songAlbum);
			if (!managerFile.validateDirectory(albumDir)) {
				if (!albumDir.mkdir()) {
					IOException e = new IOException("echec creation repertoire " + albumDir.getPath());
					throw e;
				}

			}
			pathAlbumDir = albumDir.getPath();
			sortedTarget = pathAlbumDir + File.separator + fileName;
			managerFile.move(pathFile, sortedTarget);
		} else {
			TagNotFoundException e = new TagNotFoundException("no album");
			throw e;
		}
	}
	
	private void loadMp3Id3(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {

		doLoadDTO(pathFileName);
	}

	private void runSortMp3Music() {
		ArrayList<String> listeFichiersIn;
		String fileNameitem = "";
		String pathFileItem = "";
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
					pathFileItem = dirIn + File.separator + fileNameitem;
					try {
						System.out.println("sort" + i + " :: " + (tabSize - 1));
						
						doLoadDTO(pathFileItem);
					} catch (IOException | TagException | UnsupportedOperationException e) {
						System.out.println(e.getMessage());
						// e.printStackTrace();
						try {
							// System.out.println("move: " + pahtFileItem + " to
							// dir" + dirOut);
							// System.out.println(e.getClass() +
							// e.getMessage());
							//moveFile(pahtFileItem, dirOut + File.separator + fileNameitem);
							managerFile.move(pathFileItem, dirOut + File.separator + fileNameitem);
						} catch (IOException e2) {
							System.err.println("imposible de deplacer le Fichier " + fileNameitem);
							System.err.println("du repertoir:" + dirIn + " vers le repertoire " + dirOut);
						}
					}
				}
			}
		}
	}
	
	private String applyFormatRuleAlbum(String rawAlbum)
	{
		String formatResult ="";
		// supresion espace debans et deriere
		formatResult= rawAlbum.trim();
		// tout en maj
		formatResult = formatResult.toUpperCase();
		// remplace les double espace par un simple
		formatResult = formatResult.replaceAll("  ", "_");
		// remplace les simple espace par un underscore
		formatResult = formatResult.replaceAll(" ", "_");
		// remplace un tiret par un underscore
				formatResult = formatResult.replaceAll("-", "_");
		
		return formatResult;
	}
	
	public void runSortFile() {
		runSortMp3Music();
	}
}
