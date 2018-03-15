package main.java.com.excilys.sramirez.formation.computerdatabase.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.ComputerDAO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer.ComputerBuilder;

@Service
public class ComputerService {
	
//	private final static ComputerService instance = new ComputerService();
//	
//	public static ComputerService getInstance() {
//		return instance;
//	}
	
	
//	private ComputerService() {
//	}

	@Autowired
	private ComputerDAO compDAO;
	//= ComputerDAO.getInstance();
	
	public int count() {
		return compDAO.count();
	}
	
	public ArrayList<Computer> list(int page, int numberOfElements) {
		return compDAO.list(page, numberOfElements);
	}
	
	public ArrayList<Computer> listFiltered(int page, int numberOfElements, String filter){
		return compDAO.listFiltered(page, numberOfElements, filter);
	}
	
	public Computer create(String name, LocalDate introduced, LocalDate discontinued, Company company) {
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
		
		compDAO.create(comp);			
		return comp;
	}
	
	public Computer info(int id) {
		Computer toReturn = compDAO.info(id).orElse(new Computer());
		//je vais pas enlever le sysout puisque la methode sert à afficher les détails du computer 
		System.out.println(toReturn.toString());
		return toReturn;		
	}
	
	public Computer returnComp(int id) {
		Computer toReturn = compDAO.info(id).orElse(new Computer());
		return toReturn;		
	}
	
	public Computer update(Computer comp) {
		compDAO.update(comp);
		return comp;		
	}
	
	public void delete(int id) {
		//passer en parametre de delete un computer plutot qu'un id?  ca sert? ca sert à retourner l'ordi effacé sans refaire de requete?	
		compDAO.delete(id);
	}
	
	public ArrayList<Computer> listOrdered(int page, int numberOfElements, String orderBy){
		return compDAO.listOrdered(page, numberOfElements, orderBy);
		
	}


}
