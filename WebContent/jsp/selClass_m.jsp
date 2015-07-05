<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登记分数</title>
<script type="text/javascript"
	src="${requestScope.baseUrl}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			type:'get',
			url:'${requestScope.baseUrl}/teacher/getCourse',
			dataType:'json',
			success:function(data) {
				var course = $('#course').get(0);
				var option;
				for(var i = 0; i < data.length; i++) {
					option = document.createElement("OPTION");
					course.options.add(option);
					option.innerText = data[i].name;
					option.value = data[i].id;
				}
			}
		});
	});
	
	function getClassList() {
		var course = $('#course');
		$.ajax({
			type:'get',
			url:'${requestScope.baseUrl}/teacher/getPeriod',
			data:{'id':course.val()},
			dataType:'json',
			success:function(data) {
				var _class = $('#time').get(0);
				var option;
				for(var i = 0; i < data.length; i++) {
					option = document.createElement("OPTION");
					_class.options.add(option);
					option.innerText = data[i];
				}
			}
		});
	}
	
	function sub() {
		var time = $('#time').val();
		var course = $('#course').val();
		location.href="${requestScope.baseUrl}/teacher/modifyGrade?courseId="+course+"&time="+time;
	}
</script>
</head>
<body>
	选择课程：<select id="course" onchange="getClassList()"><option>请选择</option></select></br>
	选择学年：<select id="time" name="time"><option>请选择</option></select></br>
	<input type="button" value="修改或查看分数" onclick="sub()"/>
</body>
</html>