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

import repository.IRepositoryFile;
/**
 *  repository file implemented with lib java.nio.file new walking methode
*/
public class RepositoryWalkingFile implements IRepositoryFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoryWalkingFile.class.getName());

	@Override
	public void delete(String pathFileName) throws IOException {
		LOGGER4J.trace("supresion " + pathFileName);
		Path directory = Paths.get(pathFileName);

		if (Files.deleteIfExists(directory)) {
			LOGGER4J.trace("supresion " + pathFileName + " reussi");
		} else {
			LOGGER4J.trace("supresion " + pathFileName + " echec");
		}

	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectory");
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
		LOGGER4J.trace("end listeFilesOnDirectory");
		return nomFichiers;
	}

}
