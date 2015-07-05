package servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.servlet.BaseServlet;
import bean.Course;
import bean.Grade;
import bean.Teacher;
import bean.User;
import dao.CourseDao;
import dao.StudentDao;
import dao.TeacherDao;
import dao.impl.CourseDaoImpl;
import dao.impl.StudentDaoImpl;
import dao.impl.TeacherDaoImpl;

public class TeacherServlet extends BaseServlet{
	
	private TeacherDao teacherDao = new TeacherDaoImpl();
	
	private StudentDao studentDao = new StudentDaoImpl();
	
	private CourseDao courseDao = new CourseDaoImpl();

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		if(isAction("teacherSelect")) {
			JSONArray arr = new JSONArray();
			List<Teacher> list = teacherDao.getAllTeachers();
			JSONObject obj;
			for(Teacher t : list) {
				obj = new JSONObject();
				obj.put("id", t.getId());
				obj.put("name", t.getName());
				arr.add(obj);
			}
			resp.setContentType("application/json; charset=utf-8");
			out.write(arr.toString());
		} else if(isAction("getCourse")) {
			HttpSession session = req.getSession(false);
			List<Course> courses = teacherDao.getCourses(((User)session.getAttribute("user")).getId());
			JSONArray arr = new JSONArray();
			JSONObject obj;
			for(Course c : courses) {
				obj = new JSONObject();
				obj.put("id", c.getId());
				obj.put("name", c.getName());
				arr.add(obj);
			}
			resp.setContentType("application/json; charset=utf-8");
			out.write(arr.toString());
		} else if(isAction("getPeriod")) {
			List<String> periods = teacherDao.getPeriod(getParamters().get("id"));
			JSONArray arr = new JSONArray();
			arr.addAll(periods);
			resp.setContentType("application/json; charset=utf-8");
			out.write(arr.toString());
		} else if (isAction("getGrade")) {
			String courseId = getParamters().get("courseId");
			String period = getParamters().get("time");
			List<Grade> grades = courseDao.getGrades(courseId, period);
			req.setAttribute("grades", grades);
			req.getRequestDispatcher("/jsp/recordGrade.jsp").forward(req, resp);
		} else if (isAction("recordGrade")) {
			String gradeIds = getParamters().get("id");
			String grades = getParamters().get("grade");
			JSONArray arr = JSONArray.fromObject(gradeIds);
			List<String> ids = new ArrayList<String>();
			for(int i = 0; i < arr.size(); i++)
				ids.add(arr.getString(i));
			arr = JSONArray.fromObject(grades);
			List<String> grade = new ArrayList<String>();
			for(int i = 0; i < arr.size(); i++)
				grade.add(arr.getString(i));
			teacherDao.recordGrade(ids, grade);
			resp.setContentType("application/json; charset=utf-8");
			out.write("1");
		} else if (isAction("modifyGrade")) {
			String courseId = getParamters().get("courseId");
			String period = getParamters().get("time");
			List<Grade> grades = courseDao.getGrades(courseId, period);
			req.setAttribute("grades", grades);
			req.getRequestDispatcher("/jsp/modifyGrade.jsp").forward(req, resp);
		}
	}

}
