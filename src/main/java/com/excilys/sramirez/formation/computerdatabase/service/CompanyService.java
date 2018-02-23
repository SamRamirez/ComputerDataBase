package main.java.com.excilys.sramirez.formation.computerdatabase.service;

import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.CompanyDAO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;

public class CompanyService {
	
private final static CompanyService instance = new CompanyService();
	
	public static CompanyService getInstance() {
		return instance;
	}
		
	private CompanyService() {
	}



	CompanyDAO companyDAO= CompanyDAO.getInstance();
	
	public ArrayList<Company> listCompany(int page, int numberOfElements) {
		return companyDAO.listCompany(page, numberOfElements);
	}

}
