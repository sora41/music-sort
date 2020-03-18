package ihm;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.ControlerSortMusic;

public class MusicSorterIhmThread implements Runnable  {
	
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicSorterIhmThread.class.getName());
	
	private ControlerSortMusic musicControler;
	
	private JCheckBox resetFileCheckbox ;
	private JButton startSortButton ;
	private JLabel stepValueLabel ;
	private JButton scanCountFilesButton;

	
	public JCheckBox getResetFileCheckbox() {
		return resetFileCheckbox;
	}

	public void setResetFileCheckbox(JCheckBox resetFileCheckbox) {
		this.resetFileCheckbox = resetFileCheckbox;
	}

	public JButton getStartSortButton() {
		return startSortButton;
	}

	public void setStartSortButton(JButton startSortButton) {
		this.startSortButton = startSortButton;
	}

	public JLabel getStepValueLabel() {
		return stepValueLabel;
	}

	public void setStepValueLabel(JLabel stepValueLabel) {
		this.stepValueLabel = stepValueLabel;
	}

	public JButton getScanCountFilesButton() {
		return scanCountFilesButton;
	}

	public void setScanCountFilesButton(JButton scanCountFilesButton) {
		this.scanCountFilesButton = scanCountFilesButton;
	}

	public ControlerSortMusic getMusicControler() {
		return musicControler;
	}

	public void setMusicControler(ControlerSortMusic musicControler) {
		this.musicControler = musicControler;
	}

	private void runProccesWithReset() throws IOException {
		if ( resetFileCheckbox != null && resetFileCheckbox.isSelected()) {
			this.musicControler.resetDirectory();
			this.musicControler.initDirectory();
		}

		this.musicControler.launchSort();
	}

	private void resetStep() {
		startSortButton.setEnabled(true);
		scanCountFilesButton.setEnabled(true);
		stepValueLabel.setText("NONE");
	}
	

	public void run() {
		try {
			runProccesWithReset();

		} catch (Exception ex) {

			LOGGER4J.fatal("le thread c'est arrete de maniere inatendu ");
			LOGGER4J.fatal("message :{}" , ex.getMessage());
			LOGGER4J.fatal("Eclass :{}" , ex.getClass().getName());

		} finally {
			resetStep();
		}
	}
	
	

}
