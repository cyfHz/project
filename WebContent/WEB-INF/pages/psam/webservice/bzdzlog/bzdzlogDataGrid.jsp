<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/static/meta/includeall.jsp" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html > 
<head>

<title></title>
</head>
<body class="easyui-layout">
 <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                    <div class="form-group">
                        <th>登录用户名:</th>
                        <input name="loginuser" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="log.loadRoleDataGrid">	</km:widgetTag>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					
                    </div>
                </form>
  </div>
            <div data-options="region:'center',border:false">
            <table id="bzdzLogDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			</div>	
            </div>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/webservice/bzdzlog/loadBzdzLogAll";
    	  treeDrid = $("#bzdzLogDataGrid").datagrid({
				title:"日志管理列表",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:true,
				idField:'loginid',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
				    //{field:'loginid',checkbox:true,width:110},
					{field:'LOGINUSER',title:'登录用户名',width:100,sortable:true},
					{field:'LOGINIP',title:'登录ip',width:100,sortable:true},
					{field:'LOGINTIME',title:'登录时间',width:110},
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
    function loaddata(reload) { 
	  	var queryParams =$("#bzdzLogDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#bzdzLogDataGrid").datagrid(reload);
		$("#bzdzLogDataGrid").datagrid("clearSelections");
	}
    </script>
</body>
</html>