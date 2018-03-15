package main.java.com.excilys.sramirez.formation.computerdatabase.spring;


import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan(basePackages = { "main.java.com.excilys.sramirez.formation.computerdatabase" })
public class AppConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("main.java.com.excilys.sramirez.formation.computerdatabase.spring");
		context.close();
		//ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dashboard", new DispatcherServlet(context));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");

	}



}