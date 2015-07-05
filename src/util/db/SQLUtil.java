package util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class SQLUtil {
	private static Logger log = Logger.getLogger(SQLUtil.class);

	private static final Pattern pattern = Pattern
			.compile("(and|or)?\\s*?[\\(]?\\s*?[^\\s]+?\\s*?(like|>=|<=|>|<|=|<>|!=)\\s*?:([^\\s|^\\)]+)\\s*\\)?");

	private static final Pattern value = Pattern.compile(":([\\w_$]+)");

	private static final String bracket = "\\(\\s*\\)";

	private static final int SQLSERVER = 1;

	private static final int MYSQL = 2;

	private static final int ORACLE = 3;

	private static int dbType = -1;

	public static String genInsertSQL(BaseVO vo, Map<String, Object> valueMap) {
		String tbName = vo.getDBTableName();
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(tbName).append("(");
		int count = 0;
		String DBName = null;
		StringBuilder values = new StringBuilder("values(");
		for (String name : valueMap.keySet()) {
			if (count != 0) {
				sb.append(",");
				values.append(",");
			}
			DBName = StringUtil.convertDBName(name);
			sb.append(DBName);
			values.append(":").append(name);
			count++;
		}
		values.append(")");
		sb.append(") ").append(values);
		return sb.toString();
	}

	public static void setPreValues(PreparedStatement stmt, Collection c,
			Map valueMap, boolean isIgnoreNull) throws SQLException {
		Object[] keyArray = c.toArray();
		// System.out.println(Arrays.toString(keyArray));
		for (int i = 0; i < keyArray.length; i++) {
			Object obj = valueMap.get((String) keyArray[i]);
			if (!isIgnoreNull || null != obj) {
				stmt.setObject(i + 1, obj);
				// System.out.println("value:"+obj);
			}
		}
	}

	public static String dealSQL(String sql, Map valueMap, boolean isIgnoreNull) {
		Matcher matcher = pattern.matcher(sql);
		String valueName;
		String replaceStr;
		while (matcher.find()) {
			valueName = matcher.group(3);
			replaceStr = "";
			if (isIgnoreNull && !valueMap.containsKey(valueName)) {
				if (matcher.group().contains("(")
						&& !matcher.group().contains(")"))
					replaceStr = "(";
				else if (matcher.group().contains(")")
						&& !matcher.group().contains("("))
					replaceStr = ")";

			} else {
				replaceStr = matcher.group();
			}
			sql = sql.replace(matcher.group(), replaceStr);
		}
		sql = sql.replaceAll(bracket, "");
		return sql;
	}

	public static String genDeleteSQL(BaseVO vo, Map<String, Object> valueMap) {
		String tbName = vo.getDBTableName();
		StringBuilder sb = new StringBuilder("delete from ");
		sb.append(tbName).append(" where ");
		int count = 0;
		String DBName = null;
		String pkName = vo.getPKName();
		if (valueMap.get(pkName) != null) {
			String DBpk = StringUtil.convertDBName(pkName);
			sb.append(DBpk).append("=:").append(pkName);
		} else {
			for (String name : valueMap.keySet()) {// ///////////////////////////////替换名字
				if (count != 0)
					sb.append(" and ");
				DBName = StringUtil.convertDBName(name);
				sb.append(DBName).append("=:").append(name);
				count++;
			}
		}
		return sb.toString();
	}

	public static String genUpdateSQL(BaseVO vo, Map<String, Object> valueMap) {
		String tbName = vo.getDBTableName();
		StringBuilder sb = new StringBuilder("update ");
		sb.append(tbName).append(" set ");
		int count = 0;
		String PKName = vo.getPKName();
		String DBName = null;
		for (String name : valueMap.keySet()) {
			if (name.equalsIgnoreCase(PKName))
				continue;
			if (count != 0)
				sb.append(",");
			DBName = StringUtil.convertDBName(name);
			sb.append(DBName).append("=:").append(name);
			count++;
		}
		sb.append(" where ").append(StringUtil.convertDBName(PKName))
				.append("=:").append(PKName);
		return sb.toString();
	}

	public static String convertSQL(String sql, List<String> keys) {
		Matcher m = value.matcher(sql);
		String s = sql;
		while (m.find()) {
			keys.add(m.group(1));
			s = s.replaceFirst(m.group(), "?");
		}
		return s;
	}

	public static String genSelectSQL(Class<? extends BaseVO> c)
			throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		BaseVO vo = c.newInstance();
		Map<String, Object> map = ClassUtil.getFields(vo, false);
		String tbName = vo.getDBTableName();
		StringBuilder sb = new StringBuilder("select * from ");
		sb.append(tbName);
		sb.append(" where ");
		Iterator<String> keySet = map.keySet().iterator();
		for (int i = 0; keySet.hasNext(); i++) {
			String name = keySet.next();
			if (i != 0)
				sb.append(" and ");
			sb.append(StringUtil.convertDBName(name)).append("=:").append(name);
		}
		return sb.toString();
	}

	public static String genPageSQL(String sql, Pager pager) {
		int begin = pager.getBegin();
		int end = pager.getEnd();
		switch (judgeDBType()) {
		case SQLSERVER:
			sql = getSqlServerPagerSQL(sql, begin, end);
			break;
		case MYSQL:
			sql = getMySQLPagerSQL(sql, begin, end);
			break;
		case ORACLE:
			sql = getOraclePagerSQL(sql, begin, end);
			break;
		default:
			try {
				throw new Exception("不支持此数据库");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sql;
	}

	private static String getOraclePagerSQL(String sql, int begin, int end) {
		StringBuilder pagingSelect = new StringBuilder(100);
		pagingSelect
				.append("select * from ( select row_.*, rownum rownum_userforpage from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <= ")
				.append(begin + (long) (end - begin + 1))
				.append(") where rownum_userforpage > ").append(begin);
		return pagingSelect.toString();
	}

	private static String getMySQLPagerSQL(String sql, int begin, int end) {
		StringBuilder pagingSelect = new StringBuilder(100);
		pagingSelect.append(sql);
		pagingSelect.append(" limit ").append(begin).append(",")
				.append(end - begin + 1);
		return pagingSelect.toString();
	}

	private static String getSqlServerPagerSQL(String sql, int begin, int end) {

		StringBuilder pagingSelect = new StringBuilder(300);
		sql = sql.replaceFirst(
				"^\\s*[sS][eE][lL][eE][cC][tT]\\s+",
				(new StringBuilder()).append("select top ")
						.append(begin + (long) (end - begin + 1)).append(" ")
						.toString());
		pagingSelect
				.append(" select * from ( select row_number()over(order by __tc__)tempRowNumber,* from (select     __tc__=0, *  from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) t )tt )ttt where tempRowNumber > ")
				.append(begin).append(" and tempRowNumber <= ")
				.append(begin + (long) (end - begin + 1));
		return pagingSelect.toString();
	}

	private static int judgeDBType() {
		if (dbType != -1)
			return dbType;
		Connection conn = DBUtil.getConnection();
		String DBType = null;
		try {
			DBType = conn.getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
		}
		if (DBType.matches(".*[Ss]erver.*"))
			return SQLSERVER;
		else if (DBType.matches(".*[Mm][Yy].*"))
			return MYSQL;
		else if (DBType.matches(".*[Oo]racle.*"))
			return ORACLE;
		return 0;
	}

}
