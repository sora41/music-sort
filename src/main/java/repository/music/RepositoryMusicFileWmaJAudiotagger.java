package repository.music;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.TagNotFoundException;
import org.jaudiotagger.tag.asf.AsfTag;

import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;

public class RepositoryMusicFileWmaJAudiotagger implements IRepositoryMusicFile {

	public MusicDto getDataToMusicFile(String pathFileName) throws Exception {
		MusicDto wma = null;
		File song = new File(pathFileName);
		this.shutupLog();
		AudioFile audioFi = AudioFileIO.read(song);
		AsfTag tagWma = (AsfTag) audioFi.getTag();

		if ((null != tagWma) && (tagWma.isEmpty() == false)) {
			wma = new MusicDto();
			wma.setAuthor(tagWma.getFirst("AUTHOR").toString());
			wma.setAlbum(tagWma.getFirst("WM/AlbumTitle").toString());
			wma.setFileName(song.getName());
			wma.setGenre(tagWma.getFirst("WM/Genre").toString());
			wma.setYears(tagWma.getFirst("WM/Year").toString());
			wma.setPathFile(song.getPath());
			wma.setTitleSong(tagWma.getFirst("TITLE").toString());
		} else {
			TagNotFoundException e = new TagNotFoundException(" WMA no have Tag ");
			throw e;
		}
		return wma;
	}

	public boolean saveDataToMusicFile(MusicDto data) throws Exception {
		Exception e = new Exception("fonction non implementer");
		throw e;
	}

	private void shutupLog() {
		Logger[] pin;
		pin = new Logger[] { Logger.getLogger("org.jaudiotagger") };

		for (Logger l : pin)
			l.setLevel(Level.OFF);
	}

}
