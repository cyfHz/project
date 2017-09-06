<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/static/meta/easyui.jsp" %>
<%@ include file="/static/meta/easyuipub.jsp" %>
<%@ include file="/static/meta/taglib.jsp" %>
	<%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<style type="text/css">
  v\:* {
        BEHAVIOR: url(#default#VML)
      }
html, body {
	height: 100%;
	margin: 0;
	padding: 0;

}

.top-bar {
	position: absolute;
	z-index: 10000;
	top: 5px;
	left: 20px;
}

.top-bar button {
	width: 105px;
	height: 40px;
	font-size: 20px;
	font-weight: bold;

	border: 1px solid rgb(13, 112, 13);
	border-radius: 13px;
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="top-bar">
		<button onclick="mark();">┼ 标记</button>
	</div>
	<div id="mymap" class="map" style="height: 100%;"></div>
	<script type="text/javascript">
		var _MapApp;
		var orgInfoArray = new Array();//警务区的数组，或者组织机构的数组
		var colors = new Array("red", "#FFA500", "#FF00FF", "purple", "blue","yellow", "green", "brown", "lime", "magenta", "olive", "lightcyan","pink");
		var bianjieAjaxUrl = "${pageContext.request.contextPath}/psam/jwq/loadManageBianjie";
		$(function() {
			setTimeout(function(){
				_MapApp = new EzMap(document.getElementById("mymap"));
			//uEzMap.centerAndZoom(new Point(118.07812, 35.96093), 11);
				var cccc="117.52978,36.7644,117.59667,36.75903,117.59033,36.70971,117.5249,36.72485,117.51952,36.74059,117.52978,36.7644";
			var polygonx=new Polygon(cccc, "black", 6,0.3, "red");
			_MapApp.addOverlay(polygonx);
			//loadManageBianjie(bianjieAjaxUrl);
			}, 300);
		});
		function initOnLoad() {
			if (_compatIE()) {
				try {
					setTimeout(function() {
						_MapApp = new EzMap(document.getElementById("mymap"));
						_MapApp.showMapControl();// 显示地图左侧比例尺控制控件
						_MapApp.centerAndZoom(new Point(118.67843, 36.6256), 4);
						var uOverview = new OverView();// 构造一个鹰眼对象
						uOverview.width = 200;// 设置鹰眼视窗的宽度
						uOverview.height = 200;// 设置鹰眼视窗的高度
						uOverview.minLevel = 5;// 设置鹰眼视窗中最小显示地图级别
						uOverview.maxLevel = 12;// 设置鹰眼视窗中最大显示地图级别
						_MapApp.addOverView(uOverview);// 添加鹰眼对象
					}, 500);
					initDrawToolBar();
					initDataGrid(queryGridAjaxUrl);
					setTimeout(function(){loadManageBianjie(bianjieAjaxUrl);},1000);
					setTimeout(function(){addMapStateChangeFunc();},500);
				} catch (e) {};
			} else if (_MapApp == null || _MapApp == undefined) {
				var pEle = document.getElementById("mymap");
				pEle.innerHTML = "<p>目前EzMap地图引擎不支持你使用的浏览器，我们当前支持如下浏览器类型:</p><ul><li><a href='http://www.microsoft.com/windows/ie/downloads/default.asp'>IE</a> 5.5+ (Windows)~~IE9</li>";
			}
		}
		function mark() {
			_MapApp.changeDragMode('drawPoint', null, null, function(s) {
				addMarker(s);
/* 				$.messager.confirm('确认', '经纬度为：' + s + ' 是否保存？', function(flag) {
					if (flag) {
						$.post('${pageContext.request.contextPath}/psam/mlph/mark/save', {YWLSH : '${YWLSH}',location : s})
						.success(function(data) {
							var msg = $.parseJSON(data);
							alert(msg);
							$.messager.show({title : '提示',msg : msg.message});
						});
					}
				}); */
				alertMsg.confirm('经纬度为：' + s + ' 是否保存？', {
					cancelCall : function() {alertMsg.close();},
					okCall : function() {
						alertMsg.close();
						var ajaxUrl="${ctx}/psam/mlph/mark/save";
						var param={YWLSH : '${YWLSH}',location : s};
						 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			     				KMCORE.ajaxDone(data);
			     		});
					}
				});
			
			});
		}
	
		
		function addMarker(s, title) {
			var arr = s.split(",");
			var p = new Point(arr[0], arr[1]);
			var pIcon = new Icon();
			pIcon.image = "${pageContext.request.contextPath}/static/images/marker.png";
			pIcon.height = 16;
			pIcon.width = 16;
			var marker = new Marker(p, pIcon, new Title(s));
			_MapApp.addOverlay(marker);
		}
		function loadManageBianjie(ajaxUrl) {
			var cccc="117.52978,36.7644,117.59667,36.75903,117.59033,36.70971,117.5249,36.72485,117.51952,36.74059,117.52978,36.7644";
			var polygonx=new Polygon(cccc, "black", 6,0.3, "red");
			_MapApp.addOverlay(polygonx);
			var mbr = polygonx.getMBR();
			var pointxx = mbr.centerPoint();
			_MapApp.centerAndZoom(pointxx, 15);
			return;
			var param = {};
			KMAJAX.ajaxTodo(ajaxUrl,param,function(data) {
				if (data.statusCode != 200) {
					alertMsg.error(data.message);
					return;
				}
			orgInfoArray = data.data;
			if (orgInfoArray && orgInfoArray != null&& orgInfoArray.length > 0) {
				for (var i = 0; i < orgInfoArray.length; i++) {
					var bjzbzx = orgInfoArray[i].bjzbz;
					if (PUBUtil.isStrHavaValue(bjzbzx)) {
						var everyOrgBjzb = new Array();
						var everyOrgBjzb = bjzbzx.split(";");
						for (var j = 0; j < everyOrgBjzb.length; j++) {
							if (PUBUtil.isStrHavaValue(everyOrgBjzb[j])) {
								var polygonx=new Polygon(everyOrgBjzb[j], "black", 6,0.3, "red");
								_MapApp.addOverlay(polygonx);
								if(i==orgInfoArray.length-1){
									var mbr = polygonx.getMBR();
									var pointxx = mbr.centerPoint();
									_MapApp.centerAndZoom(pointxx, 15);
								}
						   }
						}
				   }
				}
			}
		});
	 }
	</script>
</body>
</html>