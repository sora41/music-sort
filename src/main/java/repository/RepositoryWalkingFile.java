package repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class RepositoryWalkingFile implements IRepositoryFile {

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
