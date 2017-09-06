<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<script type="text/javascript">
	function doSubmit(){
		if(PUBUtil.isSpecialCharacter($("#jlxxqmc").val())){
			$.messager.alert('提示',"街路巷名称中不能包含特殊字符");	
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
	
	function openSszdyjxzqyLookBack(){
		var param={"showXzjd":true,"showSqjcwh":true};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:530, url:url,params:param,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				 var xtype=resjson.xtype;
				 $("#sszdyjxzqy_dzbm").val(id);
				 $("#sszdyjxzqy_mc").val(text);
				 $("#sszdyjxzqy_mc").focus();
				 $("#sszdyjxzqy_xtype").val(xtype);
				
			}
			}};
		 returnBackDialog.open(options);
	}
	function openLookBack_jlx(){
		var url="${ctx}/psam/jlx/loadBingbackJlxPage";
		var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm").val();
		var sszdyjxzqy_xtype=$("#sszdyjxzqy_xtype").val();
		/*  if(!sszdyjxzqy_dzbm){
			 alertMsg.warn("请选择最低行政区域");return;
		 } */
		var orgCodeSubSix=$("#orgCodeSubSix").val();//当前登录人员的行政区划代码
		sszdyjxzqy_xtype="XZQH";
		var params={"sszdyjxzqy_dzbm":orgCodeSubSix,"sszdyjxzqy_xtype":sszdyjxzqy_xtype,"isLoadFromSuperXzqy":2}
		var options={title:"查找带回",width:800,height:530, url:url,params:params,
		onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var dzbm=resjson.dzbm;
				var jlxxqmc=resjson.jlxxqmc;
				 $("#ssjlxxq_dzbm").val(dzbm);
				 $("#ssjlxxq_mc").val(jlxxqmc);
				 $("#sszdyjxzqy_mc").val(resjson.zdyjxzqhmc);
				 
				 $("#ssjlxxq_mc").focus();
				 
				 $("#sszdyjxzqy_dzbm").val(resjson.zdyjxzqhdzbm);
				/*  var ajaxUrl = "${ctx}/psam/jlx/selectMcBySszdyjxzqyDzbm";
				 var param = { "dzbm" : dzbm};
				 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					 $("#sszdyjxzqy_mc").val(data["MC"]);
					}); */
			}
			}};
		 returnBackDialog.open(options);
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">

	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<t:DataDict code="DZYSFL" subCode="40" var="dzyslxDicts"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss" method="post" action="${ctx}/psam/jlx/addJlx">
		 <input type="hidden" name="dzbm" value="${jlx.dzbm}"   />
		 <input type="hidden" id="orgCodeSubSix" value='<c:out value="${xzqh.dzbm }"></c:out>'></input>
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>街路巷（小区）信息添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <%-- <th>街路巷（小区）代码</th>
						<td><input type="text" name="jlxxqdm"   class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td> --%>
						<th>街路巷（小区）名称</th>
						<td><input type="text" id="jlxxqmc" name="jlxxqmc"  class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[1,100]'" /></td>
					   <th>地址元素类型</th>
						<td>
						<input id="dzyslx" dict="dzyslxDicts" name="dzyslxdm" class="form-control" required style="width:173px;" data-options="editable:false"/>
					 </tr> 
					  <tr>	
						<th>所属最低一级行政区域</th>
						<td>
						<input id="sszdyjxzqy_mc" name="sszdyjxzqy_mc" value="${sszdyjxzqy_mc}"  class="form-control" onclick="openSszdyjxzqyLookBack();" required="required" style="width:170px;" readonly="readonly"/>
						<input type="hidden" id="sszdyjxzqy_dzbm" name="sszdyjxzqy_dzbm" value="${sszdyjxzqy_dzbm}">
						<input type="hidden" id="sszdyjxzqy_xtype"  value="${XTYPE}">
						</td>
						    <th>所属街路巷（小区）</th>
					 	<td>
						<input type="text" id="ssjlxxq_mc"  name="ssjlxxq_mc"  class="easyui-textbox easyui-validatebox form-control"  onclick="openLookBack_jlx();" readonly="readonly" />
						<input type="hidden" id="ssjlxxq_dzbm" name="ssjlxxq_dzbm" value="">
						</td>
					</tr>
					<tr>	
						<th>别名简称</th>
						<td><input type="text" name="bmjc"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,100]'" /></td>
					<th>使用状态</th> 
						<td>
							<input id="syztdm" dict="syztDict" name="syztdm" value="10" class="form-control" style="width:173px;" data-options="editable:false"/>
						</td>
					</tr> 
					 <tr>	
					 	
						 <th>设立日期</th>
						<td><input type="text" name="slrq"   class="easyui-datetimebox easyui-validatebox form-control" data-options="editable:false" style="width:173px;" /></td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="jlx.addJlx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保存</a>
				</km:widgetTag>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取消</a>
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>