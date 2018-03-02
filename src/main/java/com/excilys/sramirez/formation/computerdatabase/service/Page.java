package main.java.com.excilys.sramirez.formation.computerdatabase.service;
import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Element;

public class Page {
	
	private int numberOfElements;
	private int pageNumber;
	private ArrayList<? extends Element> listElements;
	
//	private ComputerService computerService = ComputerService.getInstance();
//	private CompanyService companyService = CompanyService.getInstance();
	private Function<?,?,ArrayList<? extends Element>> f;

	public Page(int numberOfElements, int pageNumber, Function<Integer, Integer, ArrayList<? extends Element>> func) {
		this.numberOfElements = numberOfElements;
		this.pageNumber = pageNumber;
		this.f = func;
		this.listElements = func.apply(numberOfElements, pageNumber);
	}

	public Function<?, ?, ArrayList<? extends Element>> getF() {
		return f;
	}

	public void setF(Function<?, ?, ArrayList<? extends Element>> f) {
		this.f = f;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public ArrayList<? extends Element> getListElements() {
		return listElements;
	}

	public void setListElements(ArrayList<? extends Element> listElements) {
		this.listElements = listElements;
	}
	
	

}
