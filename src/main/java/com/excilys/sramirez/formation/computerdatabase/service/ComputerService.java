package main.java.com.excilys.sramirez.formation.computerdatabase.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.ComputerDAO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
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
	
	public Computer createComputer(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		Computer comp;
		if(company_id==0 && introduced==null && discontinued == null) {
			comp = new Computer(name);
		}
		if(discontinued!=null) {
			comp = new Computer(name, introduced, discontinued, company_id); 
		}else {
			comp = new Computer(name, introduced, company_id); 
		}
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
	
	public Computer updateComp(int id, String name, LocalDate introduced, LocalDate discontinued, int company_id) {
		Computer comp;		
		comp = new Computer(id, name, introduced, discontinued, company_id); 
		compDAO.updateComp(comp);
		return comp;		
	}
	
	public void deleteComp(int id) {
		//passer en parametre de delete un computer plutot qu'un id?  ca sert? ca sert à retourner l'ordi effacé sans refaire de requete?	
		compDAO.deleteComp(id);
	}

}
