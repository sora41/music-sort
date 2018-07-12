package bandMaster;

import java.io.File;
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
	protected IRepositoryFile managerFile = new RepositoryFile();

	public FileBandMaster(String dirIn, String dirOut, String dirSorted) {
		this.dirIn = new File(dirIn);
		this.dirOut = new File(dirOut);
		this.dirSorted = new File(dirSorted);
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

	protected boolean validateDirectorys() {
		boolean resultas = true;

		if (!managerFile.validateDirectory(dirIn))
			resultas = dirIn.mkdir();

		if ((!managerFile.validateDirectory(dirOut)) && (resultas))
			resultas = dirOut.mkdir();

		if ((!managerFile.validateDirectory(dirSorted)) && (resultas))
			resultas = dirSorted.mkdir();

		return resultas;
	}
	/*
	public void deleteFileOndirectory(String dirName) {
		// System.out.println("deleteFileOndirectory");
		ArrayList<String> fileList = managerFile.listeFilesOnDirectory(dirName);
		// System.out.println("taille liste " + fileList.size());
		File fileItem;
		String pahtItem;

		for (int i = 0; i < fileList.size(); i++) {
			pahtItem = dirName + File.separator + fileList.get(i);
			fileItem = new File(pahtItem);
			if (fileItem.isDirectory()) {
				if (fileItem.list().length > 0) {
					deleteFileOndirectory(pahtItem);
				}
			}
			if (pahtItem.contains(".gitkeep") == false) {
				managerFile.delete(pahtItem);
			}
		}
	}*/

	public void resetDirectories() {
		// System.out.println("resetDirectories");
		managerFile.recursiveDelete(this.dirIn.getPath());
		managerFile.recursiveDelete(this.dirOut.getPath());
		managerFile.recursiveDelete(this.dirSorted.getPath());
	}

	public void initDirectorieIn(String backDir) {
		File back = new File(backDir);
		ArrayList<String> listeFichiersBack;
		String fileNameitem = "";
		String pahtFileItem = "";

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
							System.err.println("imposible de deplacer le Fichier " + fileNameitem);
							System.err.println("du repertoir:" + dirIn + " vers le repertoire " + dirOut);
						}
					}

				}
			}
		}
	}

	public abstract void runSortFile();
}
