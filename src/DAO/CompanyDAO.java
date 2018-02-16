package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import Bean.Company;
import connection.Connect;

public class CompanyDAO {
	
	private final static CompanyDAO instance = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return instance;
	}
	
	String queryListCompany = "select id, name from company";


	public ArrayList<Company> listCompany() {
		ArrayList<Company> listComp = new ArrayList<Company>();
		Connection conn = (Connection) Connect.getInstance().getConnection();

		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(queryListCompany);

			ResultSet results = pstmt.executeQuery();
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
