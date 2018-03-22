package main.java.com.excilys.sramirez.formation.computerdatabase.connection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;


@Component
public class ConnectPoolVersion {

//		private static ConnectPoolVersion instance;
//	    public static ConnectPoolVersion getInstance() {
//	        if (instance == null) {
//	           instance = new ConnectPoolVersion();
//	        }
//	        return instance;
//	    }
//	    private ConnectPoolVersion() { }

	    HikariDataSource ds;

	    public Connection openConnection() {
	    	if (ds == null) {
	    		openDataSource();
	    	}
			HikariProxyConnection conn = null;
			try {
				conn = (HikariProxyConnection) ds.getConnection();
			} catch (SQLException e) {
			}
			return conn;
		}

		public HikariDataSource openDataSource() {
			ResourceBundle bundle = ResourceBundle.getBundle("connect");
	        String login = bundle.getString("login");
	        String password = bundle.getString("password");
	        String url = bundle.getString("url");
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(url);
			config.setUsername(login);
			config.setPassword(password);
			config.setDriverClassName("com.mysql.jdbc.Driver");
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "256");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			config.setMaximumPoolSize(64);
			config.setMinimumIdle(5);
			ds = new HikariDataSource(config);
			return ds;
		}

}
