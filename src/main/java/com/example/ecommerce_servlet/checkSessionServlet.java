package com.example.ecommerce_servlet;


import java.io.IOException;
import java.io.PrintWriter;

import com.example.ecommerce_servlet.HTMLbasic;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.UnavailableException;
/**
 * Servlet implementation class checkSessionServlet
 */
@WebServlet("/checkSessionServlet")
public class checkSessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if ( request.getSession().getAttribute("loggedIn") == null ) {
            response.sendRedirect("login.html");
        }
        else {
            request.getRequestDispatcher("welcome").forward(request,response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}