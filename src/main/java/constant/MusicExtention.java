package constant;

public enum MusicExtention { MP3("mp3"), WMA("wma");//,M4A("m4a"),MP4("mp4"),FLAC("flac");

	private String value = "";

	MusicExtention(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String toString() {
		return this.value;
	}
}
