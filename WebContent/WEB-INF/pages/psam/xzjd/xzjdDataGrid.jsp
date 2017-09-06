<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/includeall.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<title></title>
</head>
<body style="overflow: hidden; " class="easyui-layout">
<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
<div data-options="region:'west',title:'行政区划',split:true" style="width: 200px;">
	<ul id="xzqhTree"></ul>
</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
		   <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;">
					<form class="form-inline query-form form-horizontal"  name="searchform" id="searchform">
					<input type="hidden" id="sjxzqy_dzbm" name="webParams['sjxzqy_dzbm']" value="" />
					<div class="form-group">
						<th>乡镇街道名称:</th> 
						<input type="text" name="webParams['xzjdmc']" class="easyui-textbox form-control" style="height:21px;"/>
					</div>
					<div class="form-group">
						<th>乡镇街道代码:</th>
						<input type="text" name="webParams['xzjddm']" class="easyui-numberbox form-control" style="height:21px;"/>
						
					</div>
					<!-- <div class="form-group">
						<label>使用状态:</label>
						<input id="syztdm" dict="syztDict" name="webParams['syztdm']" class="form-control" style="height:21px;"/>
					</div> -->
					<div class="form-group">
					<km:widgetTag widgetRulevalue="xzjd.loadXzjdDataGrid">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					</km:widgetTag>
				</div>		
		  	</form>
		</div>
	  <div data-options="region:'center',border:false">
		<table id="xzjdDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			<km:widgetTag widgetRulevalue="xzjd.addXzjd">
		  		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="xzjd.updateXzjd">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="xzjd.revokeXzjd">
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="revokeXzjd()">注销</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="xzjd.activateXzjd">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="activateXzjd()">启用</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="xzjd.enterDetailXzjd">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细信息</a>
			</km:widgetTag>
			<%-- <km:widgetTag widgetRulevalue="xzjd.importdata">
				<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="openImportDialog()">导入Excel</a>
			</km:widgetTag> --%>
		</div>	
	  </div>	
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script>

</script>

<script>
$(function(){
	loadXzqhTree();
	loadXzjdDataGrid();
});

function loadXzjdDataGrid(){
		var loadurl="${ctx}/psam/xzjd/loadXzjdDataGrid";
	    $("#xzjdDataGrid").datagrid({
					title:"乡镇街道数据列表",
					nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
					rownumbers: true,
					fitColumns: false,//滚动条
					animate:true,
					//collapsible:true,//显示可折叠按钮
					striped:true,//设置为true将交替显示行背景。
					singleSelect:true,//为true时只能选择单行
					pagination : true,//分页
					rownumbers : true,//行数
					pageSize: 10,
					url:loadurl,
					checkbox:true,
					idField:'DZBM',//分页保留选中
					toolbar:"#toolbarDiv",
					frozenColumns : [ [
						//	{field:'DZBM',width:20,checkbox:true},
							{field:'XZJDDM',title:'乡镇街道代码',width:100,sortable:true},
							{field:'XZJDMC',title:'乡镇街道名称',width:130,sortable:true},
							{field:'BMJC',title:'别名简称',width:130,sortable:true},
							{field:'DZYSLXDM',title:'地址元素类型',width:100,sortable:true, formatter:dictFormat('dzyslxDict')},
							{field:'SJXZQY_DZBM',title:'上级行政区划',width:120,sortable:true},
							{field:'SYZTDM',title:'使用状态',width:70,sortable:true,formatter:dictFormat('syztDict')}
					]],
					columns:[[
						{field:'CXBJ',title:'撤销标记',width:100,formatter:formatCxbj},
						{field:'ZJF',title:'助记符',width:100},
						{field:'SLRQ',title:'设立日期',width:123,sortable:true},
						{field:'QYRQ',title:'启用日期',width:123,sortable:true},
						{field:'TYRQ',title:'停用日期',width:123,sortable:true},
						//{field:'DJR',title:'登记人',width:100},
						//{field:'DJDW',title:'登记单位',width:100},
						{field:'DJSJ',title:'登记时间',width:123},
						//{field:'XGR',title:'修改人',width:100},
						//{field:'XGDW',title:'修改单位',width:100},
						{field:'GXSJ',title:'更新时间',width:123},
						//{field:'CXR',title:'撤销人',width:100},
						//{field:'CXYY',title:'撤销原因',width:100},
						//{field:'CXDW',title:'撤销单位',width:100},
						//{field:'CXSJ',title:'撤销时间',width:123}
					]],
				    rowStyler:function(index,row){
				    	if(row.SYZTDM==20||row.SYZTDM==40){
				    		return 'color: #5e5e5e;background-color: #E4E4E4;'
				    		}
				    	},
			        onBeforeLoad:function(){ 
			        	$(this).datagrid("clearSelections");
			        },loadFilter:function(data,parentId){
						return KMCORE.ajaxDoneForServerError(data);
					}
				});
}
function loaddata(reload) { 
	var queryParams =$("#xzjdDataGrid").datagrid("options").queryParams;
	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
	$("#xzjdDataGrid").datagrid(reload);
	$("#xzjdDataGrid").datagrid("clearSelections");
}
function loadXzqhTree() {
	$('#xzqhTree').tree({
		url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:false,showSqjcwh:false}),
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
//---------------------------------------------------------------------
 function openeditDialog(){
	var rows = $("#xzjdDataGrid").datagrid("getSelections");
	if(rows.length != 1){
		alertMsg.warn("请选择一条要编辑的数据");return;
	}
	if(rows[0].CXBJ=='1'){
		alertMsg.warn("该乡镇街道已被撤销，不能修改");
		return;
	}
	var url="${ctx}/psam/xzjd/enterUpdateXzjd";
	var param={dzbm:rows[0].DZBM};
    var options={title:"乡镇街道信息编辑页面",width:700,height:400, url:url,params:param,onClosed:function(){loaddata('reload');}};
	editDialog.open(options);
	//alertMsg.info("打开编辑页面");
 }
 function openAddDialog(){
    var url="${ctx}/psam/xzjd/enterAddXzjd";
	var options={title:"乡镇街道信息添加页面",width:700,height:400, url:url,params:{},onClosed:function(){loaddata('reload');}};
	editDialog.open(options);
}
 function openDetailDialog(){
	 var rows = $("#xzjdDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要查看的乡镇街道");return;
		}
		var url="${ctx}/psam/xzjd/enterDetailXzjd";
		var param={dzbm:rows[0].DZBM};
	    var options={title:"乡镇街道详细信息",width:900,height:400, url:url,params:param,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
}
 function openImportDialog(){
	 var url="${ctx}/psam/xzjd/enterImportXzjd";
	    var options={title:"乡镇街道Excel导入",width:800,height:600, url:url,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
 }
	function  formatCxbj(value){
		if('1'==value){
			return '<span class="tag-normal">已撤销</span>';
		}else{
			return '<span class="tag-success">正常</span>';
		}
	}
 function revokeXzjd() {
	var rows = $("#xzjdDataGrid").datagrid("getSelections");
	if (rows.length != 1) {
		alertMsg.warn("请选择一条要注销的乡镇街道！");
		return;
	}
	if(rows[0].CXBJ == '1'){
		alertMsg.warn("该乡镇街道已注销！");
		return;
	}
	
	//var idArray =KMEASYUtil.rowsIdToArray(rows,"DZBM");
	alertMsg.confirm("确定要注销该乡镇街道？", {
		cancelCall : function() {alertMsg.close();},
		okCall : function() {
			alertMsg.close();
			var url="${ctx}/psam/xzjd/enterRevokeXzjd";
			var param={dzbm:rows[0].DZBM};
			var options={title:"乡镇街道注销",width:'800',height:'250', url:url,params:param,onClosed:function(){loaddata('reload');}};
			editDialog.open(options);
		}
	});
}
 function activateXzjd(){
		var rows = $("#xzjdDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要启用的乡镇街道");
			return;
		}
		/* for (var i = 0; i < rows.length; i++) {
			if (rows[i].CXBJ != '1' ) {
				alertMsg.warn(rows[i].XZJDMC + "不需要启用！");
				return;
			}
		} */
		if(rows[0].CXBJ == '0'|| rows[0].CXBJ==null){
			alertMsg.warn(rows[0].XZJDMC+"正在使用中,不需要启用！");
			return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "DZBM");
		alertMsg.confirm("确定要启用该乡镇街道？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/xzjd/activateXzjd";
				var param = { "dzbm" : idArray};
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					loaddata('reload');
				});
			}
		});
 }
</script>

</body>

</html>