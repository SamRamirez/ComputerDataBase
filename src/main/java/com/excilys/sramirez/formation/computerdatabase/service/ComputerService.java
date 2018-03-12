package main.java.com.excilys.sramirez.formation.computerdatabase.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.ComputerDAO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer.ComputerBuilder;
import main.java.com.excilys.sramirez.formation.computerdatabase.connection.Connect;

public class ComputerService {
	
	private final static ComputerService instance = new ComputerService();
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	
	private ComputerService() {
	}

	ComputerDAO compDAO = ComputerDAO.getInstance();
	
	public int countComputers() {
		return compDAO.countComputers();
	}
	
	public ArrayList<Computer> listComputer(int page, int numberOfElements) {
		return compDAO.listComputer(page, numberOfElements);
	}
	
	public ArrayList<Computer> listComputerFiltered(int page, int numberOfElements, String filter){
		return compDAO.listComputerFiltered(page, numberOfElements, filter);
	}
	
	public Computer createComputer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer comp;
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder();
		
		if (name != "") {
			computerBuilder.withName(name);
		}
		if (introduced != null) {
			computerBuilder.withDateIntro(introduced);
		} 
		if (discontinued != null) {
			computerBuilder.withDateDisc(discontinued);
		} 
		
		if (company != null) {
			computerBuilder.withCompany(company);
		}
		comp = computerBuilder.build();
		
		compDAO.createComputer(comp);			
		return comp;
	}
	
	public Computer infoComp(int id) {
		Computer toReturn = compDAO.infoComp(id).orElse(new Computer());
		System.out.println(toReturn.toString());
		return toReturn;		
	}
	
	public Computer returnComp(int id) {
		Computer toReturn = compDAO.infoComp(id).orElse(new Computer());
		return toReturn;		
	}
	
	public Computer updateComp(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		Computer comp;		
		comp = new Computer(id, name, introduced, discontinued, company); 
		compDAO.updateComp(comp);
		return comp;		
	}
	
	public void deleteComp(int id) {
		//passer en parametre de delete un computer plutot qu'un id?  ca sert? ca sert à retourner l'ordi effacé sans refaire de requete?	
		compDAO.deleteComp(id);
	}
	
	//c1 est le computer de référence et c2 est l'update à apporter
	public Computer complete(Computer c1, Computer c2) {
		Computer c3 = new Computer();
		ComputerBuilder compBuilder = new ComputerBuilder();
		if(c2.getName()  != null && c2.getName()  != "" && c2.getName() != "default") {
			compBuilder.withName(c2.getName());
		}else if (c1.getName() != null && c1.getName() != "" && c1.getName() != "default") {
			compBuilder.withName(c1.getName());
		}
		
		if(c2.getIntroduced()  != null) {
			compBuilder.withDateIntro(c2.getIntroduced());
		}else if (c1.getIntroduced() != null) {
			compBuilder.withDateIntro(c1.getIntroduced());
		}
		
		if(c2.getDiscontinued()  != null) {
			compBuilder.withDateDisc(c2.getDiscontinued());
		}else if (c1.getDiscontinued() != null) {
			compBuilder.withDateDisc(c1.getDiscontinued());
		}
		
		if(c2.getCompany()  != null) {
			compBuilder.withCompany(c2.getCompany());
		}else if (c1.getCompany() != null) {
			compBuilder.withCompany(c1.getCompany());
		}
		c3=compBuilder.build();
		
//		if(c2.  != null) {
//			compBuilder.with (c2.);
//		}else if (c1. != null) {
//			compBuilder.with (c1.);
//		}
		
		
		return c3;
	}

}
