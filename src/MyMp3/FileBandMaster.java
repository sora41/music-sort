package MyMp3;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public abstract class FileBandMaster {

	protected File dirIn;
	protected File dirOut;
	protected File dirSorted;

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

	protected boolean validateDirectory(File dir) {
		boolean resultas = false;
		if (dir != null)
			if (dir.exists())
				if (dir.isDirectory())
					resultas = true;
		return resultas;
	}

	protected boolean validateDirectorys() {
		boolean resultas = true;

		if (!validateDirectory(dirIn))
			resultas = dirIn.mkdir();

		if ((!validateDirectory(dirOut)) && (resultas))
			resultas = dirOut.mkdir();

		if ((!validateDirectory(dirSorted)) && (resultas))
			resultas = dirSorted.mkdir();

		return resultas;
	}

	public ArrayList<String> getListeFiles(String dirName) {
		//System.out.println("getlistefiles File band");
		//System.out.println("get liste files");
		ArrayList<String> nomFichiers = new ArrayList<>();
		File repertoire = new File(dirName);
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repertoire.toPath())) {
			for (Path path : directoryStream) {
				nomFichiers.add(path.getFileName().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nomFichiers;
	}

	public void deleteFile(String nameFile) {
		// System.out.println("deleteFile");
		File deleteFile = new File(nameFile);
		if (deleteFile.exists()) {
			// System.out.println("fichier " + nameFile + " existe");
			deleteFile.delete();

		} else {
			System.out.println("fichier a supermier existe pas " + nameFile);
		}
	}

	public void deleteFileOndirectory(String dirName) {
		//System.out.println("deleteFileOndirectory");
		ArrayList<String> fileList = getListeFiles(dirName);
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
				deleteFile(pahtItem);
			}
		}
	}

	public void resetDirectories() {
		//System.out.println("resetDirectories");
		deleteFileOndirectory(this.dirIn.getPath());
		deleteFileOndirectory(this.dirOut.getPath());
		deleteFileOndirectory(this.dirSorted.getPath());
	}

	public void initDirectorieIn(String backDir) {
		File back = new File(backDir);
		ArrayList<String> listeFichiersBack;
		String fileNameitem = "";
		String pahtFileItem = "";

		if (validateDirectory(back) == true) {
			listeFichiersBack = getListeFiles(back.getPath());
			if (listeFichiersBack.size() > 0) {
				for (int i = 0; i < listeFichiersBack.size(); i++) {
					fileNameitem = listeFichiersBack.get(i);
					pahtFileItem = backDir + File.separator + fileNameitem;
					if (pahtFileItem.contains(".gitkeep") == false) {

						try {
							copyFile(pahtFileItem, dirIn.getPath() + File.separator + fileNameitem);
						} catch (IOException e) {
							System.err.println("imposible de deplacer le Fichier " + fileNameitem);
							System.err.println("du repertoir:" + dirIn + " vers le repertoire " + dirOut);
						}
					}

				}
			}
		}
	}

	public void moveFile(String OrginaleName, String FinalName) throws IOException {
		File f = new File(OrginaleName);
		if (f.exists()) {
			File f2 = new File(FinalName);

			Path pf = f.toPath();
			Path pf2 = f2.toPath();

			String debugPF = pf.toString();
			String debugPF2 = pf2.toString();

			Files.move(pf, pf2, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public void copyFile(String OrginaleName, String FinalName) throws IOException {
		File f = new File(OrginaleName);
		if (f.exists()) {
			File f2 = new File(FinalName);

			Path pf = f.toPath();
			Path pf2 = f2.toPath();

			Files.copy(pf, pf2, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public abstract void runSortFile();
}
