package main.java.com.excilys.sramirez.formation.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.CompanyDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.ComputerDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.Mapper.CompanyMapper;
import main.java.com.excilys.sramirez.formation.computerdatabase.Mapper.ComputerMapper;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer.ComputerBuilder;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.CompanyService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.Page;

@WebServlet("/ServletAddComputer")
public class ServletAddComputer extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger( ServletAddComputer.class ) ;	
	static CompanyService companyService = CompanyService.getInstance();
	static CompanyMapper companyMapper = CompanyMapper.getInstance();
	static ComputerMapper computerMapper = ComputerMapper.getInstance();
	static ComputerService computerService = ComputerService.getInstance();
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<CompanyDTO> listCompanyDTO = new ArrayList<CompanyDTO>();

        for ( Company c : companyService.listCompany() ) {
        	logger.debug(c.toString());
            listCompanyDTO.add(companyMapper.toDTO(c));
        }
        request.setAttribute("listCompany", listCompanyDTO);

		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,  response);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ComputerDTO compuDTO = new ComputerDTO();
		
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		compuDTO.setName(name);
		compuDTO.setIntroduced(introduced);
		compuDTO.setDiscontinued(discontinued);
		
		int companyId = 0;
		String companyName = "";
		if(request.getParameter("companyId") != null) {
			companyId = Integer.parseInt( request.getParameter("companyId") );
			companyName = companyService.getCompanyName(companyId);
			compuDTO.setCompanyId(companyId);
			compuDTO.setCompanyName(companyName);
		}	
		
		
		Computer c = computerMapper.fromDTO(compuDTO);
		
		//changer le service pour que si certains atributs du computer sont à null, ca marche quand meme (on va passer un computer directement en parametre du service et on gere les null autre part)
		computerService.createComputer(c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany());
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,  response);
	}
	
}
