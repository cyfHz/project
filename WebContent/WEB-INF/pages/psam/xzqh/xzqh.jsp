<%@ include file="/static/meta/meta.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/easyui.jsp" %>
<%@ include file="/static/meta/easyuipub.jsp" %>
<%@ include file="/static/meta/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv="X-UA-Compatible" content="edge" /> -->
<title>行政区划管理</title>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	<t:DataDict code="DZYSFL"  var="dzyslxDict"></t:DataDict>
	<t:DataDict code="DZYSFL" subCode="10" var="dzyslxDicts"></t:DataDict>
	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<div data-options="region:'west',title:'行政区划',split:true" style="width: 200px;">
		<ul id="xzqhTree" class="easyui-tree"></ul>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 60px; background-color: #F4F4F4;">
				<form class="form-inline query-form form-horizontal">
				    <input type="hidden" id="q_sjxzqy_dzbm" name="SJXZQY_DZBM" value="">
					<div class="form-group">
						<th>区划名称:</th> <input name="XZQHMC" type="text" class="form-control">
					</div>
					<div class="form-group">
						<th>区划代码:</th> <input name="XZQHDM" type="text" class="form-control">
					</div>
				<!-- 	<div class="form-group">
						<label>地址元素类型:</label> <input id="q_dzyslx" dict="dzyslxDict" name="DZYSLXDM" >
					</div> -->
					<div class="form-group">
						<th>使用状态:</th> <input id="q_syzt" dict="syztDict" name="SYZTDM" class="form-control" > 
					</div>
					<div class="form-group">
					   <km:widgetTag widgetRulevalue="xzqh.list">
		         	     <a id="qbtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
 						</km:widgetTag>
					</div>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<table id="xzqhdg">
				</table>
				<div id="toolbar">
				      <km:widgetTag widgetRulevalue="xzqh.add">
		         	     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newXzqh()">添加</a>
 				</km:widgetTag>
 				         <km:widgetTag widgetRulevalue="xzqh.save">
		         	     <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editXzqh()">修改</a>
 				</km:widgetTag>
 				    <km:widgetTag widgetRulevalue="xzqh.cancel">
		         	     <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="cancelXzqh()">注销</a>
 				</km:widgetTag>
 				    <km:widgetTag widgetRulevalue="xzqh.activate">
		         	     <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="activateXzqh()">启用</a>
 				</km:widgetTag>
				<km:widgetTag widgetRulevalue="xzqh.detail">
		         	     <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="detailXzqh()">详细信息</a>
 				</km:widgetTag>
				<%-- 	 <km:widgetTag widgetRulevalue="xzqh.importdata">
		         	    <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="">导入</a>
 				</km:widgetTag> --%>
 				
				</div>
			</div>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 360px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<form style="display: none;" id="xzqhfm"  method="post" novalidate="novalidate">
			<fieldset>
				<legend><img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;"/>行政区划信息添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <th>行政区划代码</th>
					    <td>
				          <input type="hidden"  name="DZBM">
				          <input type="text" name="XZQHDM" id="XZQHDM" class=" easyui-validatebox form-control" data-options="required:true ,validType:'length[1,12]'" />
					    </td>  
					 </tr>
					 <tr>
					    <th>行政区划名称</th>
					    <td>
				          <input id="XZQHMC" name="XZQHMC" class=" easyui-validatebox form-control"  data-options="required:true ,validType:'length[1,30]'" />
					    </td>  
					 </tr>
					 <tr>
					    <th>别名简称</th>
					    <td>
				          <input id="BMJC" name="BMJC" class=" easyui-validatebox " data-options="required:true ,validType:'length[1,30]'" />
					    </td>  
					 </tr>
					 <tr>
					    <th>使用状态</th>
					    <td>
				           <input dict="syztDict" id="syzt_syz_input" name="SYZTDM" class="  easyui-validatebox" style="width:175px;" data-options="required:true ,editable:false"/>
					    </td>  
					 </tr>
					 <tr>
					    <th>地址元素类型</th>
					    <td>
				           <input id="dzyslxdm" dict="dzyslxDicts" name="DZYSLXDM"   class=" easyui-validatebox" style="width: 175px;" data-options="required:true ,editable:false" />
					    </td>  
					 </tr>
					 <tr>
					    <th>上级行政区域</th>
					    <td>
				          <input id="text_sjxzqymc" onclick="openXzqhTreeDialog('setSjxzbm')" name="SJXZQYMC" class=" easyui-validatebox" readonly="readonly" data-options="required:true"/>
				          <input type="hidden" id="text_sjxzqy_dzbm" name="SJXZQY_DZBM" value="">
					    </td>  
					 </tr>
					 <tr>
					    <th>设立日期</th>
					    <td>
				          <input type="text" name="SLRQ" class="form-control easyui-datebox"  style="width:175px;" data-options="editable:false"/>
					    </td>  
					 </tr>
				 </table>
			</fieldset>
			
			
			<!-- <div class="form-group">
				<input type="hidden"  name="DZBM">
				<label>行政区划代码:</label> <input name="XZQHDM" id="XZQHDM" class="easyui-textbox easyui-validatebox " data-options="required:true ,validType:'length[1,12]'" />
			</div>
			<div class="form-group">
				<label>行政区划名称:</label> <input id="XZQHMC" name="XZQHMC" class="easyui-textbox easyui-validatebox "  data-options="required:true ,validType:'length[1,30]'" />
			</div>
			<div class="form-group">
				<label>别名简称:</label> <input id="BMJC" name="BMJC" class="easyui-textbox easyui-validatebox " data-options="required:true ,validType:'length[1,30]'" />
			</div>
			 
			<div class="form-group">
				<label>助记符:</label> <input name="ZJF" class="form-control easyui-validatebox" data-options="validType:'length[1,60]'" />
			</div>
			
			<div class="form-group">
				<label>使用状态:</label> <input dict="syztDict" id="syzt_syz_input" name="SYZTDM" class=" easyui-validatebox" style="width:175px;" data-options="editable:false"/>
			</div>
			<div class="form-group">
				<label>地址元素类型:</label> 
				<input id="dzyslxdm" dict="dzyslxDicts" name="DZYSLXDM"   class="form-control" style="width: 175px;" data-options="editable:false" />
			</div>
			<div class="form-group">
				<label>上级行政区域:</label> <input id="text_sjxzqymc" onclick="openXzqhTreeDialog('setSjxzbm')" name="SJXZQYMC" class="form-control" readonly="readonly"/>
				<input type="hidden" id="text_sjxzqy_dzbm" name="SJXZQY_DZBM" value="">
			</div>
			<div class="form-group">
				<label>设立日期:</label> 
				<input type="text" name="SLRQ" class="form-control easyui-datebox"  style="width:175px;" data-options="editable:false"/>
			</div> -->
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-ok" onclick="saveXzqh()" style="width: 90px">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel('取消修改？')" style="width: 90px">关闭</a>
	</div>
	
	<jsp:include page="./xzqh.dialogXzjd.jsp">
			<jsp:param name="showXzjd" value="false"  />
			<jsp:param name="showSqjcwh" value="false" />
	</jsp:include>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script type="text/javascript">


var url;
$(function() {
	$('#qbtn').click(function() {
		var paramMap = {};
		$('.query-form input').each(function() {
			var val = $(this).val();
			if (val && val.length > 0 && val != '请选择...' && $(this).attr('name')) {
				paramMap['' + $(this).attr('name')] = val;
			}
		});
		$('#xzqhdg').datagrid('load', paramMap);
	});
	loadTree();
	
	$('#xzqhdg').datagrid({
		url:'${pageContext.request.contextPath}/psam/xzqh/list',
		title:"行政区划管理",
	    idField:'DZBM',
	    rownumbers:true,
	    fit:true,
	    pagination:true,
	    fitColumns:true,
	    checkbox:true,
	    singleSelect:true,
	    toolbar:'#toolbar',
	    columns:[[
			{field:'XZQHDM',title:'行政区划代码',sortable:true,width:50},
			{field:'XZQHMC',sortable:true,title:'行政区划名称',width:50},
			{field:'ZJF',title:'助记符',width:50},
			{field:'SYZTDM',title:'使用状态',width:50,formatter:dictFormat('syztDict')},
			{field:'DZYSLXDM',title:'地址元素类型',width:50,formatter:dictFormat('dzyslxDict')},
			{field:'SJXZQYMC',title:'上级行政区域',width:50},
			{field:'CXBJ',title:'撤销标记',width:50,formatter:formatCxbj}
	    ]],
	    rowStyler:function(index,row){
	    	if(row.SYZTDM==20){
	    		return 'color: #5e5e5e;background-color: #E4E4E4;'
	    		}
	   },loadFilter:function(data){
			return KMCORE.ajaxDoneForServerError(data);
	  }
	    
	});
});
/**
 * 加载左侧行政区划菜单
 */
function loadTree() {
		$('#xzqhTree').tree($.extend({
			url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:false,showSqjcwh:false}),
			loadFilter: function(data) {
				if (!data || !data.rows || data.rows.length < 1) {
					return [];
				}
				var nodes = [];
				$(data.rows).each(function(i, row) {
					var node = {};
					node.id = row.NODETYPE+'_'+row.NODEID;
					node.text = row.NODETEXT;
					node.state = row.NODETYPE == 'XZJD' ? "open":"closed";
					//node.state=$('#xzqhTree').tree('isLeaf',$('#xzqhTree').tree('getRoot').target)?"open":"closed";
					nodes.push(node);
				});
				return nodes;
			},
			cascadeCheck:false,
			checkbox:false,
			onLoadSuccess: function() {
				$('#xzqhTree').tree('expand',$('#xzqhTree').tree('getRoot').target);
				/* $('#xzqhTree').tree('getChildren',$('#xzqhTree').tree('getRoot').target);
				$('#xzqhTree').tree('isLeaf',$('#xzqhTree').tree('getRoot').target);	 */
			}
		},typeof treeOpts === "undefined" ? {}:treeOpts));
	}
	var treeOpts = {
			onClick: function(node) {
				$('#q_sjxzqy_dzbm').val(node.id.substring(node.id.indexOf('_')+1));
				$('#qbtn').click();
				//$('#q_sjxzqy_dzbm').val('');
			}
	};
	
	/* function newXzqh() {
		var row={XZQHDM:null,XZQHMC:null,BMJC:null,SYZTDM:"10",DZYSLXDM:null,SJXZQYMC:null,SLRQ:null};
		$('#xzqhfm').show();	
		$('#dlg').dialog('open').dialog('center').dialog('setTitle','添加行政区划');
		$("#XZQHDM").removeProp("readOnly");
		$('#xzqhfm').form('clear');
		$('#xzqhfm').form('load', row);
		url = '${pageContext.request.contextPath}/psam/xzqh/add';
	} */

	/* function editXzqh() {
		$('#xzqhfm').show();
		var row = $('#xzqhdg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改行政区划');
			$('#xzqhfm').form('load', row);
			$("#XZQHDM").prop("readOnly",true);
			url = '${pageContext.request.contextPath}/psam/xzqh/save';
		}else{
			alertMsg.warn("请选择一个行政区划");
		}
	} */
	
	function newXzqh() {
		var url="${ctx}/psam/xzqh/enterAddXzqh";
		var options={title:"行政区划信息添加页面",width:360,height:400, url:url,params:{},onClosed:function(){reloadXzqhDg();}};
		editDialog.open(options);
	}
	function editXzqh() {
		 var rows = $("#xzqhdg").datagrid("getSelections");
		 if(rows.length != 1){
			 alertMsg.warn("请选择一条要查看的行政区划");return;
		 }
		 if(rows[0].CXBJ=='1'){
			 alertMsg.warn("该行政区划已被撤销，不能修改");
			 return;
		 }
		 var param={dzbm:rows[0].DZBM};
		 var url="${ctx}/psam/xzqh/enterEditXzqh";
		 var options={title:"行政区划信息修改页面",width:360,height:400, url:url,params:param,onClosed:function(){reloadXzqhDg();}};
		 editDialog.open(options);
	}
	
	function detailXzqh() {
		var rows = $('#xzqhdg').datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要查看的行政区划");
			return;
		}
		var url="${pageContext.request.contextPath}/psam/xzqh/enterDetailXzqh";
		var param={DZBM:rows[0].DZBM};
	    var options={
	    		title:"行政区划详细信息",
	    		width:900,
	    		height:400,
	    		url:url,
	    		params:param,
	    		onClosed:function(){}
	    };
		editDialog.open(options);
	}
	function saveXzqh() {
		/* $.post(url, $('#xzqhfm').serialize()).success(function(data){
			$.messager.alert('提示',$.parseJSON(data).message);
			reloadXzqhDg();
		}); */
		if(PUBUtil.isSpecialCharacter($("#XZQHMC").val())){
			$.messager.alert('提示',"行政区划名称中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#BMJC").val())){
			$.messager.alert('提示',"别名简称中不能包含特殊字符");	
			return;
		}
		 var parm=$('#xzqhfm').serializeArray();
		 KMAJAX.ajaxTodo(url, parm, function(data){
			 if(data.statusCode==200){
				 alertMsg.info(data.message);
				 reloadXzqhDg();
			 }else{
				 alertMsg.error(data.message);
			 }
		  		
		 });
	}

	function cancelXzqh() {
		var rows = $("#xzqhdg").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要注销的行政区划！");
			return;
		}
		
		if(rows[0].CXBJ == '1'){
			alertMsg.warn("该行政区划已注销！");
			return;
		}
		alertMsg.confirm("确定要注销该行政区划？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() {
				alertMsg.close();
				var url="${ctx}/psam/xzqh/enterCancleXzqh";
				var param={dzbm:rows[0].DZBM};
				var options={title:"行政区划注销",width:'800',height:'250', url:url,params:param,onClosed:function(){reloadXzqhDg('reload');}};
				editDialog.open(options);
			}
		});
	}
	function cancelXzqhSave() {
		var cxyy = $('#cxyy-input').val();
		if(cxyy==''){
			alertMsg.warn("撤销原因必须填写！");
			return;
		}
		var ajaxUrl="${pageContext.request.contextPath}/psam/xzqh/cancel";
		var param=$('#dlg_cancle_form').serialize();
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			alertMsg.warn(data.message);
			reloadXzqhDg();
			$('#dlg_cancle').dialog('close');
		});
		/* $.post("${pageContext.request.contextPath}/psam/xzqh/cancel",
				$('#dlg_cancle_form').serialize()).success(function(data){
			$.messager.alert('提示',$.parseJSON(data).message);
			reloadXzqhDg();
			$('#dlg_cancle').dialog('close');
		}); */
	}
	
	function activateXzqh() {
		var row = $('#xzqhdg').datagrid('getSelected');
		if (row) {
			if(row.SYZTDM=='10'){
				alertMsg.warn("使用中，不需要启用！");
				return;
			}
			var ajaxUrl="${pageContext.request.contextPath}/psam/xzqh/activate";
			var param={DZBM:row.DZBM};
			KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
				alertMsg.warn(data.message);
				reloadXzqhDg();
			});
			/* $.post('${pageContext.request.contextPath}/psam/xzqh/activate', {DZBM:row.DZBM}).success(function(data){
				$.messager.alert('提示',$.parseJSON(data).message);
				reloadXzqhDg();
			}); */
		}else{
			alertMsg.warn("请选择一个行政区划");
		}
	}
	function setSjxzbm(node){
		$('#text_sjxzqy_dzbm').val(node.id);
		$('#text_sjxzqymc').val(node.text);
	}
	
	function  formatCxbj(value){
		if('1'==value){
			return '<span class="tag-normal">已撤销</span>';
		}else{
			return '<span class="tag-success">正常</span>';
		}
	}
	
	function reloadXzqhDg(){
		$('#dlg').dialog('close');
		$('#xzqhfm').form('clear');
		$("#xzqhdg").datagrid('reload');
	}
	function parserDate(date){
		var t = Date.parse(date);
		if (!isNaN(t)){
			return new Date(t);
		} else {
			return new Date();
		}
	}
	
	function closeDlg_cancle(){
		$('#dlg_cancle').dialog('close');
		$('#dlg_cancle_form').form('clear');
	}
	
	function cancel(msg){
		
		/* alertMsg.confirm(msg,{
			cancelCall:function(){
				alertMsg.close();
			},
			okCall:function(){
				$('#dlg').dialog('close') 
				$('#dlg_cancle').dialog('close');
				alertMsg.close();
			}
		}); */
		$('#dlg').dialog('close') 
		$('#dlg_cancle').dialog('close');
		alertMsg.close();
	}
	</script>
</body>
</html>