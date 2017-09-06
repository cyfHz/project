<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/includeall.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<title>社区居村委会管理</title>
</head>
<body style="overflow: hidden; " class="easyui-layout">
	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
	<t:DataDict code="DYCXSX" var="dycxsxDict"></t:DataDict>
	<div data-options="region:'west',title:'行政区划',split:true" style="width: 200px;">
		<ul id="xzqhTree"></ul>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
		   <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
					<form class="form-inline query-form form-horizontal" name="searchform" id="searchform">
					<input type="hidden" id="sjxzqy_dzbm" name="webParams['sjxzqy_dzbm']" value=""/>
							<div class="form-group">
								<th>社区居村委会名称:</th> <input type="text" name="webParams['sqjcwhmc']" class="easyui-textbox form-control" style="height:24px;"/>
							</div>
							<div class="form-group">
								<th>社区居村委会代码:</th>
								<input type="text" name="webParams['sqjcwhdm']" class="easyui-numberbox form-control" style="height:24px;"/>
							</div>
							<div class="form-group">
								<th>使用状态:</th>
								<input id="syztdm" dict="syztDict" name="webParams['syztdm']" class="form-control" style="height:24px;" />
							</div>
							<div class="form-group">
							<km:widgetTag widgetRulevalue="sqjcwh.loadSqjcwhDataGrid">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
							</km:widgetTag>	
							</div>			
				</form>	
			</div>
			<div data-options="region:'center',border:false">
			<table id="sqjcwhDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true"></table>
			<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
				<km:widgetTag widgetRulevalue="sqjcwh.addSqjcwh">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
			 	</km:widgetTag>
				<km:widgetTag widgetRulevalue="sqjcwh.updateSqjcwh">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="sqjcwh.revokeSqjcwh">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="revokeSqjcwh()">注销</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="sqjcwh.activateSqjcwh">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="activateSqjcwh()">启用</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="sqjcwh.enterDetailSqjcwh">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细信息</a>
				</km:widgetTag>
			</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script>
	$(function(){
		loadSqjcwhDataGrid();
		loadXzqhTree();
	});
	function loadSqjcwhDataGrid(){
		var loadurl = "${ctx}/psam/sqjcwh/loadSqjcwhDataGrid";
		$("#sqjcwhDataGrid").datagrid({
			title : "社区居村委会数据列表",
			nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
			rownumbers : true, fitColumns : false,//滚动条
			animate : false, striped : true,//设置为true将交替显示行背景。
			singleSelect : true,//为true时只能选择单行
			pagination : true,rownumbers : true,//行数
			pageSize : 10,
			url : loadurl,
			checkbox : false,
			idField : 'DZBM',//分页保留选中
			toolbar : "#toolbarDiv",
			frozenColumns : [ [ 
			//{field : 'DZBM',width : 20,checkbox : true},
			{field : 'SQJCWHDM',title : '社区居村委会代码',width : 105,sortable : true},
			{field : 'SQJCWHMC',title : '社区居村委会名称',width : 120,sortable : true},
			{field : 'BMJC',title : '别名简称',width : 110,sortable : true},
			{field : 'DZYSLXDM',title : '地址元素类型',width : 100, formatter:dictFormat('dzyslxDict')},
			{field:'SJXZQY_DZBM',title:'上级行政区划',width:110,sortable:true},
			//{field:'SJSQJCWH_DZBM',title:'所属社区居村委会代码',width:100,sortable:true},
			{field : 'DYCXSXDM',title : '地域属性',width : 60, formatter:dictFormat('dycxsxDict')},
			{field : 'SYZTDM',title : '使用状态',width : 60, formatter:dictFormat('syztDict')} 
			] ],
			columns : [ [ 
			 { field:'CXBJ',title:'撤销标记',width:70,formatter:formatCxbj },
			 {field : 'ZJF',title : '助记符',width : 100}, 
			 {field : 'SLRQ',title : '设立日期',width : 123}, 
			 {field : 'QYRQ',title : '启用日期',width : 123}, 
			 {field : 'TYRQ',title : '停用日期',width : 123}, 
			// {field : 'DJR',title : '登记人',width : 100},
			// {field : 'DJDW',title : '登记单位',width : 100}, 
			 {field : 'DJSJ',title : '登记时间',width : 123}, 
			// {field : 'XGR',title : '修改人',width : 100}, 
		// {field : 'XGDW',title : '修改单位',width : 100}, 
			 {field : 'GXSJ',title : '更新时间',width : 123}, 
			// {field : 'CXR',title : '撤销人',width : 100}, 
			 //{field : 'CXYY',title : '撤销原因',width : 100}, 
			 //{field : 'CXDW',title : '撤销单位',width : 100}, 
			// {field : 'CXSJ',title : '撤销时间',width : 123}
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
	function loaddata(reload) {
		var queryParams = $("#sqjcwhDataGrid").datagrid("options").queryParams;
		KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#sqjcwhDataGrid").datagrid(reload);
		$("#sqjcwhDataGrid").datagrid("clearSelections");
	}
	
	function loadXzqhTree(){
		$('#xzqhTree').tree({
			url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:true,showSqjcwh:false}),
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
				$('#sjxzqy_dzbm').val(node.id.substring(node.id.indexOf('_')+1));
				loaddata('load');
			}
		});
	}
//------------------------------------------------------------------
	function openeditDialog() {
		var rows = $("#sqjcwhDataGrid").datagrid("getSelections");
		if (rows.length !=1) {
			alertMsg.warn("请选择一条要编辑的数据");
			return;
		}
		if(rows[0].CXBJ=='1'){
			alertMsg.warn("该社区居村委会已被撤销或已停用、报废，不能修改");
			return;
		}
		var url = "${ctx}/psam/sqjcwh/enterUpdateSqjcwh";
		var param = { dzbm : rows[0].DZBM };
		var options = { title : "社区居村委会信息编辑页面",
			width : 650, height : 400,
			url : url, params : param,
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
	function openAddDialog() {
		var url = "${ctx}/psam/sqjcwh/enterAddSqjcwh";
		var options = {
			title : "社区居村委会添加页面",
			width : 650, height : 400,
			url : url, params : {},
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
	function revokeSqjcwh() {
		var rows = $("#sqjcwhDataGrid").datagrid("getSelections");
		if (rows.length !=1) {
			alertMsg.warn("请选择一个要注销的社区居村委会");
			return;
		}
		 if(rows[0].CXBJ == '1'){
			alertMsg.warn("该社区居村委会已注销！");
			return;
		} 
		//var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要注销该社区居村委会？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var url = "${ctx}/psam/sqjcwh/enterRevokeSqjcwh";
				var param = {
					dzbm : rows[0].DZBM
				};
				var options = {
					title : "社区居村委会注销",
					width : '800', height : '250',
					url : url, params : param,
					onClosed : function() {
						loaddata('reload');
					}
				};
				editDialog.open(options);
			}
		});
	}
	function  formatCxbj(value){
		if('1'==value){
			return '<span class="tag-normal">已撤销</span>';
		}else{
			return '<span class="tag-success">正常</span>';
		}
	}
	function activateSqjcwh() {
		var rows = $("#sqjcwhDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一个要启用的社区居村委会");
			return;
		}
	//	for (var i = 0; i < rows.length; i++) {
			if (rows[0].CXBJ == '0'||rows[0].CXBJ==null) {
				alertMsg.warn(rows[0].SQJCWHMC + "正在使用中,不需要启用！");
				return;
			}
	//	}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要启用该社区居村委会？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/sqjcwh/activateSqjcwh";
				var param = { "dzbm" : idArray };
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					loaddata('reload');
				});
			}
		});
	}
	function openDetailDialog() {
		var rows = $("#sqjcwhDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要查看的数据");
			return;
		}
		var url = "${ctx}/psam/sqjcwh/enterDetailSqjcwh";
		var param = {
			dzbm : rows[0].DZBM
		};
		var options = {
			title : "社区居村委会信息详情页面",
			width : 1000, height : 400,
			url : url, params : param,
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
</script>
</body>
</html>