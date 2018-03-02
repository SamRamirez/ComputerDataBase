package main.java.com.excilys.sramirez.formation.computerdatabase.Mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;

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

	//je garde ca la pour l'instant mais à priori on n'en aura pas besoin
//	ComputerService computerService = ComputerService.getInstance();
//	CompanyService companyService =  CompanyService.getInstance();
	
	
	
//	Field[] attributs = Computer.class.getFields();
//	//int nbAttributes = attributs.length;
//	for(Field attribut : attributs) {
//		attribut.setAccessible(true);
//		Object attributVrai;
//		try {
//			attributVrai = attribut.get(computer);
//			if(attributVrai!=null) {
//				Field fieldDTO = ComputerDTO.class.getField(attribut.getName());
//				if(fieldDTO.getType().equals(attribut.getType())){
//					fieldDTO.set(computerDTO, attributVrai);
//				}
//			}else {
//				
//			}
//		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//			e.printStackTrace();
//		}
//	}
	

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


}
