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
 * Servlet implementation class taxrServlet
 */
@WebServlet("/taxServlet")
public class taxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;

    public taxServlet() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.conn = dbConnector.connectDB();
		
		String zip = request.getParameter("zip");
		response.setContentType("text/html;charset=UTF-8");
		
		PreparedStatement st;
		try {
			PrintWriter out = response.getWriter();
			st = conn.prepareStatement("SELECT CombinedRate FROM taxrates WHERE ZipCode=?");
			float rate = 0.0f;
			st.setString(1, zip);

			ResultSet result = st.executeQuery();
			
			while(result.next()){
		         rate = result.getFloat("CombinedRate");
		     }
			
			out.print(rate);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
