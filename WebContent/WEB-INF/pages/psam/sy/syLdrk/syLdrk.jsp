<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ include file="/static/meta/includeall.jsp" %>
<title>流动人员维护</title>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="HYZK" var="hyzkDict"></t:DataDict>
<t:DataDict code="XL" var="xlDict"></t:DataDict>
<t:DataDict code="JZSY" var="jzsyDict"></t:DataDict>
<t:DataDict code="NJZQX" var="njzqxDict"></t:DataDict>
 <t:DataDict code="xb" var="xbDict"></t:DataDict> 
 <t:DataDict code="byzk" var="byzkDict"></t:DataDict>
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                    <div class="form-group">
                        <th>姓名:</th>
                        <input name="xm" type="text" class="easyui-textbox form-control"></input>
                    </div>
                    <div class="form-group">
                        <th> 身份证号:</th>
                        <input name="sfzh" type="text" class="easyui-textbox form-control"></input>
                    </div>
                    <div class="form-group">
                    <%--    <km:widgetTag widgetRulevalue="log.loadLogDataGrid"> --%>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
					<%-- 	</km:widgetTag> --%>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="syLdrkDataGrid"  style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   		  <km:widgetTag widgetRulevalue="syLdrk.enterDetailSyLdrk"> 
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细</a>
                    </km:widgetTag> 
                     <km:widgetTag widgetRulevalue="syLdrk.updateSyLdrkAccInfo"> 			       
                    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openUpdateDialog()">修改</a>
                     </km:widgetTag>  			 
  <!--      <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="revokesyLdrkInfo()">注销</a>
 			        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activesyLdrkInfo()">启用</a> -->
 			        
 			<%-- 	 --%>
			</div>	
            </div>
          
            <div id="dlg_ldrkdetail_dialog" class="easyui-dialog" style="width: 900px; height: 470px; padding: 2px 2px" closed="true" modal="true" >
            </div>
              <div id="dlg_ldrkupdate_dialog" class="easyui-dialog" style="width: 900px; height: 470px; padding: 2px 2px" closed="true" modal="true" >
            </div>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
 <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/sy/syLdrk/loadSyLdrkGrid";
    	  treeDrid = $("#syLdrkDataGrid").datagrid({
				title:"实有流动人员管理",
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
				idField:'LDID',
				toolbar:"#toolbarDiv",
				frozenColumns : [ [
                     {field:'XM',title:'姓名',width:100,sortable:true},
                     {field:'BM',title:'别名',width:100},
                     {field:'SFZH',title:'身份号码',width:140},
                     {field:'XB',title:'性别',width:80,formatter:dictFormat('xbDict')},
                     {field:'CSRQ',title:'出生日期',width:110/* ,formatter:function(data){
                    	 if(data.length>10){ return data.substr(0, 10); }
                    	 else{
                    		 return null;
                    	 }} */},
                     {field:'LXFS_SJ',title:'联系电话',width:120}
                   ]],
				columns:[[
				    {field:'FBYQK',title:'服兵役情况',width:100,formatter:dictFormat('byzkDict')}, 
				    {field:'MZ',title:'民族',width:100,formatter:dictFormat('mzDict')}, 
					{field:'HYZK',title:'婚姻状况',width:110,formatter:dictFormat('hyzkDict')},
					{field:'XL',title:'学历',width:110,formatter:dictFormat('xlDict')},
				    {field:'ZZMM',title:'政治面貌',width:110,formatter:dictFormat('zzmmDict')},
				    {field:'JZSY',title:' 居住事由',width:120,formatter:dictFormat('jzsyDict')},
				    {field:'DDBDRQ',title:'到达本地日期',width:120/* ,formatter:function(data){
                   	 if(data.length>10){ return data.substr(0, 10);}
                   	 else{
                   		 return null;
                   	 } 
				    } */},
				    {field:'NJZQX',title:'拟居住期限',width:120,formatter:dictFormat('njzqxDict')},
				    {field:'SFSLJZZ',title:'是否申领居住证',width:120,formatter:function(value,row){
						if(0==value){ return "<font color=red>否<font>";
						} else if(1==value){ return "<font color=green>是<font>"; }}},
						{field:'LH',title:'房间号',width:110},
						{field:'DYH',title:'单元号',width:110},
						{field:'FH',title:'房间号',width:110},
					    {field:'XXDZ',title:'现居住地详细地址',width:210}
					
					/* {field:'ZXBZ',title:'注销标志',width:110} */
								
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#syLdrkDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#syLdrkDataGrid").datagrid(reload);
		$("#syLdrkDataGrid").datagrid("clearSelections");
	}
	function openDetailDialog(){
		 var rows = $("#syLdrkDataGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要查看的流动人员信息");return;
			}
			var url="${ctx}/psam/sy/syLdrk/enterDetailSyLdrk?rkid="+rows[0].LDID;
			 $("#dlg_ldrkdetail_dialog").dialog({ title:"流动人口详细信息", href:url, modal:true });
			 $("#dlg_ldrkdetail_dialog").dialog("open");
	}
	function openUpdateDialog(){
		var rows = $("#syLdrkDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要查看的流动人员信息");return;
		}
	    	var url="${ctx}/psam/sy/syLdrk/enterUpdateLdrkAccInfo";
		  var param={rkid:rows[0].LDID}; 
			var options={title:"流动人员信息修改",width:900,height:470, url:url,params:param,onClosed:function(){loaddata('reload');}};
			editDialog.open(options);
	}
	function revokesyLdrkInfo(){
		 var rows = $("#syLdrkDataGrid").datagrid("getSelections");
		   if(rows.length!=1){
			   alertMsg.warn("请选择一条要注销的人员信息");
			   return;
		   }
		   if(rows[0].ZXBZ=='1'){
				 alertMsg.warn("该人员信息已注销！");
				 return;
			 }
		   alertMsg.confirm("确定要注销该人员信息吗？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/sy/syLdrk/cancelLdrk";
						var param = { "id" : rows[0].LDID};
						KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							KMCORE.ajaxDone(data);
							loaddata('reload');
						});
					}
			});
		
	}
	function activesyLdrkInfo(){
		var rows = $("#syLdrkDataGrid").datagrid("getSelections");
		   if(rows.length!=1){
			   alertMsg.warn("请选择一条要启用的人员信息");
			   return;
		   }
		   if(rows[0].ZXBZ=='0'){
				 alertMsg.warn("该人员已是启用状态！");
				 return;
			 }
		
		   alertMsg.confirm("确定要启用该人员信息？", {
				cancelCall : function() {alertMsg.close();},
				okCall : function() {
						alertMsg.close();
						var ajaxUrl = "${ctx}/psam/sy/syLdrk/activatesyLdrk";
						var param = { "id" : rows[0].LDID};
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