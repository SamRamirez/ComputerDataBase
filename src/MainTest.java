import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import Bean.Computer;
import Service.ComputerService;

public class MainTest {
	
	public static void main(String args[]) {
	
		ComputerService compService= ComputerService.getInstance();
	
		
		ArrayList<Computer> listComp = compService.listComputer();
		for(int i =0; i<listComp.size(); i++) {
			System.out.print(listComp.get(i).getId());
			System.out.print(" "+listComp.get(i).getName());
			System.out.print(" "+listComp.get(i).getIntroduced());
			
			System.out.println();
		}
		
		LocalDate aa= LocalDate.of(2000, 5, 26);
		System.out.println(Timestamp.valueOf(aa.atStartOfDay()));
		System.out.println(aa);
		
//		compDAO.createComputer("Abc", new Date(2000, 1, 1), new Date(2001, 1, 1), 3);
		compService.createComputer("Abc", LocalDate.of(2000, 5, 26), LocalDate.of(2000, 5, 28), 3);
		//compDAO.createComputer("Abcd", null, null, 3);
		
		listComp = compService.listComputer();
		for(int i =0; i<listComp.size(); i++) {
			System.out.print(listComp.get(i).getId());
			System.out.print(" "+listComp.get(i).getName());
			System.out.print(" "+listComp.get(i).getIntroduced());
			
			System.out.println();
		}

	}

}
