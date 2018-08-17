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
	public void delete(String pathFileName) throws FileNotFoundException {
		File deleteFile = new File(pathFileName);
		if (deleteFile.exists()) {
			
			if (deleteFile.delete() == true) {
				System.out.println("supresion "+ pathFileName+" reussi");
			} else {
				System.out.println("supresion "+ pathFileName+" echec");
			}
		} else {
			FileNotFoundException e = new FileNotFoundException("fichier a suprimer existe pas " + pathFileName);
			;
			throw e;
		}
	}

	@Override
	public void recursiveDelete(String pathFileName) throws IOException {

		System.out.println("lancement delete recursif sur le repertoire ");
		System.out.println(pathFileName);
		
		ArrayList<String> fileList = listeFilesOnDirectory(pathFileName);
		File fileItem;
		String pahtItem;
		int taille = fileList.size();
		System.out.println("contient" + taille);
		for (int i = 0; i < fileList.size(); i++) {
			pahtItem = pathFileName + File.separator + fileList.get(i);
			fileItem = new File(pahtItem);
			if (fileItem.isDirectory()) {
				if (fileItem.list().length > 0) {
					recursiveDelete(pahtItem);
				}
			}
			if (pahtItem.contains(".gitkeep") == false) {
				System.out.println("delete" + pahtItem);
				delete(pahtItem);
			}
		}
	}

	@Override
	public void move(String orginalePathName, String finalPahtName) throws IOException {
		File originsfile = new File(orginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(finalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.move(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Override
	public void copy(String OrginalePathName, String FinalPahtName) throws IOException {
		File originsfile = new File(OrginalePathName);
		if (originsfile.exists()) {
			File finalFile = new File(FinalPahtName);

			Path patheOrginsFile = originsfile.toPath();
			Path pathFinalFile = finalFile.toPath();

			Files.copy(patheOrginsFile, pathFinalFile, StandardCopyOption.REPLACE_EXISTING);
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
	public ArrayList<String> listeFilesOnDirectory(String dirName) throws IOException {

		ArrayList<String> nomFichiers = new ArrayList<>();
		File repertoire = new File(dirName);

		if (repertoire.exists()) {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repertoire.toPath());
			for (Path path : directoryStream) {
				nomFichiers.add(path.getFileName().toString());
			}
		} else {
			FileNotFoundException e = new FileNotFoundException(
					"repertoire: " + repertoire.getAbsolutePath() + " introuvable");
			throw e;
		}
		return nomFichiers;
	}

	public void writeFile(String fileName, String Contenu, Boolean erase) {
		File file = new File(fileName);
		FileOutputStream fileOutPutStream = null;
		try {
			fileOutPutStream = new FileOutputStream(file, !erase);
			fileOutPutStream.write(Contenu.getBytes(), 0, Contenu.getBytes().length);
			fileOutPutStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String fileName, int stop) {
		File file = new File(fileName);
		String chaine = "";
		FileInputStream fileInputStream = null;
		int n = 0;
		int avaible = 0;
		try {
			// Instanciation du FIleInputStream
			fileInputStream = new FileInputStream(file);
			// Tableau de byte taille 8 pour la lecture du flux
			byte[] buffer = new byte[8];
			n = fileInputStream.read(buffer);
			while (n >= 0) {
				for (int i = 0; i <= n - 1; i++)
					chaine = chaine + (char) buffer[i];

				avaible++;
				if (avaible >= stop)
					n = -1;
				else
					n = fileInputStream.read(buffer);
			}
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chaine;
	}

	public String readFile(String fileName) {
		File file = new File(fileName);
		String chaine = "";

		FileInputStream fileInputStream = null;
		try {
			// Instanciation du FIleInputStream
			fileInputStream = new FileInputStream(file);
			// Tableau de byte taille 8 pour la lecture du flux
			byte[] buffer = new byte[8];
			int n = 0;
			while ((n = fileInputStream.read(buffer)) >= 0) {
				for (int i = 0; i <= n - 1; i++)
					chaine = chaine + (char) buffer[i];
			}
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chaine;
	}
}
