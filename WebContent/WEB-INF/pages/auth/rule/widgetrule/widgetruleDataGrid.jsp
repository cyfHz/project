<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>

</head>
<body style="overflow: hidden;" class="easyui-layout">
   <div data-options="region:'west',title:'资源列表',split:true" style="width: 250px;">
        <ul id="resourceTree" class="easyui-tree"></ul> <!-- -->
    </div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" >
		   <div data-options="region:'north',border:false" style="height: 0px; background-color: #F4F4F4;overflow: hidden;">
			<form class="form-inline query-form" name="searchform" id="searchform">
			 <input type="hidden" id="resourceid" name="webParams[resourceid]" />
		  	</form>
		</div>
	  <div data-options="region:'center',border:false">
		<table id="widgetruleDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			<km:widgetTag widgetRulevalue="widgetrule.">
		  		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openGuihuaDialog()">添加</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="widgetrule.">
		  		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openGuihuaDialog()">编辑</a>
			</km:widgetTag>
		</div>	
	  </div>	
	</div>
     
</div>
<script>

$(function(){
	initWidgetruleDataGrid();
	loadResourceTree();
});
//--------------------------------------------------------------------
function initWidgetruleDataGrid(){
	$("#widgetruleDataGrid").datagrid({
		title:"系统操作列表",
		nowrap: true,rownumbers: true, fitColumns: false,//滚动条
		animate:true,striped:true,singleSelect:true,pagination : true,rownumbers : true,pageSize: 10,
		url:"${ctx}/auth/widgetrule/loadWidgetRuleDataGrid",
		checkbox:false,
		idField:'RULE_ID',//分页保留选中
		toolbar:"#toolbarDiv",
		columns:[[
			{field:'AREA_ID',title:'所属领域',hidden:true},
			{field:'RULE_NAME',title:'功能名称',width:100},
		    {field:'RULE_DEFVALUE',title:'功能值',width:123},
			{field:'RULE_VALUES',title:'功能值2',width:123},
			{field:'RULE_CODE',title:'功能编码',width:200},
			{field:'ENABLED',title:'是否启用',width:200},
			{field:'OPRATETIME',title:'更新时间',width:200}
		]],
        onBeforeLoad:function(){ 
        	$(this).datagrid("clearSelections");
        },loadFilter:function(data,parentId){
			return KMCORE.ajaxDoneForServerError(data);
		}
	});
}
	
function loaddata(reload) { 
	var queryParams =$("#widgetruleDataGrid").datagrid("options").queryParams;
	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
	$("#widgetruleDataGrid").datagrid(reload);
	$("#widgetruleDataGrid").datagrid("clearSelections");
}

function loadResourceTree(){
	$('#resourceTree').tree({
		url: '${ctx}/auth/resource/loadResourceTree',
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
           $('#resourceid').val(node.id);
           loaddata('reload');
        },onLoadSuccess: function() {
           $('#resourceTree').tree('expand',$('#resourceTree').tree('getRoot').target);
        }
	});
}
</script>
</body>

</html>