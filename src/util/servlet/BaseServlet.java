package util.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public abstract class BaseServlet extends HttpServlet{
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	private String action;
	
	public PrintWriter out;
	
	private Map<String, String> parameters;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		int flag = uri.lastIndexOf("/");
		action = uri.substring(flag + 1);//action为最后一斜杠的后面部分
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript;charset=UTF-8");
		out = resp.getWriter();
		convertParamters(req.getParameterMap());
		try {
		execute(req, resp);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	protected abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
	
	protected boolean isAction(String action) {
		return this.action.equals(action);
	}
	
	private void convertParamters(Map<String, String[]> parameterMap) {
		this.parameters = new HashMap<String, String>();
		for(String key : parameterMap.keySet()) {
			parameters.put(key, parameterMap.get(key)[0]);
		}
	}
	
	protected Map<String, String> getParamters() {
		return this.parameters;
	}
}
