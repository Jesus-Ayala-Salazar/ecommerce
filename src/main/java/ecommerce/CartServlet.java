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

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet{
    Connection conn;
    String itemId;
    String quantity;
    Blob image;
    Float total;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        conn = dbConnector.connectDB();
        total = 0.0f;
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        sessionCart cart = (sessionCart) session.getAttribute("cart");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // String builder for html page contents
        StringBuilder contents = new StringBuilder();

        // H1
        contents.append("<h1>Shopping Cart</h1>\n");
        contents.append("<div class=\"center col-12 col-s-12\">\n" +
                "        <h2>Items</h2>\n" +
                "    </div>\n");
        contents.append("<table style=\"width:90%\" class=\"center\">\n" +
                "        <colgroup>\n" +
                "            <col style=\"width: 25%\">\n" +
                "            <col style=\"width: 41.66%\">\n" +
                "            <col style=\"width: 16.66%\">\n" +
                "            <col style=\"width: 16.66%\">\n" +
                "        </colgroup>\n");
        contents.append("<tbody>\n");

        // Iterate through cart, extract info from database, and write to printer
        for (int i=0; i < cart.cart.size(); ++i) {
            itemId      = Integer.toString(cart.cart.get(i)[0]);
            quantity    = Integer.toString(cart.cart.get(i)[1]);
            // Query database for product details using productId parameter from Product-Servlet
            Statement stmt = null;
            try {
                // Execute SQL query
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT productImage, price, name FROM products WHERE productId =" + itemId;
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    // Extract data from result set
                    String name = rs.getString("name");
                    Float price = rs.getFloat("price") * Float.parseFloat(quantity);
                    total += price;

                    // Retrieve MEDIUMBLOB as image
                    // Source: https://www.codejava.net/coding/how-to-display-images-from-database-in-jsp-page-with-java-servlet
                    Blob blob = rs.getBlob("productImage");
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

                    // Write individual table rows for order/cart
                    contents.append("<tr>\n");
                    contents.append("<td> <img class=\"product-img\" src=\"data:image/jpg;base64," + base64Image + "\"> </td>\n");
                    contents.append("<td>" + name + "</td>\n");
                    contents.append("<td>" + quantity + "</td>\n");
                    contents.append(String.format("<td>$%.2f</td>\n", price));
                    contents.append("</tr>\n");
                }
                // Clean-up environment
                rs.close();
                stmt.close();
            }catch(SQLException se){
                //Handle errors for JDBC
                out.println(se);
            }catch(Exception e){
                //Handle any other type of error
                out.println(e);
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException ignore) {}// nothing we can do
            }
        }
        
        session.setAttribute("total",total);

        // Close table
        contents.append(String.format("<tr><td></td><td></td><td></td><td>Total: $%.2f</td></tr>\n", total));
        contents.append("</tbody>\n");
        contents.append("</table>\n");

        // Order form
        contents.append("<div class=\"center col-12 col-s-12\">\n" +
                "        <h2>Order Form</h2>\n" +
                "    </div>\n" +
                "\n" +
                "    <form action=\"OrderDetailsServlet\" id=\"orderform\" method=\"get\" class=\"center\">\n" +
                "\n" +
                "        <!-- Row -->\n" +
                "        <div class=\"col-12 col-s-12 order-table\">\n" +
                "            <div class=\"col-12 col-s-12\">\n" +
                "                <h3>Order Information</h3>\n" +
                "            </div>\n" +
                "            <div class=\"col-12 col-s-12\">\n" +
                "                <label for=\"ship\">Shipping Method:</label><br>\n" +
                "                <select name=\"ship\" id=\"ship\" value=\"--Select Shipping Method--\">\n" +
                "                    <option value=\"Overnight\">Overnight Shipping</option>\n" +
                "                    <option value=\"2-Day Expedited\">2-Day Expedited Shipping</option>\n" +
                "                    <option value=\"6-Day Ground\">6-day Ground Shipping</option>\n" +
                "                </select>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Row -->\n" +
                "        <div class=\"col-12 col-s-12 order-table\">\n" +
                "            <div class=\"col-12 col-s-12\">\n" +
                "                <h3>Billing</h3>\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"fname\">First name:</label><br>\n" +
                "                <input type=\"text\" id=\"fname\" name=\"fname\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"lname\">Last name:</label><br>\n" +
                "                <input type=\"text\" id=\"lname\" name=\"lname\"><br>\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"phone\">Phone Number:</label><br>\n" +
                "                <input type=\"text\" id=\"phone\" name=\"phone\" placeholder=\"(XXX) XXX-XXXX\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"card\">Credit Card Number:</label><br>\n" +
                "                <input type=\"text\" id=\"card\" name=\"card\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"cvc\">Security Code:</label><br>\n" +
                "                <input type=\"text\" id=\"cvc\" name=\"cvc\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"email\">Email Address:</label><br>\n" +
                "                <input type=\"text\" id=\"email\" name=\"email\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Row -->\n" +
                "        <div class=\"col-12 col-s-12 order-table\">\n" +
                "            <div class=\"col-12 col-s-12\">\n" +
                "                <h3>Shipping</h3>\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"street\">Street:</label><br>\n" +
                "                <input type=\"text\" id=\"street\" name=\"street\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"city\">City:</label><br>\n" +
                "                <input type=\"text\" id=\"city\" name=\"city\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"bldg\">Building:</label><br>\n" +
                "                <input type=\"text\" id=\"bldg\" name=\"bldg\" placeholder=\"optional\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"state\">State:</label><br>\n" +
                "                <input type=\"text\" id=\"state\" name=\"state\">\n" +
                "            </div>\n" +
                "            <div class=\"col-4 col-s-6\">\n" +
                "                <label for=\"zipcode\">Zipcode:</label><br>\n" +
                "                <input type=\"text\" id=\"zipcode\" name=\"zipcode\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"submit col-12 col-s-12\">\n" +
                "            <input type=\"submit\" value=\"Place Order\">\n" +
                "        </div>\n" +
                "    </form>\n");

        // Closing tags
        contents.append("</div>\n");
        contents.append("</main>\n");
        contents.append("</body>\n");
        contents.append("</html>\n");
        out.println(HTMLbasic.create_page("Shopping Cart", contents.toString()));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Servlet Info";
    }
}
