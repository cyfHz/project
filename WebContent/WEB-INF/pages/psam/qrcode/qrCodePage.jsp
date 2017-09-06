<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<style>
.main{
	width: 99%;
	height: 99%;
}
.qrClass{
	float:left;
	text-align: center;
	width: 99%;
	margin:2px;
	padding-top:3px;
	height: 90%;
}
.nameClass{
	float:left;
	height:9%;
	width:99%;
	margin:5px;
	padding-bottom:5px;
	padding-top:10px;
	position: absolute;
	text-align: center;
	font-size: 12px;
	line-height: 13px;
}
.name{
     float: left;
	 width:99%;
	 margin:2px;
	 padding-top:3px;
	 text-align: left;
	 font-weight: bold;
}
</style>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<div class="main">
			<div class="name">
			<c:if test="${statusCode eq 300}">
				<c:out value="${msg}"></c:out>
			</c:if>
			<c:if test="${statusCode eq 200 }">
				<c:out value="${typeName}"></c:out>: <c:out value="${name}"></c:out>
			</c:if>
		</div>
		<div class="qrClass">
		<c:if test="${statusCode eq 200 }">
			<img alt="" src="${ctx}/psam/qrcode/showQRCode?code=${code}&type=${type}"  height="280px" width="300px">
			</c:if>
		</div>
		</div>
	</div>
</div>
