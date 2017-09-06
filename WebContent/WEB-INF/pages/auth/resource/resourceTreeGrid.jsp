<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/static/meta/includeall.jsp" %>
<head>
<title></title>
</head>
<body class="easyui-layout">
 <div data-options="region:'north',border:false" style="height: 20px; background-color: #F4F4F4;overflow: hidden;">
</div>
<div data-options="region:'center',border:false">
            <table id="resourceTreeGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   		<%--  <km:widgetTag widgetRulevalue="resource.enterLogDetail">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="assignedJy()">添加</a>
 				</km:widgetTag>
 				 <km:widgetTag widgetRulevalue="resource.enterLogDetail">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="assignedJy()">分配资源</a>
 				</km:widgetTag>
 				<km:widgetTag widgetRulevalue="resource.enterLogDetail">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="assignedJy()">分配权限</a>
 				</km:widgetTag> --%>
			</div>	
</div>

<script>
var treegrid;
$(function(){
	var loadurl="${ctx}/auth/resource/loadResourceTreeGrid";
    treeDrid = $("#resourceTreeGrid").treegrid({
				title:"资源列表",
				nowrap: true,
				rownumbers: true,
				fitColumns: true,
				animate:true,
				url:loadurl,
				idField:'id',
				treeField:'RES_NAME',
				toolbar:"#toolbarDiv",
				columns:[[
					
					{field:'RES_NAME',title:'资源名称',width:300},
					//{field:'AREA_ID',title:'所属领域',width:160,hidden:true},
					//{field:'AREA_NAME',title:'所属领域',width:100},
					{field:'RES_DESC',title:'资源描述',width:160},
					//{field:'RES_TYPE',title:'资源类型',width:100},
					//{field:'RES_PATHTYPE',title:'res_pathtype',width:100},
					{field:'RES_CODE',title:'资源编码',width:180},
					{field:'RES_PVALUE',title:'资源路径',width:220},
					{field:'RES_ORDER',title:'顺序',width:100},
					//{field:'RES_PID',title:'res_pid',width:100},
					//{field:'DHSXS',title:'dhsxs',width:100},
					//{field:'SFYZ',title:'sfyz',width:100},
					{field:'ENABLED',title:'是否启用',width:100,formatter:function(value,row){
						 if('1'==value){return "<font color=green>启用<font>"; 
						 }else{return "<font color=#1C86EE>禁用<font>"; }}},
					{field:'OPRATETIME',title:'更新时间',width:100}
					//{field:'IS_HREF',title:'is_href',width:100},
					//{field:'IS_MAP_OP',title:'is_map_op',width:100}
					
					/* {field:'MAP_OP_RESOURCE',title:'map_op_resource',width:300},
					{field:'CJ_MODE',title:'cj_mode',width:300},
					{field:'DJR',title:'djr',width:300},
					{field:'XGR',title:'xgr',width:300},
					{field:'XGDW',title:'xgdw',width:300},
					{field:'ZHXGXJ',title:'zhxgxj',width:300},
					{field:'ZXBJ',title:'zxbj',width:300},
					{field:'ZXRQ',title:'zxrq',width:300},
					{field:'IMAGEBIG_VALUE',title:'imagebig_value',width:300},
					{field:'IMAGESMALL_VALUE',title:'imagesmall_value',width:300} */
				]]
			});
});//window onload

		function reload(){
			var node = $("#rbacMenuTreeGrid").treegrid('getSelected');
			if (node){
				$("#rbacMenuTreeGrid").treegrid('reload', node.code);
			} else {
				$("#rbacMenuTreeGrid").treegrid('reload');
			}
		}
		
		function deleteTreeNode(){//删除某节点
			var node = $("#rbacMenuTreeGrid").treegrid('getSelected');
			if(!node){
				alertMsg.warn("请选择节点");return;
			}
			alertMsg.confirm("确定要删除该节点？", {
				cancelCall:function(){
					alertMsg.close();
				},okCall:function(){
					alertMsg.close();
					var ajaxUrl="${ctx}/rbac/menu/deleteMenu";
					var param={id:node.id};
					ZHTAJAX.ajaxTodo(ajaxUrl,param,function(data){
						ZHT.ajaxDone(data);
						reload();
					});
					
				}
			});
		}


 function openAddDialog(){
	var url="${ctx}/rbac/menu/enterAddMenu";
	var options={title:"资源添加",width:600,height:400, url:url,onClosed:function(){reload();}};
	editDialog.open(options);
 }
 function openeditDialog(){
		var node = $("#rbacMenuTreeGrid").treegrid('getSelected');
		if(!node){
			alertMsg.warn("请选择节点");return;
		}
		
	    //var url="${ctx}/rbac/menu/enterEidtMenu?id="+node.id;
	    var url="${ctx}/rbac/menu/enterEidtMenu";
	    var params={id:node.id};
		var options={title:"资源编辑",width:600,height:400, url:url,params:params,onClosed:function(){reload();}};
		editDialog.open(options);
 }
</script>

</body>
</html>