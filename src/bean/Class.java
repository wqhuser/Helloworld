package bean;

import util.db.BaseVO;

public class Class implements BaseVO{
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDBTableName() {
		return "class";
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
		id = (Integer) o;
	}

}
