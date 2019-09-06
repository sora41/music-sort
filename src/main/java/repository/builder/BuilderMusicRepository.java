package repository.builder;

import constant.MusicExtention;
import repository.IRepositoryMusicFile;
import repository.music.RepositoryMusicFileMp3JAudiotagger;
import repository.music.RepositoryMusicFileWmaJAudiotagger;


public class BuilderMusicRepository {

	public IRepositoryMusicFile buildMusicRepository(MusicExtention ext) throws Exception {
		if (ext == null) {
			throw new Exception("No extentention File");
		} else {

			switch (ext) {
			case MP3:
				return new RepositoryMusicFileMp3JAudiotagger();

			case WMA:
				return new RepositoryMusicFileWmaJAudiotagger();

			default:
				throw new Exception("This Extention" + ext.getValue() + "is not suported");

			}
		}
	}

}
