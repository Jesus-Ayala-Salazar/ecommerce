package com.example.ecommerce_servlet;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* These imports are for Glassfish versions that don't use jakarta
 * and for other application servers (Tomcat)
 */
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.UnavailableException;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/OrderDetailsServlet")
public class OrderDetailsServlet extends HttpServlet {
    Connection conn;
    String itemId;
    String quantity;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        conn = dbConnector.connectDB();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out         = response.getWriter();
        StringBuilder contents  = new StringBuilder();
        boolean isValidated     = true;

        // Get form information
        String shipping     = request.getParameter("ship");
        String name         = request.getParameter("fname") + " " + request.getParameter("lname");
        String phone        = request.getParameter("phone");
        String card         = request.getParameter("card");
        String cvc          = request.getParameter("cvc");
        String email        = request.getParameter("email");
        String street       = request.getParameter("street");
        String city         = request.getParameter("city");
        String bldg         = request.getParameter("bldg");
        String state        = request.getParameter("state");
        String zipcode      = request.getParameter("zipcode");
        String addr         = street + " " + bldg + " " + city + " " + state + " " + zipcode;

        // Validate
        contents.append("<div class=\"center\">");
        if (email.isEmpty() || phone.isEmpty() || name.isEmpty() || card.isEmpty() || cvc.isEmpty() || street.isEmpty()
                || city.isEmpty() || state.isEmpty() || zipcode.isEmpty()) {
            contents.append("<script type=\"text/javascript\">");
            contents.append("alert('Please fill out all the required fields!');");
            contents.append("</script>");
            isValidated = false;
        } else if (!phone.matches("^[0-9]+$") || !card.matches("^[0-9]+$")
                || !cvc.matches("^[0-9]+$") || !zipcode.matches("^[0-9]+$")) {
            contents.append("<script type=\"text/javascript\">");
            contents.append("alert('Please enter only numeric values for phone number, credit card number, security code, and zipcode.');");
            contents.append("</script>");
            isValidated = false;
        } else if (phone.length() < 7) {
            contents.append("<script type=\"text/javascript\">");
            contents.append("alert('Phone number must contain at least 7 digits.');");
            contents.append("</script>");
            isValidated = false;
        } else if (card.length() < 16) {
            contents.append("<script type=\"text/javascript\">");
            contents.append("alert('Card number must contain at least 16 digits.');");
            contents.append("</script>");
            isValidated = false;
        } else if (cvc.length() != 3) {
            contents.append("<script type=\"text/javascript\">");
            contents.append("alert('CVC number must contain 3 digits.');");
            contents.append("</script>");
            isValidated = false;
        }else if (zipcode.length() < 5) {
            contents.append("<script type=\"text/javascript\">");
            contents.append("alert('Zipcode must contain 5 digits.');");
            contents.append("</script>");
            isValidated = false;
        }
        contents.append("</div>");

        if (isValidated) {
            this.conn = dbConnector.connectDB();

//            PreparedStatement uid;
            Statement uid;
            try {
                HttpSession session = request.getSession(false);
                String user = (String)session.getAttribute("user");

                // get uid given username (from sesison)
//                uid = conn.prepareStatement("SELECT * FROM users WHERE username=\"" + user + "\"");
                uid = conn.createStatement();
                String sql;
                sql = "SELECT * FROM users WHERE username=" + user;
                ResultSet rs = uid.executeQuery(sql);
                contents.append("<h1>" + user + "</h1>");

//                ResultSet result = uid.executeQuery();

                int userId = 0;
//                while(result.next()){
//                    userId = result.getInt("userId");
//                    contents.append("<h1>" + uid + "</h1>");
//                }

                while(rs.next()){
                    userId = rs.getInt("userId");
                    contents.append("<h1>" + userId + "</h1>");
                }


                    // get session cart
                sessionCart cart = (sessionCart) session.getAttribute("cart");

                for ( int i = 0; i < cart.cart.size(); i++ ) {
                    // add each item in cart to the db
                    PreparedStatement add;
                    add = conn.prepareStatement("INSERT INTO orders (userId, itemId, quantity) VALUES (?, ?, ?);");
                    add.setInt(1,userId);
                    add.setInt(2,cart.cart.get(i)[0]);
                    add.setInt(3,cart.cart.get(i)[1]);

                    add.executeUpdate();
                }

            }  catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.println(HTMLbasic.create_page("Shopping Cart", contents.toString()));
            RequestDispatcher rd = request.getRequestDispatcher("CartServlet");
            rd.include(request, response);
//            RequestDispatcher rd = request.getRequestDispatcher("ThankYou");
//            rd.forward(request, response);

        } else {
            out.println(HTMLbasic.create_page("Shopping Cart", contents.toString()));
            RequestDispatcher rd = request.getRequestDispatcher("CartServlet");
            rd.include(request, response);
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
}