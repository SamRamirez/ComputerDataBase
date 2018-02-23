package main.java.com.excilys.sramirez.formation.computerdatabase.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Connect {


	static Connection conn;
	private String url;
	private String login;
	private String password;



	public static void setConn(Connection conn) {
		Connect.conn = conn;
	}

	private final static Connect instance = new Connect();

	private Connect() {
		ResourceBundle bundle = ResourceBundle.getBundle("main.resources.connect");
        login = bundle.getString("login");
        password = bundle.getString("password");
        url = bundle.getString("url");
		try {
			Class.forName("com.mysql.jdbc.Driver");

			//url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
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
			conn = DriverManager.getConnection(url,login,password);
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
