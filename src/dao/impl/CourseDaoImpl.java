package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.db.BaseDAO;
import util.db.ClassUtil;
import bean.Course;
import bean.Grade;
import bean.Student;
import dao.CourseDao;

public class CourseDaoImpl extends BaseDAO implements CourseDao {

	@Override
	public boolean addCourse(Course c) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		return insertVO(c);
	}

	@Override
	public List<Course> getAllCourses() throws Exception {
		return searchByField(null, Course.class);
	}

	@Override
	public boolean delCourse(Course c) throws IllegalArgumentException,
			IllegalAccessException, SQLException {
		return deleteVO(c);
	}

	@Override
	public Course getCourseById(String courseId) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put("id", courseId);
		return searchByField(valueMap, Course.class).get(0);
	}

	@Override
	public List<Student> getStudents(String courseId, String period)
			throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = conn
					.prepareStatement("select s.* from student s, grade g where year(g.create_time) = ? and g.course_id = ? and s.Id = g.student_id");
			ps.setString(1, period);
			ps.setString(2, courseId);
		ResultSet rs = ps.executeQuery();
		List students = new ArrayList();
		students.addAll(ClassUtil.parseList(rs, Student.class));
		return students;
	}

	@Override
	public List<Grade> getGrades(String courseId, String period) throws Exception{
		Map valueMap = new HashMap();
		Connection conn = getConnection();
		PreparedStatement ps = conn
				.prepareStatement("select g.* from grade g where year(g.create_time) = ? and g.course_id = ?");
		ps.setString(1, period);
		ps.setString(2, courseId);
		ResultSet rs = ps.executeQuery();
		List grades = new ArrayList();
		grades.addAll(ClassUtil.parseList(rs, Grade.class));
		return grades;
	}

}
