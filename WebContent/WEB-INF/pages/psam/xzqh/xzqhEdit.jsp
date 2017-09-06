<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<script type="text/javascript">
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
			 $("#text_sjxzqy_dzbm").val(id);
			 $("#text_sjxzqymc").val(text);
			 $("#text_sjxzqymc").focus();
		}
		}};
	 returnBackDialog.open(options);
}

function saveXzqhEdit(){
	if(PUBUtil.isSpecialCharacter($("#XZQHMC").val())){
		$.messager.alert('提示',"行政区划名称中不能包含特殊字符");	
		return;
	}
	if(PUBUtil.isSpecialCharacter($("#BMJC").val())){
		$.messager.alert('提示',"别名简称中不能包含特殊字符");	
		return;
	}
	if(!KMAJAX.validateFromCallback($("#fmX"),KMCORE.ajaxDoneAndCloseDialog)){
		alertMsg.info("请确认校验不通过数据");
	}
	/* var parm=$('#fmX').serializeArray();
	var url="${ctx}/psam/xzqh/add";
	 KMAJAX.ajaxTodo(url, parm, function(data){
		 if(data.statusCode==200){
			 alertMsg.info(data.message);
			 reloadXzqhDg();
		 }else{
			 alertMsg.error(data.message);
		 }
	  		
	 }); */
}

function cancelEdit(){
	editDialog.close(100);
}
</script>
    <t:DataDict code="DZYSFL"  var="dzyslxDict"></t:DataDict>
	<t:DataDict code="DZYSFL" subCode="10" var="dzyslxDicts"></t:DataDict>
	<t:DataDict code="DZ_SYZT" var="syztDict"></t:DataDict>
	<div class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form  id="fmX"  method="post" action="${ctx}/psam/xzqh/save">
		 <input type="hidden" name="DZBM" value="${xzqh.DZBM}"/>
			<fieldset>
				<legend><img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;"/>行政区划信息添加</legend>
				 <table id="formTbale" cellpadding="4">
					 <tr>
					    <th>行政区划代码</th>
					    <td>
				          <input type="hidden"  name="DZBM">
				          <input type="text" name="XZQHDM" id="XZQHDM" value="${xzqh.XZQHDM}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true ,validType:'length[1,12]'" />
					    </td>  
					 </tr>
					 <tr>
					    <th>行政区划名称</th>
					    <td>
				          <input id="XZQHMC" name="XZQHMC" value="${xzqh.XZQHMC}" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true ,validType:'length[1,30]'" />
					    </td>  
					 </tr>
					 <tr>
					    <th>别名简称</th>
					    <td>
				          <input id="BMJC" name="BMJC" value="${xzqh.BMJC}" class="easyui-textbox easyui-validatebox " data-options="required:true ,validType:'length[1,30]'" />
					    </td>  
					 </tr>
					 <tr>
					    <th>使用状态</th>
					    <td>
				           <input dict="syztDict" id="syzt_syz_input" value="${xzqh.SYZTDM}" name="SYZTDM" class="form-control" style="width:175px;" data-options="required:true ,editable:false"/>
					    </td>  
					 </tr>
					 <tr>
					    <th>地址元素类型</th>
					    <td>
				           <input id="dzyslxdm" dict="dzyslxDicts" value="${xzqh.DZYSLXDM}" name="DZYSLXDM"   class="form-control" style="width: 175px;" data-options="required:true ,editable:false" />
					    </td>  
					 </tr>
					 <tr>
					    <th>上级行政区域</th>
					    <td>
				          <input id="text_sjxzqymc" onclick="openSjxzqyLookBack()" name="SJXZQYMC" value="${xzqh.SJXZQYMC}" class="easyui-textbox easyui-validatebox" readonly="readonly" data-options="required:true"/>
				          <input type="hidden" id="text_sjxzqy_dzbm" name="SJXZQY_DZBM" value="${xzqh.SJXZQY_DZBM}">
					    </td>  
					 </tr>
					 <tr>
					    <th>设立日期</th>
					    <td>
				          <input type="text" name="SLRQ" class="easyui-textbox form-control easyui-datebox" value="${xzqh.SLRQ}" style="width:175px;" data-options="editable:false"/>
					    </td>  
					 </tr>
				 </table>
			</fieldset>
			
		</form>
	</div>
         <div style="position: absolute;bottom: 5px;right: 10px;">
				<a href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-ok" onclick="saveXzqhEdit()" >保存</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelEdit()" >关闭</a>
		</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>