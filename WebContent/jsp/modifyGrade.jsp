<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,bean.Grade"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改分数</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function sub() {
		var id = new Array();
		var grade = new Array();
		$("input[name='id']").each(function(i) {
			id.push($(this).val());
		});
		$("input[name='grade']").each(function(i) {
			grade.push($(this).val());
		});
		$.ajax({
             type: "post",
             url: "${requestScope.baseUrl}/teacher/recordGrade",
             data: {'id':JSON.stringify(id), 'grade':JSON.stringify(grade)},
             success:function(data) {
            	 alert("保存成功");
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
			<th>学生</th>
			<th>成绩</th>
		</tr>
	</thead>
	<tbody>
	<%List<Grade> grades = (List<Grade>)request.getAttribute("grades"); 
	  for(Grade g : grades) {
	%>
		<tr>
			<td><%=g.getStudent().getName()%></td>
			<td class="grade"><input type="text" name="grade" value="<%=g.getGrade()%>"/><input type="hidden" value="<%=g.getId()%>" name="id"/></td>
		</tr>
	<%} %>
	</tbody>
</table>
<input type="button" value="保存" onclick="sub()"/></td>
<input type="button" value="返回" onclick="location.href='${requestScope.baseUrl}/main'"/></td>
</body>
</html>