<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/includeall.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"></link>
<%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></script>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script>
<style type="text/css">
.jlx-panel .pagination-info {
	display: none;
}
  v\:* {
        BEHAVIOR: url(#default#VML)
      }
</style>
</head>
<body class="easyui-layout">
	<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
	<t:DataDict code="mlphlx" var="mlphlxDict"></t:DataDict>
	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="yesorno" var="yesornoDict"></t:DataDict>
	
	<div data-options="region:'west',title:'组织机构',split:true" style="width: 200px;">
        <ul id="zzjgTree" class="easyui-tree"></ul> <!-- -->
    </div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<div class="easyui-layout" data-options="fit:true">
					<div  data-options="region:'north',border:false" style="overflow:hidden;height:40px; background-color: #F4F4F4;">
						<form class="form-inline query-form" id="mlphform">
						  <input type="hidden" id="orgid" name="ORGID" ></input>
							<div class="form-group">
								<label>门（楼）详址:</label> <input name="DZMC" type="text" class="form-control easyui-validatebox"></input>
							</div>
							<div class="form-group">
								<label>门（楼）牌号:</label> <input name="MLPH" type="text" class="form-control easyui-validatebox"></input>
							</div>
							<div class="form-group">
							   <km:widgetTag widgetRulevalue="checkMlph.list">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a> 
							</km:widgetTag>
							
							</div>
						</form>
					</div>
					<div data-options="region:'center',border:false">
						<table id="mlphGrid"> </table>
						<div id="toolbar">
 				   		 <km:widgetTag widgetRulevalue="checkMlph.mark">
		         	       <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openMark()">标记</a>
 				   	 	</km:widgetTag>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 <div id="dlg_mlph_zb_mark_pgis"  class="easyui-dialog" style="width: 1000px; height: 500px; padding: 0px 0px"  maximizable="true"  modal="true" closed="true" closable="true"  buttons="#dlg_mlph_zb_mark_buttons">
	<div id="dlg_mlph_zb_mark_buttons">
		  <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="mark();">坐标标注</a>&nbsp;
		  <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:$('#dlg_mlph_zb_mark_pgis').dialog('close')">关闭 </a>&nbsp;
	</div>
	<div id="mymap" class="map" style="height: 100%;"></div>
</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/mlph/km.mlph.mark.js"></script>
	<script type="text/javascript">
		//init 
		$(function() {
			loadZzjgTree();
			initGrid();
		});//end page init
		function initGrid(){
			$('#mlphGrid').datagrid({	
				title:"门楼牌号管理",
				url:'${ctx}/psam/sjjy/list',
				rownumbers:true,
				fit:true,
				pagination:true,
				fitColumns:false,
				singleSelect:true,
				pageSize : 10,
				toolbar:'#toolbar',
				onBeforeLoad:function(){
					//console.log($('#mlphGrid').parent('.datagrid-view').height());
				},
			  	columns:[[
						{field:'MLPH',title:'门（楼）牌号',width:110},
						{field:'DZMC',title:'门（楼）详址',width:200},
						{field:'DZYSLXDM',title:'地址元素类型',width:100,formatter:dictFormat('dzyslxDict')},
						{field:'MLPHLXID',title:'门楼牌号类型',width:80,formatter:dictFormat('mlphlxDict')},
						{field:'SSJLXXQ_JLXXQMC',title:'所属街路巷（小区）名称',width:150},
						{field:'SJGSDWMC',title:'数据归属单位',width:150},
						//{field:'JZWMC',title:'建筑物名称',width:130},
						{field:'JWQMC',title:'警务区名称',width:130},
						{field:'ZXDHZB',title:'中心坐标',width:180,formatter:formatGPS},
						{field:'DJSJ',title:'登记时间',width:100},
						{field:'SPZT',title:'审批状态',width:80,formatter:formatSPZT},
						{field:'DELTAG',title:'撤销标记',width:80,formatter:formatDeltag}
						
				    ]],
				    rowStyler:function(index,row){
				    	if(row.DELTAG==1){
				    		return 'color: #5e5e5e;background-color: #E4E4E4;'
				    		}
				    },loadFilter:function(data){
						return KMCORE.ajaxDoneForServerError(data);
				  	 }
			});
		}
		function openMark(){
			var rows = $("#mlphGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一个门楼牌号数据");
				return;
			}
			targetMarkMlphId=rows[0].YWLSH;
			$("#dlg_mlph_zb_mark_pgis").dialog('open').dialog('setTitle','门楼牌号坐标点标注');
			onLoad();
		}
		
		function reloadMlphDg(){
			setTimeout(function(){
				$("#mlphGrid").datagrid('reload');
			},2000);
		}
		//加载组织机构树
		function loadZzjgTree(){
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
						//node.state = row.NODETYPE == 'SQJCWH' ? "open":"closed";
// 						node.state = "closed";
                        node.state = row.NODETYPE == '3' ? "open":"closed";
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
				var queryParams = $("#mlphGrid").datagrid("options").queryParams;
				KMEASYUtil.genQueryParams(queryParams, $("#mlphform").form().serializeArray());
				$("#mlphGrid").datagrid(reload);
				$("#mlphGrid").datagrid("clearSelections");
			}
			
			//formater
			
			function formatGPS(value,row,index){
				if(typeof row.ZXDHZB=='undefined'){
					return '<span class="tag-normal">未标记</span>';
				}
				return row.ZXDHZB+","+row.ZXDZZB;
			}
			function  formatCxbj(value){
				if('1'==value){
					return '<span class="tag-normal">已撤销</span>';
				}else{
					return '<span class="tag-success">正常</span>';
				}
			}
			
			function  formatDeltag(value){
				if('1'==value){
					return '<span class="tag-normal">已撤销</span>';
				}else{
					return '<span class="tag-success">正常</span>';
				}
			}
			function  formatSPZT(value){
				if(0==value){
					return '<span class="tag-normal">未审核</span>';
				}else if(1==value){return '<span class="tag-fail">区县审核未通过</span>';
				}else if(2==value){return '<span class="tag-success">区县审核已通过</span>';
				}else if(3==value){return '<span class="tag-fail">市局审核未通过</span>';
				}else if(4==value){return '<span class="tag-success">市局审核已通过</span>';
				}else if(5==value){return '<span class="tag-success">审核已通过</span>';}
				else{return '<span class="tag-normal">未审核</span>'};
			}
			
			function closeDlg_cancle(){
				$('#dlg_cancle').dialog('close');
				$('#dlg_cancle_form').form('clear');
			}
	</script>
</body>
</html>