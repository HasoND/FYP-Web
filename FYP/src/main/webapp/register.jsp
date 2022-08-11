<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>

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

<link rel="stylesheet" href="resources/CSS/register.css">

</head>
<body>
	<%@page import="com.helper.Keys"%>
	<%@page import="java.util.Objects"%>

	<div class="register-form-container">
		<div class="top-title">
			<a href="login.jsp" class="register-back">< Back</a> <label
				class="register-title"> Register </label>
		</div>

		<form action="RegisterAction" method="post" class="register-form">
			<div class="register-uname-form form-group">
				<div class="register-uname" id="uname">
					<label>Username</label> <input type="text" id="username"
						class="form-control" name="user-username"
						placeholder="Enter a username" required>
					${requestScope[Keys.REGISTER_USERNAME_ERROR]}
				</div>
			</div>
			<div class="register-password-form form-group">
				<label>Password</label>
				<div class="register-password" id="upassword">
					<input type="password" name="user-password" class="form-control"
						placeholder="Enter a password" required>
					${requestScope[Keys.REGISTER_PASSWORD_ERROR]}
				</div>
			</div>
			<div class="register-check-password-form form-group">
				<label>Confirm Password</label>
				<div class="register-check-password" id="check-upassword">
					<input type="password" name="user-check-password"
						class="form-control" placeholder="Enter password again" required>
					${requestScope[Keys.REGISTER_COMFIRMPASSWORD_ERROR]}
				</div>
			</div>
			<div class="register-email-form form-group">
				<label>Email</label>
				<div class="register-email" id="email">
					<input type="text" id="user-email" name="user-email"
						class="form-control" placeholder="Enter email address" required>
					${requestScope[Keys.REGISTER_EMAIL_ERROR]}
				</div>
			</div>
			<div class="form-group">
				<label>Gender</label>
				<div class="register-gender form-check" id="gender">
					<input type="radio" class="form-check-input" id="user-male"
						name="userGender" value="Male"> 
					<label class="form-check-label" for="flexRadioDefault1"> Male </label> 
				</div>
				<div class="register-gender form-check" id="gender">
					<input type="radio" class="form-check-input"
						id="user-female" name="userGender" value="Female">
					<label class="form-check-label" for="flexRadioDefault1"> Female </label> 
						
				</div>
			</div>
			<div class="form-group">
				<label>Country</label>
				<div class="register-country" id="country">
					<select class="form-select" name="country">
						<option value="None">Select Country</option>
						<option value="Malaysia">Malaysia</option>
						<option value="Singapore">Singapore</option>
						<option value="China">China</option>
						<option value="India">India</option>
						<option value="England">England</option>
					</select>
				</div>
			</div>

			<div>
				<button type="submit" class="btn btn-primary" id="reg-button">Create an account</button>
			</div>
		</form>
		<div class="login-form">
			<label>Already have an account? <a href="login.jsp">Login</a></label>
		</div>
	</div>

	<script type="text/javascript">
var inputF = document.getElementById("username");
var uname= "<%=request.getAttribute("tempUsername")%>";
if(uname!="null")
inputF.setAttribute('value', uname);

var inputE = document.getElementById("user-email");
var uemail= "<%=request.getAttribute("tempEmail")%>
		";
		if (uemail != "null")
			inputE.setAttribute('value', uemail);

		var loadFile = function(event) {
			var output = document.getElementById('output');
			output.src = URL.createObjectURL(event.target.files[0]);
			output.onload = function() {
				URL.revokeObjectURL(output.src)

			}
		};
	</script>

</body>
</html>