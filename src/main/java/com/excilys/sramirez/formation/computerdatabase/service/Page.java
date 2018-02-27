package main.java.com.excilys.sramirez.formation.computerdatabase.service;
import java.util.ArrayList;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Element;

public class Page {
	
	private int numberOfElements;
	private int pageNumber;
	private ArrayList<? extends Element> listElements;
	
	
	

	public Page(int numberOfElements, int pageNumber) {
		this.numberOfElements = numberOfElements;
		this.pageNumber = pageNumber;
		this.listElements = new ArrayList<>(numberOfElements);
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
