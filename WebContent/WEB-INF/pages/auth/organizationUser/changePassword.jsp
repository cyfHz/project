<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	function doSubmit(){
		var oldPass=$("#oldPass").val();
		var newPass=$("#newPass").val();
		var repeatNewPass=$("#repeatNewPass").val();
		if(newPass!=repeatNewPass){
			alertMsg.warn("两次填写的密码不一致");
			$("#repeatNewPass").val("");
			$("#newPass").focus();
			return;
		}
		for(var i=0;i<newPass.length;i++){
	  		 var leg=newPass.charCodeAt(i);
	   			if(leg>255){
	   				alertMsg.warn("系统不支持此特殊字符，请重新输入");
	   				$("#newPass").val("");
					$("#repeatNew").val("");
					$("#newPass").focus();
	   				return;
	   			}
	   	}
	
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
	
		<form id="fm" method="post" action="${ctx}/auth/organizationUser/changePassWord">
		 <input type="hidden" id="userId" name="userId" value="${organizationUser.appuser_id}"/>
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>密码修改</legend>
				 <table>
					 <tr>
					    <th>旧密码</th>
						<td><input type="password" id="oldPass" value="" name="oldPass" class="easyui-textbox easyui-validatebox" data-options="required:true,validType:'length[3,16]'" /></td>
					</tr>
					<tr>
						<th>新密码</th>
						<td>
							<input type="password" id="newPass" value=""  name="newPass"  class="easyui-textbox easyui-validatebox"  data-options="required:true,validType:'length[3,16]'" />
						</td>
					</tr>
					<tr>
						<th>确认新密码</th>
						<td>
							<input type="password" id="repeatNewPass" value=""  name="repeatNewPass" class="easyui-textbox easyui-validatebox"  data-options="required:true,validType:'length[3,33]'" />
						</td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 20px;">
					<a href="#" class="easyui-linkbutton" iconCls="" plain="false" onclick="doSubmit()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="" plain="false" onclick="cancel()">取消</a>
			</div>
		</form>
	</div>
</div>
