package com.example.ecommerce_servlet;


import java.io.IOException;
import java.io.PrintWriter;

import com.example.ecommerce_servlet.HTMLbasic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class welcome
 */
@WebServlet("/welcome")
public class welcome extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        session.setMaxInactiveInterval(60*15); // 15 minute session memory

        sessionCart cart = new sessionCart((String)session.getAttribute("user"));
        session.setAttribute("cart", cart);

        String user = (String)session.getAttribute("user");
        String contents = "<h1>Hello, " + user + ".</h1><p class=\"center\"><a href='products-servlet'>Click here</a> to begin shopping.</p>"
                + "<p class=\"center\"><a href=\"logoutServlet\"><button>Log Out</button></p>";

        out.println(HTMLbasic.create_page("Welcome", contents));
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}