package main.java.com.excilys.sramirez.formation.computerdatabase.CLI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.CompanyService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.Page;

public class CommandLines {

	private static final Logger logger = LogManager.getLogger( CommandLines.class ) ;

	//pourquoi il faut le mettre en static?!
	static ComputerService computerService = ComputerService.getInstance();
	static CompanyService companyService = CompanyService.getInstance();


	public static void main(String[] args) {
		LocalDate introduced;
		LocalDate discontinued;
		Scanner sc;
		String entry = "";
		System.out.println("Fonctions disponibles :\n - count computers ()\n - list computers ()\n - list computers (page)\n - list companies ()\n - list companies (page)\n - create computer (name, introduced, discontinued(optionnel), company_id)\n - create computer (name)\n - create computer (name, company_id)\n - update computer (id, name)\n - update computer (id, name, id_company(/0))\n - update computer (id, name, introduced(/null), discontinued(/null))\n - update computer (id, name, introduced(/null), discontinued(/null), id_company(/0))\n - delete computer (id)\n - info computer (id)\n - exit ()");
		System.out.println("les dates sont au format aaaa-mm-dd");
		System.out.println("ENTREZ LA COMMANDE");
		int id = 0;
		//la premiere condition n'est pas inutile en fait
		while (!(Command.fromNameToEnum(entry) != null) || !(Command.fromNameToEnum(entry).equals(Command.EXIT))) {	
			sc = new Scanner(System.in);
			entry = sc.nextLine();

			String entryParsed = entry.substring(0, entry.indexOf("(") + 1);
			switch (Command.fromNameToEnum(entryParsed)) {

			case COUNT_COMPUTER :
				int isItAnInteger = computerService.countComputers();
				System.out.println(isItAnInteger);
				break;
			case LIST_COMPUTERS : 
				int pageComputerNumber = 1;
				int numberOfComputers = 10;
				if ( ! (entry.substring(entry.indexOf("(")+1, entry.indexOf(")")).equals("")) ||  (entry.substring(entry.indexOf("(")+1, entry.indexOf(")")).equals(" ")) ) {	
					pageComputerNumber = Integer.parseInt(entry.substring(entry.indexOf("(")+1, entry.indexOf(")")));
				}
				Page pageComputer = new Page(numberOfComputers, pageComputerNumber);
				pageComputer.setListElements( computerService.listComputer(pageComputerNumber, numberOfComputers) );
				for(int i =0; i<pageComputer.getNumberOfElements(); i++) {
					System.out.print(((Computer) pageComputer.getListElements().get(i)).getId());
					System.out.print(((Computer) pageComputer.getListElements().get(i)).getName());
					System.out.print(" "+((Computer) pageComputer.getListElements().get(i)).getIntroduced());				
					System.out.println();
				}
				break;

			case LIST_COMPANIES	: 
				int pageCompanyNumber = 1;
				int numberOfCompanies = 10;
				if ( !( (entry.substring(entry.indexOf("(")+1, entry.indexOf(")")).equals("")) ||  (entry.substring(entry.indexOf("(")+1, entry.indexOf(")")).equals(" ")) ) ) {				
					pageCompanyNumber = Integer.parseInt(entry.substring(entry.indexOf("(")+1, entry.indexOf(")")));
				}
				Page pageCompany = new Page(numberOfCompanies, pageCompanyNumber);
				pageCompany.setListElements(companyService.listCompany(pageCompanyNumber, numberOfCompanies));
				for(int i =0; i<pageCompany.getNumberOfElements(); i++) {
					System.out.print(((Company) pageCompany.getListElements().get(i)).getId());
					System.out.print(" "+((Company) pageCompany.getListElements().get(i)).getName());					
					System.out.println();
				}
				break;
								
			case CREATE_COMPUTER :				
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
							} else {
								//traitement pour 3 arguments
								try {
									introduced = LocalDate.parse(entry.substring(indexVirgule1+2, indexVirgule2));
									discontinued=null;
									company_id = Integer.parseInt(entry.substring(indexVirgule2+2, entry.length()-1));
									computerService.createComputer(name, introduced, discontinued, company_id);
								}catch(Exception e){
									System.out.println("entree non valide");
								}	
								
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
			case UPDATE_COMPUTER :	
				//id, parameters to change)
				int nombreAttributs = (Computer.class.getDeclaredFields()).length;
				//attention if faut mettre une erreur dans le cas ou le premier argument n'est pas un nombre?
				ArrayList<Integer> listePositionsVirgules = new ArrayList<Integer>();
				int lastPos = 0;
				listePositionsVirgules.add(lastPos);
				while(entry.substring(lastPos).indexOf(",") != -1) {	
					
					
					lastPos=listePositionsVirgules.get(listePositionsVirgules.size()-1)+1+entry.substring(listePositionsVirgules.get(listePositionsVirgules.size()-1)).indexOf(",");
					listePositionsVirgules.add(lastPos);		
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
							computerService.updateComp(id, name, computerReferant.getIntroduced(), computerReferant.getDiscontinued(), computerReferant.getCompanyId());
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
							computerService.updateComp(id, name, introduced, discontinued, computerReferant.getCompanyId());
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
			case DELETE_COMPUTER :	
				id = Integer.parseInt(entry.substring(entry.indexOf("(") + 1, entry.indexOf(")")));
				computerService.deleteComp(id);
				break;
			case INFO_COMPUTER :
				id = Integer.parseInt(entry.substring(entry.indexOf("(") + 1, entry.indexOf(")")));
				computerService.infoComp(id);
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
