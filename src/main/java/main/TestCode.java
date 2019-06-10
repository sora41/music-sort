package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import datatransfert.MusicDto;
import repository.IRepositoryFile;
import repository.IRepositoryMusicFile;
import repository.music.RepositoryMusicFileMP3Manual;
import repository.music.RepositoryMusicFileMp3JAudiotagger;
import repository.music.RepositoryMusicFileWmaJAudiotagger;
import repository.file.RepositoryWalkingFile;
import repository.file.RepositoryApacheFile;
import constant.MusicExtention;

public class TestCode {

	private static final String DIRECTORY_TEST_MP3 = "Music\\test\\mp3";
	private static final String DIRECTORY_TEST_WMA = "Music\\test\\wma";
	private static final String DIRECTORY_TEST_M4A = "Music\\test\\m4a";
	private static final String DIRECTORY_TEST_CLEAN = "Music\\test\\dircleaner";
	private static final String DIRECTORY_INIT_CLEAN = "Music\\test\\initfile";
	private static final String DIRECTORY_TEST_APACHE = "Music\\test\\apache";
	private static final String DIRECTORY_TEST_DES = "Music\\test\\dest";

	public void afficheMusicDto(MusicDto dto) {
		if (null != dto) {

			System.out.println("filname " + dto.getFileName());
			System.out.println("path " + dto.getPathFile());
			System.out.println("title " + dto.getTitleSong());
			System.out.println("autor " + dto.getAuthor());
			System.out.println("album " + dto.getAlbum());
			System.out.println("annee " + dto.getYears());
			System.out.println("genre " + dto.getGenre());
			System.out.println("custom1 " + dto.getCustom1());
		}
	}

	public void afficheStringArray(ArrayList<String> strings) {
		for (int i = 0; i < strings.size(); i++) {
			System.out.println("index : " + i + " " + strings.get(i));
		}
	}

	public void testLoadDToOnMp3ManualRepository(String dirIn) throws Exception {
		MusicDto mp3;
		IRepositoryFile repoFile = new RepositoryWalkingFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileMP3Manual();
		ArrayList<String> namefiles;

		namefiles = repoFile.listeFilesOnDirectory(dirIn);
		String path = namefiles.get(0);
		mp3 = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(mp3);
	}

	public void testLoadDToOnMP3JAudiotaggerRepository(String dirIn) throws Exception {
		MusicDto mp3;
		IRepositoryFile repoFile = new RepositoryWalkingFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileMp3JAudiotagger();
		ArrayList<String> namefiles;
		namefiles = repoFile.listeFilesOnDirectory(dirIn);
		String path = namefiles.get(0);
		mp3 = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(mp3);

	}

	public void testLoadDtoOnWmaRepository(String dirIn) throws Exception {
		MusicDto wma;
		IRepositoryFile repoFile = new RepositoryWalkingFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileWmaJAudiotagger();
		ArrayList<String> namefiles;
		namefiles = repoFile.listeFilesOnDirectory(dirIn);
		String path = namefiles.get(0);
		wma = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(wma);

	}

	public void testLoadDtoOnM4aRepository(String dirIn) throws Exception {
		MusicDto m4a;
		IRepositoryFile repoFile = new RepositoryWalkingFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileMp3JAudiotagger();
		ArrayList<String> namefiles;
		namefiles = repoFile.listeFilesOnDirectory(dirIn);
		String path = namefiles.get(0);
		m4a = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(m4a);

	}

	public void testCreateDirectory() throws IOException {
		File dest = new File(DIRECTORY_TEST_DES);

		System.out.println(dest.list().length);
		for (String fileStr : dest.list()) {
			System.out.println(fileStr);
		}
		Collection<File> files;
		files = FileUtils.listFiles(dest, null, true);
		System.out.println(files.size());
		for (File file2 : files) {
			System.out.println(file2.getName());
		}
	}

	public void testMoveDirectory() throws IOException {
		File file = new File(DIRECTORY_TEST_APACHE);
		File dest = new File(DIRECTORY_TEST_DES);
		FileUtils.moveDirectory(file, dest);
	}

	public void testMoveFile() throws IOException {
		File file = new File("Music\\test\\1.txt");
		File dest = new File(DIRECTORY_TEST_DES + "\\1.txt");
		FileUtils.moveFile(file, dest);

	}

	public void testGetlisteFileRecursifWalking(String dirtoScan) throws IOException {
		RepositoryWalkingFile rwf = new RepositoryWalkingFile();
		afficheStringArray(rwf.listeFilesOnDirectoryAndSubDirectory(dirtoScan));
	}

	public void testGetlisteFileRecursifApache(String dirtoScan) throws IOException {
		RepositoryApacheFile raf = new RepositoryApacheFile();
		afficheStringArray(raf.listeFilesOnDirectoryAndSubDirectory(dirtoScan));
	}

	public void testGetFilterlisteFileRecursifApache(String dirtoScan) throws IOException {
		MusicExtention[] filters = { MusicExtention.MP3, MusicExtention.WMA };
		RepositoryApacheFile raf = new RepositoryApacheFile();
		afficheStringArray(raf.filesListFilterOnDirectoryAndSubDirectory(dirtoScan, filters));
	}

	public void testGetFilterlisteFileRecursifWalking(String dirtoScan) throws IOException {
		MusicExtention[] filters = { MusicExtention.MP3, MusicExtention.WMA };
		RepositoryWalkingFile rwf = new RepositoryWalkingFile();
		afficheStringArray(rwf.filesListFilterOnDirectoryAndSubDirectory(dirtoScan, filters));
	}

	public void testDirectory() throws Exception {
		System.out.println("-------------------testLoadDToOnMp3ManualRepository------------");
		testLoadDToOnMp3ManualRepository(DIRECTORY_TEST_MP3);

		System.out.println("-------------------testLoadDToOnMP3JAudiotaggerRepository------------");
		testLoadDToOnMP3JAudiotaggerRepository(DIRECTORY_TEST_MP3);

		System.out.println("-------------------testLoadDtoOnWmaRepository------------");
		testLoadDtoOnWmaRepository(DIRECTORY_TEST_WMA);
	}

	public void testListFileRecursif() throws Exception {
		System.out.println("-------------------test_getlisteFileRecursifWalking------------");
		testGetlisteFileRecursifWalking(DIRECTORY_INIT_CLEAN);

		System.out.println("-------------------test_getlisteFileRecursifApache------------");
		testGetlisteFileRecursifApache(DIRECTORY_INIT_CLEAN);

		System.out.println("-------------------test_getFilterlisteFileRecursifApache------------");
		testGetFilterlisteFileRecursifApache(DIRECTORY_INIT_CLEAN);

		System.out.println("-------------------test_getFilterlisteFileRecursifWalking------------");
		testGetFilterlisteFileRecursifWalking(DIRECTORY_INIT_CLEAN);
	}

	public void testLoadDTOMusic() throws Exception {
		System.out.println("-------------------testLoadDToOnMp3ManualRepository------------");
		testLoadDToOnMp3ManualRepository(DIRECTORY_TEST_M4A);

	}

	public void runTest() throws Exception {

		testDirectory();
		//testLoadDTOMusic();
		/*
		 * System.out.println("-------------------test_forceMKDIR------------");
		 * test_create_directory();
		 * 
		 * System.out.println(
		 * "-------------------test_move_directory------------");
		 * test_move_directory();
		 * 
		 * System.out.println("-------------------test_move_file------------");
		 * test_move_file();
		 */
		testListFileRecursif();
	}
}
