package com.bean;

public class Comment {

	private int id;
	private int postId;
	private String comment;
	private String date;
	private User commentUser;

	public Comment(int id, int postId, String comment, String date, User commentUser) {
		super();
		this.id = id;
		this.postId = postId;
		this.comment = comment;
		this.date = date;
		this.commentUser = commentUser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public User getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
	}	
}
