package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.farng.mp3.TagException;

import datatransfert.MusicDto;

public class RepositoryMusicFileManual implements IRepositoryMusicFile {

	@Override
	public MusicDto getDataToMusicFile(String pathFileName)
			throws IOException, TagException, FileNotFoundException, UnsupportedOperationException {
		MusicDto dto = new MusicDto();
		//try {
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
					if (tag.equals("TAG")) {

						/*System.out.println("Title: " + id3.substring(3, 32));
						System.out.println("Artist: " + id3.substring(33, 62));
						System.out.println("Album: " + id3.substring(63, 91));
						System.out.println("Year: " + id3.substring(93, 97));*/
						dto.setSongName(id3.substring(3, 32));
						dto.setAuthor(id3.substring(33, 62));
						dto.setAlbum(id3.substring(63, 91));
						
						
					} else
						System.out.println(" does not contain" + " ID3 information.");
				} else {
					IOException e = new IOException("Size file zero");
					throw e;
				}
			} finally {
				file.close();
			}

		//} catch (Exception e) {
			//System.out.println("Error - " + e.toString());
			// e.printStackTrace();
		//}
		return dto;
	}

	@Override
	public boolean saveDataToMusicFile(MusicDto data) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void loadMp3Mannuel(String fileName) {
		try {
			File song = new File(fileName);
			FileInputStream file = new FileInputStream(song);
			int size = -1;
			if (song.canRead())
				size = (int) song.length();
			try {

				System.out.println(fileName + " taille " + size);
				if (size > 0) {

					file.skip(size - 128);
					byte[] last128 = new byte[128];
					file.read(last128);
					String id3 = new String(last128);
					String tag = id3.substring(0, 3);
					if (tag.equals("TAG")) {

						System.out.println("Title: " + id3.substring(3, 32));
						System.out.println("Artist: " + id3.substring(33, 62));
						System.out.println("Album: " + id3.substring(63, 91));
						System.out.println("Year: " + id3.substring(93, 97));
					} else
						System.out.println(" does not contain" + " ID3 information.");
				} else {
					IOException e = new IOException("Size file zero");
					throw e;
				}
			} finally {
				file.close();
			}

		} catch (Exception e) {
			System.out.println("Error - " + e.toString());
			// e.printStackTrace();
		}
	}

}
