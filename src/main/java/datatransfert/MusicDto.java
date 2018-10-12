package datatransfert;

/**
 * class compilant les informations d'un fichier de musique
 * 
 * @author sora_41
 *
 */
public class MusicDto {
	/**
	 *  file name
	 */
	private String fileName;
	/**
	 * title song's
	 */
	private String titleSong;
	/**
	 *  author name
	 */
	private String author;
	/**
	 * title Album
	 */
	private String album;
	/**
	 * path file 
	 */
	private String pathFile;
	/**
	 * edition years
	 */
	private String Years;
	/**
	 *  musical genre 
	 */
	private String genre;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getTitleSong() {
		return titleSong;
	}

	public void setTitleSong(String titleSong) {
		this.titleSong = titleSong;
	}

	public String getYears() {
		return Years;
	}

	public void setYears(String years) {
		Years = years;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	
	

}
