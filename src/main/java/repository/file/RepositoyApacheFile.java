package repository.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constant.MusicExtention;
import repository.IRepositoryFile;

/**
 * repository file implemented with lib apacheIO
 */
public class RepositoyApacheFile implements IRepositoryFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoyApacheFile.class.getName());


	public void delete(String pathFileName) throws FileNotFoundException, IOException {
		LOGGER4J.trace("start delete " + pathFileName);
		File fileToDelete = new File(pathFileName);
		FileUtils.forceDelete(fileToDelete);
		LOGGER4J.trace("end delete " + pathFileName);
	}


	public void recursiveDelete(String pathFileName) throws IOException {
		LOGGER4J.trace("start recursiveDelete " + pathFileName);
		File fileToDelete = new File(pathFileName);
		FileUtils.forceDelete(fileToDelete);
		LOGGER4J.trace("end recursiveDelete " + pathFileName);
	}

	public void cleanDirectory(String pathFileName) throws IOException {
		LOGGER4J.trace("start cleanDirectory" + pathFileName);
		File fileToClean = new File(pathFileName);
		FileUtils.cleanDirectory(fileToClean);
		LOGGER4J.trace("end cleanDirectory");
	}

	public void moveFile(String orginalePathName, String finalPahtName) throws IOException {
		LOGGER4J.trace("Start moveFile");
		File file = new File(orginalePathName);
		File dest = new File(finalPahtName);
		FileUtils.moveFile(file, dest);
		LOGGER4J.trace("end moveFile");
	}


	public void copyFile(String orginalePathName, String finalPahtName) throws IOException {
		LOGGER4J.trace("start copyFile " + orginalePathName);
		File fileSrc = new File(orginalePathName);
		File filedest = new File(finalPahtName);
		FileUtils.copyFile(fileSrc, filedest);
		LOGGER4J.trace("end copyFile" + finalPahtName);
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


	public ArrayList<String> listeFilesOnDirectoryAndSubDirectory(String dirName) throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectoryAndSubDirectory");
		ArrayList<String> nomFichiers = null;
		File repertoire = new File(dirName);
		Collection<File> files;

		files = FileUtils.listFiles(repertoire, null, true);

		if ((null != files) && (files.size() > 0)) {
			nomFichiers = new ArrayList<String>();
			for (File fileItem : files) {

				nomFichiers.add(fileItem.getPath());
			}
		}
		LOGGER4J.trace("end listeFilesOnDirectoryAndSubDirectory");
		return nomFichiers;
	}


	public ArrayList<String> filesListFilterOnDirectoryAndSubDirectory(String dirName, MusicExtention[] filters)
			throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectoryAndSubDirectory");
		ArrayList<String> nomFichiers = null;
		File repertoire = new File(dirName);
		Collection<File> files;
		// converstion des filtre tableaux de string
		int size = filters.length * 2;
		String[] strFilters = new String[size];
		int i = 0;
		// rempli la liste des fitres avec les chaine minuscule et majsucule
		for (MusicExtention musicExtention : filters) {

			strFilters[i] = musicExtention.getValue();
			strFilters[i + 1] = musicExtention.getValue().toUpperCase();

			i = i + 2;
		}

		files = FileUtils.listFiles(repertoire, strFilters, true);

		if ((null != files) && (files.size() > 0)) {
			nomFichiers = new ArrayList<String>();
			for (File fileItem : files) {

				nomFichiers.add(fileItem.getPath());
			}
		}
		LOGGER4J.trace("end listeFilesOnDirectoryAndSubDirectory");
		return nomFichiers;
	}

}
