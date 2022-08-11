package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bean.Group;

public class GroupDao extends Dao{
	
	public static List<Group> getGroupList(String username) throws SQLException {
        List<Group> groupList = new ArrayList<>();
        try{
        	Connection con=getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM category WHERE username = ? ORDER BY groupname ASC");
			ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	int id = rs.getInt("id");
            	String groupName = rs.getString("groupname");
                Group grouping = new Group(id, groupName,username);
                groupList.add(grouping);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        return groupList;
	}
	
	public static int createNewGroup(String groupName, String username) throws SQLException {
		
		try {
			Connection con=getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO category values(null, ?, ?)");
			ps.setString(1, groupName);
			ps.setString(2, username);
			return ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int editGroup(String new_groupName, String groupName, String username) {
		try {
			Connection con=getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE category SET groupname = ? WHERE groupname = ? AND username = ?");
			ps.setString(1, new_groupName);
			ps.setString(2, groupName);
			ps.setString(3, username);
			
			PreparedStatement ps1 = con.prepareStatement("UPDATE group_list SET groupname = ? WHERE groupname = ? AND username = ?");
			ps1.setString(1, new_groupName);
			ps1.setString(2, groupName);
			ps1.setString(3, username);

			ps.execute();
			ps1.execute();
			return 1;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int deleteGroup(String groupName, int groupId, String username) {
		
		try {
			Connection con=getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM category WHERE id = ? AND groupname = ? AND username = ?");
			ps.setInt(1, groupId);
			ps.setString(2, groupName);
			ps.setString(3, username);
			ps.execute();
			PreparedStatement ps1 = con.prepareStatement("DELETE FROM group_list WHERE groupname = ? AND username = ?");
			ps1.setString(1, groupName);
			ps1.setString(2, username);
			ps1.execute();
			return 1;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int updateToGroup(String username, String friendname, String[] groupArray) {
		String groupname = "";
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM group_list WHERE username = ? AND friendname = ?");
			ps.setString(1, username);
			ps.setString(2, friendname);
			ps.executeUpdate();
			
			for(int i = 0 ;i < groupArray.length; i++) {
				groupname = groupArray[i];
				PreparedStatement ps1 = con.prepareStatement("INSERT INTO group_list VALUES (null, ?, ?, ?)");
				ps1.setString(1, groupname);
				ps1.setString(2, friendname);
				ps1.setString(3, username);
				ps1.executeUpdate();	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static List<Group> getCheckList(String username, String friendname){
		List<Group> groupList = new ArrayList<>();
        try{
        	Connection con=getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ("
					+ "(SELECT groupname, false AS checked FROM category WHERE groupname NOT IN (SELECT groupname FROM group_list WHERE username = ? AND friendname = ?) AND username = ?)"
					+ "UNION"
					+ "(SELECT groupname, true AS checked FROM group_list WHERE username = ? AND friendname = ?)"
					+ ")results ORDER BY groupname ASC");
			ps.setString(1, username);
			ps.setString(2, friendname);
			ps.setString(3, username);
			ps.setString(4, username);
			ps.setString(5, friendname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String groupName = rs.getString("groupname");
            	boolean check = rs.getBoolean("checked");
                Group grouping = new Group(groupName, check);
                groupList.add(grouping);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        return groupList;
	}

}
