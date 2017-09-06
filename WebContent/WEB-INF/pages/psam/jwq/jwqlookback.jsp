<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
        <div class="easyui-layout" data-options="fit:true" >
            <div data-options="region:'north',border:false" style="height:40px; background-color: #F4F4F4; padding: 0px;overflow: hidden;">
             <%--  <input type="hidden" name="sszdyjxzqy_dzbm" id="sszdyjxzqy_dzbm" value="${sszdyjxzqy_dzbm}"   /> --%>
             <form class="form-inline query-form" id="jwqSearchform_as">
               <!--   <input type="hidden" id="xzqhmcq" name="webParams[xzqhmcq]" /> -->
                    <div class="form-group">
                        <label>警务区名称:</label>
                        <input name="webParams['jwqmc']" type="text" class="easyui-textbox"/>
                    </div>
                    <div class="form-group">
                        <label>警务区编号:</label>
                        <input name="webParams['jwqbh']" type="text" class="easyui-textbox"/>
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="jwq.jwqList"></km:widgetTag>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="jwqDataGrid1" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="comfirmData()">确认</a>
			</div>	
            </div>
        </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
    <script type="text/javascript">
    $(function() {
    	initDataGrid();
    });
    function initDataGrid(){
    	//var loadurl="${ctx}/psam/jwq/jwqList";
    	var loadurl="${ctx}/psam/jwq/jwqListForLookBack";
    	
    	  treeDrid = $("#jwqDataGrid1").datagrid({
				title:"警务区数据列表",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true,
				fitColumns: true,//滚动条
				animate:true,
				collapsible:false,//显示可折叠按钮
				striped:true,//设置为true将交替显示行背景。
				singleSelect:false,//为true时只能选择单行
				pagination : true,//分页
				rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:true,
				idField:'JWQID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[ //username ,idnum,sex,userId
				       	{field:'JWQID',width:20,checkbox:true},
						{field:'JWQBH',title:'警务区编号',width:100,sortable:true},
						{field:'JWQMC',title:'警务区名称',width:100,sortable:true}

				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
 
	function loaddata(reload) { 
	  	var queryParams =$("#jwqDataGrid1").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform_as").form().serializeArray());
		$("#jwqDataGrid1").datagrid(reload);
		$("#jwqDataGrid1").datagrid("clearSelections");
	}
	function comfirmData(){
		var node =$("#jwqDataGrid1").datagrid("getChecked");
		if(node.length==0){
			alertMsg.warn("请选择数据！");
			return;
		}
		if(node.length>1){
			alertMsg.warn("请选择一个警务区！");
			return;
		}
		var jwqid=node[0].JWQID;
		var jwqmc=node[0].JWQMC;
		var jwqbh=node[0].JWQBH;
		var strJSON = "{'jwqid':'"+jwqid+"','jwqmc':'"+jwqmc+"','jwqbh':'"+jwqbh+"'}";
		returnBackDialog.close(100,strJSON);
	}

    </script>
