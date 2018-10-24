package repository;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.farng.mp3.TagException;

import datatransfert.MusicDto;
/** inteface de gestion de fichier de musique */
public interface IRepositoryMusicFile {

	public abstract MusicDto getDataToMusicFile(String pathFileName)
			throws Exception;

	public abstract boolean saveDataToMusicFile(MusicDto data) throws Exception;

}
