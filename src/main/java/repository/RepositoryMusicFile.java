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
		MP3File mp3File ;
		try {
			mp3File = new MP3File(pathFileName);
			
			if (mp3File.hasID3v1Tag()) {
				return this.getID3DataV1(mp3File);
			} else {
				if (mp3File.hasID3v2Tag()) {
					return this.getID3DataV2(mp3File);
				} else {
					TagNotFoundException e = new TagNotFoundException(" ID3 v1 & v2 not suported ");
					throw e;
				}
			}
		} finally {
			System.out.println("echo");
		}
		
	
	}

	@Override
	public boolean saveDataToMusicFile(MusicDto data) throws Exception {
		Exception e = new Exception("fonction non implementer");
		throw e;
	}

	private MusicDto getID3DataV1(MP3File mp3file)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {

		MusicDto SongDto = new MusicDto();

		SongDto.setAlbum(mp3file.getID3v1Tag().getAlbum());
		SongDto.setFileName(mp3file.getFilenameTag().composeFilename());
		SongDto.setAuthor(mp3file.getID3v1Tag().getArtist());
		SongDto.setPathFile(mp3file.getMp3file().getPath());
		SongDto.setSongName(mp3file.getID3v1Tag().getTitle());
		SongDto.setYears(mp3file.getID3v1Tag().getYear());

		return SongDto;
	}

	private MusicDto getID3DataV2(MP3File mp3file) {
		MusicDto SongDto = new MusicDto();

		SongDto.setAlbum(mp3file.getID3v2Tag().getAlbumTitle());
		SongDto.setFileName(mp3file.getFilenameTag().composeFilename());
		SongDto.setAuthor(mp3file.getID3v2Tag().getLeadArtist());
		SongDto.setPathFile(mp3file.getMp3file().getPath());
		SongDto.setSongName(mp3file.getID3v2Tag().getSongTitle());
		SongDto.setYears(mp3file.getID3v2Tag().getYearReleased());

		return SongDto;

	}
}
