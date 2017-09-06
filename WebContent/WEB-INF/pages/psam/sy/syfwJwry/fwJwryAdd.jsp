	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
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
	//上级行政区域查找带回
	function openSjxzqyLookBack(){
		var param={"showXzjd":true,"showSqjcwh":true};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:400, url:url,params:param,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				 var xtype=resjson.xtype;
				 $("#sjxzqyid").val(id);
				 $("#sjxzqy_mc").val(text);
			}
			}};
		 returnBackDialog.open(options);
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
				 $("#pcsid").val(id);
				 $("#sspcs_mc").val(text);
			}
			}};
		 returnBackDialog.open(options);
	}
</script>
<t:DataDict code="JWQXZ" var="jwqxzDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss"  method="post" action="${ctx}/psam/jwq/addJwq">
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>境外人员采集</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
						<th>人员类别</th>
						<td><input type="text" name="RKFL" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[0,50]'" /></td>
					 	 <th>英文名</th>
						<td><input type="text" name="YWM" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" /></td>
					    <th>照片</th>
					     <td rowspan="4">
					 		<div style="width: 120px;height: 150px;border: 1px solid red; margin-left: 30px;">
					 			<font color="red">此处放图片</font>
					 		</div>
					 	</td>
					     
					 </tr>
					 <tr>
					 <th>英文姓</th>
						<td><input type="text" name="YWX" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" /></td>
					     <th>中文名</th>
						<td><input type="text" name="ZWM" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" /></td>
				 </tr>
					 <tr>
						<th>证件类型</th>
						<td><input type="text" id="ZJZL" name="ZJZL" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td>
						<th>证件号码</th>
						<td><input type="text" id="ZJHM" name="ZJHM" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" ></td>
					 </tr>

					 <tr>
					 	<th>性别</th>
						 <td>
						 <select name="XB" style="width:160px" class="form-control">
						 <option value="1">男</option>
						 <option value="0">女</option>
						 </select>
						 </td>
					
					 <th>出生日期</th>
					 <td><input type="text" name="CSRQ"  class="easyui-datebox easyui-validatebox form-control" data-options=" " /></td>
					 </tr>
					 <tr>
					  <th>联系方式</th>
					 <td><input type="text" name="JWRYLXDH" class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" /></td>
					  <th>国家地区</th>
						<td><input type="text" name="GJ"  class="easyui-textbox easyui-validatebox form-control"  data-options="required:true ,validType:'length[0,36]'" /></td>
					  <th>停留是由</th>
					 <td>
					 <select id="JLSY" name="JLSY" style="width:160px" class="form-control" >
					 <option value="">请选择</option>
					 </select>
					 </td>
				
					 </tr>
					 	 <tr>
					 	 
					  <th>入境日期</th>
						<td><input type="text" id="RJRQ" name="RJRQ" class="easyui-datebox easyui-validatebox form-control"  data-options=" " /></td>
					
					 <th>入住时间</th>
						<td><input type="text" id="RZSJ" name="RZSJ" class="easyui-textbox easyui-validatebox form-control"  data-options=" " /></td>
					 
					
					 </tr>
					 	 <tr>
					 	  <th>签证/签证类型</th>
						<td>
						<select name="QZZL" style="width:160px"  class="form-control">
						<option value="">请选择</option>
						</select>
						</td>
					 	 <th>居留（签证）有效期至</th>
						<td><input type="text" id="TLYXQ" name="TLYXQ" class="easyui-datebox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td>
					     </tr>
					 	 <tr>
					   <!--   <th>国内邀请人（单位）或电话</th>
						<td>
						<input type="text" id="ZZMM" name="ZZMM" class="easyui-textbox easyui-validatebox"  data-options="validType:'length[0,10]'" />
						</td> -->
					  <th>接待单位</th>
						<td>
						<select name="XXBC_CYZK" style="width:160px"  class="form-control">
						<option value="0">否</option>
						<option value="1">是</option>
						</select>
					 <th>现居住地</th>
						<td><input type="text" id="XXDZ" name="XXDZ" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,200]'" /></td> 
					 </tr>
					 <tr>
					 <th>备注</th>
					 <td colspan="3" >
					 <textArea name="BZ" rows="3" cols="60" class="form-control"></textArea>
					 </td>
					 
					 </tr>
					 <tr>
					 <th>登记人</th>
					
					 <td><input type="text" id="DJR" name="DJR" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td> 
					 
					  <th>登记单位</th>
					
					 <td><input type="text" id="DJDW" name="DJDW" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td> 
					 
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="jwq.addJwq">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
				</km:widgetTag>
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>