package inf226.database;

import inf226.Maybe;
import inf226.Storage.Id;
import inf226.Storage.Stored;
import inf226.Storage.Triplet;
import inf226.User;
import inf226.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBase extends BaseDatabase {
    private final Id.Generator id_generator;
    public UserBase() {
        id_generator = new Id.Generator();
        try {
            // Attempt to connect to generate database
            connect();
            ensureTablesExist();
        } catch (FailedToConnectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Maybe<Stored> lookup(Object key) {
        return super.lookup(key);
    }

    @Override
    public Stored save(Object value) throws IOException {
        Triplet data = (Triplet) value;

        String SQL =
                "INSERT OR FAIL INTO users(username, password, salt)"
                        + "VALUES (?, ?, ?);";
        try(Connection conn = connect();
            PreparedStatement prp = conn.prepareStatement(SQL)){

            prp.setString(1, data.getUser().getName());
            prp.setString(2, data.getHash());
            prp.setString(3, data.getSalt());

            prp.execute();
            return new Stored<>(id_generator, data.getUser());
        } catch (FailedToConnectException | SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Stored refresh(Stored old) throws ObjectDeletedException, IOException {
        return super.refresh(old);
    }

    @Override
    public Stored update(Stored old, Object newValue) throws ObjectModifiedException, ObjectDeletedException, IOException {
        return super.update(old, newValue);
    }

    @Override
    public void delete(Stored old) throws ObjectModifiedException, ObjectDeletedException, IOException {
        super.delete(old);
    }

    public boolean checkPassword(String username, String password){
        String query =
                "SELECT password, salt FROM users "
                + "WHERE username = ?;";

        try(Connection conn = connect();
            PreparedStatement prp = conn.prepareStatement(query)){

            prp.setString(1, username);
            ResultSet rs = prp.executeQuery();

            while (rs.next()) {
                String pass = rs.getString(1);
                String salt = rs.getString(2);
                String hash = Util.sha256(password, salt);
                System.out.println(hash);
                System.out.println(pass);
                return pass.equals(hash);
            }
        } catch (FailedToConnectException | SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
