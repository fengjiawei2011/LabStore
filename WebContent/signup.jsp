<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<div><span id="error_msg"></span></div>
<form id="signup_submit" action="signup" method="post">
	lastname: <input type="text" name="lastname" id="lastname"><span id="lastname_validate"></span><br>
	firstname:<input type="text" name="firstname" id="firstname"><span  id="firstname_validate"></span><br>
	email:<input type="text" name="email" id="email"><span  id="email_validate"></span><br>
	password:<input type="password" name="password" id="password"><span  id="password_validate"></span><br>
	password_1:<input type="password" name="password_1" id="password_1"><span  id="password_1_validate"></span><br>
	<input type="submit">
</form>
</body>
</html>