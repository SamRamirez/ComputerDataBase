package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Bean.Computer;
import connection.Connect;

public class ComputerDAO {
	
	private final static ComputerDAO instance = new ComputerDAO();
	
	public static ComputerDAO getInstance() {
		return instance;
	}
	
	private String query;
	//private ResultSet result;
	
	
	
	
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	private ComputerDAO() {
		query="";	
	}
	
	
	public ArrayList<Computer> listComputer() {
		ArrayList<Computer> listComp = new ArrayList<Computer>(); 		
		Connection conn = (Connection) Connect.getInstance().getConnection();
		
		query="select id, name, introduced, discontinued, company_id from computer;";
		
		
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
		
		ResultSet results = stmt.executeQuery(query);
		while(results.next()) {
			int id=results.getInt("id");
			String name=results.getString("name");
			LocalDate introduced;
			if(results.getDate("introduced")!=null) {
				introduced=results.getDate("introduced").toLocalDate();
			}else{
				introduced=null;
			}
			LocalDate discontinued;
			if(results.getDate("discontinued")!=null) {
				discontinued = results.getDate("discontinued").toLocalDate();
			}else {
				discontinued = null;
			}
			int company_id=results.getInt("company_id");
			Computer c = new Computer(id, name, introduced, discontinued, company_id);
			listComp.add(c);
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
		return listComp;	
	}
	
	
	public void createComputer(Computer comp) {
				
		Connection conn = (Connection) Connect.getInstance().getConnection();
		
		
		query="insert into computer (name, introduced, discontinued, company_id) values ('"+comp.getName()+"', '"+comp.getIntroduced()+"', '"+comp.getDiscontinued()+"', "+comp.getCompany_id()+");";
		ResultSet keyResults;
		
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			keyResults = stmt.getGeneratedKeys();
	
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();	
	}	
	

}
