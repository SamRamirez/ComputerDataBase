package main.java.com.excilys.sramirez.formation.computerdatabase.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.CompanyDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;

@Component
public class CompanyMapper {
	
//private final static CompanyMapper instance = new CompanyMapper();
//	
//	public static CompanyMapper getInstance() {
//		return instance;
//	}
//	
//	private CompanyMapper() {
//	}
		
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
