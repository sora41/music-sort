package ihm;

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

public class MusicPanel2 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton startSortButton = new JButton("Start Sort");
	private JButton scanCountFilesButton = new JButton("scan dir in");

    // private JFileChooser musicFileChooser = new JFileChooser();
    // ligne/colone
	private GridLayout menuLayout = new GridLayout(0, 1);
	private GridLayout diretoryInOutLayout = new GridLayout(0, 2);
	private GridLayout diretoryOutLayout = new GridLayout(2, 0);
	private GridLayout diretoryInLayout = new GridLayout(2, 0);
	private GridLayout debugLayout = new GridLayout(0, 2);
	private GridLayout actionLayout = new GridLayout(0, 2);
	private GridLayout debugBackLayout = new GridLayout(0, 2);
	private GridLayout debugResetLayout = new GridLayout(0, 2);

	private JLabel dirOutLabel = new JLabel();
	private JLabel dirOutValueLabel = new JLabel();
	private JLabel dirInValueLabel = new JLabel();
	private JLabel dirInLabel = new JLabel();
	private JLabel dirBackLabel = new JLabel();
	private JLabel dirBackValueLabel = new JLabel();
	private JLabel loadingSortLabel = new JLabel();
	private JLabel scanCountFileLabel = new JLabel();
	private JLabel loadingResetLabel = new JLabel();
	private JLabel stepLabel = new JLabel();
	private JLabel stepValueLabel = new JLabel();

	private JProgressBar sortedBar = new JProgressBar();
	private JProgressBar resetBar = new JProgressBar();

	private JPanel panelMenue = new JPanel();
	private JPanel panelDiretoryInOut = new JPanel();
	private JPanel panelDiretoryOut = new JPanel();
	private JPanel panelDiretoryIn = new JPanel();
	//private JPanel linePanel3 = new JPanel();
	//private JPanel linePanel4 = new JPanel();
	//private JPanel linePanel5 = new JPanel();
	

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

	public MusicPanel2() {

	    //this.setLayout(line4Layout);
		// ajout evenement bouton 
		startSortButton.addActionListener(new StartSortButtonListener());
		scanCountFilesButton.addActionListener(new ScanDirInButtonListener());
		
		
		this.add(panelMenue);
		this.add(panelDiretoryInOut);
		//this.add(linePanel3);
		//this.add(linePanel4);
		//this.add(linePanel5);
		

		panelMenue.setLayout(menuLayout);
		panelDiretoryInOut.setLayout(diretoryInOutLayout);
		
		panelDiretoryInOut.add(panelDiretoryIn);
		panelDiretoryInOut.add(panelDiretoryOut);
		
		
//		linePanel3.setLayout(column4Layout);
//		linePanel4.setLayout(line2Layout);
//
		//linePanelDiretoryInOut.add(dirBackLabel);
		//linePanelDiretoryInOut.add(dirBackValueLabel);
		//panelDiretoryInOut.add(dirInLabel);
		//panelDiretoryInOut.add(dirInValueLabel);
		//panelDiretoryInOut.add(dirOutLabel);
		//panelDiretoryInOut.add(dirOutValueLabel);
//		linePanel3.add(stepLabel);
//		linePanel3.add(stepValueLabel);
//
//		linePanel4.add(startSortButton);
//		linePanel4.add(sortedBar);
//		linePanel3.add(loadingSortLabel);
//		// linePanel1.add(musicFileChooser);
//		linePanel1.add(resetFileCheckbox);
//		linePanel2.add(scanCountFilesButton);
//		linePanel2.add(scanCountFileLabel);
//		loadingSortLabel.setText("0");
//		stepLabel.setText("step:");
//		stepValueLabel.setText("aucune");
		
		musicControl = new ControlerSortMusic();
		
		initDirLabel();

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

	class StartSortButtonListener implements ActionListener {
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

	class ScanDirInButtonListener implements ActionListener {
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

	private class MusicThread implements Runnable {

		private void runProccesWithReset(boolean reset) throws IOException {
			if (reset) {
				musicControl.resetDirectory();
				musicControl.initDirectory();
			}
			musicControl.launchSort();
		}

		public void run() {
			try {
				runProccesWithReset(resetFileCheckbox.isSelected());

			} catch (Exception ex) {

				LOGGER4J.fatal("le thread c'est arrete de maniere inatendu ", ex.getClass(), ex.getMessage(),
						ex.getStackTrace());
				LOGGER4J.fatal("message :" + ex.getMessage());
				LOGGER4J.fatal("Eclass :" + ex.getClass().getName());

			}
			finally {
				startSortButton.setEnabled(true);
				scanCountFilesButton.setEnabled(true);
				stepValueLabel.setText("NONE");
			}
		}
	}
}