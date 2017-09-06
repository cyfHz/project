<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
<input type="hidden" id="appuser_id" value="${appuser_id}"   /> 
<div data-options="region:'west',border:true" style="width:540px;height:600px; ">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                
                 <form class="form-inline query-form" id="searchform_roleUserHaveDataGrid">
                    <div class="form-group">
                        <label>角色名称  :</label>
                        <input name="webParams['role_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="orgUser.roleUserHaveDataGrid">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadDataRoleUserHaveDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="roleUserHaveDataGrid" data-options="fit:true" ></table>
		    <div id="roleUserHaveToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		      <km:widgetTag widgetRulevalue="orgUser.addRoleToUser">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeRoleFromUser()">从该用户移除</a>
 			</km:widgetTag>
		   
			</div>	
            </div>
        </div>
</div>

<div data-options="region:'center',border:true" style="width:600px;height:600px;">
       <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                 <form class="form-inline query-form" id="searchform_roleUserNotHaveDataGrid">
                    <div class="form-group">
                        <label>角色名称 :</label>
                        <input name="webParams['role_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="orgUser.removeRoleFromUser">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadDataRoleUserNotHaveDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="roleUserNotHaveDataGrid" data-options="fit:true" ></table>
		    <div id="roleUserNotHaveToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
 			     <km:widgetTag widgetRulevalue="orgUser.addRoleToUser">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRoleToUser()">分配到该用户</a>
 				</km:widgetTag>
			</div>	
            </div>
        </div>
</div>
<script type="text/javascript">
    $(function() {
    	initRoleUserHaveDataGrid();
    	initRoleUserNotHaveDataGrid();
    });
    function initRoleUserHaveDataGrid(){
    	var appuser_id=$("#appuser_id").val();
    	var loadurl="${ctx}/auth/organizationUser/loadRoleUserHaveDataSet?appuser_id="+appuser_id;
    	    $("#roleUserHaveDataGrid").datagrid({
				title:"用户已经拥有角色列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageSize: 10,
				url:loadurl,checkbox:true,
				idField:'ROLE_ID',//分页保留选中
				toolbar:"#roleUserHaveToolbarDiv",
				columns:[[
					{field:'ROLE_ID',width:20,checkbox:true},
					{field:'ROLE_CODE',title:'角色编码',width:80,sortable:true},
					{field:'ROLE_NAME',title:'角色名称',width:130,sortable:true},
					//{field:'AREA_ID',title:'组织机构ID',hidden:true},
					{field:'OPRATETIME',title:'更新时间',width:120},
					{field:'ENABLED',title:'是否有效',width:100,formatter:function(val,row){
						if (val =='1'){ return '<span style="color:green;">有效</span>';
						} else { return '<span style="color:red;">禁用</span>'; }
					}}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
//-----------------------------------------------------------------------------------------
	  function initRoleUserNotHaveDataGrid(){
		var appuser_id=$("#appuser_id").val();
    	var loadurl="${ctx}/auth/organizationUser/loadRoleUserNotHaveDataSet?appuser_id="+appuser_id;
    	    $("#roleUserNotHaveDataGrid").datagrid({
				title:"用户已经没有的角色列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageSize: 10,
				url:loadurl,checkbox:true,
				idField:'ROLE_ID',//分页保留选中
				toolbar:"#roleUserNotHaveToolbarDiv",
				columns:[[ 
						{field:'ROLE_ID',width:20,checkbox:true},
						{field:'ROLE_CODE',title:'角色编码',width:80,sortable:true},
						{field:'ROLE_NAME',title:'角色名称',width:130,sortable:true},
						//{field:'AREA_ID',title:'组织机构ID',hidden:true},
						{field:'OPRATETIME',title:'更新时间',width:120},
						{field:'ENABLED',title:'是否有效',width:100,formatter:function(val,row){
							if (val =='1'){ return '<span style="color:green;">有效</span>';
							} else { return '<span style="color:red;">禁用</span>'; }
						}}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
		function loadDataRoleUserHaveDataGrid(reload) { 
		  	var queryParams =$("#roleUserHaveDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#searchform_roleUserHaveDataGrid").form().serializeArray());
			$("#roleUserHaveDataGrid").datagrid(reload);
			$("#roleUserHaveDataGrid").datagrid("clearSelections");
		}
		function loadDataRoleUserNotHaveDataGrid(reload) { 
		  	var queryParams =$("#roleUserNotHaveDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#searchform_roleUserNotHaveDataGrid").form().serializeArray());
			$("#roleUserNotHaveDataGrid").datagrid(reload);
			$("#roleUserNotHaveDataGrid").datagrid("clearSelections");
		}
	
	function addRoleToUser(){
		var appuser_id=$("#appuser_id").val();
		  var rows = $("#roleUserNotHaveDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"ROLE_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/auth/organizationUser/addRoleToUser";
						var param = {"roleIds" : idArray,"appuser_id":appuser_id};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loadDataRoleUserHaveDataGrid('reload');
							loadDataRoleUserNotHaveDataGrid('reload');
						});
					}
			});
	}
	function removeRoleFromUser(){
		var appuser_id=$("#appuser_id").val();
		  var rows = $("#roleUserHaveDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要操作的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"ROLE_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/auth/organizationUser/removeRoleFromUser";
						var param = {"roleIds" : idArray,"appuser_id":appuser_id};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loadDataRoleUserHaveDataGrid('reload');
							loadDataRoleUserNotHaveDataGrid('reload');
						});
					}
			});
	}

    </script>

</div>
