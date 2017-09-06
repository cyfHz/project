<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>
</head>
<body class="easyui-layout">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form" id="searchform">
                    <div class="form-group">
                        <label>描述内容:</label>
                        <input name="webParams[describe]" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                        <label>状态:</label>
                        <input name="webParams[status]" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="log.loadDzDataSyncBizDataGrid">	</km:widgetTag>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="DzDataSyncBizDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   		<%--  <km:widgetTag widgetRulevalue="log.enterLogDetail"></km:widgetTag>
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="assignedJy()">添加</a> --%>
 				
 				 <km:widgetTag widgetRulevalue="log.resourceAssign"></km:widgetTag>
 				   <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
 				<%-- <km:widgetTag widgetRulevalue="log.widgetRuleAssign"></km:widgetTag> --%>
		         <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
		         
		         <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteDzDataSync()">删除</a>
 				
			</div>	
            </div>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/datasync/dzDataSyncBiz/loadDataGrid";
    	  treeDrid = $("#DzDataSyncBizDataGrid").datagrid({
				title:"系统角色管理",
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
					{field:'LOGICMETHOD',title:'业务逻辑方法',width:200,sortable:true},
					{field:'STATUS',title:'状态',width:80,sortable:true},
					{field:'DESCRIBE',title:'描述',width:150},
					{field:'CREATEUSER',title:'创建者',width:110},
					{field:'CREATETIME',title:'创建时间',width:110}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#DzDataSyncBizDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#DzDataSyncBizDataGrid").datagrid(reload);
		$("#DzDataSyncBizDataGrid").datagrid("clearSelections");
	}
	 function openAddDialog(){
		    var url="${ctx}/psam/datasync/dzDataSyncBiz/enterAddDzDataSyncBiz";
			var options={title:"数据同步逻辑信息添加页面",width:600,height:300, url:url,params:{},onClosed:function(){loaddata('reload');}};
			editDialog.open(options);
		}
	 function openeditDialog(){
		 var rows = $("#DzDataSyncBizDataGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要操作的数据");return;
			}
			var url="${ctx}/psam/datasync/dzDataSyncBiz/enterUpdateDzDataSyncBiz";
			var param={id:rows[0].ID};
		    var options={title:"数据同步逻辑信息修改",width:600,height:300, url:url,params:param,onClosed:function(){loaddata('reload');}};
			editDialog.open(options);
	}
	

	
    </script>
</body>
</html>