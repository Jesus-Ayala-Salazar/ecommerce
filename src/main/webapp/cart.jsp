<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "ecommerce.sessionCart"%>
<%@ page import = "jakarta.servlet.http.*,jakarta.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="author" content="Mia, James, Jesus">
	<meta name="description" content="Ecommerce website">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="css/style.css">
    <script type = "text/JavaScript" src="ajax.js"> </script>
    
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
	
	<h1>Shopping Cart</h1>
	
	<div class="center col-12 col-s-12">
	<c:set var="total" scope="session" value="0.0f"/>
	<h2>Items</h2>
	<table style="width:90%" class="center">
		<colgroup>
			<col style="width: 25%">
            <col style="width: 41.66%">
            <col style="width: 16.66%">
            <col style="width: 16.66%">
        </colgroup>
        <tbody>
	        <%
	        sessionCart cart = (sessionCart)session.getAttribute("cart");
	        %>
	        <c:forEach var = "row" items = "${cart.cart}">
	        
	        	<c:set var="itemId" scope="page" value="${row[0]}"/>
	        	<c:set var="quantity" scope="page" value="${row[1]}"/>
	        	
	        	<sql:setDataSource var = "snapshot" driver = "com.mysql.cj.jdbc.Driver" url = "jdbc:mysql://localhost:3306/inf124" user = "root"  password = "mv@72137628"/>
				<sql:query dataSource= "${snapshot}" var = "result">SELECT productId, price, name FROM products WHERE productId =${itemId}</sql:query>
	        	
	        	<c:forEach var="item" items="${result.rows}">
	        		<c:set var="total" scope="session" value="${total + item.price}" />
	        		
	        		<tr>
	        			<td><img class="product-img" src="${pageContext.servletContext.contextPath }/photoServlet?id=${item.productId}" /></td>
	        			<td>${item.name}</td>
	        			<td>${quantity}</td>
	        			<fmt:parseNumber var="price" type="number" value="${item.price}" />
	        			<td>\$${price}</td>
	        		</tr>
	        	</c:forEach>
	        </c:forEach>
	        
	        <tr>
	        	<fmt:formatNumber var="i" type="number" value="${total}" pattern = ".00"/>
		        <td></td>
		        <td>Subtotal: \$${i}</td>
		        <td>Tax: $<span id="tax">0.00</span></td>
		        <td>Total: $<span id="orderTotal">${i}</span></td>
			</tr>
		</tbody>   
	</table>
	</div>
	
	<div class="center col-12 col-s-12">
        <h2>Order Form</h2>
    </div>

    <form action="OrderDetailsServlet" id="orderform" method="get" class="center">

        <!-- Row -->
        <div class="col-12 col-s-12 order-table">
            <div class="col-12 col-s-12">
                <h3>Order Information</h3>
            </div>
            <div class="col-12 col-s-12">
                <label for="ship">Shipping Method:</label><br>
                <select name="ship" id="ship" value="--Select Shipping Method--">
                <option value="Overnight">Overnight Shipping</option>
                <option value="2-Day Expedited">2-Day Expedited Shipping</option>
                <option value="6-Day Ground">6-day Ground Shipping</option>
                </select>
            </div>
        </div>

        <!-- Row -->
        <div class="col-12 col-s-12 order-table">
            <div class="col-12 col-s-12">
                <h3>Billing</h3>
            </div>
            <div class="col-4 col-s-6">
                <label for="fname">First name:</label><br>
                <input type="text" id="fname" name="fname">
            </div>
            <div class="col-4 col-s-6">
                <label for="lname">Last name:</label><br>
                <input type="text" id="lname" name="lname"><br>
            </div>
            <div class="col-4 col-s-6">
                <label for="phone">Phone Number:</label><br>
                <input type="text" id="phone" name="phone" placeholder="(XXX) XXX-XXXX">
            </div>
            <div class="col-4 col-s-6">
                <label for="card">Credit Card Number:</label><br>
                <input type="text" id="card" name="card">
            </div>
            <div class="col-4 col-s-6">
                <label for="cvc">Security Code:</label><br>
                <input type="text" id="cvc" name="cvc">
            </div>
            <div class="col-4 col-s-6">
                <label for="email">Email Address:</label><br>
                <input type="text" id="email" name="email">
            </div>
        </div>

        <!-- Row -->
        <div class="col-12 col-s-12 order-table">
            <div class="col-12 col-s-12">
                <h3>Shipping</h3>
            </div>
            <div class="col-4 col-s-6">
                <label for="street">Street:</label><br>
                <input type="text" id="street" name="street">
            </div>
            <div class="col-4 col-s-6">
                <label for="city">City:</label><br>
                <input type="text" id="city" name="city">
            </div>
            <div class="col-4 col-s-6">
                <label for="bldg">Building:</label><br>
                <input type="text" id="bldg" name="bldg" placeholder="optional">
            </div>
            <div class="col-4 col-s-6">
                <label for="state">State:</label><br>
                <input type="text" id="state" name="state">
            </div>
            <div class="col-4 col-s-6">
                <label for="zipcode">Zipcode:</label><br>
                <input type="text" id="zipcode" name="zipcode" onblur="processZip(this.value,${total})">
            </div>
        </div>

        <div class="submit col-12 col-s-12">
            <input type="submit" value="Place Order">
        </div>
    </form>
	
</body>
</html>