<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
<input type="hidden" name="sszdyjxzqy_dzbm" id="sszdyjxzqy_dzbm2" value="${sszdyjxzqy_dzbm}"   />
<input type="hidden" name="sszdyjxzqy_xtype" id="sszdyjxzqy_xtype2" value="${sszdyjxzqy_xtype}"   />
<input type="hidden" name="isLoadFromSuperXzqy" id="isLoadFromSuperXzqy2" value="${isLoadFromSuperXzqy}"   />
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4; overflow: hidden;">
                 <form class="form-inline query-form" id="jwqSearchform_XXXas">
                  <div class="form-group">
								<label>街路巷（小区）名称:</label> 
								<input type="text" name="jlxxqmc" id="jlxxqmc" class="easyui-textbox form-control" style="height:24px;"/>
							</div>
							<!-- <div class="form-group">
								<label>街路巷（小区）代码:</label> 
								<input type="text" name="jlxxqdm" id="jlxxqdm" class="easyui-textbox  form-control" style="height:24px;"/>
							</div> -->
                    <div class="form-group">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');">查 询</a>
                    </div>
                </form> 
            </div>
            <div data-options="region:'center',border:false">
            <table id="jwqDataGrid1" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		  <%--       <km:widgetTag widgetRulevalue="jlx.loadJlxDataGrid"> --%>
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="comfirmData()">确认</a>
 	<%-- 			</km:widgetTag> --%>
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
/* */
 		/* var jlxxqmc=$("#jlxxqmc").val();
 		var jlxxqdm=$("#jlxxqdm").val(); */
		var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm2").val();
    	var sszdyjxzqy_xtype=$("#sszdyjxzqy_xtype2").val();
    	var isLoadFromSuperXzqy=$("#isLoadFromSuperXzqy2").val();
    	//var loadurl="${ctx}/psam/jlx/loadJlxAjaxData?sszdyjxzqy_dzbm="+sszdyjxzqy_dzbm+"&sszdyjxzqy_xtype="+sszdyjxzqy_xtype+"&isLoadFromSuperXzqy="+isLoadFromSuperXzqy+"&jlxxqdm="+jlxxqdm+"&jlxxqmc="+jlxxqmc;
    	var loadurl="${ctx}/psam/jlx/loadJlxAjaxData?sszdyjxzqy_dzbm="+sszdyjxzqy_dzbm+"&sszdyjxzqy_xtype="+sszdyjxzqy_xtype+"&isLoadFromSuperXzqy="+isLoadFromSuperXzqy;
    	//var loadurl="${ctx}/psam/jlx/loadJlxAjaxData";
    	
    	treeDrid = $("#jwqDataGrid1").datagrid({
				title:"街路巷数据列表",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true,
				fitColumns: true,//滚动条
				animate:true,
				collapsible:false,//显示可折叠按钮
				striped:true,//设置为true将交替显示行背景。
				singleSelect:true,//为true时只能选择单行
				pagination : true,//分页
				rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:true,
				idField:'DZBM',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[ //username ,idnum,sex,userId
				          // {field : 'DZBM',width : 20,checkbox : true},
							{field : 'JLXXQMC',title : '街路巷（小区）名称',width : 120,sortable : true},
							//{field : 'ZJF',title : '街路巷（小区）名称',width : 120},
							{field : 'JLXXQDM',title : '街路巷（小区）代码',width : 115,sortable : true},
							{field : 'SYZTDM',title:'使用状态',width : 60,sortable : true,formatter:dictFormat('syztDict')}

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
	  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform_XXXas").form().serializeArray());
	   	var jlxxqmc=$("#jlxxqmc").val();
 		var jlxxqdm=$("#jlxxqdm").val(); 
	//  loadurl+="&jlxxqmc="+jlxxqmc+"&jlxxqdm="+jlxxqdm;
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
			alertMsg.warn("请选择一个街路巷！");
			return;
		}
		var dzbm=node[0].DZBM;
		var jlxxqmc=node[0].JLXXQMC;
		var zdyjxzqhmc=node[0].MC;
		var zdyjxzqhdzbm=node[0].SSZDYJXZQY_DZBM;
		//var strJSON = "{'dzbm':'"+dzbm+"','jlxxqmc':'"+jlxxqmc+"'}";
		var strJSON = "{'dzbm':'"+dzbm+"','jlxxqmc':'"+jlxxqmc+"','zdyjxzqhmc':'"+zdyjxzqhmc+"','zdyjxzqhdzbm':'"+zdyjxzqhdzbm+"'}";
		returnBackDialog.close(100,strJSON);
	}
    </script>
