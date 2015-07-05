package util.listener;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import util.db.DBUtil;

public class ServletContextInit implements ServletContextListener {

	static {
		PropertyConfigurator.configure(ServletContextInit.class
				.getClassLoader().getResource("").getPath()
				+ "resource/log4j.properties");
	}

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream("resource/dbcp.properties");
		Properties pro = new Properties();
		DataSource source = null;
		Connection conn = null;
		try {
			pro.load(is);
			source = BasicDataSourceFactory.createDataSource(pro);
			conn = source.getConnection();
			conn.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException("There isn't a database properties file");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException("It's failed to init dataSource");
		}
		DBUtil.setSource(source);
		logger.info("dataSource init successfully");
	}

}
