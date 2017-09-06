<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<script type="text/javascript">
	function cancel() {
		editDialog.close(100);
	}
</script>
<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 10px;">
		<form id="fm" method="post" action="">
			<input type="hidden" name="dzbm" value="${xzqh.DZBM}" />
			<fieldset>
				<legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />行政区划详细信息
				</legend>
				<table class="detail-table">
				<tr></tr>
					<tr>
						<th>行政区划代码:</th>
						<td>
							<c:out value="${xzqh.XZQHDM}" />
						</td>
						<th>行政区划名称:</th>
						<td>
							<c:out value="${xzqh.XZQHMC}"></c:out>
						</td>
						<th>地址元素类型:</th>
						<td>
							<div class="form-group">
							   <km:dataDic dictCode="DZYSFL" value="${xzqh.DZYSLXDM}"/>
								<%-- <input id="dzyslxdm" dict="dzyslxDict" disabled name="dzyslxdm" value="${xzqh.DZYSLXDM}" class="form-control" /> --%>
							</div>
						</td>
					</tr>
					
					<tr>
						<th>别名简称:</th>
						<td>
							<c:out value="${xzqh.BMJC}"></c:out>
						</td>
						<th>使用状态:</th>
						<td colspan="3">
							 <div class="form-group">
							    <km:dataDic dictCode="DZ_SYZT" value="${xzqh.SYZTDM}"/>
								<%-- <input id="syztdm" dict="syztDict" disabled name="syztdm" value="${xzqh.SYZTDM}" class="form-control"/> --%>
							</div> 
						</td>
					</tr>
					
					<tr>
						<th>设立日期:</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${xzqh.SLRQ}" />
						</td>
						<th>启用日期:</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${xzqh.QYRQ}"/>
						</td>
						<th>停用日期:</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${xzqh.TYRQ}" />
						</td>
					</tr>
					
					<tr>
						<th>登记人:</th>
						<td>
							<c:out value="${xzqh.DJR}"></c:out>
						</td>
						<th>登记单位:</th>
						<td>
							<c:out value="${xzqh.DJDW}"></c:out>
						</td>
						<th>登记时间:</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${xzqh.DJSJ}" />
						</td>
					</tr>
					
					<tr>
						<th>修改人:</th>
						<td>
							<c:out value="${xzqh.XGR}"></c:out>
						</td>
						<th>修改单位:</th>
						<td>
							<c:out value="${xzqh.XGDW}"></c:out>
						</td>
						<th>修改时间:</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${xzqh.GXSJ}" />
						</td>
					</tr>
					
					<tr>
						<th>撤销标记:</th>
						<td>
							<c:choose>
								<c:when test="${xzqh.CXBJ eq 0}"><span class="tag-success">正常</span></c:when>
								<c:when test="${xzqh.CXBJ eq 1}"><span class="tag-normal">已撤销</span></c:when>
								<c:otherwise>
									<c:out value="${xzqh.CXBJ}"></c:out>
								</c:otherwise>
							</c:choose>
							
						</td>
						<th>撤销人:</th>
						<td>
							<c:out value="${xzqh.CXR}"></c:out>
						</td>
						<th>撤销时间:</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${xzqh.CXSJ}" />
						</td>
					</tr>
					<tr>
						<th>撤销原因:</th>
						<td colspan="5">
							<c:out value="${xzqh.CXYY}"></c:out>
						</td>
					</tr>
					<tr></tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>