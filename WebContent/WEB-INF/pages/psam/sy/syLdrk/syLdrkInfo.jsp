<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
           <input type="hidden" name="jzwfjid" id="jzwfjid" value="${jzwfjid}">
           
            <div data-options="region:'center',border:false,fit:true" style="overflow-y: scroll;">
            <table  border="1px"  cellspacing="0" cellpadding="0"  class="detail-table" style="width: 96%">
		    <c:forEach var="ldrklist" items="${ldrk}">
		   <tr>
		    <th>姓名</th>
		   <td><c:out value="${ldrklist.xm}"></c:out></td>
		   <th>别名</th>
		   <td><c:out value="${ldrklist.bm}"></c:out></td>
		   </tr>
		   <tr>
		    <th>身份证号</th>
		   <td><c:out value="${ldrklist.sfzh}"></c:out></td>
		   <th>联系电话</th>
		   <td><c:out value="${ldrklist.lxfsSj}"></c:out></td>
		   </tr>
		     <tr>
		    <th>详细地址</th>
		   <td><c:out value="${ldrklist.xxdz}"></c:out></td>
		   <th>房间号</th>
		   <td><c:out value="${ldrklist.fh}"></c:out></td>
		   </tr>
		 
		   <tr>
		   <td colspan="6"><hr> </td>
		    
		   </tr>
		   </c:forEach>
		   </table>
           <!--  <table id="syLdrkDataGrid"  style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table> -->
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
			</div>	
            </div>
            </div>
            <div id="dlg_ldrkdetail_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
              <div id="dlg_ldrkupdate_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
 <!-- <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	/* initDataGrid(); */
   });
    function initDataGrid(){
    	var fjid=$("#jzwfjid").val();
    	var loadurl="${ctx}/psam/sy/syLdrk/loadSyLdrkInfoList?jzwfjid="+fjid;
    	  treeDrid = $("#syLdrkDataGrid").datagrid({
				/* title:"实有境外人员信息", */
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:loadurl,
				checkbox:false,
				idField:'LDID',
				toolbar:"#toolbarDiv",
				columns:[[
					{field:'XM',title:'姓名',width:100,sortable:true},
					{field:'BM',title:'别名',width:100,sortable:true},
					{field:'SFZH',title:'身份号码',width:110},
					{field:'LXFS_SJ',title:'联系电话',width:110},
					{field:'XJZDZZ',title:'现居住地详细地址',width:110},
					{field:'FH',title:'房间号',width:110}
								
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
		$("#syLdrkDataGrid").datagrid("syLdrkDataGrid");
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
		var url="${ctx}/psam/sy/syLdrk/enterUpdateLdrkAccInfo?rkid="+rows[0].LDID;
		 $("#dlg_ldrkupdate_dialog").dialog({ title:"境外人员信息修改", href:url, modal:true });
		 $("#dlg_ldrkupdate_dialog").dialog("open");
	}
    </script> -->
</body>
</html>