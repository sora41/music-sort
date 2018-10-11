package repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import repository.IRepositoryFile;

public class RepositoryNativeFile implements IRepositoryFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoryNativeFile.class.getName());

	@Override
	public void delete(String pathFileName) throws IOException {
		File deleteFile = new File(pathFileName);
		if (deleteFile.exists()) {

			if (deleteFile.delete() == true) {
				LOGGER4J.trace("supresion " + pathFileName + " reussi");

			} else {
				LOGGER4J.fatal("supresion " + pathFileName + " echec");
			}
		} else {
			FileNotFoundException e = new FileNotFoundException("fichier a suprimer existe pas " + pathFileName);

			throw e;
		}
	}

	/* suprime le fichier et tout ceux qu'il contient */
	@Override
	public void recursiveDelete(String pathFileName) throws IOException {
		LOGGER4J.trace("lancement delete recursif sur le repertoire " + pathFileName);

		File fileItem, file;
		String pahtItem;
		ArrayList<String> fileList;

		file = new File(pathFileName);
		boolean isDir = file.isDirectory();

		if (isDir == true) {
			fileList = listeFilesOnDirectory(pathFileName);

			int taille = fileList.size();

			LOGGER4J.trace("contient" + taille);
			for (int i = 0; i < fileList.size(); i++) {
				pahtItem = pathFileName + File.separator + fileList.get(i);
				fileItem = new File(pahtItem);
				recursiveDelete(pahtItem);
			}
		}
		if (pathFileName.contains(".gitkeep") == false) {
			LOGGER4J.trace("delete" + pathFileName);
			delete(pathFileName);
		} else {
			LOGGER4J.trace("ignore " + pathFileName);
		}
	}

	

	/*
	 * vide le repertoire et tout ceux qu'il contient sauf les fichier gitkeep
	 */
	@Override
	public void cleanDirectory(String pathFileName) throws IOException {
		LOGGER4J.trace("lancement clean sur le repertoire " + pathFileName);

		ArrayList<String> fileList = listeFilesOnDirectory(pathFileName);

		String pahtItem;
		int taille = fileList.size();

		LOGGER4J.trace("contient" + taille);
		for (int i = 0; i < fileList.size(); i++) {
			pahtItem = pathFileName + File.separator + fileList.get(i);
			recursiveDelete(pahtItem);
		}

	}

	@Override
	public void moveFile(String orginalePathName, String finalPahtName) throws IOException {
		File originsfile = new File(orginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(finalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.move(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);

		}
	}

	@Override
	public void copyFile(String OrginalePathName, String FinalPahtName) throws IOException {
		File originsfile = new File(OrginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(FinalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.copy(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);
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
	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {

		ArrayList<String> nomFichiers = new ArrayList<>();
		File repertoire = new File(dirName);

		if (repertoire.exists()) {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repertoire.toPath());
			for (Path path : directoryStream) {
				nomFichiers.add(path.getFileName().toString());
			}
		} else {
			FileNotFoundException e = new FileNotFoundException(
					"repertoire: " + repertoire.getAbsolutePath() + " introuvable");
			throw e;
		}
		return nomFichiers;
	}


}
