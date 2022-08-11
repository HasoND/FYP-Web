package com.controller;

import java.io.IOException;

import com.bean.User;
import com.dao.PostDao;
import com.helper.Keys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/addComment"})
public class AddComment extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
            try {
            	User user = (User) session.getAttribute(Keys.USER_SESSION);
                String uname = user.getUsername();
                String content = req.getParameter("commentText");
                int postId = Integer.parseInt(req.getParameter("comment-post-id"));
                if (PostDao.addComment(uname, content, postId) == 1) {
                    resp.sendRedirect("home");
                } else {
                    session.setAttribute(Keys.ERROR, "Cannot upload your comment. Try again later!");
                }
            } catch (Exception ex) {
               	ex.printStackTrace();
            }
	}
}

