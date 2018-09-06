package bandMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;
import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;
import repository.RepositoryMusicFile;

public class MusicFileBandMaster extends FileBandMaster {

	private IRepositoryMusicFile repoMusic;
	private static final Logger LOGGER4J = LogManager.getLogger(MusicFileBandMaster.class.getName());

	public MusicFileBandMaster(String dirIn, String dirOut, String dirSorted)
			throws SecurityException, FileNotFoundException, IOException {
		super(dirIn, dirOut, dirSorted);
		repoMusic = new RepositoryMusicFile();
	}

	/**
	 * fonction qui recupere la liste des fichier a traite recupere tout la
	 * liste contenu dans le repertoire et suprime de la liste tout ce qui ne
	 * finis pas .mp3
	 * 
	 * @throws IOException
	 */
	private ArrayList<String> getListeFilesMP3(String dirName) throws IOException {
		LOGGER4J.trace("getlistefiles music band");
		ArrayList<String> listeFichiers = managerFile.listeFilesOnDirectory(dirName);
		int fileNumber = listeFichiers.size();
		String fileNameItem = "";
		LOGGER4J.trace(fileNumber);
		for (int i = fileNumber - 1; i >= 0; i--) {
			LOGGER4J.trace(i);
			fileNameItem = listeFichiers.get(i);
			LOGGER4J.trace(fileNameItem);
			if (!fileNameItem.endsWith(".mp3")) {
				listeFichiers.remove(i);
			}
		}

		return listeFichiers;
	}

	private MusicDto doLoadDTO(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		// extraction d'information du fiçhier mp3 dans le dto
		MusicDto dto = repoMusic.getDataToMusicFile(pathFileName);

		if (null == dto) {

			TagNotFoundException e = new TagNotFoundException("ID3 not suported ");
			throw e;
		} else {
			return dto;
		}
	}

	private void sortedByAutor(MusicDto song) throws IOException, TagNotFoundException {

		File autorDir = null;
		String songAuthor = applyFormatRuleGenerale(song.getAuthor());
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
		String songAuthor = applyFormatRuleGenerale(song.getAuthor());
		String songAlbum = applyFormatRuleGenerale(song.getAlbum());
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAlbumDir = "";

		if ((songAuthor != "") && (!songAuthor.isEmpty())) {
			if ((songAlbum != "") && (!songAlbum.isEmpty())) {
				LOGGER4J.trace(songAuthor + "-" + songAlbum + "-" + fileName);
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
		String songAlbum = applyFormatRuleGenerale(song.getAlbum());
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

	private void doRunSortMusic() throws IOException {
		ArrayList<String> listeFichiersIn;

		int tabSize = 0;

		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {
			LOGGER4J.debug("Load dir" + dirIn);
			// loggerBandMaster.log(Level.INFO, "Load dir" + dirIn);
			listeFichiersIn = managerFile.listeFilesOnDirectory(dirIn.getPath());
			LOGGER4J.debug("clean files not mp3");
			// loggerBandMaster.log(Level.INFO, "clean files not mp3");
			// netoyer la liste de fichier pour ne garder que les fichier mp3
			rejectFileNotMp3(listeFichiersIn);
			//
			sortFilesMp3(listeFichiersIn);
		}
	}

	private void rejectFileNotMp3(ArrayList<String> listeFichiersIn) {
		int fileNumber = 0;
		String fileNameItem = "";
		String pathFileItem = "";
		boolean isMp3 = false;
		boolean containegitkeep = false;

		fileNumber = listeFichiersIn.size();
		for (int i = fileNumber - 1; i >= 0; i--) {
			fileNameItem = listeFichiersIn.get(i);
			pathFileItem = dirIn + File.separator + fileNameItem;
			isMp3 = fileNameItem.endsWith(".mp3");
			containegitkeep = fileNameItem.contains(".gitkeep");
			// ca fait quoi ca tu vois ce n'est pas asser clair
			// si ce n'est pas un mp3 et pas gitkeep
			// je les deplace dans le repertoire notsuported et je retire de la
			// liste
			if (!isMp3 && containegitkeep) {
				// on ignore le gitkeep pour le deplacement mais pas dans la supresion de la liste 
				if (false == containegitkeep) {
					try {
						managerFile.move(pathFileItem, dirNotSuported + File.separator + fileNameItem);
					} catch (IOException e2) {

						String erroMgs = "imposible de deplacer le Fichier " + fileNameItem + "du repertoir:" + dirIn
								+ " vers le repertoire " + dirNotSuported;
						LOGGER4J.error(erroMgs, e2.getMessage(), e2.getClass().getName(), e2.getStackTrace());
					}
				}
				listeFichiersIn.remove(i);
			}
		}
	}

	private void sortFilesMp3(ArrayList<String> listeFichiersIn) {
		int tabSize = 0;
		String fileNameitem = "";
		// tester si on a des fichier dans le repertoire in
		tabSize = listeFichiersIn.size();
		if (tabSize > 0) {
			LOGGER4J.trace("contains files : ", tabSize);
			for (int i = 0; i < tabSize; i++) {
				fileNameitem = listeFichiersIn.get(i);
				LOGGER4J.trace("sort" + i + "-" + (tabSize - 1));
				sortFileMp3(fileNameitem);
			}
		}
	}

	private void sortFileMp3(String fileName) {
		MusicDto musicDtoItem;
		String pathFileItem = "";
		pathFileItem = dirIn + File.separator + fileName;
		try {

			musicDtoItem = doLoadDTO(pathFileItem);

			// test tri par author
			// sortedByAutor(dto);

			// test tri album
			// sortedByAlbum(dto);

			// tri artiste album
			sortedByAuthorAndAlbum(musicDtoItem);

		} catch (IOException | TagException | UnsupportedOperationException e) {

			LOGGER4J.error("Fichier : " + fileName + "-" + e.getMessage(), e.getClass().getName(), e.getStackTrace());
			try {
				managerFile.move(pathFileItem, dirError + File.separator + fileName);
			} catch (IOException e2) {
				String erroMgs = "imposible de deplacer le Fichier " + fileName + "du repertoir:" + dirIn
						+ " vers le repertoire " + dirNotSuported;
				LOGGER4J.error(erroMgs + "-" + e2.getMessage(), e2.getClass().getName(), e2.getStackTrace());
			}
		}

	}

	private String applyFormatRuleGenerale(String raw) {
		String formatResult = "";
		// supresion espace debans et deriere
		formatResult = raw.trim();
		// tout en maj
		formatResult = formatResult.toUpperCase();
		// remplace les double espace par un simple
		formatResult = formatResult.replaceAll("  ", "_");
		// remplace les simple espace par un underscore
		formatResult = formatResult.replaceAll(" ", "_");
		// remplace un tiret par un underscore
		formatResult = formatResult.replaceAll("-", "_");
		// supresion les guillement
		formatResult = formatResult.replaceAll("\"", "");
		// supresion des slash \
		formatResult = formatResult.replaceAll("/", "");
		return formatResult;
	}

	public void runSortFile() throws IOException {
		doRunSortMusic();
	}
}
