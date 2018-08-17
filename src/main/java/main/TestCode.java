package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.farng.mp3.TagException;

import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;
import repository.RepositoryFile;
import repository.RepositoryMusicFile;
import repository.RepositoryMusicFileManual;

public class TestCode {

	private static final String DIRECTORY_TEST_MP3 = "Music\\test\\filesmp3";
	private static final String DIRECTORY_TEST_CLEAN = "Music\\test\\dircleaner";
	private static final String DIRECTORY_INIT_CLEAN = "Music\\test\\initfile";
	public void afficheMusicDto(MusicDto dto) {
		if (null != dto) {

			System.out.println("filname " + dto.getFileName());
			System.out.println("path " + dto.getPathFile());
			System.out.println("title " + dto.getSongName());
			System.out.println("autor " + dto.getAuthor());
			System.out.println("album " + dto.getAlbum());
			System.out.println("annee " + dto.getYears());
		}
	}

	public void afficheStringArray(ArrayList<String> strings) {
		for (int i = 0; i < strings.size(); i++) {
			System.out.println("index : " + i + " " + strings.get(i));
		}
	}

	public void testGetDtoMusique(String DirIn) {

		MusicDto mp3;
		RepositoryFile repoFile = new RepositoryFile();
		RepositoryMusicFile repoMusic = new RepositoryMusicFile();
		ArrayList<String> namefiles;
		try {
			namefiles = repoFile.listeFilesOnDirectory(DirIn);
			String path = DirIn + File.separator + namefiles.get(0);
			// afficheStringArray(namefiles);
			System.out.println(path);

			mp3 = repoMusic.getDataToMusicFile(path);
			afficheMusicDto(mp3);

		} catch (UnsupportedOperationException | IOException | TagException e) {

			e.printStackTrace();
		}

	}

	public void testLoadManualRepository(String DirIn) {
		MusicDto mp3;
		RepositoryFile repoFile = new RepositoryFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileManual();
		ArrayList<String> namefiles;
		try {
			namefiles = repoFile.listeFilesOnDirectory(DirIn);
			String path = DirIn + File.separator + namefiles.get(0);
			// afficheStringArray(namefiles);
			System.out.println(path);

			mp3 = repoMusic.getDataToMusicFile(path);
			afficheMusicDto(mp3);

		} catch (UnsupportedOperationException | IOException | TagException e) {

			e.printStackTrace();
		}
	}

	public void test_deletefileRecursif(String DirToClean) {

	}

	public void runTest() {
		System.out.println("----------------testGetDtoMusique----------------------");
		testGetDtoMusique(DIRECTORY_TEST_MP3);

		System.out.println("-------------------testLoadManualRepository------------");
		testLoadManualRepository(DIRECTORY_TEST_MP3);

		/*System.out.println("-------------------test_delete_ recursif------------");
		test_deletefileRecursif(DIRECTORY_TEST_CLEAN);*/

	}
}
