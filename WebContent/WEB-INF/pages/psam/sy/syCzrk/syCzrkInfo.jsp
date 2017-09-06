<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
           <input type="hidden" name="jzwfjid" id="jzwfjid" value="${jzwfjid}">
            <div data-options="region:'center',border:false" style="overflow-y: scroll;">
		     <table  border="1px"  cellspacing="0" cellpadding="0"  class="detail-table" style="width: 96%">
		   <c:forEach var="czrklist" items="${czrk}">
		   <tr>
		    <th>姓名</th>
		   <td><c:out value="${czrklist.xm}"></c:out></td>
		   <th>曾用名</th>
		   <td><c:out value="${czrklist.zym}"></c:out></td>
		   </tr>
		   <tr>
		    <th>身份证号</th>
		   <td><c:out value="${czrklist.gmsfhm}"></c:out></td>
		   <th>联系电话</th>
		   <td><c:out value="${czrklist.lxfssj}"></c:out></td>
		   </tr>
		     <tr>
		    <th>门楼牌号</th>
		   <td><c:out value="${czrklist.mlph}"></c:out></td>
		   <th>门楼详址</th>
		   <td><c:out value="${czrklist.mlxz}"></c:out></td>
		   </tr>
		 
		   <tr>
		   <td colspan="6"><hr> </td>
		    
		   </tr>
		   </c:forEach>
		   </table>
		   
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			</div>	
            </div>
            </div>
            <div id="dlg_chrkdetail_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
              <div id="dlg_chrkupdate_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
 <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	/* initDataGrid(); */
   });
    function initDataGrid(){
    	var fjid=$("#jzwfjid").val();
    	var loadurl="${ctx}/psam/sy/syCzrk/loadSyCzrkInfoList?jzwfjid="+fjid;
    	 $("#syCzrkDataGrid").datagrid({
				/* title:"常住人员信息", */
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10, 
				url:loadurl,
				checkbox:false,
				idField:'RKID',
				toolbar:"#toolbarDiv",
				columns:[[
					{field:'XM',title:'姓名',width:100,sortable:true},
					{field:'ZYM',title:'曾用名',width:100,sortable:true},
					{field:'GMSFHM',title:'身份证号',width:110},
					{field:'CSRQ',title:'出生日期',width:100,sortable:true},
					{field:'LXFSSJ',title:'联系电话',width:110},
					{field:'XJZD',title:'现居住地址',width:110},
					{field:'XJZDZZFJH',title:'房间号',width:110}
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
		$("#syCzrkDataGrid").datagrid("syCzrkDataGrid");
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
		var url="${ctx}/psam/sy/syCzrk/enterUpdateCzrkAccInfo?rkid="+rows[0].RKID;
		 $("#dlg_chrkupdate_dialog").dialog({ title:"境外人员信息修改", href:url, modal:true });
		 $("#dlg_chrkupdate_dialog").dialog("open");
	}
    </script>
