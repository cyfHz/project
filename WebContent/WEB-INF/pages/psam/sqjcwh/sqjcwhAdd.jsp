<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	
	function doSubmit(){
		if(PUBUtil.isSpecialCharacter($("#sqjcwhmc").val())){
			$.messager.alert('提示',"社区居村委会名称中不能包含特殊字符");	
			return;
		}
		if(PUBUtil.isSpecialCharacter($("#bmjc").val())){
			$.messager.alert('提示',"别名简称中不能包含特殊字符");	
			return;
		}
		if(!KMAJAX.validateFromCallback($("#formss"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.info("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close(100);
	}
	function openSjxzqyLookBack(){
		var param={"showXzjd":true,"showSqjcwh":false};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		var options={title:"查找带回",width:320,height:500, url:url,params:param,
		 	onClosed:function(data){
				var resjson=PUBUtil.jsonEval(data);
				if(resjson){
					var id=resjson.id;
					var text=resjson.text;
					var xtype=resjson.xtype;
					var yType=resjson.yType;
					if("XZJD"!=yType){
						alertMsg.error("请选择行政区划下相对应的乡镇街道");return;
					}
					 $("#sjxzqy_dzbm").val(id);
					 $("#sjxzqy_mc").val(text);
					 $("#sjxzqy_mc").focus();
					 
					 $("#sjxzqy_xtype").val(yType);
				}
			
			}
		 };
		 returnBackDialog.open(options);
	}
	function openSjsqjcwhLookBack(){
		var param={"showXzjd":true,"showSqjcwh":true};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		var options={title:"查找带回",width:320,height:500, url:url,params:param,
		 	onClosed:function(data){
				var resjson=PUBUtil.jsonEval(data);
				if(resjson){
					var id=resjson.id;
					var text=resjson.text;
					var xtype=resjson.xtype;
					var yType=resjson.yType;
					if("SQJCWH"!=yType){/* "SQJCWH"!=xtype 16-08-26 改*/
						alertMsg.error("请选择行政区划下相对应的社区居村委会");return;
					}
					 $("#sjsqjcwh_dzbm").val(id);
					 $("#sjsqjcwh_mc").val(text);
					 $("#sjsqjcwh_mc").focus();
				}
			
			}
		 };
		 returnBackDialog.open(options);
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">

	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DZYSFL" subCode="30" var="dzyslxDicts"></t:DataDict>
	<t:DataDict code="DYCXSX" var="dycxsxDict"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss" method="post" action="${ctx}/psam/sqjcwh/addSqjcwh">
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>社区居村委会添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <!-- <th>社区、居（村）委会代码</th>
						<td><input type="text" name="sqjcwhdm"  class="easyui-numberbox easyui-validatebox form-control" data-options="required:true,validType:'length[12,12]'" /></td>
						 -->
						 <th>社区、居（村）委会名称</th>
						<td><input type="text" id="sqjcwhmc" name="sqjcwhmc"  class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[1,50]'" /></td>
						<th>地址元素类型</th>
						<td>
						<input id="dzyslx" dict="dzyslxDicts" name="dzyslxdm" required="required" class="form-control" style="width:173px;" data-options="editable:false"/>
					 	</td>
					 </tr>
					 <tr>
						<th>所属社区居村委会</th>
						<td>
						<input type="text" id="sjsqjcwh_mc" onclick="openSjsqjcwhLookBack();"  class="easyui-textbox easyui-validatebox form-control" readonly="readonly"  />
						<input type="hidden" id="sjsqjcwh_dzbm" name="sjsqjcwh_dzbm" value="">
						</td>
						
						<th>上级行政区域</th>
						<td>
						<input id="sjxzqy_mc" class="easyui-textbox easyui-validatebox form-control" onclick="openSjxzqyLookBack();" required="required"  readonly="readonly"/>
						<input type="hidden" id="sjxzqy_dzbm" name="sjxzqy_dzbm" value="">
						<input type="hidden" id="sjxzqy_xtype" value="">
						</td>
						
					 </tr>
					 <tr>
					    <th>地域城乡属性</th>
						<td>
						<input id="dycxsx" dict="dycxsxDict" name="dycxsxdm"   style="width:173px;" required="required" data-options="editable:false"/>
						</td>
					    <th>别名简称</th>
						<td><input type="text" id="bmjc" name="bmjc"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
					</tr>
					<tr>	
						<th>使用状态</th>
						<td>
							<input id="syztdm" value="10" dict="syztDict" name="syztdm" class="form-control" style="width:173px;"  required="required" data-options="editable:false"/>
						</td>
					    <th>设立日期</th>
					    <jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
						<td><input type="text" name="slrq"  value='<fmt:formatDate value="${now }" type="both" />' class="easyui-datetimebox easyui-validatebox form-control" style="width:173px;" data-options="editable:false"/></td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="sqjcwh.addSqjcwh">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保存</a>
				</km:widgetTag>
				   <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取消</a>
			</div>
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>