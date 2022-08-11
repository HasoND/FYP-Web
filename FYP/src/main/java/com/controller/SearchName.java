package com.controller;

import java.io.IOException;
import java.util.List;

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

@WebServlet(urlPatterns = {"/searchName"})
public class SearchName extends HttpServlet{

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		try {
            HttpSession session = req.getSession();
            User sessionuser = (User) session.getAttribute(Keys.USER_SESSION);
            
            if(sessionuser== null) {
            	RequestDispatcher dis = req.getRequestDispatcher("login.jsp");
                dis.forward(req, resp);	
            }
            String sessionuname = sessionuser.getUsername();
            String searchInput = req.getParameter("search-name-bar");
            List<User> searchedList = UserDao.searchFriend(searchInput,sessionuname);
            session.setAttribute(Keys.SEARCH_LIST, searchedList);
            session.setAttribute(Keys.SEARCH_INPUT, "Result of '" + searchInput + "'");
            
            RequestDispatcher dis = req.getRequestDispatcher("search-friend-page.jsp");
            dis.forward(req, resp);
            
		}catch(Exception e) {
        	e.printStackTrace();;
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
