package ecommerce;

import ecommerce.DatabaseUtils;

import ecommerce.Orders;
import jakarta.servlet.UnavailableException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderService {


    private final static String ALL_ORDERS_QUERY = "SELECT * FROM orders";

    public static Orders getOrderById(int id) throws UnavailableException {
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection;
		
		connection = dbConnector.connectDB();
		
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_ORDERS_QUERY + " orderId = " + id);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Orders order = new Orders();

                    order.setOrderId(resultSet.getInt("orderId"));
                    order.setCustomerId(resultSet.getInt("customerId"));
                    order.setProductId(resultSet.getInt("productId"));
                    order.setQuantity(resultSet.getInt("quantity"));
                    
                    return order;

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

    public static List<Orders> getAllOrders() throws UnavailableException {
        List<Orders> allOrders = new ArrayList<Orders>();

        Connection connection = dbConnector.connectDB();
        ResultSet resultSet = DatabaseUtils.retrieveQueryResults(connection, ALL_ORDERS_QUERY);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Orders order = new Orders();

                    order.setOrderId(resultSet.getInt("orderId"));
                    order.setCustomerId(resultSet.getInt("customerId"));
                    order.setProductId(resultSet.getInt("productId"));
                    order.setQuantity(resultSet.getInt("quantity"));

                    allOrders.add(order);

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

        return allOrders;
    }

    public static boolean AddOrder(Orders order) throws UnavailableException {

        String sql = "INSERT INTO Orders  (customerId, productId, quantity)" +
                "VALUES (?, ?, ?)";
        
        String c = String.valueOf(order.getCustomerId());
        String p = String.valueOf(order.getProductId());
        String q = String.valueOf(order.getQuantity());
        
        Connection connection = dbConnector.connectDB();
        return DatabaseUtils.performDBUpdate(connection, sql, c, p, q);

    }

    public static boolean updateOrders(Orders order) throws UnavailableException {

        String sql = "UPDATE orders SET customerId=?, productId=?, quantity=?, WHERE orderId=?;";

        Connection connection = dbConnector.connectDB();
        
        String c = String.valueOf(order.getCustomerId());
        String p = String.valueOf(order.getProductId());
        String q = String.valueOf(order.getQuantity());
        String o = String.valueOf(order.getOrderId());

        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, c, p, q, o);

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateStatus;

    }

    public static boolean deleteOrder(Orders order) throws UnavailableException {

        String sql = "DELETE FROM orders WHERE orderId=?;";

        Connection connection = dbConnector.connectDB();

        boolean updateStatus = DatabaseUtils.performDBUpdate(connection, sql, String.valueOf(order.getOrderId()));

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateStatus;
    }
}
