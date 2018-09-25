package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;
import datatransfert.MusicDto;

public class RepositoryMusicFile implements IRepositoryMusicFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoryMusicFile.class.getName());
	
	@Override
	public MusicDto getDataToMusicFile(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		MP3File mp3File = null;
		MusicDto result = null;
		if (containTag(pathFileName)) {
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
		}
		return result;
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

	private boolean containTag(String pathFileName) throws IOException, TagNotFoundException {

		boolean resultas = true;

		File song = new File(pathFileName);
		FileInputStream file = new FileInputStream(song);
		int size = -1;

		if (song.canRead())
			size = (int) song.length();
		try {
			if (size > 0) {

				file.skip(size - 128);
				byte[] last128 = new byte[128];
				file.read(last128);
				String id3 = new String(last128);
				String tag = id3.substring(0, 3);
				if (tag.equals("TAG") == false) {
					TagNotFoundException e = new TagNotFoundException(" No tag detected on file " );
					throw e;
				}
			} else {
				IOException e = new IOException("Size file zero");
				throw e;
			}
		} finally {
			file.close();
		}
		return resultas;
	}
}
