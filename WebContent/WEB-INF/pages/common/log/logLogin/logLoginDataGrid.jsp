<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>
</head>
<body class="easyui-layout">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                    <div class="form-group">
                        <th>用户名:</th>
                        <input name="user_loginname" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                        <th>起始时间:</th>
                        <input name="start_time" type="text" class="easyui-datetimebox form-control">
                    </div>
                     <div class="form-group">
                        <th>截止时间:</th>
                        <input name="end_time" type="text" class="easyui-datetimebox form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="logLogin.loadLogDataGrid">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
						</km:widgetTag>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="logLoginDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
 				<%--  <km:widgetTag widgetRulevalue="logLogin.loadLogLoginDataGrid">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="assignedJy()">详细</a>
 				</km:widgetTag> --%>
			</div>	
            </div>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/common/log/logLogin/loadLogLoginDataGrid";
    	  treeDrid = $("#logLoginDataGrid").datagrid({
				title:"系统登陆管理",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:false,
				idField:'ID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
					//{field:'USER_ID',title:'用户ID',width:100,sortable:true},
					{field:'USER_NAME',title:'用户名',width:100,sortable:true},
					//{field:'ORGNA_ID',title:'组织机构ID',width:110},
					//{field:'LOGINTIME1',title:'登录时间',width:110},
					{field:'LOGINTIME',title:'登录时间',width:110},
					{field:'LOGINTYPE',title:'登录类型',width:110},
					{field:'IP',title:'登录IP',width:110},
					{field:'LOGINCLIENT',title:'终端类型',width:110},
					//{field:'MEMO',title:'MEMO',width:110},
					{field:'USER_TYPE',title:'用户类型',width:110,formatter:function(val,row){
						if ('0'==val){ return '管理员账号'; } else if('1'==val){ return '企业用户';}
					}},
					{field:'GPS_X',title:'GPS_X',width:110},
					{field:'GPS_Y',title:'GPS_Y',width:110},
					
					//{field:'LOGOUTTIME1',title:'LOGOUTTIME1',width:110},
					{field:'LOGOUTTIME',title:'退出时间',width:110},
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#logLoginDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#logLoginDataGrid").datagrid(reload);
		$("#logLoginDataGrid").datagrid("clearSelections");
	}
    </script>
</body>
</html>