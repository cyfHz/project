<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	$(function(){
		 //$("#rwlb").combobox("loadData", data);
	})
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		 editDialog.close(100);
		
	}
	
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post" class="form-horizontal" action="${ctx}/auth/organization/addOrganization">
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>组织机构信息添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
						<th>组织机构名称</th>
						<td><input type="text" id="orgna_name" name="orgna_name" value="${organization.orgna_name}" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true ,validType:'length[1,300]'" /></td>
					</tr>
					<tr>
						<th>组织机构简称</th>
						<td>
							<input type="text" id="orgna_jc" name="orgna_jc" value="${organization.orgna_jc}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,260]'" />
						</td>
					 </tr>
					 <tr>
					    <th>组织机构类型</th>
						<td>
							<%-- <input type="hidden"  name="type" value="${type}">  --%>
							<c:if test="${ptype eq 'st'}"><input type="hidden"  name="type" value="sj"/>市局</c:if>
							<c:if test="${ptype eq 'sj'}"><input type="hidden"  name="type" value="fj"/>分局</c:if>
							<c:if test="${ptype eq 'fj'}"><input type="hidden"  name="type" value="pcs"/>派出所</c:if>
					</td>
					</tr>
					<tr>
						<th>上级行政组织机构${porg.porgna_id }</th>
						<td>
						 <input type="text" value="${porg.orgna_name }"class="form-control easyui-validatebox"  data-options="required:true"/>
						 <input type="hidden"  name="porgna_id"  value="${porg.orgna_id }" >
						 </td>
					 </tr>
					 <tr>
					 	<th>是否直属分局</th>
					 	<td>
					 	 <select name="sfzsj" id="sfzsj" style="width: 170px" class="easyui-combobox">
			 		   <option value="0">否</option>
					   <option value="1">是</option>
					 </select>
					 </td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="xzjd.addXzjd">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
				
			</div>
		</form>
	</div>
</div>