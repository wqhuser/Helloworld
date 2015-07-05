<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增老师</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
</head>
<body>
<form action="${requestScope.baseUrl}/manager/addTeacher" method="post">
	<table>
		<tr>
			<td>姓名：</td><td><input type="text" name="name"/></td>
		</tr>
		<tr>
			<td>性别：</td><td><label><input type="radio" name="sex" value="男"/>男</label><label><input type="radio" name="sex" value="女"/>女</label></td>
		</tr>
		<tr>
			<td>年龄：</td><td><input type="text" name="age"/></td>
		</tr>
		<tr>
			<td>职位：</td><td><input type="text" name="position"/></td>
		</tr>
		<tr>
			<td><input type="submit" value="新增"/></td><td><input type="button" value="取消" onclick="history.go(-1)"/></td>
		</tr>
	</table>
</form>
</body>
</html>