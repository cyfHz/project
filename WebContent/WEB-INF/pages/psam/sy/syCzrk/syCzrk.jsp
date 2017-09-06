<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ include file="/static/meta/includeall.jsp" %>
<title>常住人口维护</title>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
     <t:DataDict code="MZ" var="mzDict"></t:DataDict>
     <t:DataDict code="XL" var="xlDict"></t:DataDict>
      <t:DataDict code="rygx" var="rygxDict"></t:DataDict> 
       <t:DataDict code="xb" var="xbDict"></t:DataDict> 
       <t:DataDict code="HYZK" var="hyzkDict"></t:DataDict>
       <t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
         <t:DataDict code="byzk" var="byzkDict"></t:DataDict>
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                    <div class="form-group">
                        <th>名称:</th>
                        <input name="xm"  id="xm" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                        <th>身份证号:</th>
                        <input name="gmsfhm" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                    <%--    <km:widgetTag widgetRulevalue="log.loadLogDataGrid"> --%>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					<%-- 	</km:widgetTag> --%>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="syCzrkDataGrid"  style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   		 <km:widgetTag widgetRulevalue="syCzrk.enterDetailSyCzrk"> 
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细</a>
               </km:widgetTag> 
                <km:widgetTag widgetRulevalue="syCzrk.updateSyCzrkAccInfo">  			       
              <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openUpdateDialog()">修改</a>
               </km:widgetTag>  			     
 <!--   <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="revokeCzrkInfo()">注销</a>
 			        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activeCzrkInfo()">启用</a> -->
			</div>	
            </div>
            </div>
            <div id="dlg_chrkdetail_dialog" class="easyui-dialog" style="width: 900px; height: 470px; padding: 10px 10px" closed="true" modal="true" >
            </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
 <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
  $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/sy/syCzrk/loadSyCzrkGrid";
    	 $("#syCzrkDataGrid").datagrid({
				title:"常住人员管理",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true,
				fitColumns: false,//滚动条
				animate:true,
				//collapsible:true,//显示可折叠按钮
				striped:true,//设置为true将交替显示行背景。
				singleSelect:true,//为true时只能选择单行
				pagination : true,//分页
				rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:false,
				idField:'RKID',
				toolbar:"#toolbarDiv",
				frozenColumns : [ [
                     {field:'XM',title:'姓名',width:100,sortable:true},
                     {field:'ZYM',title:'曾用名',width:100,sortable:true},
                     {field:'GMSFHM',title:'身份证号',width:150},
  
                     {field:'XB',title:'性别',width:80,formatter:dictFormat('xbDict')},
                     {field:'LXFSSJ',title:'联系电话',width:110},
                     {field:'CSRQ',title:'出生日期',width:100}
				               	]],
				columns:[[
				     {field:'YHZGX',title:'与户主关系',width:110,formatter:dictFormat('rygxDict')},
				     {field:'XL',title:'学历',width:110,formatter:dictFormat('xlDict')},
				     {field:'BYZK',title:'兵役状况',width:110,formatter:dictFormat('byzkDict')},
				    {field:'ZZMM',title:'政治面貌',width:110,formatter:dictFormat('zzmmDict')},
					{field:'HYZK',title:'婚姻状况',width:110,formatter:dictFormat('hyzkDict')},
				 	{field:'MZ',title:'民族',width:100,formatter:dictFormat('mzDict')}, 
					{field:'XJZDZZ',title:'现居住地址',width:110},
					{field:'XXBC_QQ',title:'QQ',width:110},
					{field:'XXBC_WX',title:'微信',width:110},
					{field:'XXBC_WB',title:'微博',width:110},
					{field:'XXBC_DZYX',title:'电子邮箱',width:110},
					{field:'ZY',title:'职业',width:110},
					{field:'MLXZ',title:'详细地址',width:210}
					/* {field:'ZXZT',title:'注销状态',width:110} */
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#syCzrkDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#syCzrkDataGrid").datagrid(reload);
		$("#syCzrkDataGrid").datagrid("clearSelections");
	}
	function openDetailDialog(){
		 var rows = $("#syCzrkDataGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要查看的常住人员信息");return;
			}
			var url="${ctx}/psam/sy/syCzrk/enterDetailSyCzrk?rkid="+rows[0].RKID;
			 $("#dlg_chrkdetail_dialog").dialog({ title:"常住人口详细信息", href:url, modal:true });
			 $("#dlg_chrkdetail_dialog").dialog("open");
	}
	function openUpdateDialog(){
		var rows = $("#syCzrkDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要查看的常住人员信息");return;
		}
		var url="${ctx}/psam/sy/syCzrk/enterUpdateCzrkAccInfo";
	    var param={rkid:rows[0].RKID};
		var options={title:"常住人口信息修改",width:900,height:470, url:url,params:param,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
	}
	function revokeCzrkInfo(){
		   var rows = $("#syCzrkDataGrid").datagrid("getSelections");
		   if(rows.length!=1){
			   alertMsg.warn("请选择一条要注销的人员信息");
			   return;
		   }
		   if(rows[0].ZXZT=='1'){
				 alertMsg.warn("该人员信息已注销！");
				 return;
			 }
		   alertMsg.confirm("确定要注销该人员信息吗？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/sy/syCzrk/cancelCzrk";
						var param = { "id" : rows[0].RKID};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddata('reload');
						});
					}
			});
	}
	function activeCzrkInfo(){
		 var rows = $("#syCzrkDataGrid").datagrid("getSelections");
		   if(rows.length!=1){
			   alertMsg.warn("请选择一条要启用的人员信息");
			   return;
		   }
		   if(rows[0].ZXZT=='0'){
				 alertMsg.warn("该人员已是启用状态！");
				 return;
			 }
		
		   alertMsg.confirm("确定要启用该人员信息？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/sy/syCzrk/activatesyCzrk";
						var param = { "id" : rows[0].RKID};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddata('reload');
						});
					}
			});
		}
    </script>
</body>
</html>