<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="file/upload" method="POST" enctype="multipart/form-data">
			<p>特征：<select name="feature">
				  <option value ="1001">活动1</option>
				  <option value ="1002">活动2</option>
				  <option value="1003">活动3</option>
				</select></p>
			<p>模块：<select name="module">
				  <option value ="item1">模块1</option>
				  <option value ="item1">模块2</option>
				</select></p>
			<p>jar文件：<input type="file" name="file"></p>
			<input type="submit">
		</form>
</body>
</html>