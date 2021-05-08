package ecommerce;

import java.io.*;
import java.util.*;
import java.sql.*;

/* These imports are for Glassfish versions that don't use jakarta
 * and for other application servers (Tomcat)
 */
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.UnavailableException;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet implementation class submitRatingServlet
 */
@WebServlet("/submitRatingServlet")
public class submitRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("pid"));
		int rating = Integer.parseInt(request.getParameter("rate"));
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		this.conn = dbConnector.connectDB();
		
		PreparedStatement st;
		try {
			st = conn.prepareStatement("UPDATE orders SET rating=? WHERE orderId=? and userId=? and itemId=?");
				
			st.setInt(1, rating);
			st.setInt(2, orderId);
			st.setInt(3, userId);
			st.setInt(4, productId);
		
		    st.executeUpdate();

		    st.close();
		    conn.close();
		    
		    response.sendRedirect("welcome");
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
