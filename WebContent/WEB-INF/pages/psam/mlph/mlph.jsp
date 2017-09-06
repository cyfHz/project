<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/includeall.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"></link>
<%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<script src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></script>
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
	<div data-options="region:'west',title:'行政区划',split:true" style="width: 250px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false,collapsible:false" style="height: 260px;">
				<jsp:include page="../xzqh/xzqh.tree.jsp">
					<jsp:param name="showXzjd" value="true"  />
					<jsp:param name="showSqjcwh" value="true" />
					<jsp:param name="checkbox" value="false" />
				</jsp:include>
			</div>
			<div data-options="region:'center',title:'街路巷（小区）',split:true" class="jlx-panel" style="width: 230px;">
				<div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north',border:false" style="height: 35px; background-color: #F4F4F4;">
						<div style="padding: 5px 25px;">
							<input id="jlxxqmc-text" class="easyui-searchbox" name="JLXXQMC" style="width: 170px; margin: 5px;" data-options="searcher:loadJlx,prompt:'街路巷名称'"></input>
						</div>
					</div>
					<div data-options="region:'center',border:false">
						<table id="jlxGrid" class="easyui-datagrid">
							<thead>
								<tr>
									<th data-options="field:'JLXXQMC'" width="240px">街路巷（小区）名称</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',border:false">
				<div class="easyui-layout" data-options="fit:true">
					<div  data-options="region:'north',border:false" style="overflow:hidden;height:40px; background-color: #F4F4F4;">
						<form class="form-inline query-form form-horizontal" id="mlphform">
						  <input type="hidden" id="jlxdzbm" name="jlxdzbm" /></input>
						  <input type="hidden" id="ORGID" name="ORGID" ></input>
						   <input type="hidden" id="sszdyjxzqy_dzbm" name="SSZDYJXZQY_DZBM" value="" ></input>
							<div class="form-group">
								门（楼）详址: <input name="DZMC" id="dzmc" type="text" class="form-control easyui-validatebox"></input>
							</div>
							<div class="form-group">
								门（楼）牌号: <input name="MLPH" id="mlph" type="text" class="form-control easyui-validatebox"></input>
							</div>
							<div class="form-group">
								数据归属单位: <input name="SJGSDWMC" id="sjgsdwmc" type="text" class="form-control easyui-validatebox"></input>
							</div>
							<div class="form-group">
							   <km:widgetTag widgetRulevalue="mlph.list">
							  	 <a id="qbtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
							</km:widgetTag>
							
							</div>
						</form>
					</div>
					<div data-options="region:'center',border:false">
						<table id="mlphGrid"> </table>
						<div id="toolbar">
						 <km:widgetTag widgetRulevalue="mlph.add">
		         	       <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMlph()">添加</a>
 				    	</km:widgetTag>
		 				    	 <km:widgetTag widgetRulevalue="mlph.save">
		         	       <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editMlph()">修改</a>
 				   		 </km:widgetTag>
		 				     <km:widgetTag widgetRulevalue="mlph.settag">
		         	       	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="cancelMlph()">注销</a>
 				    	</km:widgetTag>
		 				<km:widgetTag widgetRulevalue="mlph.settag">
		         	      <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="enableMlph()">启用</a>
 				    	</km:widgetTag>
		 				    <km:widgetTag widgetRulevalue="mlph.enterDetailMlph">
				           <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细信息</a>
			      		</km:widgetTag>
		 				<km:widgetTag widgetRulevalue="mlph.mark">
		         	       <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openMark()">标记</a>
 				   	 	</km:widgetTag>
 					<%--  <km:widgetTag widgetRulevalue="mlph.batchMark">
		         	       <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openBatchMark()">批量标记</a>
 				   	 	</km:widgetTag> --%>
		 				 <km:widgetTag widgetRulevalue="mlph.applyUseJlx">
							<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openApplyDialog()">再次申请</a>
						</km:widgetTag>
						<%-- <km:widgetTag widgetRulevalue="mlph.reviewMlph">
							<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="openReviewDialog('yes')">审批通过</a>
						</km:widgetTag>
						<km:widgetTag 	widgetRulevalue="mlph.reviewMlph">
							<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="openReviewDialog('no')">审批不通过</a>
						</km:widgetTag> --%>
						
					<km:widgetTag widgetRulevalue="qrcode.enterQRCodePage">	
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="viewQRCode()">查看二维码</a>
					</km:widgetTag>
						<km:widgetTag 	widgetRulevalue="qrcode.exportExcel">
							<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportType()">Excel导出</a>
						</km:widgetTag>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%-- <div id="dlg_cancle" class="easyui-dialog" style="width: 800px; height: 250px; padding: 10px 5px" closed="true">
		<form id="dlg_cancle_form" method="post" class="form-horizontal" novalidate>
			<input type="hidden"  name="YWLSH">
			<fieldset>
				<legend><img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;"/>门楼牌号撤销编辑</legend>
				 <table>
					 <tr>
					    <th>撤销原因</th>
						<td>
						<textarea name="CXYY" class="easyui-validatebox form-control" data-options=" required:true, validType:'length[0,100]'" style="height:80px; width:600px"></textarea>
						</td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;"  >
				<km:widgetTag widgetRulevalue="mlph.cancel">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="cancelMlphSave()">确 定</a>
				</km:widgetTag>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="closeDlg_cancle();">取 消</a>
			</div>
			
		</form>
	</div> --%>
	<div id="edit-dialog"></div>
	
 <div id="dlg_mlph_zb_mark_pgis"  class="easyui-dialog" style="width: 1000px; height: 500px; padding: 0px 0px" onClose="closeGuihuaDialog()" maximizable="true"  modal="true" closed="true" closable="true"  buttons="#dlg_mlph_zb_mark_buttons">
	<div id="dlg_mlph_zb_mark_buttons">
		  <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="mark();">坐标标注</a>&nbsp;
		  <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:$('#dlg_mlph_zb_mark_pgis').dialog('close')">关闭 </a>&nbsp;
	</div>
	<div id="mymap" class="map" style="height: 100%;"></div>
</div>

<div id="excel_or_select" class="easyui-dialog" style="width: 400px; height: 130px; padding: 10px 20px" closed="true">
			<div class="form-group">
				<label>Excel导出类别选择:</label>
				<select id="ExcelType" style="width: 280px;" class="easyui-combobox" editable="false">
					<option value="01">导出当前页</option>
					<option value="02">导出选择数据</option>
					<option value="03">根据查询条件导出数据</option>
				</select>
			</div>
</div>

	<jsp:include page="../xzqh/xzqh.dialog.jsp">
			<jsp:param name="showXzjd" value="true"  />
			<jsp:param name="showSqjcwh" value="true" />
	</jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/mlph/km.mlph.mark.js"></script>

	<script type="text/javascript">
		var treeOpts = {
			onClick: function(node) {
				var qhdm = node.id.substring(node.id.indexOf('_')+1);	
				var params = {};
				params.SSZDYJXZQY_DZBM = qhdm;
				var jlxxqmc = $('#jlxxqmc-text').val();
				if(jlxxqmc){
					params.JLXXQMC = jlxxqmc;
				}
				 $('#ORGID').val(qhdm);
				$('#jlxGrid').datagrid('load', params);
				//$('#mlphGrid').datagrid('load', {SSZDYJXZQY_DZBM:qhdm});
				$("#sszdyjxzqy_dzbm").val(qhdm);
				loaddata('load');
			}
		};
	
		//init 
		$(function() {
			$('#mlphGrid').datagrid({	
				title:"门楼牌号管理",
				url:'${pageContext.request.contextPath}/psam/mlph/list',
				rownumbers:true,
				fit:true,
				pagination:true,
				fitColumns:false,
				singleSelect:true,
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
						{field:'SPZT',title:'审批状态',width:80,
							//formatter:formatSPZT},
							formatter:function(value,row){
								if(0==value){
									return '<span class="tag-normal">未审核</span>';
								}else if(1==value){return '<span class="tag-fail">区县审核未通过</span>';
								}else if(2==value){return '<span class="tag-success">区县审核已通过</span>';
								}else if(3==value){return '<span class="tag-fail">市局审核未通过</span>';
								}else if(4==value){return '<span class="tag-success">市局审核已通过</span>';
								}else if(5==value){return '<span class="tag-success">审核已通过</span>';}
								else{return '<span class="tag-normal">未审核</span>'};
							}
						},
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
			
			initJLXGrid();
			
			$('#qbtn').click(function() {
				var paramMap = {};
				$('.query-form input').each(function() {
					var val = $(this).val();
					if (val && val.length > 0 && val != '请选择...' && $(this).attr('name')) {
						paramMap['' + $(this).attr('name')] = val;
					}
				});
				$('#mlphGrid').datagrid('load', paramMap);
			});
			
		});//end page init
		
		function addMlph() {
			$('#edit-dialog').dialog({
			    closed: false,
			    cache: false,
			    modal: true,
			    title:"添加门（楼）牌号",
	    		width:980,
	    		height:430,
	    		href:"${pageContext.request.contextPath}/psam/mlph/enterAddMlph"
			});
		}

		function editMlph() {
			var rows = $('#mlphGrid').datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要查看的门楼牌号");
				return;
			}
			$('#edit-dialog').dialog({
			    closed: false,
			    cache: false,
			    modal: true,
			    title:"门楼牌号详细信息",
	    		width:980,
	    		height:500,
	    		href:"${pageContext.request.contextPath}/psam/mlph/enterEditMlph?YWLSH="+rows[0].YWLSH
			});
		}
		
		/* function openDetailDialog(){
		 	var rows = $('#mlphGrid').datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要查看的门楼牌号");
				return;
			}
			$('#edit-dialog').dialog({
			    closed: false,
			    cache: false,
			    modal: true,
			    title:"门楼牌号详细信息",
	    		width:1000,
	    		height:600,
	    		href:"${pageContext.request.contextPath}/psam/mlph/enterDetailMlph?YWLSH="+rows[0].YWLSH
			});
		} */
		
		function openDetailDialog(){
			var rows = $("#mlphGrid").datagrid("getSelections");
			if (rows.length != 1) {
				alertMsg.warn("请选择一条要查看的数据");
				return;
			}
			var url = "${ctx}/psam/mlph/enterDetailMlph?YWLSH="+rows[0].YWLSH;
			var options = {
				title : "门楼牌号详细信息",
				width : 1000,
				height : 600,
				url : url,
				onClosed : function() {
					loaddata('reload');
				}
			};
			editDialog.open(options);
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
		
		function loaddata(reload) {
			var queryParams = $("#mlphGrid").datagrid("options").queryParams;
			KMEASYUtil.genQueryParams(queryParams, $("#mlphform").form().serializeArray());
			$("#mlphGrid").datagrid(reload);
			$("#mlphGrid").datagrid("clearSelections");
		}
		//注销 && 启用
		function enableMlph() {
			var row = $('#mlphGrid').datagrid('getSelected');
			if (row) {
				if(row.DELTAG == '0'){
					alertMsg.warn("正在使用中,不需要启用！");
					return;
				}
				alertMsg.confirm("确定要启用门楼牌号？", {
					cancelCall : function() {
						alertMsg.close();
					},
					okCall : function() {
						$.post('${pageContext.request.contextPath}/psam/mlph/enable', {YWLSH:row.YWLSH}).success(function(data){
							$.messager.alert('提示',$.parseJSON(data).message);
							reloadMlphDg();
						});
						alertMsg.close();
					}
				});
			}else{
				alertMsg.warn("请选择一个门楼牌号");
			}
		}
		function cancelMlph(){
	
			var rows = $("#mlphGrid").datagrid("getSelections");
			if (rows.length != 1) {
				alertMsg.warn("请选择一条要注销的门楼牌号！");
				return;
			}
			if(rows[0].DELTAG == '1'){
				alertMsg.warn("该门楼牌号已注销！");
				return;
			}
			alertMsg.confirm("确定要注销该门楼牌号？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
					alertMsg.close();
					var url="${ctx}/psam/mlph/enterCancleMlph";
					var param={ywlsh:rows[0].YWLSH};
					var options={title:"门楼牌号注销",width:'800',height:'250', url:url,params:param,onClosed:function(){reloadMlphDg();}};
					editDialog.open(options);
				}
			});
		}
		function cancelMlphSave() {
			var cxyy = $('#cxyy-input').val();
			if(cxyy==''){
				alertMsg.warn("撤销原因必须填写！");
				return;
			}
			$.post("${pageContext.request.contextPath}/psam/mlph/cancel",$('#dlg_cancle_form').serialize()).success(function(data){
				$.messager.alert('提示',$.parseJSON(data).message);
				reloadMlphDg();
				$('#dlg_cancle').dialog('close');
			});
		}
		
		
		function openBatchMark(){
			window.open('${pageContext.request.contextPath}/psam/mlph/enterBatchMark', '批量标记','height=' + screen.height + ',width=' + screen.width + ',resizable=yes,scrollbars=yes,toolbar=yes,menubar=yes,location=yes');
		}
		
		function reloadMlphDg(){
			setTimeout(function(){
				$("#mlphGrid").datagrid('reload');
			},2300);
		}
		function openReviewDialog(spzt){
			var row = $('#mlphGrid').datagrid('getSelected');
			if (!row) {
				alertMsg.warn("请选择要审批的门楼牌号");
				return;
			}
			alertMsg.confirm("确定要审批门楼牌号？", {
				cancelCall : function() {
					alertMsg.close();
				},
				okCall : function() {
					alertMsg.close();
					var ajaxUrl = "${ctx}/psam/mlph/reviewMlph";
					var param = {
						"ywlsh" : row.YWLSH,
						"spzt":spzt
					};
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						//$.messager.alert('提示',data.message);
						//loaddata('reload');
						setTimeout(function(){
							alertMsg.warn(data.message);
			             },500);
						reloadMlphDg();
					});
				}
			});
		}
			//加载街路巷信息
			function loadJlx(value,name) {
				var params = {};
				if(value){
					params.JLXXQMC=value;
					var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm").val();
					params.SSZDYJXZQY_DZBM=sszdyjxzqy_dzbm;
				}
				$('#jlxGrid').datagrid('load',params);
			}
			
		 function initJLXGrid(){
				var loadurl="${pageContext.request.contextPath}/psam/jlx/loadJlxBMMCDataSet";
				var jlxGrid = $("#jlxGrid").datagrid({
					 url:loadurl,
					 fit:true,border:false,pagination:true,fitColumns:true,singleSelect:true,rownumbers: true,
					 fixColumnSize:'JLXXQMC',
					 loadFilter:function(data){
						var pager = $("#jlxGrid").datagrid("getPager");
						pager.pagination({
							showPageList : false,
							showRefresh : false
						});
						return data;
					}, onSelect:function(rowIndex,rowData){
						$("#jlxdzbm").val(rowData.DZBM);
						loaddata("reload");
					 }
				});
			}
			function loaddata(reload) {
				var queryParams = $("#mlphGrid").datagrid("options").queryParams;
				KMEASYUtil.genQueryParams(queryParams, $("#mlphform").form().serializeArray());
				$("#mlphGrid").datagrid(reload);
				$("#mlphGrid").datagrid("clearSelections");
				$("#jlxdzbm").val('');
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
			function viewQRCode(){
				var rows = $('#mlphGrid').datagrid("getSelections");
				 if(rows.length == 0){
					alertMsg.warn("请选择要查看的数据");return;
				 }
				 if(rows.length>1){
					alertMsg.warn("请选择一条数据");return;
				}
			    var url="${ctx}/psam/qrcode/enterQRCodePage";
			    var param={code:rows[0].YWLSH,type:"mlph"};
				var options={title:"门楼牌基本信息二维码",width:350,height:360, url:url,params:param,onClosed:function(){loaddata('reload');}};
				editDialog.open(options);
			}
			
 			 function openApplyDialog(){
					var rows = $("#mlphGrid").datagrid("getSelections");
					if (rows.length <1) {
						alertMsg.warn("请选择要申请添加的门楼牌号!");
						return;
					}
					/* for (var i = 0; i < rows.length; i++) {
						if (rows[i].SPZT == '0') {
							alertMsg.warn(rows[i].MLPH + "正在审批中,不要重复申请！");
							return;
						}
					}
					for (var i = 0; i < rows.length; i++) {
						if (rows[i].SPZT == '2'||rows[i].SPZT=="") {
							alertMsg.warn(rows[i].MLPH + "审批已通过,不需申请！");
							return;
						}
					} */
					for (i = 0; i < rows.length; i++) {
						if (rows[i].SPZT != '1'&&rows[i].SPZT!='3') {
							alertMsg.warn(rows[i].DZMC + "不需要再次申请！");
							return;
						}
					}
					var idArray = KMEASYUtil.rowsIdToArray(rows, "YWLSH");
					alertMsg.confirm("确定要申请使用该门楼牌号？", {
						cancelCall : function() {
							alertMsg.close();
						},
						okCall : function() {
							alertMsg.close();
							var ajaxUrl = "${ctx}/psam/mlph/applyUseMlph";
							var param = {
								"ywlshs" : idArray,
								"pzlx":"2"
							};
							KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
								alertMsg.warn(data.message);
								loaddata('reload');
							});
						}
					});
				} 
 			 
 			function exportExcel(type){
 				$("#jzwjbxx_asdfgweqwewqewqdsfjkldjl").remove();
 				if(type=="01"){
 					var rows = $("#mlphGrid").datagrid("getRows");
 					var idArray = KMEASYUtil.rowsIdToArray(rows,"YWLSH");
 					//console.log(idArray);
 				}else if(type=="02"){
 					var rows = $("#mlphGrid").datagrid("getSelections");
 					var idArray = KMEASYUtil.rowsIdToArray(rows,"YWLSH");
 				}else if(type=="03"){
 					var sjgsdwmc=$("#sjgsdwmc").val();
 					var dzmc=$("#dzmc").val();
 					var mlph=$("#mlph").val();
 					var idArray = [sjgsdwmc,dzmc,mlph];
 					/* idArray[dzmc] = dzmc
 					idArray[mlph] = mlph
 					idArray[sjgsdwmc] = sjgsdwmc */
 					//var idArray = {sjgsdwmc:sjgsdwmc,dzmc:dzmc,mlph:mlph};
 				}
 			    $(document.body).append("<div id=\"jzwjbxx_asdfgweqwewqewqdsfjkldjl\"><form method=\"post\" id=\"exportExcel\" action=\"${ctx}/psam/qrcode/exportExcel\"><input type=\"hidden\" name=\"idList\" id=\"idList\" /><input type=\"hidden\" name=\"type\" value=\"mlph\" /><input type=\"hidden\" name=\"orl\" id=\"orl\" /></form></div>");
				$("#idList").val(idArray);
				$("#orl").val(type);
				$("#exportExcel").submit();
			}
 			
 function exportType(){
		$("#excel_or_select").dialog({
			autoOpen:false,
			modal:true,
			title:'Excel导出选择',
			position:'center',
			width:400,
 		height:130,
			buttons:[{
				text:"确定",iconCls:"icon-ok",
				handler:function(){
					var type = $("#ExcelType").combobox("getValue");
					exportExcel(type);
					$("#excel_or_select").dialog("close");
				}
			},{
				text:"取消",iconCls:"icon-cancel",
				handler:function(){
					$("#excel_or_select").dialog("close");
				}
			}]
		}).dialog("open");
} 			
	</script>
</body>
</html>