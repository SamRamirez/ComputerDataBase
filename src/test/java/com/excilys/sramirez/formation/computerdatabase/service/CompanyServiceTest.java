package test.java.com.excilys.sramirez.formation.computerdatabase.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.CompanyDAO;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.service.CompanyService;


	@RunWith(MockitoJUnitRunner.class)
	public class CompanyServiceTest {

		@InjectMocks
		public CompanyService companyService;

		@Mock
		public CompanyDAO companyDao;

		@Test
		public void getAllCompaniesTest_SUCCESS() {
			List<Company> companies = new ArrayList<>();
			companies.add(new Company());
			companies.add(new Company());

			Mockito.when(companyDao.list(1, 10)).thenReturn((ArrayList<Company>) companies);
			assertEquals(companyService.list(1, 10).size(), 2);
		}

}
