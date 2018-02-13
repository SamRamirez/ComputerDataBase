package Service;

import java.time.LocalDate;
import java.util.ArrayList;

import Bean.Computer;
import DAO.ComputerDAO;

public class ComputerService {
	
	private final static ComputerService instance = new ComputerService();
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	
	ComputerDAO compDAO= ComputerDAO.getInstance();
	
	public ArrayList<Computer> listComputer() {
		return compDAO.listComputer();
	}
	
	public Computer createComputer(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
				Computer comp = new Computer(0, name, introduced, discontinued, company_id); 
				compDAO.createComputer(comp);			
		return comp;
	}

}
