<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-min.js"></script>
<script type="text/javascript">
var rem = function(pid,uid){
	console.log("remove");
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/LabStore/myrest/sc/remove/"+pid+"?buyerid="+uid,
		success : function(data) {
			console.log("success");
			console.log(data);
		},
		error : function() {
			console.log("error");
		}
	});
	
	return false;
};

var checkout = function(uid){
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/LabStore/myrest/sc/checkout/"+uid,
		success : function(data) {
			console.log("success");
			console.log(data);
		},
		error : function() {
			console.log("error");
		}
	});
}
</script>


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
 	<button onclick="rem(${product.getProduct_id()}, ${userid})"> remove </button>
</div>
</c:forEach>
<div><a href="http://localhost:8080/LabStore/myrest/sc/checkout/${userid}">Check Out</a></div>
<div><a href="http://localhost:8080/LabStore/myrest/home/signin">home</a></div>
</body>
</html>