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
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn;
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	final String DB_URL="jdbc:mysql://localhost:3306/inf124";
    private String DB_USER = "root";
    private String DB_PW = "mv@72137628";
    
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
			st = conn.prepareStatement("SELECT pw FROM users WHERE username=?");
			String pw = "";
		
			st.setString(1, request.getParameter("username"));

			ResultSet result = st.executeQuery();
			
			while(result.next()){
		         pw = result.getString("pw");

		         //Display values
		         System.out.println("Password: " + pw + " | " + password);
		     }
		    
	
	        // Get a writer pointer 
	        // to display the successful result
	        PrintWriter out = response.getWriter();
	        if ( pw.compareTo(password) == 0 ) {
	        	Cookie loginCookie = new Cookie("user",username);
	        	loginCookie.setMaxAge(45*60); // login session = 45min
				
				response.addCookie(loginCookie);
				response.sendRedirect("LoginSuccess.jsp");
				
	        	out.println("<html><body><b>Logged in!"
	                    + "</b></body></html>");
	        }
	        else {
	        	out.println("<html><body><p><b>Incorrect username or password.</b></p>"
	        			+ "<p><a href='/login'><button>Try Again</button></a></p></body></html>");
	        }
	        
	        result.close();
	        st.close();
	        conn.close();
	        
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

