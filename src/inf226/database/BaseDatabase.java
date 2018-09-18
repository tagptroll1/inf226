package inf226.database;

import inf226.Maybe;
import inf226.Storage.KeyedStorage;
import inf226.Storage.Stored;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class BaseDatabase<K, C> implements KeyedStorage<K, C>
{
    private HashMap<K, C> storage = new HashMap<>();

    public BaseDatabase()
    {
        System.out.println("Hello");
    }

    @Override
    public Maybe<Stored<C>> lookup(K key)
    {
        return null;
    }

    @Override
    public Stored<C> save(C value) throws IOException {
        return null;
    }

    /**
     * Saves a username
     * @param value
     * @return
     * @throws IOException
     */
    public Stored<C> save(C value, String password, String salt) throws IOException
    {
        return null;
    }

    @Override
    public Stored<C> refresh(Stored<C> old) throws ObjectDeletedException, IOException
    {
        return null;
    }

    @Override
    public Stored<C> update(Stored<C> old, C newValue) throws ObjectModifiedException, ObjectDeletedException, IOException
    {
        return null;
    }

    @Override
    public void delete(Stored<C> old) throws ObjectModifiedException, ObjectDeletedException, IOException
    {

    }

    protected static Connection connect() throws FailedToConnectException
    {
        String url = "jdbc:sqlite:src/inf226/Storage/database.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null){
                return conn;
            }
        } catch (SQLException e){
            System.out.println("db error");
            System.out.println(e.getMessage());
        }
        throw new FailedToConnectException();
    }

    public static void ensureTablesExist(){
        String users = "CREATE TABLE IF NOT EXISTS users("
                + "username text PRIMARY KEY,"
                + "password text NOT NULL,"
                + "salt text NOT NULL);";

        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(users);

        } catch (SQLException | FailedToConnectException e){
            System.out.println(e);
        }
    }
}

class FailedToConnectException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public FailedToConnectException() {
        super("Failed to connect to database prior to call");
    }
}



