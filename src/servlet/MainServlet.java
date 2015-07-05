package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.servlet.BaseServlet;
import bean.User;
import dao.UserDao;
import dao.impl.UserDaoImpl;

public class MainServlet extends BaseServlet {
	
	private UserDao userDao = new UserDaoImpl();

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		HttpSession session = req.getSession(false);
		if(session == null)
			return;
		User user = (User) session.getAttribute("user");
		String type = user.getType();
		req.setAttribute("name", userDao.getName(user.getId(), type));
		if(type.equals("sm")) {
			req.getRequestDispatcher("/jsp/manager.jsp").forward(req,resp);
		}
		else if(type.equals("t")) {
			req.getRequestDispatcher("/jsp/teacher.jsp").forward(req,resp);
		}
		else if(type.equals("s")) {
			req.getRequestDispatcher("/jsp/student.jsp").forward(req,resp);
		}
	}

}
