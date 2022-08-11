package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.bean.User;
import com.bean.Group;
import com.bean.Post;
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

@WebServlet(urlPatterns = {"/profile"})
public class Profile extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		try {
			HttpSession session = req.getSession();
	        User user = (User) session.getAttribute(Keys.USER_SESSION);
	        if(user == null) {
	        	resp.sendRedirect("login.jsp");
	        	return;
	        }
	        String uname = user.getUsername();
			String fname = req.getParameter("username");

			List<Post> mypostList = new ArrayList<>();
			if(uname.equals(fname)) {
				mypostList = PostDao.getMyPost(fname);
			}else {
				mypostList = PostDao.getFriendPost(fname);
			}			
			
			if(fname != null) {
				session.setAttribute(Keys.CLICKED_USER, fname);
				session.setAttribute(Keys.CLICKED_USER_INFO, UserDao.getUserDetail(fname));
				session.setAttribute(Keys.IS_FRIEND, UserDao.isFriend(uname, fname));
				if(UserDao.isFriend(uname, fname)) {
					List<Group> checkList = GroupDao.getCheckList(uname, fname);
					session.setAttribute(Keys.CHECKED_LIST, checkList);
				}
				if (mypostList == null) {
					session.setAttribute(Keys.NO_POST_NOTICE, "This user haven't post anythings yet.");
					session.setAttribute(Keys.MY_POST_LIST, mypostList);
				} else {
					session.setAttribute(Keys.NO_POST_NOTICE, "");
					session.setAttribute(Keys.MY_POST_LIST, mypostList);
				}
			}			
		
			RequestDispatcher dis = req.getRequestDispatcher("profile.jsp");
			dis.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
}
