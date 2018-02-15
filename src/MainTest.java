import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import Bean.Company;
import Bean.Computer;
import Service.CompanyService;
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

		
		
		compService.createComputer("Abc", LocalDate.of(2000, 5, 26), null, 3);
		
		
		
		
		
//		listComp = compService.listComputer();
//		for(int i =0; i<listComp.size(); i++) {
//			System.out.print(listComp.get(i).getId());
//			System.out.print(" "+listComp.get(i).getName());
//			System.out.print(" "+listComp.get(i).getIntroduced());
//			
//			System.out.println();
//		}
//		
//		
//		//print
//		Computer compx=compService.infoComp(613);
//		//modif
//		compService.updateComp(613, "Gateway", compx.getIntroduced(), compx.getDiscontinued(), compx.getCompany_id());
//		//print
//		Computer compz=compService.infoComp(613);
//		
//		compService.deleteComp(613);
//		
//		
//
//		CompanyService companyService= CompanyService.getInstance();
//		ArrayList<Company> listCompany = companyService.listCompany();
//		for(int i =0; i<listCompany.size(); i++) {
//			System.out.print(listCompany.get(i).getId());
//			System.out.print(" "+listCompany.get(i).getName());
//			
//			System.out.println();
//		}
		

	}

}
