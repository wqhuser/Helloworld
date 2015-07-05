package util.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import util.db.DBUtil;

public class TestFilter implements Filter{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	ThreadLocal local;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		local = new ThreadLocal();
		Connection conn = DBUtil.getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("", e);
		}
		local.set(conn);
		chain.doFilter(req, res);
		try {
			conn.close();
		} catch (SQLException e) {
			log.error("", e);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
