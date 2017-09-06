<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!-- <SCRIPT src="http://10.48.1.227:9081/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></SCRIPT> -->
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<SCRIPT src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></SCRIPT>
<title></title>
<style type="text/css">
  v\:* {
        BEHAVIOR: url(#default#VML)
      }
    <!--[if gte IE 8]> 
    <script type="text/javascript">
        document.namespaces.add('vml', 'urn:schemas-microsoft-com:vml');
        document.createStyleSheet().cssText =
            'vml\\:fill, vml\\:path, vml\\:shape, vml\\:stroke' +
                '{ behavior:url(#default#VML); } ';
    </script>
    <![endif]-->
</style>
</head>
<body style="overflow: hidden;" class="easyui-layout">
<t:DataDict code="ORGNA_TYPE" var="orgTypeDict"></t:DataDict>
<t:DataDict code="ORGNA_PEROPERTY" var="orgPeropertyDict"></t:DataDict>
<t:DataDict code="ORGNA_LEVEL" var="orgLevelDict"></t:DataDict>
   <div data-options="region:'west',title:'组织机构',split:true" style="width: 250px;">
        <ul id="zzjgTree" class="easyui-tree"></ul> <!-- -->
    </div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" >
		   <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
			<form class="form-inline query-form " name="searchform" id="searchform">
					<div class="form-group">
					 <form class="form-inline query-form form-horizontal" id="searchform">
		                  <input type="hidden" id="porgid" name="webParams[porgid]" />
		                    <div class="form-group">
		                        <th>机构名称:</th>
		                        <input name="webParams['orgnaName']" type="text" class="easyui-textbox form-control">
		                    </div>
		                    
		                    <div class="form-group">
		                        <th>机构编号:</th>
		                        <input name="webParams['orgnaCode']" type="text"  value="" class="easyui-textbox form-control">
		                    </div>
		               
						<km:widgetTag widgetRulevalue="orgPgisArea.loadOrganizationGrid">
							<div class="form-group">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
							</div>
						</km:widgetTag>
						
						 </form>
					</div>		
		  	</form>
		</div>
	  <div data-options="region:'center',border:false">
		<table id="organizationDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			<km:widgetTag widgetRulevalue="orgPgisArea.addorgPgisArea">
		  		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openGuihuaDialog()">边界规划</a>
			</km:widgetTag>
		</div>	
	  </div>	
	</div>
	
	    <div id="dlg_pgis"  class="easyui-dialog" style="width: 1000px; height: 600px; padding: 0px 0px" modal="true" maximizable=true closed="true" buttons="#dlg-buttons">
	    	<div id="dlg-buttons">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="startGuihua();">添加区域</a>&nbsp;
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="savePolygonRes()">提交</a>&nbsp;
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeGuihuaDialog()">关闭 </a>&nbsp;
	    	
	    	<!-- - -->
	    	<input type="hidden" id="drawPolygonRes" value=""></input>
			<input type="hidden"  id="org_id" value=""></input>
			</div>
    		<div id="mymap" class="map" style="width:100%; height:100%;" ></div>
     </div>
     
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script>
var _MapApp;
var pPolygons= new Array();//多区域
var orgPgisArae;
var removedIndexes=new Array();//存放已经被删除的

$(function(){
	initOrgDataGrid();
	loadZzjgTree();
});

function openGuihuaDialog(){
	pPolygons.splice(0,pPolygons.length);//清空已边界数组
	removedIndexes.splice(0,removedIndexes.length);//清空已删除的id 数组
	pPolygons= new Array();
	removedIndexes=new Array();
	 var rows = $("#organizationDataGrid").datagrid("getSelections");
	   if(rows.length!=1){
		   alertMsg.warn("请选择要规划组织机构");
		   return;
	   }
	   if(rows[0].ENABLED=='0'){
			 alertMsg.warn("该组织机构已禁用！");
			 return;
	 }
	 clearMap();//清除标记
	$("#drawPolygonRes").val("");
    $("#org_id").val();
    var orgid=rows[0].ORGNA_ID;
    $("#org_id").val(orgid);
	$("#dlg_pgis").dialog('open').dialog('center').dialog('setTitle','警务区规划');
	if(!_MapApp){ onLoad(); }
	loadBianjieById();//加载选中组织区的 边界
}

//加载选中警组织区的 边界   
function loadBianjieById(){
	var ajaxUrl="${ctx}/psam/orgPgisArea/loadOrgPgisArea";
	var orgid=$("#org_id").val();
	var param={"orgid":orgid};
	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		if(data.statusCode!=200){
			 alertMsg.error(data.message);
			 return;
		}
		orgPgisArae=data.data;

		var mapArea=orgPgisArae.mapArea;
		var orgPgisAraeid=orgPgisArae.orgPgisAraeid;
		if(!mapArea||mapArea==""||mapArea==undefined||mapArea=="undefined"||mapArea=="null"||mapArea==null){
			alertMsg.warn("该警务区没有划分边界");
		}
		var zuobiao = new Array();
		zuobiao=mapArea.split(";");
		if(zuobiao&&zuobiao.length>0){
			 for(var i = 0; i < zuobiao.length; i++){
    			 var  points= constructOneBianjie(zuobiao[i]);
    			 var strPoints=points.join(",");
    			 pPolygons.push(new Polygon(strPoints,"black", 3,0.2,"red"));
    			 showOneBianjie(pPolygons[i],orgPgisArae,i);
    		 }
		}
	}); 
}
function showOneBianjie(pPolygon,orgPgisArae,index){
	_MapApp.addOverlay(pPolygon);
	
	var orgid=orgPgisArae.id;
	var orgname=orgPgisArae.orgname;
	var orgaddres=orgPgisArae.orgaddres;
	var orgcode=orgPgisArae.orgcode;
	var strMsg=" 组织机构名称 :"+orgname+"<br/>"
			   +"组织机构编号："+orgcode+"<br/>"
			   +"组织机构地址:"+orgaddres+"<br/>"
			   +"<br/>"
			   +"<a href=\"#\" onclick=\"doDelete("+index+");\" >删除</a>&nbsp;&nbsp;&nbsp;"
			   +"<a href=\"#\" onclick=\"doEdit("+index+");\" >编辑</a>";
	pPolygon.addListener("click",function(){
		pPolygon.openInfoWindowHtml(strMsg);
	 });
	  pPolygon.setExtendStatus(1,100,1,2);
 	  pPolygon.setInterval(500);
 	 // pPolygon.enableEdit();
 	 pPolygon.disableEdit();
 	
}
function doEdit(index){
	(pPolygons[index]).closeInfoWindowHtml();
	(pPolygons[index]).enableEdit();
}
//删除一个边界区域,
function doDelete(index){
	(pPolygons[index]).closeInfoWindowHtml();
	 _MapApp.removeOverlay(pPolygons[index],true);
	 removedIndexes.push(index);//已经删除的在数组removedIndexes中记录
}
function closeGuihuaDialog(){
	clearMap();
    $("#drawPolygonRes").val("");
 	$("#org_id").val();
	$("#dlg_pgis").dialog('close');
	
}
function clearMap() {
	if(_MapApp){
		_MapApp.clear();
	}
}
function startGuihua(){
	_MapApp.changeDragMode('drawPolygon',drawPolygonRes,org_id,guihuaCallback);
}
//规划回调函数:
//思路提示：  
//1.把新规划的区域push到数组pPolygons中，
//2. clearMap()把地图中标注全部删除，
//3. 重绘数组pPolygons 中的所有边界区域
//4.已经删除的在removedIndexes记录中的，在步骤3.中不再重绘。
function guihuaCallback(res){
	
	var zuobiao=res.split(",");
	if(!zuobiao||zuobiao.length==6){
		alertMsg.warn("未选择规划区域");
		return;
	}
	
	if(zuobiao&&zuobiao.length>0){
		var  points= constructOneBianjie(res);
		 var strPoints=points.join(",");
		 var polyon=new Polygon(strPoints,"black", 3,0.2,"red");
		 pPolygons.push(polyon);
		 clearMap();
		 for(var i = 0; i < pPolygons.length; i++){
			  var iscontinue=0;
			 for(var j=0;j<removedIndexes.length;j++){
    			 if(removedIndexes[j]==i){
    				 iscontinue++; 
    			 }
    		 }
			 if(iscontinue>0){
				 continue;
			 }
			 pPolygons[i].setFillColor("red");
			 pPolygons[i].setFillOpacity(0.2);
			 showOneBianjie(pPolygons[i],orgPgisArae,i);
			 pPolygons[i].disableEdit();
		 }
	}
}

function savePolygonRes(){
	var res=$("#drawPolygonRes").val();
	var orgid=$("#org_id").val();
	var resStr="";
	//保存时，已经被删除的 多边形，不会被拼接在提交的数据中
	 for(var i = 0; i < pPolygons.length; i++){
		 var iscontinue=0;
		 for(var j=0;j<removedIndexes.length;j++){
			 if(removedIndexes[j]==i){
				 iscontinue++; 
			 }
		 }
		 if(iscontinue>0){
			 continue;
		 }
		 var pp=pPolygons[i].getPoints();
		 if(pp=="NaN"||pp==undefined){
			 continue;
		 }
		 resStr=resStr+pp+";";
	 }
	
	if(resStr==""||resStr=="NAN,undefined"){
		var iscomfirm=alertMsg.confirm("该警务区没有边界数据，确认提交？", {
 			cancelCall : function() {alertMsg.close(); return false;},
 			okCall : function() { 
 				//String orgid,String mapArea
	     			var ajaxUrl="${ctx}/psam/orgPgisArea/saveOrgPgisArea";
	     	    	 var param={"orgid":orgid,"mapArea":resStr};
	     	    	 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
	     				KMCORE.ajaxDone(data);
	     				//closeGuihuaDialog();
	     				//loaddata('reload');
	     			});
 	    	 }
 		});
 }else{
	 var ajaxUrl="${ctx}/psam/orgPgisArea/saveOrgPgisArea";
	 var param={"orgid":orgid,"mapArea":resStr};
    	 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			KMCORE.ajaxDone(data);
			//closeGuihuaDialog();
			//loaddata('reload');
		});
 }
	
}
//--------------------------------------------------------------------
function initOrgDataGrid(){
	$("#organizationDataGrid").datagrid({
		title:"组织机构列表",
		nowrap: true,rownumbers: true, fitColumns: false,//滚动条
		animate:true,striped:true,singleSelect:true,pagination : true,rownumbers : true,pageSize: 10,
		url:"${ctx}/psam/orgPgisArea/loadOrganizationGrid",
		checkbox:false,
		idField:'ORGNA_ID',//分页保留选中
		toolbar:"#toolbarDiv",
		columns:[[
		    //{field:'ORGNA_ID',width:20,checkbox:true},
		    {field:'ORGNA_CODE',title:'机构编号',width:100},
		    {field:'ORGNA_NAME',title:'机构名称',width:200,sortable:true},
			{field:'ORGNA_JC',title:'结构简称',width:200,sortable:true},
		//	{field:'ORGNA_TYPE',title:'机构类型',width:123,formatter:dictFormat('orgTypeDict')},
			//{field:'ORGNA_PROPERTY',title:'机构性质',width:100,formatter:dictFormat('orgPeropertyDict')},
			//{field:'ORGNA_LEVEL',title:'机构级别',width:123,sortable:true,formatter:dictFormat('orgLevelDict')},
				{field:'ORGNA_ZIPCODE',title:'机构邮编',width:123},
			{field:'ORGNA_TEL',title:'机构电话',width:100},
		    {field:'ORGNA_FAX',title:'机构传真',width:123},
			{field:'ORGNA_EMAIL',title:'机构邮箱',width:123},
			{field:'ORGNA_ADDRESS',title:'机构地址',width:200},
		/* 	{field:'PORGNA_ID',title:'父组织机构ID',width:123},
			{field:'ENTORGNA_ID',title:'企业组织机构ID',width:100},
			{field:'AREA_ID',title:'应用领域ID',width:123,sortable:true,formatter:formatEnabled}, */
		/* 	{field:'ORGNA_VALIDITY_START',title:'有效时间',width:123,sortable:true},
			{field:'ORGNA_VALIDITY_END',title:'失效时间',width:123}, */
	/* 		{field:'CCH',title:'层次号',width:100},
		    {field:'CCBZ',title:'层次标志',width:123,sortable:true},
			{field:'HZBZ',title:'汇总标志',width:123,sortable:true},
			{field:'OPRATETIME',title:'操作时间',width:123},
			{field:'ORGNA_ORDER',title:'排列序号',width:123,sortable:true},
			{field:'ORGNA_HISCHILDNODE',title:'子节点',width:123,sortable:true}, */
			/* {field:'ORGNA_YXX',title:'有效性',width:123}, */
			{field:'SFZSJ',title:'是否直属局',width:100,
				formatter:function(value){ if("1"==value){ return "是"; }else{ return "否"; } }}
		 /*    {field:'BZ0',title:'备注',width:123,sortable:true},
			{field:'BZ1',title:'备注1',width:123,sortable:true},
			{field:'BZ2',title:'备注2',width:123} */
		]],
        onBeforeLoad:function(){ 
        	$(this).datagrid("clearSelections");
        },loadFilter:function(data,parentId){
			return KMCORE.ajaxDoneForServerError(data);
		}
	});
}
	
function loaddata(reload) { 
	var queryParams =$("#organizationDataGrid").datagrid("options").queryParams;
	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
	$("#organizationDataGrid").datagrid(reload);
	$("#organizationDataGrid").datagrid("clearSelections");
}

function loadZzjgTree(){
	$('#zzjgTree').tree({
		url: '${ctx}/auth/organization/loadOrganTree',
		cascadeCheck:false,
		loadFilter: function(data) {
			if (!data || !data.rows || data.rows.length < 1) {
				return [];
			}
			var nodes = [];
			$(data.rows).each(function(i, row) {
				var node = {};
				node.id = row.NODEID;
				node.text = row.NODETEXT;
				//node.state = row.NODETYPE == 'SQJCWH' ? "open":"closed";
				node.state = "closed"
				nodes.push(node);
			});
			return nodes;
		} , onClick: function(node) {
           $('#porgid').val(node.id);
           loaddata('reload');
        },onLoadSuccess: function() {
           $('#zzjgTree').tree('expand',$('#zzjgTree').tree('getRoot').target);
        }
	});
}
//--------------------------------------------------------------------
function onLoad(){
	if(_compatIE()){
        // 构造地图类
        _MapApp = new EzMap(document.getElementById("mymap"));
        // 显示地图左侧比例尺控制控件
        _MapApp.showMapControl();
        // 设置地图对中中心
        _MapApp.centerAndZoom(new Point(116.99655, 36.66345), 11);
        // 添加鹰眼
        var uOverview=new OverView();// 构造一个鹰眼对象
        uOverview.width=200;// 设置鹰眼视窗的宽度
        uOverview.height=200;// 设置鹰眼视窗的高度
        uOverview.minLevel=5;// 设置鹰眼视窗中最小显示地图级别
        uOverview.maxLevel=12;// 设置鹰眼视窗中最大显示地图级别
        _MapApp.addOverView(uOverview);// 添加鹰眼对象
	}else if(_MapApp==null){
		var pEle=document.getElementById("mymap");
		pEle.innerHTML="<p>目前EzMap地图引擎不支持你使用的浏览器，我们当前支持如下浏览器类型:</p><ul><li><a href='http://www.microsoft.com/windows/ie/downloads/default.asp'>IE</a> 5.5+ (Windows)</li>";
	}
  }
</script>
</body>

</html>