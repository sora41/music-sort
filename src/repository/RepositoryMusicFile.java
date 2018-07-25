package repository;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;

import datatransfert.MusicDto;

public class RepositoryMusicFile implements IRepositoryMusicFile {

	@Override
	public MusicDto getDataToMusicFile(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		MP3File mp3file = new MP3File(pathFileName);
		MusicDto SongDto = new MusicDto();

		if (mp3file.hasID3v1Tag()) {

			SongDto.setAlbum(mp3file.getID3v1Tag().getAlbum());
			SongDto.setFileName(mp3file.getFilenameTag().composeFilename());
			SongDto.setAuthor(mp3file.getID3v1Tag().getArtist());
			SongDto.setPathFile(mp3file.getMp3file().getPath());
			SongDto.setSongName(mp3file.getID3v1Tag().getTitle());
			SongDto.setYears(mp3file.getID3v1Tag().getYear());

			return SongDto;
		} else {
			//System.out.println("file: " + mp3file.getFilenameTag().composeFilename() + " ID3 not suported ");
			TagNotFoundException e = new TagNotFoundException("ID3 not suported ");
			throw e;
		}
	}

	@Override
	public boolean saveDataToMusicFile(MusicDto data) {
		// TODO Auto-generated method stub
		return false;
	}

}
