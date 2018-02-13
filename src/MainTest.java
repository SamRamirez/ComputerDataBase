import java.sql.Date;
import java.util.ArrayList;

import Bean.Computer;
import DAO.ComputerDAO;

public class MainTest {
	
	public static void main(String args[]) {
	
		ComputerDAO compDAO= ComputerDAO.getInstance();
		
		
		
		
		
		
		ArrayList<Computer> listComp = compDAO.listComputer();
		for(int i =0; i<listComp.size(); i++) {
			System.out.print(listComp.get(i).getId());
			System.out.print(" "+listComp.get(i).getName());
			System.out.print(" "+listComp.get(i).getIntroduced());
			
			System.out.println();
		}
		
		compDAO.createComputer("A", new Date(2000, 1, 1), new Date(2001, 1, 1), 3);
	}

}
