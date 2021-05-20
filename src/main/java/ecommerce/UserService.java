package ecommerce;

import ecommerce.dbConnector;
import ecommerce.DatabaseUtils;
import ecommerce.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {


    private final static String QUERY = "SELECT * FROM users";


    // Used on servlets where SQL statement: "SELECT */pw FROM users..." is used
    public static User getUserByUserName(String username) {
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection = dbConnector.connectDB();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, QUERY + " WHERE username = " + username);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    User user = new User();

                    user.setUserName(resultSet.getString("username"));
                    user.setUserId(resultSet.getInt("id"));
                    user.setPassword(resultSet.getString("pw"));
                    
                    return user;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {

                    // We will always close the connection once we are done interacting with the Database.
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static boolean AddUser(User user) {

        String sql = "INSERT INTO users  (username, pw)" +
                "VALUES (?, ?)";
        
        String un = user.getUserName();
        String pw = user.getPassword();
        
        Connection connection = dbConnector.connectDB();
        return DatabaseUtils.performDBUpdate(connection, sql, un, pw);
    }
}
