package com.hibernate_demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/student_tracker?allowPublicKeyRetrieval=true&useSSL=false";
		String user = "user1";
		String pass = "pass1";
		
		try {
			System.out.println("connecting to db ...");
			Connection myConn = 
					DriverManager.getConnection(jdbcUrl, user, pass);
			System.out.println("connected to db !");
			
			myConn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
