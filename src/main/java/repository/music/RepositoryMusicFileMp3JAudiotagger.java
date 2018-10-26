package repository.music;

import java.util.logging.Level;
import java.util.logging.Logger;


import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagNotFoundException;
import org.jaudiotagger.tag.id3.ID3v24Frames;

import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;

public class RepositoryMusicFileMp3JAudiotagger implements IRepositoryMusicFile {
	
	
	@Override
	public MusicDto getDataToMusicFile(String pathFileName) throws Exception {
		MusicDto result = null;
		MP3File mp3File = null;
		this.shutupLog();
		// if (containTag(pathFileName)) {
		mp3File = new MP3File(pathFileName);
		if (mp3File.hasID3v1Tag()) {
			result = this.getID3DataV1(mp3File);
		} else {
			if (mp3File.hasID3v2Tag()) {
				result = this.getID3DataV2(mp3File);
			} else {
				TagNotFoundException e = new TagNotFoundException(" ID3 v1 & v2 not suported ");
				throw e;
			}
		}
		// }
		return result;
	}

	@Override
	public boolean saveDataToMusicFile(MusicDto data) throws Exception {
		Exception e = new Exception("fonction non implementer");
		throw e;
	}

	private MusicDto getID3DataV1(MP3File mp3File) {
		MusicDto songDto = new MusicDto();
		songDto.setAlbum(mp3File.getID3v1Tag().getAlbum().get(0).toString());
		songDto.setFileName(mp3File.getFile().getName());
		songDto.setAuthor(mp3File.getID3v1Tag().getArtist().get(0).toString());
		songDto.setPathFile(mp3File.getFile().getPath());
		songDto.setTitleSong(mp3File.getID3v1Tag().getFirstTitle());
		songDto.setYears(mp3File.getID3v1Tag().getFirstYear());
		songDto.setGenre(mp3File.getID3v1Tag().getFirstGenre());
		return songDto;
	}

	private MusicDto getID3DataV2(MP3File mp3file) {
		MusicDto songDto = new MusicDto();
		songDto.setAlbum(mp3file.getID3v2Tag().getFirst(ID3v24Frames.FRAME_ID_ALBUM));
		songDto.setFileName(mp3file.getFile().getName());
		songDto.setAuthor(mp3file.getID3v2Tag().getFirst(ID3v24Frames.FRAME_ID_ARTIST));
		songDto.setPathFile(mp3file.getFile().getPath());
		songDto.setTitleSong(mp3file.getID3v2Tag().getFirst(ID3v24Frames.FRAME_ID_TITLE));
		songDto.setYears(mp3file.getID3v2Tag().getFirst(ID3v24Frames.FRAME_ID_YEAR));
		songDto.setGenre(mp3file.getID3v2Tag().getFirst(ID3v24Frames.FRAME_ID_GENRE));
		return songDto;
	}

	private void shutupLog() {
		Logger[] pin;
		pin = new Logger[] { Logger.getLogger("org.jaudiotagger") };

		for (Logger l : pin)
			l.setLevel(Level.OFF);
	}

}
