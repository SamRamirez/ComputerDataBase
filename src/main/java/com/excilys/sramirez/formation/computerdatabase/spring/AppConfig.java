package main.java.com.excilys.sramirez.formation.computerdatabase.spring;


//import javax.servlet.ServletContext;
//import javax.servlet.ServletRegistration;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//@Configuration
//@ComponentScan(basePackages = { "main.java.com.excilys.sramirez.formation.computerdatabase" })
//public class AppConfig implements WebApplicationInitializer {
//
//	@Override
//	public void onStartup(ServletContext servletContext) {
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//		context.setConfigLocation("main.java.com.excilys.sramirez.formation.computerdatabase.spring");
//		context.close();
//		//ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dashboard", new DispatcherServlet(context));
////		dispatcher.setLoadOnStartup(1);
////		dispatcher.addMapping("/");
//
//	}
//
//
//
//}

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
 
@Configuration
@ComponentScan(basePackages = "java.com.excilys.sramirez.formation.computerdatabase")
@PropertySource(value = { "classpath:connect.properties" })
public class AppConfig {
 
    @Autowired
    private Environment env;
 
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("url"));
        dataSource.setUsername(env.getRequiredProperty("login"));
        dataSource.setPassword(env.getRequiredProperty("password"));
        return dataSource;
    }
 
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }
 
}