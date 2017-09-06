<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	
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

	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DZYSFL" subCode="30" var="dzyslxDicts"></t:DataDict>
	<t:DataDict code="DYCXSX" var="dycxsxDict"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss" method="post" action="${ctx}/psam/webservice/bzdzuser/addUser.do">
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>WEB用户添加编辑页面</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
						 <th>用户名</th>
						<td><input type="text" id="username" name="username"  class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[1,50]'" /></td>
						<th>密码</th>
						<td>
						<input type="text" id="userpassword"  name="userpassword" required="required" class="easyui-textbox easyui-validatebox form-control" style="width:173px;" />
					 	</td>
					 </tr>
					 <tr>
						<th>绑定ip</th>
						<td>
						<input type="text" id="bdip" name="bdip"  class="easyui-textbox easyui-validatebox form-control"   />
						</td>
						<th>设立日期</th>
					    <jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
						<td><input type="text" name="registertime"  value='<fmt:formatDate value="${now }" type="both" />' class="easyui-datetimebox easyui-validatebox form-control" style="width:173px;" data-options="editable:false"/></td>
						
					 </tr>
					 <tr>
						<th>限制用户每分钟登录次数</th>
						<td>
						<input type="text" id="rejectCount" name="rejectCount"  class="easyui-numberbox "  data-options="min:0,max:1000" />
						</td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="bzdzuser.addUser">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保存</a>
				</km:widgetTag>
				   <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取消</a>
			</div>
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>