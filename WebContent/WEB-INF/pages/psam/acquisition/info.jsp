<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml">
<head>
<title>建筑物信息采集</title>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ include file="/static/meta/easyui.jsp" %>
<%@ include file="/static/meta/easyuipub.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style type="text/css" media="screen">
body {
	background-color: #f1f1f1;
	margin: 0;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
}

.container {
	margin: 50px auto 40px auto;
	width: 600px;
	text-align: center;
}

h1 {
	width: 800px;
	position: relative;
	left: -100px;
	letter-spacing: -1px;
	line-height: 60px;
	font-size: 60px;
	font-weight: 100;
	margin: 0px 0 50px 0;
	text-shadow: 0 1px 0 #fff;
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
		<h1>信息提示:</h1>
		<p>
			<strong>对不起，当前您不能进行信息采集，可能原因：一、选取点不在当前用户管辖警务区内部，二、当前点位置未查询到警务区数据。</strong>
		</p>
	</div>
</body>
</html>