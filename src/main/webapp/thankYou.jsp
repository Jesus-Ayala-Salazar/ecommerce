<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="author" content="Mia, James, Jesus">
	<meta name="description" content="Ecommerce website">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/style.css">
	<title>Order Confirmed</title>
</head>
<body>
	<script src="index.js"></script>
	<!-- top bar, holds site title + menu-button -->
    <div class="navbar">
      <a href="home.html">Early Birds</a>
      <button onclick="openMenu()" class="navbar-button"><em class="material-icons sz-36 menu-button" id="menu-button">menu</em></button>
    </div>
    <!-- end top bar -->

    <!-- collapsible menu, hidden until menu-button is clicked on -->
    <div id="menu" class="menu">
      <button onclick="closeMenu()" class="navbar-button"><em class="material-icons sz-36 open-menu-button" id="open-menu-button">close</em></button>

      <!-- side menu navigation sections-->
      <h3><a href="home.html" onclick="closeMenu()">Home</a></h3>
      <h3><a href="productsPage.jsp" onclick="closeMenu()">Products</a></h3>
      <h3><a href="team.html" onclick="closeMenu()">Team</a></h3>
      <h3><a href="login.html" onclick="closeMenu()">Login / Create Account</a></h3>
    </div>
    <!-- end collapsible menu -->
    
    <%
    	String shipping = request.getParameter("ship");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("bldg") + " " + request.getParameter("street") + ", " +
                request.getParameter("city") + ", " + request.getParameter("state") + ", " + request.getParameter("zipcode");
        request.getSession(false);
        Double total = (Double) session.getAttribute("total");
    %>
    
    <div class="center">
    	<h1>Thank you for your order!</h1>
    	<p>First Name: <%= fname %> </p>
    	<p>Last Name: <%= lname %> </p>
    	<p>Phone: <%= phone %> </p>
    	<p>Email: <%= email %> </p>
    	<p>Address: <%= address %> </p>
    	<fmt:formatNumber var="i" type="number" value="${total}" pattern = ".00"/>
    	<p>The total of your order is: \$${i}</p>
    </div>
</body>
</html>