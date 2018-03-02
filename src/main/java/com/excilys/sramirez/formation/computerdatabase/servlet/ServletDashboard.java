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
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;

@WebServlet("/ServletDashboard")
public class ServletDashboard extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger( ServletDashboard.class ) ;
	
	static ComputerService computerService = ComputerService.getInstance();


	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @request requete.
	 * @response reponse.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int firstPage = 1;
		int nbEltsPerPage =  10;
		ArrayList<Computer> listCompu = computerService.listComputer(firstPage, nbEltsPerPage);
		request.setAttribute("listComputers", listCompu);
		
		int nbCompu = computerService.countComputers();
		int maxPage = (nbCompu / nbEltsPerPage) + 1;
		request.setAttribute("nbCompu", nbCompu);		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,  response);
		

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	

}
