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
/**
 * Servlet implementation class addToCartServlet
 */
@WebServlet("/addToCartServlet")
public class addToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession(false);

        if ( request.getSession().getAttribute("loggedIn") == null ) {
            response.sendRedirect("login.html");
        }
        else {
            sessionCart cart = (sessionCart) session.getAttribute("cart");

            cart.addToCart(pid, quantity);

            session.setAttribute("cart", cart);

            PrintWriter out = response.getWriter();

            String contents = "<h1>Success!</h1><p class=\"center\">Your worm has been added to your shopping cart.</p>"
                    + "<p class=\"center\"><a href=\"products-servlet\">Continue Shopping</a> or <a href=\"CartServlet\">Go To Cart</a></p>";

            out.println(HTMLbasic.create_page("Welcome", contents));
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