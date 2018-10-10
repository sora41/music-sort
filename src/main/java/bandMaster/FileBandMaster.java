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

public abstract class FileBandMaster {

	private static final Logger LOGGER4J = LogManager.getLogger(FileBandMaster.class.getName());
	private static final String DIR_NOT_SUPORTED = "NotSuported";
	private static final String DIR_ERROR = "Erro";
	
	protected File dirIn;
	protected File dirOut;
	protected File dirSorted;
	protected File dirNotSuported;
	protected File dirError;
	protected IRepositoryFile managerFile;

	public FileBandMaster(String dirIn, String dirOut, String dirSorted) {
		this.dirIn = new File(dirIn);
		this.dirOut = new File(dirOut);
		this.dirError = new File(dirOut + File.separator + DIR_ERROR);
		this.dirNotSuported = new File(dirOut + File.separator + DIR_NOT_SUPORTED);
		this.dirSorted = new File(dirSorted);
		//this.managerFile = new RepositoryWalkingFile();
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

	public void resetDirectories() throws IOException {
		managerFile.cleanDirectory(this.dirIn.getPath());
		managerFile.cleanDirectory(this.dirOut.getPath());
		managerFile.cleanDirectory(this.dirSorted.getPath());
	}

	public void initDirectorieIn(String backDir) throws IOException {
		File back = new File(backDir);
		ArrayList<String> listeFichiersBack;
		String fileNameitem = "";
		String pahtFileItem = "";
		LOGGER4J.debug("initalisation  " + backDir);
		if (managerFile.validateDirectory(back) == true) {
			listeFichiersBack = managerFile.listeFilesOnDirectory(back.getPath());
			if (listeFichiersBack.size() > 0) {
				for (int i = 0; i < listeFichiersBack.size(); i++) {
					fileNameitem = listeFichiersBack.get(i);
					pahtFileItem = backDir + File.separator + fileNameitem;
					if (pahtFileItem.contains(".gitkeep") == false) {
						try {
							managerFile.copy(pahtFileItem, dirIn.getPath() + File.separator + fileNameitem);
						} catch (IOException e) {
							LOGGER4J.error("imposible de deplacer le Fichier " + fileNameitem +"du repertoir:" + dirIn + " vers le repertoire " + dirOut);
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

	public abstract void runSortFile() throws IOException;
}
