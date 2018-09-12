package inf226;

import inf226.Storage.KeyedStorage;
import inf226.Storage.Stored;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;

public class DataBaseUserStorage<K, C> implements KeyedStorage<K, C>
{
    public DataBaseUserStorage()
    {
        System.out.println("Hello");
    }

    @Override
    public Maybe<Stored<C>> lookup(K key)
    {
        return null;
    }

    @Override
    public Stored<C> save(C value) throws IOException
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
}

class SQLiteJDBCConnection{

    private Connection connect() throws FailedToConnectException
    {
        String url = "jdbc:sqlite:src/inf226/Storage/messages.db";
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

    public void test(){
        String table = "CREATE TABLE IF NOT EXISTS tests("
                + "id integer PRIMARY KEY, "
                + "name text NOT NULL, "
                + "capacity real default 0"
                + ");";

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(table);

        } catch (SQLException | FailedToConnectException e){
            System.out.println(e);
        }

        String insert = "INSERT INTO tests(name, capacity) VALUES (?, ?)";

        try(Connection conn = this.connect();
            PreparedStatement prep = conn.prepareStatement(insert))
        {
            prep.setString(1, "LOL");
            prep.setInt(2, 454);
            prep.execute();

        } catch (SQLException | FailedToConnectException e){
            System.out.println(e);
        }


        String sql = "SELECT * FROM tests";

        try(Connection conn = this.connect();
            PreparedStatement prep = conn.prepareStatement(sql)
        ){
            ResultSet res = prep.executeQuery();
            while (res.next()){
                System.out.println(res.getInt("id") + "\t" +
                                   res.getString("name") + "\t" +
                                   res.getInt("capacity")
                                    );
            }

        } catch (FailedToConnectException | SQLException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException
    {
        SQLiteJDBCConnection sql = new SQLiteJDBCConnection();
        sql.test();
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
