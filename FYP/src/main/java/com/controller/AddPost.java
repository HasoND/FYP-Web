package com.controller;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.bean.User;
import com.dao.PostDao;
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
@WebServlet(urlPatterns = { "/addPost" })
public class AddPost extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		InputStream inputStream = null;
		InputStream inputImage = null;
		String imgType = null;
		
		HttpSession session = req.getSession();
		
		User user = (User) session.getAttribute(Keys.USER_SESSION);
		
		String postContent = req.getParameter("new-post-content").trim();
		String watermarkCheckbox = req.getParameter("watermark-checkbox");		
		int durations = Integer.parseInt(req.getParameter("durations"));
		Part filePart = req.getPart("userPostImg");
		
		if (filePart.getSize() > 0) {
			inputStream = filePart.getInputStream();

			ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
			Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

			while (imageReaders.hasNext()) {
				ImageReader reader = (ImageReader) imageReaders.next();
				imgType = reader.getFormatName();
			}
			if(watermarkCheckbox == null) {
				BufferedImage sourceImage = ImageIO.read(iis);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(sourceImage, imgType, baos);
		        byte[] bytes = baos.toByteArray();
		        inputImage = Converter.convertByteArrayToInputStream(bytes);		    
			}else {
				//call watermark function
				inputImage = Converter.convertByteArrayToInputStream(addWatermark("@"+user.getUsername(),imgType, iis));
			}
		}else {
			inputImage = null;
			imgType = null;
		}		
		if(!postContent.isBlank() || imgType != null) {
			session.setAttribute(Keys.ONE_POST_CONTENT,"");
			PostDao.addPost(user.getUsername(), postContent, inputImage, imgType, durations);
		}else {
			session.setAttribute(Keys.ONE_POST_CONTENT, "At least one content type (text/image). ");
		}		
		resp.sendRedirect("home");
	}
	
	
	protected static byte[] addWatermark(String usernameText,String imgType, ImageInputStream sourceImageFile) {
		 try {
		        BufferedImage sourceImage = ImageIO.read(sourceImageFile);
		        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
		 
		        // initializes necessary graphic properties
		        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		        g2d.setComposite(alphaChannel);
		        g2d.setColor(Color.RED);
		        g2d.setFont(new Font("Arial", Font.BOLD, 64));
		        FontMetrics fontMetrics = g2d.getFontMetrics();
		        Rectangle2D rect = fontMetrics.getStringBounds(usernameText, g2d);
		 
		        // calculates the coordinate where the String is painted
		        int cornerX = (sourceImage.getWidth() - (int) rect.getWidth() - 5);
		        int cornerY = (0 + sourceImage.getHeight() - ((int)rect.getHeight()/2));
		 
		        // paints the textual watermark
		        g2d.drawString(usernameText, cornerX, cornerY);
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        ImageIO.write(sourceImage, imgType, baos);
		        byte[] bytes = baos.toByteArray();
		        g2d.dispose();
		 
		        return bytes;
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		return null;
	}

}
