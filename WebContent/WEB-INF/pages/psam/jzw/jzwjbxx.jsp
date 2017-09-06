<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
  <%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<SCRIPT src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></SCRIPT>
<style type="text/css">
.jlx-panel .pagination-info {
	display: none;
}
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
<body class="easyui-layout">
	<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	
	<!-- ############west行政区划+街路巷################# -->
	<div data-options="region:'west',title:'行政区划',split:true" style="width: 250px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false,collapsible:false" style="height: 360px;">
				<ul id="xzqhTree"></ul>
			</div>
			<div data-options="region:'center',title:'街路巷（小区）',split:true" class="jlx-panel" style="width: 220px;height: 260px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;">
						<div style="padding: 5px 25px;">
							<input id="jlxxqmc-searchbox" class="easyui-searchbox" name="JLXXQMC" searcher="loadJLXData" style="width: 180px" data-options="prompt:'街路巷名称'"></input>
						</div>
					</div>
					<div data-options="region:'center',border:false">
						<table id="jlxGrid" >
							<thead>
								<tr>
									<th data-options="field:'JLXXQMC'" width="240px">街路巷（小区）名称</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
			
		</div>
	</div>

		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;">
				<form class="form-inline query-form form-horizontal" id="jzwjbxxform">
				
				 <input type="hidden" id="ssjlxxq_dzbm" name="ssjlxxq_dzbm"  value=""></input>
				 <input type="hidden" id="sszdyjxzqy_dzbm" name="sszdyjxzqy_dzbm" value="" ></input>
				  <input type="hidden" id="sszdyjxzqy_type" name="sszdyjxzqy_type" value="" ></input>
				 
					<div class="form-group">
						建筑物名称: <input name="JZWMC" id="jzwmc" type="text" class="form-control"></input>
					</div>
					<div class="form-group">
						地址: <input name="DZMC" id="dzmc"  type="text" class="form-control"></input>
					</div>
					<div class="form-group">
						<a href="javascript:;" onclick="loadJzwjbxxData('load');" class="easyui-linkbutton" data-options="iconCls:'icon-search'" >查询</a>
					</div>
				</form>
			</div>
				<div data-options="region:'center',border:false">
				 <table id="jzwjbxxDataGrid" data-options="fit:true" ></table>
					<div id="toolbarDiv">
					<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjbxx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editJzwJbxx()">修改</a>
					</km:widgetTag>			
						<km:widgetTag widgetRulevalue="jzwjbxx.Cancellation">	
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="Cancellation()">注销</a>
					</km:widgetTag>	
					<km:widgetTag widgetRulevalue="jzwjbxx.Cancellation">	
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activeJzw()">启用</a>
					</km:widgetTag>			
					<km:widgetTag widgetRulevalue="jzwjbxx.loadJzwjgDyLcInfo">	
						<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="loadJzwjgDyLcInfo()">结构信息</a>
					</km:widgetTag>	
					<km:widgetTag widgetRulevalue="jzwjbxx.enterSeachJzwjg">	
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="loadJzwjg()">查看结构信息</a>
					</km:widgetTag>
						<km:widgetTag widgetRulevalue="jzwjbxx.marMark">	
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openMark()">坐标标注</a>
					</km:widgetTag>
					<km:widgetTag widgetRulevalue="qrcode.enterQRCodePage">	
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="viewQRCode()">查看二维码</a>
					</km:widgetTag>
					<km:widgetTag widgetRulevalue="qrcode.exportExcel">
						<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportType()">导出Excel</a>
					</km:widgetTag> 
					</div>
				</div>
			</div>
		</div>
 <div id="dlg_jzw_zb_mark_pgis"  class="easyui-dialog" style="width: 1000px; height: 500px; padding: 0px 0px" onClose="closeGuihuaDialog()" maximizable="true"  modal="true" closed="true" closable="true"  buttons="#dlg_jzw_zb_mark_buttons">
	<div id="dlg_jzw_zb_mark_buttons">
		  <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="mark();">坐标标注</a>&nbsp;
		  <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:$('#dlg_jzw_zb_mark_pgis').dialog('close')">关闭 </a>&nbsp;
	</div>
	<div id="mymap" class="map" style="height: 100%;"></div>
</div>

<div id="excel_or_select" class="easyui-dialog" style="width: 400px; height: 130px; padding: 10px 20px" closed="true">
			<div class="form-group">
				<label>Excel导出类别选择:</label>
				<select id="ExcelType" style="width: 280px;" class="easyui-combobox" editable="false">
					<option value="01">导出当前页</option>
					<option value="02">导出选择数据</option>
					<option value="03">根据查询条件导出数据</option>
				</select>
			</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jzwjbxx/km.jzwjbxx.mark.js"></script> 
<script type="text/javascript">
$(function() {
	 loadXzqhTree();
	 initJLXGrid();
	 initJzwjbxxDataGrid();
	});
//-----------------Xzqh---------------------------------------------------------------------------	
function loadXzqhTree() {
		$('#xzqhTree').tree({
			url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:true,showSqjcwh:true}),
			loadFilter: function(data) {
				if (!data || !data.rows || data.rows.length < 1) {
					return [];
				}
				var nodes = [];
				$(data.rows).each(function(i, row) {
					var node = {};
					node.id = row.NODETYPE+'_'+row.NODEID;
					node.text = row.NODETEXT;
					node.state = row.NODETYPE == 'SQJCWH' ? "open":"closed";
					nodes.push(node);
				});
				return nodes;
			},
			cascadeCheck:false,checkbox:false,
			onLoadSuccess: function() {$('#xzqhTree').tree('expand',$('#xzqhTree').tree('getRoot').target);},
			onClick: function(node) {
				var qhdm=node.id.substring(node.id.indexOf('_')+1);
				var xtype=node.id.substring(0,node.id.indexOf('_'));
				var params = {};
				params.SSZDYJXZQY_DZBM = qhdm;
				var jlxxqmc = $('#jlxxqmc-searchbox').val();
				if(jlxxqmc){
					params.JLXXQMC = jlxxqmc;
				}
				$("#jlxxqmc-searchbox").searchbox("setValue",""); 
				$("#ssjlxxq_dzbm").val("");
		
				//加载街路巷
				//$("#xzqhTreeValue").val(sszdyjxzqy_dzbm);
				//loadJLXData();
				$('#jlxGrid').datagrid('load', params);
				//加载最低行政区域
				$("#sszdyjxzqy_dzbm").val(qhdm);
				$("#sszdyjxzqy_type").val(xtype);
				loadJzwjbxxData('load');
			}
		});
	}
//----------------JLX-----------------------------------------------	
function initJLXGrid(){
	var loadurl="${pageContext.request.contextPath}/psam/jlx/loadJlxBMMCDataSet";
	$("#jlxGrid").datagrid({
			url:loadurl,
			fit:true,border:false,pagination:true,fitColumns:true,singleSelect:true,rownumbers: true,
			//frozenColumns :[[ {field : 'JLXXQMC',title : '街路巷名称',width : 240} ]],
			fixColumnSize:'JLXXQMC',
			loadFilter:function(data){
					var pager = $("#jlxGrid").datagrid("getPager");
					pager.pagination({
						showPageList : false,
						showRefresh : false
					});
					return data;
				}, onSelect:function(rowIndex,rowData){
					$("#ssjlxxq_dzbm").val(rowData.DZBM);
					loadJzwjbxxData("load");
				 }
			});
}
function loadJLXData(value,name) {
	var queryParams = $("#jlxGrid").datagrid("options").queryParams;
	//var jlxxqmc = $("#jlxxqmc-searchbox").searchbox("getValue");
	var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm").val();
	
	queryParams.SSZDYJXZQY_DZBM = sszdyjxzqy_dzbm;
	queryParams.JLXXQMC = value;
	$("#jlxGrid").datagrid('load');
}
//-----------------------JzwjbxxDataGrid----------------------------------------------------------------
function initJzwjbxxDataGrid() {
			var loadurl = "${ctx}/psam/jzwjbxx/jzwjbxxList";
			var treeDrid = $("#jzwjbxxDataGrid").datagrid({
				title : "建筑物基本信息",
				nowrap : true,rownumbers : true,fitColumns : false,animate : true,
				collapsible : false,striped : true,singleSelect : true,
				pagination : true,rownumbers : true,
				pageSize : 10,
				url : loadurl,
				checkbox : false,
				idField : 'DZBM',//分页保留选中
				toolbar : "#toolbarDiv",
				frozenColumns :[[
               // {field : 'DZBM',width : 20,checkbox : true}, 
                {field : 'JZWMC',title : '建筑物名称',width : 150}, 
                {field : 'DZMC',title : '地址名称',width : 270}, 
               // {field : 'BMJC',title:'别名简称',width:100},
                //{field : 'SSZDYJXZQY_MC',title:'最低一级行政区划',width:150},
                //{field : 'SSJLXXQ_MC',title:'所属街路巷（小区）',width:150}
			 ]],
				columns : [ [ 
				//{field : 'DYS',title : '单元数',width : 50}, 
				//{field : 'LCS',title : '楼层数',width : 50}, 
				{field : 'ZXDHZB',title:'中心点横坐标',width:100},
				{field : 'ZXDZZB',title:'中心点纵坐标',width:100},
				//{field : 'MDYMS',title : '每单元房间数',width : 80}, 
				{field : 'ENABLE',title: '是否可用', width:80,formatter:function(value,row){
					if('1'==value){
						return "<font color=green>可用<font>";
					}else if('0'==value){
						return "<font color=red>不可用<font>";
					}
				}},
				{field : 'ZAGLSSJWZRQMC',title : '所属警务责任区',width : 250},
				{field : 'DZZCZBZ',title : '地(住)址存在标识',width : 100,formatter:formatDZZZYBS},  
				{field : 'DZZZYBS',title : '地(住)址在用标识',width : 100,formatter:formatDZZZYBS}
				] ],
				loadFilter : function(data, parentId) {
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
		}
function formatDZZZYBS(value){
	if('0'==value){
		return '<span class="tag-normal">否</span>';
	}else{
		return '<span class="tag-success">是</span>';
	}
}
function loadJzwjbxxData(reload) {
	var queryParams = $("#jzwjbxxDataGrid").datagrid("options").queryParams;
	KMEASYUtil.genQueryParams(queryParams, $("#jzwjbxxform").form().serializeArray());
	$("#jzwjbxxDataGrid").datagrid(reload);
	$("#jzwjbxxDataGrid").datagrid("clearSelections");
}
//--------------------------------------------------------------------------------
		function openMark(){
			var rows = $("#jzwjbxxDataGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一个建筑物");
				return;
			}
			targetMarkJzwId=rows[0].DZBM;
			$("#dlg_jzw_zb_mark_pgis").dialog('open').dialog('setTitle','建筑物坐标点标注');
			onLoad();
		}
		
	/* 	function addJzwJbxx(){
			   var url="${ctx}/psam/jzwjbxx/enteraddJzwJbxx";
				var options={title:"建筑物基本信息添加页面",width:800,height:500, url:url,params:{},onClosed:function(){loadJzwjbxxData('reload');}};
				editDialog.open(options);
		 } */
		function editJzwJbxx(){
			 var rows = $("#jzwjbxxDataGrid").datagrid("getSelections");
			 if(rows.length == 0){
				alertMsg.warn("请选择要编辑的数据");return;
			 }
			 if(rows.length>1){
				alertMsg.warn("请选择一条数据");return;
			}
			 if(rows[0].ENABLE=='0'){
				 alertMsg.warn("该建筑物信息已注销，不允许修改！");return;
			 }
		    var url="${ctx}/psam/jzwjbxx/enterUpdateJzwjbxx";
		    var param={dzbm:rows[0].DZBM};
			var options={title:"建筑物基本信息编辑",width:800,height:500, url:url,params:param,onClosed:function(){loadJzwjbxxData('reload');}};
			editDialog.open(options);
	   }
		
	   
       function Cancellation( ){
    	   var rows= $("#jzwjbxxDataGrid").datagrid("getSelections");
    	   if(rows.length==0){
    			alertMsg.warn("请选择要操作的数据！");
    			return;
    	   }
    	   if(rows[0].ENABLE=='0'){
				 alertMsg.warn("该建筑物信息已注销！");return;
			 }
    	   var idArray =KMEASYUtil.rowsIdToArray(rows,"DZBM");
    		alertMsg.confirm("确定要对该数据进行操作吗？", {
    			cancelCall : function() {alertMsg.close();},
    			okCall : function() {alertMsg.close();
    					var ajaxUrl = "${ctx}/psam/jzwjbxx/Cancellation";
    					var param = {"ids" : idArray,"flag" : "0"};
    					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
    						if(data.statusCode!=200){
    					  		 alertMsg.error(data.message);
    					  		 return;
    						}
    						alertMsg.info(data.message);
    						 setTimeout(function(){
 								loadJzwjbxxData('reload');
 							 },2000);
    						//$("#jzwjbxxDataGrid").datagrid(reload);
    						
    					});
    				}
    			});
    	   
       }
       
       function activeJzw( ){
    	   var rows= $("#jzwjbxxDataGrid").datagrid("getSelections");
    	   if(rows.length==0){
    			alertMsg.warn("请选择要操作的数据！");
    			return;
    	   }
    	   if(rows[0].ENABLE=='1'){
				 alertMsg.warn("状态为启用！");return;
			 }
    	   var idArray =KMEASYUtil.rowsIdToArray(rows,"DZBM");
    		alertMsg.confirm("确定要对该数据进行操作吗？", {
    			cancelCall : function() {alertMsg.close();},
    			okCall : function() {alertMsg.close();
    					var ajaxUrl = "${ctx}/psam/jzwjbxx/activeJzw";
    					var param = {"ids" : idArray,"flag" : "1"};
    					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
    						if(data.statusCode!=200){
    					  		 alertMsg.error(data.message);
    					  		 return;
    						}
    						alertMsg.info(data.message);
    						 setTimeout(function(){
    								loadJzwjbxxData('reload');
    							 },2000);
    					
    						//$("#jzwjbxxDataGrid").datagrid(reload);
    						
    					});
    				}
    			});
    	   
       }
		function loadJzwjgDyLcInfo(){
		       var rows = $("#jzwjbxxDataGrid").datagrid("getSelections");
				 if(rows.length == 0){
					alertMsg.warn("请选择要编辑的数据");return;
				 }
				 if(rows.length>1){
					alertMsg.warn("请选择一条数据");return;
				}
				var url="${ctx}/psam/jzwjg/enterJzwjgDyLcInfo";

				var param={jzwid:rows[0].DZBM};
				var options={title:"建筑物结构信息编辑",width:980,height:600, url:url,params:param,onClosed:function(){loadJzwjbxxData('reload');}};
				editDialog.open(options);
		   }
		function loadJzwjg(){
			var wid = 1000;
			var hei = 600;
			var rows = $("#jzwjbxxDataGrid").datagrid("getSelections");
			var param={jzwId:rows[0].DZBM};
			 if(rows.length == 0){
				alertMsg.warn("请选择要编辑的数据");return;
			 }
			 if(rows.length>1){
				alertMsg.warn("请选择一条数据");return;
			}
			 //200：结构信息存在（正确） 301：结构信息不存在，300： 结构信息错误
			 KMAJAX.ajaxTodo("${ctx}/psam/jzwjg/checkIsHaveJg", param,
						function(data) {
							if (data.statusCode == 200) {//建筑物结构信息存在
								wid = window.top.document.body.clientWidth;
								hei = window.top.document.body.clientHeight;
								var url = "${ctx}/psam/jzwjg/enterSeachJzwjg";
								var options = {title:"建筑物结构信息编辑",width:wid,height:hei,url:url,params:param,onClosed:function(){}};
								editDialog.open(options);
							} else if (data.statusCode == 301) {//建筑物结构信息不存在
								$("#jzwid_tmp").val(rows[0].DZBM);
								var url = "${ctx}/psam/jzwjg/enterNoJg";
								var options = {title:"建筑物结构信息编辑",width:wid,height:hei,url:url,params:param,onClosed:function(){}};
								editDialog.open(options);
								//openJzwjgEditPage(jzwId,"01");
							}else if (data.statusCode == 300) {//建筑物结构错误
								alertMsg.warn(data.message);return;
							} else {
								alertMsg.warn("状态错误");return;
							}
						});	
			/*  var url="${ctx}/psam/jzwjg/enterSeachJzwjg";

				var options={title:"建筑物结构信息编辑",width:wid,height:hei, url:url,params:param,onClosed:function(){loadJzwjbxxData('reload');}};
				editDialog.open(options); */
		}
		function viewQRCode(){
			 var rows = $("#jzwjbxxDataGrid").datagrid("getSelections");
			 if(rows.length == 0){
				alertMsg.warn("请选择要查看的数据");return;
			 }
			 if(rows.length>1){
				alertMsg.warn("请选择一条数据");return;
			}
			 if(rows[0].ENABLE=='0'){
				 alertMsg.warn("该建筑物信息已注销，不允许查看！");return;
			 }
		    var url="${ctx}/psam/qrcode/enterQRCodePage";
		    var param={code:rows[0].DZBM,type:"_jzw"};
			var options={title:"建筑物基本信息二维码",width:350,height:360, url:url,params:param,onClosed:function(){loadJzwjbxxData('reload');}};
			editDialog.open(options);
		}
		
		/* function exportExcel(){
			$("#jzwjbxx_asdfgweqwewqewqdsfjkldjl").remove();
			var rows = $("#jzwjbxxDataGrid").datagrid("getRows");
			var idArray = KMEASYUtil.rowsIdToArray(rows,"DZBM");
			//console.log(idArray);
			$(document.body).append("<div id=\"jzwjbxx_asdfgweqwewqewqdsfjkldjl\"><form method=\"post\" id=\"exportExcel\" action=\"${ctx}/psam/qrcode/exportExcel\"><input type=\"hidden\" name=\"idList\" id=\"idList\" /><input type=\"hidden\" name=\"type\" value=\"_jzw\" /></form></div>");
			$("#idList").val(idArray);
			$("#exportExcel").submit(); 
		} */
		
		function exportExcel(type){
				$("#jzwjbxx_asdfgweqwewqewqdsfjkldjl").remove();
				if(type=="01"){
					var rows = $("#jzwjbxxDataGrid").datagrid("getRows");
					var idArray = KMEASYUtil.rowsIdToArray(rows,"DZBM");
					//console.log(idArray);
				}else if(type=="02"){
					var rows = $("#jzwjbxxDataGrid").datagrid("getSelections");
					var idArray = KMEASYUtil.rowsIdToArray(rows,"DZBM");
				}else if(type=="03"){
					var jzwmc=$("#jzwmc").val();
					var dzmc=$("#dzmc").val();
					var idArray = [jzwmc,dzmc];
					/* idArray[dzmc] = dzmc
					idArray[mlph] = mlph
					idArray[sjgsdwmc] = sjgsdwmc */
					//var idArray = {sjgsdwmc:sjgsdwmc,dzmc:dzmc,mlph:mlph};
				}
			$(document.body).append("<div id=\"jzwjbxx_asdfgweqwewqewqdsfjkldjl\"><form method=\"post\" id=\"exportExcel\" action=\"${ctx}/psam/qrcode/exportExcel\"><input type=\"hidden\" name=\"idList\" id=\"idList\" /><input type=\"hidden\" name=\"type\" value=\"_jzw\" /><input type=\"hidden\" name=\"orl\" id=\"orl\" /></form></div>");
			$("#idList").val(idArray);
			$("#orl").val(type);
			$("#exportExcel").submit();  
		}
			
function exportType(){
	$("#excel_or_select").dialog({
		autoOpen:false,
		modal:true,
		title:'Excel导出选择',
		position:'center',
		width:400,
		height:130,
		buttons:[{
			text:"确定",iconCls:"icon-ok",
			handler:function(){
				var type = $("#ExcelType").combobox("getValue");
				exportExcel(type);
				$("#excel_or_select").dialog("close");
			}
		},{
			text:"取消",iconCls:"icon-cancel",
			handler:function(){
				$("#excel_or_select").dialog("close");
			}
		}]
	}).dialog("open");
} 			
	//--------------------------------------------------------------------------------------------	

	</script>
</body>
</html>