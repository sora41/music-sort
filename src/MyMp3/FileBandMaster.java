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

	protected void DeleteFile(String nameFile) {
		File deleteFile = new File(nameFile);

		if (deleteFile.exists())
			if (deleteFile.isFile())
				deleteFile.delete();
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

	public abstract void runSortFile();
}
