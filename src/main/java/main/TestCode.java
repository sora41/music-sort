package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.farng.mp3.TagException;

import datatransfert.MusicDto;
import repository.IRepositoryFile;
import repository.IRepositoryMusicFile;
import repository.music.RepositoryMusicFile;
import repository.music.RepositoryMusicFileManual;
import repository.file.RepositoryNativeFile;

public class TestCode {

	private static final String DIRECTORY_TEST_MP3 = "Music\\test\\filesmp3";
	private static final String DIRECTORY_TEST_CLEAN = "Music\\test\\dircleaner";
	private static final String DIRECTORY_INIT_CLEAN = "Music\\test\\initfile";
	private static final String DIRECTORY_TEST_APACHE = "Music\\test\\apache";
	private static final String DIRECTORY_TEST_DES = "Music\\test\\dest";
	
	public void afficheMusicDto(MusicDto dto) {
		if (null != dto) {

			System.out.println("filname " + dto.getFileName());
			System.out.println("path " + dto.getPathFile());
			System.out.println("title " + dto.getSongName());
			System.out.println("autor " + dto.getAuthor());
			System.out.println("album " + dto.getAlbum());
			System.out.println("annee " + dto.getYears());
			System.out.println("genre " + dto.getGenre());
		}
	}

	public void afficheStringArray(ArrayList<String> strings) {
		for (int i = 0; i < strings.size(); i++) {
			System.out.println("index : " + i + " " + strings.get(i));
		}
	}

	public void testGetDtoMusique(String DirIn) {

		MusicDto mp3;
		IRepositoryFile repoFile = new RepositoryNativeFile();
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
		IRepositoryFile repoFile = new RepositoryNativeFile();
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

	public void test_create_directory() throws IOException
	{
		
		File file = new File(DIRECTORY_TEST_APACHE);
		File dest = new File(DIRECTORY_TEST_DES);
		
		System.out.println(dest.list().length);
		for (String fileStr : dest.list()) {
			System.out.println(fileStr);
		};
		Collection<File> files ;
		//FileUtils.moveDirectory(file, dest);
		//FileUtils.cleanDirectory(dest);
		String [] extention = {""};
		files = FileUtils.listFiles(dest, null,true);
		System.out.println(files.size());
		for (File file2 : files) {
			System.out.println(file2.getName());
		}
	}
	
	public void test_deletefileRecursif(String DirToClean) {

	}

	public void runTest() throws IOException {
		System.out.println("----------------testGetDtoMusique----------------------");
		testGetDtoMusique(DIRECTORY_TEST_MP3);

		System.out.println("-------------------testLoadManualRepository------------");
		testLoadManualRepository(DIRECTORY_TEST_MP3);

		/*System.out.println("-------------------test_delete_ recursif------------");
		test_deletefileRecursif(DIRECTORY_TEST_CLEAN);*/
		

		System.out.println("-------------------test_forceMKDIR------------");
		test_create_directory();

	}
}
