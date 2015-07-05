package bean;

import util.db.BaseVO;

public class Teacher implements BaseVO {

	private int id;

	private String name;

	private String sex;

	private int age;

	private String position;

	private String userName;

	@Override
	public String getDBTableName() {
		return "teacher";
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
		this.id = (Integer) o;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
