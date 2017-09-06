<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/static/meta/includeall.jsp" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html > 
<head>

<title></title>
</head>
<body class="easyui-layout">
<t:DataDict code="SFYY" var="sfyyDict"></t:DataDict>
<t:DataDict code="XZJKDZ" var="xzjkdzDict"></t:DataDict>
           
            <div data-options="region:'center',border:false">
            <table id="bzdzUserDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		   	<km:widgetTag widgetRulevalue="bzdzRejectIp.addRejectIp">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>
			 	</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzRejectIp.updateIpSfyx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="removeRejectIp('1')">取消限制</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="bzdzRejectIp.updateIpSfyx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="removeRejectIp('0')">限制</a>
				</km:widgetTag>
				<%-- <km:widgetTag widgetRulevalue="bzdzRejectIp.saveXzjkRejectIp">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateXZJk()">修改限制IP接口</a>
				</km:widgetTag> --%>
				
			</div>	
            </div>
            <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
 <script type="text/javascript">

//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/psam/webservice/bzdzRejectIp/loadBzdzRejectIpAll";
    	  treeDrid = $("#bzdzUserDataGrid").datagrid({
				title:"限制IP管理列表",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:true,
				idField:'IPID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
				    //{field:'ipid',checkbox:true,width:110},
					{field:'IP',title:'限制ip',width:100,sortable:true},
					{field:'SFYY',title:'是否有效',width:100,formatter:formatSfyx},
					{field:'TJSJ',title:'添加时间',width:110},
					/* {field:'IPXZJK',title:'限制接口',width:110,formatter:dictFormat('xzjkdzDict')}, */
					{field:'XGSJ',title:'修改时间',width:110},
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    
	function loaddata(reload) { 
	  	var queryParams =$("#bzdzUserDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#bzdzUserDataGrid").datagrid(reload);
		$("#bzdzUserDataGrid").datagrid("clearSelections");
	}

	function openAddDialog(){
		var url="${ctx}/psam/webservice/bzdzRejectIp/enterAddBzdzRejectIp";
		var params={};
		var options={title:"添加限制IP信息编辑页面",width : 300, height : 360,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}
	
	function removeRejectIp(sfyy){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		alertMsg.confirm("确认进行IP限制是否有效操作？", {
			cancelCall : function() {
				alertMsg.close();
			},
			okCall : function() {
				alertMsg.close();
				var url="${ctx}/psam/webservice/bzdzRejectIp/updateIpSfyx";
				var params={ipid:rows[0].IPID,sfyy:sfyy,ip:rows[0].IP};
				KMAJAX.ajaxTodo(url, params, function(data) {
					//$.messager.alert('提示',data.message);
					alertMsg.info(data.message);
					loaddata('reload');
				});
			}
		});
		
		
	}
	
	function openeditDialog(){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		var url="${ctx}/psam/webservice/bzdzRejectIp/enterUpdateBzdzRejectIp";
		var params={ipid:rows[0].IPID};
		var options={title:"修改限制IP信息编辑页面",width : 760, height : 400,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}
	
	function updateXZJk(){
		var rows = $("#bzdzUserDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条记录！");
			return;
		}
		var url="${ctx}/psam/webservice/bzdzRejectIp/enterRejectIpXzjk";
		var params={ipid:rows[0].IPID};
		var options={title:"修改限制IP页面",width : 350, height : 400,
				url : url, params :params ,
				onClosed : function() {
					loaddata('reload');
				}}
		editDialog.open(options);
	}
	
	function  formatSfyx(value){
		if('1'==value){
			return '<span class="tag-normal">无效</span>';
		}else{
			return '<span class="tag-success">有效</span>';
		}
	}
    </script>
</body>
</html>