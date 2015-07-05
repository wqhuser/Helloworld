package servlet;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.db.StringUtil;
import util.project.FirstLetterUtil;
import util.servlet.BaseServlet;
import bean.Course;
import bean.Student;
import bean.Teacher;
import bean.User;
import dao.ClassDao;
import dao.CourseDao;
import dao.StudentDao;
import dao.TeacherDao;
import dao.impl.ClassDaoImpl;
import dao.impl.CourseDaoImpl;
import dao.impl.StudentDaoImpl;
import dao.impl.TeacherDaoImpl;

public class ManagerServlet extends BaseServlet {

	private TeacherDao teacherDao = new TeacherDaoImpl();

	private StudentDao studentDao = new StudentDaoImpl();

	private ClassDao classDao = new ClassDaoImpl();

	private CourseDao courseDao = new CourseDaoImpl();

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		if (isAction("addTeacher")) {
			Teacher teacher = new Teacher();
			int id = UUID.randomUUID().toString().hashCode();
			teacher.setId(id);
			teacher.setAge(Integer.valueOf(getParamters().get("age")));
			String name = getParamters().get("name");
			teacher.setName(name);
			teacher.setPosition(getParamters().get("position"));
			teacher.setSex(getParamters().get("sex"));
			teacherDao.addTeacher(teacher);
			String uName = FirstLetterUtil.getFirstLetter(name);
			String temp = uName;
			int t = 1;
			while (teacherDao.findUser(temp)) {
				temp = uName + t;
				t++;
			}
			User user = new User();
			user.setId("" + id);
			user.setName(temp);
			user.setPassword(uName);
			user.setType("t");
			teacherDao.addUser(user);
			resp.sendRedirect("teacherDetail");
		} else if (isAction("teacherDetail")) {
			List<Teacher> teachers = teacherDao.getAllTeachers();
			for (Teacher t : teachers) {
				User user = teacherDao.findUserById(t.getId());
				if (user != null)
					t.setUserName(user.getName());
				else
					t.setUserName("");
			}
			req.setAttribute("teachers", teachers);
			req.getRequestDispatcher("/jsp/delTeacher.jsp").forward(req, resp);
		} else if (isAction("delTeacher")) {
			String id = getParamters().get("id");
			Teacher t = new Teacher();
			t.setId(Integer.valueOf(id));
			teacherDao.delTeacher(t);
		} else if (isAction("addStudent")) {
			Student s = new Student();
			int id = UUID.randomUUID().hashCode();
			s.setId(id);
			s.setHobby(getParamters().get("hobby"));
			String name = getParamters().get("name");
			s.setName(name);
			s.setSex(getParamters().get("sex"));
			String newClass = getParamters().get("newClass");
			if (!StringUtil.isEmpty(newClass)) {
				bean.Class c = new bean.Class();
				int cid = UUID.randomUUID().hashCode();
				c.setId(cid);
				c.setName(newClass);
				s.setStuClass(cid);
				classDao.addClass(c);
			} else
				s.setStuClass(Integer.parseInt(getParamters().get("class")));
			studentDao.addStudent(s);
			String uName = FirstLetterUtil.getFirstLetter(name);
			String temp = uName;
			int t = 1;
			while (teacherDao.findUser(temp)) {
				temp = uName + t;
				t++;
			}
			User user = new User();
			user.setId("" + id);
			user.setName(temp);
			user.setPassword(uName);
			user.setType("s");
			teacherDao.addUser(user);
			resp.sendRedirect("studentDetail");
		} else if (isAction("studentDetail")) {
			List<Student> students = studentDao.getAllStudents();
			for (Student s : students) {
				User user = studentDao.findUserById(s.getId());
				if (user != null)
					s.setUserName(user.getName());
				else
					s.setUserName("");
			}
			req.setAttribute("students", students);
			req.getRequestDispatcher("/jsp/delStudent.jsp").forward(req, resp);
		} else if (isAction("delStudent")) {
			String id = getParamters().get("id");
			Student s = new Student();
			s.setId(Integer.valueOf(id));
			studentDao.delStudent(s);
		} else if (isAction("addCourse")) {
			Course course = new Course();
			course.setId(UUID.randomUUID().hashCode());
			course.setName(getParamters().get("name"));
			course.setTeacherId(Integer.parseInt(getParamters().get("teacher")));
			course.setCourseHour(Integer.parseInt(getParamters().get("hour")));
			courseDao.addCourse(course);
			resp.sendRedirect("courseDetail");
		} else if (isAction("courseDetail")) {
			List<Course> courses = courseDao.getAllCourses();
			req.setAttribute("courses", courses);
			req.getRequestDispatcher("/jsp/delCourse.jsp").forward(req, resp);
		} else if (isAction("delCourse")) {
			String id = getParamters().get("id");
			Course c = new Course();
			c.setId(Integer.valueOf(id));
			courseDao.delCourse(c);
		}
	}

}
