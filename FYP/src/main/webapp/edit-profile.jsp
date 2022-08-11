<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Profile</title>

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

<link rel="stylesheet" href="resources/CSS/edit-profile.css" />
<%@page import="com.helper.Keys"%>
<%@page import="com.bean.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
if (session == null || session.getAttribute(Keys.USER_SESSION) == null) {
    	response.sendRedirect("login.jsp");
    	return;
	}
%>
</head>
<body>

	<%
	User user = (User) session.getAttribute(Keys.USER_SESSION);
	%>

	<nav class="navbar navbar-light bg-light">
		<div>
			<a href="/FYP/home">Home</a>
			>
			<a class="user-home-link" href = "profile?username=${sessionScope[Keys.USER]}">Profile</a>
		</div>

		<div class="search-bar">
			<form action="searchName" method="post" class="d-flex">
				<input class="form-control me-2 search-input" type="text"
					name="search-name-bar" placeholder="Search">
				<button type="submit" class="btn btn-outline-success">Search</button>
			</form>
		</div>
		<div class="current-day-time">
			<span id="time"></span>

			<form action="logout" method="post">
				<button type="submit" class="btn btn-danger">Logout</button>
			</form>
		</div>
	</nav>


	<div class="profile-container">
		<div class="profile">
			<div class="avatar">				
				<label class="avatar-tittle">Avatar</label>
				<img class="edit-profile-avatar"
					src="data:image/<%=user.getAvatarType()%>;base64,<%=user.getAvatar()%>"
					style="max-width: 300px; max-height: 300px;">
				<form action="updateAvatar" method="post" class="edit-avatar-form"
					enctype="multipart/form-data">
					<input type="file" name="editAvatar" accept="image/*">
					<div class="avatar-submit">
						<button type="submit" class="btn btn-primary">Upload New Avatar</button>
					</div>
				</form>
			</div>
			<div class="personal-information-container">
				<label class="personal-information-tittle">Account Information</label>
				<div class="personal-information">
					<form action="updateProfile" method="post"
						class="edit-profile-form">
						<div class="form-group">
						<label>Username: <input type="text" name="new-username" class="form-control"
							value="<%=user.getUsername()%>" readonly></label> 
						</div>
						<div class="form-group">
						<label>DOB:	<input type="text" name="new-dob" class="form-control" value="<%=user.getDob()%>"
							required>
						</label>
						</div>
						<div class="form-group">
							<c:set var="data" value="<%=user.getGender()%>"></c:set>
							<label>Gender:</label>
							<div class="register-gender form-check" id="gender">
								<input type="radio" class="form-check-input" id="user-male"
									name="new-gender" value="Male"
									<c:if test="${data == 'Male'}">checked="checked"</c:if> /> <label
									class="form-check-label" for="flexRadioDefault1"> Male
								</label>
							</div>
							<div class="register-gender form-check" id="gender">
								<input type="radio" class="form-check-input" id="user-female"
									name="new-gender" value="Female"
									<c:if test="${data == 'Female'}">checked="checked"</c:if> /> <label
									class="form-check-label" for="flexRadioDefault1">
									Female </label>

							</div>
						</div>
						<div class="form-group">
							<c:set var="key" value="<%=user.getCountry()%>"></c:set>
							<label>Country</label>
							<div class="register-country" id="country">
								<select class="form-select" name="new-country">
									<option value="None"  <c:if test="${key == 'None'}"> <c:out value= "selected=selected"/></c:if> >Select Country</option>
									<option value="Malaysia" <c:if test="${key == 'Malaysia'}"> <c:out value= "selected=selected"/></c:if> >Malaysia</option>
									<option value="Singapore" <c:if test="${key == 'Singapore'}"> <c:out value= "selected=selected"/></c:if>>Singapore</option>
									<option value="China" <c:if test="${key == 'China'}"> <c:out value= "selected=selected"/></c:if>>China</option>
									<option value="India" <c:if test="${key == 'India'}"> <c:out value= "selected=selected"/></c:if>>India</option>
									<option value="England"  <c:if test="${key == 'England'}"> <c:out value= "selected=selected"/></c:if>>England</option>
								</select>
							</div>
						</div>
						<div class="form-group">
						<label>Email: <input type="text" name="new-email" class="form-control"
							value="<%=user.getEmail()%>" required></label>
						</div>
						<div class="submit-container">
							<button type="submit" class="btn btn-primary">Update</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>