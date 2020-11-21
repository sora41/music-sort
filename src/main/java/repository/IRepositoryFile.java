package repository;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import constant.MusicExtention;
/** inteface de gestion de fichier */
public interface IRepositoryFile {

	public abstract void delete(String pathFileName)throws  IOException;

	public abstract void recursiveDelete(String pathFileName) throws IOException;
	
	public abstract void cleanDirectory(String pathFileName) throws IOException;

	public abstract void moveFile(String orginalePathName, String finalPahtName) throws IOException;

	public abstract void copyFile(String orginalePathName, String finalPahtName) throws IOException;
	
	public abstract boolean validateDirectory(File dir); 
	
	public abstract ArrayList<String> listFilesOnDirectory(String dirName) throws IOException ;
	
	public abstract ArrayList<String> listFilesOnDirectoryAndSubDirectory(String dirName) throws IOException ;
	/*** /
	 * renvoie la liste des fichier contenu dans le repertoire et ses sous repertoire , avec un filtre 
	 * @param dirName
	 * @param filters
	 * @return
	 * @throws IOException
	 */
	public abstract ArrayList<String> filesListFilterOnDirectoryAndSubDirectory(String dirName,MusicExtention [] filters) throws IOException;

}
