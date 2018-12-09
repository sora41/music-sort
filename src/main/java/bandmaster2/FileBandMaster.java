package bandMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import repository.IRepositoryFile;
import repository.file.RepositoryWalkingFile;
import repository.file.RepositoyApacheFile;
/**
 *  bandMaster file music 
 * */
public abstract class FileBandMaster {
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(FileBandMaster.class.getName());
	/**
	 * nom du repertoire de sorti des fichier non suporté par l'application 
	 * */
	private static final String DIR_NOT_SUPORTED = "NotSuported";
	/**
	 * nom du repertoire de sorti erreur 
	 * */
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
	 * repertoire de sorti des fichier en erreur dans la couche metier de l'application 
	 **/
	protected File dirError;
	/**
	 * manager de fichier
	 */
	protected IRepositoryFile managerFile;

	public FileBandMaster(String dirIn, String dirOut, String dirSorted) {
		this.dirIn = new File(dirIn);
		this.dirOut = new File(dirOut);
		this.dirError = new File(dirOut + File.separator + DIR_ERROR);
		this.dirNotSuported = new File(dirOut + File.separator + DIR_NOT_SUPORTED);
		this.dirSorted = new File(dirSorted);
		this.managerFile = new RepositoyApacheFile();
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

	/**
	 * 
	 * initialise le repertoire d'entré de l'application en copiant les fichier
	 * un a un du repertoire back ver le repertoire inMusic
	 */
	public void initDirectorieIn(String backDir) throws IOException {
		File back = new File(backDir);
		ArrayList<String> listeFichiersBack;
		String fileNameitem = "";
		String newPahtFileItem = "";
		LOGGER4J.debug("initalisation  " + backDir);
		// verfie si le repertoire back existe
		if (managerFile.validateDirectory(back) == true) {
			listeFichiersBack = managerFile.listeFilesOnDirectoryAndSubDirectory(back.getPath());
			// verifie si la liste de fichier existe et si elle contient des
			// elements
			if (null != listeFichiersBack && listeFichiersBack.size() > 0) {
				for (int i = 0; i < listeFichiersBack.size(); i++) {
					// recuperation du nom du fichier
					fileNameitem = listeFichiersBack.get(i);
					// creation de la string path de destination 
					newPahtFileItem = fileNameitem.substring(backDir.length()+1);
					// ignore gitkeep
					if (newPahtFileItem.contains(".gitkeep") == false) {
						try {
							managerFile.copyFile(fileNameitem,  dirIn.getPath() + File.separator +newPahtFileItem);
						} catch (IOException e) {
							LOGGER4J.error("imposible de deplacer le Fichier " + fileNameitem + "du repertoir:" + dirIn
									+ " vers le repertoire " + dirOut);
							LOGGER4J.error("error" + e.getMessage());
						}
					}
				}
			}
		} else {
			FileNotFoundException e = new FileNotFoundException(
					"repertoire: " + back.getAbsolutePath() + " introuvable");
			throw e;
		}
	}
	/**
	 *  run sort file
	 * */
	public abstract void runSortFile() throws IOException;
}
