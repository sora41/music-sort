package constant;

public enum MusicExtention {
	MP3("mp3"), WMA("wma"),NO_EXTENTION("no_extention");

	private String value = "";

	MusicExtention(String value) {
		this.value = value;

	}

	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
