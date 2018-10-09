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
		File fileToDelete = new File(pathFileName);
		FileUtils.forceDelete(fileToDelete);
	}

	@Override
	public void recursiveDelete(String pathFileName) throws IOException {
		File fileToDelete = new File(pathFileName);
		FileUtils.forceDelete(fileToDelete);
	}

	@Override
	public void cleanDirectory(String pathFileName) throws IOException {
		File fileToClean = new File(pathFileName);
		FileUtils.cleanDirectory(fileToClean);
	}

	@Override
	public void move(String orginalePathName, String finalPahtName) throws IOException {
		File file = new File(orginalePathName);
		File dest = new File(finalPahtName);
		FileUtils.moveDirectory(file, dest);
	}

	@Override
	public void copy(String orginalePathName, String finalPahtName) throws IOException {
		File fileSrc = new File(orginalePathName);
		File filedest = new File(finalPahtName);
		FileUtils.copyDirectory(fileSrc, filedest);
	}

	@Override
	public boolean validateDirectory(File dir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {
		LOGGER4J.trace("start listeFilesOnDirectory");
		ArrayList<String> nomFichiers = new ArrayList<>();
		File repertoire = new File(dirName);
		Collection<File> files ;
	
		
		files = FileUtils.listFiles(repertoire, null,false);
		
		for (File fileItem : files) {
			
			nomFichiers.add(fileItem.getName());
		}
		
		return null;
	}

}
