package com.controller;

import java.io.IOException;
import java.util.List;

import com.bean.Group;
import com.bean.Post;
import com.bean.User;
import com.dao.GroupDao;
import com.dao.PostDao;
import com.dao.UserDao;
import com.helper.Keys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(urlPatterns = {"/home"})
public class Home extends HttpServlet{

	 protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
	    	try {
	            
	            HttpSession session = request.getSession();
	            User user = (User)session.getAttribute(Keys.USER_SESSION);
	            if(user == null) {
	            	response.sendRedirect("login.jsp");
	            	return;
	            }
	            
	            String groupName = (String)session.getAttribute(Keys.SET_GROUP);
	            
	            List<Group> groupList = GroupDao.getGroupList(user.getUsername());
	            session.setAttribute(Keys.GROUP_LIST, groupList);
	            
	            if(groupName == null || groupName == "All") {
	            	List<Post> postList = PostDao.getPost(user.getUsername(), "All");
	            	session.setAttribute(Keys.POST_LIST, postList);
	            }else {
	            	List<Post> postList = PostDao.getPost(user.getUsername(), groupName);
		            session.setAttribute(Keys.POST_LIST, postList);
	            }
	            
	            List<String> friendList = UserDao.getFriend(user.getUsername());
	            session.setAttribute(Keys.FRIEND_LIST, friendList);
	            	           
	    		RequestDispatcher dis = request.getRequestDispatcher("home.jsp");
	            dis.forward(request, response);
	    	}catch (Exception ex) {
	            ex.printStackTrace();
	        }		    	
	    }	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(req, resp);
	}

}
