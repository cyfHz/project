<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>建筑物信息采集</title>
<%@ include file="/static/meta/includeall.jsp"%>
<SCRIPT src="http://10.48.1.227:9081/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></SCRIPT>
<style type="text/css">
  v\:* {
        BEHAVIOR: url(#default#VML)
      }
</style>
    <script type="text/javascript">
    var _MapApp;	
    $(function() {
    	onLoad();
    });
      function onLoad(){
        // 构造地图类
        _MapApp = new EzMap(document.getElementById("mymap"));
        // 显示地图左侧比例尺控制控件

        _MapApp.showMapControl();
        // 设置地图对中中心
        _MapApp.centerAndZoom(new Point(116.3919, 39.9419), 8);
        
        // 添加鹰眼
        var uOverview=new OverView();// 构造一个鹰眼对象

        uOverview.width=200;// 设置鹰眼视窗的宽度

        uOverview.height=200;// 设置鹰眼视窗的高度

        uOverview.minLevel=5;// 设置鹰眼视窗中最小显示地图级别

        uOverview.maxLevel=12;// 设置鹰眼视窗中最大显示地图级别

        _MapApp.addOverView(uOverview);// 添加鹰眼对象
      }
    </script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',title:'门楼牌号',split:true" class="jlx-panel" style="width: 220px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north',border:false" style="height: 45px; background-color: #F4F4F4;">
						<div style="padding: 6px 15px;">
						<input id="mlphmc-searchbox" class="easyui-searchbox" name="MLPHMC"   data-options="prompt:'门楼牌号'"></input>
						</div>
					</div>
					<div data-options="region:'center',border:false">
						<table id="mlphGrid" class="easyui-datagrid">
							<thead>
								<tr><th data-options="field:'JLXXQMC'" width="240px">门楼牌号</th></tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
			
			<div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;">
				<form class="form-inline query-form" id="jzwjbxxform">
					<div class="form-group">
						<label>建筑物名称:sd</label> <input name="XZQHMC" type="text" class="form-control">
					</div>
					<div class="form-group">
						<label>地址:</label> <input name="XZQHDM" type="text" class="form-control">
					</div>
					<div class="form-group">
						<a href="javascript:;" onclick="loaddata('load');" class="easyui-linkbutton" data-options="iconCls:'icon-search'" >查询</a>
					</div>
				</form>
			</div>
				<div data-options="region:'center',border:true">
				 	<div id="mymap" class="map" style="width:100%; height:100%;" ></div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>