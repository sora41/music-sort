package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.farng.mp3.TagException;

import datatransfert.MusicDto;
import repository.IRepositoryFile;
import repository.IRepositoryMusicFile;
import repository.music.RepositoryMusicFileMP3Jid3;
import repository.music.RepositoryMusicFileMP3Manual;
import repository.music.RepositoryMusicFileMp3JAudiotagger;
import repository.file.RepositoryNativeFile;
import repository.file.RepositoryWalkingFile;
import repository.file.RepositoyApacheFile;
import org.jaudiotagger.tag.TagNotFoundException;

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
		}
	}

	public void afficheStringArray(ArrayList<String> strings) {
		for (int i = 0; i < strings.size(); i++) {
			System.out.println("index : " + i + " " + strings.get(i));
		}
	}

	public void testLoadDToOnMP3Jid3Repository(String DirIn) throws IOException, UnsupportedOperationException, TagException {

		MusicDto mp3;
		IRepositoryFile repoFile = new RepositoryNativeFile();
		RepositoryMusicFileMP3Jid3 repoMusic = new RepositoryMusicFileMP3Jid3();
		ArrayList<String> namefiles;
		namefiles = repoFile.listeFilesOnDirectory(DirIn);
		String path = namefiles.get(0);
		System.out.println(path);
		mp3 = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(mp3);

	}

	public void testLoadDToOnMp3ManualRepository(String DirIn) throws Exception {
		MusicDto mp3;
		IRepositoryFile repoFile = new RepositoryNativeFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileMP3Manual();
		ArrayList<String> namefiles;

		namefiles = repoFile.listeFilesOnDirectory(DirIn);
		String path = namefiles.get(0);
		mp3 = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(mp3);
	}

	public void testLoadDtoOnWmaRepository(String DirIn) throws Exception {
		MusicDto wma;
		IRepositoryFile repoFile = new RepositoryNativeFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileMp3JAudiotagger();
		ArrayList<String> namefiles;
		namefiles = repoFile.listeFilesOnDirectory(DirIn);
		String path = namefiles.get(0);
		wma = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(wma);

	}

	public void testLoadDtoOnM4aRepository(String DirIn) throws Exception {
		MusicDto m4a;
		IRepositoryFile repoFile = new RepositoryNativeFile();
		IRepositoryMusicFile repoMusic = new RepositoryMusicFileMP3Manual();
		ArrayList<String> namefiles;
		namefiles = repoFile.listeFilesOnDirectory(DirIn);
		String path = namefiles.get(0);
		m4a = repoMusic.getDataToMusicFile(path);
		afficheMusicDto(m4a);

	}

	public void test_create_directory() throws IOException {

		File file = new File(DIRECTORY_TEST_APACHE);
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

	public void test_move_directory() throws IOException {
		File file = new File(DIRECTORY_TEST_APACHE);
		File dest = new File(DIRECTORY_TEST_DES);
		FileUtils.moveDirectory(file, dest);
	}

	public void test_move_file() throws IOException {
		File file = new File("Music\\test\\1.txt");
		File dest = new File(DIRECTORY_TEST_DES + "\\1.txt");
		FileUtils.moveFile(file, dest);

	}

	public void test_getlisteFileRecursifWalking(String dirtoScan) throws IOException {
		RepositoryWalkingFile rwf = new RepositoryWalkingFile();
		afficheStringArray(rwf.listeFilesOnDirectoryAndSubDirectory(dirtoScan));
	}

	public void test_getlisteFileRecursifNatif(String dirtoScan) throws IOException {
		RepositoryNativeFile rnf = new RepositoryNativeFile();
		afficheStringArray(rnf.listeFilesOnDirectoryAndSubDirectory(dirtoScan));
	}

	public void test_getlisteFileRecursifApache(String dirtoScan) throws IOException {
		RepositoyApacheFile raf = new RepositoyApacheFile();
		afficheStringArray(raf.listeFilesOnDirectoryAndSubDirectory(dirtoScan));
	}

	public void test_getFilterlisteFileRecursifApache(String dirtoScan) throws IOException {
		String[] filters = { "mp3" };
		RepositoyApacheFile raf = new RepositoyApacheFile();
		afficheStringArray(raf.filesListFilterOnDirectoryAndSubDirectory(dirtoScan, filters));
	}

	public void test_getFilterlisteFileRecursifNative(String dirtoScan) throws IOException {
		String[] filters = { "mp3" };
		RepositoryNativeFile rnf = new RepositoryNativeFile();
		afficheStringArray(rnf.filesListFilterOnDirectoryAndSubDirectory(dirtoScan, filters));
	}

	public void runTest() throws Exception {
		System.out.println("----------------testLoadDToOnMP3Jid3Repository----------------------");
		testLoadDToOnMP3Jid3Repository(DIRECTORY_TEST_MP3);

		System.out.println("-------------------testLoadDToOnMp3ManualRepository------------");
		testLoadDToOnMp3ManualRepository(DIRECTORY_TEST_MP3);

		System.out.println("-------------------testLoadDtoOnWmaRepository------------");
		testLoadDtoOnWmaRepository(DIRECTORY_TEST_MP3);

		/*
		 * System.out.println(
		 * "-------------------testLoadDToOnMp3ManualRepository------------");
		 * testLoadDToOnMp3ManualRepository(DIRECTORY_TEST_M4A);
		 */

		/*
		 * System.out.
		 * println("-------------------test_delete_ recursif------------");
		 * test_deletefileRecursif(DIRECTORY_TEST_CLEAN);
		 */

		/*
		 * System.out.println("-------------------test_forceMKDIR------------");
		 * test_create_directory();
		 */
		/*
		 * System.out.println(
		 * "-------------------test_move_directory------------");
		 * test_move_directory();
		 * 
		 * System.out.println("-------------------test_move_file------------");
		 * test_move_file();
		 * 
		 * 
		 * System.out.println(
		 * "-------------------test_getlisteFileRecursifWalking------------");
		 * test_getlisteFileRecursifWalking(DIRECTORY_INIT_CLEAN);
		 * 
		 * System.out.println(
		 * "-------------------test_getlisteFileRecursifNatif------------");
		 * test_getlisteFileRecursifNatif(DIRECTORY_INIT_CLEAN);
		 * 
		 * System.out.println(
		 * "-------------------test_getlisteFileRecursifApache------------");
		 * test_getlisteFileRecursifApache(DIRECTORY_INIT_CLEAN);
		 */
		System.out.println("-------------------test_getFilterlisteFileRecursifApache------------");
		test_getFilterlisteFileRecursifApache(DIRECTORY_INIT_CLEAN);

		System.out.println("-------------------test_getFilterlisteFileRecursifNative------------");
		test_getFilterlisteFileRecursifNative(DIRECTORY_INIT_CLEAN);
	}
}
