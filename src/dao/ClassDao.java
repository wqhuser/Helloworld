package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Class;

public interface ClassDao {
	List<bean.Class> getAllClass();

	boolean addClass(Class c) throws IllegalArgumentException, IllegalAccessException, SQLException;
}
