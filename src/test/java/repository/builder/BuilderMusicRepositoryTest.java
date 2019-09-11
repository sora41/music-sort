package repository.builder;
/*
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import constant.MusicExtention;
import repository.IRepositoryMusicFile;
import repository.music.RepositoryMusicFileMp3JAudiotagger;
import repository.music.RepositoryMusicFileWmaJAudiotagger;

public class BuilderMusicRepositoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void buildMusicRepositoryTestNull() {
		BuilderMusicRepository builderRepo = new BuilderMusicRepository();
		try {
			builderRepo.buildMusicRepository(null);
			fail("echec  test build repository null");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No extentention File");
		}

	}
	
	@Test
	public void buildMusicRepositoryTestExtMp3() {
		BuilderMusicRepository builderRepo = new BuilderMusicRepository();
		IRepositoryMusicFile musicRepo =null;
		try {
		 musicRepo=	builderRepo.buildMusicRepository(MusicExtention.MP3);
		 assertTrue(musicRepo instanceof RepositoryMusicFileMp3JAudiotagger);
		} catch (Exception e) {
			fail("echec test build repository mp3");
		}

	}
	
	@Test
	public void buildMusicRepositoryTestExtWma() {
		BuilderMusicRepository builderRepo = new BuilderMusicRepository();
		IRepositoryMusicFile musicRepo =null;
		try {
		 musicRepo=	builderRepo.buildMusicRepository(MusicExtention.WMA);
		 assertTrue(musicRepo instanceof RepositoryMusicFileWmaJAudiotagger);
		} catch (Exception e) {
			fail("echec  test build repository wma");
		}

	}
	
	@Test
	public void buildMusicRepositoryTestNoExtention() {
		BuilderMusicRepository builderRepo = new BuilderMusicRepository();
		try {
		 builderRepo.buildMusicRepository(MusicExtention.NO_EXTENTION);
		 fail("echec  test build repository NoExtention");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "This Extention" + MusicExtention.NO_EXTENTION.getValue() + "is not suported");
		}

	}

}*/
