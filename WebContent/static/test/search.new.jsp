<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>Search demo</title>
<link href="draw.css" rel="stylesheet" type="text/css"></link>
<style type="text/css">
.top {
	
}

.autocomplete-wrapper {
	margin: 44px auto 44px;
	max-width: 600px;
	z-index: 100;
}

.autocomplete-wrapper label {
	display: block;
	margin-bottom: .75em;
	color: #3f4e5e;
	font-size: 1.25em;
	z-index: 100;
}

.autocomplete-wrapper .text-field {
	padding: 0 15px;
	width: 500px;
	height: 40px;
	border: 1px solid #CBD3DD;
	font-size: 1.125em;
	z-index: 100;
	margin:0 auto;
}

.autocomplete-wrapper ::-webkit-input-placeholder {
	color: #CBD3DD;
	font-style: italic;
	font-size: 18px;
}

.autocomplete-wrapper :-moz-placeholder {
	color: #CBD3DD;
	font-style: italic;
	font-size: 18px;
}

.autocomplete-wrapper ::-moz-placeholder {
	color: #CBD3DD;
	font-style: italic;
	font-size: 18px;
}

.autocomplete-wrapper :-ms-input-placeholder {
	color: #CBD3DD;
	font-style: italic;
	font-size: 18px;
}

.autocomplete-suggestions {
	overflow: auto;
	border: 1px solid #CBD3DD;
	background: #FFF;
}

.autocomplete-suggestion {
	overflow: hidden;
	padding: 5px 15px;
	white-space: nowrap;
}

.autocomplete-selected {
	background: #F0F0F0;
}

.autocomplete-suggestions strong {
	color: #029cca;
	font-weight: normal;
}

.map {
	height: 500px;
}
</style>
</head>
<body>
	<div class="top">
		<div class="autocomplete-wrapper">
			<input type="text" class="text-field" id="search-text" placeholder="请输入地址名称..." />
			<%--<select id="precision">
			<option value="10">1.2m x 59.5cm</option>
			<option value="9">4.8m x 4.8m</option>
			<option value="8">38.2m x 19m</option>
			<option value="7">152.9m x 152.4m</option>
			<option value="6">1.2km x 609.4m</option>
			<option value="5">4.9km x 4.9km</option>
			<option value="4">39.1km x 19.5km</option>
			<option value="3">156.5km x 156km</option>
			<option value="2">1,252.3km x 624.1km</option>
			<option value="1">5,009.4km x 4,992.6km</option>
		</select><input type="text" id="max" value="10000"/><input type="text" id="radius" value="20"/>
		<button id="sum-btn">聚合检索</button> --%>
		</div>
	</div>
	<div>
		<div id="map" class="map">
			<div class="drawing-toolbar"></div>
		</div>
		<input type="hidden" id="dataInputx" value=""></input> <input type="hidden" id="dataInputy" value=""></input>
	</div>
	<script type="text/javascript" src="jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="elasticsearch.jquery.min.js"></script>
	<script type="text/javascript" src="jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="http://10.48.1.227:9081/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js"></script>

	<script type="text/javascript" src="map.draw.util.js"></script>

	<script type="text/javascript">
		$(function() {

			// 构造地图类
			_MapApp = new EzMap(document.getElementById("map"));
			// 显示地图左侧比例尺控制控件
			_MapApp.showMapControl();
			// 设置地图对中中心
			_MapApp.centerAndZoom(new Point(116.3919, 35.9419), 8);
			// 添加鹰眼
			var uOverview = new OverView();// 构造一个鹰眼对象

			uOverview.width = 200;// 设置鹰眼视窗的宽度

			uOverview.height = 200;// 设置鹰眼视窗的高度

			uOverview.minLevel = 5;// 设置鹰眼视窗中最小显示地图级别

			uOverview.maxLevel = 12;// 设置鹰眼视窗中最大显示地图级别

			_MapApp.addOverView(uOverview);// 添加鹰眼对象

			_MapApp.addMapChangeListener(function(map) {
				console.log(map.getBoundsLatLng());
			});

			// 'drawPolyline' 'pan' 'drawPoint' 'drawRect' 'drawPolygon' 'drawCircle'

			$('.drawing-toolbar').drawingTool({
				map : _MapApp,
				on_pan : function() {
					console.log(arguments)
				},
				on_drawPoint : function() {
					console.log(arguments)
				},
				on_drawRect : function() {
					console.log(arguments)
				},
				on_drawPolygon : function() {
					console.log(arguments)
				},
				on_drawCircle : function() {
					console.log(arguments)
				},
				on_drawPolyline : function() {
					console.log(arguments)
				}
			});

			$('#search-text').autocomplete({
				serviceUrl : '/psam/psam/elasticsearch/search',
				paramName : 'text',
				transformResult : function(response) {
					var resp = $.parseJSON(response);
					return {
						suggestions : $.map(resp.hits.hits, function(hit) {
							return {
								value : hit.source.DZMC,
								data : hit.source.LOCATION
							};
						})
					};
				},
				onSelect : function(suggestion) {
					var pIcon = new Icon();// 构造一个图标类
					pIcon.image = "/test/home.png";
					pIcon.height = 16;
					pIcon.width = 16;
					var strMsg = suggestion.value;

					var p = toPoint(suggestion.data);
					var marker = new Marker(p, pIcon);// 构造一个标记叠加对象
					marker.addListener("click", function() {
						marker.openInfoWindowHtml(strMsg);
					});// 添加点击事件的响应
					_MapApp.addOverlay(marker);
					_MapApp.centerAtLatLng(p);
				}
			});
			//$('#search-text').val()
			$('#search-text').bind("enterKey", function(e) {
				//var bounds = map.getBounds();
			});
			$('#search-text').keyup(function(e) {
				if (e.keyCode == 13) {
					$(this).trigger("enterKey");
				}
			});
		});

		function toPoint(str) {
			var arr = str.split(',');
			return new Point(arr[1], arr[0]);
		}
	</script>
</body>
</html>