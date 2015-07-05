package bean;

import java.util.Date;
import java.util.List;

import util.annotation.Foreign;
import util.annotation.ManyToMany;
import util.db.BaseVO;

public class Course implements BaseVO {

	private int id;

	private String name;

	private int teacherId;

	private int courseHour;

	@Foreign(foreginKey = "teacherId")
	private Teacher teacher;

	@ManyToMany(dependKey = "course_id", foreignKey = "id", key = "student_id", tableName = "grade", type = Student.class)
	private List<Student> students;

	@Override
	public String getDBTableName() {
		return "course";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getCourseHour() {
		return courseHour;
	}

	public void setCourseHour(int courseHour) {
		this.courseHour = courseHour;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
