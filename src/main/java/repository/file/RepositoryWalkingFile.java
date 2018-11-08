package repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constant.MusicExtention;
import repository.IRepositoryFile;

/**
 * repository file implemented with lib java.nio.file new walking methode
 */
public class RepositoryWalkingFile implements IRepositoryFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoryWalkingFile.class.getName());


	public void delete(String pathFileName) throws IOException {
		LOGGER4J.trace("supresion " + pathFileName);
		Path directory = Paths.get(pathFileName);

		if (Files.deleteIfExists(directory)) {
			LOGGER4J.trace("supresion " + pathFileName + " reussi");
		} else {
			LOGGER4J.trace("supresion " + pathFileName + " echec");
		}
	}


	public void cleanDirectory(String pathFileName) throws IOException {
		LOGGER4J.trace("start cleanDirectory" + pathFileName);
		ArrayList<String> fileList = listeFilesOnDirectory(pathFileName);
		String pahtItem;
		for (int i = 0; i < fileList.size(); i++) {
			pahtItem = pathFileName + File.separator + fileList.get(i);
			recursiveDelete(pahtItem);
		}
		LOGGER4J.trace("end cleanDirectory");
	}


	public void recursiveDelete(String pathFileName) throws IOException {
		LOGGER4J.trace("start recursiveDelete" + pathFileName);
		Path directory = Paths.get(pathFileName);
		LOGGER4J.trace("is a directory " + directory.toFile().isDirectory());

		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				LOGGER4J.trace("start FileVisitResult" + file.toString());
				if (file.endsWith(".gitkeep") == false) {
					LOGGER4J.trace("on gitkeep == false");
					Files.delete(file);
				}
				LOGGER4J.trace("end FileVisitResult");
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				LOGGER4J.trace("start FileVisitResult" + dir.toString());
				Files.delete(dir);
				LOGGER4J.trace("end FileVisitResult");
				return FileVisitResult.CONTINUE;
			}

		});
		LOGGER4J.trace("end recursiveDelete");
	}


	public void moveFile(String orginalePathName, String finalPahtName) throws IOException {
		LOGGER4J.trace("Start move");
		File originsfile = new File(orginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(finalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.move(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);
		}
		LOGGER4J.trace("end move");
	}


	public void copyFile(String OrginalePathName, String FinalPahtName) throws IOException {
		LOGGER4J.trace("Start copy");
		File originsfile = new File(OrginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(FinalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.copy(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);
		}
		LOGGER4J.trace("end copy");
	}


	public boolean validateDirectory(File dir) {
		LOGGER4J.trace("start validateDirectory");
		boolean resultas = false;
		if (dir != null)
			if (dir.exists())
				if (dir.isDirectory())
					resultas = true;
		LOGGER4J.trace("end validateDirectory");
		return resultas;
	}


	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectory");
		ArrayList<String> nomFichiers = null;
		File repertoire = new File(dirName);

		if (repertoire.exists()) {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repertoire.toPath());
			nomFichiers = new ArrayList<String>();
			for (Path path : directoryStream) {
				nomFichiers.add(path.toString());
			}
		} else {
			FileNotFoundException e = new FileNotFoundException(
					"repertoire: " + repertoire.getAbsolutePath() + " introuvable");
			throw e;
		}
		LOGGER4J.trace("end listeFilesOnDirectory");
		return nomFichiers;
	}


	public ArrayList<String> listeFilesOnDirectoryAndSubDirectory(String dirName) throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectoryAndSubDirectory");
		ArrayList<String> nomFichiers = null;
		ArrayList<String> nomFichiersTempo = null;
		File repertoire = new File(dirName);
		String fileNameItem;
		System.out.println(dirName);
		if (repertoire.exists()) {
			nomFichiers = new ArrayList<String>();
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repertoire.toPath());
			for (Path path : directoryStream) {
				fileNameItem = path.getFileName().toString();
				if (path.toFile().isDirectory()) {
					nomFichiersTempo = listeFilesOnDirectoryAndSubDirectory(dirName + "\\" + fileNameItem);
					if ((null != nomFichiersTempo)) {
						if (nomFichiersTempo.size() > 0) {
							if (nomFichiers.addAll(nomFichiersTempo) == false) {
								IOException eCollectionSet = new IOException(
										"erro affection apelle recurisif sur le fichier" + fileNameItem
												+ "dans le repertoire" + dirName);
								throw eCollectionSet;
							}
						}
					}
				} else {
					nomFichiers.add(path.toString());
				}
			}
		} else {
			FileNotFoundException eFileNotFound = new FileNotFoundException(
					"repertoire: " + repertoire.getAbsolutePath() + " introuvable");
			throw eFileNotFound;
		}
		LOGGER4J.trace("end listeFilesOnDirectoryAndSubDirectory");
		return nomFichiers;
	}


	public ArrayList<String> filesListFilterOnDirectoryAndSubDirectory(String dirName, MusicExtention[] filters)
			throws IOException {

		ArrayList<String> finalPathFileList = null;
		ArrayList<String> subPathFileList = null;
		File directory = new File(dirName);
		String fileNameItem;

		if (directory.exists()) {
			finalPathFileList = new ArrayList<String>();
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory.toPath());
			for (Path path : directoryStream) {
				fileNameItem = path.getFileName().toString();
				if (path.toFile().isDirectory()) {
					// rapelle la fonction pour recupere la liste des repertoire
					// du sous repertoire
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
							if ((pathFileStr.contains(musicExtention.getValue()))
									|| (pathFileStr.contains(musicExtention.getValue().toUpperCase()))) {
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