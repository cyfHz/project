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
	//最低一级行政区划
	/* function openLookBack(){
		 var url="${ctx}/psam/jlx/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:400, url:url,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				var xtype=resjson.xtype;
				 $("#sszdyjxzqy_dzbm").val(id);
				 $("#sszdyjxzqy_mc").val(text);
				 $("#sszdyjxzqy_xtype").val(xtype);
			}
			}};
		 returnBackDialog.open(options);
	} */
	function openLookBack(){
		var param={"showXzjd":true,"showSqjcwh":true};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:500, url:url,params:param,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				 var xtype=resjson.xtype;
				 $("#sszdyjxzqy_dzbm").val(id);
				 $("#sszdyjxzqy_mc").val(text);
				 $("#sszdyjxzqy_xtype").val(xtype);
			}
			}};
		 returnBackDialog.open(options);
	}
	
	function openLookBackjlx(){
		var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm").val();
		var sszdyjxzqy_xtype=$("#sszdyjxzqy_xtype").val();
		/* if(!sszdyjxzqy_dzbm){
			alertMsg.warn("请选择最低行政区域");return;
		} */
		var orgCodeSubSix=$("#orgCodeSubSix").val();//当前登录人员的行政区划代码
		sszdyjxzqy_xtype="XZQH";
		var url="${ctx}/psam/jlx/loadBingbackJlxPage?sszdyjxzqy_dzbm="+orgCodeSubSix+"&sszdyjxzqy_xtype="+sszdyjxzqy_xtype+"&isLoadFromSuperXzqy=2";
		var options={title:"查找带回",width:700,height:500, url:url,
				onClosed:function(data){
				var resjson=PUBUtil.jsonEval(data);
				if(resjson){
					var id=resjson.dzbm;
					var text=resjson.jlxxqmc;
					$("#ssjlxxq_dzbm").val(id);
				    $("#ssjlxxq_dzbm_mc").val(text);	
				    $("#sszdyjxzqy_mc").val(resjson.zdyjxzqhmc);
					 $("#sszdyjxzqy_dzbm").val(resjson.zdyjxzqhdzbm);
					 
					 $("#ssjlxxq_dzbm_mc").validatebox('validate');
					 $("#sszdyjxzqy_mc").validatebox('validate');
				    /* var ajaxUrl = "${ctx}/psam/jlx/selectMcBySszdyjxzqyDzbm";
					 var param = { "dzbm" : id};
					 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						 $("#sszdyjxzqy_mc").val(data["MC"]);
						}); */
				}
		}};
		returnBackDialog.open(options);
	}
	function openLookBackjwq(){
		var url="${ctx}/psam/jwq/loadBingbackjwqPage";
		 var options={title:"查找带回",width:700,height:500, url:url,
				 onClosed:function(data){
					var resjson=PUBUtil.jsonEval(data);
					if(resjson){
						var id=resjson.jwqid;
						var text=resjson.jwqmc;
					    
						 $("#zaglssjwzrqdm").val(id);
						 $("#zaglssjwzrqmc").val(text);
						 $("#zaglssjwzrqmc").validatebox('validate');    
					}
					}};
				 returnBackDialog.open(options);
		
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
<t:DataDict code="DZYSFL" var="dzyslxdmDic"></t:DataDict><!-- subCode="60" -->
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss"  method="post" action="${ctx}/psam/jzwjbxx/updateJzwjbxx">
		 <input type="hidden" name="dzbm" value="${jzwjbxx.dzbm}"   />
		 <input type="hidden" id="orgCodeSubSix" value='<c:out value="${xzqh.dzbm }"></c:out>'></input>
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;"/>建筑物基本信息编辑</legend>
					<table id="formTbale" cellpadding="4">
					 <tr>
					 <th>所属最低一级行政区域</th>
	                        <td>
						<input id="sszdyjxzqy_mc" name="sszdyjxzqy_mc" value="${jlx.sszdyjxzqy_mc}" class=" easyui-textbox easyui-validatebox form-control" onclick="openLookBack();" required="required"  readonly="readonly"/>
						<input type="hidden" id="sszdyjxzqy_dzbm" name="sszdyjxzqy_dzbm" value="${jlx.sszdyjxzqy_dzbm }">
						<input type="hidden" id="sszdyjxzqy_xtype"  value="">
						</td> 
						<th>所属街路巷（小区）</th>
						<%-- <td><input type="text" name="ssjlxxq_dzbm" value="${jzwjbxx.ssjlxxq_dzbm}" class="easyui-textbox easyui-validatebox" data-options="required:true ,validType:'length[1,36]'" /></td>  --%>
					    	<td>
						<input type="text" name="ssjlxxq_dzbm_mc" id="ssjlxxq_dzbm_mc" value="${jlx.jlxxqmc}" onclick="openLookBackjlx();" class="form-control easyui-textbox easyui-validatebox" data-options="required:true " readonly="readonly"  />
						<input type="hidden" id="ssjlxxq_dzbm" name="ssjlxxq_dzbm" value="${jlx.dzbm}"><!-- ${jlx.ssjlxxq_dzbm} -->
						</td> 
					 </tr>
					 
					 <tr>
					    <th>建筑物类型代码</th>
					   <%--  <td><input type="text" id="dzyslxdm" name="dzyslxdm" value="${jzwjbxx.dzyslxdm}" class="easyui-textbox easyui-validatebox"  data-options="validType:'length[0,2]'"/></td> --%>
						<td><input id="dzyslxdm" dict="dzyslxdmDic" name="dzyslxdm" class="form-control easyui-textbox easyui-validatebox"   value="${jzwjbxx.dzyslxdm}" style="width:173px;"  required="required" data-options="editable:false"/></td>
						<th>建筑物名称</th>
						<td><input type="text" id="jzwmc" name="jzwmc"  value="${jzwjbxx.jzwmc}" class="form-control easyui-textbox easyui-validatebox"  data-options="required:true,validType:'length[0,300]'"   /></td>	
				
					 </tr>
					 <tr>
					 <th>别名简称</th>
					 <td><input type="text" id="bmjc" name="bmjc" value="${jzwjbxx.bmjc}" class="form-control easyui-textbox easyui-validatebox"  data-options="validType:'length[0,200]'" /></td>
					 <th>是否可用</th>
					 <td>
					 <select id="enable" name="enable" value="${jzwjbxx.enable}" style="width:172px;" data-options="editable:false" class="form-control  easyui-validatebox easyui-combobox">
					 <option value="1">可用</option>
					 <option value="0">不可用</option>
					 </select>
					 </td>
					 </tr>
					 <tr>
					 <th>中心点横坐标</th> 
                       <td><input type="text" id="zxdhzb"  value="${jzwjbxx.zxdhzb}" name="zxdhzb" maxLength="11" class="form-control easyui-textbox easyui-validatebox" /></td>
					   <th>中心点纵坐标</th>
					 <td><input type="text" id="zxdzzb"  value="${jzwjbxx.zxdzzb}" name="zxdzzb" maxLength="11" class="form-control easyui-textbox easyui-validatebox" /></td>
					 </tr>
					 <tr>
					 <th>地址名称</th>
					 <td colspan="3">
					 <textarea rows="1" cols="70" name="dzmc" id="dzmc" data-options="validType:'length[0,200]'" style="width: 480px;" class=" easyui-textbox easyui-validatebox">${jzwjbxx.dzmc}</textarea>
					 </td>
					 </tr>
					 <tr>
					 <th>地址是否存在</th>
					 <td>
					 <select id="dzzczbz" name="dzzczbz" value="${jzwjbxx.dzzczbz}" style="width:172px;" data-options="editable:false" class="form-control easyui-combobox easyui-validatebox">
					 <option value="1">存在</option>
					 <option value="0">不存在</option>
					 </select>
					 </td>
					 <th>地址是否在用</th>
					 <td>
					 <select id="dzzzybs" name="dzzzybs" value="${jzwjbxx.dzzzybs}" style="width:172px;" data-options="editable:false" class="easyui-combobox easyui-validatebox">
					 <option value="1">在用</option>
					 <option value="0">已不用</option>
					 </select>
					 </td>
					 </tr>
					<tr>
					<th>警务责任区名称</th>
				<%-- 	<td>
					<input type="text" name="zaglssjwzrqmc" id="zaglssjwzrqmc" value="${jzwjbxx.zaglssjwzrqmc}" onclick="openLookBackjwq();" class="form-control easyui-validatebox" data-options="required:true " />
						<input type="hidden" id="zaglssjwzrqdm" name="zaglssjwzrqdm" value=${jzwjbxx.zaglssjwzrqdm}>
					</td> --%>
						<td>
					  <input type="text" name="zaglssjwzrqmc" id="zaglssjwzrqmc" value="${jzwjbxx.zaglssjwzrqmc}"  class="form-control easyui-textbox easyui-validatebox" data-options="required:true " readonly="readonly" onclick="openLookBackjwq();" />
						<input type="hidden" id="zaglssjwzrqdm" name="zaglssjwzrqdm" value=${jzwjbxx.zaglssjwzrqdm}>
					</td>
					</tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				
				<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjbxx">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>