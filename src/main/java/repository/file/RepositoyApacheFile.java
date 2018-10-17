package repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import repository.IRepositoryFile;

/**
 * repository file implemented with lib apacheIO
 */
public class RepositoyApacheFile implements IRepositoryFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoyApacheFile.class.getName());

	@Override
	public void delete(String pathFileName) throws FileNotFoundException, IOException {
		LOGGER4J.trace("start delete " + pathFileName);
		File fileToDelete = new File(pathFileName);
		FileUtils.forceDelete(fileToDelete);
		LOGGER4J.trace("end delete " + pathFileName);
	}

	@Override
	public void recursiveDelete(String pathFileName) throws IOException {
		LOGGER4J.trace("start recursiveDelete " + pathFileName);
		File fileToDelete = new File(pathFileName);
		FileUtils.forceDelete(fileToDelete);
		LOGGER4J.trace("end recursiveDelete " + pathFileName);
	}

	@Override
	public void cleanDirectory(String pathFileName) throws IOException {
		LOGGER4J.trace("start cleanDirectory" + pathFileName);
		File fileToClean = new File(pathFileName);
		FileUtils.cleanDirectory(fileToClean);
		LOGGER4J.trace("end cleanDirectory");
	}

	@Override
	public void moveFile(String orginalePathName, String finalPahtName) throws IOException {
		LOGGER4J.trace("Start move");
		File file = new File(orginalePathName);
		File dest = new File(finalPahtName);
		FileUtils.moveFile(file, dest);
		LOGGER4J.trace("end move");
	}

	@Override
	public void copyFile(String orginalePathName, String finalPahtName) throws IOException {
		LOGGER4J.trace("start copy");
		File fileSrc = new File(orginalePathName);
		File filedest = new File(finalPahtName);
		FileUtils.copyFile(fileSrc, filedest);
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
		ArrayList<String> nomFichiers = null;
		File repertoire = new File(dirName);
		Collection<File> files;

		files = FileUtils.listFiles(repertoire, null, false);

		if ((null != files) && (files.size() > 0)) {
			nomFichiers = new ArrayList<>();
			for (File fileItem : files) {

				nomFichiers.add(fileItem.getPath());
			}
		}
		LOGGER4J.trace("end listeFilesOnDirectory");
		return nomFichiers;
	}

	@Override
	public ArrayList<String> listeFilesOnDirectoryAndSubDirectory(String dirName) throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectoryAndSubDirectory");
		ArrayList<String> nomFichiers = null;
		File repertoire = new File(dirName);
		Collection<File> files;

		files = FileUtils.listFiles(repertoire, null, true);

		if ((null != files) && (files.size() > 0)) {
			nomFichiers = new ArrayList<>();
			for (File fileItem : files) {

				nomFichiers.add(fileItem.getPath());
			}
		}
		LOGGER4J.trace("end listeFilesOnDirectoryAndSubDirectory");
		return nomFichiers;
	}

}
