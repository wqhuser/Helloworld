<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加课程</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			type:'get',
			url:'${requestScope.baseUrl}/teacher/teacherSelect',
			dataType:'json',
			success:function(data) {
				var classList = $('#teacher').get(0);
				var option;
				for(var i = 0; i < data.length; i++) {
					option = document.createElement("OPTION");
					classList.options.add(option);
					option.innerText = data[i].name;
					option.value = data[i].id;
				}
			}
		});
	});
</script>
</head>
<body>
	<form action="${requestScope.baseUrl}/manager/addCourse" method="post">
		<table>
			<tr>
				<td>课程名：</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>授课老师：</td>
				<td><select id="teacher" name="teacher"><option>请选择</option></select></td>
			</tr>
			<tr>
				<td>课时：</td>
				<td><input type="text" name="hour" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="新增" /></td>
				<td><input type="button" value="取消" onclick="history.go(-1)" /></td>
			</tr>
		</table>
	</form>
</body>
</html>