<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
           <input type="hidden" name="jzwfjid" id="jzwfjid" value="${jzwfjid}">
       
<!--             <div data-options="region:'center',border:false"> -->
            <div data-options="region:'center',border:false" style="overflow-y: scroll;">
		    <table  border="1px"  cellspacing="0" cellpadding="0"  class="detail-table" style="width: 96%">
		   <c:forEach var="jwrylist" items="${jwry}">
		   <tr>
		    <th>中文名</th>
		   <td><c:out value="${jwrylist.zwm}"></c:out></td>
		   <th>英文名</th>
		   <td><c:out value="${jwrylist.ywm}"></c:out></td>
		   </tr>
		   <tr>
		    <th>证件号码</th>
		   <td><c:out value="${jwrylist.zjhm}"></c:out></td>
		   <th>联系电话</th>
		   <td><c:out value="${jwrylist.jwrylxdh}"></c:out></td>
		   </tr>
		     <tr>
		    <th>现居住地址</th>
		   <td><c:out value="${jwrylist.xxdz}"></c:out></td>
		   <th>房间号</th>
		   <td><c:out value="${jwrylist.fjh}"></c:out></td>
		   </tr>
		   <tr>
		     <th>房屋房主姓名</th>
		   <td><c:out value="${jwrylist.fwfzxm}"></c:out></td>
		   <th>房主身份证号</th>
		   <td><c:out value="${jwrylist.fzsfzh}"></c:out></td>
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
            <div id="dlg_jwrydetail_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
              <div id="dlg_jwryupdate_dialog" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" >
            </div>
 <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	/* initDataGrid(); */
   });
    function initDataGrid(){
    	var fjid=$("#jzwfjid").val();
    	var loadurl="${ctx}/psam/sy/syJwry/loadSyJwryInfoList?jzwfjid="+fjid;
    	$("#syJwryDataGrid").datagrid({
			/* 	title:"实有境外人员管理", */
				
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true,
				fitColumns: false,//滚动条
				animate:true,
				//collapsible:true,//显示可折叠按钮
				striped:true,//设置为true将交替显示行背景。
				singleSelect:true,//为true时只能选择单行
				pagination : true,//分页
			
				pageSize: 10,
				
				url:loadurl,
				checkbox:false,
				idField:'JWRYID',
				toolbar:"#toolbarDiv",
				columns:[[
					{field:'ZWM',title:'中文名',width:100,sortable:true},
					{field:'YWM',title:'英文名',width:100,sortable:true},
					{field:'ZJHM',title:'证件号码',width:110},
					{field:'JWRYLXDH',title:'联系电话',width:110},
					{field:'XXDZ',title:'现居住地详细地址',width:200},
					{field:'FWFZXM',title:'房屋房主姓名',width:110},
					{field:'FZSFZH',title:'房主身份证号',width:110},
					{field:'CSRQ',title:'出生日期',width:100}
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
		$("#syJwryDataGrid").datagrid("syJwryDataGrid");
	}
	function openDetailDialog(){
		 var rows = $("#syJwryDataGrid").datagrid("getSelections");
			if(rows.length != 1){
				alertMsg.warn("请选择一条要查看的境外人员信息");return;
			}
			var url="${ctx}/psam/sy/syJwry/enterDetailSyJwry?jwryid="+rows[0].JWRYID;
			
			/* alert("122");
		    var options={title:"境外人员详细信息",width:900,height:500, url:url,params:param,onClosed:function(){loaddata('reload');}};
			editDialog.open(options);
			 */
			 $("#dlg_jwrydetail_dialog").dialog({ title:"境外人员详细信息", href:url, modal:true });
			 $("#dlg_jwrydetail_dialog").dialog("open");
	}
	function openUpdateDialog(){
		var rows = $("#syJwryDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一条要查看的境外人员信息");return;
		}
		var url="${ctx}/psam/sy/syJwry/enterUpdateSyJwryAccInfo?jwryid="+rows[0].JWRYID;
		 $("#dlg_jwryupdate_dialog").dialog({ title:"境外人员信息修改", href:url, modal:true });
		 $("#dlg_jwryupdate_dialog").dialog("open");
	}
	
    </script>
