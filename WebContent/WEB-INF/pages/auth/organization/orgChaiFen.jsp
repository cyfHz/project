<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true" >
<div data-options="region:'west',border:true,title:'要拆分到的组织机构列表'" style="width:240px;height:600px; ">
	<table id="chaiFenTargetOrgDataGrid"  data-options="fit:true" ></table>
</div>

<div data-options="region:'center',border:true,title:'被拆分的组织机构子机构列表'" style="width:400px;height:600px;">
     <table id="childOrgDataGrid"  data-options="fit:true" ></table> 
</div>

<script type="text/javascript"> 
function init(){
	 $("#chaiFenTargetOrgDataGrid").datagrid({
			rownumbers: true,
			url:"${ctx}/auth/organization/loadOrgChaiFenDataGrid?fromOrgId="+fromOrgId,
			idField:'ORGNA_ID',
			columns:[[
			    {field:'ORGNA_CODE',title:'机构编号',width:100},
			    {field:'ORGNA_NAME',title:'机构名称',width:240}
			]],
	        onBeforeLoad:function(){ 
	        	$(this).datagrid("clearSelections");
	        },loadFilter:function(data,parentId){
				return KMCORE.ajaxDoneForServerError(data);
			},toolbar:[{
				text:"确认",iconCls:'icon-ok',
				handler:function(){processChaifen();}
			},'-',{
				text:"取消",iconCls:'icon-cancel',
				handler:function(){$('#dlg_chaiFenOrgDataGrid').dialog('close');}
			}]
		});
	 $("#chaiFenOrgDataGrid").datagrid("clearSelections");
}
</script>
</div>
