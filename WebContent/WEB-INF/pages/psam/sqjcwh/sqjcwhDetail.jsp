<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
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
<style>

table tr {
border-color:#9EB5D0;
border-collapse:collapse;
}
</style>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<div class="easyui-layout" data-options="fit:true,border:false">
<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DYCXSX" var="dycxsxDict"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss" method="post" action="${ctx}/psam/sqjcwh/updateSqjcwh">
		 <input type="hidden" name="dzbm" value="${sqjcwh.dzbm}"/>
		 
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>社区居村委会信息详情</legend>
				 <table border="1px" cellspacing="0" cellpadding="0" class="detail-table">
					 <tr></tr>
					 <tr>
					    <th>社区、居（村）委会代码:</th>
						<td><c:out value="${sqjcwh.SQJCWHDM}"></c:out></td>
						<th>社区、居（村）委会名称:</th>
						<td ><c:out value="${sqjcwh.SQJCWHMC}"></c:out></td>
						<th>地址元素类型:</th>
					 	<td >
					 	 	<div class="form-group">
					 	 	     <km:dataDic dictCode="DZYSFL" value="${sqjcwh.DZYSLXDM}"/>
								<%-- <input id="dzyslxdm" dict="dzyslxDict" disabled name="dzyslxdm" value="${sqjcwh.DZYSLXDM}" class="form-control"/> --%>
							</div> 
							<%--<c:out value="${sqjcwh.DZYSLXDM}"></c:out> --%>
					 	</td>
					 	
					 </tr>
					 <tr>
						<th>所属社区居村委会:</th>
						<td ><c:out value="${sqjcwh.SJSQJCWHMC}"></c:out></td>
						<th>上级行政区域:</th>
						<td><c:out value="${sqjcwh.SJXZQYMC}"></c:out></td>
						<th></th>
						<td></td>
					 </tr>
					 <tr>
					    <th>使用状态:</th>
						<td >
							    <km:dataDic dictCode="DZ_SYZT" value="${sqjcwh.SYZTDM}"/>
								<%-- <input id="syztdm" dict="syztDict" disabled name="syztdm" value="${sqjcwh.SYZTDM}" class="form-control"/> --%>
						<%-- <c:out value="${sqjcwh.SYZTDM}"></c:out> --%></td>
					    <th>别名简称:</th>
						<td ><c:out value="${sqjcwh.BMJC}"></c:out></td>
						<th>地域城乡属性:</th>
						<td >
							<div class="form-group">
							    <km:dataDic dictCode="DYCXSX" value="${sqjcwh.DYCXSXDM}"/>
								<%-- <input id="dycxsx" dict="dycxsxDict" disabled name="dycxsx" value="${sqjcwh.DYCXSXDM}" class="form-control"/> --%>
							</div> 
						</td>
						
					 </tr>
					 <tr>
					    <th>设立日期:</th>
		
						<td ><c:out value="${sqjcwh.SLRQ}"></c:out></td>
						<th>启用日期:</th>
			
						<td ><c:out value="${sqjcwh.QYRQ}"></c:out></td>
						<th>停用日期:</th>
			
					    <td ><c:out value="${sqjcwh.TYRQ}"></c:out></td>
					 </tr>
					  <tr>
						<th>登记人:</th>
						<td ><c:out value="${sqjcwh.DJR}"></c:out></td>
						<th>登记单位:</th>
						<td ><c:out value="${sqjcwh.DJDW}"></c:out></td>
						<th>登记时间:</th>
					    <td ><c:out value="${sqjcwh.DJSJ}"></c:out></td>
					 </tr>
					 <tr>
						<th>修改人:</th>
						  <td ><c:out value="${sqjcwh.XGR}"></c:out></td>
						<th>修改单位:</th>
						     <td ><c:out value="${sqjcwh.XGDW}"></c:out></td>
						<th>更新时间:</th>
					       <td ><c:out value="${sqjcwh.GXSJ}"></c:out></td>
					 </tr>
					  <tr>
						<th>撤销人:</th>
				
						<td ><c:out value="${sqjcwh.CXR}"></c:out></td>
						<th>撤销单位:</th>
				
						<td ><c:out value="${sqjcwh.CXDW}"></c:out></td>
						<th>撤销时间:</th>
					    <td ><c:out value="${sqjcwh.CXSJ}"></c:out></td>
					 </tr>
					 <tr>
				 		<th>撤销原因:</th>
					
						<td colspan="5" ><c:out value="${sqjcwh.CXYY}"></c:out></td>
					 </tr>
					 <tr></tr>
				 </table>
			</fieldset>
		</form>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>