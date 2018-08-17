package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

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

}