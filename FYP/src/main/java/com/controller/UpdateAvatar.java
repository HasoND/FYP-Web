package com.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.bean.User;
import com.dao.UserDao;
import com.helper.Converter;
import com.helper.Keys;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(maxFileSize = 16177215)
@WebServlet(urlPatterns= {"/updateAvatar"})
public class UpdateAvatar extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Keys.USER_SESSION);
		
		InputStream inputStream = null;
		InputStream inputImage = null;
		String imgType = null;
		Part filePart = req.getPart("editAvatar");
		if (filePart.getSize() > 0) {
			try {
			inputStream = filePart.getInputStream();

			ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
			Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
						
			//get image type
			while (imageReaders.hasNext()) {
				ImageReader reader = (ImageReader) imageReaders.next();
				imgType = reader.getFormatName();
			}
			BufferedImage sourceImage = ImageIO.read(iis);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(sourceImage, imgType, baos);
	        byte[] bytes = baos.toByteArray();
	        inputImage = Converter.convertByteArrayToInputStream(bytes);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if(UserDao.updateAvatar(user.getUsername(), inputImage, imgType) > 0) {
			try {
				User user1 = UserDao.getUserDetail(user.getUsername());
				session.setAttribute(Keys.USER_SESSION, user1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.sendRedirect("edit-profile.jsp");
		}		
	}
}
