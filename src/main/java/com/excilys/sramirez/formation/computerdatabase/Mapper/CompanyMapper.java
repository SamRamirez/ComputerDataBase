package main.java.com.excilys.sramirez.formation.computerdatabase.Mapper;

import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.CompanyDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.ComputerDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;

public class CompanyMapper {
	
private final static CompanyMapper instance = new CompanyMapper();
	
	public static CompanyMapper getInstance() {
		return instance;
	}
	
	private CompanyMapper() {
	}
		
		public CompanyDTO toDTO(Company company) {
			CompanyDTO companyDTO = new CompanyDTO();
			companyDTO.setId(company.getId());
			companyDTO.setName(company.getName());
			return companyDTO;
		}
		
		
		//extends ElementDTO
		public ArrayList<CompanyDTO> toDTO(ArrayList<Company> listCompanies){
			ArrayList<CompanyDTO> listCompanyDTO = new ArrayList<CompanyDTO>();
			listCompanies.forEach(x->listCompanyDTO.add(toDTO(x)));
			return listCompanyDTO;
		}
		
	
	
	

}
