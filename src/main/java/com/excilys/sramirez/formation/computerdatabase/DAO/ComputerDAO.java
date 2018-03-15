package main.java.com.excilys.sramirez.formation.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.java.com.excilys.sramirez.formation.computerdatabase.Mapper.ComputerMapper;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer.ComputerBuilder;
import main.java.com.excilys.sramirez.formation.computerdatabase.connection.ConnectPoolVersion;


@Repository
public class ComputerDAO {
	
	private static final Logger logger = LogManager.getLogger(ComputerDAO.class);

//	private ComputerDAO() {
//	}
//
//	private final static ComputerDAO instance = new ComputerDAO();
//	
//	public static ComputerDAO getInstance() {
//		return instance;
//	}
	
	@Autowired
	CompanyDAO companyDAO;
	//= CompanyDAO.getInstance();
	
	@Autowired
	ComputerMapper computerMapper;
	//= ComputerMapper.getInstance();
	
	@Autowired
	ConnectPoolVersion poolConnect;
	//= ConnectPoolVersion.getInstance();
		
	//alternative1 commentée
	//String queryCountComputers =  "SELECT COUNT(*) as nbComputers from computer";
	String queryCountComputers =  "SELECT COUNT(*) from computer";
	//String queryListComputers = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ?, ?";
	String queryListComputers = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id LIMIT ?, ?";
	String queryCreateComputer = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
	String queryInfoComputer = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name from computer LEFT JOIN company ON computer.company_id=company.id where computer.id=?";
	String queryUpdateComputer = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	String queryDeleteComputer = "DELETE from computer WHERE id = ?";
	String queryListComputersFiltered = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id WHERE computer.name LIKE ? LIMIT ?, ? ";
	String queryListComputersOrdered = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id ORDER BY %s ASC LIMIT ?, ? ";
	
	public int count() {
		int toReturn=0;
		//try (Connection conn = Connect.getInstance().getConnection();){
		try(Connection conn = poolConnect.openConnection();){
	        PreparedStatement pstmt = conn.prepareStatement(queryCountComputers);
	        ResultSet result = pstmt.executeQuery();
	        while (result.next()) {
	        	//alternative1 commentée
	        	//toReturn=result.getInt("nbComputers");
	        	toReturn =  result.getInt("count(*)");
	        }
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return toReturn;
	}
	
	
	public ArrayList<Computer> listFiltered(int page, int numberOfElements, String filter){
		ArrayList<Computer> listComp = new ArrayList<Computer>();
		
		//try (Connection conn = Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
	        PreparedStatement pstmt = conn.prepareStatement(queryListComputersFiltered);
	        pstmt.setInt(2, 10*(page-1));
	        pstmt.setInt(3, numberOfElements);
	        pstmt.setString(1, "%"+filter+"%");
			ResultSet results = pstmt.executeQuery();
//			System.out.println(pstmt);
			while (results.next()) {
				listComp.add(computerMapper.fromResultSetToModel(results));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return listComp;
	}
	
	public ArrayList<Computer> list(int page, int numberOfElements) {
		ArrayList<Computer> listComp = new ArrayList<Computer>();
		
		//try (Connection conn = Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
	        PreparedStatement pstmt = conn.prepareStatement(queryListComputers);
	        pstmt.setInt(1, 10*(page-1));
	        pstmt.setInt(2, numberOfElements);
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				listComp.add(computerMapper.fromResultSetToModel(results));
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return listComp;
	}
	//j'ai du effacer un bout sans faire expres...
	//if (results.getDate("discontinued") != null) {

	public void create(Computer comp) {

		//try (Connection conn = Connect.getInstance().getConnection();){
		try(Connection conn = poolConnect.openConnection();){
	        PreparedStatement pstmt = conn.prepareStatement(queryCreateComputer);
	        
	        pstmt.setString(1, comp.getName());
	        //pour faire le traitement avec les if, on peut plutot le faire par une classe mapper : on passe à la méthode de mapper les arguments, et on construit le computer avec
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
	        //if(comp.getCompany().getId()!=0) {
	        if(comp.getCompany() != null) {
	        	pstmt.setInt(4, comp.getCompany().getId());
	        }else {
	        	pstmt.setNull(4, Types.INTEGER);
	        }
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	
	public Optional<Computer> info(int id) {
		
		Computer comp = new Computer();
		int company_id=0;
		ComputerBuilder cpBuild = new ComputerBuilder();

		//try (Connection conn = Connect.getInstance().getConnection();){
		try(Connection conn = poolConnect.openConnection();){
			PreparedStatement pstmt;

			pstmt =  conn.prepareStatement(queryInfoComputer);
			if(id!=0) {
				pstmt.setInt(1, id);
//				cpBuild.withId(id);
			}
			ResultSet results = pstmt.executeQuery();
			if (results.next()) {

//				String name = results.getString("name");
//				cpBuild.withName(name);
//				LocalDate introduced;
//				if (results.getDate("introduced") != null) {
//					introduced = results.getDate("introduced").toLocalDate();
//					cpBuild.withDateIntro(introduced);
//				} else {
//					introduced = null;
//				}
//				LocalDate discontinued;
//				if (results.getDate("discontinued") != null) {
//					discontinued = results.getDate("discontinued").toLocalDate();
//					cpBuild.withDateDisc(discontinued);
//				} else {
//					discontinued = null;
//				}
//				company_id = results.getInt("company_id");
//
//				//PROBLEME CONSTRUCTEUR
//				
//				comp = cpBuild.build();
				
				comp = computerMapper.fromResultSetToModel(results);
			} else {
				comp = new Computer();
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		//String companyName = companyDAO.getName(company_id);
		//comp.setCompany(new Company(company_id, companyName));
		return Optional.ofNullable(comp);
	}


	
	//Ne serais-ce pas bien de signaler à l'utilisateur que l'ordi à modifier n'existe pas dans le cas ou l'id ne correspond à aucun computer?
	public Computer update(Computer comp) {
		
		//try(Connection conn = Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
			PreparedStatement pstmt;
			pstmt = (PreparedStatement) conn.prepareStatement(queryUpdateComputer);
			pstmt.setInt(5, comp.getId());
			if (comp.getName() != "" && comp.getName() != null) {
				pstmt.setString(1, comp.getName());
			} else {
				pstmt.setString(1, null);
			}
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
			if (comp.getCompany().getId()!=0) {
				pstmt.setInt(4, comp.getCompany().getId());
			}else {
				pstmt.setNull(4, Types.INTEGER);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return comp;
	}
	
	
	
	//ca serait pas mal de dire à l'utilisateur si rien n'a ete effacé
	public void delete(int id) {
		
		//try (Connection conn = Connect.getInstance().getConnection();){
		try(Connection conn = poolConnect.openConnection();){
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(queryDeleteComputer);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	//order by computer.name, introduced, discontinued, company.id
	public ArrayList<Computer> listOrdered(int page, int numberOfElements, String orderBy){
ArrayList<Computer> listComp = new ArrayList<Computer>();
		
		//try (Connection conn = Connect.getInstance().getConnection();) {
		try(Connection conn = poolConnect.openConnection();){
	        PreparedStatement pstmt = conn.prepareStatement(String.format(queryListComputersOrdered, orderBy));
	        pstmt.setInt(1, 10*(page-1));
	        pstmt.setInt(2, numberOfElements);
	        //pstmt.setString(1, orderBy);
			ResultSet results = pstmt.executeQuery();
//			System.out.println(pstmt);
			while (results.next()) {
				listComp.add(computerMapper.fromResultSetToModel(results));
				//System.out.println(computerMapper.fromResultSetToModel(results).getName());
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return listComp;
	}
}
