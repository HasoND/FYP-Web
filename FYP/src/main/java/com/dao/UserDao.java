package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.sql.*;

import com.helper.Converter;
import com.bean.User;

public class UserDao extends Dao{
	
	public User checkUserLogin(String username, String password) throws SQLException{
		User user = null;
		try {
			Connection con = getConnection();
			String sql = "SELECT * FROM users WHERE username = ? and password = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				int userID = rs.getInt("id");
                String uname = rs.getString("username");
                String uemail = rs.getString("email");
                String gender = rs.getString("gender");
                String country = rs.getString("country");
                String dob = rs.getString("dob");
                String avatar= Converter.convertAvatarBase64(rs.getBytes("avatar"));
                String avatarType = rs.getString("avatarType");
                
                if(avatarType == null) {
                	avatarType = "png";
                }
                if(dob == null) {
                	dob = "N/A";
                }
				
	            user = new User(userID, uname, uemail, gender, country, dob, avatar, avatarType);
	        return user;
			}
	        
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean checkRegisterUsername(String registername) throws SQLException{
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ?");
			ps.setString(1, registername);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	public static int registerNewUser(String username, String password, String email, String gender, String country) throws SQLException{
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (null, ?, ?, ?, ?, ?,null, null, null)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, email);
			if(gender == null) {
				ps.setString(4,"N/A");
			}else {
				ps.setString(4,gender);
			}
			ps.setString(5,country);
			return ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static List<User> searchFriend(String username, String sessionUname) throws SQLException{
		List<User> searchList = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username LIKE '%"+username+"%' AND username IN "
					+ "(SELECT friendname AS username FROM friend WHERE username = ?) AND username != ?");
			ps.setString(1, sessionUname);
			ps.setString(2, sessionUname);
			ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	int userID = rs.getInt("id");
                String uname = rs.getString("username");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String country = rs.getString("country");
                String dob = rs.getString("dob");
                String avatar= Converter.convertAvatarBase64(rs.getBytes("avatar"));
                String avatarType = rs.getString("avatarType");
                

                if(avatarType == null) {
                	avatarType = "png";
                }
                if(dob == null) {
                	dob = "N/A";
                }
                
                
                User searchedUser = new User(userID, uname, email, gender, country, dob, avatar, avatarType, true);
                searchList.add(searchedUser);
            }
            String preparedstatement = "";
            if(username == null || username == "") {
            	preparedstatement = "SELECT * FROM users WHERE username NOT IN (SELECT friendname AS username FROM friend WHERE username = ?) AND username != ? AND username != 'System'";
            }else {
            	preparedstatement = "SELECT * FROM users WHERE username LIKE '%"+username+"%' AND username NOT IN (SELECT friendname AS username FROM friend WHERE username = ?) AND username != ? AND username != 'System'";
            }
            PreparedStatement ps1 = con.prepareStatement(preparedstatement);
            ps1.setString(1, sessionUname);
			ps1.setString(2, sessionUname);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
            	int userID = rs1.getInt("id");
                String uname = rs1.getString("username");
                String email = rs1.getString("email");
                String gender = rs1.getString("gender");
                String country = rs1.getString("country");
                String dob = rs1.getString("dob");
                String avatar= Converter.convertAvatarBase64(rs1.getBytes("avatar"));
                String avatarType = rs1.getString("avatarType");
                

                if(avatarType == null) {
                	avatarType = "png";
                }
                if(dob == null) {
                	dob = "N/A";
                }
                
                
                User searchedUser = new User(userID, uname, email, gender, country, dob, avatar, avatarType, false);
                searchList.add(searchedUser);
            }
            
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchList;
	}

	public static boolean isFriend(String username, String friendname) {
		
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT friendname FROM friend WHERE username =? AND friendname = ?");
			ps.setString(1, username);
			ps.setString(2, friendname);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static int addFriend(String username, String friendname) throws SQLException{
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO friend VALUES (null, ?,?)");
			ps.setString(1, username);
			ps.setString(2, friendname);
			PreparedStatement ps1 = con.prepareStatement("INSERT INTO group_list VALUES (null, ?, ?, ?)");
			ps1.setString(1, "All");
			ps1.setString(2, friendname);
			ps1.setString(3, username);
			ps.execute();
			ps1.execute();
			PostDao.addAnnouce(username, friendname);
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static boolean deleteFriend(String username, String friendname) {
		
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM friend WHERE username = ? AND friendname = ?");
			ps.setString(1, username);
			ps.setString(2, friendname);
			PreparedStatement ps1 = con.prepareStatement("DELETE FROM annoucement WHERE user1 = ? AND user2 = ?");
			ps1.setString(1, username);
			ps1.setString(2, friendname);
			if(ps.executeUpdate() >= 1 && ps1.executeUpdate() >= 1) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
public static boolean deleteFriendANDGrouping(String username, String friendname) {
		
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM friend WHERE username = ? AND friendname = ?");
			ps.setString(1, username);
			ps.setString(2, friendname);
			PreparedStatement ps1 = con.prepareStatement("DELETE FROM annoucement WHERE user1 = ? AND user2 = ?");
			ps1.setString(1, username);
			ps1.setString(2, friendname);
			PreparedStatement ps2 = con.prepareStatement("DELETE FROM group_list WHERE username = ? AND friendname = ?");
			ps2.setString(1, username);
			ps2.setString(2, friendname);
			ps.execute();
			ps1.execute();
			ps2.execute();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static User getUserDetail(String sessionusername) throws SQLException{
		User user = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ?");
			ps.setString(1, sessionusername);
			ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	int userID = rs.getInt("id");
                String uname = rs.getString("username");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String country = rs.getString("country");
                String dob = rs.getString("dob");
                String avatar= Converter.convertAvatarBase64(rs.getBytes("avatar"));
                String avatarType = rs.getString("avatarType");
                
                if(avatarType == null) {
                	avatarType = "png";
                }
                if(dob == null) {
                	dob = "N/A";
                }
                
                user = new User(userID, uname, email, gender, country, dob, avatar, avatarType);
                
            }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static List<String> getFriend(String sessionusername){
		List<String> friendList = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT friendname FROM friend WHERE username = ?");
			ps.setString(1, sessionusername);
			ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	String friendname = rs.getString("friendname");
            	friendList.add(friendname);
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return friendList;
	}
	
	public static int updateAvatar(String username, InputStream is, String avatarType) {
		
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE users SET avatar = ?, avatarType = ? WHERE username = ?");
			ps.setBlob(1, is);
			ps.setString(2, avatarType);
			ps.setString(3, username);
			
			
			if(ps.executeUpdate() >0)
				return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static int updateProfile(String username, String dob, String gender, String country, String email) throws SQLException{
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE users SET dob = ?, gender = ?, country = ?, email = ? WHERE username = ?");
			ps.setString(1, dob);
			ps.setString(2, gender);
			ps.setString(3, country);
			ps.setString(4, email);
			ps.setString(5, username);
			
			if(ps.executeUpdate() > 0) {
				return 1;
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
