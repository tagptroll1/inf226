package inf226.Storage;

import inf226.User;

public class Triplet {
    private final User user;
    private final String hash;
    private final String salt;

    public Triplet(User user, String hash, String salt) {
        this.user = user;
        this.hash = hash;
        this.salt = salt;
    }

    public User getUser() {
        return user;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}
