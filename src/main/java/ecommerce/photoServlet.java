package ecommerce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class photoServlet
 */
@WebServlet("/photoServlet")
public class photoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.conn = dbConnector.connectDB();
		
		try {
	        PreparedStatement stmt = conn.prepareStatement("select productImage from products where productId=?");
	        stmt.setInt(1, Integer.valueOf(request.getParameter("id")));
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	        	Blob blob = rs.getBlob("productImage");
                InputStream inputStream = blob.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                inputStream.close();
                outputStream.close();
	            response.getOutputStream().write(imageBytes);
	        }
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
