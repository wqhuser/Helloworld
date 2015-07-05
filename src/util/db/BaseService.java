package util.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public abstract class BaseService {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	protected void commit() {
		ThreadLocal t = new ThreadLocal();
		Connection conn = (Connection) t.get();
		try {
			conn.commit();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	protected void rollBack() {
		ThreadLocal t = new ThreadLocal();
		Connection conn = (Connection) t.get();
		try {
			conn.rollback();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
}
