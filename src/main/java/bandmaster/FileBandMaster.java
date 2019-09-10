package bandmaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constant.MusicExtention;
import repository.IRepositoryFile;
import repository.file.RepositoryApacheFile;

/**
 * bandMaster file music
 */
public abstract class FileBandMaster {
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(FileBandMaster.class.getName());
	/**
	 * nom du repertoire de sorti des fichier non suporté par l'application
	 */
	private static final String DIR_NOT_SUPORTED = "NotSuported";
	/**
	 * nom du repertoire de sorti erreur
	 */
	private static final String DIR_ERROR = "Erro";
	/**
	 * repertoire d'entre des musique a trie
	 **/
	protected File dirIn;
	/**
	 * repertoire de sorti des musiques non triable
	 **/
	protected File dirOut;
	/**
	 * repertoire de sorti des musique trier
	 **/
	protected File dirSorted;
	/**
	 * repertoire de sorti des fichier non suporter (different format )
	 **/
	protected File dirNotSuported;
	/**
	 * repertoire de sorti des fichier en erreur dans la couche metier de
	 * l'application
	 **/
	protected File dirError;
	/**
	 * manager de fichier
	 */
	protected IRepositoryFile managerFile;

	public FileBandMaster(String dirIn, String dirOut, String dirSorted) {
		this.dirIn = new File(dirIn);
		this.dirOut = new File(dirOut);

		this.dirError = new File(buildNameFile(dirOut, DIR_ERROR));
		this.dirNotSuported = new File(buildNameFile(dirOut, DIR_NOT_SUPORTED));

		this.dirSorted = new File(dirSorted);
		this.managerFile = new RepositoryApacheFile();
	}

	protected String buildNameFile(String dirNameContainer, String lastDirName) {
		StringBuilder fileName = new StringBuilder(dirNameContainer);
		fileName.append(File.separator);
		fileName.append(lastDirName);
		return fileName.toString();
	}

	public File getDirIn() {
		return dirIn;
	}

	public void setDirIn(File dirIn) {
		this.dirIn = dirIn;
	}

	public File getDirOut() {
		return dirOut;
	}

	public void setDirOut(File dirOut) {
		this.dirOut = dirOut;
	}

	public File getDirSorted() {
		return dirSorted;
	}

	public void setDirSorted(File dirSorted) {
		this.dirSorted = dirSorted;
	}

	public File getDirNotSuported() {
		return dirNotSuported;
	}

	public void setDirNotSuported(File dirNotSuported) {
		this.dirNotSuported = dirNotSuported;
	}

	public File getDirError() {
		return dirError;
	}

	public void setDirError(File dirError) {
		this.dirError = dirError;
	}

	/**
	 * verifie si les repertoires utilise par l'application existe
	 **/
	protected boolean validateDirectorys() {
		boolean resultas = true;

		if (!managerFile.validateDirectory(dirIn))
			resultas = dirIn.mkdir();

		if ((!managerFile.validateDirectory(dirOut)) && (resultas))
			resultas = dirOut.mkdir();

		if ((!managerFile.validateDirectory(dirError)) && (resultas))
			resultas = dirError.mkdir();

		if ((!managerFile.validateDirectory(dirNotSuported)) && (resultas))
			resultas = dirNotSuported.mkdir();

		if ((!managerFile.validateDirectory(dirSorted)) && (resultas))
			resultas = dirSorted.mkdir();

		return resultas;
	}

	/**
	 * vide les repertoire inmusi, outmusic,sortedmusic
	 */
	public void resetDirectories() throws IOException {
		managerFile.cleanDirectory(this.dirIn.getPath());
		managerFile.cleanDirectory(this.dirOut.getPath());
		managerFile.cleanDirectory(this.dirSorted.getPath());
	}

	private void copyListFileOnDIR(String strDir, ArrayList<String> listFiles) {

		String fileNameitem = "";
		String newPahtFileItem = "";

		if (null != listFiles && !listFiles.isEmpty()) {

			for (int i = 0; i < listFiles.size(); i++) {
				// recuperation du nom du fichier
				fileNameitem = listFiles.get(i);
				// creation de la string path de destination
				newPahtFileItem = fileNameitem.substring(strDir.length() + 1);
				// ignore gitkeep
				if (!newPahtFileItem.contains(".gitkeep")) {
					try {
						managerFile.copyFile(fileNameitem, buildNameFile(dirIn.getPath(), newPahtFileItem));
					} catch (IOException e) {
						StringBuilder exceptionString = new StringBuilder("imposible de deplacer le Fichier ");
						exceptionString.append(fileNameitem);
						exceptionString.append(" introuvable");
						exceptionString.append("du repertoir:");
						exceptionString.append(dirIn);
						exceptionString.append(" vers le repertoire ");
						exceptionString.append(dirOut);

						LOGGER4J.error(exceptionString.toString());
						StringBuilder exceptionString2 = new StringBuilder(("error"));
						exceptionString2.append(e.getMessage());
						LOGGER4J.error(exceptionString2.toString());
					}
				}
			}
		}
	}

	/**
	 * 
	 * initialise le repertoire d'entré de l'application en copiant les fichier
	 * un a un du repertoire back ver le repertoire inMusic
	 */
	public void initDirectoryIn(String strBackDir) throws IOException {
		File back = new File(strBackDir);
		ArrayList<String> listeFichiersBack;
		StringBuilder logString = new StringBuilder("initalisation : ");
		logString.append(strBackDir);

		LOGGER4J.debug(logString);
		// verfie si le repertoire back existe
		if (managerFile.validateDirectory(back)) {
			listeFichiersBack = managerFile.listeFilesOnDirectoryAndSubDirectory(back.getPath());
			// verifie si la liste de fichier existe et si elle contient des
			// elements
			this.copyListFileOnDIR(strBackDir, listeFichiersBack);
		} else {
			StringBuilder exceptionString = new StringBuilder("repertoire: ");
			exceptionString.append(back.getAbsolutePath());
			exceptionString.append(" introuvable");

			throw new FileNotFoundException(exceptionString.toString());
		}
	}

	/**
	 * run sort file
	 */
	public abstract void runSortFile() throws IOException;

	public int getCountFileDirIn(MusicExtention[] filters) throws IOException {
		LOGGER4J.trace("demarage getCountFileDirIn");
		
		ArrayList<String> listeFichiersIn = new ArrayList<String>();
		// etape 1 tester sur les repertoire suivant existe
		// sinon les cree
		if (validateDirectorys()) {
			// modifier pour revoyer uniquement le nombre de fichier et pas la liste
			listeFichiersIn = managerFile.filesListFilterOnDirectoryAndSubDirectory(dirIn.getPath(), filters);
			
		}
		int countfiles =  listeFichiersIn.size();
		LOGGER4J.trace("fin getCountFileDirIn");
		return countfiles;
	}
}
