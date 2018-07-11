package repository;

import java.io.IOException;

public interface IRepositoryFile {

	public abstract void delete(String pathFileName);

	public abstract void recursiveDelete(String pathFileName);

	public abstract void move(String OrginalePathName, String FinalPahtName) throws IOException;

	public abstract void copy(String OrginalePathName, String FinalPahtName) throws IOException;

}
