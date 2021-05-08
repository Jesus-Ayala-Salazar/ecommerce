package com.example.ecommerce_servlet;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet(name = "productsServlet", value = "/products-servlet")
public class ProductsServlet extends HttpServlet {
    Connection conn;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        conn = dbConnector.connectDB();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // String builder for html page contents
        StringBuilder contents = new StringBuilder();
        contents.append("<h1 class=\"center\">Products</h1>\n");        // h1
        contents.append("<div class=\"center col-12 col-s-12\">\n");    // Product grid opening div

        // Individual product divs
        Statement stmt = null;
        try {
            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM products";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set

            while(rs.next()){
                //Retrieve by column name
                int productId   = rs.getInt("productId");
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

                // Create individual product divs
                contents.append("  <div class=\"col-3 col-s-6\">\n");
                contents.append("    <a href=\"product-details-servlet?productId=" + productId + "\"> " +
                        "<img class=\"product-img\" src=\"data:image/jpg;base64," + base64Image + "\"> </a>\n");
                contents.append("    <h3>" + name + "</h3>\n");
                contents.append(String.format("<span>Price $%.2f</span>\n", price));
                contents.append("  </div>\n");
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

        // Closing tags
        contents.append("</div>\n");
        contents.append("</main>\n");
        contents.append("</body>\n");
        contents.append("</html>\n");
        out.println(HTMLbasic.create_page("Products", contents.toString()));
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