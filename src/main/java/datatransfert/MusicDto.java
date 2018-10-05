package datatransfert;

/**
 * class compilant les informations d'un fichier de musique
 * 
 * @author sora_41
 *
 */
public class MusicDto {

	/**
	 * nom du fichier
	 */
	private String fileName;

	/**
	 * titre de la chanson
	 */
	private String songName;

	/**
	 * nom de l'auteur
	 */
	private String author;

	/**
	 * nom de l'album
	 */
	private String album;

	/**
	 * chemin du fichier
	 */
	private String pathFile;

	/**
	 * annee de parution
	 */
	private String Years;
	

	/**
	 * genre musical
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

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
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
