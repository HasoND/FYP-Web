package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bean.User;
import com.dao.GroupDao;
import com.helper.Keys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/AddNewGroup"})
public class AddGroupList extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        try {
            HttpSession session = request.getSession();
            String newgroup = request.getParameter("newGroup");
            User user = (User) session.getAttribute(Keys.USER_SESSION);
            if(!newgroup.isEmpty() && user != null){
            	if(GroupDao.createNewGroup(newgroup, user.getUsername()) == 1) {
            		response.sendRedirect("home");
            	}
            }else {
            	response.sendRedirect("home");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddGroupList.class.getName()).log(Level.SEVERE, null, ex);
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
