package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface IRepositoryFile {

	public abstract void delete(String pathFileName);

	public abstract void recursiveDelete(String pathFileName);

	public abstract void move(String OrginalePathName, String FinalPahtName) throws IOException;

	public abstract void copy(String OrginalePathName, String FinalPahtName) throws IOException;
	
	public abstract boolean validateDirectory(File dir); 
	
	public abstract ArrayList<String> listeFilesOnDirectory(String dirName) ;


}
