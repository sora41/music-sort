package ihm;

import javax.swing.JFrame;

public class MusicFrame extends JFrame {
	private MusicPanel musicPanel = new MusicPanel();

	public MusicFrame()
		  {  
		     super("MusicSorter");
		     this.setSize(500,500); 
		     this.setLocationRelativeTo(null);
		     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     this.setContentPane(musicPanel);
		     
		     this.setVisible(true);
		  }

}
