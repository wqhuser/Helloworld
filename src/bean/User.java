package bean;

import util.db.BaseVO;

public class User implements BaseVO {

	private String id;

	private String name;

	private String password;

	private String type;

	@Override
	public String getDBTableName() {
		return "users";
	}

	@Override
	public Object getPKValue() {
		return id;
	}

	@Override
	public String getPKName() {
		return "id";
	}

	@Override
	public void setPKValue(Object o) {
		id = (String) o;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
