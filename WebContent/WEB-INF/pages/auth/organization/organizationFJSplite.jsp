<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
<input id="orgnaid" value="${orgnaid}"> 
<div data-options="region:'west',border:true" style="width:540px;height:600px; ">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <input type="hidden" name="jwqid1" id="jwqid1" value="${jwq.jwqid}"   />
                 <form class="form-inline query-form" id="jwqSearchform_PCSDataGrid">
                    <div class="form-group">
                        <label>组织机构名称 :</label>
                        <input name="webParams['user_name']" type="text" class="form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="jwq.jwqReYuanList">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddataPCSDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="PCSDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		        <km:widgetTag widgetRulevalue="jwqyJygx.addJyToJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addJyToJwq()">拆分成所选派出所</a>
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
                       <km:widgetTag widgetRulevalue="jwq.jwqReYuanList">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddataJyInjwqDataGrid('load');">查 询</a>
						</km:widgetTag>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="jyInjwqDataGrid" data-options="fit:true" ></table>
		    <div id="jwqyJygxToolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		    
		    <km:widgetTag widgetRulevalue="jwqyJygx.disableJwqyJygx">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="disableJwqyJygx()">分配警务区到该派出所</a>
 			</km:widgetTag>
 				
			</div>	
            </div>
        </div>
</div>
<script type="text/javascript">
    $(function() {
    	initJwqDataGrid();
    	initPCSDataGrid();
    });
    function initPCSDataGrid(){
    	var orgnaid=$("#orgnaid").val();
    	var loadurl="${ctx}/auth/organization/organPCSDataGrid?orgnaid="+orgnaid;
    	    $("#PCSDataGrid").datagrid({
				title:"派出所列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:false,
				pagination : true,rownumbers : true,pageSize: 10,
				url:loadurl,checkbox:true,
				idField:'ORGNA_ID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[ //username ,idnum,sex,userId
				           //{field:'ORGNA_ID',width:20,checkbox:true},
						    {field:'ORGNA_CODE',title:'机构编号',width:100},
						    {field:'ORGNA_NAME',title:'机构名称',width:200,sortable:true},
							{field:'ORGNA_JC',title:'结构简称',width:200,sortable:true},
							{field:'ORGNA_ZIPCODE',title:'机构邮编',width:123},
							{field:'ORGNA_TEL',title:'机构电话',width:100},
						    {field:'ORGNA_FAX',title:'机构传真',width:123},
							{field:'ORGNA_EMAIL',title:'机构邮箱',width:123},
							{field:'ORGNA_ADDRESS',title:'机构地址',width:200},
							{field:'SFZSJ',title:'是否直属局',width:100,
								formatter:function(value){ if("1"==value){ return "是"; }else{ return "否"; } }}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
//-----------------------------------------------------------------------------------------
	  function initJwqDataGrid(){
		var orgnaid=$("#orgnaid").val();
    	var loadurl="${ctx}/psam/jwq/jwqListByPcsId?orgnaid="+orgnaid;
    	    $("#jyInjwqDataGrid").datagrid({
				title:"所在要拆分的派出所警务区列表",
				nowrap: true,rownumbers: true, fitColumns: true,//滚动条
				animate:true,collapsible:false,striped:true,singleSelect:true,
				pagination : true,rownumbers : true,pageSize: 10,
				url:loadurl,checkbox:true,
				idField:'JWQID',//分页保留选中
				toolbar:"#jwqyJygxToolbarDiv",
				columns:[[ //username ,idnum,sex,userId
				       	//{field:'JWQID',width:20,checkbox:true},
							{field:'JWQBH',title:'警务区编号',width:130,sortable:true},
							{field:'JWQMC',title:'警务区名称',width:200,sortable:true},
							{field:'ORGNA_NAME',title:'所属派出所',width:150,sortable:true},
							{field:'SFYX',title:'是否有效 ',width:100,formatter:function(value,row){
								if(0==value){ return "<font color=red>无效<font>";
								} else if(1==value){ return "<font color=green>有效<font>"; }
							}},
							{field:'JWQJJ',title:'警务区简介',width:100},
							{field:'JWQMJ',title:'警务区面积',width:100},
							{field:'NCGQSL',title:'农村管区数量',width:100},
						
							{field:'QYRQ',title:'启用日期 ',width:100},
							{field:'SXRQ',title:'失效日期 ',width:100}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
		function loaddataPCSDataGrid(reload) { 
		  	var queryParams =$("#PCSDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform_PCSDataGrid").form().serializeArray());
			$("#PCSDataGrid").datagrid(reload);
			$("#PCSDataGrid").datagrid("clearSelections");
		}
		function loaddataJyInjwqDataGrid(reload) { 
		  	var queryParams =$("#jyInjwqDataGrid").datagrid("options").queryParams;
		  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform_JyInjwqDataGrid").form().serializeArray());
			$("#jyInjwqDataGrid").datagrid(reload);
			$("#jyInjwqDataGrid").datagrid("clearSelections");
		}
	
	function addJyToJwq(){
		var jwqid=$("#jwqid").val();
		  var rows = $("#PCSDataGrid").datagrid("getSelections");
		   if(rows.length==0){
			   alertMsg.warn("请选择要禁用的数据");
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
							loaddataPCSDataGrid('reload');
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
							loaddataPCSDataGrid('reload');
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
							loaddataPCSDataGrid('reload');
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
