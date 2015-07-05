package util.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

public class DBUtil {

	private static Logger logger = Logger.getLogger(DBUtil.class);

	private static DataSource source;
	
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	public static void setSource(DataSource source) {
		/*test*/
		InputStream is = DBUtil.class.getClassLoader().getResourceAsStream(
				"resource/dbcp.properties");
		Properties pro = new Properties();
		try {
			pro.load(is);
			DBUtil.source = BasicDataSourceFactory.createDataSource(pro);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		/*test*/
		// DBUtil.source = source;
	}

	public static Connection getConnection() {
		Connection conn = tl.get();
		try {
			if(conn == null) {
				conn = source.getConnection();
				tl.set(conn);
			}
		} catch (SQLException e) {
			logger.error("获取Connection失败");
			logger.error(e.getMessage());
		}
		return conn;
	}
}
