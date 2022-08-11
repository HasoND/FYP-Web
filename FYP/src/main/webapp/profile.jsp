<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
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
<link rel = "stylesheet" href = "resources/CSS/profile.css">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.helper.Keys"%>
<%@page import="java.util.*"%>
<%@page import="com.bean.User"%>
<%@page import="com.dao.UserDao"%>
<%@page import="com.bean.Group"%>


<meta charset="ISO-8859-1">

<title>
Profile
</title>
</head>
<body>
<%
User user = null;
User clicked_user = null;
	 if (session == null || session.getAttribute(Keys.USER_SESSION) == null) {
    	response.sendRedirect("login.jsp");
    	return;
	}else{ 
		user = (User)session.getAttribute(Keys.USER_SESSION);
		clicked_user = (User)session.getAttribute(Keys.CLICKED_USER_INFO);
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
				<span id="time"></span>
				
				<form action = "logout" method="post">
					<button type="submit" class = "btn btn-danger">Logout</button>
				</form>
			</div>
		</nav>

	<div class="content-container row">	
		<div class="left-container col-lg-3">
			<div class="user-home-link">
				<img class="user-home-avatar" src="data:image/<%=clicked_user.getAvatarType()%>;base64,<%=clicked_user.getAvatar()%>" style = "max-width:100px">
				<div class="profile-edit">
					${sessionScope[Keys.CLICKED_USER]}
					<c:if test="${sessionScope[Keys.CLICKED_USER] == sessionScope[Keys.USER]}">
						<a href="edit-profile.jsp"><img src="resources/images/edit_pen.png" style="width: 25px; height: 25px;">
						</a>
					</c:if>
				</div>
				
			<div class="profile-detail-container">
				<div class="profile-group-container">
					<c:if test="${sessionScope[Keys.CLICKED_USER] != sessionScope[Keys.USER]}">
						<button type="button" class="btn btn-primary dropdown-toggle"
							data-toggle="dropdown" style="border-radius: 0;">Manage</button>
					</c:if>
					<div id="dd-menu" class="dropdown-menu" style="padding: 0px;">

						<c:if test="${sessionScope[Keys.IS_FRIEND] == true }">
							<button value="${sessionScope[Keys.CLICKED_USER]}"
								class="dropdown-item grouping-button" onclick="openForm()">Add
								Group</button>
							<button value="${sessionScope[Keys.CLICKED_USER]}"
								class="dropdown-item" style="background-color: yellow;" onclick="openDeleteForm()">Delete
								Friend</button>
						</c:if>
						<c:if test="${sessionScope[Keys.IS_FRIEND] == false }">
							<a class="dropdown-item"
								href="addFriend?friendName=${sessionScope[Keys.CLICKED_USER]}">ADD</a>
						</c:if>

					</div>

				</div>
			</div>
			</div>
		</div>
		<div class="mid-container col-lg-6">
				
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
				${sessionScope[Keys.NO_POST_NOTICE]}
				<c:set var="list" value="${sessionScope[Keys.MY_POST_LIST]}" />
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
									<c:if test="${post.author.username == sessionScope[Keys.USER]}">
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
									</c:if>
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
				
				</c:forEach>
			</div>
		</div>
		<div class="right-container col-lg-3"></div>
	</div>

	<div class="form-popup" id="addGroupForm">
		<form class="form-container" method="post" id="update-to-group">
			<div class = "form-header">
				<h5>Group List</h5>
				<button type="button" class="btn-close" aria-label="Close" onclick="closeForm()"></button>
			</div>
			
			<div class="grouping-list-container" id="grouping-container">
				<c:set var="list" value="${sessionScope[Keys.CHECKED_LIST]}" />
				<c:forEach var="group" items="${list}">
					<div id="grouping-items">
						<label class = "group-checkbox">
						<input type="checkbox"
							<c:if test="${group.checked==true}">checked=checked</c:if>
							<c:if test="${group.groupName == 'All'}">disabled=disabled</c:if>
							name="add-group" value="${group.groupName}"> 
							${group.groupName }</label>
					</div>
				</c:forEach>
			</div>

		</form>
		<div class = "grouping-bottom">
			<button type="submit" formaction="UpdateToGroup" form="update-to-group" class="btn btn-primary" name="update-group-submit" value="${sessionScope[Keys.CLICKED_USER]}">Done</button>
		</div>
	</div>
	
	<div class = "form-popup" id = "deleteFriendForm">
		<form class = "form-container" method = "post" id = "delete-friend-form">
			<div class = "form-header">
				<p>Are you sure want to delete?</p>
			</div>
			<input class="" type="checkbox" name="deleteGroup" value="deleteGroup">Tick if you wish to delete friend's grouping as well.</input>
		</form>
		
		<div>
		<button class="btn btn-primary" onclick="closeDeleteForm()">Cancel</button>
			<button class="btn btn-danger" formaction = "deleteFriend" form="delete-friend-form" name="friendName" value="${sessionScope[Keys.CLICKED_USER]}">DELETE</button>
		
		</div>
	</div>

	<script type="text/javascript">
		document.getElementById("addGroupForm").style.visibility = "hidden";
		document.getElementById("deleteFriendForm").style.visibility = "hidden";		
		
		function openForm() {
			document.getElementById("addGroupForm").style.visibility = "visible";
		}
		function openDeleteForm() {
			document.getElementById("deleteFriendForm").style.visibility = "visible";
		}
		function closeForm() {
			document.getElementById("addGroupForm").style.visibility = "hidden";
		}
		function closeDeleteForm() {
			document.getElementById("deleteFriendForm").style.visibility = "hidden";
		}
		var offsetHeight = document.getElementById('grouping-items').offsetHeight;
		document.getElementById("grouping-container").style.maxHeight = (offsetHeight*5) - (offsetHeight/2) + "px";
	</script>
</body>
</html>