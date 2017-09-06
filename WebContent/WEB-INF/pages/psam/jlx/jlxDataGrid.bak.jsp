<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/includeall.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<title>街路巷（小区）管理</title>

</head>
<body style="overflow: hidden; " class="easyui-layout">

	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
	<t:DataDict code="SHPZDM" var="shpzdmDict"></t:DataDict>
	
 	<div data-options="region:'west',title:'行政区划',split:true" style="width: 200px;">
		<ul id="xzqhTree"></ul>
</div> 

	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
		   <div data-options="region:'north',border:false" style="height: 80px; background-color: #F4F4F4;">
					<form class="form-inline query-form form-horizontal" name="searchform" id="searchform">
					<input type="hidden" id="sszdyjxzqy_dzbm" name="webParams['sszdyjxzqy_dzbm']" value=""/>
							<div class="form-group">
								<th>街路巷（小区）名称:</th> <input type="text" name="webParams['jlxxqmc']" class="easyui-textbox form-control" style="height:24px;"/>
							</div>
							<div class="form-group">
								<th>街路巷（小区）代码:</th> 
								<input type="text" name="webParams['jlxxqdm']" class="easyui-textbox form-control" style="height:24px;"/>
							</div>
							<div class="form-group">
								<th>上级街路巷（小区）名称:</th> 
								<input type="text" name="webParams['ssjlxxq_mc']" class="easyui-textbox form-control" style="height:24px;"/>
							</div>
							<div class="form-group">
								<th>使用状态:</th>
								<input id="syztdm" dict="syztDict" name="webParams['syztdm']" class="form-control" style="height:24px;" data-options="editable:false"/>
							</div>
							<div class="form-group">
							<km:widgetTag widgetRulevalue="jlx.loadJlxDataGrid">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查询</a>
							</km:widgetTag>
							</div>
				</form>	
			</div>
			<div data-options="region:'center',border:false">
			<table id="jlxDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true"></table>
			<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
				<km:widgetTag widgetRulevalue="jlx.addJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="jlx.updateJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="jlx.revokeJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="revokejlx()">注销</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="jlx.activateJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="activatejlx()">启用</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="jlx.enterJlxPage">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细信息</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="jlx.applyUseJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openApplyDialog()">再次申请</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="jlx.reviewJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openReviewDialog('yes')">审批通过</a>
				</km:widgetTag>
				<km:widgetTag 	widgetRulevalue="jlx.reviewJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="openReviewDialog('no')">审批不通过</a>
				</km:widgetTag>
			</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script>
	var treegrid;
	$(function(){
		loadJlxDataGrid();
		loadXzqhTree();
	});
	
function loadJlxDataGrid(){
		var loadurl = "${ctx}/psam/jlx/loadJlxDataGrid";
		$("#jlxDataGrid").datagrid({
				title : "街路巷(小区)数据列表",
				nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers : true, fitColumns : false,//滚动条
				animate : true,
				//collapsible : true,//显示可折叠按钮
				striped : true, singleSelect : true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize : 10,
				url : loadurl,
				checkbox : true,
				idField : 'DZBM',//分页保留选中
				toolbar : "#toolbarDiv",
				frozenColumns : [ [ 
						//{field : 'DZBM',width : 20,checkbox : true},
						{field : 'JLXXQDM',title : '街路巷（小区）代码',width : 115,sortable : true},
						{field : 'JLXXQMC',title : '街路巷（小区）名称',width : 120,sortable : true},
						{field : 'BMJC',title : '别名简称',width : 100,sortable : true},
						{field : 'DZYSLXDM',title : '地址元素类型',width : 90,sortable : true,formatter:dictFormat('dzyslxDict')},
						{field : 'SSZDYJXZQY_MC',title:'所属最低行政区域',width:110},
					    {field:'SSJLXXQ_MC',title:'所属街路巷（小区）',width:100,sortable:true},
						{field : 'SYZTDM',title : '使用状态',width : 60,sortable : true,formatter:dictFormat('syztDict')},
					//	{field:'SHPZDM',title:'审批单位',width:60,sortable:true,formatter:dictFormat('shpzdmDict')},
						{field:'SPZT',title:'审批状态',width:110,sortable:true,
							formatter:function(value,row){
									if(0==value){return '<span class="tag-normal">未审核</span>';
								}else if(1==value){return '<span class="tag-fail">区县审核未通过</span>';
								}else if(2==value){return '<span class="tag-success">区县审核已通过</span>';
								}else if(3==value){return '<span class="tag-fail">市局审核未通过</span>';
								}else if(4==value){return '<span class="tag-success">市局审核已通过</span>';
								}else if(5==value){return '<span class="tag-success">审核已通过</span>';}
								else{return '<span class="tag-normal">未审核</span>';}
							}
						}
				] ],
				columns : 
				[ [ 
					{field:'CXBJ',title:'撤销标记',width:55,formatter:formatCxbj},
					{field : 'ZJF',title : '助记符',width : 100}, 
					{field : 'SLRQ',title : '设立日期',width : 123},
					{field : 'QYRQ',title : '启用日期',width : 123,sortable : true}, 
					{field : 'TYRQ',title : '停用日期',width : 123,sortable : true},
					{field : 'DJR',title : '登记人',width : 100}, 
					{field : 'DJDW',title : '登记单位',width : 100}, 
					{field : 'DJSJ',title : '登记时间',width : 123},
					{field : 'XGR',title : '修改人',width : 100},
					{field : 'XGDW',title : '修改单位',width : 100}, 
					{field : 'GXSJ',title : '更新时间',width : 123}, 
					{field : 'CXR',title : '撤销人',width : 100}, 
					{field : 'CXYY',title : '撤销原因',width : 100}, 
					{field : 'CXDW',title : '撤销单位',width : 100}, 
					{field : 'CXSJ',title : '撤销时间',width : 123} 
				] ],
			    rowStyler:function(index,row){
			    	if(row.SYZTDM==20||row.SYZTDM==40){
			    		return 'color: #5e5e5e;background-color: #E4E4E4;'
			    		}
			    	},
				onBeforeLoad : function() {
					$(this).datagrid("clearSelections");
				},
				loadFilter : function(data, parentId) {
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
}
function loadXzqhTree(){
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
		cascadeCheck:false,
		checkbox:false,
		onLoadSuccess: function() {
			$('#xzqhTree').tree('expand',$('#xzqhTree').tree('getRoot').target);
		},onClick: function(node) {
			$('#sszdyjxzqy_dzbm').val(node.id.substring(node.id.indexOf('_')+1));
			loaddata('load');
		}
	});
}
//----------------------------------------------------------
	function loaddata(reload) {
		var queryParams = $("#jlxDataGrid").datagrid("options").queryParams;
		KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#jlxDataGrid").datagrid(reload);
		$("#jlxDataGrid").datagrid("clearSelections");
	}
	function openeditDialog() {
		var rows = $("#jlxDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要修改的数据");
			return;
		}
		if(rows[0].CXBJ == '1'){
			alertMsg.warn("该街路巷（小区）已注销,不能修改！");
			return;
		}
		var url = "${ctx}/psam/jlx/enterUpdateJlx";
		var param = {dzbm : rows[0].DZBM};
		var options = {
			title : "街路巷（小区）信息编辑页面",
			width : 960, height : 400,
			url : url, params : param,
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
	function openAddDialog() {
		var url = "${ctx}/psam/jlx/enterAddJlx";
		var options = {
			title : "街路巷（小区）添加页面",
			width : 960, height : 400,
			url : url, params : {},
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
	function  formatCxbj(value){
		if('1'==value){
			return '<span class="tag-normal">已撤销</span>';
		}else{
			return '<span class="tag-success">正常</span>';
		}
	}
	function revokejlx() {
		var rows = $("#jlxDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要注销的街路巷（小区）");
			return;
		}
	/* 	if(rows[0].SPZT != '5' ||rows[0].SPZT==""){
			alertMsg.warn("请通过审批后进行注销！");
			return;
		} */
		if(rows[0].CXBJ == '1'){
			alertMsg.warn("该街路巷（小区）已注销！");
			return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要注销该街路巷（小区）？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var url = "${ctx}/psam/jlx/enterRevokeJlx";
				var param = {dzbm : rows[0].DZBM};

				var options = {
					title : "街路巷（小区）注销",
					width : '800',
					height : '250',
					url : url,
					params : param,
					onClosed : function() {
						loaddata('reload');
					}
				};
				editDialog.open(options);
			}
		});
	}
	function activatejlx() {
		var rows = $("#jlxDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要启用的街路巷（小区）");
			return;
		}
		if(rows[0].CXBJ == '0'){
			alertMsg.warn(rows[0].JLXXQMC+"正在使用中,不需要启用！");
			return;
		}
		if(rows[0].SPZT != '5'&&rows[0].SPZT!=""){
			alertMsg.warn("请通过审批后进行启用！");
			return;
		}
		for (i = 0; i < rows.length; i++) {
			if (rows[i].CXBJ != '1' || rows[i].SYZTDM == '10') {
				alertMsg.warn(rows[i].JLXXQMC + "正在使用中,不需要启用！");
				return;
			}
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要启用该街路巷（小区）？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/jlx/activateJlx";
				var param = {"dzbm" : idArray};
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					loaddata('reload');
				});
			}
		});
	}
	function openDetailDialog(){
		var rows = $("#jlxDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要查看的数据");
			return;
		}
		var url = "${ctx}/psam/jlx/enterDetailJlx";
		var param = {dzbm : rows[0].DZBM};
		var options = {
			title : "街路巷（小区）详情页面",
			width : 900,
			height : 400,
			url : url,
			params : param,
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
	function openApplyDialog(){
		var rows = $("#jlxDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要申请的街路巷（小区）");
			return;
		}
		for (i = 0; i < rows.length; i++) {
			if (rows[i].SPZT != '1'&&rows[i].SPZT!='3') {
				alertMsg.warn(rows[i].JLXXQMC + "不需要再次申请！");
				return;
			}
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要申请使用该街路巷（小区）？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/jlx/applyUseJlx";
				var param = {"dzbm" : idArray};
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					loaddata('reload');
				});
			}
		});
	}
	function openReviewDialog(spzt){
		var rows = $("#jlxDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要审批的街路巷（小区）");
			return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要审核该街路巷（小区）？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/jlx/reviewJlx";
				var param = { "dzbm" : idArray, "spzt":spzt };
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					$.messager.alert('提示',data.message);	
					loaddata('reload');
				});
			}
		});
	}
</script>
</body>
</html>