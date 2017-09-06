<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/static/meta/includeall.jsp" %>
<title>境外人员维护</title>
</head>
           <body class="easyui-layout" data-options="fit:true,border:false">
           <t:DataDict code="gj" var="gjDict"></t:DataDict>
           <t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
           <t:DataDict code="qzzl" var="qzzlDict"></t:DataDict>
          <input type="hidden" name="jzwfjid" id="jzwfjid" value="${jzwfjid}">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                    <div class="form-group">
                        <th>英文名称:</th>
                        <input name="ywm"  id="ywm" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                        <th>证件号码:</th>
                        <input name="zjhm" type="text" class="easyui-textbox form-control">
                    </div>
                    <div class="form-group">
                    <%--    <km:widgetTag widgetRulevalue="log.loadLogDataGrid"> --%>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					<%-- 	</km:widgetTag> --%>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="syJwryDataGrid"  style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   		  <km:widgetTag widgetRulevalue="syJwry.enterDetailSyJwry"> 
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细</a>
                   </km:widgetTag> 
                    <km:widgetTag widgetRulevalue="syJwry.updateSyJwryAccInfo"> 			       
                   <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openUpdateDialog()">修改</a>
                    </km:widgetTag>     
	   <!-- 		     
                  <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="revokeJwryInfo()">注销</a>
 			        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activeJwryInfo()">启用</a -->
 			<%-- 	 --%>
			</div>	
            </div>
        
            <div id="dlg_jwrydetail_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
              <div id="dlg_jwryupdate_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
 <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
 <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/sy/syJwry/loadSyJwryGrid";
    	$("#syJwryDataGrid").datagrid({
				title:"实有境外人员管理",
				
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
				idField:'JWRYID',
				toolbar:"#toolbarDiv",
				frozenColumns : [ [
                 {field:'YWM',title:'英文名',width:100,sortable:true},
                 {field:'YWX',title:'英文姓',width:100,sortable:true},
                 {field:'ZWM',title:'中文名',width:100,sortable:true},
                 {field:'ZJHM',title:'证件号码',width:110},
                 {field:'JWRYLXDH',title:'联系电话',width:110},
                 {field:'CSRQ',title:'出生日期',width:100}
			 ]],
				columns:[[
					
				    {field:'GJ',title:'国籍',width:100,formatter:dictFormat('gjDict')},
				    {field:'ZJZL',title:'证件种类',width:100,formatter:dictFormat('zjlxDict')},
				    {field:'QZZL',title:'证件种类',width:100,formatter:dictFormat('qzzlDict')},
				    {field:'TLYXQ',title:'停留有效期',width:110},
					{field:'FWFZXM',title:'房屋房主姓名',width:110},
					{field:'FZSFZH',title:'房主身份证号',width:110},
					{field:'ZY',title:'职业',width:110},
					{field:'XXDZ',title:'现居住地详细地址',width:200}
					
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#syJwryDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#syJwryDataGrid").datagrid(reload);
		$("#syJwryDataGrid").datagrid("clearSelections");
	}
	function openDetailDialog(){
		 var rows = $("#syJwryDataGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要查看的境外人员信息");return;
			}
			var url="${ctx}/psam/sy/syJwry/enterDetailSyJwry?jwryid="+rows[0].JWRYID;
			
		     
			 $("#dlg_jwrydetail_dialog").dialog({ title:"境外人员详细信息", href:url, modal:true });
			 $("#dlg_jwrydetail_dialog").dialog("open");
	}
	function openUpdateDialog(){
		var rows = $("#syJwryDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要查看的境外人员信息");return;
		}
		   var url="${ctx}/psam/sy/syJwry/enterUpdateSyJwryAccInfo";
		  
		    var param={jwryid:rows[0].JWRYID};
			var options={title:"境外人员信息修改",width:900,height:470, url:url,params:param,onClosed:function(){loaddata('reload');}};
			editDialog.open(options);
	}
	function revokeJwryInfo(){
		   var rows = $("#syJwryDataGrid").datagrid("getSelections");
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
						var ajaxUrl = "${ctx}/psam/sy/syJwry/cancelsyJwry";
						var param = { "id" : rows[0].JWRYID};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddata('reload');
						});
					}
			});
	}
	function activeJwryInfo(){
		 var rows = $("#syJwryDataGrid").datagrid("getSelections");
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
						var ajaxUrl = "${ctx}/psam/sy/syJwry/activatesyJwry";
						var param = { "id" : rows[0].JWRYID};
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