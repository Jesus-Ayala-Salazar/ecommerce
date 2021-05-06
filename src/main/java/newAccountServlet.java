import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.Cookie;


/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/newAccountServlet")
public class newAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.conn = dbConnector.connectDB();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		PreparedStatement st;
		try {
			st = conn.prepareStatement("INSERT INTO users (username, pw) VALUES (?, ?)");
		
			st.setString(1, username);
			  
	        // Same for second parameter
	        st.setString(2, password);
	
	        // Execute the insert command using executeUpdate()
	        // to make changes in database
	        st.executeUpdate();
	
	        // Close all the connections
	        st.close();
	        conn.close();
	
	        // Get a writer pointer 
	        // to display the successful result
	        PrintWriter out = response.getWriter();
	        
	        Cookie loginCookie = new Cookie("user",username);
        	loginCookie.setMaxAge(45*60); // login session = 45min
			
			response.addCookie(loginCookie);
			response.sendRedirect("LoginSuccess.jsp");
			
	        out.println("<html><body><b>New Account Created!"
	                    + "</b></body></html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		if( username == "aaa" && password == "no" ) {
			Cookie loginCookie = new Cookie("user",username);
			
			loginCookie.setMaxAge(45*60); // login session = 45min
			
			response.addCookie(loginCookie);
			response.sendRedirect("LoginSuccess.jsp");
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Incorrect username or password.</font>");
			rd.include(request, response);
		}
		*/

	}
}

