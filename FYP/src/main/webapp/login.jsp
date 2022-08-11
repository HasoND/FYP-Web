<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="resources/CSS/login.css">

<%@page import="com.helper.Keys"%>
<%@page import="java.util.Objects"%>



<meta charset="ISO-8859-1">
<title>Login</title>

</head>
<body>
	<div class ="main-container">
	<div class="row login-title">
		<label>Welcome</label>
	</div>
	
	<div class="row login-form-container">
	<form action="/FYP/login" method="post" class = "login-form">
		<c:if test="${sessionScope[Keys.LOGIN_ERROR] != null }" >
		<div class="alert alert-warning login-msg" role="alert">${sessionScope[Keys.LOGIN_ERROR]}
		</div>
		</c:if>
		
		<div class="form-group">
			<label>Username</label> 
			<input type="text" class="form-control" name="username-login" placeholder="Enter your username"/>
		</div>
		<div class="form-group">
			<label>Password</label> 
			<input type="password" class="form-control" name="userpass-login" placeholder = "Enter your password"/>
		</div>
		<div class="button-container">
		<input type="submit" class="btn btn-primary login-btn" value="Login" /> 
		Don't have an account? <a href="register.jsp">Sign Up</a>
		</div>
	</form>
	</div>
	</div>
</body>
</html>