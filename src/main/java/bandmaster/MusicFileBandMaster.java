package bandmaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.tag.TagNotFoundException;

import constant.MusicExtention;
import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;


/**
 * bandMaster file music
 */
public class MusicFileBandMaster extends FileBandMaster {

	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicFileBandMaster.class.getName());

	/**
	 * Creates a new MusicFileBandMaster object with diretory In ,diretory out
	 * and diretory sorted set . initliatise tne RepositoryMusiqueFile
	 */
	public MusicFileBandMaster(String dirIn, String dirOut, String dirSorted)
			throws SecurityException, FileNotFoundException, IOException {
		super(dirIn, dirOut, dirSorted);
	}

	private String ExtractExtention(String fileName) throws Exception {
		String extention = "";
		if ((fileName != null) && fileName.isEmpty() == false) {
			if (fileName.contains(".")) {
				int size = fileName.length();
				extention = fileName.substring(size - 3);
				extention = extention.toLowerCase();
			} else {
				Exception eSansExtention = new Exception(fileName + " fichier sans extention");
				throw eSansExtention;
			}
		} else {
			Exception eChaineVide = new Exception("la chaine fileName est vide ");
			throw eChaineVide;
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
		IRepositoryMusicFile repositoryMusic = null;
		String extention = this.ExtractExtention(pathFileName);
		MusicExtention enumExention = MusicExtention.valueOf(extention.toUpperCase());
		
		Class<?> repoClass =Class.forName( enumExention.getRepoClass());
		
		repositoryMusic =  (IRepositoryMusicFile) repoClass.newInstance();
	
		MusicDto dto = repositoryMusic.getDataToMusicFile(pathFileName);
		if (null == dto) {
			TagNotFoundException e = new TagNotFoundException("Tag not found ");
			throw e;
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
			managerFile.moveFile(pathFile, sortedTarget);

		} else {
			TagNotFoundException e = new TagNotFoundException("no artiste");
			throw e;
		}
	}

	/**
	 * sort music file by author and album
	 */
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
				managerFile.moveFile(pathFile, sortedTarget);
			} else {
				TagNotFoundException e = new TagNotFoundException("no album");
				throw e;
			}
		} else {
			TagNotFoundException e = new TagNotFoundException("no artiste");
			throw e;
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
			managerFile.moveFile(pathFile, sortedTarget);
		} else {
			TagNotFoundException e = new TagNotFoundException("no album");
			throw e;
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
					LOGGER4J.trace("sort" + i + "-" + (tabSize - 1));
					sortMusicFile(fileNameitem);
				}
			}
		}
		if (zeroFile == true) {
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
			LOGGER4J.error("Fichier : " + fileName + "-" + sortException.getMessage(), sortException.getClass().getName(), sortException.getStackTrace());
			try {
				managerFile.moveFile(fileName, dirError + File.separator + fileName);
			} catch (IOException moveException) {
				String erroMgs = "imposible de deplacer le Fichier " + fileName + "du repertoir:" + dirIn
						+ " vers le repertoire " + dirNotSuported;
				LOGGER4J.error(erroMgs + "-" + moveException.getMessage(), moveException.getClass().getName(), moveException.getStackTrace());
			}
		}
	}

	/** format string use for create directori */
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

	/** launch sort procedure */
	public void runSortFile() throws IOException {
		ArrayList<String> listeFichiersIn;
		MusicExtention[] filter = MusicExtention.values();
		int tabSize = 0;
		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {
			LOGGER4J.debug("Load dir" + dirIn);
			listeFichiersIn = managerFile.filesListFilterOnDirectoryAndSubDirectory(dirIn.getPath(), filter);
			sortListMusicFile(listeFichiersIn);
		}
	}
}
