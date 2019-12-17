package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.ControlerSortMusic;
import observer.Observateur;

public class MusicPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton startSortButton = new JButton("Start Sort");
	private JButton scanCountFilesButton = new JButton("scan dir in");
	private JButton debugButton = new JButton("debugButton");
	
	private BorderLayout mainBorderLayout = new BorderLayout();

	// private JFileChooser musicFileChooser = new JFileChooser();
	// ligne/colone
	private GridLayout line4Layout = new GridLayout(4, 0);
	private GridLayout column4Layout = new GridLayout(0, 4);
	private GridLayout line2Layout = new GridLayout(2, 0);

	private JLabel dirOutLabel = new JLabel();
	private JLabel dirOutValueLabel = new JLabel();
	private JLabel dirInValueLabel = new JLabel();
	private JLabel dirInLabel = new JLabel();
	private JLabel dirBackLabel = new JLabel();
	private JLabel dirBackValueLabel = new JLabel();
	private JLabel loadingSortLabel = new JLabel();
	private JLabel scanCountFileLabel = new JLabel();

	private JLabel stepLabel = new JLabel();
	private JLabel stepValueLabel = new JLabel();

	private JProgressBar sortedBar = new JProgressBar();


	private JPanel linePanel1 = new JPanel();
	private JPanel linePanel2 = new JPanel();
	private JPanel linePanel3 = new JPanel();
	private JPanel linePanel4 = new JPanel();


	private JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);


	private JPanel onglet1 = new JPanel();

	private JPanel onglet2 = new JPanel();

	private JCheckBox resetFileCheckbox = new JCheckBox("reset des fichier ");

	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicPanel.class.getName());

	private ControlerSortMusic musicControl;
	private Thread musicSorterThread;

	private void initDirLabel() {

		dirBackLabel.setText("repertoire Back :");
		dirInLabel.setText("repertoire In :");
		dirOutLabel.setText("repertoire Out :");

		dirOutValueLabel.setText(musicControl.getDirectoryOut());
		dirInValueLabel.setText(musicControl.getDirectoryIn());
		dirBackValueLabel.setText(musicControl.getDirectoryBack());
	}

	private void initActionListener() {
		startSortButton.addActionListener(new StartSortButtonListener());
		scanCountFilesButton.addActionListener(new ScanDirInButtonListener());
		debugButton.addActionListener(new DebugButtonListener());
	}

	public MusicPanel() {

		
		onglet1.setLayout(line4Layout);
		this.setLayout(mainBorderLayout);
		
		
		initActionListener();

		onglet1.add(linePanel1);
		onglet1.add(linePanel2);
		onglet1.add(linePanel3);
		onglet1.add(linePanel4);
		this.add(onglets,BorderLayout.CENTER);
		

		linePanel1.setLayout(column4Layout);
		linePanel2.setLayout(column4Layout);
		linePanel3.setLayout(column4Layout);
		linePanel4.setLayout(line2Layout);

		linePanel1.add(dirBackLabel);
		linePanel1.add(dirBackValueLabel);
		linePanel2.add(dirInLabel);
		linePanel2.add(dirInValueLabel);
		linePanel3.add(dirOutLabel);
		linePanel3.add(dirOutValueLabel);
		linePanel3.add(stepLabel);
		linePanel3.add(stepValueLabel);

		linePanel4.add(startSortButton);
		linePanel4.add(sortedBar);
		linePanel3.add(loadingSortLabel);
		// linePanel1.add(musicFileChooser);
		linePanel1.add(resetFileCheckbox);
		linePanel2.add(scanCountFilesButton);
		linePanel2.add(scanCountFileLabel);
		onglet2.add(debugButton);
		//linePanel2.add(debugButton);
		loadingSortLabel.setText("0");
		stepLabel.setText("step:");
		stepValueLabel.setText("aucune");
		musicControl = new ControlerSortMusic();

		initDirLabel();

		
		onglet1.setPreferredSize(new Dimension(300, 80));
		onglets.addTab("onglet1title", onglet1);

		
		onglets.addTab("onglet2Tilte", onglet2);
		

		try {
			musicControl.initApplication();
			musicControl.getMusicSorter().addObservateur(new Observateur() {

				public void update(int enCours, int fin, String step) {

					loadingSortLabel.setText(String.valueOf(enCours));
					sortedBar.setMaximum(fin);
					sortedBar.setValue(enCours);
					stepValueLabel.setText(step);
				}
			});
		} catch (Exception e) {
			LOGGER4J.fatal("l'application c'est arrete de maniere inatendu ", e.getClass(), e.getMessage(),
					e.getStackTrace());
			LOGGER4J.fatal("message :" + e.getMessage());
			LOGGER4J.fatal("Eclass :" + e.getClass().getName());
		}
	}

	private class StartSortButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			LOGGER4J.info("Debut clic sur sort");
			loadingSortLabel.setText("0");
			sortedBar.setValue(0);
			musicSorterThread = new Thread(new MusicThread());
			musicSorterThread.start();
			startSortButton.setEnabled(false);
			scanCountFilesButton.setEnabled(false);
			LOGGER4J.info(" Fin clic sur sort");
		}
	}

	private class ScanDirInButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			LOGGER4J.info("Debut clic scan");
			int countFiles = 0;
			try {
				countFiles = musicControl.getCountFileDirIn();
			} catch (IOException ex) {
				LOGGER4J.fatal("le thread c'est arrete de maniere inatendu ", ex.getClass(), ex.getMessage(),
						ex.getStackTrace());
				LOGGER4J.fatal("message :" + ex.getMessage());
				LOGGER4J.fatal("Eclass :" + ex.getClass().getName());
			}

			scanCountFileLabel.setText(String.valueOf(countFiles));
			LOGGER4J.info(" Fin clic scan");
		}
	}

	private class DebugButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			LOGGER4J.info("Debut clic Debug ");
			startSortButton.setEnabled(true);
			scanCountFilesButton.setEnabled(true);
			LOGGER4J.info(" Fin clic Debug");
		}
	}

	private class MusicThread implements Runnable {

		private void runProccesWithReset(boolean reset) throws IOException {
			if (reset) {
				musicControl.resetDirectory();
				musicControl.initDirectory();
			}

			musicControl.launchSort();
		}

		private void resetStep() {
			startSortButton.setEnabled(true);
			scanCountFilesButton.setEnabled(true);
			stepValueLabel.setText("NONE");
		}

		public void run() {
			try {
				runProccesWithReset(resetFileCheckbox.isSelected());

			} catch (Exception ex) {

				LOGGER4J.fatal("le thread c'est arrete de maniere inatendu ", ex.getClass(), ex.getMessage(),
						ex.getStackTrace());
				LOGGER4J.fatal("message :" + ex.getMessage());
				LOGGER4J.fatal("Eclass :" + ex.getClass().getName());

			} finally {
				resetStep();
			}
		}
	}

}
