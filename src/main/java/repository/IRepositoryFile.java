package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
/** inteface de gestion de fichier */
public interface IRepositoryFile {

	public abstract void delete(String pathFileName)throws FileNotFoundException, IOException;

	public abstract void recursiveDelete(String pathFileName) throws IOException;
	
	public abstract void cleanDirectory(String pathFileName) throws IOException;

	public abstract void moveFile(String orginalePathName, String finalPahtName) throws IOException;

	public abstract void copyFile(String orginalePathName, String finalPahtName) throws IOException;
	
	public abstract boolean validateDirectory(File dir); 
	
	public abstract ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException ;


}
