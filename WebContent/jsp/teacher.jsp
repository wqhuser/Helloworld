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
	欢迎<strong>${requestScope.name}</strong>老师&nbsp;&nbsp;<a href="${requestScope.baseUrl}/logoff">注销</a>
	</br>
	<a href="${requestScope.baseUrl}/jsp/selClass.jsp">登记分数</a></br>
	<a href="${requestScope.baseUrl}/jsp/selClass_m.jsp">修改分数</a></br>
</body>
</html>