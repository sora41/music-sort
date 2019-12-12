package bandmaster;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.tag.TagNotFoundException;

import constant.MusicExtention;
import datatransfert.MusicDto;

import repository.IRepositoryMusicFile;
import repository.builder.BuilderMusicRepository;

/**
 * bandMaster file music
 */
public class MusicFileBandMaster extends FileBandMaster //implements Observable 
{

	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicFileBandMaster.class.getName());

	private static final String ECHEC_CREATE_DIR = "echec creation repertoire";
	private static final String LOG_FILE_SEPARATOR = "-";
	private static final String NO_EXTENTION_FILE = " fichier sans extention";
	private static final String EMPTHY_STRING = "";
	private static final String UNDERSCORE = "_";
	private static final String NO_ARTISTE = "no artiste";
	private static final String NO_ALBUM = "no album";
	
	/**
	 * Creates a new MusicFileBandMaster object with diretory In ,diretory out
	 * and diretory sorted set . initliatise tne RepositoryMusiqueFile
	 */
	public MusicFileBandMaster(String dirIn, String dirOut, String dirSorted) throws IOException {
		super(dirIn, dirOut, dirSorted);
	}

	private String extractExtention(String fileName) throws Exception {
		String extention = "";
		if ((fileName != null) && !fileName.isEmpty()) {
			if (fileName.contains(".")) {
				int size = fileName.length();
				extention = fileName.substring(size - 3);
				extention = extention.toLowerCase();
			} else {
				StringBuilder exceptionString = new StringBuilder(fileName);
				exceptionString.append(NO_EXTENTION_FILE);
				throw new Exception(exceptionString.toString());
			}
		} else {
			throw new Exception("la chaine fileName est vide ");
		}
		return extention;
	}

	/**
	 * load DtoMusic object by Path file
	 * 
	 * @param pathFileName
	 *            file String path
	 * @return dto object load or null
	 * @throws Exception
	 */
	private MusicDto doLoadDTO(String pathFileName) throws Exception {
		// extraction d'information des fichier audio celons les extention dans
		// le dto
		String extention = this.extractExtention(pathFileName);
		MusicExtention enumExention = MusicExtention.valueOf(extention.toUpperCase());
		IRepositoryMusicFile repositoryMusic = new BuilderMusicRepository().buildMusicRepository(enumExention);

		MusicDto dto = repositoryMusic.getDataToMusicFile(pathFileName);
		if (null == dto) {
			throw new TagNotFoundException("Tag not found ");
		} else {
			return dto;
		}
	}

	/**
	 * sort music file by author
	 * 
	 * @param song
	 * @throws IOException
	 * @throws TagNotFoundException
	 */
	private void sortedByAutor(MusicDto song) throws IOException, TagNotFoundException {

		File autorDir = null;
		String songAuthor = applyFormatRuleGenerale(song.getAuthor());
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAutorDir = "";

		if ((songAuthor != null) && (!songAuthor.isEmpty())) {

			autorDir = new File(buildNameFile(dirSorted.getPath(), songAuthor));
			pathAutorDir = autorDir.getPath();
			if (!managerFile.validateDirectory(autorDir) && !autorDir.mkdir()) {
				StringBuilder ioExecptionChaine = new StringBuilder(ECHEC_CREATE_DIR);
				ioExecptionChaine.append(pathAutorDir);
				throw new IOException(ioExecptionChaine.toString());
			}

			sortedTarget = buildNameFile(pathAutorDir, fileName);
			managerFile.moveFile(pathFile, sortedTarget);

		} else {
			throw new TagNotFoundException(NO_ARTISTE);
		}
	}

	/**
	 * sort music file by author and album
	 */
	private void sortedByAuthorAndAlbum(MusicDto song) throws IOException, TagNotFoundException {

		File autorDir;
		File albumDir;
		String songAuthor = applyFormatRuleGenerale(song.getAuthor());
		String songAlbum = applyFormatRuleGenerale(song.getAlbum());
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAlbumDir = "";
		String pathAutorDir;

		if ((songAuthor != null) && (!songAuthor.isEmpty())) {
			if ((songAlbum != null) && (!songAlbum.isEmpty())) {

				StringBuilder tracerLogger = new StringBuilder(songAuthor);
				tracerLogger.append(LOG_FILE_SEPARATOR);
				tracerLogger.append(songAlbum);
				tracerLogger.append(LOG_FILE_SEPARATOR);
				tracerLogger.append(fileName);

				LOGGER4J.trace(tracerLogger.toString());

				pathAutorDir = buildNameFile(dirSorted.getPath(), songAuthor);
				autorDir = new File(pathAutorDir);
				if (!managerFile.validateDirectory(autorDir) && !autorDir.mkdir()) {
					StringBuilder ioExecptionChaine = new StringBuilder(ECHEC_CREATE_DIR);
					ioExecptionChaine.append(pathAutorDir);
					throw new IOException(ioExecptionChaine.toString());
				}
				pathAlbumDir = buildNameFile(pathAutorDir, songAlbum);
				albumDir = new File(pathAlbumDir);
				if (!managerFile.validateDirectory(albumDir) && !albumDir.mkdir()) {
					StringBuilder ioExecptionChaine = new StringBuilder(ECHEC_CREATE_DIR);
					ioExecptionChaine.append(pathAlbumDir);
					throw new IOException(ioExecptionChaine.toString());
				}

				sortedTarget = buildNameFile(pathAlbumDir, fileName);
				managerFile.moveFile(pathFile, sortedTarget);
			} else {
				throw new TagNotFoundException(NO_ALBUM);
			}
		} else {
			throw new TagNotFoundException(NO_ARTISTE);
		}
	}

	/**
	 * sort music file by album
	 */
	private void sortedByAlbum(MusicDto song) throws IOException, TagNotFoundException {

		File albumDir = null;
		String songAlbum = applyFormatRuleGenerale(song.getAlbum());
		String fileName = song.getFileName();
		String pathFile = song.getPathFile();
		String sortedTarget = "";
		String pathAlbumDir = "";

		if ((songAlbum != null) && (!songAlbum.isEmpty())) {
			pathAlbumDir = buildNameFile(dirSorted.getPath(), songAlbum);
			albumDir = new File(pathAlbumDir);
			

			if (!managerFile.validateDirectory(albumDir) && !albumDir.mkdir()) {
				throw new IOException("echec creation repertoire " + albumDir.getPath());
			}

			sortedTarget = buildNameFile(pathAlbumDir, fileName); 
			managerFile.moveFile(pathFile, sortedTarget);
		} else {
			throw new TagNotFoundException(NO_ALBUM);
		}
	}

	/**
	 * 
	 * @param listeFichiersIn
	 */
	private void sortListMusicFile(ArrayList<String> listeFichiersIn) {
		int tabSize = 0;
		String fileNameitem = "";
		boolean zeroFile = true;
		if (null != listeFichiersIn) {
			tabSize = listeFichiersIn.size();
			// tester si on a des fichier dans le repertoire in
			if ((tabSize > 0)) {
				zeroFile = false;
				LOGGER4J.trace("contains files :  {}", tabSize);
				for (int i = 0; i < tabSize; i++) {
					fileNameitem = listeFichiersIn.get(i);

					StringBuilder logMgs = new StringBuilder("sort");
					logMgs.append(i);
					logMgs.append(LOG_FILE_SEPARATOR);
					logMgs.append(tabSize - 1);
					
					LOGGER4J.trace(logMgs.toString());
					sortMusicFile(fileNameitem);
					updateObservateur(i, tabSize - 1,"SORT");
				}
			}
		}
		if (zeroFile) {
			LOGGER4J.info("aucun fichier a traité ");
		}
	}

	/**
	 * 
	 * @param fileName
	 */
	private void sortMusicFile(String fileName) {
		MusicDto musicDtoItem;
		try {
			musicDtoItem = doLoadDTO(fileName);
			// tri artiste album
			sortedByAuthorAndAlbum(musicDtoItem);
		} catch (Exception sortException) {
			

			StringBuilder logMsg = new StringBuilder("Fichier : ");
			logMsg.append(fileName);
			logMsg.append(LOG_FILE_SEPARATOR);
			logMsg.append(sortException.getMessage());

			LOGGER4J.error(logMsg.toString(), sortException.getClass().getName(), sortException.getStackTrace());
			try {
				managerFile.moveFile(fileName, buildNameFile(dirError.getPath(), fileName));
			} catch (IOException moveException) {
				StringBuilder erroMsg = new StringBuilder("imposible de deplacer le Fichier ");
				erroMsg.append(fileName);
				erroMsg.append("du repertoir:");
				erroMsg.append(dirIn);
				erroMsg.append(" vers le repertoire ");
				erroMsg.append(dirNotSuported);
				erroMsg.append(LOG_FILE_SEPARATOR);
				erroMsg.append(moveException.getMessage());

				LOGGER4J.error(erroMsg.toString(), moveException.getClass().getName(), moveException.getStackTrace());
			}
		}
	}

	/** format string use for create directori */
	private String applyFormatRuleGenerale(String raw) {
		String formatResult = "";
		// supresion espace devan et deriere
		formatResult = raw.trim();

		// replacement des é en e
		formatResult = formatResult.replace("é", "e");
		formatResult = formatResult.replace("è", "e");
		// tout en maj
		formatResult = formatResult.toUpperCase();
		// remplace les double espace par un simple
		formatResult = formatResult.replace("  ", UNDERSCORE);
		// remplace les simple espace par un underscore
		formatResult = formatResult.replace(" ", UNDERSCORE);
		// remplace un tiret par un underscore
		formatResult = formatResult.replace("-", UNDERSCORE);
		// supresion les guillement
		formatResult = formatResult.replace("\"", EMPTHY_STRING);
		// supresion des slash \
		formatResult = formatResult.replace("/", EMPTHY_STRING);
		// supresion des '
		formatResult = formatResult.replace("'", EMPTHY_STRING);

		return formatResult;
	}

	/** launch sort procedure */
	public void runSortFile() throws IOException {
		ArrayList<String> listeFichiersIn;
		MusicExtention[] filter = MusicExtention.values();
		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {
			LOGGER4J.debug("Load dir {}" , dirIn);
			listeFichiersIn = managerFile.filesListFilterOnDirectoryAndSubDirectory(dirIn.getPath(), filter);
			sortListMusicFile(listeFichiersIn);
		}
	}
	
}
