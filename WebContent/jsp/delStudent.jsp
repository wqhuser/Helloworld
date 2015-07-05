<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,bean.Student"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>删除学生</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function delStudent(id) {
		 $.ajax({
             type: "post",
             url: "${requestScope.baseUrl}/manager/delStudent",
             data: {'id':id},
             dataType: "text",
             success: function(data){
            	 location.href='${requestScope.baseUrl}/manager/studentDetail';
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
			<th>爱好</th>
			<th>班级</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<%List<Student> students = (List<Student>)request.getAttribute("students"); 
	  for(Student s : students) {
	%>
		<tr>
			<td><%=s.getName()%></td>
			<td><%=s.getUserName()%></td>
			<td><%=s.getSex()%></td>
			<td><%=s.getHobby()%></td>
			<td><%=s.getCl().getName()%></td>
			<td>
				<%-- <a href="javascript:delTeacher(<%=teacher.getId() %>)">修改</a>&nbsp; --%>
				<a href="javascript:delStudent(<%=s.getId() %>)">删除</a>
			</td>
		</tr>
	<%} %>
	</tbody>
</table>
<input type="button" value="返回" onclick="location.href='${requestScope.baseUrl}/main'"/></td>
</body>
</html>