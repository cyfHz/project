<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	$(function() {
	});
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			var CXYY=$("#CXYY").val();
			if(CXYY==null||CXYY==""){
				alertMsg.warn("数据校验不通过!");
			}else if(CXYY.length>50){
				alertMsg.warn("输入字数大于50字!");
			}
		}
	}
	function cancel(){
		editDialog.close(100);
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		
		<form id="fm" method="post" class="form-horizontal" action="${ctx}/psam/xzjd/revokeXzjd">
		
		 <input type="hidden" name="dzbm" value="${xzjd.dzbm}"/>
		 
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>乡镇街道撤销编辑</legend>
				 <table>
					 <tr>
					    <th>撤销原因</th>
						<td>
						<textarea id="CXYY" name="cxyy" value="${xzjd.cxyy}" class="easyui-validatebox " data-options=" required:true, validType:'length[0,50]'" style="height:80px; width:600px"></textarea>
						</td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;"  >
				<km:widgetTag widgetRulevalue="xzjd.revokeXzjd">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">确 定</a>
				</km:widgetTag>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
			</div>
			
		</form>
	</div>
</div>