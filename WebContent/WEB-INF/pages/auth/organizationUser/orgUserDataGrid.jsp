<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>
</head>
<body style="overflow: hidden;" class="easyui-layout">
   <div data-options="region:'west',title:'组织机构',split:true" style="width: 250px;">
        <ul id="zzjgTree" class="easyui-tree"></ul> <!-- -->
    </div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" >
		   <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
			<form class="form-inline query-form form-horizontal" name="searchform" id="searchform">
					<div class="form-group">
					 <form class="form-inline query-form" id="searchform">
		                  <input type="hidden" id="orgid" name="webParams[orgid]" />
		                  
		                    <div class="form-group">
		                        <th>用户名:</th>
		                        <input name="webParams['user_name']" type="text" class="easyui-textbox form-control">
		                    </div>
		                    
		                    <!-- <div class="form-group">
		                        <label>省份证号:</label>
		                        <input name="webParams['orgnaCode']" type="text"  value="" class="easyui-textbox form-control">
		                    </div> -->
		               <div class="form-group">
						<km:widgetTag widgetRulevalue="orgPgisArea.loadOrganizationGrid">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
						</km:widgetTag>
						</div>
						 </form>
					</div>		
		  	</form>
		</div>
	  <div data-options="region:'center',border:false">
		<table id="orgUserDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			<km:widgetTag widgetRulevalue="orgUser.loadOrgUserGrid">
		  		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="enterRoleAssign()">分配角色</a>
			</km:widgetTag>
		</div>	
	  </div>	
	</div>
     
</div>
<script>
$(function(){
	initOrgDataGrid();
	loadZzjgTree();
});
//--------------------------------------------------------------------
function initOrgDataGrid(){
	$("#orgUserDataGrid").datagrid({
		title:"系统用户列表",
		nowrap: true,rownumbers: true, fitColumns: false,//滚动条
		animate:true,striped:true,singleSelect:true,pagination : true,rownumbers : true,pageSize: 10,
		url:"${ctx}/auth/organizationUser/loadOrgUserGrid",
		checkbox:false,
		idField:'APPUSER_ID',//分页保留选中
		toolbar:"#toolbarDiv",
		columns:[[
		  	    {field:'AREA_ID',width:20,hidden:true},
			    {field:'USER_ID',title:'USER_ID',width:140},
			    {field:'USER_NAME',title:'USER_NAME',width:200},
				//{field:'USER_SEX',title:'USER_SEX',width:200},
				{field:'USER_SFZH',title:'USER_SFZH',width:140},
				{field:'SSFJ',title:'SSFJ',width:100},
			    {field:'SSPCS',title:'SSPCS',width:123},
				{field:'SSJWQ',title:'SSJWQ',width:123},
				{field:'SSJB',title:'SSJB',width:200},
				{field:'USER_MOBILE',title:'USER_MOBILE',width:200}
		]],
        onBeforeLoad:function(){ 
        	$(this).datagrid("clearSelections");
        },loadFilter:function(data,parentId){
			return KMCORE.ajaxDoneForServerError(data);
		}
	});
}
	
function loaddata(reload) { 
	var queryParams =$("#orgUserDataGrid").datagrid("options").queryParams;
	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
	$("#orgUserDataGrid").datagrid(reload);
	$("#orgUserDataGrid").datagrid("clearSelections");
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
           $('#orgid').val(node.id);
           loaddata('reload');
        },onLoadSuccess: function() {
           $('#zzjgTree').tree('expand',$('#zzjgTree').tree('getRoot').target);
        }
	});
}

function enterRoleAssign(){
	 var rows = $("#orgUserDataGrid").datagrid("getSelections");
	 if(rows.length != 1){
		alertMsg.warn("请选择一条数据 ");return;
	 }
   var url="${ctx}/auth/organizationUser/enterRoleAssign";
   var param={appuser_id:rows[0].APPUSER_ID};
	var options={title:"角色分配",width:1100,height:700, url:url,params:param,onClosed:function(){loaddata('reload');}};
	editDialog.open(options);
}

</script>
</body>

</html>