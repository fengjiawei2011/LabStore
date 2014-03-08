<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<h1>User : ${lastname} ${firstname}'s shopping cart</h1>
<c:forEach var="product" items="${products}"> 
<div>
	<span>title: </span><span id="${product.getProduct_id()}_name">${product.getProduct_name()}</span><br>
	<span>price: </span><span id="${product.getProduct_id()}_price">${product.getProduct_price()}</span><br>
	<span>Quantity: </span><span id="${product.getProduct_id()}_quantity">${product.getProduct_quantity()}</span><br>
	<span>description: </span><span id="${product.getProduct_id()}_des">${product.getProduct_description()}</span><br>
	<span>owner: </span><span id="${product.getProduct_id()}_oid">${product.getOwner_id()}</span><br>
	<span>Catalog: </span><span id="${product.getProduct_id()}_catalg">${product.getCatalog_name()}</span><br>
 	<button onclick=""> remove </button>
</div>
</c:forEach>
</body>
</html>