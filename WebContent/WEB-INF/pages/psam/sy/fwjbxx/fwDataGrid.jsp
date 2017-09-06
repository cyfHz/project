<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/includeall.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>房屋基本信息管理</title>
<style type="text/css">
.jlx-panel .pagination-info {
	display: none;
}
</style>
</head>
<body style="overflow: hidden;" class="easyui-layout">
	<t:DataDict code="FWLB" var="fwlb"></t:DataDict>
	<t:DataDict code="FWXZ" var="fwxz"></t:DataDict>
	<t:DataDict code="FWYT" var="fwyt"></t:DataDict>
	<t:DataDict code="fwlx" var="fwlx"></t:DataDict>

	<!-- ############west行政区划+街路巷################# -->
	<div data-options="region:'west',title:'行政区划',split:true" style="width: 250px;">
		<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false,collapsible:false" style="height: 260px;"><ul id="xzqhTree"></ul></div>
		<div data-options="region:'center',title:'街路巷（小区）',split:true" class="jlx-panel" style="width: 230px;height: 220px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north',border:false" style="height:35px; background-color: #F4F4F4;">
						<div style="padding: 5px 20px;">
							<input id="jlxxqmc-searchbox" class="easyui-searchbox" name="JLXXQMC" searcher="loadJLXData" style="width: 200px"  data-options="prompt:'街路巷名称'"></input>
						</div>
					</div>
					<div data-options="region:'center',border:false">
						<table id="jlxGrid" class="easyui-datagrid" >
							<thead>
								<tr><th data-options="field:'JLXXQMC'" width="240px">街路巷（小区）</th></tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
				<form class="form-inline query-form form-horizontal" name="searchform" id="searchform">
				
					<input type="hidden" id="ssjlxxq_dzbm" name="ssjlxxq_dzbm"  value=""/>
				 	<input type="hidden" id="sszdyjxzqy_dzbm" name="sszdyjxzqy_dzbm" value="" />
				  	<input type="hidden" id="sszdyjxzqy_type" name="sszdyjxzqy_type" value="" />
				  	
					<div class="form-group">
						<th>房主姓名:</th>
						 <input type="text" name="fzxm" class="easyui-textbox form-control" /> 
					</div>
						<div class="form-group">
						<th>房主身份证号码:</th>
						 <input type="text" name="fzsfzhm" class="easyui-textbox form-control" /> 
					</div>
					<div class="form-group">
						<th>房屋产权证号:</th>
						 <input type="text" name="fwcqzh" class="easyui-textbox form-control" style="height: 24px;" /> 
					</div>
<!-- 					<div class="form-group">
						<label>房屋类别:</label> 
						<input id="syztdm" dict="fwlb" name="fwlb" class="form-control"/>
					</div>
 -->					<div class="form-group">
						<km:widgetTag widgetRulevalue="fw.loadFwGridView">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadFwJbxxData('load');;">查询</a>
						</km:widgetTag>
					</div>
				</form>
			</div>
			<div data-options="region:'center',border:false">
			
				<table id="fwDataGrid" ></table>
				<div id="toolbarDiv" class="easyui-toolbar" style="padding: 4px; height: auto">
					<km:widgetTag widgetRulevalue="fw.enterUpdateFw">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
					</km:widgetTag>
					<km:widgetTag widgetRulevalue="fw.enterDetailFw">
						<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="opendetailDialog()">详细信息</a>
					</km:widgetTag>
					<km:widgetTag widgetRulevalue="qrcode.enterQRCodePage">	
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="viewQRCode()">查看二维码</a>
					</km:widgetTag>
					<km:widgetTag widgetRulevalue="qrcode.exportExcel">
						<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportExcel()">Excel导出</a>
					</km:widgetTag>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script>
$(function(){
	 loadXzqhTree();
	 initJLXGrid();
	 initFwDataGrid();
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
	 					node.state = row.NODETYPE == 'XZJD' ? "open":"closed";
	 					nodes.push(node);
	 				});
	 				return nodes;
	 			},
	 			cascadeCheck:false,checkbox:false,
	 			onLoadSuccess: function() {$('#xzqhTree').tree('expand',$('#xzqhTree').tree('getRoot').target);},
	 			onClick: function(node) {
	 				var qhdm=node.id.substring(node.id.indexOf('_')+1);
	 				var xtype=node.id.substring(0,node.id.indexOf('_'));
	 				
	 				$("#jlxxqmc-searchbox").searchbox("setValue",""); 
	 				$("#ssjlxxq_dzbm").val("");
	 				loadJLXData();
	 				//加载最低行政区域
	 				$("#sszdyjxzqy_dzbm").val(qhdm);
	 				$("#sszdyjxzqy_type").val(xtype);
	 				loadFwJbxxData('load');
	 			}
	 		});
	 	}
	
function initJLXGrid(){
	var loadurl="${pageContext.request.contextPath}/psam/jlx/loadJlxBMMCDataSet";
	$("#jlxGrid").datagrid({									
			url:loadurl,
			fit:true,border:false,pagination:true,fitColumns:true,singleSelect:true,rownumbers: true,
			fixColumnSize:'JLXXQMC',
			loadFilter:function(data){
			var pager = $("#jlxGrid").datagrid("getPager");
			pager.pagination({showPageList : false,showRefresh : false});
				return data;
			}, onSelect:function(rowIndex,rowData){
				$("#ssjlxxq_dzbm").val(rowData.DZBM);
					loadFwJbxxData("load");
				}
			});
}
function loadJLXData(value,name) {
	var queryParams = $("#jlxGrid").datagrid("options").queryParams;
	var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm").val();
	queryParams.SSZDYJXZQY_DZBM = sszdyjxzqy_dzbm;
	queryParams.JLXXQMC = value;
	$("#jlxGrid").datagrid('load');
}
//------------------------------------------------------------------------------
function initFwDataGrid(){
	var loadurl = "${pageContext.request.contextPath}/psam/fw/loadFwGridGrid";
	 $("#fwDataGrid").datagrid({
		title : "房屋基本信息数据列表",
		fit:true,
		nowrap : true,rownumbers : true,fitColumns : false,//滚动条
		animate : true,collapsible : false,striped : true,singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,pageSize : 10,
		url : loadurl,
		checkbox : false,
		idField : 'FWID',//分页保留选中
		toolbar : "#toolbarDiv",
		frozenColumns : [ [
         //{field : 'FWID',width : 20,checkbox : true}, 
        // {field : 'FWBH',title : '房屋编号',width : 115}, 
        // {field : 'XZQHMC',title : '所属省市区',width : 115},
        // {field : 'FZXM',title : '房主姓名',width : 123},
       //  {field : 'FZSFZHM',title : '房主身份证号码',width : 123}, 
 
	] ],
	columns : [ [ 
                 {field : 'FWID',width : 20,hidden:true},
	             {field : 'FZXM',title : '房主姓名',width : 123},
	             {field : 'FZSFZHM',title : '房主身份证号码',width : 123}, 
	             //{field : 'JLXXQMC',title : '街路巷小区',width : 120},
	           //  {field : 'JZWMC',title : '所在建筑物',width : 120},
	            // {field : 'FJH',title : '房间号',width : 100}, 
	           //  {field : 'FJMC',title : '房间名称',width : 80},
	             {field : 'FWCQZH',title : '房屋产权证号',width : 123},
	             {field : 'SFCZFW',title : '是否出租房屋',width : 90,formatter:formatSfczfw},
	             {field : 'FWDZ',title : '房屋地址详细地址',width : 200},
	             {field : 'ZXZT',title : '注销状态',width : 80,formatter:formatZxzt},
	         	{field : 'HX',title : '户型',width : 60},              
	    		{field : 'FWJS',title : '房屋间数',width : 60},
	    		{field : 'FWMJ',title : '房屋面积',width : 80}, 
	             {field : 'FWYT',title : '房屋用途',width : 60,formatter:dictFormat('fwyt')}, 
	             {field : 'FWLX',title : '房屋类型',width : 60,formatter:dictFormat('fwlx')} ,
	     	    {field : 'FWLB',title : '房屋类别',width : 80,formatter:dictFormat('fwlb')},
	    	    {field : 'FWXZ',title : '房屋性质',width : 80,formatter:dictFormat('fwxz')},
	     	   {field : 'DJSJ',title : '登记时间',width : 123}
	          
	          ]],
	// columns : [ [ 
	   // {field : 'FJH',title : '房间号',width : 100}, 
	   //// {field : 'FWDZ',title : '房屋地址详细地址',width : 90}, 
	  //  {field : 'FWLB',title : '房屋类别',width : 110,formatter:dictFormat('fwlb')},
	   // {field : 'FWXZ',title : '房屋性质',width : 100,formatter:dictFormat('fwxz')},
		//{field : 'HX',title : '户型',width : 60},              
		//{field : 'FWJS',title : '房屋间数',width : 55},
	//	{field : 'FWMJ',title : '房屋面积',width : 100}, 
		//{field : 'FWCQZH',title : '房屋产权证号',width : 123},
		//{field : 'FZXM',title : '房主姓名',width : 123},
		//{field : 'FZSFZHM',title : '房主身份证号码',width : 123}, 
		//{field : 'FZLXDH',title : '房主联系电话',width : 100},
		//{field : 'SFCZFW',title : '是否出租房屋',width : 100,formatter:formatSfczfw},
		//{field : 'TGRXM',title : '托管人姓名',width : 123},
		//{field : 'TGRSFZHM',title : '托管人身份证号码',width : 100}, 
	//	{field : 'TGRLXDH',title : '托管人联系电话',width : 100}, 
		//{field : 'YFZGX',title : '与房主关系',width : 123}, 
		//{field : 'FWSSDWBM',title : '房屋所属单位编码',width : 100}, 
		//{field : 'FWSSDWMC',title : '房屋所属单位名称',width : 100},
		//{field : 'SDPCS',title : '属地派出所',width : 100},
		//{field : 'JWZRQ',title : '警务责任区',width : 123}, 
		//{field : 'BZ',title : '备注',width : 123}, 
		//{field : 'ZXZT',title : '注销状态',width : 123,formatter:formatZxzt},
		//{field : 'ZXRQ',title : '注销日期',width : 123},
	/* 	{field : 'DELTAG',title : '删除标记',width : 123}, 
		{field : 'DELTIME',title : '删除时间',width : 123}, */
		//{field : 'DJR',title : '登记人',width : 123}, 
		//{field : 'DJDW',title : '登记单位',width : 123}, 
		//{field : 'DJRMC',title : '登记人名称',width : 123},
		//{field : 'DJDWMC',title : '登记单位名称',width : 123},
		//{field : 'DJSJ',title : '登记时间',width : 123},
		//{field : 'XGR',title : '修改人',width : 123}, 
		//{field : 'XGDW',title : '修改单位',width : 123},
		//{field : 'XGRMC',title : '修改人名称',width : 123}, 
		//{field : 'XGDWMC',title : '修改单位名称',width : 123},
		//{field : 'GXSJ',title : '更新时间',width : 123}, 
		//{field : 'MOVESIGN',title : 'MOVESIGN',width : 123}, 
		//{field : 'SBID',title : '设备ID',width : 123}, 
		//{field : 'LRJS',title : '录入角色',width : 123,formatter:formatLrjs}, 
		//{field : 'LRFS',title : '录入方式',width : 123,formatter:formatLrfs}, 
		//{field : 'LRWL',title : '录入网络',width : 123,formatter:formatLrwl}
		//] ],
		 rowStyler:function(index,row){
		    	if(row.ZXZT==1){ return 'color: #5e5e5e;background-color: #E4E4E4;'}
		 },
		onBeforeLoad : function() {
			$(this).datagrid("clearSelections");
		},
		loadFilter : function(data, parentId) {
			return KMCORE.ajaxDoneForServerError(data);
		}
	});
}
	function loadFwJbxxData(reload) {
		var queryParams = $("#fwDataGrid").datagrid("options").queryParams;
		KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#fwDataGrid").datagrid(reload);
		$("#fwDataGrid").datagrid("clearSelections");
	}
	function openAddDialog(){
		
	}
	function formatSfczfw(value){
		if('0'==value){
			return '否';
		}else{
			return '是';
		}
	}
	function formatLrjs(value){
		if('0'==value){
			return '<span class="tag-normal">民警</span>';
		}else{
			return '<span class="tag-success">社会积极力量</span>';
		}
	}
	function formatZxzt(value){
		if('0'==value){
			return '<span class="tag-success">正常</span>';
		}else{
			return '<span class="tag-normal">已注销</span>';
		}
	}
	function formatLrfs(value){
		if('0'==value){
			return '<span class="tag-normal">后台系统</span>';
		}else{
			return '<span class="tag-success">移动终端</span>';
		}
	}
	function formatLrwl(value){
		if('A'==value){
			return '<span class="tag-normal">公安网</span>';
		}else{
			return '<span class="tag-success">互联网</span>';
		}
	}
	
	function openeditDialog(){
		var rows = $("#fwDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要编辑的数据");return;
		}
		var url="${ctx}/psam/fw/enterUpdateFw";
		var param={fwid:rows[0].FWID};
	    var options={title:"房屋信息编辑页面",width:1000,height:400, url:url,params:param,onClosed:function(){loadFwJbxxData('reload');}};
		editDialog.open(options);
	}
	
	function opendetailDialog(){
		var rows = $("#fwDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要编辑的数据");return;
		}
		var url="${ctx}/psam/fw/enterDetailFw";
		var param={fwid:rows[0].FWID};
	    var options={title:"房屋信息详细页面",width:1000,height:400, url:url,params:param,onClosed:function(){loadFwJbxxData('reload');}};
		editDialog.open(options);
	}
	function viewQRCode(){
		var rows = $("#fwDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要编辑的数据");return;
		}
	    var url="${ctx}/psam/qrcode/enterQRCodePage";
	    var param={code:rows[0].FWID,type:"__fw"};
		var options={title:"房屋基本信息二维码",width:350,height:360, url:url,params:param,onClosed:function(){loadJzwjbxxData('reload');}};
		editDialog.open(options);
	}
	
	function exportExcel(){
		$("#fwDataGrid_asdfg").remove();
		var rows = $("#fwDataGrid").datagrid("getRows");
		var idArray = KMEASYUtil.rowsIdToArray(rows,"FWID");
		//console.log(idArray);
		$(document.body).append("<div id=\"fwDataGrid_asdfg\"><form method=\"post\" id=\"exportExcel\" action=\"${ctx}/psam/qrcode/exportExcel\"><input type=\"hidden\" name=\"idList\" id=\"idList\" /><input type=\"hidden\" name=\"type\" value=\"__fw\" /></form></div>");
		$("#idList").val(idArray);
		$("#exportExcel").submit(); 
	}
	</script> 
</body>
</html>