package ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import ecommerce.HTMLbasic;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.UnavailableException;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
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
			st = conn.prepareStatement("SELECT pw FROM users WHERE username=?");
			String pw = "";
		
			st.setString(1, request.getParameter("username"));

			ResultSet result = st.executeQuery();
			
			while(result.next()){
		         pw = result.getString("pw");
		     }
		    
			response.setContentType("text/html;charset=UTF-8");
	        
	        if ( username!= "" && password != "" && pw != "" && pw.compareTo(password) == 0 ) {
	        	HttpSession session = request.getSession();
	            session.setAttribute("user", username);
	            session.setAttribute("loggedIn",true);
	            response.sendRedirect("welcome");

	        }
	        else {
	        	PrintWriter out = response.getWriter();
	        	String contents = "<h1>Invalid username or password.</h1><p class=\"center\"><a href='login.html'><button>Try Again</button></a></p>";
	        	out.println(HTMLbasic.create_page("Invalid Login",contents));
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

