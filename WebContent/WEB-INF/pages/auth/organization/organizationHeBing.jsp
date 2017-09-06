<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
  <input id="oranIds" value="${oranIds}"> 
    <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center',border:false">
            <table id="HebingOrgDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		        <km:widgetTag widgetRulevalue="jwq.addJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="enterAddJwq()">添加</a>
 				</km:widgetTag>
 			 		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="splitJwq()">拆分</a>
			</div>	
            </div>
        </div>
    </div>
	</div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	/* loadZzjgTree(); */
    	startOrgHeBing();
    	
   });
   
   function initHeBingOrgDataGrid(){
		$("#HebingOrgDataGrid").datagrid({
			nowrap: true,rownumbers: true, fitColumns: true,
			animate:true,striped:true,singleSelect:true,pagination : false,rownumbers : true,
			pageSize: 100,
			pageList:[100],
			/* url:"${ctx}/auth/organization/organHeBing?orgnaid="+orgIds, */
			checkbox:false,
			idField:'ORGNA_ID',
			columns:[[
			    {field:'ORGNA_CODE',title:'机构编号',width:100},
			    {field:'ORGNA_NAME',title:'机构名称',width:240,sortable:true},
				{field:'ORGNA_JC',title:'结构简称',width:200,sortable:true},
			]],
	        onBeforeLoad:function(){ 
	        	$(this).datagrid("clearSelections");
	        },loadFilter:function(data,parentId){
				return KMCORE.ajaxDoneForServerError(data);
			}
		});
	}
   function startOrgHeBing(){
		/* var rows = $("#organizationDataGrid").datagrid("getSelections");
		if(rows.length ==0 ){
			alertMsg.warn("请选择要合并的数据");return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "ORGNA_ID");
		idArray_g = idArray;
		alert(idArray);
		alert(idArray_g); */
		var idArray=$("#oranIds").val();
		alert(idArray);
		var ajaxUrl = "${ctx}/auth/organization/validateOrgan";
		var param = {"oranIds":idArray};
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			if(data.statusCode=='300'){
				 alertMsg.error(data.message);
			}
			if(data.statusCode=='200'){
				//方案（一）
				 $("#dlg_heBingOrgDataGrid").dialog("open").dialog("center").dialog("setTitle","选择要合并到的组织机构");
				 initHeBingOrgDataGrid();
				$('#HebingOrgDataGrid').datagrid('loadData', data.data); 
				/*  initHeBingOrgDataGrid1();
				$('#HebingOrgDataGrid1').datagrid('loadData', data.data); 
				alert("21"); */
				
			}

		}); 
	} 
	function loaddata(reload) { 
	  	var queryParams =$("#HebingOrgDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform").form().serializeArray());
		$("#HebingOrgDataGrid").datagrid(reload);
		$("#HebingOrgDataGrid").datagrid("clearSelections");
	}

    </script>
  


