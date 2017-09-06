<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   -->
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>标准地住址管理系统</title>
<%@ include file="/static/meta/includeall.jsp" %>
<link href="${ctx}/static/layout/leftmenu.css" rel="stylesheet" type="text/css"></link>
<script src="static/layout/leftmenu.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/highcharts/exporting.js"></script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout" id="main_page_body_layout_PKjC58slRXqte7eALgbjjA">
<!-- 
<div href="static/layout/north.html" data-options="region:'north',border:false,split:true" style="height:60px;padding:0px;overflow: hidden;" ></div>
 -->
 <div href="static/layout/center.html" data-options="region:'center',plain:true,title:''" style="overflow: hidden;padding: 0px;" ></div>
<!-- 
<div href="static/layout/south.html" data-options="region:'south',border:false" style="height:25px;background:#EEE;padding:3px;overflow: hidden;" ></div>
 -->
<!-- 
<div data-options="region:'west',split:true,title:'系统菜单'" style="width:160px;">
		<div id="leftSideMenuAccordion" class="easyui-accordion"  data-options="fit:true" >
</div> 
-->
<div data-options="region:'west',split:true" style="width: 178px;" class="fixbg">
	<div class="easyui-layout" data-options="fit:true" >
		<div data-options="region:'north'"  style="background: url('static/layout/image/left_logo.jpg');height:110px;">
		
		</div>
		<div data-options="region:'center',split:true" class="logo">
			<div id="leftSideMenuAccordion" class="easyui-accordion mainpageleftmenu" data-options="fit:true,border:false,animate:false">
				<!-- <div title="标准化地址管理" style="overflow-y:auto"></div> -->
			</div>
		</div>
	</div>
</div>
</body>
</html>
