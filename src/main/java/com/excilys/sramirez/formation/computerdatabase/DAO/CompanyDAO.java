package main.java.com.excilys.sramirez.formation.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.connection.ConnectPoolVersion;

@Repository
public class CompanyDAO {
	
	private static final Logger logger = LogManager.getLogger(CompanyDAO.class);

	
//	private final static CompanyDAO instance = new CompanyDAO();
//
//	public static CompanyDAO getInstance() {
//		return instance;
//	}
//	
//	private CompanyDAO() {
//	}
	
	@Autowired
	ConnectPoolVersion poolConnect;
	//= ConnectPoolVersion.getInstance();
	
	String queryListCompanyFull = "select id, name from company";
	String queryListCompany = "select id, name from company LIMIT ?, ?";
	String queryNameCompany = "SELECT name from company WHERE id = ?";


	public ArrayList<Company> list(int page, int numberOfElements) {
		ArrayList<Company> listComp = new ArrayList<Company>();		
		//try (Connection conn =  Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
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
	
	public ArrayList<Company> list() {
		ArrayList<Company> listComp = new ArrayList<Company>();		
		//try (Connection conn =  Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(queryListCompanyFull);

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

	public String getName(int companyId) {
		String name = "";
		//try (Connection conn =  Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(queryNameCompany);
			pstmt.setInt(1, companyId);
			ResultSet results = pstmt.executeQuery();
			if(results.next()) {
				name = results.getString("name");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return name;
	}

}
