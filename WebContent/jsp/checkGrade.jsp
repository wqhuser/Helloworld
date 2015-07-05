<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,bean.Grade"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看分数</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
</head>
<body>
		<table border="1px" bordercolor="#000000" cellspacing="0px"
			style="border-collapse: collapse">
			<thead>
				<tr>
					<th>课程名</th>
					<th>分数</th>
				</tr>
			</thead>
			<tbody>
				<%
					List<Grade> grades = (List<Grade>) request
							.getAttribute("grades");
					for (Grade g : grades) {
				%>
				<tr>
					<td><%=g.getCourse().getName()%></td>
					<td><%=g.getGrade()==null?"未知":g.getGrade()+""%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<input type="button" value="返回"
			onclick="location.href='${requestScope.baseUrl}/main'" />
</body>
</html>