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

@WebServlet(urlPatterns = {"/DeleteGroup"})
public class DeleteGroup extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Keys.USER_SESSION);
		String groupName = req.getParameter("groupName");
		int groupId = Integer.parseInt(req.getParameter("groupId"));
        if(GroupDao.deleteGroup(groupName, groupId, user.getUsername()) >= 1) {
        		resp.sendRedirect("home");
        }
	}
}
