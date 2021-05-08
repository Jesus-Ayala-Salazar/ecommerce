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
        StringBuilder contents = new StringBuilder();

        // String builder for html page contents

        // H1

        String shipping = request.getParameter("ship");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("bldg") + " " + request.getParameter("street") + ", " +
                request.getParameter("city") + ", " + request.getParameter("state") + ", " + request.getParameter("zipcode");
        HttpSession session = request.getSession(false);
        Float total = (Float) session.getAttribute("total");

        contents.append("<div class=\"center\">\n");
        contents.append("<h1>Thank you for your order!</h1>\n");
        contents.append("<body>\n");
        contents.append("<p>First Name: " + fname + "</p>\n");
        contents.append("<p>Last Name: " + lname + "</p>\n");
        contents.append("<p>Phone: " + phone + "</p>\n");
        contents.append("<p>Email: " + email + "</p>\n");
        contents.append("<p>Address:  " + address + "</p>\n");
        contents.append(String.format("<p>The total of your order is: $%.2f</p>\n", total));
        contents.append("</div>");
        out.println(HTMLbasic.create_page("Thank You!", contents.toString()));

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