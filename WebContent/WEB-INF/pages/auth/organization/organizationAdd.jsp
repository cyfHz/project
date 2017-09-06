<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	
	function doSubmit(){
		
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		 editDialog.close(100);
		
	}
	//所属派出所查找带回
	function openSspcsLookBack(){
		var url="${ctx}/auth/organization/loadOrgBingbackPage";
		 var options={title:"查找带回",width:320,height:400, url:url,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				 $("#porgna_id").val(id);
				 $("#sspcs_mc").val(text);
			}
			}};
		 returnBackDialog.open(options);
	}
	
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post"  action="${ctx}/auth/organization/addOrganization">
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
							<input type="text" id="orgna_jc" name="orgna_jc" value="${organization.orgna_jc}" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true ,validType:'length[0,260]'" />
						</td>
					 </tr>
					 <tr>
					    <th>组织机构类型</th>
						<td>
					 <select name="type" id="type" style="width: 175px" class="easyui-textbox easyui-combobox" data-options="editable:false">
			 		<option value="sj">市局</option>
					<option value="fj">分局</option>
					<option value="pcs">派出所</option>
					 </select>
					</td>
					</tr>
					<tr>	
						<th>上级行政组织机构</th>
						<td>
						 <input id="sspcs_mc" class="form-control easyui-textbox easyui-validatebox" onclick="openSspcsLookBack();"  data-options="required:true"  readonly="readonly" />
						 <input type="hidden" id="porgna_id" name="porgna_id" value="">
						 </td>
					 </tr>
					 <tr>
					 	<th>是否直属分局</th>
					 	<td>
					 	 <select name="sfzsj" id="sfzsj" style="width: 175px" class="easyui-combobox easyui-textbox" data-options="editable:false">
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