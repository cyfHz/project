<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	function doSubmit(){
		if(PUBUtil.isSpecialCharacter($("#fwcqzh").val())){
			$.messager.alert('提示',"产权证号中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#fzxm").val())){
			$.messager.alert('提示',"姓名中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#fzsfzhm").val())){
			$.messager.alert('提示',"身份证号中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#tgrxm").val())){
			$.messager.alert('提示',"姓名中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#tgrsfzhm").val())){
			$.messager.alert('提示',"身份证号中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#hx").val())){
			$.messager.alert('提示',"户型中不能包含特殊字符");	
			return;
		}
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close(100);
	}
</script>
<t:DataDict code="FWLB" var="fwlbDict"></t:DataDict>
<t:DataDict code="FWXZ" var="fwxzDict"></t:DataDict>
<t:DataDict code="FWYT" var="fwytDict"></t:DataDict>
<t:DataDict code="fwlx" var="fwlxDict"></t:DataDict>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post" action="${ctx}/psam/fw/updateOrAddFW">
		 <input type="hidden" name="fwid" value="${fw.fwid}"/>
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>房屋基本信息信息编辑</legend>
				 <table id="formTbale" cellpadding="4">
					  <tr>
						<th>房间号</th>
						<td><input type="text" name="fjh" value="${fw.fjh}" disabled="disabled" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[1,100]'" /></td>
						
						<th>房屋类型</th>
						<td>
							<input id="fwlx" dict="fwlxDict" name="fwlx" value="${fw.fwlx}" class="form-control"/>
						</td>
					    <th>房屋产权证号</th>
						<td><input type="text" id="fwcqzh" name="fwcqzh" value="${fw.fwcqzh}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
					 </tr>
					 <tr>
					   	<th>房主姓名</th>
						<td><input type="text" id="fzxm" name="fzxm" value="${fw.fzxm}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>房主身份证号</th>
						<td><input type="text" id="fzsfzhm" name="fzsfzhm" value="${fw.fzsfzhm}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>房主联系电话</th>
						<td><input type="text" name="fzlxdh" value="${fw.fzlxdh}"  class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
					</tr>
					 <tr>
					   	<th>托管人姓名</th>
						<td><input type="text" id="tgrxm" name="tgrxm" value="${fw.tgrxm}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>托管人身份证号</th>
						<td><input type="text" id="tgrsfzhm" name="tgrsfzhm" value="${fw.tgrsfzhm}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>托管人联系电话</th>
						<td><input type="text" name="tgrlxdh" value="${fw.tgrlxdh}"  class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
					</tr>
					<tr>
					    <th>托管人与房主关系</th>
						<td><input type="text" name="yfzgx" value="${fw.yfzgx}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>是否出租房屋</th>
						<td colspan="3">
							<select id="sfczfw" name="sfczfw"  class="easyui-combobox" style="width: 170px;" data-options=" ">
								<option value="0" <c:if test="${fw.sfczfw eq '0' }">selected='selected' </c:if>>否</option>
								<option value="1" <c:if test="${fw.sfczfw eq '1' }">selected='selected' </c:if>>是</option>
							</select>
						<%-- <input type="text" name="sfczfw" value="${fw.sfczfw}"  class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						 --%></td>
					</tr>
					 <tr>
					    <th>房屋类别</th>
						<td>
							<input id="fwlb" dict="fwlbDict" name="fwlb" value="${fw.fwlb}" class="form-control"/>
						</td>
						<th>房屋性质</th>
						<td>
							<input id="fwxz" dict="fwxzDict" name="fwxz" value="${fw.fwxz}" class="form-control"/>
						</td>
						<th>房屋用途</th>
						<td>
							<input id="fwyt" dict="fwytDict" name="fwyt" value="${fw.fwyt}" class="form-control"/>
						</td>
					 </tr>
					 <tr>
					    <th>户型(*室*厅)</th>
						<td><input type="text" id="hx" name="hx" value="${fw.hx}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>房屋间数</th>
						<td><input type="text" name="fwjs" value="${fw.fwjs}"  class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
						<th>房屋面积(平米)</th>
						<td><input type="text" name="fwmj" value="${fw.fwmj}"  class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				
				<km:widgetTag widgetRulevalue="fw.updateOrAddFW">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
			</div>
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>