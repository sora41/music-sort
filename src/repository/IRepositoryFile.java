package repository;

import datatransfert.MusicDto;

public interface IRepositoryFile {

	public abstract MusicDto extractDataToMusicFile(String pathFileName);

	public abstract void delete(String pathFileName);

	public abstract void recursiveDelete(String pathFileName);

	public abstract void move(String pathFileName);

	public abstract void copy(String pathFileName);

}
