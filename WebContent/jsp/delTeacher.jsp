<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,bean.Teacher"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>删除老师</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function delTeacher(id) {
		 $.ajax({
             type: "post",
             url: "${requestScope.baseUrl}/manager/delTeacher",
             data: {'id':id},
             dataType: "text",
             success: function(data){
            	 location.href='${requestScope.baseUrl}/manager/teacherDetail';
	         },
	         error:function(XMLHttpRequest, textStatus, errorThrown){
	             alert("操作异常");
	         }
         });
	}
</script>
</head>
<body>
<table border="1px" bordercolor="#000000" cellspacing="0px" style="border-collapse:collapse">
	<thead>
		<tr>
			<th>姓名</th>
			<th>账号</th>
			<th>性别</th>
			<th>年龄</th>
			<th>职位</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<%List<Teacher> teachers = (List<Teacher>)request.getAttribute("teachers"); 
	  for(Teacher teacher : teachers) {
	%>
		<tr>
			<td><%=teacher.getName()%></td>
			<td><%=teacher.getUserName()%></td>
			<td><%=teacher.getSex()%></td>
			<td><%=teacher.getAge()%></td>
			<td><%=teacher.getPosition()%></td>
			<td>
				<%-- <a href="javascript:delTeacher(<%=teacher.getId() %>)">修改</a>&nbsp; --%>
				<a href="javascript:delTeacher(<%=teacher.getId() %>)">删除</a>
			</td>
		</tr>
	<%} %>
	</tbody>
</table>
<input type="button" value="返回" onclick="location.href='${requestScope.baseUrl}/main'"/></td>
</body>
</html>