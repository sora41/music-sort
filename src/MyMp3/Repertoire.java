package MyMp3;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Repertoire {

	public boolean renameFile(String OrginaleName, String FinalName) {
		boolean resultas = false;

		File f = new File(OrginaleName);
		File f2 = new File(FinalName);
		if (f.renameTo(f2))
			resultas = true;
		return resultas;
	}

}
