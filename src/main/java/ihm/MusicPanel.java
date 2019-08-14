package ihm;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class MusicPanel extends JPanel {

	// private JPanel loadFilePanel = new JPanel();
	private JButton loadFilebutton = new JButton("load");
	private JFileChooser musicFileChooser = new JFileChooser();
	private GridLayout fileLayout = new GridLayout();

	public MusicPanel() {

		this.setLayout(fileLayout);
		this.add(loadFilebutton);
		// this.add(musicFileChooser);

	}
}
