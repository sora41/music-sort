package repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constant.MusicExtention;
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
		boolean isDiretory = file.isDirectory();

		if (isDiretory == true) {
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
		int size = fileList.size();
		LOGGER4J.trace("contient" + size);
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
	public boolean validateDirectory(File directory) {
		boolean resultas = false;
		if (directory != null)
			if (directory.exists())
				if (directory.isDirectory())
					resultas = true;
		return resultas;
	}

	@Override
	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {

		ArrayList<String> nomFichiers = null;
		File directory = new File(dirName);

		if (directory.exists()) {
			nomFichiers = new ArrayList<>();
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory.toPath());
			for (Path path : directoryStream) {
				nomFichiers.add(path.toString());
			}
		} else {
			FileNotFoundException e = new FileNotFoundException(
					"repertoire: " + directory.getAbsolutePath() + " introuvable");
			throw e;
		}
		return nomFichiers;
	}

	@Override
	public ArrayList<String> listeFilesOnDirectoryAndSubDirectory(String dirName) throws IOException {
		ArrayList<String> finalPathFileList = null;
		ArrayList<String> subPathFileList = null;
		File directory = new File(dirName);
		String fileNameItem;

		if (directory.exists()) {
			finalPathFileList = new ArrayList<>();
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory.toPath());
			for (Path path : directoryStream) {
				fileNameItem = path.getFileName().toString();
				if (path.toFile().isDirectory()) {
					subPathFileList = listeFilesOnDirectoryAndSubDirectory(dirName + "\\" + fileNameItem);

					if ((null != subPathFileList)) {
						if (subPathFileList.size() > 0) {

							if (finalPathFileList.addAll(subPathFileList) == false) {
								IOException eCollectionSet = new IOException(
										"erro affection apelle recurisif sur le fichier" + fileNameItem
												+ "dans le repertoire" + dirName);
								throw eCollectionSet;
							}
						}
					}

				} else {
					finalPathFileList.add(path.toString());
				}
			}
		} else {
			FileNotFoundException eFileNotFound = new FileNotFoundException(
					"repertoire: " + directory.getAbsolutePath() + " introuvable");
			throw eFileNotFound;
		}
		return finalPathFileList;
	}

	@Override
	public ArrayList<String> filesListFilterOnDirectoryAndSubDirectory(String dirName, MusicExtention[] filters)
			throws IOException {

		ArrayList<String> finalPathFileList = null;
		ArrayList<String> subPathFileList = null;
		File directory = new File(dirName);
		String fileNameItem;

		if (directory.exists()) {
			finalPathFileList = new ArrayList<>();
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory.toPath());
			for (Path path : directoryStream) {
				fileNameItem = path.getFileName().toString();
				if (path.toFile().isDirectory()) {
					// rapelle la fonction pour recupere la liste des repertoire du sous repertoire 
					subPathFileList = filesListFilterOnDirectoryAndSubDirectory(dirName + "\\" + fileNameItem, filters);

					if ((null != subPathFileList)) {
						if (subPathFileList.size() > 0) {
							if (finalPathFileList.addAll(subPathFileList) == false) {
								IOException eCollectionSet = new IOException(
										"erro affection apelle recurisif sur le fichier" + fileNameItem
												+ "dans le repertoire" + dirName);
								throw eCollectionSet;
							}
						}
					}
				} else {
					
					if (filters != null && filters.length > 0) {
						String pathFileStr = path.toString();
						boolean add = false;
						// parcour des filtre 
						for (MusicExtention musicExtention : filters) {
							if ((pathFileStr.contains(musicExtention.getValue()))||(pathFileStr.contains(musicExtention.getValue().toUpperCase()))) {
								add = true;
							}
						}
						
						if (add == true)
							finalPathFileList.add(pathFileStr);
							
					} else {
						finalPathFileList.add(path.toString());
					}
				}
			}
		} else {
			FileNotFoundException eFileNotFound = new FileNotFoundException(
					"repertoire: " + directory.getAbsolutePath() + " introuvable");
			throw eFileNotFound;
		}
		return finalPathFileList;

	}
}
