<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/includeall.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"></link>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',title:'组织机构',split:true" style="width: 200px;">
        <ul id="zzjgTree" class="easyui-tree"></ul>
    </div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<div class="easyui-layout" data-options="fit:true">
					<div  data-options="region:'north',border:false" style="overflow:hidden;height:0px; background-color: #F4F4F4;">
						<form class="form-inline query-form" id="mlphform">
						  <input type="hidden" id="orgid" name="porg_code" value="${orgCode}"></input>
						</form>
					</div>
					<div data-options="region:'center',border:false">
						<table id="workLoadGrid"> </table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
	<script type="text/javascript">
		//init 
		$(function() {
			initZzjgTree();
			 $('#orgid').val("370000000000");
			initGrid();
		});
		function initGrid(){
			$('#workLoadGrid').datagrid({	
				title:"数据类型统计管理",
				url:'${ctx}/psam/workload/workLoadList',
				rownumbers:true,
				fit:true,
				pagination:true,
				fitColumns:false,
				singleSelect:true,
				pageSize : 50,
				toolbar:'#toolbar',
				frozenColumns : [ [
				        {field:'name',title:'名称',width:210},
				        {field:'code',title:'编码',width:160}        
			    ]],
			  	columns:[[
						{field:'mlph_totle',title:'门楼牌号',width:120},
						{field:'mlph_nozb',title:'门楼牌号无坐标',width:120},
						{field:'mlph_jwq',title:'门楼牌号无警务区',width:120},
						{field:'jzwjbxx_totle',title:'建筑物',width:120},
						{field:'jzwjbxx_nozb',title:'建筑物无坐标',width:120},
						{field:'jzwjbxx_jwq',title:'建筑物无警务区',width:120},
						{field:'jwq_totle',title:'警务区',width:120},
						{field:'jwq_nozb',title:'警务区无坐标',width:120}
				    ]],
				    rowStyler:function(index,row){
				    	if(row.DELTAG==1){return 'color: #5e5e5e;background-color: #E4E4E4;'}
				    },loadFilter:function(data){
						return KMCORE.ajaxDoneForServerError(data);
				  	 }
			});
		}
		//加载组织机构树
		function initZzjgTree(){
	    	$('#zzjgTree').tree({
				url: '${ctx}/auth/organization/loadOrganTree',
				cascadeCheck:false,
				loadFilter: function(data) {
					if (!data || !data.rows || data.rows.length < 1) {
						return [];
					}
					var nodes = [];
					$(data.rows).each(function(i, row) {
						var node = {};
						node.id = row.NODEID;
						node.text = row.NODETEXT;
						node.state = "closed";
						nodes.push(node);
					});
					return nodes;
				} , onClick: function(node) {
	               $('#orgid').val(node.id);
	               loaddata('reload');
	            },onLoadSuccess: function() {
	               $('#zzjgTree').tree('expand',$('#zzjgTree').tree('getRoot').target);
	            }
			});
	    }
		
		function loaddata(reload) {
			var queryParams = $("#workLoadGrid").datagrid("options").queryParams;
			KMEASYUtil.genQueryParams(queryParams, $("#mlphform").form().serializeArray());
			$("#workLoadGrid").datagrid(reload);
			$("#workLoadGrid").datagrid("clearSelections");
		}
	</script>
</body>
</html>