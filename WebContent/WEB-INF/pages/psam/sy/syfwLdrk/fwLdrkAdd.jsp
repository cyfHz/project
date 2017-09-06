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
		<form id="formss" class="form-horizontal" method="post" action="${ctx}/psam/jwq/addJwq">
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>常住人口采集</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <th>公民身份证号</th>
						<td><input type="text" name="SFZH" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,20]'" /></td>
					
					 	 <th>姓名</th>
						<td><input type="text" name="XM" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
					 		<td rowspan="4">
					 		<div style="width: 120px;height: 150px;border: 1px solid red; margin-left: 30px;">
					 		  
					 			<font color="red">此处放图片</font>
					 		</div>
					 	</td>
					 </tr>
					 <tr>
					    <th>性别</th>
						 <td>
						 <select name="XB" style="width:160px" class="form-control">
						 <option value="1">男</option>
						 <option value="0">女</option>
						 </select>
						 </td>
						  <th>婚姻状况</th>
					 <td>
                        <select name="HYZK" style="width:160px" class="form-control">
                        <option value="1">未婚</option>
                        <option value="0">已婚</option>
                        </select>
					 </td> 		
						
					 </tr>
					 <tr>
					 <th>政治面貌</th>
						<td><input type="text" id="ZZMM" name="ZZMM" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,10]'" /></td>
					<th>民族</th>
						<td><input type="text" id="MZ" name="MZ" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,2]'" /></td>
					 </tr>
                     <tr>
                    <th>手机</th>
						<td><input type="text" id="LXFS_SJ" name="LXFS_SJ" class="easyui-numberbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" ></td> 
                        <th>学历</th>
						<td><input type="text" id="XL" name="XL" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,10]'" /></td>
                     </tr>
					 <tr>
					 <th>现居住地住址</th>
					 <td><input type="text" name="XJZDZZ"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,500]'" /></td>
					 <th>房间号</th>
					 <td><input type="text" name="XJZDZZFJH" id="XJZDZZFJH" class="easyui-numberbox easyui-validatebox form-control" data-options="validType:'length[0,50]'"/></td>
						 
					<th>兵役状况</th>
						<td>
						<select name="FBYQK" style="width:160px" class="form-control">
						<option value="0">否</option>
						<option value="1">是</option>
						</select>
					 </tr>
					 	 <tr>
					 <th>住所类别</th>
						<td><input type="text" id="ZSLB" name="ZSLB" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,10]'" /></td>
					  <th>居住事由</th>
						<td><input type="text" id="JZSY" name="JZSY" class="easyui-textbox easyui-validatebox form-control"   data-options="validType:'length[0,10]'" /></td>
					<th>拟居住期限</th>
						<td><input type="text" id="NJZQX" name="NJZQX" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td>
					 </tr>
					 	 <tr>
					 <th>是否申请居住证</th>
						<td>
						<!-- <input type="text" id="SFSLJZZ" name="SFSLJZZ" class="easyui-textbox easyui-validatebox"  data-options="validType:'length[0,10]'" /> -->
						<select name="SFSLJZZ" style="width:160px" class="form-control">
						<option value="1">是</option>
						<option value="0">否</option>
						</select>
						</td>
					  <th>居住地有无工作单位</th>
						<td>
						<select name="JZDYWGZDW" style="width:160px" class="form-control">
						<option value="0">否</option>
						<option value="1">是</option>
						</select>
					
					 <th>到达本地日期</th> 
						<td><input type="text" id="DDBDRQ" name="DDBDRQ" class="easyui-datebox easyui-validatebox form-control"  data-options=" " /></td> 
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