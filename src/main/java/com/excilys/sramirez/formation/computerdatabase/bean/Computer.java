package main.java.com.excilys.sramirez.formation.computerdatabase.bean;
import java.time.LocalDate;

public class Computer extends Element{
	
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int companyId;
	

	
	
	public Computer() {
	}
	
	//C'est dans les constructeurs qu'on va éventuellement demander à ce que les champs soient non null
	public Computer(String name) {
		this.name=name;
	}
	
	
	public Computer(String name , LocalDate introduced , int companyId ) {
		this.name = name ;
		this.introduced = introduced ;
		this.companyId = companyId ;
	}
	
	public Computer(String name , LocalDate introduced , LocalDate discontinued , int companyId ) {
		this.name = name ;
		this.introduced = introduced ;
		this.discontinued = discontinued ;
		this.companyId = companyId ;
	}
	
	public Computer(int id, String name , LocalDate introduced , LocalDate discontinued , int companyId ) {
		this.id=id;
		this.name = name ;
		this.introduced = introduced ;
		this.discontinued = discontinued ;
		this.companyId = companyId ;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + "]";
	}
	
	
	
}
