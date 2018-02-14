import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Bean.Company;
import Bean.Computer;
import Service.CompanyService;
import Service.ComputerService;

public class CommandLines {
	
	//pourquoi il faut le mettre en static?!
	static ComputerService computerService= ComputerService.getInstance();
	static CompanyService companyService= CompanyService.getInstance();
	
	public static void main(String[] args) {
		Scanner sc;
		String entry="";
		System.out.println("Fonctions disponibles :\n - list computers ()\n - list companies ()\n - computer details (id)\n - create computer (name, introduced, discontinued(null?), company_id)\n - create computer (name)\n - create computer (name, company_id)\n - update computer (id, parameters to change)\n - delete computer (id)\n - exit ()");
		System.out.println("les dates sont au format aaaa-mm-dd");
		System.out.println("ENTREZ LA COMMANDE");
		while(!(entry.equals("exit"))) {
			sc= new Scanner(System.in);
			entry = sc.nextLine();
			
			String entryParsed = entry.substring(0, entry.indexOf("(")+1);
			switch(entryParsed) {
			case "list computers (" : 
				ArrayList<Computer> listComp = computerService.listComputer();
				for(int i =0; i<listComp.size(); i++) {
					System.out.print(listComp.get(i).getId());
					System.out.print(" "+listComp.get(i).getName());
					System.out.print(" "+listComp.get(i).getIntroduced());				
					System.out.println();
				}
				break;
			case "list companies ("	:
				ArrayList<Company> listCompany = companyService.listCompany();
				for(int i =0; i<listCompany.size(); i++) {
					System.out.print(listCompany.get(i).getId());
					System.out.print(" "+listCompany.get(i).getName());					
					System.out.println();
				}
				break;
			case "computer details ("	:
				System.out.println("computer details");
				System.out.println(entry);
				System.out.println(entryParsed);
				int id = Integer.parseInt(entry.substring(entry.indexOf("(")+1, entry.length()-1));
				//attention, on ne fait rien du return la
				computerService.infoComp(id);	
				break;
			case "create computer ("	:
				//name, introduced, discontinued, company_id)
				
				String name = "";
				int company_id=0;
				LocalDate introduced;
				LocalDate discontinued;
				
				//on compte d'abord combien il y a de virgules pour savoir combien il y a de parametres.
				// si ca ne mene à rien, on break en disant que cette combinaison de paramettres ne mene à rien
				
				//grace à ces variables, on regarde s'il y a 1, 2 ou 4 parametres avec eventuellement discontinued à null	
				//pas de virgule donc 1 ou 0 parametre. erreur non encore traitée dans le cas de 0 pour l'instant
				//test pour 0 arguments :  entre les 2 parentheses il n'y a rien ou que des espaces (grace a substring)
				if (entry.indexOf(",")==-1) {
					name=entry.substring(entry.indexOf("(")+1, entry.indexOf(")"));
					computerService.createComputer(name, null, null, 0);
				//4, 3 ou 2 args	
				}else {
					int indexVirgule1=entry.indexOf(",");
					int indexVirgule2=indexVirgule1+1+entry.substring(indexVirgule1+1).indexOf(",");
					int indexVirgule3=indexVirgule2+1+entry.substring(indexVirgule2+1).indexOf(",");
					System.out.println("0 à virgule1 "+entry.substring(0, indexVirgule1));
					System.out.println(indexVirgule1);
					System.out.println("virgule1 à virgule2 "+entry.substring(indexVirgule1+1, indexVirgule2));
					System.out.println(indexVirgule2);
					System.out.println("virgule2 à virgule3 "+entry.substring(indexVirgule2, indexVirgule3));
					System.out.println(indexVirgule3);
					
					
					name="\""+entry.substring(entry.indexOf("(")+1, indexVirgule1)+"\"";
					//company_id=Integer.parseInt(entry.substring(indexVirgule3, entry.indexOf(")")));
					//3 ou 2 args
					if(indexVirgule3==indexVirgule2) {
						//2 args
						if(indexVirgule2==indexVirgule1) {
							computerService.createComputer(name, null, null, company_id);
						}else {
							//traitement pour 3 arguments
							introduced = LocalDate.parse(entry.substring(indexVirgule1+2, indexVirgule2));
							discontinued=null;
							
							System.err.println(name);
							System.err.println(introduced);
							System.err.println(discontinued);
							System.err.println(company_id);
							computerService.createComputer(name, introduced, discontinued, company_id);
							
						}
					}else {
						//traitement pour 4 args
				        introduced = LocalDate.parse(entry.substring(indexVirgule1+1, indexVirgule2));
						discontinued=LocalDate.parse(entry.substring(indexVirgule2+1, indexVirgule3));
						computerService.createComputer(name, introduced, discontinued, company_id);
					}
					
					
				}
				
				
				
				
				//String name = entry.substring(entry.indexOf("(")+1, entry.indexOf(",")-1);
				//on ne fait rien du retour :/
				//computerService.createComputer(name, LocalDate.of(2000, 5, 26), LocalDate.of(2000, 5, 28), 3);
				break;
			case "update computer (" :	
				//id, parameters to change)
				break;
			case "delete computer (id)" :	
				break;
			case "exit (" :
				break;
			default :
				System.out.println("fonction non reconnue");
				break;	
			}
			System.out.println();
			System.out.println("Nouvelle commande?");
		}
		System.out.println("Sortie de la saisie");
	}

}
