package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class MusicPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * the loger from log4j
	 */
	private static final Logger LOGGER4J = LogManager.getLogger(MusicPanel.class.getName());

	private JButton debugButton = new JButton("debugButton");

	private BorderLayout mainBorderLayout = new BorderLayout();

	private JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);

	private MusicSortPanel ongletSort = new MusicSortPanel();
	private JPanel ongletDebug = new JPanel();
	private JPanel ongletScandir = new JPanel();

	private void initActionListener() {

		debugButton.addActionListener(new DebugButtonListener());
	}

	public MusicPanel() {

		this.setLayout(mainBorderLayout);

		initActionListener();

		this.add(onglets, BorderLayout.CENTER);

		ongletDebug.add(debugButton);

		ongletSort.setPreferredSize(new Dimension(300, 80));
		onglets.addTab("Sort", ongletSort);

		//onglets.addTab("Debug", onglet2);
		//onglets.addTab("Count file  on directory", ongletScandir);

	}

	private class DebugButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			LOGGER4J.info("Debut clic Debug ");
			boolean test = !onglets.isEnabledAt(2);
			
			onglets.setEnabledAt(2, test);
			LOGGER4J.info(" Fin clic Debug");
		}
	}

}
