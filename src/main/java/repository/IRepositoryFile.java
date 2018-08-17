package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface IRepositoryFile {

	public abstract void delete(String pathFileName)throws FileNotFoundException, IOException;

	public abstract void recursiveDelete(String pathFileName) throws  IOException;

	public abstract void move(String OrginalePathName, String FinalPahtName) throws IOException;

	public abstract void copy(String OrginalePathName, String FinalPahtName) throws IOException;
	
	public abstract boolean validateDirectory(File dir); 
	
	public abstract ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException ;


}
