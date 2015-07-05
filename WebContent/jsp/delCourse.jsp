<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,bean.Course"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>删除课程</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function delCourse(id) {
		 $.ajax({
             type: "post",
             url: "${requestScope.baseUrl}/manager/delCourse",
             data: {'id':id},
             dataType: "text",
             success: function(data){
            	 location.href='${requestScope.baseUrl}/manager/courseDetail';
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
			<th>课程名</th>
			<th>授课老师</th>
			<th>课时</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<%List<Course> courses = (List<Course>)request.getAttribute("courses"); 
	  for(Course c : courses) {
	%>
		<tr>
			<td><%=c.getName()%></td>
			<td><%=c.getTeacher().getName()%></td>
			<td><%=c.getCourseHour()%></td>
			<td>
				<%-- <a href="javascript:delTeacher(<%=teacher.getId() %>)">修改</a>&nbsp; --%>
				<a href="javascript:delCourse(<%=c.getId() %>)">删除</a>
			</td>
		</tr>
	<%} %>
	</tbody>
</table>
<input type="button" value="返回" onclick="location.href='${requestScope.baseUrl}/main'"/></td>
</body>
</html>