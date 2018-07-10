package repository;

import datatransfert.MusicDto;

public interface IDiggerFileMusic {
	
	public abstract MusicDto extractDataToMusicFile(String pathFileName);

}
