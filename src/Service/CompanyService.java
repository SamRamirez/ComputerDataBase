package Service;

import java.util.ArrayList;

import Bean.Company;
import Bean.Computer;
import DAO.CompanyDAO;
import DAO.ComputerDAO;

public class CompanyService {
	
private final static CompanyService instance = new CompanyService();
	
	public static CompanyService getInstance() {
		return instance;
	}
	
	CompanyDAO companyDAO= CompanyDAO.getInstance();
	
	public ArrayList<Company> listCompany() {
		return companyDAO.listCompany();
	}

}
