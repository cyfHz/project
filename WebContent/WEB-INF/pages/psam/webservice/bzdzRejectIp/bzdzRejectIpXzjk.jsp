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

	<t:DataDict code="SFYY" var="sfyyDict"></t:DataDict>
	<t:DataDict code="XZJKDZ" var="xzjkdzDict"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss" method="post" action="${ctx}/psam/webservice/bzdzRejectIp/saveXzjkRejectIp">
			<input type="hidden" name="ipid" id="ipid" value="${rejectIp.ipid}"/>
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>修改限制接口编辑页面</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					   <th>限制接口</th>
						<td>
						<input type="text" id="ipxzjk" value="${rejectIp.ipxzjk}"  name="ipxzjk" dict="xzjkdzDict" required="required" class=" form-control" style="width:173px;" data-options="editable:false"/>
						</td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="bzdzRejectIp.saveXzjkRejectIp">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保存</a>
				</km:widgetTag>
				   <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取消</a>
			</div>
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>