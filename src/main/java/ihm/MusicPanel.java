package ihm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.ControlerSortMusic;
import main.MainThreadMusic;
import observer.Observateur;

public class MusicPanel extends JPanel {

	// private JPanel loadFilePanel = new JPanel();
	private JButton startSortButton = new JButton("Start Sort");
	private JFileChooser musicFileChooser = new JFileChooser();
	// ligne/colone
	private GridLayout gridLayout = new GridLayout(4,0);
	private GridLayout Line1Layout = new GridLayout(0,1);
	private GridLayout Line2Layout = new GridLayout(0,1);
	private JLabel dirOutLabel = new JLabel();
	private JLabel dirOutValueLabel = new JLabel();
	private JLabel dirInValueLabel = new JLabel();
	private JLabel dirInLabel = new JLabel();
	private JLabel dirBackLabel = new JLabel();
	private JLabel dirBackValueLabel = new JLabel();
	private JProgressBar sortedBar = new JProgressBar();
	private JLabel chargementLabel = new JLabel();
	//private BorderLayout mainBorder = new BorderLayout();
	private JPanel linePanel1 = new JPanel();
	private JPanel linePanel2 = new JPanel();
	private JPanel linePanel3 = new JPanel();
	private JPanel linePanel4 = new JPanel();
	//private JPanel panelNord = new JPanel();
	//private JPanel panelSouth = new JPanel();
	//private JPanel panelEast = new JPanel();
	//private JPanel panelWest = new JPanel();
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicPanel.class.getName());

	private ControlerSortMusic musicControl;
	private Thread musicSorterThread;

	public MusicPanel() {

		this.setLayout(gridLayout);

		
		startSortButton.addActionListener(new ButtonLoadListener());
		
		this.add(linePanel1);
		this.add(linePanel2);
		this.add(linePanel3);
		this.add(linePanel4);
		
		
		linePanel1.add(dirBackLabel);
		linePanel1.add(dirBackValueLabel);
		linePanel2.add(dirInLabel);
		linePanel2.add(dirInValueLabel);
		linePanel3.add(dirOutLabel);
		linePanel3.add(dirOutValueLabel);
		linePanel4.add(startSortButton);
		linePanel4.add(sortedBar);
		// this.add(musicFileChooser);
		chargementLabel.setText("0");
		musicControl = new ControlerSortMusic();
		
		dirBackLabel.setText("repertoire Back :");
		dirInLabel.setText("repertoire In :");
		dirOutLabel.setText("repertoire Out :");
		
		dirOutValueLabel.setText(musicControl.getDIRECTORY_OUT());
		dirInValueLabel.setText(musicControl.getDIRECTORY_IN());
		dirBackValueLabel.setText(musicControl.getDIRECTORY_BACK());
		
		try {

			musicControl.initApplication();
			musicControl.getMusicSorter().addObservateur(new Observateur() {

				public void update(int enCours, int fin) {

					chargementLabel.setText(String.valueOf(enCours));
					sortedBar.setMaximum(fin);
					sortedBar.setValue(enCours);
					
					if (enCours == fin) {
						startSortButton.setEnabled(true);
					}
				}
			});
		} catch (Exception e) {

			LOGGER4J.fatal("l'application c'est arrete de maniere inatendu ", e.getClass(), e.getMessage(),
					e.getStackTrace());
			LOGGER4J.fatal("message :" + e.getMessage());
			LOGGER4J.fatal("Eclass :" + e.getClass().getName());

		}
	}

	public void runProccesWithReset(boolean reset) throws IOException {
		if (reset) {
			musicControl.resetDirectory();
			musicControl.initDirectory();
		}
		musicControl.launchSort();
	}

	class ButtonLoadListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton loadButton;
			LOGGER4J.info("Debut clic sur load");
			chargementLabel.setText("0");
			sortedBar.setValue(0);
			musicSorterThread = new Thread(new MusicThread());
			musicSorterThread.start();
			loadButton = (JButton) ae.getSource();
			loadButton.setEnabled(false);
			LOGGER4J.info(" Fin clic sur load");
		}
	}

	class MusicThread implements Runnable {
		public void run() {
			try {
				runProccesWithReset(true);

			} catch (Exception ex) {

				LOGGER4J.fatal("l'thread c'est arrete de maniere inatendu ", ex.getClass(), ex.getMessage(),
						ex.getStackTrace());
				LOGGER4J.fatal("message :" + ex.getMessage());
				LOGGER4J.fatal("Eclass :" + ex.getClass().getName());

			}
		}
	}

}
