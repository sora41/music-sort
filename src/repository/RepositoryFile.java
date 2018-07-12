package repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class RepositoryFile implements IRepositoryFile {

	@Override
	public void delete(String pathFileName) {
		File deleteFile = new File(pathFileName);
		if (deleteFile.exists()) {
			deleteFile.delete();

		} else {
			// TODO Raise exception
			System.out.println("fichier a supermier existe pas " + pathFileName);
		}

	}

	@Override
	public void recursiveDelete(String pathFileName) {

		ArrayList<String> fileList = listeFilesOnDirectory(pathFileName);

		File fileItem;
		String pahtItem;

		for (int i = 0; i < fileList.size(); i++) {
			pahtItem = pathFileName + File.separator + fileList.get(i);
			fileItem = new File(pahtItem);
			if (fileItem.isDirectory()) {
				if (fileItem.list().length > 0) {
					recursiveDelete(pahtItem);
				}
			}
			if (pahtItem.contains(".gitkeep") == false) {
				delete(pahtItem);
			}
		}

	}

	@Override
	public void move(String OrginalePathName, String FinalPahtName) throws IOException {
		File f = new File(OrginalePathName);
		if (f.exists()) {
			File f2 = new File(FinalPahtName);

			Path pf = f.toPath();
			Path pf2 = f2.toPath();

			// String debugPF = pf.toString();
			// String debugPF2 = pf2.toString();

			Files.move(pf, pf2, StandardCopyOption.REPLACE_EXISTING);
		}

	}

	@Override
	public void copy(String OrginalePathName, String FinalPahtName) throws IOException {
		File f = new File(OrginalePathName);
		if (f.exists()) {
			File f2 = new File(FinalPahtName);

			Path pf = f.toPath();
			Path pf2 = f2.toPath();

			Files.copy(pf, pf2, StandardCopyOption.REPLACE_EXISTING);
		}

	}

	@Override
	public boolean validateDirectory(File dir) {
		boolean resultas = false;
		if (dir != null)
			if (dir.exists())
				if (dir.isDirectory())
					resultas = true;
		return resultas;

	}

	@Override
	public ArrayList<String> listeFilesOnDirectory(String dirName) {

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

}
