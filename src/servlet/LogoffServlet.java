package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.servlet.BaseServlet;

public class LogoffServlet extends BaseServlet{

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		HttpSession session = req.getSession(false);
		if(session != null)
			session.invalidate();
		resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
	}

}
