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

@WebServlet("/ThankYou")
public class ThankYou extends HttpServlet{


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // String builder for html page contents
        StringBuilder contents = new StringBuilder();

        String shipping = request.getParameter("ship");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email"); 
        String address = request.getParameter("bldg") + " " + request.getParameter("street") + ", " + 
                request.getParameter("city") + ", " + request.getParameter("state") + ", " + request.getParameter("zipcode");
        String total = request.getParameter("total"); 
        
        contents.append("<h1>Thank you for your order!</h1>\n");
        contents.append("<div><b>First Name:</b> " + fname + "</div>\n");
        contents.append("<div><b>Last Name:</b> " + lname + "</div>\n");
        contents.append("<div><b>Phone:</b> " + phone + "</div>\n");
        contents.append("<div><b>Email:</b> " + email + "</div>\n");
        contents.append("<div><b>Address:</b>  " + address + "</div>\n");
        contents.append("<div><b>Order Total:</b>  $" + total + "</div>\n");
        
        
        out.println(HTMLbasic.create_page("Order Confirmed", contents.toString()));
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
