<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<script type="text/javascript">
	function cancel(){
		editDialog.close(100);
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;padding: 10px;">

		<form id="fm" method="post" action="">
			<fieldset>
				<legend>
					<img src="static/images/fromedit.png" style="margin-bottom: -3px;" />我的信息
				</legend>
				<table>
					<tr>
						<th>姓名</th>
						<td><c:out value="${userInfo.USER_LOGINNAME}"></c:out></td>
					</tr>
					<tr>
						<th>电话</th>
						<td><c:out value="${userInfo.USER_MOBILE}"></c:out></td>
					</tr>
					<tr>
						<th>邮箱</th>
						<td><c:out value="${userInfo.USER_EMAIL}"></c:out></td>
					</tr>
					<tr>
						<th>单位编号</th>
						<td><c:out value="${userInfo.ORGNA_CODE}"></c:out></td>
					</tr>
					<tr>
						<th>单位名称</th>
						<td><c:out value="${userInfo.ORGNA_NAME}"></c:out></td>
					</tr>
				</table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 20px;">
				<a href="#" class="easyui-linkbutton" plain="false"
					onclick="cancel()">关闭</a>
			</div>
		</form>
	</div>
</div>
