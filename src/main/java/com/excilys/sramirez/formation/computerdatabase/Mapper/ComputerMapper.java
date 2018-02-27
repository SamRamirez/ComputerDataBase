package main.java.com.excilys.sramirez.formation.computerdatabase.Mapper;

import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.ComputerDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.CompanyService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;

public class ComputerMapper {

private final static ComputerMapper instance = new ComputerMapper();
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	
	private ComputerMapper() {
	}

	ComputerService computerService = ComputerService.getInstance();
	CompanyService companyService =  CompanyService.getInstance();
	
	public ComputerDTO toDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());
		computerDTO.setIntroduced(computer.getIntroduced().toString());
		computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		computerDTO.setCompanyId(computer.getCompanyId());
		//faire un service dans company qui donne le nom de la company en ayant entré l'id de la company
		computerDTO.setCompanyName(companyService.getCompanyName(computer.getCompanyId()));
		return computerDTO;
	}
	
	
}
