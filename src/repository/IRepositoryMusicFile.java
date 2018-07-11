package repository;

import datatransfert.MusicDto;

public interface IRepositoryMusicFile {

	public abstract MusicDto extractDataToMusicFile(String pathFileName);

}
