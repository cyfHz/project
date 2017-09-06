<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/static/meta/includeall.jsp"%>
<!-- 下面style的用处是把命名空间"v"和系统预定义的行为VML连接 -->
<style>
v\:* {
	BEHAVIOR: url(#default#VML)
}

.mlph-panel .pagination-info {
	float: left;
	margin: 0 0 0 6px;
}
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<script type="text/javascript">
	function loadMlph(value,name) {
		var params = {};
		if(value){
			params.DZMC=value;
		}
		$('#mlphGrid').datagrid('load',params);
	}
	var _MapApp;
	$(function() {
		setTimeout(function(){
			$('#v-center').html('<div id="mymap" style="height: 100%;width: 100%;"></div>');
			_MapApp = new EzMap(document.getElementById("mymap"));
			_MapApp.showMapControl();
			_MapApp.centerAndZoom(new Point(118.07812,35.96093 ), 4);
		},100);
		initMlphGrid();
	});

	function initMlphGrid() {
		$("#mlphGrid").datagrid({
			url : "${pageContext.request.contextPath}/psam/mlph/mark/list",
			fit : true,
			title : '待标记的门楼牌号列表',
			pagination : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			onSelect : function(rowIndex, rowData) {
				_MapApp.changeDragMode('drawPoint', null, null,function(s) {
					addMarker(s,rowData.DZMC);
					$.messager.confirm('确认','经纬度为：'+s+' 是否保存？',function(flag){
						if(flag){
							$.post('${pageContext.request.contextPath}/psam/mlph/mark/save', {YWLSH:rowData.YWLSH,location:s}).success(function(data){
								var msg = $.parseJSON(data);
								if(msg.statusCode==200){
									$('#mlphGrid').datagrid('deleteRow',rowIndex);	
								}
								$.messager.show({title:'提示',msg:msg.message});
							});
						}
					});
				});
			}
		});
	}
	
	function addMarker(s,title){
		var arr = s.split(",");
		var p = new Point(arr[0], arr[1]);
		var pIcon=new Icon();
		pIcon.image="${pageContext.request.contextPath}/static/images/marker.png";
		pIcon.height=16;
		pIcon.width=16;
		var marker = new Marker(p,pIcon,new Title(title));
		_MapApp.addOverlay(marker);
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',title:'门楼牌号标记',split:true" style="width: 400px;">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false" style="height: 55px; background-color: #F4F4F4;">
					<div style="padding: 15px 20px;">
						<input id="dzmc-text" class="easyui-searchbox" name="DZMC" style="width: 320px; margin: 5px;" data-options="searcher:loadMlph,prompt:'门（楼）详址'"></input>
					</div>
				</div>
				<div data-options="region:'center',border:false" class="mlph-panel">
					<table id="mlphGrid">
						<thead>
							<tr>
								<th data-options="field:'DZMC',sortable:true" width="300">门（楼）详址</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<div id="v-center" data-options="region:'center'">
			
		</div>
	</div>

</body>
</html>