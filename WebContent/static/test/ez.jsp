<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <title>easyMap JavaScript API Example: simple</title>
    <link href="draw.css" rel="stylesheet" type="text/css"></link>
    <script type="text/javascript">
    	document.focus = function(){};
    </script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/easyui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/test/elasticsearch.jquery.min.js"></script>
	<script type="text/javascript" src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js"></script>
   <script type="text/javascript" src="map.draw.util.js"></script>
    <script type="text/javascript">
    var client;
    var uEzMap;
    var jwqPoly = {};
    function onLoad(){
    	 client = new $.es.Client({
			hosts : '192.168.201.192:9200'
		});
	  //1） ********构造地图控件对象，用于装载地图
	  uEzMap = new EzMap(document.getElementById("mymap"));
	  //2）********初始化地图，并显示地图
	  uEzMap.initialize();
	  uEzMap.centerAndZoom(new Point(115.88964,36.78925), 18);
	  // 显示左侧导航工具条
	  uEzMap.showMapControl();
	  
	  uEzMap.addMapEventListener(EzEvent.MAP_ZOOMCHANGE, function(e){
			console.log(e.extent);
			if(e.zoomLevelPrevious < e.zoomLevel ){
				return;
			}
			if(e.extent.maxX>180|| e.extent.minX<-180||e.extent.maxY>90||e.extent.minY<-90){
				return;
			}
			
			client.search({
				index : 'psam',
				type:'jwq',
				size : 3000,
				body : {
					"query": {
					    "geo_shape": {
					      "BJZB": { 
					        "relation": "within",
					        "shape": { 
					          "type":   "envelope",
					          //maxY minX ,minY maxX
					          "coordinates": [ [e.extent.maxX,e.extent.minY], [e.extent.minX ,e.extent.maxY] ]
					        }
					      }
					    }
					  }
				}
			}, function(error, response) {
				response.hits.hits.forEach(function(hit) {
					var jwqbh = hit._source.JWQBH;
					if(!jwqPoly[jwqbh]){
						var uPoly = new Polygon(hit._source.BJZB.coordinates.join(';'),getRandomColor(), 3,0.6,getRandomColor());
						jwqPoly[jwqbh] = uPoly;
						uPoly.addListener("click",function(){uPoly.openInfoWindowHtml(hit._source.JWQBH+'<br>'+hit._source.JWQMC);});
						uEzMap.addOverlay(uPoly);
						//uEzMap.centerAtLatLng(uPoly.getPoints()[0]);
					}
					
				});
			});
	  });

		$('.drawing-toolbar').drawingTool({
			map : uEzMap,
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
	}
    
    function getRandomColor() {
        var letters = '0123456789ABCDEF'.split('');
        var color = '#';
        for (var i = 0; i < 6; i++ ) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }
    </script>
  </head> 
  <body onload="onLoad()">
    <div id="mymap" class="map" style="width:100%; height:100%;" ><div class="drawing-toolbar"></div></div>
  </body>
</html>