package dao;

import bean.User;

public interface UserDao {
	User getLoginUser(String name, String password) throws Exception;

	String getName(String id, String type) throws Exception;
}
