package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.db.BaseDAO;
import bean.Student;
import bean.Teacher;
import bean.User;
import dao.UserDao;

public class UserDaoImpl extends BaseDAO implements UserDao{

	@Override
	public User getLoginUser(String name, String password) throws Exception {
		Long time = System.currentTimeMillis();
		Map valueMap = new HashMap();
		valueMap.put("name", name);
		valueMap.put("password", password);
		List list = searchByField(valueMap, User.class);
		System.out.println(System.currentTimeMillis() - time);
		if(list != null && list.size() != 0)
			return (User) list.get(0);
		return null;
//		Long time = System.currentTimeMillis();
//		Connection conn = getConnection();
//		PreparedStatement ps = conn .prepareStatement("select * from USERS where name = ? and password = ?"); 
//		ps.setString(1, name);
//		ps.setString(2, password);
//		ResultSet rs = ps.executeQuery();
//		User user = new User();
//		if(rs.next()) {
//			user.setId(rs.getString("id"));
//			user.setPassword(password);
//			user.setName(rs.getString("name"));
//			user.setType(rs.getString( "type"));
////			……//将数据库中的数据注入对象
//		}
//		System.out.println(System.currentTimeMillis() - time);
//		return user;
	}

	@Override
	public String getName(String id, String type) throws Exception {
		Map valueMap = new HashMap();
		valueMap.put("id", Integer.parseInt(id));
		if("sm".equals(type)) {
			return searchByField(valueMap, User.class).get(0).getName();
		}else if ("t".equals(type)) {
			return searchByField(valueMap, Teacher.class).get(0).getName();
		} else {
			return searchByField(valueMap, Student.class).get(0).getName();
		}
	}

}
