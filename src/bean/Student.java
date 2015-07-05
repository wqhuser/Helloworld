package bean;

import java.util.List;

import util.annotation.Foreign;
import util.annotation.ManyToMany;
import util.annotation.OneToMany;
import util.db.BaseVO;

public class Student implements BaseVO {

	private int id;

	private String name;

	private String sex;

	private String hobby;

	private int stuClass;

	private String userName;

	@OneToMany(Type = Grade.class, foreginKey = "student_id")
	private List<Grade> grades;

	@ManyToMany(dependKey = "student_id", foreignKey = "id", key = "course_id", tableName = "grade", type = Course.class)
	private List<Course> courses;

	@Foreign(foreginKey = "stuClass")
	private bean.Class cl;

	@Override
	public String getDBTableName() {
		return "student";
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

	public List<Grade> getGrades() {
		return grades;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public int getStuClass() {
		return stuClass;
	}

	public void setStuClass(int stuClass) {
		this.stuClass = stuClass;
	}

	public bean.Class getCl() {
		return cl;
	}

	public void setCl(bean.Class cl) {
		this.cl = cl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
