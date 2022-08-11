package com.controller;

import java.io.IOException;

import com.bean.User;
import com.dao.UserDao;
import com.helper.Keys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/deleteFriend" })
public class DeleteFriend extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Keys.USER_SESSION);
        String uname = user.getUsername();
        String fname = req.getParameter("friendName");
        String test = req.getParameter("deleteGroup");
        if(test == null){
        	if(!UserDao.deleteFriend(uname, fname)){
        		resp.sendRedirect("/FYP/profile?username="+fname);
        	}
        }else { 
        	if(!UserDao.deleteFriendANDGrouping(uname, fname)){
        		resp.sendRedirect("/FYP/profile?username="+fname);
			 } 
        }       
       resp.sendRedirect("/FYP/profile?username="+fname);  
	}
}
