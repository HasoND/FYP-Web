package com.controller;

import java.io.IOException;

import com.dao.GroupDao;
import com.dao.UserDao;
import com.helper.Keys;
import com.helper.Validate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterAction")
public class Register extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String username = req.getParameter("user-username").trim();
			String password1 = req.getParameter("user-password").trim();
			String password2 = req.getParameter("user-check-password").trim();
			String email = req.getParameter("user-email").trim();
					
			String tempUname = "";
			String tempPassword1 = "";
			String tempPassword2 = "";
			String tempEmail = "";
			
			String gender = req.getParameter("userGender");
			String country = req.getParameter("country");
			
			RequestDispatcher reqDis = req.getRequestDispatcher("register.jsp");
			if (Validate.checkUsername(username)==false||UserDao.checkRegisterUsername(username)==false) {
				tempUname = "";
				req.setAttribute(Keys.REGISTER_USERNAME_ERROR, "Invalid Username.");
			}else {
				tempUname = username;
			}
			
			
			if (Validate.checkPassword(password1) == false) {
				tempPassword1 = "";
				req.setAttribute(Keys.REGISTER_PASSWORD_ERROR, "Invalid Password.");
			}else {
				tempPassword1 = password1;
				req.setAttribute(Keys.REGISTER_PASSWORD_ERROR, "");
				if (Validate.comfirmPassword(password1, password2) == false) {
					tempPassword2 = "";
					req.setAttribute(Keys.REGISTER_COMFIRMPASSWORD_ERROR, "Password is incorrect.");
				}else {
					tempPassword2 = tempPassword1;
					req.setAttribute(Keys.REGISTER_COMFIRMPASSWORD_ERROR, "");
				}
			}			
			
			if (Validate.checkEmail(email)==false) {
				tempEmail = "";
				req.setAttribute(Keys.REGISTER_EMAIL_ERROR, "Invalid Email.");
			}else {
				tempEmail = email;
				req.setAttribute(Keys.REGISTER_EMAIL_ERROR, "");
			}

			req.setAttribute("tempEmail", tempEmail);
			req.setAttribute("tempPassword1",tempPassword1);
			req.setAttribute("tempPassword2",tempPassword2);
			req.setAttribute("tempUsername",tempUname);
			System.out.println(password1.equals(password2));
			if (Validate.checkUsername(username) && Validate.checkPassword(password1)
					&& Validate.comfirmPassword(password1, password2) && Validate.checkEmail(email)
					&& UserDao.checkRegisterUsername(username)==true) {
				if (UserDao.registerNewUser(username, password2, email, gender, country) > 0) {
					if(GroupDao.createNewGroup("All", username) == 1) {
						resp.sendRedirect("login.jsp");
					}
				}else {
					reqDis.forward(req, resp);
				}
			}else {
				reqDis.forward(req, resp);
			}

		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	
}
