package main.java.com.excilys.sramirez.formation.computerdatabase.Mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.ComputerDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer.ComputerBuilder;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.CompanyService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;

public class ComputerMapper {

private final static ComputerMapper instance = new ComputerMapper();
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	
	private ComputerMapper() {
	}

	//je garde ca la pour l'instant mais à priori on n'en aura pas besoin
//	ComputerService computerService = ComputerService.getInstance();
//	CompanyService companyService =  CompanyService.getInstance();
	

	public Computer fromResultSetToModel(ResultSet results) throws SQLException {
		Computer toReturn = new Computer();
		ComputerBuilder computerBuilder = new ComputerBuilder();
		int id;
		if ( results.getInt("computer.id") != 0 ) {
			id = results.getInt("computer.id");
			computerBuilder.withId(id);
		}
		String name = "";
		if(results.getString("computer.name") != "") {
			name = results.getString("computer.name");
			computerBuilder.withName(name);
		}
		LocalDate introduced;
		if (results.getDate("introduced") != null) {
			introduced = results.getDate("introduced").toLocalDate();
			computerBuilder.withDateIntro(introduced);
		} else {
			introduced = null;
		}
		LocalDate discontinued;
		if (results.getDate("discontinued") != null) {
			discontinued = results.getDate("discontinued").toLocalDate();
			computerBuilder.withDateDisc(discontinued);
		} else {
			discontinued = null;
		}
		int companyId = results.getInt("company.id");
		String companyName = results.getString("company.name");
		Company company = new Company(companyId, companyName);
		toReturn = computerBuilder.withCompany(company).build();
		return toReturn;
	}
	
	
	public ComputerDTO toDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());
		computerDTO.setIntroduced(computer.getIntroduced() == null ? null : computer.getIntroduced().toString());
		computerDTO.setDiscontinued(computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString());
		computerDTO.setCompanyId(computer.getCompany().getId());
		computerDTO.setCompanyName(computer.getCompany().getName());
		return computerDTO;
	}
	
	//extends ElementDTO
	public ArrayList<ComputerDTO> toDTO(ArrayList<Computer> listComputers){
		ArrayList<ComputerDTO> listComputerDTO = new ArrayList<ComputerDTO>();
		listComputers.forEach(x->listComputerDTO.add(toDTO(x)));
		return listComputerDTO;
	}
	
	public Computer fromDTO(ComputerDTO computerDTO) {
		Computer c;
		ComputerBuilder computerBuilder = new Computer.ComputerBuilder();
		
		String name;
		if (computerDTO.getName() != "") {
			name = computerDTO.getName();
			computerBuilder.withName(name);
		}
		LocalDate introduced;
		if (computerDTO.getIntroduced() != "") {
			introduced = LocalDate.parse( computerDTO.getIntroduced() );
			computerBuilder.withDateIntro(introduced);
		} 

		LocalDate discontinued;
		if (computerDTO.getDiscontinued() != "") {
			discontinued = LocalDate.parse( computerDTO.getDiscontinued() );
			computerBuilder.withDateDisc(discontinued);
		} 
		
		Company compa;
		if (computerDTO.getCompanyName() != null && computerDTO.getCompanyName() != "") {
			compa = new Company(computerDTO.getCompanyId(), computerDTO.getCompanyName());
			computerBuilder.withCompany(compa);
		}
		
		c = computerBuilder.build();
		return c;
	}


}
