package com.controller;

import java.io.IOException;

import com.bean.User;
import com.dao.GroupDao;
import com.helper.Keys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(urlPatterns = {"/UpdateToGroup"})
public class UpdateToGroup extends HttpServlet{

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			 HttpSession session = req.getSession(); 
			 User user = (User) session.getAttribute(Keys.USER_SESSION);
			 String uname = user.getUsername();
			 String fname = req.getParameter("update-group-submit");
			 String[] addedGroup = req.getParameterValues("add-group");
	        
	        GroupDao.updateToGroup(uname, fname, addedGroup);
	                
            resp.sendRedirect("/FYP/profile?username="+fname);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
}
