package repository.music;


import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.asf.AsfTag;

import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;

public class RepositoryMusicFileWmaJAudiotagger implements IRepositoryMusicFile {
	
		
	@Override
	public MusicDto getDataToMusicFile(String pathFileName) throws Exception {
		MusicDto wma = null;
		File song = new File(pathFileName);
		AudioFile audioFi = AudioFileIO.read(song);
		
		AsfTag tagWma =  (AsfTag) audioFi.getTag();
		
		System.out.println("taille tag"+tagWma.getFieldCount());
		
		
		 /* wma  = null;
		this.shutupLog();
		// if (containTag(pathFileName)) {
		mp3File = new MP3File(pathFileName);
		if (mp3File.hasID3v1Tag()) {
			result = this.getID3DataV1(mp3File);
		} else {
			if (mp3File.hasID3v2Tag()) {
				result = this.getID3DataV2(mp3File);
			} else {
				TagNotFoundException e = new TagNotFoundException(" ID3 v1 & v2 not suported ");
				throw e;
			}
		}
		// }*/
		return wma;
	}

	@Override
	public boolean saveDataToMusicFile(MusicDto data) throws Exception {
		Exception e = new Exception("fonction non implementer");
		throw e;
	}

	private void shutupLog() {
		Logger[] pin;
		pin = new Logger[] { Logger.getLogger("org.jaudiotagger") };

		for (Logger l : pin)
			l.setLevel(Level.OFF);
	}

}
