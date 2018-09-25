package repository;

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

public class RepositoryWalkingFile implements IRepositoryFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoryWalkingFile.class.getName());

	@Override
	public void delete(String pathFileName) throws IOException {
		Path directory = Paths.get(pathFileName);
		Files.deleteIfExists(directory);
	}

	@Override
	public void recursiveDelete(String pathFileName) throws IOException {
		Path directory = Paths.get(pathFileName);
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

				if (file.endsWith(".gitkeep") == false) {
					Files.delete(file);
				}

				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});

	}

	@Override
	public void move(String orginalePathName, String finalPahtName) throws IOException {
		File originsfile = new File(orginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(finalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.move(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Override
	public void copy(String OrginalePathName, String FinalPahtName) throws IOException {
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
