package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;

public class TestMp3 {

	private String convertCharset(String text) {
		Charset charset = Charset.forName("ISO-8859-1");
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		try {
			ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(text));
			CharBuffer cbuf = decoder.decode(bbuf);
			return cbuf.toString();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void loadMp3Id3(String fileName) throws IOException, TagException {
		MP3File mp3file = new MP3File(fileName);
		AbstractID3v2 tag = mp3file.getID3v2Tag();
		String title = tag.getSongTitle();

		System.out.println("Title : " + convertCharset(title));
		System.out.println(mp3file.getID3v1Tag().getTitle());
		/*
		 * if (mp3file.hasID3v1Tag()) { System.out.println("ID3v1"); if
		 * (mp3file.hasID3v2Tag()) System.out.println("ID3v2"); }
		 */

	}

	public void loadMp3Mannuel(String fileName) {
		try {
			File song = new File(fileName);
			FileInputStream file = new FileInputStream(song);
			int size = -1;
			if (song.canRead())
				size = (int) song.length();
			try {

				System.out.println(fileName + " taille " + size);
				if (size > 0) {

					file.skip(size - 128);
					byte[] last128 = new byte[128];
					file.read(last128);
					String id3 = new String(last128);
					String tag = id3.substring(0, 3);
					if (tag.equals("TAG")) {

						System.out.println("Title: " + id3.substring(3, 32));
						System.out.println("Artist: " + id3.substring(33, 62));
						System.out.println("Album: " + id3.substring(63, 91));
						System.out.println("Year: " + id3.substring(93, 97));
					} else
						System.out.println(" does not contain" + " ID3 information.");
				} else {
					IOException e = new IOException("Size file zero");
					throw e;
				}
			} finally {
				file.close();
			}

		} catch (Exception e) {
			System.out.println("Error - " + e.toString());
			// e.printStackTrace();
		}
	}

	public String LireFichier(String fileName, int stop) {
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

	public String LireFichier(String fileName) {
		File f = new File(fileName);
		String chaine = "";
		// if (f.exists()) {
		FileInputStream fis = null;
		try {
			// Instanciation du FIleInputStream
			fis = new FileInputStream(f);
			// Tableau de byte taille 8 pour la lecture du flux
			byte[] buffer = new byte[8];
			int n = 0;
			int avaible = 0;
			while ((n = fis.read(buffer)) >= 0) {
				avaible = fis.available();
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

	public void ecrireFichier(String fileName, String Contenu, Boolean erase) {
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
}
