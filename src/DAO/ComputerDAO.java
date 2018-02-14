package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Bean.Computer;
import connection.Connect;

public class ComputerDAO {

	private final static ComputerDAO instance = new ComputerDAO();

	public static ComputerDAO getInstance() {
		return instance;
	}

	//ce parametre ne semble servir à rien
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	private ComputerDAO() {
		query = "";
	}

	public ArrayList<Computer> listComputer() {
		ArrayList<Computer> listComp = new ArrayList<Computer>();
		Connection conn = (Connection) Connect.getInstance().getConnection();

		query = "select id, name, introduced, discontinued, company_id from computer;";

		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();

			ResultSet results = stmt.executeQuery(query);
			while (results.next()) {
				int id = results.getInt("id");
				String name = results.getString("name");
				LocalDate introduced;
				if (results.getDate("introduced") != null) {
					introduced = results.getDate("introduced").toLocalDate();
				} else {
					introduced = null;
				}
				LocalDate discontinued;
				if (results.getDate("discontinued") != null) {
					discontinued = results.getDate("discontinued").toLocalDate();
				} else {
					discontinued = null;
				}
				int company_id = results.getInt("company_id");
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

		query = "insert into computer (name, introduced, discontinued, company_id) values ('" + comp.getName() + "', '"
				+ comp.getIntroduced() + "', '" + comp.getDiscontinued() + "', " + comp.getCompany_id() + ");";
		// pas utile?
		// ResultSet keyResults;

		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			
			// pas utile?
			// keyResults = stmt.getGeneratedKeys();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
	}

	public Optional<Computer> infoComp(int id) {
		query = "select name, introduced, discontinued, company_id from computer where id='" + id + "';";

		Connection conn = (Connection) Connect.getInstance().getConnection();

		Statement stmt;

		Computer comp = new Computer();
		try {
			stmt = (Statement) conn.createStatement();
			ResultSet results = stmt.executeQuery(query);
			

			if (results.next()) {

				String name = results.getString("name");
				LocalDate introduced;
				if (results.getDate("introduced") != null) {
					introduced = results.getDate("introduced").toLocalDate();
				} else {
					introduced = null;
				}
				LocalDate discontinued;
				if (results.getDate("discontinued") != null) {
					discontinued = results.getDate("discontinued").toLocalDate();
				} else {
					discontinued = null;
				}
				int company_id = results.getInt("company_id");

				comp = new Computer(id, name, introduced, discontinued, company_id);
			} else {
				comp = new Computer();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();

		return Optional.ofNullable(comp);
	}
	
	
	
	
	//Ne serais-ce pas bien de signaler à l'utilisateur que l'ordi à modifier n'existe pas dans le cas ou l'id ne correspond à aucun computer?
	public Computer updateComp(Computer comp) {
		Computer toReturn=comp;
		
		query = "UPDATE computer SET name = '"+comp.getName()+"' ";
		
		LocalDate introduced;
		if (comp.getIntroduced() != null) {
			introduced = comp.getIntroduced();
			query=query+", introduced = '"+introduced+"' "; 
		} else {
			introduced = null;
		}
		LocalDate discontinued;
		if (comp.getDiscontinued() != null) {
			discontinued = comp.getDiscontinued();
			query=query+", discontinued = '"+discontinued+"' "; 
		} else {
			discontinued = null;
		}
		
		query=query+"WHERE id = '"+comp.getId()+"' ;";
		
		Connection conn = (Connection) Connect.getInstance().getConnection();
		
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
		return toReturn;
	}
	
	
	
	//ca serait pas mal de dire à l'utilisateur si rien n'a ete effacé
	public void deleteComp(int id) {
		
		query = "DELETE from computer WHERE id = '"+id+"' ;";
		
		Connection conn = (Connection) Connect.getInstance().getConnection();
		
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
	}
	

}
