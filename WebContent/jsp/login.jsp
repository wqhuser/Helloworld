<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function login() {
		var name = $('#name').val();
		var pass = $('#password').val();
		$.ajax({
			url : '${requestScope.baseUrl}/login',
			data : {
				'name' : name,
				'password' : pass
			},
			dataType : 'text',
			success : function(data) {
				if (data == 1)
					window.location.href = '${requestScope.baseUrl}/main';
				else
					alert('用户名或密码不正确');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(textStatus);
			}
		});
	}
</script>
</head>
<body>

	<table>
		<tr>
			<td>账号：</td>
			<td><input type="text" name="name" id="name" /></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="password" name="password" id="password" /></td>
		</tr>
		<tr>
			<td />
			<td><input type="button" value="登陆" onclick="login()" />
		</tr>
	</table>
</body>
</html>