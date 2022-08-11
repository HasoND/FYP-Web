package com.helper;

public class Validate{

	public static boolean checkUsername(String uname){
		return uname != null && uname.matches("[a-zA-Z]{3,20}");
	}
	
	public static boolean checkPassword(String password1) {
		return password1 != null && password1.matches("[a-zA-Z0-9]{6,16}");
	}
	
	public static boolean comfirmPassword(String password1, String password2) {
		return password1 != null && password2 != null && password2.equals(password1);
	}
	
	public static boolean checkEmail(String email) {
		return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}
}
