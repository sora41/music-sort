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
	private String years;
	/**
	 *  musical genre 
	 */
	private String genre;
	
	/**
	 *  musical Custom 1 
	 */
	private String custom1;

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
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCustom1() {
		return custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}
	
	
	
	

}
