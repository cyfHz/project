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
					<div  data-options="region:'north',border:false" style="overflow:hidden;height:40px; background-color: #F4F4F4;">
						<form class="form-inline query-form" id="mlphform">
						  <input type="hidden" id="orgid" name="ORGID" ></input>
							<div class="form-group">
								<label>姓名:</label> <input name="DZMC" type="text" class="form-control easyui-validatebox"></input>
							</div>
							<div class="form-group">
							   <km:widgetTag widgetRulevalue="checkMlph.list">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a> 
							</km:widgetTag>
							</div>
						</form>
					</div>
					<div data-options="region:'center',border:false">
						<table id="workLoadGrid"> </table>
						<div id="toolbar">
 				   		 <km:widgetTag widgetRulevalue="checkMlph.mark">
		         	      	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openMark()">详情</a>
 				   	 	</km:widgetTag>
						</div>
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
			initGrid();
		});
		function initGrid(){
			$('#workLoadGrid').datagrid({	
				title:"门楼牌号管理",
				url:'${ctx}/psam/sjjy/list',
				rownumbers:true,
				fit:true,
				pagination:true,
				fitColumns:false,
				singleSelect:true,
				pageSize : 10,
				toolbar:'#toolbar',
			  	columns:[[
						{field:'Lo',title:'姓名',width:110},
						{field:'DZMC',title:'单位',width:200},
						{field:'DZYSLXDM',title:'门楼牌号',width:100,formatter:dictFormat('dzyslxDict')},
						{field:'MLPHLXID',title:'建筑物',width:80,formatter:dictFormat('mlphlxDict')},
						{field:'SSJLXXQ_JLXXQMC',title:'房间',width:150},
						{field:'SJGSDWMC',title:'房屋',width:150}
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
	</script>
</body>
</html>