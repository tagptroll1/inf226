package inf226;

import inf226.database.UserBase;

/**
 * Immutable class for users.
 * @author INF226
 *
 */
public final class User{
	
	private final String name;
	private final ImmutableLinkedList<Message> log;


	public User(final String name) {
		this.name=name;
		this.log = new ImmutableLinkedList<Message>();
	}

	private User(final String name, final ImmutableLinkedList<Message> log) {
		this.name=name;
		this.log = log;
	}
	
	/**
	 * 
	 * @return User name
	 */
	public String getName() {
		return name;
	}


	public boolean checkPassword(UserBase db, String password){
		return db.checkPassword(this.name, password);
	}

	/**
	 * @return Messages sent to this user.
	 */
	public Iterable<Message> getMessages() {
		return log;
	}



	/**
	 * Add a message to this user’s log.
	 * @param m Message
	 * @return Updated user object.
	 */
	public User addMessage(Message m) {
		return new User(name,new ImmutableLinkedList<Message>(m,log));
	}

}
