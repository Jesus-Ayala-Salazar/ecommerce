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


@WebServlet(name = "productDetailsServlet", value = "/product-details-servlet")
public class ProductDetailsServlet extends HttpServlet {
    Connection conn;
    InitialContext ctx;
    DataSource ds;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
            conn = ds.getConnection();
        }
        catch (SQLException e) {
            throw new UnavailableException("ProductsServlet.init() SQLException: " + e.getMessage());
        }
        catch (Exception e) {
            throw new UnavailableException("ProductsServlet.init() newInstanceException: " + e.getMessage());
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Head
        out.println("<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<meta name=\"description\" content=\"Ecommerce website\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        out.println("<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n");
        out.println("<link rel=\"stylesheet\" href=\"css/style.css\">\n");
        out.println("<title>Early Birds</title>\n");
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
        out.println("<h3><a href=\"products.html\" onclick=\"closeMenu()\">Products</a></h3>\n");
        out.println("<h3><a href=\"team.html\" onclick=\"closeMenu()\">Team</a></h3>\n");
        out.println("<h3><a href=\"login.html\" onclick=\"closeMenu()\">Login / Create Account</a></h3>\n");
        out.println("</div>\n");

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
                out.println("<h1>" + name + "</h1>\n");

                // Row for product description, image, id, price, and add to cart button
                out.println("<div class=\"center\">\n");

                // Image
                out.println("  <div class=\"col-6 col-s-6\">\n");
                out.println("    <img class=\"product-img img-zoom\" src=\"data:image/jpg;base64," + base64Image + "\">\n");
                out.println("  </div>");

                // Description
                out.println("  <div class=\"col-6 col-s-6\">\n");
                out.println("    <h3>Product Description</h3>\n");
                out.println("    <p>" + desc + "</p>\n");
                out.println("  </div>\n");

                // Price
                out.println("  <div class=\"col-6 col-s-12\">\n");
                out.format(   "<p>Price: $%.2f</p>", price);
                out.println("    <p>Product ID: " + productId + "</p>");
                out.println("  </div>\n");

                // Closing tags
                out.println("</div>\n");
                out.println("</main>\n");
                out.println("</body>\n");
                out.println("</html>\n");
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