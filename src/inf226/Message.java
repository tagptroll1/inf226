package inf226;
import java.lang.Character;

public class Message {
	public final String sender, recipient, message;
	
	Message(final User user, final String recipient, final String message) throws Invalid {
		this.sender = user.getName();
		this.recipient = recipient;
		if (!valid(message))
			throw new Invalid(message);
		this.message = message;
	}

	public static boolean valid(String message) {
		// TODO: Implement message string validation.
		if (message.equals(".")) return false;
		else if (message.contains("\n.")) return false;

		char[] chars = message.toCharArray();
		for (char chr:chars){
			if (Character.isISOControl(chr)){
				return false;
			}
		}
		return true;
	}

	public static class Invalid extends Exception {
		private static final long serialVersionUID = -3451435075806445718L;

		public Invalid(String msg) {
			super("Invalid string: " + msg);
		}
	}
}
