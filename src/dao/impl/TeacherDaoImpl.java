package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.db.BaseDAO;
import bean.Course;
import bean.Grade;
import bean.Teacher;
import bean.User;
import dao.TeacherDao;

public class TeacherDaoImpl extends BaseDAO implements TeacherDao {

	@Override
	public boolean addTeacher(Teacher teacher) throws Exception {
		return insertVO(teacher);
	}

	@Override
	public boolean delTeacher(Teacher teacher) throws Exception {
		User user = new User();
		user.setId(teacher.getId() + "");
		return deleteVO(teacher) && deleteVO(user);
	}

	@Override
	public List<Teacher> getAllTeachers() throws Exception {
		return searchByField(null, Teacher.class);
	}

	@Override
	public boolean findUser(String uName) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put("name", uName);
		List user = searchByField(valueMap, User.class);
		if (user != null && user.size() != 0)
			return true;
		return false;
	}

	@Override
	public void addUser(User user) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		insertVO(user);
	}

	@Override
	public User findUserById(int id) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put("id", id + "");
		valueMap.put("type", "t");
		List l = searchByField(valueMap, User.class);
		if (l != null && l.size() != 0)
			return (User) l.get(0);
		return null;
	}

	@Override
	public List<Course> getCourses(String id) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put("teacherId", id);
		return searchByField(valueMap, Course.class);
	}

	@Override
	public List<bean.Class> getClass(String courseId) throws Exception {
		String sql = "select distinct s.stu_class id from student s where s.id in (select student_id from grade g where g.course_id=:courseId)";
		Map valueMap = new HashMap();
		valueMap.put("courseId", courseId);
		List<Map> classIds = searchByField(sql, valueMap);
		List<bean.Class> classes = new ArrayList<bean.Class>();
		for (Map m : classIds) {
			classes.addAll(searchByField(m, bean.Class.class));
		}
		return classes;
	}

	@Override
	public List<String> getPeriod(String courseId) throws SQLException {
		String sql = "select distinct year from (select YEAR(create_time) year from grade where course_id=:courseId) t";
		Map valueMap = new HashMap();
		valueMap.put("courseId", courseId);
		List<Map> result = searchByField(sql, valueMap);
		List<String> periods = new ArrayList<String>();
		for(Map m : result) {
			periods.add(""+m.get("year"));
		}
		return periods;
	}

	@Override
	public boolean recordGrade(List<String> ids, List<String> grade) throws IllegalArgumentException, SQLException, IllegalAccessException {
		Grade g;
		for(int i = 0; i < ids.size(); i++) {
			g = new Grade();
			g.setId(Integer.parseInt(ids.get(i)));
			g.setGrade(Integer.parseInt(grade.get(i)));
			updateVO(g, true);
		}
		return false;
	}

}
