package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Course;
import bean.Grade;
import bean.Student;

public interface CourseDao {
	boolean addCourse(Course c) throws IllegalArgumentException, IllegalAccessException, SQLException;

	List<Course> getAllCourses() throws Exception;

	boolean delCourse(Course c) throws IllegalArgumentException, IllegalAccessException, SQLException;

	Course getCourseById(String courseId) throws Exception;

	List<Student> getStudents(String courseId, String period) throws Exception;

	List<Grade> getGrades(String courseId, String period) throws Exception;
}
