package ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getCityStateServlet
 */
@WebServlet("/getCityStateServlet")
public class getCityStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
this.conn = dbConnector.connectDB();
		
		String zip = request.getParameter("zip");
		response.setContentType("text/html;charset=UTF-8");
		
		PreparedStatement st;
		try {
			PrintWriter out = response.getWriter();
			st = conn.prepareStatement("SELECT state, city FROM zipcodes WHERE zip=?");
			st.setString(1, zip);

			ResultSet result = st.executeQuery();
			
			while(result.next()){
				out.write(result.getString("city"));
				out.write(", ");
				out.write(result.getString("state"));
		     }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
