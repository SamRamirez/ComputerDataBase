package main.java.com.excilys.sramirez.formation.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer.ComputerBuilder;
import main.java.com.excilys.sramirez.formation.computerdatabase.connection.Connect;

public class ComputerDAO {
	
	private static final Logger logger = LogManager.getLogger(ComputerDAO.class);

	private ComputerDAO() {
	}

	private final static ComputerDAO instance = new ComputerDAO();
	
	CompanyDAO companyDAO = CompanyDAO.getInstance();

	public static ComputerDAO getInstance() {
		return instance;
	}
		
	//alternative1 commentée
	//String queryCountComputers =  "SELECT COUNT(*) as nbComputers from computer";
	String queryCountComputers =  "SELECT COUNT(*) from computer";
	//String queryListComputers = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ?, ?";
	String queryListComputers = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON company_id=company.id LIMIT ?, ?";
	String queryCreateComputer = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
	String queryInfoComputer = "SELECT name, introduced, discontinued, company_id from computer where id=?";
	String queryUpdateComputer = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";
	String queryDeleteComputer = "DELETE from computer WHERE id = ?";

	public int countComputers() {
		int toReturn=0;
		try (Connection conn = Connect.getInstance().getConnection();){
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
	
	public ArrayList<Computer> listComputer(int page, int numberOfElements) {
		ArrayList<Computer> listComp = new ArrayList<Computer>();
		
		try (Connection conn = Connect.getInstance().getConnection();) {
	        PreparedStatement pstmt = conn.prepareStatement(queryListComputers);
	        pstmt.setInt(1, 10*(page-1));
	        pstmt.setInt(2, numberOfElements);
			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Computer c;
				ComputerBuilder computerBuilder = new Computer.ComputerBuilder();
				int id = results.getInt("computer.id");
				String name = results.getString("computer.name");
				
				computerBuilder.withId(id).withName(name);
				
				LocalDate introduced;
				if (results.getDate("introduced") != null) {
					introduced = results.getDate("introduced").toLocalDate();
					computerBuilder.withDateIntro(introduced);
				} else {
					introduced = null;
				}
				LocalDate discontinued;
				if (results.getDate("discontinued") != null) {
					discontinued = results.getDate("discontinued").toLocalDate();
					computerBuilder.withDateDisc(discontinued);
				} else {
					discontinued = null;
				}
				int companyId = results.getInt("company.id");
				String companyName = results.getString("company.name");
				Company company = new Company(companyId, companyName);
				
				//on utilise ca ou bien le computer builder
				//Computer c = new Computer(id, name, introduced, discontinued, company);
				
				c = computerBuilder.withCompany(company).build();
				listComp.add(c);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return listComp;
	}
	//j'ai du effacer un bout sans faire expres...
	//if (results.getDate("discontinued") != null) {

	public void createComputer(Computer comp) {

		try (Connection conn = Connect.getInstance().getConnection();){
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

	
	public Optional<Computer> infoComp(int id) {
		
		Computer comp = new Computer();
		int company_id=0;
		ComputerBuilder cpBuild = new ComputerBuilder();

		try (Connection conn = Connect.getInstance().getConnection();){
			PreparedStatement pstmt;

			pstmt =  conn.prepareStatement(queryInfoComputer);
			if(id!=0) {
				pstmt.setInt(1, id);
			}else {
				System.out.println("que fait-on?");
			}
			ResultSet results = pstmt.executeQuery();
			if (results.next()) {

				String name = results.getString("name");
				cpBuild.withName(name);
				LocalDate introduced;
				if (results.getDate("introduced") != null) {
					introduced = results.getDate("introduced").toLocalDate();
					cpBuild.withDateIntro(introduced);
				} else {
					introduced = null;
				}
				LocalDate discontinued;
				if (results.getDate("discontinued") != null) {
					discontinued = results.getDate("discontinued").toLocalDate();
					cpBuild.withDateDisc(discontinued);
				} else {
					discontinued = null;
				}
				company_id = results.getInt("company_id");

				//PROBLEME CONSTRUCTEUR
				
				comp = cpBuild.build();
			} else {
				comp = new Computer();
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		String companyName = companyDAO.getCompanyName(company_id);
		comp.setCompany(new Company(company_id, companyName));
		return Optional.ofNullable(comp);
	}


	
	//Ne serais-ce pas bien de signaler à l'utilisateur que l'ordi à modifier n'existe pas dans le cas ou l'id ne correspond à aucun computer?
	public Computer updateComp(Computer comp) {
		
		try(Connection conn = Connect.getInstance().getConnection();) {
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
			System.err.println("id = "+comp.getId()+" name = "+comp.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return comp;
	}
	
	
	
	//ca serait pas mal de dire à l'utilisateur si rien n'a ete effacé
	public void deleteComp(int id) {
		
		try (Connection conn = Connect.getInstance().getConnection();){
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(queryDeleteComputer);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	

}
