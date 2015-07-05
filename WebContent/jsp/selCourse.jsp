<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,bean.Course"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选修课程</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
</head>
<body>
	<form action="${requestScope.baseUrl}/student/selectCourse" method="post">
		<table border="1px" bordercolor="#000000" cellspacing="0px"
			style="border-collapse: collapse">
			<thead>
				<tr>
					<th>选择</th>
					<th>课程名</th>
					<th>授课老师</th>
					<th>课时</th>
				</tr>
			</thead>
			<tbody>
				<%
					List<Course> courses = (List<Course>) request
							.getAttribute("courses");
					for (Course c : courses) {
				%>
				<tr>
					<td><input type="radio" name="id" value="<%=c.getId()%>" /></td>
					<td><%=c.getName()%></td>
					<td><%=c.getTeacher().getName()%></td>
					<td><%=c.getCourseHour()%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<input type="submit" value="选择" />
		<input type="button" value="返回"
			onclick="location.href='${requestScope.baseUrl}/main'" />
	</form>
</body>
</html>