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
                        <th>用户名称:</th>
                        <input name="username" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="">	</km:widgetTag>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="bzdzUserDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   	<km:widgetTag widgetRulevalue="bzdzuser.addUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
			 	</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzuser.saveUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzuser.deleteUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzuser.reviewUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="openReviewDialog('1')">审核通过</a>
				</km:widgetTag>
				<km:widgetTag 	widgetRulevalue="bzdzuser.reviewUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="openReviewDialog('0')">审核不通过</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzuser.lockedUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openSd()">锁定</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzuser.unlockedUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openJs()">解锁</a>
				</km:widgetTag>
				
				<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditDialog()">修改</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="openReviewDialog('1')">审核通过</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="openReviewDialog('0')">审核不通过</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openSd()">锁定</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openJs()">解锁</a> -->
			</div>	
            </div>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/webservice/bzdzuser/loadBzdzUserAll";
    	  treeDrid = $("#bzdzUserDataGrid").datagrid({
				title:"用户管理列表",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:true,
				idField:'SYSTEMID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
				    //{field:'SYSTEMID',checkbox:true,width:110},
					{field:'USERNAME',title:'用户名',width:100,sortable:true},
					{field:'BDIP',title:'绑定ip',width:100,sortable:true},
					{field:'SFSH',title:'是否审核',width:110,formatter:formatSfsh},
					{field:'SFSD',title:'是否锁定',width:110,formatter:formatSfsd},
					{field:'REJECT_COUNT',title:'限定次数',width:110,formatter:formatCount},
					{field:'REGISTERTIME',title:'注册时间',width:110}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#bzdzUserDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#bzdzUserDataGrid").datagrid(reload);
		$("#bzdzUserDataGrid").datagrid("clearSelections");
	}

	function openAddDialog(){
		var url="${ctx}/psam/webservice/bzdzuser/enterAddBzdzUser";
		var params={};
		var options={title:"添加用户信息",width : 760, height : 400,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}

	function openeditDialog(){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		var url="${ctx}/psam/webservice/bzdzuser/enterUpdateBzdzUser";
		var params={systemid:rows[0].SYSTEMID};
		var options={title:"修改用户信息页面",width : 760, height : 400,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}
	
	function deleteUser(){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		
		var idArray = KMEASYUtil.rowsIdToArray(rows, "");
		alertMsg.confirm("确认删除？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/webservice/bzdzuser/deleteUser.do";
				var param={systemid:rows[0].SYSTEMID};
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					$.messager.alert('提示',data.message);	
					loaddata('reload');
				});
			}
		});
	}
	
	function openReviewDialog(spzt){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条数据！");
			return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "");
		alertMsg.confirm("确认进行审核该用户？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var ajaxUrl = "${ctx}/psam/webservice/bzdzuser/reviewUser";
				var param={systemid:rows[0].SYSTEMID,spzt:spzt};
				KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					//$.messager.alert('提示',data.message);
					alertMsg.warn(data.message);
					loaddata('reload');
				});
			}
		});
	}
	
	function openSd(){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		if(rows[0].SFSD ==1){
			alertMsg.warn("该用户已经锁定");
			return;
		}
		var url="${ctx}/psam/webservice/bzdzuser/enterSdBzdzUser";
		var params={systemid:rows[0].SYSTEMID};
		var options={title:"锁定编辑页面",width : 760, height : 300,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}
	
    function openJs(){
    	var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		if(rows[0].SFSD ==0){
			alertMsg.warn("该用户已经解锁");
			return;
		}
		var url="${ctx}/psam/webservice/bzdzuser/enterJsBzdzUser";
		var params={systemid:rows[0].SYSTEMID};
		var options={title:"解锁编辑页面",width : 760, height : 300,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}
    
    function formatCount(value){
    	if(value==""||value==null){
    		return '<span class="tag-success">不限</span>';
    	}else{
    		return '<span class="tag-fail">每分钟限制登录'+value+'次</span>';
    	}
    }
    
    function  formatSfsd(value){
		if('1'==value){
			return '<span class="tag-normal">已锁定</span>';
		}else{
			return '<span class="tag-success">正常</span>';
		}
	}
    
    function  formatSfsh(value){
		if('0'==value){
			return '<span class="tag-normal">未审核</span>';
		}else if('1'==value){
			return '<span class="tag-success">已审核</span>';
		}else{
			return '<span class="tag-normal">未审核</span>';
		}
	}
    </script>
</body>
</html>