<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>
</head>
<body class="easyui-layout">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                    <div class="form-group">
                        <th>角色名称:</th>
                        <input name="webParams[role_name]" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                        <th>角色 编码:</th>
                        <input name="webParams[role_code]" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="log.loadRoleDataGrid">	</km:widgetTag>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="roleDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   		<%--  <km:widgetTag widgetRulevalue="log.enterLogDetail"></km:widgetTag>
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="assignedJy()">添加</a> --%>
 				
 				 <km:widgetTag widgetRulevalue="log.resourceAssign"></km:widgetTag>
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="enterResourceAssign()">分配资源</a>
 				
 				<km:widgetTag widgetRulevalue="log.widgetRuleAssign"></km:widgetTag>
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="enterWidgetRuleAssign()">分配权限</a>
 				
			</div>	
            </div>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/auth/role/loadRoleDataGrid";
    	  treeDrid = $("#roleDataGrid").datagrid({
				title:"系统角色管理",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:false,
				idField:'ROLE_ID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
					{field:'ROLE_NAME',title:'ROLE_NAME',width:100,sortable:true},
					{field:'ROLE_CODE',title:'ROLE_CODE',width:100,sortable:true},
					{field:'ROLE_DESC',title:'ROLE_DESC',width:110},
					{field:'ENABLED',title:'ENABLED',width:110},
					{field:'OPRATETIME',title:'OPRATETIME',width:110},
					{field:'AREA_ID',title:'AREA_ID',width:110},
					{field:'ORGANID',title:'ORGANID',width:110},
					{field:'ORGNA_NAME',title:'ORGNA_NAME',width:110},
					{field:'CREATEUSER',title:'CREATEUSER',width:110},
					{field:'CREATEUSER_NAME',title:'CREATEUSER_NAME',width:110}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#roleDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#roleDataGrid").datagrid(reload);
		$("#roleDataGrid").datagrid("clearSelections");
	}

	function enterResourceAssign(){
		 var rows = $("#roleDataGrid").datagrid("getSelections");
		 if(rows.length != 1){
			alertMsg.warn("请选择一条数据 ");return;
		 }
	   var url="${ctx}/auth/role/enterResourceAssign";
	   var param={role_id:rows[0].ROLE_ID};
		var options={title:"资源分配",width:1100,height:700, url:url,params:param,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
	}

	function enterWidgetRuleAssign(){
		 var rows = $("#roleDataGrid").datagrid("getSelections");
		 if(rows.length != 1){
			alertMsg.warn("请选择一条数据 ");return;
		 }
	   var url="${ctx}/auth/role/enterWidgetRuleAssign";
	   var param={role_id:rows[0].ROLE_ID};
		var options={title:"组件分配",width:1100,height:700, url:url,params:param,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
	}
    </script>
</body>
</html>