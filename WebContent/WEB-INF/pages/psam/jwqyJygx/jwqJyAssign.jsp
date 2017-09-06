<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
<input id="jwqid" value="${jwq.jwqid }"> 
<div data-options="region:'west',border:true" style="width:540px;height:600px; ">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                 <form class="form-inline query-form" id="jwqSearchform_jyNotInJwqDataGrid">
                   <input type="hidden" name="jwqid1" id="jwqid1" value="${jwq.jwqid}"   />
                    <div class="form-group">
                        <label>警务人员姓名 :</label>
                        <input name="webParams['user_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="jwq.pcsJyNotInJwqList">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddataJyNotInJwqDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="jyNotInJwqDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		        <km:widgetTag widgetRulevalue="jwq.addJyToJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addJyToJwq()">分配到该警务区</a>
 				</km:widgetTag>
			</div>	
            </div>
        </div>
</div>

<div data-options="region:'center',border:true" style="width:600px;height:600px;">
       <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                 <form class="form-inline query-form" id="jwqSearchform_JyInjwqDataGrid">
                    <div class="form-group">
                        <label>警务人员姓名 :</label>
                        <input name="webParams['user_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="jwq.pcsJyInJwqList">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddataJyInjwqDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="jyInjwqDataGrid" data-options="fit:true" ></table>
		    <div id="jwqyJygxToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		    
		    <km:widgetTag widgetRulevalue="jwq.disableJwqyJygx">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="disableJwqyJygx()">从该警务区移除</a>
 			</km:widgetTag>
			</div>	
            </div>
        </div>
</div>
<script type="text/javascript">
    $(function() {
    	try{
			initJyNotInJwqDataGrid();
	    	initJyInjwqDataGrid();
		}catch(e){}
    });
    function initJyNotInJwqDataGrid(){
    	var jwqid=$("#jwqid").val();
    	var loadurl="${ctx}/psam/jwqyJygx/pcsJyNotInJwqList?jwqid="+jwqid;
    	    $("#jyNotInJwqDataGrid").datagrid({
				title:"派出所未分配警务区的警员列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageSize: 10,
				url:loadurl,checkbox:true,
				idField:'APPUSER_ID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[ //username ,idnum,sex,userId
					{field:'APPUSER_ID',width:20,checkbox:true},
					{field:'USER_NAME',title:'用户姓名',width:80,sortable:true},
					{field:'USER_SFZH',title:'身份证号',width:130,sortable:true},
					{field:'ORGNA_ID',title:'组织机构ID',hidden:true},
					{field:'ORGNA_NAME',title:'所在部门',width:120},
					{field:'USER_MOBILE',title:'手机号',width:100}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
//-----------------------------------------------------------------------------------------
	  function initJyInjwqDataGrid(){
		var jwqid=$("#jwqid").val();
    	var loadurl="${ctx}/psam/jwqyJygx/pcsJyInJwqList?jwqid="+jwqid;
    	    $("#jyInjwqDataGrid").datagrid({
				title:"派出所已分配警员列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:true,
				pagination : true,rownumbers : true,pageSize: 10,
				url:loadurl,checkbox:true,
				//j.ID,j.JWQID,j.USER_ID,j.MOVESIGN,j.SFYX,j.QYRQ,j.SXRQ,j.XGSJ,
				idField:'APPUSER_ID',//分页保留选中
				toolbar:"#jwqyJygxToolbarDiv",
				columns:[[ //username ,idnum,sex,userId
				   		//{field:'APPUSER_ID',width:20,checkbox:true},
						{field:'USER_NAME',title:'用户姓名',width:80,sortable:true},
						{field:'USER_SFZH',title:'身份证号',width:130},
						{field:'ORGNA_ID',title:'组织机构ID',hidden:true},
						{field:'ORGNA_NAME',title:'所在部门',width:120},
						{field:'USER_MOBILE',title:'手机号',width:100},
						{field:'SFYX',title:'是否有效',width:100,formatter:function(val,row){
							if (val =='1'){
								return '<span style="color:green;">有效</span>';
							} else {
								return '<span style="color:red;">禁用</span>';
							}
						}},
						{field:'QYRQ',title:'启用日期',width:100},
						{field:'SXRQ',title:'失效日期',width:100}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
		function loaddataJyNotInJwqDataGrid(reload) { 
		  	var queryParams =$("#jyNotInJwqDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform_jyNotInJwqDataGrid").form().serializeArray());
			$("#jyNotInJwqDataGrid").datagrid(reload);
			$("#jyNotInJwqDataGrid").datagrid("clearSelections");
		}
		function loaddataJyInjwqDataGrid(reload) { 
		  	var queryParams =$("#jyInjwqDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform_JyInjwqDataGrid").form().serializeArray());
			$("#jyInjwqDataGrid").datagrid(reload);
			$("#jyInjwqDataGrid").datagrid("clearSelections");
		}
	
	function addJyToJwq(){
		var jwqid=$("#jwqid").val();
		  var rows = $("#jyNotInJwqDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要分配的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"APPUSER_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/jwqyJygx/addJyToJwq";
						var param = {"app_userids" : idArray,"jwqid":jwqid};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddataJyInjwqDataGrid('reload');
							loaddataJyNotInJwqDataGrid('reload');
						});
					}
			});
	}
	/* 
	function removeJyFromJwq(){
		var jwqid=$("#jwqid").val();
		  var rows = $("#jyInjwqDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"APPUSER_ID");
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/jwqyJygx/removeJyFromJwq";
						var param = {"app_userids" : idArray,"jwqid":jwqid};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddataJyInjwqDataGrid('reload');
							loaddataJyNotInJwqDataGrid('reload');
						});
					}
			});
	}*/
	function disableJwqyJygx(){
		var jwqid=$("#jwqid").val();
		  var rows = $("#jyInjwqDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
			   return;
		   }
		   if(rows.length>1){
			   alertMsg.warn("请选择一条数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"APPUSER_ID");
		   var appuser_id=idArray[0];
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/jwqyJygx/disableJwqyJygx";
						var param = {"app_userid" : appuser_id,"jwqid":jwqid};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddataJyInjwqDataGrid('reload');
							loaddataJyNotInJwqDataGrid('reload');
						});
					}
			});
	}
	/*
	function enableJwqyJygx(){
		var jwqid=$("#jwqid").val();
		  var rows = $("#jyInjwqDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
			   return;
		   }
		   if(rows.length>1){
			   alertMsg.warn("请选择一条数据");
			   return;
		   }
		   var idArray =KMEASYUtil.rowsIdToArray(rows,"APPUSER_ID");
		   var appuser_id=idArray[0];
		   alertMsg.confirm("确定要操作该数据？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/jwqyJygx/enableJwqyJygx";
						var param = {"app_userid" : appuser_id,"jwqid":jwqid};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddataJyInjwqDataGrid('reload');
						});
					}
			});
	} */

    </script>

</div>
