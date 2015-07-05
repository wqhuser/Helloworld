package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.servlet.BaseServlet;
import bean.User;
import dao.UserDao;
import dao.impl.UserDaoImpl;

public class LoginServlet extends BaseServlet{
	
	private UserDao userDao = new UserDaoImpl();

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		User user = userDao.getLoginUser(getParamters().get("name"), getParamters().get("password"));
		out = resp.getWriter();
		if(user != null) {
			HttpSession session = req.getSession();//默认为true
			session.setAttribute("user", user);
			out.write("1");
		}
		else
			out.write("f");
	}

}
