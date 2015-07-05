package util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class BaseDAO {

	private Logger logger = Logger.getLogger(this.getClass());

	private Connection conn;

	private PreparedStatement stmt;

	private static final int INSERT = 1;

	private static final int DELETE = 2;

	private static final int UPDATE = 3;

	protected BaseDAO() {
		conn = DBUtil.getConnection();
	}

	public Connection getConnection() {
		try {
			if (null == conn || conn.isClosed()) {
				conn = DBUtil.getConnection();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return conn;
	}

	public void setAutoCommit(boolean flag) {
		try {
			getConnection().setAutoCommit(flag);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void rollBack() {
		try {
			if (null != conn)
				conn.rollback();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void closeConnection() {
		try {
			if (null != conn && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected Pager getPager(String sql, Map valueMap, Pager pager)
			throws SQLException {
		sql = sql.replace("where", "where 1=1 and");
		List<String> keys = new ArrayList();
		// 忽略空，则删掉sql中map不含的参数的相关语句
		if (null == valueMap)
			valueMap = new HashMap();
		sql = SQLUtil.dealSQL(sql, valueMap, true);
		// 将:**替换为?
		sql = SQLUtil.convertSQL(sql, keys);
		sql = SQLUtil.genPageSQL(sql, pager);
		System.out.println(sql);
		stmt = getConnection().prepareStatement(sql);
		SQLUtil.setPreValues(stmt, keys, valueMap, false);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		int colCount = md.getColumnCount();
		List<Map> list = new ArrayList<Map>();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= colCount; i++)
				map.put(md.getColumnName(i), rs.getObject(i));
			list.add(map);
		}
		pager.setData(list);
		// closeConnection();
		return pager;
	}

	protected List searchByField(String sql, Map valueMap) throws SQLException {
		List<Map> list = new ArrayList<Map>();
		sql = sql.replace("where", "where 1=1 and");
		List<String> keys = new ArrayList();
		// 忽略空，则删掉sql中map不含的参数的相关语句
		if (null == valueMap)
			valueMap = new HashMap();
		sql = SQLUtil.dealSQL(sql, valueMap, true);
		// 将:**替换为?
		sql = SQLUtil.convertSQL(sql, keys);
		System.out.println(sql);
		stmt = getConnection().prepareStatement(sql);
		SQLUtil.setPreValues(stmt, keys, valueMap, false);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rmd = rs.getMetaData();
		int colCount = rmd.getColumnCount();
		while (rs.next()) {
			Map values = new HashMap();
			for (int i = 1; i <= colCount; i++) {
				Object s = rs.getObject(i);
				if (s != null) {
					values.put(StringUtil.colNameConvert(rmd.getColumnName(i)),
							s);
				}
			}
			list.add(values);
		}
		// list = ClassUtil.parseList(rs, c);
		return list;
	}

	protected List searchByField(String sql, Map valueMap,
			Class<? extends BaseVO> c, boolean isIgnoreNull) throws Exception {
		List<BaseVO> list = null;
		sql = sql.replace("where", "where 1=1 and");
		List<String> keys = new ArrayList();
		// 忽略空，则删掉sql中map不含的参数的相关语句
		if (null == valueMap)
			valueMap = new HashMap();
		sql = SQLUtil.dealSQL(sql, valueMap, isIgnoreNull);
		// 将:**替换为?
		sql = SQLUtil.convertSQL(sql, keys);
		System.out.println(sql);
		stmt = getConnection().prepareStatement(sql);
		SQLUtil.setPreValues(stmt, keys, valueMap, false);
		ResultSet rs = stmt.executeQuery();
		list = ClassUtil.parseList(rs, c);
		return list;
	}

	protected <T extends BaseVO> List<T> searchByField(Map valueMap, Class<T> c)
			throws Exception {
		String sql = "";
		try {
			sql = SQLUtil.genSelectSQL(c);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (null == valueMap)
			valueMap = new HashMap();
		return searchByField(sql, valueMap, c, true);
	}

	protected boolean insertVO(BaseVO vo) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		return updateData(vo, INSERT, true);
	}

	protected boolean deleteVO(BaseVO vo) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		return updateData(vo, DELETE, true);
	}

	protected void updateVO(BaseVO vo, boolean isIngoreNull)
			throws SQLException, IllegalArgumentException,
			IllegalAccessException {
		updateData(vo, UPDATE, isIngoreNull);
	}

	protected boolean updateData(BaseVO vo, int type, boolean isIgnoreNull)
			throws SQLException, IllegalArgumentException,
			IllegalAccessException {
		Map valueMap = ClassUtil.getFields(vo, isIgnoreNull);
		String sql = null;
		if (!valueMap.isEmpty()) {
			switch (type) {
			case INSERT:
				sql = SQLUtil.dealSQL(SQLUtil.genInsertSQL(vo, valueMap),
						valueMap, true);
				break;
			case DELETE:
				sql = SQLUtil.dealSQL(SQLUtil.genDeleteSQL(vo, valueMap),
						valueMap, true);
				break;
			case UPDATE:
				sql = SQLUtil.dealSQL(SQLUtil.genUpdateSQL(vo, valueMap),
						valueMap, isIgnoreNull);
			}
			logger.debug("\n" + sql);
			List c = new ArrayList();
			Connection conn = getConnection();
			stmt = conn.prepareStatement(SQLUtil.convertSQL(sql, c));
			SQLUtil.setPreValues(stmt, c, valueMap, false);
		} else {
			throw new SQLException("vo can't be empty");
		}
		return stmt.execute();
		// closeConnection();
	}

	// public static void main(String[] args) {
	// BaseDAO d = new BaseDAO();
	// Map valueMap = new HashMap();
	// valueMap.put("t3", "");
	// d.searchByField("select * from tb where (t1=:t1 or t2 <> :t2) and t3 like :t3",
	// valueMap, null, true);
	// }
}
