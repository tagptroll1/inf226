package inf226;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.function.Function;

import inf226.Storage.KeyedStorage;
import inf226.Storage.Storage.ObjectDeletedException;
import inf226.Storage.Stored;
import inf226.Storage.TransientStorage;
import inf226.Storage.Triplet;
import inf226.database.UserBase;
import inf226.exceptions.InvalidInputException;

/**
 * 
 * The Server main class. This implements all critical server functions.
 * 
 * @author INF226
 *
 */
public class Server {
	private static SecureRandom random = new SecureRandom();
	private static UserBase userdb = new UserBase();
	private static final int portNumber = 1337;
	private static final KeyedStorage<String,User> storage
	  = new TransientStorage<String,User>
	         (new Function<User,String>()
	        		 {public String apply(User u)
	        		 {return u.getName();}});
	
	public static Maybe<Stored<User>> authenticate(String username, String password) {
		// TODO: Implement user authentication
		try {
			User user = new User(username);
			if (user.checkPassword(userdb, password)){
				return Maybe.just(storage.save(user));
			}
		}catch (IOException ex){
				ex.printStackTrace();
		}


		return Maybe.nothing();
	}

	public static Maybe<Stored<User>> register(String username, String password) {
		// TODO: Implement user registration
		String salt = Util.salt(random);
		String pass = Util.sha256(password, salt);
		password = null;

		// Hashing has to happen here to pass the salt on given the signature of the task..
		//UserBase.registerUser(username, pass, salt);

		User user = new User(username);
		try{
            userdb.save(new Triplet(user, pass, salt));
            System.out.println(pass);
			return Maybe.just(storage.save(user));
		} catch (IOException e){
			System.out.println(e);

		}

		return Maybe.nothing();
	}
	
	public static Maybe<Token> createToken(Stored<User> user) {
		// TODO: Implement token creation
		return Maybe.nothing();
	}
	public static Maybe<Stored<User>> authenticate(String username, Token token) {
		// TODO: Implement user authentication
		return Maybe.nothing();
	}

	public static Maybe<String> validateUsername(String username){
		// TODO: Validate username before returning
		String pattern = "[a-zA-Z0-9]{4,32}";
		boolean match = username.matches(pattern);
		if (!match) return Maybe.nothing();

		return Maybe.just(username);
	}

	public static Maybe<String> validatePassword(String pass){
		// TODO: Validate pass before returning
		String pattern = "[a-zA-Z0-9.,:;()\\[\\]{}<>\"'#!$%&/+*?=\\-_|]{8,40}";
		boolean match = pass.matches(pattern);

		if (!match) return Maybe.nothing();

		return Maybe.just(pass);
	}

	public static boolean sendMessage(Stored<User> sender, Message message) {
		// TODO: Implement the message sending.
		return false;
	}
	
	/**
	 * Refresh the stored user object from the storage.
	 * @param user
	 * @return Refreshed value. Nothing if the object was deleted.
	 */
	public static Maybe<Stored<User>> refresh(Stored<User> user) {
		try {
			return Maybe.just(storage.refresh(user));
		} catch (ObjectDeletedException e) { 
		} catch (IOException e) { 
		}
		return Maybe.nothing();
	}
	
	/**
	 * @param args TODO: Parse args to get port number
	 */
	public static void main(String[] args) {
		final RequestProcessor processor = new RequestProcessor();
		System.out.println("Staring authentication server");
		processor.start();
		try (final ServerSocket socket = new ServerSocket(portNumber)) {
            while(!socket.isClosed()) {
            	System.err.println("Waiting for client to connect…");
        		Socket client = socket.accept();
            	System.err.println("Client connected.");
        		processor.addRequest(new RequestProcessor.Request(client));
			}
		} catch (IOException e) {
			System.out.println("Could not listen on port " + portNumber);
			e.printStackTrace();
		}
	}


}
