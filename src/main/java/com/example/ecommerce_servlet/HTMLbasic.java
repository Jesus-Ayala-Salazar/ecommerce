package com.example.ecommerce_servlet;


public class HTMLbasic {

    static String create_header(String pageTitle) {
        return "<head>\r\n"
                + "  <meta charset=\"utf-8\">\r\n"
                + "  <meta name=\"author\" content=\"Mia, James, Jesus\">\r\n"
                + "  <meta name=\"description\" content=\"Ecommerce website\">\r\n"
                + "  <!--Online scripts-->\r\n"
                + "  <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\r\n"
                + "  <!--Local scripts-->\r\n"
                + "  <!--Stylesheets-->\r\n"
                + "  <link rel=\"stylesheet\" href=\"css/style.css\"> <!--local stylesheet-->\r\n"
                + "  <title>" + pageTitle + "</title>\r\n"
                + "</head>\r\n";	}

    static String create_body(String contents) {
        return "<body>\r\n"
                + "  <main>\r\n"
                + "    <script src=\"index.js\">var slideIndex = 1;</script>\r\n"
                + "    <div class=\"navbar\">\r\n"
                + "      <a href=\"home.html\">Early Birds</a>\r\n"
                + "      <button onclick=\"openMenu()\" class=\"navbar-button\"><em class=\"material-icons sz-36 menu-button\" id=\"menu-button\">menu</em></button>\r\n"
                + "    </div>\r\n"
                + "    <div id=\"menu\" class=\"menu\">\r\n"
                + "      <button onclick=\"closeMenu()\" class=\"navbar-button\"><em class=\"material-icons sz-36 open-menu-button\" id=\"open-menu-button\">close</em></button>\r\n"
                + "      <h3><a href=\"home.html\" onclick=\"closeMenu()\">Home</a></h3>\r\n"
                + "      <h3><a href=\"products-servlet\" onclick=\"closeMenu()\">Products</a></h3>\r\n"
                + "      <h3><a href=\"team.html\" onclick=\"closeMenu()\">Team</a></h3>\r\n"
                + "      <h3><a href=\"login.html\" onclick=\"closeMenu()\">Login / Create Account</a></h3>\r\n"
                + "    </div>\r\n"
                + contents + "\r\n"
                + "</main>\r\n"
                + "</body>";
    }

    public static String create_page(String pageTitle, String contents) {
        return "<!doctype html>\r\n<html lang=\"en\">" + create_header(pageTitle) + create_body(contents) + "</html>";
    }
}