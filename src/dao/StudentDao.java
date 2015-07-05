package dao;

import java.sql.SQLException;
import java.util.List;

import bean.Student;
import bean.User;

public interface StudentDao {
	boolean addStudent(Student s) throws IllegalArgumentException, IllegalAccessException, SQLException;

	List<Student> getAllStudents() throws Exception;

	User findUserById(int id) throws Exception;

	boolean delStudent(Student s) throws IllegalArgumentException, IllegalAccessException, SQLException;
}
