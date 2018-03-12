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
import main.java.com.excilys.sramirez.formation.computerdatabase.service.CompanyService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;

@WebServlet("/ServletEdit")
public class ServletEditComputer extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger( ServletEditComputer.class ) ;	
	static CompanyService companyService = CompanyService.getInstance();
	static CompanyMapper companyMapper = CompanyMapper.getInstance();
	static ComputerMapper computerMapper = ComputerMapper.getInstance();
	static ComputerService computerService = ComputerService.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int idCompu = Integer.parseInt( request.getParameter("id") );
		request.setAttribute("id", idCompu);
		
		ArrayList<CompanyDTO> listCompanyDTO = new ArrayList<CompanyDTO>();

        for ( Company c : companyService.listCompany() ) {
        	logger.debug(c.toString());
            listCompanyDTO.add(companyMapper.toDTO(c));
        }
        request.setAttribute("listCompany", listCompanyDTO);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,  response);
	}



	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idCompu = Integer.parseInt( request.getParameter("id") );
		request.setAttribute("id", idCompu);
		
		Computer compuReferent = computerService.infoComp(idCompu);
		
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

		Computer computerUpdated = computerMapper.fromDTO(compuDTO);
		Computer c = computerService.complete(compuReferent, computerUpdated);
		computerService.updateComp(idCompu, c.getName(), c.getIntroduced(), c.getDiscontinued(), c.getCompany());
		
		
		response.sendRedirect("ServletDashboard");
		//this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,  response);
	}

}
