package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bandmaster.MusicFileBandMaster;
import ihm.MusicFrame;

public class MainThreadMusic {

	/**
	 * the application tester object deprecated (unit test incoming )
	 */
	private static TestCode test;
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MainThreadMusic.class.getName());
	/**
	 * fenetre principale de l'application 
	 */
	//private static MusicFrame mainFrame; //= new FrameMusic();
	private static ControlerSortMusic musicControl ;

	/**
	 * init application sorter et les tests
	 */
	public static void initApplication() throws SecurityException, FileNotFoundException, IOException {
		
		test = new TestCode();
		musicControl = new ControlerSortMusic();
		musicControl.initApplication();
	}

	/**
	 * launch manual test deprecied (unit test incomming )
	 * 
	 * @throws Exception
	 */
	public static void runTest() throws Exception {
		LOGGER4J.info("demarage des tests");
		test.runTest();
		LOGGER4J.info("fin des tests");
	}
	/**
	 * launch the sort operations
	 */
	public static void runProccesWithReset(boolean reset) throws IOException {
		if (reset) {
			musicControl.resetDirectory();
			musicControl.initDirectory();
		}
		musicControl.launchSort();
	}

	/** Main */
	public static void main(String[] args) {
		LOGGER4J.info("demarage de l'application");
		try {
			
			MusicFrame mainFrame = new MusicFrame();
			//initApplication();
			//runProccesWithReset(true);
			// runTest();
			
		} catch (Exception e) {

			LOGGER4J.fatal("l'application c'est arrete de maniere inatendu ", e.getClass(), e.getMessage(),
					e.getStackTrace());
			LOGGER4J.fatal("message :" + e.getMessage());
			LOGGER4J.fatal("Eclass :" + e.getClass().getName());

		}
		LOGGER4J.info("fin de l'application ");
	}
}
