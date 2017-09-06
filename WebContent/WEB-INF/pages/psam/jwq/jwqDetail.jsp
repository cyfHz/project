<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
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
<t:DataDict code="JWQXZ" var="jwqxzDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: scroll;padding: 10px;">
		<form id="formss" class="form-horizontal" method="post"
			action="${ctx}/psam/jwq/updateJwq">
			<input type="hidden" name="jwqid" value="${jwq.jwqid}" />
			<fieldset>
				<legend>
					<img src="static/images/fromedit.png" style="margin-bottom: -3px;" />警务区详细信息
				</legend>
				<table id="formTbale" border="1px"  cellspacing="0" cellpadding="0" class="detail-table"  >
					<tr></tr>
					<tr>
						<th style="width:150px">警务区编号</th>
						<td><c:out value="${jwq.jwqbh}"></c:out></td>
						<th style="width:150px">上级行政区域</th>
						<td><c:out value="${jwq.sjxzqy_mc }"></c:out></td>
						
					</tr>
					<tr>
					    <th>所属派出所</th>
						<td><c:out value="${jwq.pcsmc}"></c:out></td>
						<th>警务区名称</th>
						<td><c:out value="${jwq.jwqmc}"></c:out></td>
					</tr>
					<tr>
						<th>警务区面积(平方米)</th>
						<td><c:out value="${jwq.jwqmj}"></c:out></td>
						<th>警务区性质</th>
						<td>
						  <km:dataDic dictCode="JWQXZ" value="${jwq.jwqxz}"/>
						  <%-- <input id="jwqxz" dict="jwqxzDict" disabled="disabled" name="jwqxz" value='<c:out value="${jwq.jwqxz}"></c:out>' class="form-control">  --%>
						</td>
					</tr>
					<tr>
						<th>居委会数量(个)</th>
						<td><c:out value="${jwq.jwhsl}"></c:out></td>
						<th>农村管区数量(个)</th>
						<td><c:out value="${jwq.ncgqsl}"></c:out></td>
					</tr>
					<tr>
					    
						<th>启用日期</th>
						<td colspan="3"><c:out value="${jwq.qyrq}"></c:out></td>
					</tr>
					<tr>
					    <th>警务区简介</th>
						<td colspan="3"><c:out value="${jwq.jwqjj}"></c:out></td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3"><c:out value="${jwq.bz}"></c:out></td>
					</tr>
					<tr></tr>
				</table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">关　闭</a>
				</km:widgetTag>
			</div>

		</form>
	</div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>