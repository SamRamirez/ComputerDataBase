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

import main.java.com.excilys.sramirez.formation.computerdatabase.CLI.CommandLines;
import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.ComputerDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.Mapper.ComputerMapper;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.Page;

@WebServlet("/ServletDashboard")
public class ServletDashboard extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger( ServletDashboard.class ) ;
	
	static ComputerService computerService = ComputerService.getInstance();
	static ComputerMapper computerMapper = ComputerMapper.getInstance();


	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @request requete.
	 * @response reponse.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int firstPage = 1;
		int nbEltsPerPage =  10;
		int firstLocalisationPages = 1;
		
		//les computers à afficher
//		ArrayList<ComputerDTO> listCompu = computerMapper.toDTO(computerService.listComputer(firstPage, nbEltsPerPage));
//		request.setAttribute("listComputers", listCompu);
		
		//le nombre de computers
		int nbCompu = computerService.countComputers();
		request.setAttribute("nbCompu", nbCompu);		
		
		//on va passer les liens vers les pages suivantes à la jsp
		int page;
		if (request.getParameter("page") != null) {
            page = Integer.valueOf(request.getParameter("page"));
            logger.debug("PAGE");
            logger.debug("page if = "+page);
        } else {
            page = firstPage;
            logger.debug("PAS PAGE");
        }
        request.setAttribute("page", page);
        ArrayList<ComputerDTO> list = new ArrayList<ComputerDTO>();
        logger.debug("page = "+page);
        for (Computer c : (ArrayList<Computer>) new Page(page, nbEltsPerPage, (x,y) -> computerService.listComputer(x, y)).getListElements()) {
        	logger.debug(c.toString());
            list.add(computerMapper.toDTO(c));
        }
        request.setAttribute("listComputers", list);

        //pour le next page et le prévious page
        int localisationPages;
        int localisationNext;
        
        if (request.getParameter("localisationNext") != null ) {
        	localisationNext = Integer.valueOf(request.getParameter("localisationNext"));
        	logger.debug("localisation next = "+localisationNext);
        } else {
        	localisationNext=0;
        	logger.debug("pas localisation next");
        }
        request.setAttribute("localisationNext", localisationNext);

        if (request.getParameter("localicationPages") != null ) {
        	localisationPages = Integer.valueOf(request.getParameter("localisationPages"));
        	logger.debug("localisation page = "+localisationPages);
        } else {
        	localisationPages = firstLocalisationPages;
        }
        localisationPages+=localisationNext;
        request.setAttribute("localisationPages", localisationPages);
        logger.debug("localisationPages = "+ localisationPages);
        logger.debug("localisationNext = "+ localisationNext);
        
        
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,  response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	

}
