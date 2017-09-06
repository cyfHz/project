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
<div class="easyui-layout" data-options="fit:true,border:false">

	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 10px;" >
		<form id="formss" method="post" action="${ctx}/psam/jlx/updateJlx">
		 <input type="hidden" name="dzbm" value="${jlx.dzbm}"   />
		 
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>街路巷（小区）信息详情</legend>
				 <table border="1px"  cellspacing="0" cellpadding="0" class="detail-table" >
					 <tr></tr>
					 <tr>
					    <th>街路巷（小区）代码</th>
						<td colspan="5"><c:out value="${jlx.JLXXQDM}"></c:out></td>
						
					 </tr>
					 <tr>
					    <th>街路巷（小区）名称</th>
						<td><c:out value="${jlx.JLXXQMC}"></c:out></td>
						<th>地址元素类型</th>
						<td>
							<div class="form-group">
							    <km:dataDic dictCode="DZYSFL" value="${jlx.DZYSLXDM}"/>
								<%-- <input id="dzyslxdm" dict="dzyslxDict" disabled name="dzyslxdm" value="${jlx.DZYSLXDM}" class="form-control"/> --%>
							</div> 
					 	</td>
					    <th>别名简称</th>
						<td><c:out value="${jlx.BMJC}"></c:out></td>
					 </tr>
					 <tr>
						<th>所属最低一级行政区域</th>
						<td><c:out value="${jlx.SSZDYJXZQY_DZBM}"></c:out></td>
						<th>所属街路巷（小区）</th>
						<td><c:out value="${jlx.SSJLXXQ_DZBM}"></c:out></td>
						<th>使用状态</th>
						<td>
							<div class="form-group">
							    <km:dataDic dictCode="DZ_SYZT" value="${jlx.SYZTDM}"/>
								<%-- <input id="syztdm" dict="syztDict" disabled name="syztdm" value="${jlx.SYZTDM}" class="form-control"/> --%>
							</div> 
						</td>
					 </tr> 
					 <tr>
					    <th>设立日期</th>
						<td><c:out value="${jlx.SLRQ}"></c:out></td>
						<th>启用日期</th>
						<td><c:out value="${jlx.QYRQ}"></c:out></td>
						<th>停用日期</th>
						<td><c:out value="${jlx.TYRQ}"></c:out></td>
					 </tr>
					  <tr>
						<th>登记人</th>
						<td><c:out value="${jlx.DJR}"></c:out></td>
						<th>登记单位</th>
						<td><c:out value="${jlx.DJDW}"></c:out></td>
						<th>登记时间</th>
						<td><c:out value="${jlx.DJSJ}"></c:out></td>
					 </tr>
					 <tr>
						<th>修改人</th>
						<td><c:out value="${jlx.XGR}"></c:out></td>
						<th>修改单位</th>
						<td><c:out value="${jlx.XGDW}"></c:out></td>
						<th>更新时间</th>
						<td><c:out value="${jlx.GXSJ}"></c:out></td>
					 </tr>
					  <tr>
						<th>撤销人</th>
						<td><c:out value="${jlx.CXR}"></c:out></td>
						<th>撤销单位</th>
						<td><c:out value="${jlx.CXDW}"></c:out></td>
						<th>撤销时间</th>
						<td><c:out value="${jlx.CXSJ}"></c:out></td>
					 </tr>
					 <c:if test="${jlx.CXYY!=null}">
					 <tr>
				 		<th>撤销原因</th>
						<td colspan="5">
						<c:out value="${jlx.CXYY}"></c:out>
						<%-- <textarea name="cxyy" value="" disabled class="easyui-validatebox" data-options=" required:true, validType:'length[0,300]'" style="height:80px; width:600px">${jlx.CXYY}</textarea> --%>
						</td>
					 </tr>
					 </c:if>
					 <tr></tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>