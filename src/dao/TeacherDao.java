package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Course;
import bean.Teacher;
import bean.User;

public interface TeacherDao {
	boolean addTeacher(Teacher teacher) throws Exception;

	boolean delTeacher(Teacher teacher) throws Exception;

	List<Teacher> getAllTeachers() throws Exception;

	boolean findUser(String uName) throws Exception;

	void addUser(User user) throws IllegalArgumentException, IllegalAccessException, SQLException;

	User findUserById(int id) throws Exception;

	List<Course> getCourses(String id) throws Exception;

	List<bean.Class> getClass(String string) throws SQLException, Exception;

	List<String> getPeriod(String string) throws SQLException;

	boolean recordGrade(List<String> ids, List<String> grade) throws IllegalArgumentException, SQLException, IllegalAccessException;
}
