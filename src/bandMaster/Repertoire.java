package bandMaster;

import java.io.File;

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
