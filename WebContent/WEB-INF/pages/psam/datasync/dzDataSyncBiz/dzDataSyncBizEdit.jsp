<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		 editDialog.close(100);
		
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post" class="form-horizontal" action="${ctx}/psam/datasync/dzDataSyncBiz/updateDzDataSyncBiz">
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>数据同步信息修改</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
						<th>业务逻辑方法</th>
						<td><input type="text" id="logicmethod" name="logicmethod" value="${dzDataSyncBiz.logicmethod}" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true ,validType:'length[1,80]'" /></td>
						
						<th>状态 </th>
						<td>
						   <select name="status" id="status">
						     <option value="1" <c:if test="${'1' eq dzDataSyncBiz.status}">selected = "selected"</c:if>>启用</option>
						      <option value="0" <c:if test="${'0' eq dzDataSyncBiz.status}">selected = "selected"</c:if>>禁用</option>
						   </select>
						</td>
					 </tr>
					 <tr>
					 	<th>描述</th>
					 	<td>
				        <input type="text" name="describe" id="describe" value="${dzDataSyncBiz.describe}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,200]'" />
					 </td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="xzjd.addXzjd">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
				
			</div>
		</form>
	</div>
</div>