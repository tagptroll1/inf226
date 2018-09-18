package inf226;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.EOFException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility functions to insulate the rest of the code from NullPointerException.
 * @author INF226
 */
public class Util {
	
	/**
	 * Read a single line from a buffered reader.
	 * @param in The buffered reader.
	 * @return The next line from the reader.
	 * @throws IOException If the stream ends before line is read.
	 */
	public static String getLine(BufferedReader in) throws IOException {
		final String s = in.readLine();
		if (s == null)
			throw new EOFException("Unexpected end of stream (" + in.toString() + ")");
		else
			return s;
	}
	
	/**
	 * Take an input from a range of integers
	 * @param prompt Promt to diplay to the user
	 * @param min The minimal valid input value.
	 * @param numChoices The number of choices
	 * @param in Reader to read values from.
	 * @return Selected value
	 * @throws IOException If the stream ends before value is selected.
	 */
	public static Integer getOption(String prompt, int min, int numChoices, BufferedReader in) throws IOException {
		Integer messageSelection = numChoices==1 ? min : null;
		while(messageSelection == null) {
			System.out.print(prompt);
			try {
				int i = Integer.parseInt(Util.getLine(in));
				if (min <= i && i < min + numChoices)
					messageSelection = i;
			} catch (NumberFormatException e) {
			}
		}
		return messageSelection;
	}

	public static String salt(SecureRandom randomer){
		byte[] byteSalt = new byte[16];
		randomer.nextBytes(byteSalt);
		return bytesToHex(byteSalt);
	}

	public static String sha256(String originalString, String salt){
		originalString = originalString + salt;
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// TODO Logging
		}

		byte[] encodedHash = digest.digest(
				originalString.getBytes(StandardCharsets.UTF_8));

		return bytesToHex(encodedHash);
	}

	private static String bytesToHex(byte[] hash){
		StringBuffer hexString = new StringBuffer();

		for(byte chr:hash){
			String hex = Integer.toHexString(0xff&chr);
			if(hex.length() == 1) hexString.append("0");
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static boolean isAscii(char ch) {
		return ch < 128;
	}

}
