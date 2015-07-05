package servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.servlet.BaseServlet;
import dao.ClassDao;
import dao.impl.ClassDaoImpl;

public class ClassServlet extends BaseServlet{
	
	private ClassDao classDao = new ClassDaoImpl();

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/json; charset=utf-8");
		List<bean.Class> list = classDao.getAllClass();
		JSONArray arr = new JSONArray();
		JSONObject obj;
		for(bean.Class c : list) {
			obj = new JSONObject();
			obj.put("id", c.getId());
			obj.put("name", c.getName());
			arr.add(obj);
		}
		out.write(arr.toString());
	}

}
