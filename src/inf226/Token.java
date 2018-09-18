package inf226;

import java.security.SecureRandom;
import java.util.Base64;

public final class Token {
	private final SecureRandom random = new SecureRandom();
	private final byte[] bytes;
	// TODO: This should be an immutable class representing a token.

	/**
	 * The constructor should generate a random 128 bit token
	 */
	public Token(){
		// TODO:  generate a random 128 bit token

		bytes = new byte[128];
		random.nextBytes(bytes);
	}
	
	/**
	 * This method should return the Base64 encoding of the token
	 * @return A Base64 encoding of the token
	 */
	public String stringRepresentation() {
		return Base64.getEncoder().encodeToString(this.bytes);
	}
}
