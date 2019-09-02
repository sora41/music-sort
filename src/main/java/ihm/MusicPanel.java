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
	private JButton loadFilebutton = new JButton("load");
	private JFileChooser musicFileChooser = new JFileChooser();
	private GridLayout fileLayout = new GridLayout();
	private JLabel dirOutLabel = new JLabel();
	private JLabel dirOutValueLabel = new JLabel();
	private JLabel dirInValueLabel = new JLabel();
	private JLabel dirInLabel = new JLabel();
	private JLabel dirBackLabel = new JLabel();
	private JLabel dirBackValueLabel = new JLabel();
	private JProgressBar sortedBar = new JProgressBar();
	//private BorderLayout mainBorder = new BorderLayout();
	private JPanel panelCenter = new JPanel();
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

		this.setLayout(fileLayout);
		
		
		
		loadFilebutton.addActionListener(new ButtonLoadListener());
		this.add(loadFilebutton,BorderLayout.WEST);
		this.add(panelCenter,BorderLayout.CENTER);
		panelCenter.add(dirOutLabel,BorderLayout.CENTER);
		panelCenter.add(dirOutValueLabel,BorderLayout.CENTER);
		panelCenter.add(dirBackLabel,BorderLayout.CENTER);
		panelCenter.add(dirBackValueLabel,BorderLayout.CENTER);
		panelCenter.add(dirInLabel,BorderLayout.CENTER);
		panelCenter.add(dirInValueLabel,BorderLayout.CENTER);
		this.add(sortedBar,BorderLayout.SOUTH);
		// this.add(musicFileChooser);
		musicControl = new ControlerSortMusic();
		
		dirBackLabel.setText("repertoire Back ");
		dirInLabel.setText("repertoire In");
		dirOutLabel.setText("repertoire Out ");
		
		dirOutValueLabel.setText(musicControl.getDIRECTORY_OUT());
		dirInLabel.setText(musicControl.getDIRECTORY_IN());
		dirBackLabel.setText(musicControl.getDIRECTORY_BACK());
		
		try {

			musicControl.initApplication();
			musicControl.getMusicSorter().addObservateur(new Observateur() {

				public void update(int enCours, int fin) {

					//enCoursLabel.setText(String.valueOf(enCours));
					//nbMaxLabel.setText(String.valueOf(fin));
					sortedBar.setMaximum(fin);
					sortedBar.setValue(enCours);
					
					if (enCours == fin) {
						loadFilebutton.setEnabled(true);
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
			//nbMaxLabel.setText("0");
			//enCoursLabel.setText("0");
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
