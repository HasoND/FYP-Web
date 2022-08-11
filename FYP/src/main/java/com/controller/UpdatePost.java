package com.controller;

import java.io.IOException;

import com.bean.User;
import com.dao.PostDao;
import com.helper.Keys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/updatePost"})
public class UpdatePost extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			 HttpSession session = req.getSession(); 
			 User user = (User) session.getAttribute(Keys.USER_SESSION);
			 String uname = user.getUsername();
			 int postId = Integer.parseInt(req.getParameter("repost-post"));
			 int durations = Integer.parseInt(req.getParameter("durations"));
			 
			 if(PostDao.editPost(postId, durations) == 1) {
				 resp.sendRedirect("home");
			 } 
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
