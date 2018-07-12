package repository;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.farng.mp3.TagException;

import datatransfert.MusicDto;

public interface IRepositoryMusicFile {

	public abstract MusicDto getDataToMusicFile(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException;

	public abstract boolean saveDataToMusicFile(MusicDto data);

}
