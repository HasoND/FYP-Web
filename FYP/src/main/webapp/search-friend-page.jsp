<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<%@page import="com.helper.Keys"%>
<%@page import="com.bean.User"%>
<%@page import="java.util.*"%>

<link rel="stylesheet" href="resources/CSS/search-friend-page.css">

</head>
<body>
<%
	 if (session == null || session.getAttribute(Keys.USER_SESSION) == null) {
    	response.sendRedirect("login.jsp");
	}else{ 
		User user = (User)session.getAttribute(Keys.USER_SESSION);
	 }
%>
	<nav class="navbar navbar-light bg-light">
			<div>
				<a href = "/FYP/home">Home</a>
			</div>
			
			<div class = "search-bar">
				<form action = "searchName" method = "post" class="d-flex">
					<input class="form-control me-2 search-input" type="text" name="search-name-bar" placeholder="Search">
					<button type = "submit" class = "btn btn-outline-success">Search</button>
				</form>
			</div>
			<div class="current-day-time">
				<span id="demo"></span>
			</div>
		</nav>

	<div class="result-container">
		<c:set var="list" value="${sessionScope[Keys.SEARCH_LIST]}" />
		<c:if test = "${sessionScope[Keys.SEARCH_LIST] == null}">
			<div>
				No user found.
			</div>
		</c:if>
		<div class="result-text">		
			${sessionScope[Keys.SEARCH_INPUT] }
		</div>
		<c:forEach var="search" items="${list}">
		<div class="results">
			<form action = "addFriend" method = "get" class="results-form">
				<img src="data:image/${search.avatarType};base64,${search.avatar}" style = "max-width:50px; max-height:50px;">
				<div class="infor-container">
					<div><a href = "profile?username=${search.username}" >${search.username}</a></div>
					<div>${search.email}</div>
					<div>${search.country}</div>
				</div>
				<button type = "submit" value = "${search.username}" name = "friendName" class="btn btn-warning search-add"
			<c:if test="${search.friendStatus == true}"><c:out value="disabled='disabled'"/></c:if>
			>ADD</button>
			</form>
		</div>
		</c:forEach>
	</div>
</body>
</html>