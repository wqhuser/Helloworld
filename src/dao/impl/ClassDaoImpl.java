package dao.impl;

import java.sql.SQLException;
import java.util.List;

import bean.Class;

import util.db.BaseDAO;
import dao.ClassDao;

public class ClassDaoImpl extends BaseDAO implements ClassDao{

	@Override
	public List<bean.Class> getAllClass() {
		List<bean.Class> list = null;
		try {
			list = searchByField(null, bean.Class.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean addClass(Class c) throws IllegalArgumentException, IllegalAccessException, SQLException {
		return insertVO(c);
	}

}
