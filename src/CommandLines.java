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
		LocalDate introduced;
		LocalDate discontinued;
		Scanner sc;
		String entry="";
		System.out.println("Fonctions disponibles :\n - list computers ()\n - list companies ()\n - computer details (id)\n - create computer (name, introduced, discontinued(optionnel), company_id)\n - create computer (name)\n - create computer (name, company_id)\n - update computer (id, name)\n - update computer (id, name, id_company(/0))\n - update computer (id, name, introduced(/null), discontinued(/null))\n - update computer (id, name, introduced(/null), discontinued(/null), id_company(/0))\n - delete computer (id)\n - info computer (id)\n - exit ()");
		System.out.println("les dates sont au format aaaa-mm-dd");
		System.out.println("ENTREZ LA COMMANDE");
		int id=0;
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
				id = Integer.parseInt(entry.substring(entry.indexOf("(")+1, entry.length()-1));
				//attention, on ne fait rien du return la
				computerService.infoComp(id);	
				break;
			case "create computer ("	:
				//name, introduced, discontinued, company_id)
				
				String name = "";
				int company_id=0;
				
				//on compte d'abord combien il y a de virgules pour savoir combien il y a de parametres.
				// si ca ne mene à rien, on break en disant que cette combinaison de paramettres ne mene à rien
				
				//grace à ces variables, on regarde s'il y a 1, 2 ou 4 parametres avec eventuellement discontinued à null	
				//pas de virgule donc 1 ou 0 parametre. erreur non encore traitée dans le cas de 0 pour l'instant
				//test pour 0 arguments :  entre les 2 parentheses il n'y a rien ou que des espaces (grace a substring)
				
				//Xavier a parsé suivant les espaces. Ce serait mieux de copier sur lui je pense
				if (  (entry.substring(entry.indexOf("(")+1, entry.indexOf(")")) != "") ||  (entry.substring(entry.indexOf("(")+1, entry.indexOf(")")) != " ") ) {
					if (entry.indexOf(",")==-1) {
						name=entry.substring(entry.indexOf("(")+1, entry.indexOf(")"));
						computerService.createComputer(name, null, null, 0);
					//4, 3 ou 2 args	
					}else {
						int indexVirgule1=entry.indexOf(",");
						int indexVirgule2=indexVirgule1+1+entry.substring(indexVirgule1+1).indexOf(",");
						int indexVirgule3=indexVirgule2+1+entry.substring(indexVirgule2+1).indexOf(",");
						name=entry.substring(entry.indexOf("(")+1, indexVirgule1);
						//company_id=Integer.parseInt(entry.substring(indexVirgule3, entry.indexOf(")")));
						//3 ou 2 args
						if(indexVirgule3==indexVirgule2) {
							//2 args
							if(indexVirgule2==indexVirgule1) {
								company_id = Integer.parseInt(entry.substring(indexVirgule1+2, entry.length()-1));
								computerService.createComputer(name, null, null, company_id);
							}else {
								//traitement pour 3 arguments
								introduced = LocalDate.parse(entry.substring(indexVirgule1+2, indexVirgule2));
								discontinued=null;
								company_id = Integer.parseInt(entry.substring(indexVirgule2+2, entry.length()-1));
								computerService.createComputer(name, introduced, discontinued, company_id);
								
							}
						}else {
							//traitement pour 4 args
					        introduced = LocalDate.parse(entry.substring(indexVirgule1+2, indexVirgule2));
							discontinued = LocalDate.parse(entry.substring(indexVirgule2+2, indexVirgule3));
							company_id = Integer.parseInt(entry.substring(indexVirgule3+2, entry.length()-1));
							computerService.createComputer(name, introduced, discontinued, company_id);
						}	
					}
				}	
				break;
			case "update computer (" :	
				//id, parameters to change)
				int nombreAttributs = (Computer.class.getDeclaredFields()).length;
				//attention if faut mettre une erreur dans le cas ou le premier argument n'est pas un nombre?
				ArrayList<Integer> listePositionsVirgules = new ArrayList<Integer>();
				int lastPos = 0;
				listePositionsVirgules.add(lastPos);
				while(entry.substring(lastPos).indexOf(",") != -1) {	
//					System.out.println();
//					System.out.println(listePositionsVirgules.get(listePositionsVirgules.size()-1));
//					System.out.println(entry.substring(listePositionsVirgules.get(listePositionsVirgules.size()-1)));
					
					
					lastPos=listePositionsVirgules.get(listePositionsVirgules.size()-1)+1+entry.substring(listePositionsVirgules.get(listePositionsVirgules.size()-1)).indexOf(",");
					listePositionsVirgules.add(lastPos);
//					System.err.println("nb "+listePositionsVirgules.size());
//					System.err.println("pos "+listePositionsVirgules.get(listePositionsVirgules.size()-1));			
				}	
				if(nombreAttributs<listePositionsVirgules.size()-1 || listePositionsVirgules.size()-1<=0) {
					System.out.println("trop d'arguments ou aucun argument");
					break;
				}else {
					id=Integer.parseInt(entry.substring(entry.indexOf("(")+1, listePositionsVirgules.get(1)-1));
					Computer computerReferant = computerService.returnComp(id);
					switch(listePositionsVirgules.size()-1){
						case 1 : 
							name=entry.substring(listePositionsVirgules.get(1)+1, entry.length()-1);
							computerService.updateComp(id, name, computerReferant.getIntroduced(), computerReferant.getDiscontinued(), computerReferant.getCompany_id());
						break;
						case 2 :
							name=entry.substring(listePositionsVirgules.get(1)+1, listePositionsVirgules.get(2)-1);
							company_id=Integer.parseInt(entry.substring(listePositionsVirgules.get(2)+1, entry.length()-1));
							computerService.updateComp(id, name, computerReferant.getIntroduced(), computerReferant.getDiscontinued(), company_id);
						break;
						case 3 : 
							name=entry.substring(listePositionsVirgules.get(1)+1, listePositionsVirgules.get(2)-1);
							if(entry.substring(listePositionsVirgules.get(2)+1, listePositionsVirgules.get(3)-1).equals("null")) {
								introduced=null;
							}else {
								introduced=LocalDate.parse(entry.substring(listePositionsVirgules.get(2)+1, listePositionsVirgules.get(3)-1));
							}
							if(entry.substring(listePositionsVirgules.get(3)+1, entry.length()-1).equals("null")) {	
								discontinued=null;
							}else {
								discontinued=LocalDate.parse(entry.substring(listePositionsVirgules.get(3)+1, entry.length()-1));
							}	
							computerService.updateComp(id, name, introduced, discontinued, computerReferant.getCompany_id());
						break;
						case 4 : 
							name=entry.substring(listePositionsVirgules.get(1)+1, listePositionsVirgules.get(2)-1);
							if(entry.substring(listePositionsVirgules.get(2)+1, listePositionsVirgules.get(3)-1).equals("null")) {
								introduced=null;
							}else {
								introduced=LocalDate.parse(entry.substring(listePositionsVirgules.get(2)+1, listePositionsVirgules.get(3)-1));
							}
							if(entry.substring(listePositionsVirgules.get(3)+1, listePositionsVirgules.get(4)-1).equals("null")) {	
								discontinued=null;
							}else {
								discontinued=LocalDate.parse(entry.substring(listePositionsVirgules.get(3)+1, listePositionsVirgules.get(4)-1));
							}	
							company_id=Integer.parseInt( entry.substring(listePositionsVirgules.get(4)+1, entry.length()-1) );
							computerService.updateComp(id, name, introduced, discontinued, company_id);
						break;
						default :
							System.out.println("nous ne verrons jamais ce message");
					}	
				}			
				break;
			case "delete computer (" :	
				id=Integer.parseInt(entry.substring(entry.indexOf("(")+1, entry.indexOf(")")));
				computerService.deleteComp(id);
				break;
			case "info computer (" :
				id=Integer.parseInt(entry.substring(entry.indexOf("(")+1, entry.indexOf(")")));
				computerService.infoComp(id);
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
