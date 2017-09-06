<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
<input type="hidden" id="role_id" value="${role_id}"   /> 
<div data-options="region:'west',border:true" style="width:540px;height:600px; ">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                
                 <form class="form-inline query-form" id="searchform_resourceRoleHaveDataGrid">
                    <div class="form-group">
                        <label>资源名称  :</label>
                        <input name="webParams['res_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="orgUser.roleUserHaveDataGrid">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadDataRoleUserHaveDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="resourceRoleHaveDataGrid" data-options="fit:true" ></table>
		    <div id="resourceRoleHaveToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		      <km:widgetTag widgetRulevalue="orgUser.addRoleToUser">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeResourceFromRole()">从该角色移除</a>
 			</km:widgetTag>
		   
			</div>	
            </div>
        </div>
</div>

<div data-options="region:'center',border:true" style="width:600px;height:600px;">
       <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                 <form class="form-inline query-form" id="searchform_resourceRoleNotHaveDataGrid">
                    <div class="form-group">
                        <label>资源名称 :</label>
                        <input name="webParams['res_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="role.removeResourceFromRole">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadDataRoleUserNotHaveDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="resourceRoleNotHaveDataGrid" data-options="fit:true" ></table>
		    <div id="resourceRoleNotHaveToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
 			     <km:widgetTag widgetRulevalue="role.addResourceToRole">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addResourceToRole()">分配到角色</a>
 				</km:widgetTag>
			</div>	
            </div>
        </div>
</div>
<script type="text/javascript">
    $(function() {
    	initResourceRoleHaveDataGrid();
    	initResourceRoleNotHaveDataGrid();
    });
    function initResourceRoleHaveDataGrid(){
    	var role_id=$("#role_id").val();
    	var loadurl="${ctx}/auth/role/loadResourceRoleHaveDataSet?role_id="+role_id;
    	    $("#resourceRoleHaveDataGrid").datagrid({
				title:"角色已经拥有资源列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageSize: 50,
				url:loadurl,checkbox:true,
				idField:'RES_ID',//分页保留选中
				toolbar:"#resourceRoleHaveToolbarDiv",
				columns:[[
					{field:'RES_ID',width:20,checkbox:true},
					{field:'RES_CODE',title:'编码',width:80,sortable:true},
					{field:'RES_NAME',title:'名称',width:130,sortable:true},
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
	  function initResourceRoleNotHaveDataGrid(){
		var role_id=$("#role_id").val();
    	var loadurl="${ctx}/auth/role/loadResourceRoleNotHaveDataSet?role_id="+role_id;
    	    $("#resourceRoleNotHaveDataGrid").datagrid({
				title:"角色已经没有资源列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageSize: 50,
				url:loadurl,checkbox:true,
				idField:'RES_ID',//分页保留选中
				toolbar:"#resourceRoleNotHaveToolbarDiv",
				columns:[[ 
							{field:'RES_ID',width:20,checkbox:true},
							{field:'RES_CODE',title:'编码',width:80,sortable:true},
							{field:'RES_NAME',title:'名称',width:130,sortable:true},
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
		function loadDataResourceRoleHaveDataGrid(reload) { 
		  	var queryParams =$("#resourceRoleHaveDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#searchform_resourceRoleHaveDataGrid").form().serializeArray());
			$("#resourceRoleHaveDataGrid").datagrid(reload);
			$("#resourceRoleHaveDataGrid").datagrid("clearSelections");
		}
		function loadDataResourceRoleNotHaveDataGrid(reload) { 
		  	var queryParams =$("#resourceRoleNotHaveDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#searchform_resourceRoleNotHaveDataGrid").form().serializeArray());
			$("#resourceRoleNotHaveDataGrid").datagrid(reload);
			$("#resourceRoleNotHaveDataGrid").datagrid("clearSelections");
		}
	
	function addResourceToRole(){
		var role_id=$("#role_id").val();
		  var rows = $("#resourceRoleNotHaveDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"RES_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/auth/role/addResourceToRole";
						var param = {"resourceIds" : idArray,"role_id":role_id};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loadDataResourceRoleHaveDataGrid('reload');
							loadDataResourceRoleNotHaveDataGrid('reload');
						});
					}
			});
	}
	function removeResourceFromRole(){
		var role_id=$("#role_id").val();
		  var rows = $("#resourceRoleHaveDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要操作的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"RES_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/auth/role/removeResourceFromRole";
						var param = {"resourceIds" : idArray,"role_id":role_id};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loadDataResourceRoleHaveDataGrid('reload');
							loadDataResourceRoleNotHaveDataGrid('reload');
						});
					}
			});
	}

    </script>

</div>
