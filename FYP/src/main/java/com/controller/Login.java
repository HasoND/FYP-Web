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

@WebServlet(urlPatterns = { "/login" })
public class Login extends HttpServlet {

	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// TODO Auto-generated method stub

		String username = req.getParameter("username-login");
		String password = req.getParameter("userpass-login");

		UserDao userdao = new UserDao();
		try {
			User user = userdao.checkUserLogin(username, password);
			HttpSession session = req.getSession();
			if (user != null) {
				session.setAttribute(Keys.USER_SESSION, user);
				session.setAttribute(Keys.USER, user.getUsername());
				req.setAttribute("groupType", "all");
				session.setMaxInactiveInterval(300); 
				
				res.sendRedirect("home");
			} else {
				session.setAttribute(Keys.LOGIN_ERROR, "Invalid login information");
				res.sendRedirect("login.jsp");
			}

		} catch (Exception ex) {
			ex.printStackTrace();		
			}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dis = req.getRequestDispatcher("/login.jsp");
        dis.forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		processRequest(req, res);
	}
}

