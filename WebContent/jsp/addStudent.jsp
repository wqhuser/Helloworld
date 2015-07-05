<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加学生</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			type:'get',
			url:'${requestScope.baseUrl}/class',
			dataType:'json',
			success:function(data) {
				var classList = $('#class').get(0);
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
	
	function isEmpty() {
		var newClass = $('#newClass');
		var s_class = $('#class');
		if(newClass.val() != "")
			s_class.attr('disabled', true);
		else
			s_class.attr('disabled', false);
	}
</script>
</head>
<body>
	<form action="${requestScope.baseUrl}/manager/addStudent" method="post">
		<table>
			<tr>
				<td>姓名：</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td><label><input type="radio" name="sex" value="男" />男</label><label><input
						type="radio" name="sex" value="女" />女</label></td>
			</tr>
			<tr>
				<td>爱好：</td>
				<td><input type="text" name="hobby" /></td>
			</tr>
			<tr>
				<td>班级：</td>
				<td><select id="class" name="class"><option>请选择</option></select><input id="newClass" type="text"
					name="newClass" onchange="isEmpty()"/>(若选项中没有，则在输入框中新增)</td>
			</tr>
			<tr>
				<td><input type="submit" value="新增" /></td>
				<td><input type="button" value="取消" onclick="history.go(-1)" /></td>
			</tr>
		</table>
	</form>
</body>
</html>