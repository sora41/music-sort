package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JLabel enCoursLabel = new JLabel(); 
	private JLabel nbMaxLabel = new JLabel(); 
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicPanel.class.getName());

	private ControlerSortMusic musicControl;
	private Thread musicSorterThread;

	public MusicPanel() {

		this.setLayout(fileLayout);
		loadFilebutton.addActionListener(new ButtonLoadListener());
		this.add(loadFilebutton);
		this.add(nbMaxLabel);
		this.add(enCoursLabel);
		// this.add(musicFileChooser);
		musicControl = new ControlerSortMusic();

		try {

			musicControl.initApplication();
			musicControl.getMusicSorter().addObservateur(new Observateur() {
				
				public void update(int enCours, int fin) {
					loadFilebutton.setEnabled(true);
					enCoursLabel.setText(String.valueOf(enCours));
					nbMaxLabel.setText(String.valueOf(fin));
					
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
			nbMaxLabel.setText("0");
			enCoursLabel.setText("0");
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
