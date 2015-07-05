package util.db;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import util.annotation.Foreign;
import util.annotation.ManyToMany;
import util.annotation.OneToMany;
import util.proxy.VOProxy;

public class ClassUtil {
	private static Logger log = Logger.getLogger(ClassUtil.class);

	public static BaseVO exportBean(Class<? extends BaseVO> c, ResultSet rs)
			throws InstantiationException, IllegalAccessException,
			SQLException, SecurityException, IllegalArgumentException,
			NoSuchFieldException {
		BaseVO vo = null;
		Map valueMap = new HashMap();
		vo = c.newInstance();
		ResultSetMetaData rmd = rs.getMetaData();
		int colCount = rmd.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			Object s = rs.getObject(i);
			if (s != null) {
				valueMap.put(StringUtil.colNameConvert(rmd.getColumnName(i)), s);
			}
		}
		putValueInVO(vo, valueMap);
		vo = new VOProxy().bind(vo);
		return vo;
	}

	public static void putValueInVO(BaseVO vo, Map valueMap)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		// for (Object obj : valueMap.entrySet()) {
		// Entry entry = (Entry) obj;
		// String fieldName = (String) entry.getKey();
		// Field field = vo.getClass().getDeclaredField(fieldName);
		// field.setAccessible(true);
		// // Method m = vo.getClass().getDeclaredMethod(new
		// //
		// StringBuilder("set").append(StringUtil.upcaseFirstChar(fieldName)).toString(),
		// // new Class[]{field.getType()});
		// // m.setAccessible(true);
		// field.set(vo, entry.getValue());
		//
		// }
		Field[] fields = vo.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object obj = valueMap.get(field.getName());
			if (obj != null)
				if (obj instanceof Object[]) {
					obj = ((Object[]) obj)[0];
				}
			field.set(vo, obj);
		}
	}

	/**
	 * 从ResultSet中获取List<VO>
	 * */
	public static List<BaseVO> parseList(ResultSet rs, Class<? extends BaseVO> c)
			throws SQLException, SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			NoSuchFieldException {
		List<BaseVO> list = new ArrayList();
		BaseVO vo = null;
		while (rs.next()) {
			vo = exportBean(c, rs);
			list.add(vo);
		}
		return list;
	}

	/**
	 * 取出vo中的值，并放进map
	 * */
	public static Map getFields(BaseVO vo, boolean isIgnoreNull)
			throws IllegalArgumentException, IllegalAccessException {
		Map map = new HashMap();
		Class c = vo.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.getAnnotation(Foreign.class) == null
					&& f.getAnnotation(ManyToMany.class) == null
					&& f.getAnnotation(OneToMany.class) == null) {
				Object obj = null;
				obj = f.get(vo);
				if (null != obj || !isIgnoreNull)
					map.put(f.getName(), obj);
			}
		}
		return map;
	}
}
