package com.example.ecommerce_servlet;

import jakarta.servlet.UnavailableException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class dbConnector {
    public static Connection connectDB() throws UnavailableException {
        InitialContext ctx;
        DataSource ds;
        try {
            ctx             = new InitialContext();
            ds              = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            Connection conn = ds.getConnection();
            return conn;
        }
        catch (SQLException e) {
            throw new UnavailableException("DBConnector.connectDB() SQLException: " + e.getMessage());
        }
        catch (Exception e) {
            throw new UnavailableException("DBConnector.connectDB() newInstanceException: " + e.getMessage());
        }
    }

}
