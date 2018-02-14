package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Bean.Company;
import Bean.Computer;
import connection.Connect;

public class CompanyDAO {
	
	private final static CompanyDAO instance = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return instance;
	}

	public ArrayList<Company> listCompany() {
		ArrayList<Company> listComp = new ArrayList<Company>();
		Connection conn = (Connection) Connect.getInstance().getConnection();

		String query = "select id, name from company;";

		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();

			ResultSet results = stmt.executeQuery(query);
			while (results.next()) {
				int id = results.getInt("id");
				String name = results.getString("name");
				
				Company c = new Company(id, name);
				listComp.add(c);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
		return listComp;
	}

}
