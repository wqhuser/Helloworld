package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bean.Grade;

public interface GradeDao {
	boolean addGrade(Grade g) throws IllegalArgumentException, IllegalAccessException, SQLException;

	List<Grade> getGrade(Map valueMap) throws Exception;
}
