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
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>警务区信息添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <!-- <th>警务区编号</th>
						<td><input type="text" name="jwqbh" class="easyui-numberbox easyui-validatebox" data-options="required:true,validType:'length[1,50]'" /></td>
						 -->
						<th>所属派出所</th>
						 <td>
						 <input id="sspcs_mc" class="form-control easyui-validatebox" onclick="openSspcsLookBack();"  readonly="readonly" data-options="required:true" style="width:170px;"/>
						 <input type="hidden" id="pcsid" name="pcsid" value="">
						 </td>
						 
						<th>上级行政区域</th>
						<td>
						<input id="sjxzqy_mc" class="form-control easyui-validatebox "  data-options="required:true" onclick="openSjxzqyLookBack();" style="width:170px;" readonly="readonly" />
						<input type="hidden" id="sjxzqyid" name="sjxzqyid" value="">
						</td> 
					 	
					 </tr>
					 <tr>
					    
						<th>警务区名称 </th>
						<td><input type="text" name="jwqmc"  class="easyui-textbox easyui-validatebox form-control"  data-options="required:true ,validType:'length[1,100]'" /></td>
						<th>警务区简介</th>
						<td><input type="text" id="jwqjj" name="jwqjj" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[1,300]'" /></td>
						
					 </tr>

					 <tr>
					 <th>警务区面积(平方米)</th>
						<td><input type="text" id="jwqmj" name="jwqmj" class="easyui-numberbox easyui-validatebox form-control"  data-options="precision:2,validType:'length[0,10]'" style="width:170px;" ></td>
					 <th>居委会数量(个)</th>
					 <td><input type="text" name="jwhsl"  class="easyui-numberbox easyui-validatebox form-control"   data-options="validType:'length[0,8]'" style="width:170px;" /></td>
					 </tr>
					 <tr>
					 <th>农村管区数量(个)</th>
					 <td><input type="text" name="ncgqsl" id="ncgqsl" class="easyui-numberbox easyui-validatebox form-control" data-options="validType:'length[0,8]'" style="width:170px;"/></td>
					 <th>警务区性质</th>
					 <td>
                        <input id="jwqxz" dict="jwqxzDict" name="jwqxz" class="form-control easyui-validatebox" style="width: 175px" data-options="editable:false">
					 </td> 		
					 </tr>
					 <tr>
					 <th>启用日期 </th>
                       <td><input type="text" name="qyrq"  class="easyui-datebox easyui-validatebox  form-control" style="width: 175px" data-options="editable:false"></td>
					 <th>备注</th>
					 <td>
					 <input type="text" name="bz"  class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[1,200]'"/>
					 </td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="jwq.addJwq">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
				
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>