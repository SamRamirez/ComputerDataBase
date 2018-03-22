package main.java.com.excilys.sramirez.formation.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import main.java.com.excilys.sramirez.formation.computerdatabase.DTO.ComputerDTO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.ComputerService;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.PageComputer;

@Controller
@WebServlet("/ServletDashboard")
public class ServletDashboard extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger( ServletDashboard.class ) ;
	
	@Autowired
	private ComputerService computerService;
	//= ComputerService.getInstance();
	
	@Autowired
	private ComputerMapper computerMapper;
	//= ComputerMapper.getInstance();
	
	
	int firstPage = 1;
	int nbEltsPerPage =  10;
	int firstLocalisationPages = 1;
	int nbAcessiblePages = 5;


	@SuppressWarnings("unchecked")
	public void listComputersFilterOrOrderedOrNot(HttpServletRequest request, int page){
		 ArrayList<ComputerDTO> list = new ArrayList<ComputerDTO>();
		if(request.getParameter("filter") != null && request.getParameter("filter") != "") {	
	    	String filter = request.getParameter("filter");
	    	request.setAttribute("filter", filter);
	    	System.out.println("filter = "+filter);
	    	//à enlever a priori
//	    	for (Computer c : (ArrayList<Computer>) new PageComputer(page, nbEltsPerPage, (x,y) -> computerService.listFiltered(x, y, filter)).getListElements()) {
	    	//à garder à priori
//	    	for (Computer c : (ArrayList<Computer>) new PageComputer(page, nbEltsPerPage, computerService.listFiltered(page, nbEltsPerPage, filter)).getListComputers()) {
	    	for (Computer c : computerService.listFiltered(page, nbEltsPerPage, filter) ) {
	    		list.add(computerMapper.toDTO(c));
	        }
	        //request.setAttribute("listComputers", list);
	    }else if(request.getParameter("orderType") != null && request.getParameter("orderType") != "") {
			String orderType = request.getParameter("orderType");
			request.setAttribute("orderType", orderType);
			System.out.println("orderType = "+orderType);
			//à enlever à priori
			//for (Computer c : (ArrayList<Computer>) new PageComputer(page, nbEltsPerPage, (x,y) -> computerService.listOrdered(x, y, orderType)).getListElements()) {
		    //à garder a priori
			//for (Computer c : (ArrayList<Computer>) new PageComputer(page, nbEltsPerPage, computerService.listOrdered(page, nbEltsPerPage, orderType)).getListComputers()) {
			for (Computer c : computerService.listOrdered(page, nbEltsPerPage, orderType) ) {
		    	list.add(computerMapper.toDTO(c));
	        }
		}else {
	        //à enlever a priori
			//for (Computer c : (ArrayList<Computer>) new PageComputer(page, nbEltsPerPage, (x,y) -> computerService.list(x, y)).getListElements()) {
			for (Computer c : computerService.list(page, nbEltsPerPage) ) {  
				list.add(computerMapper.toDTO(c));
	        }
	        //request.setAttribute("listComputers", list);
	    }
		
        request.setAttribute("listComputers", list);
	}	
	
	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @request requete.
	 * @response reponse.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("nbAcessiblePages", nbAcessiblePages);
		
		//le nombre de computers et de pages
		int nbCompu = computerService.count();
		int maxPage;
		if(nbCompu % nbEltsPerPage != 0) {
			maxPage = (nbCompu / nbEltsPerPage) + 1;
		}else {
			maxPage = (nbCompu / nbEltsPerPage);
		}
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("nbCompu", nbCompu);		
		
		//on va passer les liens vers les pages suivantes à la jsp
		int page;
		if (request.getParameter("page") != null) {
            page = Integer.valueOf(request.getParameter("page"));
        } else {
            page = firstPage;
        }
        request.setAttribute("page", page);
        listComputersFilterOrOrderedOrNot(request, page);

        //pour le next page et le prévious page
        int localisationPages;
        int localisationNext = 0;
        
        if (request.getParameter("localisationNext") != null ) {
        	localisationNext = Integer.valueOf(request.getParameter("localisationNext"));
        } 
        request.setAttribute("localisationNext", localisationNext);

        if (request.getParameter("localicationPages") != null ) {
        	localisationPages = Integer.valueOf(request.getParameter("localisationPages"));
        } else {
        	localisationPages = firstLocalisationPages;
        }
        localisationPages+=localisationNext;
        request.setAttribute("localisationPages", localisationPages);

        
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,  response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String selection = (String) request.getParameter("selection");

			if (selection != null) {
				String[] toDeleteList = selection.split(",");

				for (String toDelete : toDeleteList) {
					int id = Integer.parseInt(toDelete);
					computerService.delete(id);
				}
			}
			
			response.sendRedirect("ServletDashboard");
	}
		
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}


	

}
