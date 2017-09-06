<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/includeall.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link href="${pageContext.request.contextPath}/static/css/easyuiqueryform.css"  rel="stylesheet" type="text/css"></link>
<script>
var treegrid;
$(function(){
	var h1=$(this).height();
	var loadurl="${ctx}/demo/user/loadUserDataGrid";
    treeDrid = $("#userDataGrid").datagrid({
				title:"",
				width:'100%',
				height:400,
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true,
				fitColumns: false,//滚动条
				animate:true,
				collapsible:true,//显示可折叠按钮
				striped:true,//设置为true将交替显示行背景。
				singleSelect:false,//为true时只能选择单行
				pagination : true,//分页
				rownumbers : true,//行数
				pageSize: 10,
				pageList: [10, 20, 50, 100, 200, 500],
				url:loadurl,
				checkbox:true,
				idField:'id',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[ //username ,idnum,sex,userId
					{field:'USERID',width:20,checkbox:true},
					{field:'USERNAME',title:'用户名',width:100,sortable:true},
					{field:'IDNUM',title:'用户编号',width:100,sortable:true},
					{field:'SEX',title:'性别',width:130,formatter:function(value,row){
						 if(true==value){ return "<font color=red>男<font>"; 
						 }else if(false==value){return "<font color=green>女<font>";  
						 }else{ return "<font color=red>错误状态<font>";  
						 }
					}}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
});
	
	function loaddata(reload) { 
	  	var queryParams =$("#userDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#userDataGrid").datagrid(reload);
		$("#userDataGrid").datagrid("clearSelections");
	}

 function openeditDialog(){
		 var rows = $("#userDataGrid").datagrid("getSelections");
		 if(rows.length == 0){
			alertMsg.warn("请选择要编辑的数据");return;
		 }
		 if(rows.length>1){
			alertMsg.warn("请选择一条数据");return;
		}
	    var url="${ctx}/demo/user/enterUpdateUser";
	    var param={id:rows[0].id};
		var options={title:"用户编辑",width:600,height:400, url:url,params:param,onClosed:function(){loaddata('reload');}};
		//editDialog.open(options);
		 alertMsg.info("打开编辑页面");
 }
 function openAddDialog(){
    var url="${ctx}/demo/user/enterAddUser";
	var options={title:"用户添加",width:600,height:400, url:url,params:{},onClosed:function(){loaddata('reload');}};
	editDialog.open(options);
	 //alertMsg.info("打开添加页面");
}
 function deleteUser() {
		var rows = $("#userDataGrid").datagrid("getSelections");
		if (rows.length == 0) {
			alertMsg.warn("请选择要删除的数据");
			return;
		}
		var idArray =KMEASYUtil.rowsIdToArray(rows,"USERID");
		alertMsg.confirm("确定要删除该数据？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() {alertMsg.close();
					var ajaxUrl = "${ctx}/demo/user/batchDeleteUser";
					var param = {"ids" : idArray};
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						KMCORE.ajaxDone(data);
						loaddata('reload');
					});
				}
			});
		}
</script>
</head>
<body style="overflow: hidden; ">
<div class="easyui-layout" style="margin:1px;padding:0px;width:100%;" data-options="fit:true,border:false">
	<div data-options="region:'center'"  title=""  style="width:99.7%;overflow-y:hidden;" >
  
  	<div id="queryFormDiv" class="easyui-panel" style="margin:1px;padding:0px;width:1000; overflow-x:sroll;overflow-y:hidden; " >
		<form name="searchform" method="post" action="" id ="searchform" >
		<input type="hidden" name="" id="departmentId" />
	       <table class="easyuiqueryform" id="easyuiqueryform"  >
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="webParams['username']" class="easyui-textbox "/></td>
					<td>编号:</td>
					<td><input type="text"  name="webParams['alias']" class="easyui-textbox" /></td>
					<td>&nbsp;</td>
					<td><km:authButton text="查询" onclick="loaddata('load');;" iconCls="icon-search" /></td>
				</tr>
			</table>
	  	</form>
	</div>
	<table id="userDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>

	<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		<km:authButton text="添 加" iconCls="icon-add" widgetRuleCode="wrule_test_02_enterAddUser" hiddenIfNoPerm="true" onclick="openAddDialog();"/>
		<km:authButton text="编 辑" iconCls="icon-edit"   hiddenIfNoPerm="true" onclick="openeditDialog();"/>
		<km:authButton text="删 除" iconCls="icon-remove"  onclick="deleteUser();"/>
	</div>	
</div>	
</div>
</body>
</html>