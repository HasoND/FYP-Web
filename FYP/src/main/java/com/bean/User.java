package com.bean;

public class User {

	private int id;
	private String username;
	private String password;
	private String email;
	private String gender;
	private String country;
	private String dob;
	private String avatar;
	private String avatarType;
	private Boolean friendStatus;
	
	public User(int id, String username, String email, String gender, String country, String dob,
			String avatar, String avatarType) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.dob = dob;
		this.avatar = avatar;
		this.avatarType = avatarType;
	}

	public User(int id, String username,String email, String gender, String country, String dob,
			String avatar, String avatarType, Boolean friendStatus) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.dob = dob;
		this.avatar = avatar;
		this.avatarType = avatarType;
		this.friendStatus = friendStatus;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}	
	public String getAvatarType() {
		return avatarType;
	}
	public void setAvatarType(String avatarType) {
		this.avatarType = avatarType;
	}
	public Boolean getFriendStatus() {
		return friendStatus;
	}
	public void setFriendStatus(Boolean friendStatus) {
		this.friendStatus = friendStatus;
	}	
}
