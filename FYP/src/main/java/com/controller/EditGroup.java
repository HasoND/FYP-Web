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

@WebServlet(urlPatterns = {"/EditGroup"})
public class EditGroup extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Keys.USER_SESSION);
		String newGroupName = req.getParameter("new-group-name");
		String groupName = req.getParameter("new-group-submit");
		if(!newGroupName.isEmpty() && user != null){
        	if(GroupDao.editGroup(newGroupName, groupName, user.getUsername()) >= 1) {
        		resp.sendRedirect("home");
        	}
        }else {
        	resp.sendRedirect("home");
        }
	}
	
}
