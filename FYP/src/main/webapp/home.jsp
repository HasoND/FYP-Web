<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- bootstrap -->
<!-- CSS only -->
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
	
<link rel="stylesheet" href="resources/CSS/home.css" />
<meta charset="ISO-8859-1">
<title>Home</title>

</head>

<body>
<%@page import="com.helper.Keys"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.dao.UserDao"%>
<%@page import="com.dao.PostDao"%>
<%@page import="com.bean.Group"%>
<%@page import="com.bean.User"%>
<%@page import="com.bean.Comment"%>


<% 
User user = null;

if (session == null || session.getAttribute(Keys.USER_SESSION) == null) {
	response.sendRedirect("login.jsp");
	return;
}else{
	user = (User)session.getAttribute(Keys.USER_SESSION);
} 
%>
	<div class="home-container">

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
				<span id="time"></span>
				
				<form action = "logout" method="post">
					<button type="submit" class = "btn btn-danger">Logout</button>
				</form>
			</div>
		</nav>

		<div class="content-container row">

			<div class="left-container col-lg-3">
				<a class="user-home-link" href = "profile?username=${sessionScope[Keys.USER]}">
					<img class="user-home-avatar" src="data:image/<%=user.getAvatarType()%>;base64,<%=user.getAvatar()%>" style = "max-width:100px">
					${sessionScope[Keys.USER]}
				</a>
			</div>
			<div class="mid-container col-lg-6">
				<div id = "gContainer" class="group-container" >
					<c:set var="list" value="${sessionScope[Keys.GROUP_LIST]}" />
					<c:forEach var="group" items="${list}">
						<div id = "gItems" class = "group-items btn-group" role="group">
							<a href="/FYP/FetchGroupPost?groupType=${group.groupName}"
								class="btn btn-secondary" style="border-radius: 0;">${group.groupName}</a>
							<div class="dropdown">
								<c:if test="${group.groupName != 'All'}">
								<button type="button" class="btn btn-secondary dropdown-toggle group-dropdown"
									data-toggle="dropdown" style="border-radius: 0;"></button>
								<div id = "dd-menu" class="dropdown-menu" style="padding:0px;">
									<button class="dropdown-item" onclick="openForm(this.id)"  id = "${group.groupName}">Edit</button> 
									<a href="/FYP/DeleteGroup?groupName=${group.groupName}&&groupId=${group.id}" class="dropdown-item btn" style="border-radius: 0;">Delete</a>
								</div>
								</c:if>
							</div>
						</div>
						
					</c:forEach>
					<form action="AddNewGroup" method="post" class = "submit-group-form">
						<div class="container btn-group">
							<input class="form-control" type="text" name="newGroup" placeholder = "Enter group name" required>
							<button type="submit" value="Submit" class = "submit-group">+</button>
						</div>
					</form>
				</div>

				<div class="new-post-container">
					<form action="addPost" method="post" class="new-post-form" enctype="multipart/form-data">
						<textarea type="text" name="new-post-content" placeholder="Your text...."></textarea>
						<input type="file" name = "userPostImg" accept="image/*" onchange="loadFile(event)">
						<img id="output" class = "watermarks" style = "max-width: 500px;">
						<div class="new-post-selection">
							<input type="checkbox" id = "check_watermark" name="watermark-checkbox">Add Image Watermark
							<div id = "time-form">
							Post Durations:
							<select name="durations">
    							<option value="-1">No Limit</option>
    							<option value="-2">Demo</option>
    							<option value="1">1 Day</option>
    							<option value="3">3 Days</option>
    							<option value="7">7 Days</option>
   							</select>	
							</div>
						</div>
						
						${sessionScope[Keys.ONE_POST_CONTENT]}
						<button type="submit" value="Post" name="postButton" class="btn btn-warning">
							POST</button>
					</form>
				</div>

				<div class="post-container">
					<c:set var="list" value="${sessionScope[Keys.POST_LIST]}" />
					<c:forEach var="post" items="${list}">
					<c:if test="${post.status == 0 && post.author.username == sessionScope[Keys.USER]}">
						<div style="background-color: #98FB98	" class="post">
							<div class="post-content">
								<div class="post-user">
									<img class="user-home-avatar" src="data:image/${post.author.avatarType};base64,${post.author.avatar}" style = "max-width:50px; max-height:50px;">
									<div class="post-date">
										<c:if test="${post.author.username != 'System'}"><a href = "profile?username=${post.author.username}" >${post.author.username}</a></c:if>
										<c:if test="${post.author.username == 'System'}">${post.author.username}</c:if>
										${post.date }
									</div>
									<div class="post-update">
										<form action = "updatePost" method="post" >
											<div id = "time-form">
												<select name="durations">
													<option value="-1">No Limit</option>
					    							<option value="-2">Demo</option>
					    							<option value="1">1 Day</option>
					    							<option value="3">3 Days</option>
					    							<option value="7">7 Days</option>
					   							</select>	
											</div>
											<button type="submit" value="${post.id}" name = "repost-post">Repost</button>
										</form>
									</div>
								</div>
									<div>${post.postContent}</div>
									<c:if test = "${post.postImg != null }"><div><img class="post-image-content" src="data:image/${post.imageType};base64,${post.postImg}"></div>
									</c:if>
							</div>
						<div class="comment-container">
							<div>
								Comment:
							</div>
						<c:set var="commentList" value="${post.commentList}"/>
						<c:forEach var="comment" items = "${commentList}">
							<div class="comment">
								<div class="comment-user">
									<img class="user-home-avatar" src="data:image/${comment.commentUser.avatarType};base64,${comment.commentUser.avatar}" style = "max-width:50px; max-height:45px;">
									<div class="comment-time">
										<a href = "profile?username=${comment.commentUser.username}" >${comment.commentUser.username}</a>
										<div class="comment-date">${comment.date}</div>
									</div>
									<div class="comment-content">${comment.comment}</div>
								</div>
								
							</div>
							
							<div class="divide-line"></div>
						</c:forEach>
						<div class="comment-form">
							<form action = "addComment" method = "post" class="input-group">
								<input type = "text" name = "commentText" class="form-control me-2" placeholder="Your Comment..." required>
								<button type = "submit" name = "comment-post-id" class="btn btn-primary" value = "${post.id}">Add</button>
							</form>
						</div>
						</div>
						</div>
					</c:if>
					
					<c:if test="${post.status != 0}">
						<div class="post">
							<div class="post-content">
								<div class="post-user">
									<img class="user-home-avatar" src="data:image/${post.author.avatarType};base64,${post.author.avatar}" style = "max-width:50px; max-height:50px;">
									<div class="post-date">
										<c:if test="${post.author.username != 'System'}"><a href = "profile?username=${post.author.username}" >${post.author.username}</a></c:if>
										<c:if test="${post.author.username == 'System'}">${post.author.username}</c:if>
										${post.date }
									</div>
								</div>
									<div>${post.postContent}</div>
									<c:if test = "${post.postImg != null }"><div><img class="post-image-content" src="data:image/${post.imageType};base64,${post.postImg}"></div>
									</c:if>
							</div>
							
							<div class="comment-container">
						<c:set var="commentList" value="${post.commentList}"/>
						<c:forEach var="comment" items = "${commentList}">
							<div class="comment">
								<div class="comment-user">
									<img class="user-home-avatar" src="data:image/${comment.commentUser.avatarType};base64,${comment.commentUser.avatar}" style = "max-width:50px; max-height:45px;">
									<div class="comment-time">
										<a href = "profile?username=${comment.commentUser.username}" >${comment.commentUser.username}</a>
										<div class="comment-date">${comment.date}</div>
									</div>
									<div class="comment-content">${comment.comment}</div>
								</div>
								
							</div>
							
							<div class="divide-line"></div>
						</c:forEach>
						<div class="comment-form">
							<form action = "addComment" method = "post" class="input-group">
								<input type = "text" name = "commentText" class="form-control me-2" placeholder="Your Comment..." required>
								<button type = "submit" name = "comment-post-id" class="btn btn-primary" value = "${post.id}">Add</button>
							</form>
						</div>
						</div>
						</div>
					</c:if>
					</c:forEach>
 				</div>
			</div>
			<div class="right-container col-lg-3">
				<div class = "friend-list-container" id="friend-container">
				<label>Friend List</label>
				<ul class = "friend-ul">
					<c:set var="list" value = "${sessionScope[Keys.FRIEND_LIST]}"/>
					<c:forEach var = "friend" items = "${list}">
						<li class="friend">
							<a href = "profile?username=${friend}">${friend}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>

	</div>

	<div class="form-popup" id="myForm">
		<form class="form-container" method="post" id="edit-group-form">
			<h5>New Group Name</h5>
			<input id="myForm_value" type="text" name="new-group-name">
		</form>
		<div class="btn-group">
			<button id="myForm_submit" name="new-group-submit" type="submit"
				formaction="EditGroup" form="edit-group-form"
				class="btn btn-success">Done</button>
			<button class="btn btn-danger cancel" onclick="closeForm()">Close</button>
		</div>

	</div>
	</div>
<script type="text/javascript">
		document.getElementById("myForm").style.visibility = "hidden";

		function openForm(clicked_id) {
			document.getElementById("myForm_value").value = clicked_id;
			document.getElementById("myForm_submit").value = clicked_id;
			document.getElementById("myForm").style.visibility = "visible";
		}

		function closeForm() {
			document.getElementById("myForm").style.visibility = "hidden";
			
		}
		
		
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