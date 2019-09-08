package bandmaster;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.tag.TagNotFoundException;

import constant.MusicExtention;
import datatransfert.MusicDto;
import observer.Observable;
import observer.Observateur;
import repository.IRepositoryMusicFile;
import repository.builder.BuilderMusicRepository;

/**
 * bandMaster file music
 */
public class MusicFileBandMaster extends FileBandMaster implements Observable {

	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicFileBandMaster.class.getName());

	private static final String ECHEC_CREATE_DIR = "echec creation repertoire";
	private static final String LOG_FILE_SEPARATOR ="-";

	/**
	 * Creates a new MusicFileBandMaster object with diretory In ,diretory out and
	 * diretory sorted set . initliatise tne RepositoryMusiqueFile
	 */
	public MusicFileBandMaster(String dirIn, String dirOut, String dirSorted) throws IOException {
		super(dirIn, dirOut, dirSorted);
	}

	/**
	 * 
	 */
	private ArrayList<Observateur> listObservers = new ArrayList<Observateur>();

	private String extractExtention(String fileName) throws Exception {
		String extention = "";
		if ((fileName != null) && !fileName.isEmpty()) {
			if (fileName.contains(".")) {
				int size = fileName.length();
				extention = fileName.substring(size - 3);
				extention = extention.toLowerCase();
			} else {
				StringBuilder exceptionString = new StringBuilder(fileName);
				exceptionString.append(" fichier sans extention");
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
	 * @param pathFileName file String path
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

			autorDir = new File(dirSorted.getPath() + File.separator + songAuthor);
			if (!managerFile.validateDirectory(autorDir) && !autorDir.mkdir()) {
				throw new IOException(ECHEC_CREATE_DIR + autorDir.getPath());
			}
			pathAutorDir = autorDir.getPath();
			sortedTarget = pathAutorDir + File.separator + fileName;
			managerFile.moveFile(pathFile, sortedTarget);

		} else {
			throw new TagNotFoundException("no artiste");
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

		if ((songAuthor != null) && (!songAuthor.isEmpty())) {
			if ((songAlbum != null) && (!songAlbum.isEmpty())) {
				
				StringBuilder tracerLogger = new StringBuilder(songAuthor);
				tracerLogger.append(LOG_FILE_SEPARATOR);
				tracerLogger.append(songAlbum);
				tracerLogger.append(LOG_FILE_SEPARATOR);
				tracerLogger.append(fileName);
				
				LOGGER4J.trace(tracerLogger.toString());
				
				autorDir = new File(dirSorted.getPath() + File.separator + songAuthor);
				if (!managerFile.validateDirectory(autorDir) && !autorDir.mkdir()) {
					throw new IOException(ECHEC_CREATE_DIR + autorDir.getPath());
				}

				albumDir = new File(dirSorted.getPath() + File.separator + songAuthor + File.separator + songAlbum);
				if (!managerFile.validateDirectory(albumDir) && !albumDir.mkdir()) {
					throw new IOException(ECHEC_CREATE_DIR + albumDir.getPath());
				}
				
				pathAlbumDir = albumDir.getPath();
				sortedTarget = pathAlbumDir + File.separator + fileName;
				managerFile.moveFile(pathFile, sortedTarget);
			} else {
				throw new TagNotFoundException("no album");
			}
		} else {
			throw new TagNotFoundException("no artiste");
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

			albumDir = new File(dirSorted.getPath() + File.separator + songAlbum);
			if (!managerFile.validateDirectory(albumDir) && !albumDir.mkdir()) {
				throw new IOException("echec creation repertoire " + albumDir.getPath());
			}
			pathAlbumDir = albumDir.getPath();
			sortedTarget = pathAlbumDir + File.separator + fileName;
			managerFile.moveFile(pathFile, sortedTarget);
		} else {
			throw new TagNotFoundException("no album");
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
				LOGGER4J.trace("contains files : ", tabSize);
				for (int i = 0; i < tabSize; i++) {
					fileNameitem = listeFichiersIn.get(i);
					LOGGER4J.trace("sort" + i + LOG_FILE_SEPARATOR + (tabSize - 1));
					sortMusicFile(fileNameitem);
					updateObservateur(i, tabSize - 1);
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
			LOGGER4J.error("Fichier : " + fileName + "-" + sortException.getMessage(),
					sortException.getClass().getName(), sortException.getStackTrace());
			try {
				managerFile.moveFile(fileName, dirError + File.separator + fileName);
			} catch (IOException moveException) {
				String erroMgs = "imposible de deplacer le Fichier " + fileName + "du repertoir:" + dirIn
						+ " vers le repertoire " + dirNotSuported;
				LOGGER4J.error(erroMgs + LOG_FILE_SEPARATOR + moveException.getMessage(), moveException.getClass().getName(),
						moveException.getStackTrace());
			}
		}
	}

	/** format string use for create directori */
	private String applyFormatRuleGenerale(String raw) {
		String formatResult = "";
		// supresion espace devan et deriere
		formatResult = raw.trim();

		// replacement des é en e
		formatResult = formatResult.replaceAll("é", "e");
		formatResult = formatResult.replaceAll("è", "e");
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
		// supresion des '
		formatResult = formatResult.replaceAll("'", "");

		return formatResult;
	}

	/** launch sort procedure */
	public void runSortFile() throws IOException {
		ArrayList<String> listeFichiersIn;
		MusicExtention[] filter = MusicExtention.values();
		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {
			LOGGER4J.debug("Load dir" + dirIn);
			listeFichiersIn = managerFile.filesListFilterOnDirectoryAndSubDirectory(dirIn.getPath(), filter);
			sortListMusicFile(listeFichiersIn);
		}
	}

	public void addObservateur(Observateur obs) {
		this.listObservers.add(obs);

	}

	public void updateObservateur(int enCours, int fin) {
		for (Observateur obs : this.listObservers) {
			obs.update(enCours, fin);
		}
	}

	public void delObservateur() {
		this.listObservers = new ArrayList<Observateur>();

	}
}
