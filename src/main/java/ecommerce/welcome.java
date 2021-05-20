package ecommerce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import ecommerce.HTMLbasic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class welcome
 */
@WebServlet("/welcome")
public class welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        this.conn = dbConnector.connectDB();
        
        session.setMaxInactiveInterval(60*15); // 15 minute session memory
        
        sessionCart cart = new sessionCart((String)session.getAttribute("user"));
        session.setAttribute("cart", cart);
        
        // Head
        out.println("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<meta name=\"description\" content=\"Ecommerce website\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n");
        out.println("<link rel=\"stylesheet\" href=\"css/style.css\">\n");
        out.println("<title>Welcome</title>\n");
        out.println("</head>\n");

        //  Body
        out.println("<body>\n");
        out.println("<main>\n");
        out.println("<script src=\"index.js\"></script>\n");
        out.println("<script src=\"index.js\"></script>\n");

        // Top bar
        out.println("<div class=\"navbar\">\n");
        out.println("<a href=\"home.html\">Early Birds</a>\n");
        out.println("<button onclick=\"openMenu()\" class=\"navbar-button\"><em class=\"material-icons sz-36 menu-button\" id=\"menu-button\">menu</em></button>\n");
        out.println("</div>\n");

        // Collapsable menu
        out.println("<div id=\"menu\" class=\"menu\">\n");
        out.println("<button onclick=\"closeMenu()\" class=\"navbar-button\"><em class=\"material-icons sz-36 open-menu-button\" id=\"open-menu-button\">close</em></button>\n");

        // Side menu
        out.println("<h3><a href=\"home.html\" onclick=\"closeMenu()\">Home</a></h3>\n");
        out.println("<h3><a href=\"productsPage.jsp\" onclick=\"closeMenu()\">Products</a></h3>\n");
        out.println("<h3><a href=\"team.html\" onclick=\"closeMenu()\">Team</a></h3>\n");
        out.println("<h3><a href=\"login.html\" onclick=\"closeMenu()\">Login / Create Account</a></h3>\n");
        out.println("</div>\n");

        // H1
        String user = (String)session.getAttribute("user");
        out.println("<h1>Hello, " + user + ".</h1><p class=\"center\"><a href='productsPage.jsp'>Click here</a> to begin shopping.</p>" 
        		+ "<p class=\"center\"><a href=\"logoutServlet\"><button>Log Out</button></a></p>");
        
        // Previously Purchased Table
        out.println("<h2 class=\"center\">Rate Previously Purchased Products</h2>");
        out.println("<div class=\"center\">");
        out.println("<table style=\"width:90%\" class=\"center\">\n" +
                "        <colgroup>\n" +
                "            <col style=\"width: 25%\">\n" +
                "            <col style=\"width: 33.33%\">\n" +
                "            <col style=\"width: 25%\">\n" +
                "            <col style=\"width: 16.66%\">\n" +
                "        </colgroup>\n");

        PreparedStatement uid;
        PreparedStatement lastFive;
        try {
        	// get userId given username
        	uid = conn.prepareStatement("SELECT * FROM users WHERE username=\"" + user + "\"");
            
            ResultSet result = uid.executeQuery();
    		
            int userId = 0;
            int count = 1;
            while(result.next()){
            	userId = result.getInt("userId");
		     }
        	
        	// with userId, get last 5 ordered products
    		lastFive = conn.prepareStatement("SELECT * FROM orders WHERE userId=? ORDER BY orderId DESC LIMIT 5"); 
    		lastFive.setInt(1, userId);
            ResultSet rs = lastFive.executeQuery();

            while(rs.next()) {       
               //Retrieve by column name
               int r = rs.getInt("rating");
               int iid = rs.getInt("itemId"); 
               int orderid = rs.getInt("orderId"); 
               
               out.println("<tr>\n");
               
               // get item info...
               PreparedStatement item = conn.prepareStatement("SELECT productImage, name FROM products WHERE productId =?");
               item.setInt(1, iid);
               ResultSet itemInfo = item.executeQuery();

               while(itemInfo.next()){
            	   String itemName = itemInfo.getString("name");
            	   Blob blob = itemInfo.getBlob("productImage");
                   InputStream inputStream = blob.getBinaryStream();
                   ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                   byte[] buffer = new byte[4096];
                   int bytesRead = -1;
                   while ((bytesRead = inputStream.read(buffer)) != -1) {
                       outputStream.write(buffer, 0, bytesRead);
                   }
                   byte[] imageBytes = outputStream.toByteArray();
                   String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                   inputStream.close();
                   outputStream.close();
                   
                   
                   out.println("<td> <img class=\"product-img\" src=\"data:image/jpg;base64," + base64Image + "\"> </td>\n");
                   out.println("<td>" + itemName + "</td>\n");
                   out.println("<td>");
   		     	}
               
               // begin form for rating an item
              
               out.println("<form class=\"rating rate\" action=\"submitRatingServlet\" method=\"post\">");
               out.println("	<input type=\"number\" name=\"pid\" value=\"" + iid + "\" style=\"display:none\">\r\n");
               out.println("	<input type=\"number\" name=\"orderId\" value=\"" + orderid + "\" style=\"display:none\">\r\n");
               out.println("	<input type=\"number\" name=\"userId\" value=\"" + userId + "\" style=\"display:none\">\r\n");
               
               //Display values
               if (r == 1) {
            	   out.println("<input type=\"radio\" id=\"entry" + count + "-star5\" name=\"rate\" value=\"5\"  >");
                   out.println("<label for=\"entry" + count + "-star5\" title=\"text\">5 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star4\" name=\"rate\" value=\"4\" >");
                   out.println("<label for=\"entry" + count + "-star4\" title=\"text\">4 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star3\" name=\"rate\" value=\"3\" >");
                   out.println("<label for=\"entry" + count + "-star3\" title=\"text\">3 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star2\" name=\"rate\" value=\"2\" >");
                   out.println("<label for=\"entry" + count + "-star2\" title=\"text\">2 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star1\" name=\"rate\" value=\"1\" checked>");
                   out.println("<label for=\"entry" + count + "-star1\" title=\"text\">1 star</label>");
               }
               else if (r == 2) {
            	   out.println("<input type=\"radio\" id=\"entry" + count + "-star5\" name=\"rate\" value=\"5\"  >");
                   out.println("<label for=\"entry" + count + "-star5\" title=\"text\">5 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star4\" name=\"rate\" value=\"4\" >");
                   out.println("<label for=\"entry" + count + "-star4\" title=\"text\">4 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star3\" name=\"rate\" value=\"3\" >");
                   out.println("<label for=\"entry" + count + "-star3\" title=\"text\">3 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star2\" name=\"rate\" value=\"2\" checked>");
                   out.println("<label for=\"entry" + count + "-star2\" title=\"text\">2 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star1\" name=\"rate\" value=\"1\" >");
                   out.println("<label for=\"entry" + count + "-star1\" title=\"text\">1 star</label>");
               }
               else if (r == 3) {
            	   out.println("<input type=\"radio\" id=\"entry" + count + "-star5\" name=\"rate\" value=\"5\"  >");
                   out.println("<label for=\"entry" + count + "-star5\" title=\"text\">5 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star4\" name=\"rate\" value=\"4\" >");
                   out.println("<label for=\"entry" + count + "-star4\" title=\"text\">4 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star3\" name=\"rate\" value=\"3\" checked>");
                   out.println("<label for=\"entry" + count + "-star3\" title=\"text\">3 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star2\" name=\"rate\" value=\"2\" >");
                   out.println("<label for=\"entry" + count + "-star2\" title=\"text\">2 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star1\" name=\"rate\" value=\"1\" >");
                   out.println("<label for=\"entry" + count + "-star1\" title=\"text\">1 star</label>");
               }
               else if (r == 4) {
            	   out.println("<input type=\"radio\" id=\"entry" + count + "-star5\" name=\"rate\" value=\"5\"  >");
                   out.println("<label for=\"entry" + count + "-star5\" title=\"text\">5 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star4\" name=\"rate\" value=\"4\" checked>");
                   out.println("<label for=\"entry" + count + "-star4\" title=\"text\">4 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star3\" name=\"rate\" value=\"3\" >");
                   out.println("<label for=\"entry" + count + "-star3\" title=\"text\">3 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star2\" name=\"rate\" value=\"2\" >");
                   out.println("<label for=\"entry" + count + "-star2\" title=\"text\">2 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star1\" name=\"rate\" value=\"1\" >");
                   out.println("<label for=\"entry" + count + "-star1\" title=\"text\">1 star</label>");
               }
               else if (r == 5) {
            	   out.println("<input type=\"radio\" id=\"entry" + count + "-star5\" name=\"rate\" value=\"5\" checked>");
                   out.println("<label for=\"entry" + count + "-star5\" title=\"text\">5 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star4\" name=\"rate\" value=\"4\" >");
                   out.println("<label for=\"entry" + count + "-star4\" title=\"text\">4 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star3\" name=\"rate\" value=\"3\" >");
                   out.println("<label for=\"entry" + count + "-star3\" title=\"text\">3 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star2\" name=\"rate\" value=\"2\" >");
                   out.println("<label for=\"entry" + count + "-star2\" title=\"text\">2 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star1\" name=\"rate\" value=\"1\" >");
                   out.println("<label for=\"entry" + count + "-star1\" title=\"text\">1 star</label>");
               }
               else {
            	   out.println("<input type=\"radio\" id=\"entry" + count + "-star5\" name=\"rate\" value=\"5\"  >");
                   out.println("<label for=\"entry" + count + "-star5\" title=\"text\">5 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star4\" name=\"rate\" value=\"4\" >");
                   out.println("<label for=\"entry" + count + "-star4\" title=\"text\">4 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star3\" name=\"rate\" value=\"3\" >");
                   out.println("<label for=\"entry" + count + "-star3\" title=\"text\">3 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star2\" name=\"rate\" value=\"2\" >");
                   out.println("<label for=\"entry" + count + "-star2\" title=\"text\">2 stars</label>");
                   out.println("<input type=\"radio\" id=\"entry" + count + "-star1\" name=\"rate\" value=\"1\" >");
                   out.println("<label for=\"entry" + count + "-star1\" title=\"text\">1 star</label>");
               }
               
               count++;
               
               out.println("</td>\n");
               out.println("<td>\n");
               out.println("<input type=\"submit\" value=\"Submit\">");
               out.println("</td>\n");
               out.println("</tr>\n");
               out.println("</form >");
            }
            
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
        // Closing tags
        out.println("</div>\n");
        out.println("</main>\n");
        out.println("</body>\n");
        out.println("</html>\n");

        
        //out.println(HTMLbasic.create_page("Welcome", contents));
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
