package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {

	public static Connection getConnection() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fyp-project", "root", "1234");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
