package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class RepositoryFile implements IRepositoryFile {

	@Override
	public void delete(String pathFileName) {
		File deleteFile = new File(pathFileName);
		if (deleteFile.exists()) {
			deleteFile.delete();

		} else {
			System.out.println("fichier a supermier existe pas " + pathFileName);
		}
	}

	@Override
	public void recursiveDelete(String pathFileName) {

		ArrayList<String> fileList = listeFilesOnDirectory(pathFileName);

		File fileItem;
		String pahtItem;

		for (int i = 0; i < fileList.size(); i++) {
			pahtItem = pathFileName + File.separator + fileList.get(i);
			fileItem = new File(pahtItem);
			if (fileItem.isDirectory()) {
				if (fileItem.list().length > 0) {
					recursiveDelete(pahtItem);
				}
			}
			if (pahtItem.contains(".gitkeep") == false) {
				delete(pahtItem);
			}
		}
	}

	@Override
	public void move(String orginalePathName, String finalPahtName) throws IOException {
		File originsfile = new File(orginalePathName);
		if (originsfile.exists()) {
			File f2 = new File(finalPahtName);

			Path pf = originsfile.toPath();
			Path pf2 = f2.toPath();

			Files.move(pf, pf2, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Override
	public void copy(String OrginalePathName, String FinalPahtName) throws IOException {
		File f = new File(OrginalePathName);
		if (f.exists()) {
			File f2 = new File(FinalPahtName);

			Path pf = f.toPath();
			Path pf2 = f2.toPath();

			Files.copy(pf, pf2, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Override
	public boolean validateDirectory(File dir) {
		boolean resultas = false;
		if (dir != null)
			if (dir.exists())
				if (dir.isDirectory())
					resultas = true;
		return resultas;
	}

	@Override
	public ArrayList<String> listeFilesOnDirectory(String dirName) {

		ArrayList<String> nomFichiers = new ArrayList<>();
		File repertoire = new File(dirName);
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repertoire.toPath())) {
			for (Path path : directoryStream) {
				nomFichiers.add(path.getFileName().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nomFichiers;
	}

	public void writeFile(String fileName, String Contenu, Boolean erase) {
		File f = new File(fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f, !erase);
			fos.write(Contenu.getBytes(), 0, Contenu.getBytes().length);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String fileName, int stop) {
		File f = new File(fileName);
		String chaine = "";
		FileInputStream fis = null;
		int n = 0;
		int avaible = 0;
		try {
			// Instanciation du FIleInputStream
			fis = new FileInputStream(f);
			// Tableau de byte taille 8 pour la lecture du flux
			byte[] buffer = new byte[8];
			n = fis.read(buffer);
			while (n >= 0) {
				for (int i = 0; i <= n - 1; i++)
					chaine = chaine + (char) buffer[i];

				avaible++;
				if (avaible >= stop)
					n = -1;
				else
					n = fis.read(buffer);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chaine;
	}

	public String readFile(String fileName) {
		File f = new File(fileName);
		String chaine = "";

		FileInputStream fis = null;
		try {
			// Instanciation du FIleInputStream
			fis = new FileInputStream(f);
			// Tableau de byte taille 8 pour la lecture du flux
			byte[] buffer = new byte[8];
			int n = 0;
			// int avaible = 0;
			while ((n = fis.read(buffer)) >= 0) {
				// avaible = fis.available();
				for (int i = 0; i <= n - 1; i++)
					chaine = chaine + (char) buffer[i];
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chaine;
	}
}
