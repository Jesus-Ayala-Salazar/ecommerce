package ecommerce;

import ecommerce.DatabaseUtils;
import ecommerce.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {


    private final static String ALL_PRODUCTS_QUERY = "SELECT * FROM products";

    public static Product getProductById(int id) {
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection = dbConnector.connectDB();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_PRODUCTS_QUERY + " WHERE productId = " + id);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Product p = new Product();

                    p.setId(resultSet.getInt("productId"));
                    p.setDescription(resultSet.getString("description"));
                    p.setPrice(resultSet.getDouble("price"));
                    p.setName(resultSet.getString("name"));
                    p.setUrl(resultSet.getString("productURL"));

                    return p;

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

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();

        Connection connection = dbConnector.connectDB();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_PRODUCTS_QUERY);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Product p = new Product();

                    p.setId(resultSet.getInt("productId"));
                    p.setDescription(resultSet.getString("description"));
                    p.setPrice(resultSet.getDouble("price"));
                    p.setName(resultSet.getString("name"));
                    p.setUrl(resultSet.getString("productURL"));


                    products.add(p);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return products;
    }

    public static boolean AddProduct(Product p) {

        String sql = "INSERT INTO products  (description, price, name, productURL)" +
                "VALUES (?, ?, ?, ?)";
        
        
        
        Connection connection = dbConnector.connectDB();
        return DatabaseUtils.performDBUpdate(connection, sql, p.getDescription(), String.valueOf(p.getPrice()), p.getName(), p.getUrl());

    }

    public static boolean updateProduct(Product p) {

        String sql = "UPDATE products SET description=?, price=?, name=?, productURL=? WHERE productId=?;";

        Connection connection = dbConnector.connectDB();

        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, p.getDescription(), String.valueOf(p.getPrice()), p.getName(), p.getUrl(),
                String.valueOf(p.getId()));

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateStatus;

    }

    public static boolean deleteProduct(Product p) {

        String sql = "DELETE FROM products WHERE productId=?;";

        Connection connection = dbConnector.connectDB();

        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, String.valueOf(p.getId()));

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateStatus;
    }
}
