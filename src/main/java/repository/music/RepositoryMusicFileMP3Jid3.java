package repository.music;

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
import repository.IRepositoryMusicFile;
//jid3
public class RepositoryMusicFileMP3Jid3 implements IRepositoryMusicFile {

	private static final Logger LOGGER4J = LogManager.getLogger(RepositoryMusicFileMP3Jid3.class.getName());
	
	@Override
	public MusicDto getDataToMusicFile(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		MP3File mp3File = null;
		MusicDto result = null;
		if (containTag(pathFileName)) {
			mp3File = new MP3File(pathFileName);

			if (mp3File.hasID3v1Tag()) {
				result = this.loadID3DataV1(mp3File);
			} else {
				if (mp3File.hasID3v2Tag()) {
					result = this.loadID3DataV2(mp3File);
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

	private MusicDto loadID3DataV1(MP3File mp3file)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {

		MusicDto songDto = new MusicDto();

		songDto.setAlbum(mp3file.getID3v1Tag().getAlbum());
		songDto.setFileName(mp3file.getFilenameTag().composeFilename());
		songDto.setAuthor(mp3file.getID3v1Tag().getArtist());
		songDto.setPathFile(mp3file.getMp3file().getPath());
		songDto.setTitleSong(mp3file.getID3v1Tag().getTitle());
		songDto.setYears(mp3file.getID3v1Tag().getYear());
		String dtoGenre = Byte.toString(mp3file.getID3v1Tag().getGenre());

		songDto.setGenre(dtoGenre);
		

		return songDto;
	}

	private MusicDto loadID3DataV2(MP3File mp3file) {
		MusicDto songDto = new MusicDto();

		songDto.setAlbum(mp3file.getID3v2Tag().getAlbumTitle());
		songDto.setFileName(mp3file.getFilenameTag().composeFilename());
		songDto.setAuthor(mp3file.getID3v2Tag().getLeadArtist());
		songDto.setPathFile(mp3file.getMp3file().getPath());
		songDto.setTitleSong(mp3file.getID3v2Tag().getSongTitle());
		songDto.setYears(mp3file.getID3v2Tag().getYearReleased());
		songDto.setGenre(mp3file.getID3v2Tag().getSongGenre());
		return songDto;

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
