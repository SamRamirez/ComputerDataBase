package test.java.com.excilys.sramirez.formation.computerdatabase.DAO;

import org.junit.AfterClass;
import org.junit.BeforeClass;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

public class CompanyDAOTest {

//	@BeforeClass
//	public static void init() throws SQLException, ClassNotFoundException, IOException {
//		Class.forName("com.mysql.jdbc.Driver");
//		// initialize database
//		initDatabase();
//	}
//	
//
//	@AfterClass
//	public static void destroy() throws SQLException, ClassNotFoundException, IOException {
//		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
//			statement.executeUpdate("DROP TABLE company ");
//			connection.commit();
//		}
//	}
//
//	/**
//	 * Database initialization for testing i.e.
//	 * <ul>
//	 * <li>Creating Table</li>
//	 * <li>Inserting record</li>
//	 * </ul>
//	 * 
//	 * @throws SQLException
//	 */
//	private static void initDatabase() throws SQLException {
//		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
//			statement.execute(
////					"drop schema if exists `computer-database-db`;\n" + 
////					"  create schema if not exists `computer-database-db`;\n" + 
////					"  use `computer-database-db`;\n" + 
////					"\n" + 
////					"  drop table if exists computer;\n" + 
////					"  drop table if exists company;\n" + 
////					"\n" + 
//					"  create table company (\n" + 
//					"    id                        bigint not null auto_increment,\n" + 
//					"    name                      varchar(255),\n" + 
//					"    constraint pk_company primary key (id))\n" + 
//					"  ;\n" + 
//					"\n"  
////					"  create table computer (\n" + 
////					"    id                        bigint not null auto_increment,\n" + 
////					"    name                      varchar(255),\n" + 
////					"    introduced                timestamp NULL,\n" + 
////					"    discontinued              timestamp NULL,\n" + 
////					"    company_id                bigint default NULL,\n" + 
////					"    constraint pk_computer primary key (id))\n" + 
////					"  ;\n" + 
////					"\n" + 
////					"  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;\n" + 
////					"  create index ix_computer_company_1 on computer (company_id);"
//);
//			connection.commit();
//			statement.executeUpdate(
//					"insert into company (id,name) values (  1,'Apple Inc.');\n" + 
//					"insert into company (id,name) values (  2,'Thinking Machines');\n" + 
//					"insert into company (id,name) values (  3,'RCA');\n" + 
//					"insert into company (id,name) values (  4,'Netronics');\n");
//			connection.commit();
//		}
//	}
//
//	/**
//	 * Create a connection
//	 * 
//	 * @return connection object
//	 * @throws SQLException
//	 */
//	private static Connection getConnection() throws SQLException {
//		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false", "admincdb", "qwerty1234");
//	}
//
//	/**
//	 * Get total records in table
//	 * 
//	 * @return total number of records. In case of exception 0 is returned
//	 */
//	private int getTotalRecords() {
//		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
//			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM company");
//			if (result.next()) {
//				return result.getInt("total");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	@Test
//	public void getTotalRecordsTest() {
//		assertThat(3, is(getTotalRecords()));
//	}
//
//	@Test
//	public void checkNameExistsTest() {
//		try (Connection connection = getConnection();
//				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//						ResultSet.CONCUR_READ_ONLY);) {
//
//			ResultSet result = statement.executeQuery("SELECT name FROM company");
//
//			if (result.first()) {
//				assertThat("Apple Inc.", is(result.getString("name")));
//			}
//
//			if (result.last()) {
//				assertThat("Thinking Machines", is(result.getString("name")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
