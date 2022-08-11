package com.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bean.Comment;
import com.bean.Post;
import com.bean.User;
import com.helper.Converter;

public class PostDao extends Dao {

	public static int addPost(String username, String content, InputStream is, String imgType, int duration) {

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO post VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, username);
			ps.setString(2, content);
			ps.setBlob(3, is);
			ps.setString(4, imgType);
			ps.setString(5, Converter.ConvertDateTimer());

			String dt = Converter.ConvertDateTimer();
			String dt1 = "";
			if (duration != -1) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.setTime(sdf.parse(dt));
					c.add(Calendar.DATE, duration); // number of days to add
					dt1 = sdf.format(c.getTime()); // dt1 is now the new date
					sdf.parse(dt).before(sdf.parse(dt1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(duration >= 0) {
					ps.setString(6, dt);
					ps.setString(7, dt1);
					ps.setInt(8, duration);
					ps.setInt(9, 1);
				}else {
					ps.setString(6, dt);
					ps.setString(7, dt1);
					ps.setInt(8, duration + 1);
					ps.setInt(9, 1);
				}
			} else{

				ps.setString(6, null);
				ps.setString(7, null);
				ps.setString(8, null);
				ps.setInt(9, -1);

			}

			return ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	public static int addAnnouce(String username, String friendname) {

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO annoucement VALUES (null, ?, ?, ?, ?, ?)");
			ps.setString(1, "System");
			ps.setString(2, username);
			ps.setString(3, friendname);
			String content = "You and " + friendname + " has become friend.";
			ps.setString(4, content);
			ps.setString(5, Converter.ConvertDateTimer());
			if (ps.executeUpdate() >= 1) {
				return 1;
			} else {
				return 0;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	public static List<Post> getPost(String username, String groupname) {
		List<Post> postlist = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = null;
			if (groupname.equals("All") || groupname.isEmpty()) {
				ps = con.prepareStatement("SELECT * FROM ("
						+ "(SELECT NULL AS id, annoucement.system AS author, annoucement.content, NULL AS image, NULL AS imageType, annoucement.date, "
						+ "NULL AS showDate, NULL AS status  FROM annoucement WHERE user1 = ? or user2 = ?)"
						+ "UNION ALL"
						+ "(SELECT post.id, post.username AS author, post.content, post.image, post.imageType, post.date, post.showDate, post.status FROM post"
						+ "	WHERE username = ? or username in((SELECT friendname AS friendname FROM `friend` WHERE username = ?) UNION ALL "
						+ "(SELECT username AS friendname FROM `friend` WHERE friendname = ?)"
						+ ")))" + "results ORDER BY date DESC");
				ps.setString(1, username);
				ps.setString(2, username);
				ps.setString(3, username);
				ps.setString(4, username);
				ps.setString(5, username);
			} else {
				ps = con.prepareStatement("SELECT * FROM ("
						+ "(SELECT NULL AS id, annoucement.system AS author, annoucement.content, NULL AS image,NULL AS imageType, annoucement.date, "
						+ "NULL AS showDate, NULL AS status FROM annoucement WHERE user1 = ? AND user2 in "
						+ "(SELECT friendname FROM `group_list` WHERE username = ? AND groupname = ?) "
						+ "	or user2 = ? AND user1 in (SELECT friendname FROM `group_list` WHERE username = ? AND groupname = ?))"
						+ "UNION ALL"
						+ "(SELECT post.id, post.username AS author, post.content, post.image, post.imageType, post.date, post.showDate, post.status FROM post"
						+ "	WHERE username in"
						+ "    (SELECT friendname AS username FROM `group_list` WHERE username = ? AND groupname = ? AND friendname in"
						+ "    (SELECT friendname FROM `friend` WHERE username = ?) UNION ALL (SELECT username FROM `friend` WHERE friendname = ?))"
						+ ")" + ")results ORDER BY date DESC;");
				ps.setString(1, username);
				ps.setString(2, username);
				ps.setString(3, groupname);
				ps.setString(4, username);
				ps.setString(5, username);
				ps.setString(6, groupname);
				ps.setString(7, username);
				ps.setString(8, groupname);
				ps.setString(9, username);
				ps.setString(10, username);
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int postId = rs.getInt("id");
				Date date = rs.getDate("date");
				Time time = rs.getTime("date");
				String content = rs.getString("content");
				String datetime = date + " " + time;
				User author = UserDao.getUserDetail(rs.getString("author"));
				String postimg = "";
				String imgType = "";
				int status = rs.getInt("status");

				List<Comment> commentList = getComment(postId);

				if (rs.getBytes("image") != null) {
					postimg = Converter.convertPostBase64(rs.getBytes("image"));
					imgType = rs.getString("imageType");
				} else {
					postimg = null;
					imgType = null;
				}
					if (status == 1) {
						String dt = Converter.ConvertDateTimer(); // Start date
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						sdf.parse(dt);
						if (rs.getDate("showDate").before(sdf.parse(dt))) {
							PreparedStatement ps1 = con.prepareStatement("UPDATE post SET status = ? WHERE id = ?");
							ps1.setInt(1, 0);
							ps1.setInt(2, rs.getInt("id"));
							ps1.executeUpdate();
						}
					}

					Post post = new Post(postId, content, datetime, author, postimg, imgType, status, commentList);
					postlist.add(post);


			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postlist;
	}

	public static List<Post> getMyPost(String username) throws SQLException {
		List<Post> postlist = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM post WHERE username = ? ORDER BY date DESC");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {
				do {
					int postId = rs.getInt("id");
					Date date = rs.getDate("date");
					Time time = rs.getTime("date");
					String content = rs.getString("content");
					String datetime = date + " " + time;
					User uname = UserDao.getUserDetail(rs.getString("username"));
					String postimg = "";
					String imgType = "";
					int status = rs.getInt("status");
					List<Comment> commentList = getComment(postId);

					if (rs.getBytes("image") != null) {
						postimg = Converter.convertPostBase64(rs.getBytes("image"));
						imgType = rs.getString("imageType");
					} else {
						postimg = null;
						imgType = null;
					}
					if (status == 1) {
						String dt = Converter.ConvertDateTimer(); // Start date
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						sdf.parse(dt);
						if (rs.getDate("showDate").before(sdf.parse(dt))) {
							PreparedStatement ps1 = con.prepareStatement("UPDATE post SET status = ? WHERE id = ?");
							ps1.setInt(1, 0);
							ps1.setInt(2, rs.getInt("id"));
							ps1.executeUpdate();
						}
					}

					Post post = new Post(postId, content, datetime, uname, postimg, imgType, status, commentList);
					postlist.add(post);
				} while (rs.next());
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postlist;
	}
	
	public static List<Post> getFriendPost(String username) throws SQLException {
		List<Post> postlist = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM post WHERE username = ? AND status != ? ORDER BY date DESC");
			ps.setString(1, username);
			ps.setInt(2, 0);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			} else {
				do {
					int postId = rs.getInt("id");
					Date date = rs.getDate("date");
					Time time = rs.getTime("date");
					String content = rs.getString("content");
					String datetime = date + " " + time;
					User uname = UserDao.getUserDetail(rs.getString("username"));
					String postimg = "";
					String imgType = "";
					int status = rs.getInt("status");
					List<Comment> commentList = getComment(postId);

					if (rs.getBytes("image") != null) {
						postimg = Converter.convertPostBase64(rs.getBytes("image"));
						imgType = rs.getString("imageType");
					} else {
						postimg = null;
						imgType = null;
					}
					if (status == 1) {
						String dt = Converter.ConvertDateTimer(); // Start date
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						sdf.parse(dt);
						if (rs.getDate("showDate").before(sdf.parse(dt))) {
							PreparedStatement ps1 = con.prepareStatement("UPDATE post SET status = ? WHERE id = ?");
							ps1.setInt(1, 0);
							ps1.setInt(2, rs.getInt("id"));
							ps1.executeUpdate();
						}
					}

					Post post = new Post(postId, content, datetime, uname, postimg, imgType, status, commentList);
					postlist.add(post);
				} while (rs.next());
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postlist;
	}
	

	public static int addComment(String username, String content, int postId) {

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO comment VALUES(null,?,?,?,?)");
			ps.setInt(1, postId);
			ps.setString(2, content);
			ps.setString(3, Converter.ConvertDateTimer());
			ps.setString(4, username);
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static List<Comment> getComment(int postId) {
		List<Comment> commentList = new ArrayList<>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM comment WHERE postId = ? ORDER BY date DESC");
			ps.setInt(1, postId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String content = rs.getString("comment");
				Date date = rs.getDate("date");
				Time time = rs.getTime("date");
				String datetime = date + " " + time;

				User author = UserDao.getUserDetail(rs.getString("commentUser"));
				Comment comment = new Comment(id, postId, content, datetime, author);
				commentList.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
	
	public static int editPost(int postId, int durations) {
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE post SET startDate = ?, showDate = ?, durations = ?, status = ? WHERE id = ?");
			String todayDate = Converter.ConvertDateTimer();
			String showDate = "";
			if (durations != -1) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.setTime(sdf.parse(todayDate));
					c.add(Calendar.DATE, durations);
					showDate = sdf.format(c.getTime()); 
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(durations >= 0) {
					ps.setString(1, todayDate);
					ps.setString(2, showDate);
					ps.setInt(3, durations);
					ps.setInt(4, 1);
				}else {

					ps.setString(1, todayDate);
					ps.setString(2, showDate);
					ps.setInt(3, durations +1);
					ps.setInt(4, 1);
				}
			}else {
				ps.setString(1, null);
				ps.setString(2, null);
				ps.setString(3, null);
				ps.setInt(4, -1);
			}
			ps.setInt(5, postId);
			return ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
