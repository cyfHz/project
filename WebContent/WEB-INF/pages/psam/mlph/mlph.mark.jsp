  <!--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
-->
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml">
<head>
<title>建筑物信息采集</title>
<%@ include file="/static/meta/easyui.jsp" %>
<%@ include file="/static/meta/easyuipub.jsp" %>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <link href="${pageContext.request.contextPath}/static/css/autocomplete.css" rel="stylesheet" type="text/css"></link> --%>
<%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.tool.js"></script>
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.search.js"></script>
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.bianjie.js"></script>
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.query.js"></script>
<style type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	overflow: hidden;
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
	color: white;
	background-color: rgb(4, 211, 4);
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
	var uEzMap;
	$(function() {
		 uEzMap = new EzMap(document.getElementById("mymap"));
		 uEzMap.showMapControl();
		 loadMangeBianjie();
	});
	
	function mark(){
		uEzMap.changeDragMode('drawPoint', null, null,function(s) {
			var arr = s.split(",");
			if(!isInperminssion(arr[0],arr[1])){
				alertMsg.warn("所选点不在管辖区域内,请重现选取");
				return;
			}
			addMarker(s);
			alertMsg.confirm("经纬度为："+s+" 是否保存？", {
    			cancelCall : function() {alertMsg.close();},
    			okCall : function() {alertMsg.close();
    					var ajaxUrl = "${ctx}/psam/mlph/mark/save";
    					var param = {dzbm:'${YWLSH}',location:s};
    					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
    						if(data.statusCode!=200){
    					  		 alertMsg.error(data.message);
    					  		 uEzMap.clear();
    					  		 return;
    						}
    						alertMsg.info(data.message);
    					});
    				}
    			});
		});
	}
	function addMarker(s,title){
		removeAllMarker();
		var arr = s.split(",");
		var p = new Point(arr[0], arr[1]);
		var pIcon=new Icon();
		pIcon.image="${pageContext.request.contextPath}/static/images/marker.png";
		pIcon.height=16;
		pIcon.width=16;
		var marker = new Marker(p,pIcon,new Title(s));
		uEzMap.addOverlay(marker);
		voArray.push(marker);
	}
	var voArray=new Array();
	function removeAllMarker(){
		if(voArray!=null&&voArray.length>0){
			for(var i=0;i<voArray.length;i++){
				uEzMap.removeOverlay(voArray[i], false);
			}
			voArray=[];
		}
	}
	//----------------------------------------
	function isInperminssion(lon,lat){
	    	var pointX=lon;
	    	var pointY=lat;
	    	if(allPolygons==null){
	    		return false;	
	    	}
	    	for(var k=0;k<allPolygons.length;k++){
	    		if(""!=allPolygons[k]&&"null"!=allPolygons[k]&&"undefined"!=allPolygons[k]&&undefined!=allPolygons[k]){
		    		var flag=isPermissionbyPoint(allPolygons, pointX, pointY);
		        	if(!flag){
		        		 return false;	
		        	}
	    		}
	    		
	    	}
	    	return true;
	 }
	
	var allPolygons=new Array();//[eveyPolygons1,eveyPolygons1] /**/
	var orgInfoArray=new Array();//警务区的数组，或者组织机构的数组
	var colors=new Array("red","#FFA500","#FF00FF","purple","blue","yellow","green","brown","lime","magenta","olive","pink");

	var pPolygons=new Array();
	function loadMangeBianjie() {
		var ajaxUrl = "${pageContext.request.contextPath}/psam/jwq/loadManageBianjie";
		var param = {};
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}
				orgInfoArray=data.data;
			 if(orgInfoArray&&orgInfoArray!=null&&orgInfoArray.length>0){
				 for(var i=0;i<orgInfoArray.length;i++){
					var bjzbzx= orgInfoArray[i].bjzbz;
					if(PUBUtil.isStrHavaValue(bjzbzx)){
						 var everyOrgBjzb =new Array();
						 var everyOrgBjzb = bjzbzx.split(";");
						 for(var j = 0; j < everyOrgBjzb.length; j++){
							 if(PUBUtil.isStrHavaValue(everyOrgBjzb[j])){
								 allPolygons.push(everyOrgBjzb[j]);
								 var points = constructOneBianjie(everyOrgBjzb[j]);
								 var strPoints = points.join(",");
								 pPolygons.push(new Polygon(strPoints, "black", 3, 0.2, colors[i]));
								 showOneBianjie(pPolygons[i+j], orgInfoArray[i], j);
							 }
							
						 }
					}
				 }
				 
			 }
		});
	}
	function constructOneBianjie(data){
	   	 var  points=new Array();
	   	 var arrx =new Array();//纵坐标
	   	 arrx=data.split(",");
	   	 for (var i = 0; i < arrx.length-1; i=i+2) {
	   		 points.push(new Point(arrx[i],arrx[i+1]));
	   	 }
	      return points; 
	   }
	function showOneBianjie(pPolygon, orginfo, index) {
		uEzMap.addOverlay(pPolygon);
		 var mbr=pPolygon.getMBR();
		 var pointxx=mbr.centerPoint();
		 if(pointxx){
			 uEzMap.centerAndZoom(pointxx, 11);
		 }
		var mc = orginfo.mc;
		var bh = orginfo.bh;
		var type = orginfo.type;
		pPolygon.setExtendStatus(1, 100, 1, 2);
		pPolygon.setInterval(500);
		pPolygon.disableEdit();
	}
</script>
</body>
</html>