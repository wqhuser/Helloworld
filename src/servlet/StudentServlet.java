package servlet;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.servlet.BaseServlet;
import bean.Course;
import bean.Grade;
import bean.User;
import dao.CourseDao;
import dao.GradeDao;
import dao.impl.CourseDaoImpl;
import dao.impl.GradeDaoImpl;

public class StudentServlet extends BaseServlet{
	
	private CourseDao courseDao = new CourseDaoImpl();
	
	private GradeDao gradeDao = new GradeDaoImpl();

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		if(isAction("selectCourse")) {
			Grade grade = new Grade();
			grade.setId(UUID.randomUUID().hashCode());
			grade.setCourseId(Integer.parseInt(getParamters().get("id")));
			HttpSession session = req.getSession(false);
			grade.setStudentId(Integer.parseInt(((User)session.getAttribute("user")).getId()));
			grade.setCreateTime(new Date(System.currentTimeMillis()));
			gradeDao.addGrade(grade);
			resp.sendRedirect("checkGrade");
		} else if(isAction("courseList")) {
			List<Course> list = courseDao.getAllCourses();
			req.setAttribute("courses", list);
			req.getRequestDispatcher("/jsp/selCourse.jsp").forward(req, resp);
		} else if(isAction("checkGrade")) {
			HttpSession session = req.getSession(false);
			String userId = ((User)session.getAttribute("user")).getId();
			Map valueMap = new HashMap();
			valueMap.put("studentId", userId);
			List<Grade> list = gradeDao.getGrade(valueMap);
			req.setAttribute("grades", list);
			req.getRequestDispatcher("/jsp/checkGrade.jsp").forward(req, resp);
		}
	}

}
