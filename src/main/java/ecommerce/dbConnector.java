package ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.UnavailableException;


public class dbConnector {

    public static Connection connectDB() throws UnavailableException {
    	String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    	String DB_URL="jdbc:mysql://localhost:3306/inf124";
        String DB_USER = "root";
        String DB_PW = "mv@72137628";
        
        try {
              Class.forName(JDBC_DRIVER);
              
              return DriverManager.getConnection(DB_URL, DB_USER, DB_PW);    
              
          } catch (ClassNotFoundException e) {
              throw new UnavailableException("JDBCDemoServlet.init() ClassNotFoundException: " + e.getMessage());
          } catch (SQLException e) {
              throw new UnavailableException("JDBCDemoServlet.init() SQLException: " + e.getMessage());
          }
        
    }

}
