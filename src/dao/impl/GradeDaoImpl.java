package dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bean.Grade;
import util.db.BaseDAO;
import dao.GradeDao;

public class GradeDaoImpl extends BaseDAO implements GradeDao{

	@Override
	public boolean addGrade(Grade g) throws IllegalArgumentException, IllegalAccessException, SQLException {
		return insertVO(g);
	}

	@Override
	public List<Grade> getGrade(Map valueMap) throws Exception {
		return searchByField(valueMap, Grade.class);
	}

}
