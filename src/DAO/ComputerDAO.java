package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.mysql.jdbc.Connection;

import Bean.Computer;
import connection.Connect;

public class ComputerDAO {

	private final static ComputerDAO instance = new ComputerDAO();

	public static ComputerDAO getInstance() {
		return instance;
	}
	
	
	String queryListComputers = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	String queryCreateComputer = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
	String queryInfoComputer = "SELECT name, introduced, discontinued, company_id from computer where id=?";
	String queryUpdateComputer = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	String queryDeleteComputer = "DELETE from computer WHERE id = ?";


	private ComputerDAO() {
	}

	public ArrayList<Computer> listComputer() {
		ArrayList<Computer> listComp = new ArrayList<Computer>();
		Connection conn = (Connection) Connect.getInstance().getConnection();

		try {
	        PreparedStatement pstmt = conn.prepareStatement(queryListComputers);

			ResultSet results = pstmt.executeQuery();
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

		try {
	        PreparedStatement pstmt = conn.prepareStatement(queryCreateComputer);
	        
	        pstmt.setString(1, comp.getName());
	        if(comp.getIntroduced()!=null) {
	        	pstmt.setDate(2, Date.valueOf(comp.getIntroduced()));
	        }else {
	        	pstmt.setDate(2, null);
	        }
	        if(comp.getDiscontinued()!=null) {
	        	pstmt.setDate(3, Date.valueOf(comp.getDiscontinued()));
	        }else {
	        	pstmt.setDate(3, null);
	        }
	        if(comp.getCompany_id()!=0) {
	        	pstmt.setInt(4, comp.getCompany_id());
	        }else {
	        	pstmt.setNull(4, Types.INTEGER);
	        }
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
	}

	public Optional<Computer> infoComp(int id) {

		Connection conn = (Connection) Connect.getInstance().getConnection();

		PreparedStatement pstmt;

		Computer comp = new Computer();
		try {
			pstmt =  conn.prepareStatement(queryInfoComputer);
			if(id!=0) {
				pstmt.setInt(1, id);
			}else {
				System.out.println("que fait-on?");
			}
			ResultSet results = pstmt.executeQuery();
			

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
		
		Connection conn = (Connection) Connect.getInstance().getConnection();
		
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(queryUpdateComputer);
			pstmt.setInt(5, comp.getId());
			pstmt.setString(1, comp.getName());
			if (comp.getIntroduced() != null) {
	        	pstmt.setDate(2, Date.valueOf(comp.getIntroduced()));
			} else {
	        	pstmt.setDate(2, null);
			}
			if (comp.getDiscontinued() != null) {
				pstmt.setDate(3, Date.valueOf(comp.getDiscontinued()));
			} else {
	        	pstmt.setDate(3, null);
			}
			if (comp.getCompany_id()!=0) {
				pstmt.setInt(4, comp.getCompany_id());
			}else {
				pstmt.setNull(4, Types.INTEGER);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
		return comp;
	}
	
	
	
	//ca serait pas mal de dire à l'utilisateur si rien n'a ete effacé
	public void deleteComp(int id) {
		
		Connection conn = (Connection) Connect.getInstance().getConnection();
		
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(queryDeleteComputer);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect.close();
	}
	

}
