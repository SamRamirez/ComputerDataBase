package main.java.com.excilys.sramirez.formation.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.PreparedStatement;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.connection.Connect;

public class CompanyDAO {
	
	private static final Logger logger = Logger.getLogger(CompanyDAO.class);

	
	private final static CompanyDAO instance = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return instance;
	}
	
	private CompanyDAO() {
	}
	
	String queryListCompany = "select id, name from company LIMIT ?, ?";


	public ArrayList<Company> listCompany(int page, int numberOfElements) {
		ArrayList<Company> listComp = new ArrayList<Company>();		
		try (Connection conn =  Connect.getInstance().getConnection();) {
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(queryListCompany);
			pstmt.setInt(1, 10*(page-1));
			pstmt.setInt(2, numberOfElements);

			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				int id = results.getInt("id");
				String name = results.getString("name");
				
				Company c = new Company(id, name);
				listComp.add(c);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		//Connect.close();
		
		return listComp;
	}

}
