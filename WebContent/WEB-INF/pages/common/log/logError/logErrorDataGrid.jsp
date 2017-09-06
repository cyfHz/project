<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>
</head>
<body class="easyui-layout">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="searchform">
                   <!--  <div class="form-group">
                        <label>设备ID</label>
                        <input name="sbid" type="text" class="easyui-textbox form-control">
                    </div> -->
                     <div class="form-group">
                        <th>起始时间:</th>
                        <input name="start_time" type="text" class="easyui-datetimebox form-control">
                    </div>
                     <div class="form-group">
                        <th>截止时间:</th>
                        <input name="end_time" type="text" class="easyui-datetimebox form-control">
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="logError.loadLogErrorDataGrid">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
						</km:widgetTag>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="logErrorDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
 				<%--  <km:widgetTag widgetRulevalue="logError.enterLogErrorDetail">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="assignedJy()">详细</a>
 				</km:widgetTag> --%>
			</div>	
            </div>
 <script type="text/javascript">
   $(function() {
    	initDataGrid();
   });
    function initDataGrid(){
    	var loadurl="${ctx}/common/log/logError/loadLogErrorDataGrid";
    	 $("#logErrorDataGrid").datagrid({
				title:"系统日志管理",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:false,
				idField:'ID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
					{field:'SBID',title:'设备ID',width:70,sortable:true},
					{field:'LX',title:'数据类型',width:70,formatter:function(val,row){
						if ('P'==val){ return '后台'; } else if('1'==val){ return '终端';}
					}},
					{field:'LOGLEVEL',title:'日志级别',width:60},
					{field:'CREATETIME',title:'日志时间',width:120,formatter:formatDateboxFull},
					{field:'CLASSES',title:'类',width:180},
					{field:'MOTHOD',title:'方法',width:180},
					{field:'MSG',title:'内容',width:110}
				]],loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
    function formatDateboxFull(value) {  
        if (value == null || value == '') {  
            return '';  
        }  
        var unixTimestamp = new Date(value);  
        return unixTimestamp.toLocaleString(); 
        //var dt = parseToDate(value);  
        //return dt.format("yyyy-MM-dd hh:mm:ss");  
    }  
	function loaddata(reload) { 
	  	var queryParams =$("#logErrorDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
		$("#logErrorDataGrid").datagrid(reload);
		$("#logErrorDataGrid").datagrid("clearSelections");
	}
</script>
</body>
</html>