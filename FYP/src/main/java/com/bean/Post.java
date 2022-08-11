package com.bean;

import java.util.List;

public class Post {

	private int id;
    private String postContent;
    private String date;
    private String postImg;
    private String imageType;
    private int status;    
    private User author;
    private List<Comment> commentList;
    
	public Post(int id, String postContent, String date, User author, String postImg, String imageType, int status,
			List<Comment> commentList) {
		this.id = id;
		this.postContent = postContent;
		this.date = date;
		this.author = author;
		this.postImg = postImg;
		this.imageType = imageType;
		this.status = status;
		this.commentList = commentList;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getPostImg() {
		return postImg;
	}
	public void setPostImg(String postImg) {
		this.postImg = postImg;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
}
