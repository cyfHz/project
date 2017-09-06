<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
<input type="hidden" id="role_id" value="${role_id}"   /> 
<div data-options="region:'west',border:true" style="width:540px;height:600px; ">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                 <form class="form-inline query-form" id="searchform_widgetRuleRoleHaveDataGrid">
                    <div class="form-group">
                        <label>资源名称x  :</label>
                        <input name="webParams['res_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>资源编码:</label>
                        <input name="webParams['res_code']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="orgUser.roleUserHaveDataGrid">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadDataRoleUserHaveDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="widgetRuleRoleHaveDataGrid" data-options="fit:true" ></table>
		    <div id="widgetRuleRoleHaveToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		      <km:widgetTag widgetRulevalue="orgUser.addRoleToUser">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeWidgetRuleFromRole()">从该角色移除</a>
 			</km:widgetTag>
		   
			</div>	
            </div>
        </div>
</div>

<div data-options="region:'center',border:true" style="width:600px;height:600px;">
       <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                 <form class="form-inline query-form" id="searchform_widgetRuleRoleNotHaveDataGrid">
                    <div class="form-group">
                        <label>资源名称 :</label>
                        <input name="webParams['res_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>资源编码:</label>
                        <input name="webParams['rule_code']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="role.removeWidgetRuleFromRole">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loadDataRoleUserNotHaveDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="widgetRuleRoleNotHaveDataGrid" data-options="fit:true" ></table>
		    <div id="widgetRuleRoleNotHaveToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
 			     <km:widgetTag widgetRulevalue="role.addWidgetRuleToRole">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addWidgetRuleToRole()">分配到角色</a>
 				</km:widgetTag>
			</div>	
            </div>
        </div>
</div>
<script type="text/javascript">
    $(function() {
    	initWidgetRuleRoleHaveDataGrid();
    	initWidgetRuleRoleNotHaveDataGrid();
    });
    function initWidgetRuleRoleHaveDataGrid(){
    	var role_id=$("#role_id").val();
    	var loadurl="${ctx}/auth/role/loadWidgetRuleRoleHaveDataSet?role_id="+role_id;
    	    $("#widgetRuleRoleHaveDataGrid").datagrid({
				title:"角色已经拥有资源列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageList:[10,50,500],pageSize: 500,
				url:loadurl,checkbox:true,
				idField:'RULE_ID',//分页保留选中
				toolbar:"#widgetRuleRoleHaveToolbarDiv",
				columns:[[
					{field:'RULE_ID',width:20,checkbox:true},
					{field:'RULE_CODE',title:'编码',width:80,sortable:true},
					{field:'RULE_NAME',title:'名称',width:130,sortable:true},
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
	  function initWidgetRuleRoleNotHaveDataGrid(){
		var role_id=$("#role_id").val();
    	var loadurl="${ctx}/auth/role/loadWidgetRuleRoleNotHaveDataSet?role_id="+role_id;
    	    $("#widgetRuleRoleNotHaveDataGrid").datagrid({
				title:"角色已经没有资源列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageList:[10,50,500],pageSize: 500,
				url:loadurl,checkbox:true,
				idField:'RULE_ID',//分页保留选中
				toolbar:"#widgetRuleRoleNotHaveToolbarDiv",
				columns:[[ 
							{field:'RULE_ID',width:20,checkbox:true},
							{field:'RULE_CODE',title:'编码',width:80,sortable:true},
							{field:'RULE_NAME',title:'名称',width:130,sortable:true},
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
		function loadDataWidgetRuleRoleHaveDataGrid(reload) { 
		  	var queryParams =$("#widgetRuleRoleHaveDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#searchform_widgetRuleRoleHaveDataGrid").form().serializeArray());
			$("#widgetRuleRoleHaveDataGrid").datagrid(reload);
			$("#widgetRuleRoleHaveDataGrid").datagrid("clearSelections");
		}
		function loadDataWidgetRuleRoleNotHaveDataGrid(reload) { 
		  	var queryParams =$("#widgetRuleRoleNotHaveDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#searchform_widgetRuleRoleNotHaveDataGrid").form().serializeArray());
			$("#widgetRuleRoleNotHaveDataGrid").datagrid(reload);
			$("#widgetRuleRoleNotHaveDataGrid").datagrid("clearSelections");
		}
	
	function addWidgetRuleToRole(){
		var role_id=$("#role_id").val();
		  var rows = $("#widgetRuleRoleNotHaveDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"RULE_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/auth/role/addWidgetRuleToRole";
						var param = {"widgetRuleIds" : idArray,"role_id":role_id};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loadDataWidgetRuleRoleHaveDataGrid('reload');
							loadDataWidgetRuleRoleNotHaveDataGrid('reload');
						});
					}
			});
	}
	function removeWidgetRuleFromRole(){
		var role_id=$("#role_id").val();
		  var rows = $("#widgetRuleRoleHaveDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要操作的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"RULE_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/auth/role/removeWidgetRuleFromRole";
						var param = {"widgetRuleIds" : idArray,"role_id":role_id};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loadDataWidgetRuleRoleHaveDataGrid('reload');
							loadDataWidgetRuleRoleNotHaveDataGrid('reload');
						});
					}
			});
	}

    </script>

</div>
