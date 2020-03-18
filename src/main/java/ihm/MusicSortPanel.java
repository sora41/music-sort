package ihm;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.ControlerSortMusic;
import observer.Observateur;

public class MusicSortPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1734414249201747918L;

	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicSortPanel.class.getName());

	private JPanel linePanel1 = new JPanel();
	private JPanel linePanel2 = new JPanel();
	private JPanel linePanel3 = new JPanel();
	private JPanel linePanel4 = new JPanel();

	// ligne/colone
	private GridLayout line4Layout = new GridLayout(4, 0);
	private GridLayout column4Layout = new GridLayout(0, 4);
	private GridLayout line2Layout = new GridLayout(2, 0);

	private JLabel dirBackLabel = new JLabel();
	private JLabel dirBackValueLabel = new JLabel();
	private JLabel dirInValueLabel = new JLabel();
	private JLabel dirInLabel = new JLabel();
	private JLabel dirOutLabel = new JLabel();
	private JLabel dirOutValueLabel = new JLabel();

	private JLabel stepLabel = new JLabel();
	private JLabel stepValueLabel = new JLabel();
	private JLabel loadingSortLabel = new JLabel();
	private JLabel scanCountFileLabel = new JLabel();

	private JButton startSortButton = new JButton("Start Sort");
	//private JButton scanCountFilesButton = new JButton("scan dir in");

	private JProgressBar sortedBar = new JProgressBar();

	private JCheckBox resetFileCheckbox = new JCheckBox("reset des fichier ");

	private ControlerSortMusic musicControler;
	private Thread musicSorterIhmThread;

	private void initDirLabel() {
		dirBackLabel.setText("repertoire Back :");
		dirInLabel.setText("repertoire In :");
		dirOutLabel.setText("repertoire Out :");
		dirOutValueLabel.setText(musicControler.getDirectoryOut());
		dirInValueLabel.setText(musicControler.getDirectoryIn());
		dirBackValueLabel.setText(musicControler.getDirectoryBack());
	}

	private void initActionListener() {
		startSortButton.addActionListener(new StartSortButtonListener());
		//scanCountFilesButton.addActionListener(new ScanDirInButtonListener());
	}

	public MusicSortPanel() {
		this.setLayout(line4Layout);

		initActionListener();

		this.add(linePanel1);
		this.add(linePanel2);
		this.add(linePanel3);
		this.add(linePanel4);

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

		linePanel1.add(resetFileCheckbox);
		//linePanel2.add(scanCountFilesButton);
		linePanel2.add(scanCountFileLabel);

		loadingSortLabel.setText("0");
		stepLabel.setText("step:");
		stepValueLabel.setText("aucune");

		musicControler = new ControlerSortMusic();

		initDirLabel();

		this.setPreferredSize(new Dimension(300, 80));

		try {
			musicControler.initApplication();
			musicControler.getMusicSorter().addObservateur(new Observateur() {

				public void update(int enCours, int fin, String step) {

					loadingSortLabel.setText(String.valueOf(enCours));
					sortedBar.setMaximum(fin);
					sortedBar.setValue(enCours);
					stepValueLabel.setText(step);
				}
			});
		} catch (Exception e) {
			LOGGER4J.fatal("le thread c'est arrete de maniere inatendu");
			LOGGER4J.fatal("message :{}", e.getMessage());
			LOGGER4J.fatal("Eclass :{}", e.getClass().getName());
		}
	}

	private class StartSortButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			LOGGER4J.info("Debut clic sur sort");
			loadingSortLabel.setText("0");
			sortedBar.setValue(0);
			MusicSorterIhmThread noCastthreadMusic =new MusicSorterIhmThread();
			musicSorterIhmThread = new Thread(noCastthreadMusic);
			noCastthreadMusic.setMusicControler(musicControler);
			noCastthreadMusic.setStartSortButton(startSortButton);
			//noCastthreadMusic.setScanCountFilesButton(scanCountFilesButton);
			noCastthreadMusic.setResetFileCheckbox(resetFileCheckbox);
			noCastthreadMusic.setStepValueLabel(stepValueLabel);
			musicSorterIhmThread.start();
			startSortButton.setEnabled(false);
			//scanCountFilesButton.setEnabled(false);
			LOGGER4J.info(" Fin clic sur sort");
		}
	}

	private class ScanDirInButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {

			LOGGER4J.info("Debut clic scan");
			int countFiles = 0;
			try {
				countFiles = musicControler.getCountFileDirIn();
			} catch (IOException ex) {
				LOGGER4J.fatal("le thread c'est arrete de maniere inatendu ");
				LOGGER4J.fatal("message :{}", ex.getMessage());
				LOGGER4J.fatal("Eclass :'{}", ex.getClass().getName());
			}

			scanCountFileLabel.setText(String.valueOf(countFiles));
			LOGGER4J.info(" Fin clic scan");
		}
	}

}
