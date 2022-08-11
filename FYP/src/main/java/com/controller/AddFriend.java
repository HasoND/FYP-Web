package com.controller;

import java.io.IOException;

import com.bean.User;
import com.dao.UserDao;
import com.helper.Keys;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/addFriend"})
public class AddFriend extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(Keys.USER_SESSION);
            String uname = user.getUsername();
            String fname = req.getParameter("friendName");
            
            int isAdd = UserDao.addFriend(uname, fname);
			System.out.println(isAdd);
	        resp.sendRedirect("home");
			
    	}catch (Exception e) {
            e.printStackTrace();
        }
	}
}
