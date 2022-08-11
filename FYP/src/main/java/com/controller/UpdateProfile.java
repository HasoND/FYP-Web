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

@WebServlet(urlPatterns = {"/updateProfile"})
public class UpdateProfile extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			HttpSession session = req.getSession(); 
			User user = (User) session.getAttribute(Keys.USER_SESSION);
			String uname = user.getUsername();
			
	        String newdob = req.getParameter("new-dob");
	        String newgender = req.getParameter("new-gender");
	        String newcountry = req.getParameter("new-country");
	        System.out.println(newcountry);
	        String newemail = req.getParameter("new-email");
	        
	        if(UserDao.updateProfile(uname, newdob, newgender, newcountry, newemail) == 1) {
	        	User user1 = UserDao.getUserDetail(uname);
				session.setAttribute(Keys.USER_SESSION, user1);
	        	resp.sendRedirect("profile?username="+uname);
	        }	        
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
}
