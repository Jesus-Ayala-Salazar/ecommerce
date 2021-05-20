<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "jakarta.servlet.http.*,jakarta.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="author" content="Mia, James, Jesus">
		<meta name="description" content="Ecommerce website">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="css/style.css">
        
		<title>Early Birds</title>
	</head>

	<body>
		<script src="index.js"></script>
		
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
	      <h3><a href="products-servlet" onclick="closeMenu()">Products</a></h3>
	      <h3><a href="team.html" onclick="closeMenu()">Team</a></h3>
	      <h3><a href="login.html" onclick="closeMenu()">Login / Create Account</a></h3>
	    </div>
	    <!-- end collapsible menu -->
	    
	    <h1 class="center">Products</h1>
	    
	    <div class="center col-12 col-s-12">
	    	
		
		<sql:setDataSource var = "snapshot" driver = "com.mysql.cj.jdbc.Driver" url = "jdbc:mysql://localhost:3306/inf124" user = "root"  password = "mv@72137628"/>
		<sql:query dataSource= "${snapshot}" var = "result">SELECT * FROM products;</sql:query>
		
		<c:forEach var = "row" items = "${result.rows}">
			<div class="col-3 col-s-6">
				<a href="product-details-servlet?productId=${row.productId}"><img class="product-img" src="${pageContext.servletContext.contextPath }/photoServlet?id=${row.productId}" /></a>
				<h3>${row.name}</h3>
				<span>Price: ${row.price}</span>
			</div>
		</c:forEach>
		</div>
	</body>
</html>