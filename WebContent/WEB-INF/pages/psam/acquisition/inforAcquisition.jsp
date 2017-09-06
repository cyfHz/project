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
<!---->
 <style type="text/css">
  v\:* {
        BEHAVIOR: url(#default#VML)
      }
.drawing-toolbarx {
	background-color:#F4F4F4;
	position: absolute;
	z-index: 100;
	bottom: auto;
	left: 5px;
	top: 18px;
	right: auto;
}
</style> 
</head>
<body id="body_layout" class="easyui-layout">
<%-- <input id="centerPoint" type="hidden" value="${centerPoint }"> --%>
	<div data-options="region:'west',title:'检索结果',split:true"  style="width: 225px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center',border:false">
						<table id="queryResGrid"></table>
					</div>
				</div>
			</div>
<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 37px;  padding:2px;overflow: hidden;">
			<div  style="text-align: center;height: 30px;width: 100%;padding: 3px;">
					<input type="text" id="seacrch_param"   name="seacrch_param"  class="easyui-searchbox" 
						  data-options="prompt:'   请在左侧选择要检索的类型',menu:'#seacrchboxmenu',searcher:doSearch"  style="height: 30px;width: 600px;"></input>
					<div id="seacrchboxmenu" style="width:140px">
						<div data-options="name:'jzwjbxx',iconCls:'icon-kmhouse24'">&nbsp;&nbsp;&nbsp;建筑物</div>
						<div data-options="name:'mlph',iconCls:'icon-kmhousemlph'">&nbsp;&nbsp;&nbsp;门楼牌号</div>
					</div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="matchPhrase" type="checkbox" name="matchPhrase" value="1"  style="height: 25px;">
					<span style="height: 25px;vertical-align: 20%;font-size: 14px">精确查询</span>
					
				</div>
				
				<input type="hidden" onclick="" id="dataInputx" /><input type="hidden"  id="dataInputy" />
			</div>
				<div data-options="region:'center',border:true" id="v-center">
				 	 <div id="mymap" class="map" style="width:100%; height:100%;" >
				 		<div class="drawing-toolbarx" id="drawing-toolbar" ></div>
				 	</div> 
				</div>
			</div>
			
<div id="jzwjg_type_select_dlg" class="easyui-dialog" style="width: 400px; height: 360px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<input type="hidden" id="jzwid_tmp" >
			<div class="form-group">
				<label>建筑物类型:</label>
				<select id="jzwjgType" style="width: 280px;" class="easyui-combobox" editable="false">
				<!--<option value="01">楼层规整楼房</option>
					<option value="02">楼层不规整楼房</option>
					<option value="01">平房(别墅、自建小楼、独立平房、四合院平房)</option>
					<option value="01">楼层规整楼房</option>
					<option value="01">筒子楼</option> -->
					<option value="01">规整楼房</option>
					<option value="03">平房(别墅、自建小楼、独立平房、四合院平房)</option>
					<!-- <option value="02">不规整楼房</option> -->
				</select>
			</div>
</div>

<div id="jzw_or_mlph_select" class="easyui-dialog" style="width: 400px; height: 130px; padding: 10px 20px" closed="true">
		<input type="hidden" id="jzwid_tmp" >
			<div class="form-group">
				<label>采集类别:</label>
				<select id="jzwOrMlphType" style="width: 280px;" class="easyui-combobox" editable="false">
					<option value="01">采集建筑物</option>
					<option value="02">采集门楼牌</option>
				</select>
			</div>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="openJzwjgEditPage()" style="width: 90px">确定</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#jzwjg_type_select_dlg').dialog('close')" style="width: 90px">关闭</a>
</div>

</div>
<script type="text/javascript">
var _MapApp=null;
var allPolygons=new Array();//警务区或者组织机构的---多个*多边形--坐标点的数组
var isGt=false;
var queryResGrid = undefined;
var voArray = new Array();
//--------------------警务区边界--------------------------------------------
var orgInfoArray = new Array();//警务区的数组，或者组织机构的数组
var colors = new Array("red", "#FFA500", "#FF00FF", "purple", "blue","yellow", "green", "brown", "lime", "magenta", "olive", "lightcyan","pink");
var pPolygons = new Array();//警务区或者组织机构的---多个*多边形 PGIS-Polygon的数组
var bianjieAjaxUrl = "${pageContext.request.contextPath}/psam/jwq/loadManageBianjie";

var queryGridAjaxUrl="${ctx}/psam/acquisition/search";

$(function() {initOnLoad();});
//--------------------CaiJi-----------------------------------------------------------    
	function xinXiCaiJiTypeSelect(str){
		if (!str) {
			alertMsg.warn("未正确选取点");
			return;
		}
		var xyArray = str.split(",");
		var pointX = xyArray[0];
		var pointY = xyArray[1];
		for (var k = 0; k < allPolygons.length; k++) {
			if (PUBUtil.isStrHavaValue(allPolygons[k])) {
				var flag = isPermissionbyPoint(allPolygons, pointX, pointY);
				if (!flag) {alertMsg.warn("未在指定区域内选取点");return;}
			}
		}
		
		$("#jzw_or_mlph_select").dialog({
			autoOpen:false,
			modal:true,
			title:'采集类别选择',
			position:'center',
			width:400,
    		height:130,
			buttons:[{
				text:"确定",
				iconCls:"icon-ok",
				handler:function(){
					var type = $("#jzwOrMlphType").combobox("getValue");
					var param = {"lon" : pointX,"lat" : pointY,"type":type};
					xinXiCaiJi(param,type);
					$("#jzw_or_mlph_select").dialog("close");
				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#jzw_or_mlph_select").dialog("close");
				}
			}]
		}).dialog("open");
	}
	function xinXiCaiJi(param,type) {
		var title = "";
		if(type == "01"){
			title = "建筑物基本信息采集";
		}else{
			title = "门楼牌基本信息采集";
		}
		var url = "${ctx}/psam/acquisition/enterAcquisition";
		var options = {
			title : title,
			width : 1000,height : 400,url : url,params : param,
			onClosed : function() {
				var returndata = "";
				returndata = window.top.$("#div_lookupbackvarValue_tmp").find('input[id="bringbacklookupbackvarValue_tmp"]').val();
				//returndata="70e7e820-a02e-495b-92ff-c794d51828e1#36.66575000#116.99243000#zht";
				if (!returndata || returndata == "undefined"|| returndata == undefined||returndata=="") {
					return;
				}
				window.top.$("#div_lookupbackvarValue_tmp").remove();
				var dataArrat = new Array();
				dataArrat = returndata.split("#");
				var jzwid = dataArrat[0];
				var lat = dataArrat[1];
				var lon = dataArrat[2];
				var jzwmc = dataArrat[3];
				var jzwlx = dataArrat[4];
				_MapApp.centerAtLatLng(new Point(lon, lat));
				var pTitle = new Title(jzwmc, 14, 7, "宋体", null, null,"red", 1);
				pTitle.setPoint(new Point(lon, lat));
				if(jzwlx&&jzwlx=='jzw'){
					var strMsg = "建筑物名称 :"+ jzwmc+ "<br/> 建筑物名:"+ jzwmc+ "<br/> <br/> <a href=\"javascript:void(0);\" onclick=\"enterJzwjgPage('"+ jzwid + "');\" >结构信息</a> &nbsp;&nbsp; <a href=\"javascript:void(0);\" onclick=\"editJzwJbxx('"+ jzwid + "');\" >建筑物信息修改</a>";
				}else{
					var strMsg = "门牌号名称 : "+ jzwmc+ "<br/><br/> 门牌号: "+ jzwmc+ "";
				}
				pTitle.addListener("click", function() {pTitle.openInfoWindowHtml(strMsg);});
				_MapApp.addOverlay(pTitle);
			}
		};
		editDialog.open(options);
	}
	//没有结构信息--进入结构采集页面;
	//存在结构信息，--直接进入结构展示页面
	function enterJzwjgPage(jzwId) {
		var param = {"jzwId" : jzwId};
		var wid = 1000;
		var hei = 600;
		KMAJAX.ajaxTodo("${ctx}/psam/jzwjg/checkIsHaveJg", param,
				function(data) {
					//200：结构信息存在（正确） 301：结构信息不存在，300： 结构信息错误
					if (data.statusCode == 200) {//建筑物结构信息存在
						wid = window.top.document.body.clientWidth;
						hei = window.top.document.body.clientHeight;
						var param = {"jzwId":jzwId};
						//var url = "${ctx}/psam/jzwjg/enterShowJzwjg";
						//var options = {title:"建筑物结构信息",width:wid,height:hei,url:url,params:param,onClosed:function(){}};
						//editDialog.open(options);
						var url =encodeURI("${ctx}/psam/jzwjg/enterShowJzwjg?jzwId="+jzwId);
						window.open(url,"_blank");
						//window.open(url,"建筑物结构信息",'height=1000, width=1000, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');  
					} else if (data.statusCode == 301) {//建筑物结构信息不存在
						$("#jzwid_tmp").val(jzwId);
						$("#jzwjg_type_select_dlg").dialog('open').dialog('center').dialog('setTitle','建筑物结构类型选择');
					} else if (data.statusCode == 300) {//建筑物结构信息错误
						alertMsg.error(data.message);return;
					/* 	alertMsg.confirm(data.message+"<br><br><br>是否进行结构信息维护？", {
				 			cancelCall : function() {alertMsg.close(); return false;},
				 			okCall : function() { 
				 				
				 	    	 }
				 		}); */
					}  else {
						alertMsg.warn(data.message);return;
						
					}
				});
	}
	
	//上图时 查询是否存在结构
	function selectJzwjg(jzwId){
		var num="";
		var param = {"jzwId" : jzwId};
		var url="${ctx}/psam/jzwjg/checkIsHaveJg";
		KMAJAX.ajaxTodo1("${ctx}/psam/jzwjg/checkIsHaveJg", param,
				function(data) {
					//200：结构信息存在（正确） 301：结构信息不存在，300： 结构信息错误
					if (data.statusCode == 200) {//建筑物结构信息存在
						
					} else if (data.statusCode == 301) {//建筑物结构信息不存在
						num=1;
					} else if (data.statusCode == 300) {//建筑物结构信息错误
					}  else {
					}
				});
		return num;
	}
	
	//点击Pgis 没有结构 直接弹出窗口
	function jzwJgLx(jzwId){
		$("#jzwid_tmp").val(jzwId);
		$("#jzwjg_type_select_dlg").dialog('open').dialog('center').dialog('setTitle','建筑物结构类型选择');
	}
	
	function openJzwjgEditPage(jzwId,jzwType) {
		if(!jzwId){var jzwId = $("#jzwid_tmp").val();}
		if(!jzwType){var jzwType=$("#jzwjgType").combobox("getValue");}
		var param = {"jzwId":jzwId,"jzwType":jzwType};
		var url = "${ctx}/psam/jzwjg/enterJzwjgAddPage";
		var wid = 980;var hei = 700;
		if("03"==jzwType){wid = 910;hei=380;}
		if("01"==jzwType){wid = 920;hei=600;}
		var options={title:"建筑物结构信息采集",width:wid,height:hei,url:url,params:param,onClosed:function(){
			$("#jzwjg_type_select_dlg").dialog('close');
		}};
		editDialog.open(options);
	}
//--------------------------EZMAP--initOnLoad------------------------------------------------------ 
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
	
	
	function editJzwJbxx(jzwid){
	    var url="${ctx}/psam/jzwjbxx/enterUpdateJzwjbxx";
	    var param={dzbm:jzwid};
		var options={title:"建筑物基本信息编辑",width:800,height:500, url:url,params:param,onClosed:function(){loadJzwjbxxData('reload');}};
		editDialog.open(options);
  }
</script>
</body>
</html>