package inf226;

import java.sql.*;

public class SQLiteJDBCConnection{

    private static Connection connect() throws FailedToConnectException
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
                + "username text PRIMARY KEY, "
                + "password text);";

        try(Connection conn = SQLiteJDBCConnection.connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(users);

        } catch (SQLException | FailedToConnectException e){
            System.out.println(e);
        }
    }

    public static boolean insertMember(String username, String password){
        String sql = "  INSERT INTO users(username, password)"
                    +"     VALUES($1, $2);";

        try(Connection conn = SQLiteJDBCConnection.connect();
            PreparedStatement prep = conn.prepareStatement(sql)){

            prep.setString(1, username);
            prep.setString(2, password);
            prep.execute();

        } catch(SQLException | FailedToConnectException e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public void test(){

        String table = "select * from tests";

        try(Connection conn = this.connect();
            Statement stmt = conn.createStatement()){

            stmt.execute(table);

        } catch (SQLException | FailedToConnectException e){
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

