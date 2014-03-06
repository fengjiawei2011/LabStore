<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/home.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/polyfill.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<h1><a>my shopping cart: ${sc_num} </a></h1>
<h1> ALL Products</h1>
<c:forEach var="product" items="${products}"> 
<div>
	<span>title: </span><span id="${product.getProduct_id()}_name">${product.getProduct_name()}</span><br>
	<span>price: </span><span id="${product.getProduct_id()}_price">${product.getProduct_price()}</span><br>
	<span>Quantity: </span><span id="${product.getProduct_id()}_quantity">${product.getProduct_quantity()}</span><br>
	<span>description: </span><span id="${product.getProduct_id()}_des">${product.getProduct_description()}</span><br>
	<span>owner: </span><span id="${product.getProduct_id()}_oid">${product.getOwner_id()}</span><br>
	<span>Catalog: </span><span id="${product.getProduct_id()}_catalg">${product.getCatalog_name()}</span><br>
 	<button onclick="add(${product.getProduct_id()})"> add into shoppingcart</button>
</div>
</c:forEach>

<h1><span id="uid">${legalUser.getUser_id()}</span></h1>
</body>
</html>