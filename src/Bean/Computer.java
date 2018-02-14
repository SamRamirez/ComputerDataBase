package Bean;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Computer {
	
	public int id;
	public String name;
	public LocalDate introduced;
	public LocalDate discontinued;
	public int company_id;
	

	
	
	public Computer() {
		super();
	}
	
	
	public Computer(String name2, LocalDate introduced2, LocalDate discontinued2, int company_id2) {
		super();
		this.name = name2;
		this.introduced = introduced2;
		this.discontinued = discontinued2;
		this.company_id = company_id2;
	}
	
	public Computer(int id, String name2, LocalDate introduced2, LocalDate discontinued2, int company_id2) {
		super();
		this.id=id;
		this.name = name2;
		this.introduced = introduced2;
		this.discontinued = discontinued2;
		this.company_id = company_id2;
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
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	
}
