<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	$(function() {
	});
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#formss"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.info("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close(100);
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false ," title="" style="overflow: hidden;padding: 10px;">
		
		<form id="formss" method="post" action="${ctx}/demo/user/addUser">
		
		 <input type="hidden" name="userId" value="${user.userId}"   />
		 
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>用户编辑</legend>
				 <table>
					 <tr>
					    <th>用户名</th>
						<td><input type="text" name="username" value="${user.username}"  class="easyui-textbox easyui-validatebox" data-options="required:true" /></td>
						<th>用户编码号</th>
						<td>
							<input type="text" id="idnum" name="idnum" value="${user.idnum}" class="easyui-textbox easyui-validatebox"  data-options="required:true" />
						</td>
					 </tr>
					 <tr>
						<th>是否启用</th>
						<td>
							<select id="type" name="sex"   class="easyui-combobox"  style="width:170px;"  data-options="required:true" >
								<option value="true" <c:if test="${user.sex eq true }">selected="selected"</c:if>>男</option>
								<option value="false" <c:if test="${user.sex eq false }">selected="selected"</c:if>>女</option>
							</select>
						</td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;"  >
				<km:authButton text="保存" onclick="doSubmit();" iconCls="icon-ok" />
				<km:authButton text="取消" onclick="cancel();" iconCls="icon-cancel" />
			</div>
			
		</form>
	</div>
</div>