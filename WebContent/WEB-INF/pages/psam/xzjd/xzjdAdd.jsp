<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	
	function doSubmit(){
		if(PUBUtil.isSpecialCharacter($("#xzjdmc").val())){
			$.messager.alert('提示',"乡镇街道名称中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#bmjc").val())){
			$.messager.alert('提示',"别名简称中不能包含特殊字符");	
			return;
		}
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close(100);
	}
	function openSjxzqyLookBack(){
		var param={"showXzjd":false,"showSqjcwh":false};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:500, url:url,params:param,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				 var xtype=resjson.xtype;
				 $("#sjxzqy_dzbm").val(id);
				 $("#sjxzqy_mc").val(text);
				 $("#sjxzqy_mc").focus();
				 $("#sjxzqy_xtype").val(xtype);
			}
			}};
		 returnBackDialog.open(options);
	}
	function parserDate(date){
		var t = Date.parse(date);
		if (!isNaN(t)){
			return new Date(t);
		} else {
			return new Date();
		}
	}
	
</script>
<t:DataDict code="DZYSFL" subCode="20" var="dzyslxDicts"></t:DataDict>
<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post"  action="${ctx}/psam/xzjd/addXzjd" >
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>乡镇街道信息添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <!-- <th>乡镇（街道）代码</th>
						<td><input type="text" name="xzjddm" class="easyui-numberbox easyui-validatebox form-control"  data-options="required:true,validType:'length[9,9]'" /></td>
						 -->
						<th>乡镇（街道）名称</th>
						<td><input type="text" id="xzjdmc" name="xzjdmc" class="easyui-textbox easyui-validatebox form-control"  data-options=" required:true ,validType:'length[1,50]'" /></td>
						
						<th>地址元素类型代码</th>
						<td>
							<input id="dzyslxdm" dict="dzyslxDicts" name="dzyslxdm"  required="required" class="form-control" style="width: 170px;" data-options="editable:false" />
						</td>
					 </tr>
					 <tr>
					    <th>别名简称</th>
						<td><input type="text" id="bmjc" name="bmjc" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
						
						<th>上级行政区域</th>
						<td>
							<input id="sjxzqy_mc"  name="sjxzqy_mc" class="form-control easyui-textbox easyui-validatebox" onclick="openSjxzqyLookBack();" required="required" readonly="readonly" />
							<input type="hidden" id="sjxzqy_dzbm" name="sjxzqy_dzbm" >
							<input type="hidden" id="sjxzqy_xtype" value="">
						</td>

					 </tr>
					 <tr>
					    <th>使用状态</th>
						<td>
							<input id="syztdm" value="10" required="required" style="width:173px;" dict="syztDict" name="syztdm" class="form-control" data-options="editable:false"/>
						</td>
					 	<th>设立日期</th>
					 	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
						<td><input type="text" name="slrq" id="slrq" value='<fmt:formatDate value="${now }" type="both" />' class="easyui-datetimebox form-control"  data-options="editable:false" style="width:170px;"/></td>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>