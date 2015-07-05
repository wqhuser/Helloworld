package util.db;

public interface BaseVO {
	String getDBTableName();
	Object getPKValue();
	String getPKName();//获取该实现类中主键的属性名
	void setPKValue(Object o);
}
