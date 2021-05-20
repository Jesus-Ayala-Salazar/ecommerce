package ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import ecommerce.UserResource;

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

// RESTful Web Services
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

// Jersey
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import org.glassfish.jersey.client.ClientConfig;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Connection conn;
    
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

		// this.conn = dbConnector.connectDB();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// PreparedStatement st;
		try {
			// Replace with API calls
			// st = conn.prepareStatement("SELECT pw FROM users WHERE username=?");

			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI());

			String jsonResponse =
                target.path("v1").path("api").path("todos").path(username).
                        request(). //send a request
                        accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                        get(String.class); // use the get method and return the response as a string

			ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library

			User user = objectMapper.readValue(jsonResponse, User.class);

			String pw = user.getPassword();
			int id	 = user.getUserId();
		
			// st.setString(1, request.getParameter("username"));

			// ResultSet result = st.executeQuery();
			
			// while(result.next()){
		   //       pw = result.getString("pw");
		   //   }
		    
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
	        
	      //   result.close();
	      //   st.close();
	      //   conn.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static URI getBaseURI() {

		//Change the URL here to make the client point to your service.
		return UriBuilder.fromUri("http://localhost:8080/UserService").build();
  }
}

