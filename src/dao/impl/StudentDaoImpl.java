package dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.User;
import dao.StudentDao;
import util.db.BaseDAO;

public class StudentDaoImpl extends BaseDAO implements StudentDao{

	@Override
	public boolean addStudent(Student s) throws IllegalArgumentException, IllegalAccessException, SQLException {
		return insertVO(s);
	}

	@Override
	public List<Student> getAllStudents() throws Exception {
		return searchByField(null, Student.class);
	}

	@Override
	public User findUserById(int id) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put("id", id + "");
		valueMap.put("type", "s");
		List l = searchByField(valueMap, User.class);
		if (l != null && l.size() != 0)
			return (User) l.get(0);
		return null;
	}

	@Override
	public boolean delStudent(Student s) throws IllegalArgumentException, IllegalAccessException, SQLException {
		User user = new User();
		user.setId(s.getId()+"");
		return deleteVO(s) && deleteVO(user);
	}

}
