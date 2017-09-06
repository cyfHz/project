<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
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
<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post" action="">
		 <input type="hidden" name="dzbm" value="${xzjd.dzbm}"   />
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>乡镇街道详细信息</legend>
				 <table border="1px"  cellspacing="0" cellpadding="0" class="detail-table" >
					 <tr></tr>
					 <tr>
					    <th>乡镇（街道）代码: </th>
						  <td ><c:out value="${xzjd.XZJDDM}"/></td>
						<th>乡镇（街道）名称: </th>
						 <td ><c:out value="${xzjd.XZJDMC}"></c:out></td>
						<th>地址元素类型代码: </th>
						<td>
							 <div class="form-group">
							    <km:dataDic dictCode="DZYSFL" value="${xzjd.DZYSLXDM}"/>
								<%-- <input id="dzyslxdm" dict="dzyslxDict" disabled name="dzyslxdm" value="${xzjd.DZYSLXDM}" class="form-control"/> --%>
							</div> 
						</td>
					<%-- 	 <td ><c:out value="${xzjd.DZYSLXDM}"></c:out></td> --%>
					 </tr>
					  
					 <tr></tr>
					 <tr>
					    <th>别名简称: </th>
					
						 <td ><c:out value="${xzjd.BMJC}"></c:out></td>
						<th>上级行政区域: </th>
	
							  <td ><c:out value="${xzjd.SJXZQY_MC}"></c:out></td>
						<th>使用状态: </th>
						<td>
							 <div class="form-group">
							    <km:dataDic dictCode="DZ_SYZT" value="${xzjd.SYZTDM}"/>
								<%-- <input id="syztdm" dict="syztDict" disabled name="syztdm" value="${xzjd.SYZTDM}" class="form-control"/> --%>
							</div> 
						</td>
					 </tr>
					  <tr></tr>
					<tr>
					    <th>设立日期: </th>
						 <td ><c:out value="${xzjd.SLRQ}"></c:out></td>
						<th>启用日期: </th>
						<td ><c:out value="${xzjd.QYRQ}"></c:out></td>
						<th>停用日期: </th>
						<td ><c:out value="${xzjd.TYRQ}"></c:out></td>
					 </tr>
					  <tr></tr>
					 <tr>
						<th>登记人: </th>
							<td ><c:out value="${xzjd.DJR}"></c:out></td>
						<th>登记单位: </th>
						<td ><c:out value="${xzjd.DJDW}"></c:out></td>
						<th>登记时间: </th>
					     	<td ><c:out value="${xzjd.DJSJ}"></c:out></td>
					 </tr>
					  <tr></tr>
					 <tr>
						<th>修改人： </th>
						 <td ><c:out value="${xzjd.XGR}"></c:out></td>
						<th>修改单位： </th>
						   <td ><c:out value="${xzjd.XGDW}"></c:out></td>
						<th>更新时间： </th>
					        <td ><c:out value="${xzjd.GXSJ}"></c:out></td>
					 </tr>
					  <tr></tr>
					  <tr>
						<th>撤销人： </th>
						       <td ><c:out value="${xzjd.CXR}"></c:out></td>
						<th>撤销单位： </th>
						     <td ><c:out value="${xzjd.CXDW}"></c:out></td>
						<th>撤销时间： </th>
					        <td ><c:out value="${xzjd.CXSJ}"></c:out></td>
					 </tr>
					  <tr></tr>
					 <tr>
				 		<th>撤销原因： </th>
						<td colspan="5" >
						<c:out value="${xzjd.CXYY}"></c:out>
						</td>
					 </tr>
					 <tr></tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;"  >
			</div>
			
		</form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>