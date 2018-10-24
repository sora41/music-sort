package repository.music;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.Level;

import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagNotFoundException;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.ID3Frames;
import org.jaudiotagger.tag.id3.ID3Tags;
import org.jaudiotagger.tag.id3.ID3v1FieldKey;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import datatransfert.MusicDto;
import repository.IRepositoryMusicFile;

public class RepositoryMusicFileMp3JAudiotagger implements IRepositoryMusicFile{

	@Override
	public MusicDto getDataToMusicFile(String pathFileName)
			throws Exception {
		MusicDto result = null;
		MP3File mp3File = null;
	
		//if (containTag(pathFileName)) {
			mp3File = new MP3File(pathFileName);

			if (mp3File.hasID3v1Tag()) {
				result = new MusicDto();
				 
				result.setAlbum(mp3File.getID3v1Tag().getAlbum().get(0).toString());
				result.setFileName(mp3File.getFile().getName());
				result.setAuthor(mp3File.getID3v1Tag().getArtist().get(0).toString());
				result.setPathFile(mp3File.getFile().getPath());
				result.setTitleSong(mp3File.getID3v1Tag().getFirstTitle());
				result.setYears(mp3File.getID3v1Tag().getFirstYear());
				result.setGenre(mp3File.getID3v1Tag().getFirstGenre());
			} else {
				//if (mp3File.hasID3v2Tag()) {
				//	result = this.getID3DataV2(mp3File);
				//} else {
					TagNotFoundException e = new TagNotFoundException(" ID3 v1 & v2 not suported ");
		
					throw e;
					
				//}
			}
		//}
		return result;
	}

	@Override
	public boolean saveDataToMusicFile(MusicDto data) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
