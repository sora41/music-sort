package ihm;

import javax.swing.JFrame;

public class MusicFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MUSIC_FRAME_NAME ="MusicSorter";
	
	//private MusicPanel2 musicPanel2 = new MusicPanel2();
	private MusicPanel musicPanel = new MusicPanel();

	public MusicFrame()
		  {  
		     super(MUSIC_FRAME_NAME);
		     this.setSize(500,500); 
		     this.setLocationRelativeTo(null);
		     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     this.setContentPane(musicPanel);
		     
		     this.setVisible(true);
		  }

}
