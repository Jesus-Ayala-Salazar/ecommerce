package com.example.ecommerce_servlet;

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


@WebServlet(name = "productDetailsServlet", value = "/product-details-servlet")
public class ProductDetailsServlet extends HttpServlet {
    Connection conn;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        conn = dbConnector.connectDB();
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // String builder for html page contents
        StringBuilder contents = new StringBuilder();

        // Query database for product details using productId parameter from Product-Servlet
        Statement stmt = null;
        try {
            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM products WHERE productId=" + productId;
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String desc     = rs.getString("description");
                Float price     = rs.getFloat("price");
                String name     = rs.getString("name");

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

                // Write product details to page
                // H1
                contents.append("<h1>" + name + "</h1>\n");

                // Row for product description, image, id, price, and add to cart button
                contents.append("<div class=\"center\">\n");

                // Image
                contents.append("  <div class=\"col-6 col-s-6\">\n");
                contents.append("    <img class=\"product-img img-zoom\" src=\"data:image/jpg;base64," + base64Image + "\">\n");
                contents.append("  </div>");

                // Description
                contents.append("  <div class=\"col-6 col-s-6\">\n");
                contents.append("    <h3>Product Description</h3>\n");
                contents.append("    <p>" + desc + "</p>\n");
                contents.append("  </div>\n");

                // Price
                contents.append("  <div class=\"col-6 col-s-12\">\n");
                contents.append(String.format(   "<p>Price: $%.2f</p>", price));
                contents.append("    <p>Product ID: " + productId + "</p>");
                contents.append("  </div>\n");

                // Add to cart button
                contents.append("<form id=\"addToCart\" method=\"POST\" action=\"addToCartServlet\">\r\n"
                        + "        <div class=\"col-6 col-s-6 center\">\r\n"
                        + "            <label for=\"quantity\">Quantity: </label>\r\n"
                        + "            <input type=\"number\" name=\"quantity\" value=\"1\" min=\"1\">\r\n"
                        + "            <input type=\"number\" name=\"pid\" value=\"" + productId + "\" style=\"display:none\">\r\n"
                        + "        </div>\r\n"
                        + "        <div class=\"submit col-6 col-s-6 center\">\r\n"
                        + "            <input type=\"submit\" value=\"Add to Cart\">\r\n"
                        + "        </div>\r\n"
                        + "    </form>");

                // Closing tags
                contents.append("</div>\n");
                contents.append("</main>\n");
                contents.append("</body>\n");
                contents.append("</html>\n");
                out.println(HTMLbasic.create_page(name, contents.toString()));
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