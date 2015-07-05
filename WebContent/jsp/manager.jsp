<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
</head>
<body>
	欢迎<strong>${requestScope.name}</strong>管理员&nbsp;&nbsp;<a href="${requestScope.baseUrl}/logoff">注销</a>
	</br>
	<a href="${requestScope.baseUrl}/jsp/addTeacher.jsp">新增老师</a></br>
	<a href="${requestScope.baseUrl}/jsp/addStudent.jsp">新增学生</a></br>
	<a href="${requestScope.baseUrl}/jsp/addCourse.jsp">新增课程</a></br>
	<a href="${requestScope.baseUrl}/manager/teacherDetail">删除老师</a></br>
	<a href="${requestScope.baseUrl}/manager/studentDetail">删除学生</a></br>
	<a href="${requestScope.baseUrl}/manager/courseDetail">删除课程</a></br>
</body>
</html>