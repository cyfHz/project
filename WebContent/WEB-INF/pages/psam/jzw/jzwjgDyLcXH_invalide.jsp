<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div style="width: 100%;height: 100%;overflow: hidden;"   class="easyui-layout" data-options="fit:true">
<div data-options="region:'west',border:true" style="width:160px;"><br/>
<div style="text-align: center;"><font style="font-weight: bold;">建筑物结构信息</font></div><br/>
<hr/>
<form id="jg_edit_form">
<div class="form-group">
<label>　　　单元数：</label> 
<input name="jg_dys" id="jg_dys" type="text" size="3"  value="${jzwjg.dys}" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0"></input>
		</div><br/>
<div class="form-group">
			<label>　最大楼层数：</label> 
<input name="jg_lcs" id="jg_lcs" type="text" size="3" value="${jzwjg.lcs}" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0"></input>
		</div><br/>
<div class="form-group">
<label>　　每层门数：</label> 
<input name="jg_mdyms" id="jg_mdyms" type="text" size="3" value="${jzwjg.mdyms}" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0"></input>
		</div><br/>
<div class="form-group">
<label>　　地下层数：</label> 
<input name="jg_dxcs" id="jg_dxcs" type="text" size="3" value="${jzwjg.dxcs}" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0"></input>
		</div><br/>
 <div class="form-group">
<label>地下每层门数：</label> 
<input name="jg_dxmcms" id="jg_dxmcms" type="text" size="3" value="${jzwjg.dxmcms}" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0"></input>
		</div>   <br/>
		
		
<!--  -->
 <div style="text-align: center;">
			<a href="javascript:void(0)" iconCls="icon-save"  class="easyui-linkbutton" onclick="saveJzwJg()">保存</a>
</div>
</form>
<hr/>	
<font color="red">*注意：<br>&nbsp;请首先修改左侧建筑结构，<br> &nbsp;并保存确认该信息<br>&nbsp;再修改对应单元、楼层信息</font>	
</div>
<div data-options="region:'center',border:true" style="width:800px;">
<div style="margin:4px 10px;height: 25px;">
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)" iconCls="icon-edit" class="easyui-linkbutton" onclick="edit()">编辑</a>
	</km:widgetTag>		
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)" iconCls="icon-save"  class="easyui-linkbutton" onclick="save()">保存</a>
	</km:widgetTag>	
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)" iconCls="icon-cancel"  class="easyui-linkbutton" onclick="cancel()">取消</a>
	</km:widgetTag>	
</div>

<input type="hidden" id="jzwjgid" name="jzwjgid" value="${jzwjgid }">
<table id="tg" class="easyui-treegrid"  style="width:800px;height:510px"
			data-options="
				fit:true,
				rownumbers: true,
				fitColumns: true,
				url: '${ctx}/psam/jzwjg/loadJzwjgDyLcInfo?jzwjgid=${jzwjgid}',
				idField: 'id',
				treeField: 'mc' ">
		<thead>
			<tr>
				<th data-options="field:'mc',width:50,editor:'text'">单元或楼层名称</th>
				<th data-options="field:'xh',width:100,editor:'numberbox'">单元或楼层序号</th>
				<th data-options="field:'lcs',width:100">楼层数或房间数</th>
				<!-- <th data-options="field:'id',width:100, formatter:format">操作</th> -->
				<!-- <th data-options="field:'id',width:50,formatter:rowformater">操作</th> -->
			</tr>
		</thead>
	</table>
</div>
<script type="text/javascript">

//------------------------------------------------------
var datax=undefined;//
var editingId=undefined;

//t.treegrid('getData');
function edit(){
	if (editingId != undefined){
		$('#tg').treegrid('select', editingId);
		return;
	}
	var row = $('#tg').treegrid('getSelected');
	if (row){
		editingId = row.id;
		$('#tg').treegrid('beginEdit', editingId);
	}else{
		alertMsg.error("请选择一条记录");
	}
}
function save(){
	if (editingId != undefined&&editingId!="undefined"){
		var t = $('#tg');
		t.treegrid('endEdit', editingId);
		t.treegrid('select', editingId);
		var row=t.treegrid('find', editingId);
		var params={"id":row.id,"xh":row.xh,"mc":row.mc,"lcs":row.lcs,"jgtype":row.jgtype};
		var ajaxUrl="${ctx}/psam/jzwjg/updateJzwjgDyLcXh";
		KMAJAX.ajaxTodo(ajaxUrl,params,function(data){
			if(data.statusCode==200){
				alertMsg.info(data.message);
			}else{
				setTimeout(function(){$('#tg').treegrid('reload');},200); 
				alertMsg.error(data.message);
				t.treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
			editingId = undefined;
		});
	}else{
		//alertMsg.info("保存成功");
	}
}

function cancel(){
	if (editingId != undefined){
		$('#tg').treegrid('cancelEdit', editingId);
		editingId = undefined;
	}
	setTimeout(function(){$('#tg').treegrid('reload');},200); 
}
function isInteger(a)
{
    var reg='/^(-|+)?d+$/';
    return reg.test(a);
}
function saveJzwJg(){
	var jzwjgid=$("#jzwjgid").val();
	var jg_dys=$("#jg_dys").numberbox('getValue'); 
	var jg_lcs=$("#jg_lcs").numberbox('getValue'); 
	var jg_mdyms=$("#jg_mdyms").numberbox('getValue'); 
	var jg_dxcs=$("#jg_dxcs").numberbox('getValue'); 
	var jg_dxmcms=$("#jg_dxmcms").numberbox('getValue'); 
	/* if(lc_mc.length>10){
		alertMsg.error("楼层名称长度过长");return;
	} */
	var params={"jzwjgid":jzwjgid,"jg_dys":jg_dys,"jg_lcs":jg_lcs,"jg_mdyms":jg_mdyms,"jg_dxcs":jg_dxcs,"jg_dxmcms":jg_dxmcms};
	//console.log(params);
	 var ajaxUrl="${ctx}/psam/jzwjg/updateJzwjg";
	KMAJAX.ajaxTodo(ajaxUrl,params,function(data){
		if(data.statusCode==200){
			alertMsg.info(data.message);
		}else{
			alertMsg.error(data.message);
		}
	});
	
}
function reloadjg(){
	var jzwjgid=$("#jzwjgid").val();
	var ajaxUrl="${ctx}/psam/jzwjg/loadJzwjg";
	KMAJAX.ajaxTodo(ajaxUrl,{"jzwjgId":jzwjgid,"isJustJg":"1"},function(datax){
		var data= datax.data.jzwjg;
		$("#jg_dys").numberbox('setValue',data.dys); 
		$("#jg_lcs").numberbox('setValue',data.lcs); 
		$("#jg_mdyms").numberbox('setValue',data.mdyms); 
		$("#jg_dxcs").numberbox('setValue',data.dxcs); 
		$("#jg_dxmcms").numberbox('setValue',data.dxmcms); 
	});
}
</script>
</div>