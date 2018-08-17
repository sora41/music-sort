package bandMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import repository.IRepositoryFile;
import repository.RepositoryFile;

public abstract class FileBandMaster {

	protected File dirIn;
	protected File dirOut;
	protected File dirSorted;
	private static final String DIR_NOT_SUPORTED = "NotSuported";
	private static final String DIR_ERROR = "Erro";
	protected File dirNotSuported;
	protected File dirError;
	protected IRepositoryFile managerFile;

	public FileBandMaster(String dirIn, String dirOut, String dirSorted) {
		this.dirIn = new File(dirIn);
		this.dirOut = new File(dirOut);
		this.dirError = new File(dirOut + File.separator + DIR_ERROR);
		this.dirNotSuported = new File(dirOut + File.separator + DIR_NOT_SUPORTED);
		this.dirSorted = new File(dirSorted);
		this.managerFile = new RepositoryFile();
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
		managerFile.recursiveDelete(this.dirIn.getPath());
		managerFile.recursiveDelete(this.dirOut.getPath());
		managerFile.recursiveDelete(this.dirSorted.getPath());
	}

	public void initDirectorieIn(String backDir) throws IOException {
		File back = new File(backDir);
		ArrayList<String> listeFichiersBack;
		String fileNameitem = "";
		String pahtFileItem = "";
		System.out.println("initalisation  " + backDir);
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
							System.out.println("imposible de deplacer le Fichier " + fileNameitem);
							System.out.println("du repertoir:" + dirIn + " vers le repertoire " + dirOut);
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