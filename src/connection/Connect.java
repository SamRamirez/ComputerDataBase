package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import Bean.Computer;
import DAO.ComputerDAO;

public class Connect {
	
//	static String query = "select * from computer;";
//	static ResultSet results;
	
	static Connection conn;
	static String url;
	
	
//	public static void main(String args[]) {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
//			Connection conn = DriverManager.getConnection(url,"admincdb","qwerty1234");
//			Statement stmt = (Statement) conn.createStatement();
//			results = stmt.executeQuery(query);
//			while(results.next()) {
//				System.out.println(results.getString("name"));
//			}
//			conn.close();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
//	public static Connection getConn() {
//		return conn;
//	}

	public static void setConn(Connection conn) {
		Connect.conn = conn;
	}

	private final static Connect instance = new Connect();
	
	private Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static Connect getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url,"admincdb","qwerty1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}
	
	public static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
