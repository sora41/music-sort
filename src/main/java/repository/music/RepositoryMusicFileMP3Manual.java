package repository.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;

public class RepositoryMusicFileMP3Manual implements IRepositoryMusicFile {


	public MusicDto getDataToMusicFile(String pathFileName)
			throws IOException, FileNotFoundException, UnsupportedOperationException {
		MusicDto dto = new MusicDto();
		File song = new File(pathFileName);
		FileInputStream file = new FileInputStream(song);
		int size = -1;
		if (song.canRead()) {
			size = (int) song.length();
		}
		try {
			if (size > 0) {

				file.skip(size - 128L);
				byte[] last128 = new byte[128];
				file.read(last128);
				String id3 = new String(last128);
				String tag = id3.substring(0, 3);
				if (tag.equals("TAG")) {
					dto.setTitleSong(id3.substring(3, 32));
					dto.setAuthor(id3.substring(33, 62));
					dto.setAlbum(id3.substring(63, 91));
					dto.setYears(id3.substring(93, 97));
					dto.setFileName(song.getName());
					dto.setPathFile(pathFileName);

				} else {
					throw  new IOException(" does not contain" + " ID3 information.");
				}
			} else {
				throw new IOException("Size file zero");
			}
		} finally {
			file.close();
		}
		return dto;
	}


	public boolean saveDataToMusicFile(MusicDto data) throws Exception {
		
		throw new Exception("fonction non implementer");
	}

}
