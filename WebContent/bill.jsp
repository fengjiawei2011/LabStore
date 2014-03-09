<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/home.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/polyfill.js"></script>

</head>

<body>

<h1>Your Order:</h1><hr>
<h2>Products List</h2>
<c:forEach var="p" items="${products}">
	<div><span>Product Name: </span><span>${p.getProduct_name()}</span></div>
	<div><span>Product Quantity:</span><span>${p.getProduct_quantity()}</span></div>
</c:forEach><hr>
<h2>Total Price</h2>
<div><span>${total}</span></div><hr>

<h1>credit card payment: </h1>
<form action="http://localhost:8080/LabStore/myrest/sc/pay/${uid}"  method="post">
<div><span>Firstname: </span><span><input type = "text" name="firstname"></span></div>
<div><span>Lastname: </span> <span><input type = "text" name="lastname"></span></div>
<div><span>card number: </span> <span><input type ="text" name="cardnumber" ></span></div>

<input type = "submit" value="Place your order">
</form>
</body>
</html>