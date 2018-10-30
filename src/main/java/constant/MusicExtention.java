package constant;

public enum MusicExtention { 
	MP3("mp3","repository.music.RepositoryMusicFileMp3JAudiotagger"), 
	WMA("wma","repository.music.RepositoryMusicFileWmaJAudiotagger");
	//,M4A("m4a"),MP4("mp4"),FLAC("flac");

	private String value = "";
	private String repoClass = "";

	MusicExtention(String value,String repoClass) {
		this.value = value;
		this.repoClass= repoClass;
	}

	public String getValue() {
		return this.value;
	}
	
	public String getRepoClass() {
		return this.repoClass;
	}

	public String toString() {
		return this.value;
	}
}
