package test.java.com.excilys.sramirez.formation.computerdatabase.DAO;


import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Company;
import main.java.com.excilys.sramirez.formation.computerdatabase.bean.Computer;
import main.java.com.excilys.sramirez.formation.computerdatabase.connection.Connect;
import main.java.com.excilys.sramirez.formation.computerdatabase.CLI.CommandLines;
import main.java.com.excilys.sramirez.formation.computerdatabase.DAO.ComputerDAO;



@RunWith(PowerMockRunner.class)
@PrepareForTest(Connect.class)
@PowerMockIgnore( {"javax.management.*"})
public class TestSQL {

	private static final Logger logger = Logger.getLogger( TestSQL.class ) ;

		

		/**
		 * Create a connection.
		 * 
		 * @return connection object
		 * @throws SQLException
		 */
		private static Connection getConnection() throws SQLException {
			Connection toReturn=DriverManager.getConnection("jdbc:hsqldb:mem:computer-database_db", "admincdb", "qwerty1234");
			if ( toReturn.equals(null) ){
				logger.error("connection null");
			}else {
				logger.error("connection pas null");
			}
			return toReturn;
		}
		
		/**
		 * Database initialization for testing i.e.
		 * <ul>
		 * <li>Creating Table</li>
		 * <li>Inserting record</li>
		 * </ul>
		 * 
		 * @throws SQLException
		 */
		private static void initDatabase() {
			try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
				statement.execute(
						/*"drop schema if exists `computer-database-db`;\n" + 
						"  create schema if not exists `computer-database-db`;\n" + 
						"  use `computer-database-db`;\n" + 
						"\n" + 
						"  drop table if exists computer;\n" + 
						"  drop table if exists company;\n" + 
						"\n" + */
						"  create table company (\n" + 
						"    id                        bigint not null ,\n" + 
						"    name                      varchar(255),\n" + 
						"    constraint pk_company primary key (id))\n" + 
						"  ;\n" + 
						"\n" + 
						"  create table computer (\n" + 
						"    id                        bigint not null ,\n" + 
						"    name                      varchar(255),\n" + 
						"    introduced                timestamp NULL,\n" + 
						"    discontinued              timestamp NULL,\n" + 
						"    company_id                bigint default NULL,\n" + 
						"    constraint pk_computer primary key (id))\n" + 
						"  ;");
				connection.commit();
				statement.execute("CREATE USER admincdb PASSWORD qwerty1234 ADMIN");
				connection.commit();
				statement.executeUpdate(
						"insert into company (id,name) values (  1,\'Apple Inc.\');");
				statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);");
				statement.executeUpdate("insert into computer (id,name,introduced,discontinued,company_id) values (  3,'CM-200',null,null,2);");
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@BeforeClass
		public static void init() throws SQLException, ClassNotFoundException, IOException {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");

			// initialize database
			initDatabase();
		}

		@Test 
		public void testComputerDAO() throws SQLException {

			PowerMockito.mockStatic(Connect.class);
			when(Connect.getInstance().getConnection()).thenReturn(getConnection());
			
			Computer c1 = ComputerDAO.getInstance().infoComp(1).orElse(new Computer());
			Computer c = new Computer("MacBook Pro 15.4 inch", null, null, 1);
			if (c != null && c1 != null) {
				assertTrue(c.equals(c1));
			}		
		}



		
		@AfterClass
		public static void destroy() throws SQLException, ClassNotFoundException, IOException {
			try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
				statement.executeUpdate("DROP TABLE computer;\n"
						+ "DROP TABLE company;");
				connection.commit();
			}
		}


}

