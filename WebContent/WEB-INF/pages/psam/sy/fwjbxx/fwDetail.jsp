<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close();
	}
</script>
<t:DataDict code="FWLB" var="fwlbDict"></t:DataDict>
<t:DataDict code="FWXZ" var="fwxzDict"></t:DataDict>
<t:DataDict code="FWYT" var="fwytDict"></t:DataDict>
<t:DataDict code="fwlx" var="fwlxDict"></t:DataDict>
<t:DataDict code="rygx" var="rygxDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post" class="form-horizontal" action="${ctx}/psam/fw/updateOrAddFW">
		 <input type="hidden" name="fwid" value="${fw.fwid}"/>
			<fieldset>
				<!-- <legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>房屋信息</legend> -->
				 <table  border="1px"  cellspacing="0" cellpadding="0" >
					  <tr>
						<th>房屋号</th>
						<td><c:out value="${fw.fjh}"></c:out></td>
						<th>房屋类型</th>
						<td><input id="fwlx" dict="fwlxDict" disabled="disabled" name="fwlx" value="${fw.fwlx}" class="form-control"/></td>
					    <th>房屋产权证号</th>
						<td><c:out value="${fw.fwcqzh}"></c:out></td>
					 </tr>
					 <tr>
					   	<th>房主姓名</th>
						<td><c:out value="${fw.fzxm}"></c:out></td>
						<th>房主身份证号</th>
						<td><c:out value="${fw.fzsfzhm}"></c:out></td>
						<th>房主联系电话</th>
						<td><c:out value="${fw.fzlxdh}"></c:out></td>
					</tr>
					 <tr>
					   	<th>托管人姓名</th>
						<td><c:out value="${fw.tgrxm}"></c:out></td>
						<th>托管人身份证号</th>
						<td><c:out value="${fw.tgrsfzhm}"></c:out></td>
						<th>托管人联系电话</th>
						<td><c:out value="${fw.tgrlxdh}"></c:out></td>
					</tr>
					<tr>
					    <th>与房主关系</th>
						<td>
						  <div class="form-group">
								<input id="yfzgx" dict="rygxDict" disabled name="yfzgx" value="${fw.yfzgx}" class="form-control"/>
							</div>
					</td>
						<th>是否出租房屋</th>
						<td colspan="3">
						<select id="sfczfw" name="sfczfw"  class="easyui-combobox" style="width: 170px;" disabled="disabled" data-options=" ">
								<option value="0" <c:if test="${fw.sfczfw eq '0' }">selected='selected' </c:if>>否</option>
								<option value="1" <c:if test="${fw.sfczfw eq '1' }">selected='selected' </c:if>>是</option>
							</select>
						</td>
					</tr>
					 <tr>
					    <th>房屋类别</th>
						<td>
							<input id="fwlb" dict="fwlbDict" disabled="disabled" name="fwlb" value="${fw.fwlb}" class="form-control"/>
						</td>
						<th>房屋性质</th>
						<td>
							<input id="fwxz" dict="fwxzDict" disabled="disabled" name="fwxz" value="${fw.fwxz}" class="form-control"/>
						</td>
						<th>房屋用途</th>
						<td>
							<input id="fwyt" dict="fwytDict" disabled="disabled" name="fwyt" value="${fw.fwyt}" class="form-control"/>
						</td>
					 </tr>
					 <tr>
					    <th>户型(*室*厅)</th>
						<td><c:out value="${fw.hx}"></c:out></td>
						<th>房屋间数</th>
						<td><c:out value="${fw.fwjs}"></c:out></td>
						<th>房屋面积(平米)</th>
						<td><c:out value="${fw.fwmj}"></c:out></td>
					 </tr>
				 </table>
			</fieldset>
			<!-- <div style="position: absolute;bottom: 5px;right: 10px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
			</div> -->
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>