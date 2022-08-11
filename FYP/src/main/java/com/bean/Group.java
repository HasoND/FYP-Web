package com.bean;

public class Group {
	
	private int id;
	private String groupName;
	private String username;
	private String friendname;
	private boolean checked;
	
	public Group(int id, String groupName, String username) {
		this.id = id;
		this.groupName = groupName;
		this.username = username;
	}
	
	public Group(String groupName, boolean checked) {
		this.groupName = groupName;
		this.checked = checked;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFriendname() {
		return friendname;
	}
	public void setFriendname(String friendname) {
		this.friendname = friendname;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
