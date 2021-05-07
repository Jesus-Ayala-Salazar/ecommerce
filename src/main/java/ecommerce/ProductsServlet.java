package ecommerce;

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


@WebServlet("/products-servlet")
public class ProductsServlet extends HttpServlet {
    Connection conn;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.conn = dbConnector.connectDB();
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        // H1
        out.println("<h1 class=\"center\">Products</h1>\n");

        // Product grid opening div
        out.println("<div class=\"center col-12 col-s-12\">\n");

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
                out.println("  <div class=\"col-3 col-s-6\">\n");
                out.println("    <a href=\"product-details-servlet?productId=" + productId + "\"> " +
                        "<img class=\"product-img\" src=\"data:image/jpg;base64," + base64Image + "\"> </a>\n");
                out.println("    <h3>" + name + "</h3>\n");
                out.format("<span>Price $%.2f</span>\n", price);
                out.println("  </div>\n");
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
        out.println("</div>\n");
        out.println("</main>\n");
        out.println("</body>\n");
        out.println("</html>\n");
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