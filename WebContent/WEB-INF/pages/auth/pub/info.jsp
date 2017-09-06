<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>信息提示</title>
<style type="text/css" media="screen">
body {
	background-color: #f1f1f1;
	margin: 0;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

.container {
	/* margin: 50px auto 40px auto; */
	width: "100%";
	height:"100%";
	text-align: center;
	background-color: #f1f1f1;
	margin: 0;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}



p {
	color: rgba(0, 0, 0, 0.5);
	margin: 20px 0;
	line-height: 1.6;
}
</style>
</head>
<body>
	<div class="container">
		<h1>信息提示</h1>
		<p>
		<strong>${msg}</strong>
		</p>
	
	</div>
</body>
</html>

