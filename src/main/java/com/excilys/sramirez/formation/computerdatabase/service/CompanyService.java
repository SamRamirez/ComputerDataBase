package main.java.com.excilys.sramirez.formation.computerdatabase.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.CompanyDAO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;

@Service
public class CompanyService {
	
//private final static CompanyService instance = new CompanyService();
//	
//	public static CompanyService getInstance() {
//		return instance;
//	}
//		
//	private CompanyService() {
//	}


	@Autowired
	CompanyDAO companyDAO;
	//= CompanyDAO.getInstance();
	
//	public ArrayList<Company> list(int page, int numberOfElements) {
//		return companyDAO.list(page, numberOfElements);
//	}
	
	public ArrayList<Company> list() {
		return companyDAO.list();
	}

	public String getName(int companyId) {
		String toReturn = companyDAO.getName(companyId);
		return toReturn;
	}

}
