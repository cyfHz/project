<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div style="width: 100%;height: 100%;overflow: hidden;">
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
	<span style="margin-left: 160px">	
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)"  iconCls="icon-add" class="easyui-linkbutton" onclick="openAddDyDialog()">添加单元</a>
	</km:widgetTag>
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)"  iconCls="icon-add" class="easyui-linkbutton" onclick="openAddLcDialog()">添加楼层</a>
	</km:widgetTag>
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)"  iconCls="icon-add" class="easyui-linkbutton" onclick="openBatchAddLc()">批量添加楼层</a>
	</km:widgetTag>
	</span>
</div>

<input type="hidden" id="jzwjgid" name="jzwjgid" value="${jzwjgid }">
<table id="tg" class="easyui-treegrid"  style="width:800px;height:510px"
			data-options="
				fit:false,
				rownumbers: true,
				fitColumns: true,
				url: '${ctx}/psam/jzwjg/loadJzwjgDyLcInfo?jzwjgid=${jzwjgid}',
				idField: 'id',
				treeField: 'mc' ">
		<thead>
			<tr>
				<th data-options="field:'mc',width:50,editor:'text'">单元或楼层名称</th>
				<th data-options="field:'xh',width:100,editor:'numberbox'">单元或楼层序号</th>
				<!-- <th data-options="field:'id',width:100, formatter:format">操作</th> -->
				<th data-options="field:'id',width:50,formatter:rowformater">操作</th>
			</tr>
		</thead>
	</table>
<div style="margin-top: 2px;margin-left: 10px;">
<font style="color: red;font-weight: bold;">说明：该建筑物结构中：单元数：${jzwjg.dys } ；楼层数：${jzwjg.lcs } ；每层户数：${jzwjg.mdyms }。</font>
</div>	

<div id="dlg_lc_edit" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 10px" closed="true" modal="true" 
	data-options="buttons:[{text:'确认',iconCls:'icon-ok', handler:saveLc},{text:'关闭',iconCls:'icon-cancel',handler:function(){$('#dlg_lc_edit').dialog('close')}}]">
			<form id="lc_edit_form">
				<div class="form-group">
					<label>所属单元：</label> 
					<input name="ss_dymc" id="ss_dymc" type="text" readonly="readonly"></input>
					<input name="ss_dyid" id="ss_dyid" type="hidden" readonly="readonly"></input>
					<font color="red">*(不可更改)</font>
				</div><br/>
				<div class="form-group">
					<label>楼层序号：</label> 
					<input name="lc_xh" id="lc_xh" type="text" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0" onblur="genLcMc();"  ></input><font color="red">*(数字)</font>
				</div><br/>
				<div class="form-group">
					<label>楼层名称：</label> 
					<input name="lc_mc" id="lc_mc" type="text" class="easyui-validatebox" data-options="required:true,validType:length[0,10]"></input><font color="red">*(根据序号自动生成)</font>
				</div>
				<br>
				<font color="red">*如果添加的楼层序号，比已经存在的最大楼层序号大，<br>请输入与该单元内最大楼层序号连续的楼层序号,<br><br>例如： 原先该单元内最大楼层序号：6，请输入 ：7，或者比6 小的而且该单元内不存在的楼层序号</font>
			</form>
</div>

<div id="dlg_lc_batch_edit" class="easyui-dialog" style="width: 400px; height: 260px; padding: 10px 10px" closed="true" modal="true" 
	data-options="buttons:[{text:'确认',iconCls:'icon-ok', handler:batchSaveLc},{text:'关闭',iconCls:'icon-cancel',handler:function(){$('#dlg_lc_batch_edit').dialog('close')}}]">
			<form id="lc_edit_batch_form">
				<div class="form-group">
					<label>楼层所属单元：</label> 
					<input name="batch_ss_dymc" id="batch_ss_dymc" type="text" readonly="readonly"></input>
					<input name="batch_ss_dyid" id="batch_ss_dyid" type="hidden" readonly="readonly"></input>
					<font color="red">*(不可更改)</font>
				</div><br/>
				<div class="form-group">
					<label>最大楼层序号：</label> 
					<input name="max_lc_xh" id="max_lc_xh" type="text" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0" ></input><font color="red">*(数字)</font>
				</div><br/>
				<div class="form-group">
					<label>默认名称后缀：</label> 
					<input name="default_lc_mc" id="default_lc_mc" type="text" value="${'楼'}" class="easyui-validatebox" data-options="required:true,validType:length[0,10]"></input>
				</div>
				<br>
		<font color="red">*只需输入最大楼层数即可，<br/>本操作会在该单元原有基础上，进行楼层添加，<br/>已经存在的楼层，不会覆盖</font>
			</form>
</div>

<div id="dlg_dy_edit" class="easyui-dialog" style="width: 400px; height: 260px; padding: 10px 10px" closed="true" modal="true" 
	data-options="buttons:[{text:'确认',iconCls:'icon-ok', handler:saveDy},{text:'关闭',iconCls:'icon-cancel',handler:function(){$('#dlg_dy_edit').dialog('close')}}]">
		<form id="dy_edit_form">
			<div class="form-group">
				<label>单元序号：</label> 
				<input name="dy_xh" id="dy_xh" type="text" class="easyui-numberbox easyui-validatebox" data-options="required:true,precision:0" onblur="genDyMc();"  ></input><font color="red">*(数字)</font>
			</div><br/>
			<div class="form-group">
				<label>单元名称：</label> 
				<input name="dy_mc" id="dy_mc" type="text" class="easyui-validatebox" data-options="required:true,validType:length[0,10]"></input><font color="red">*(根据序号自动生成)</font>
			</div><br/>
			<div class="form-group">
				<label>楼层数目：</label> 
				<input name="dy_lcs" id="dy_lcs" type="text" class="easyui-numberbox" data-options="required:true,precision:0" ></input>
			</div><br/>
			<div class="form-group">
				<label>每层门数：</label> 
				<input name="dy_ms" id="dy_ms" type="text" class="easyui-numberbox" data-options="required:true,precision:0" ></input><font color="red">*该单元每层房间数</font>
			</div><br/>
				<div class="form-group" >
				<label><font color="red">*根据楼层数量，系统自动生成该单元内对应楼层</font></label> 
				</div>
		</form>
</div>

<script type="text/javascript">
function rowformater(value,row,index){
	return "<a href='javacript:;' onclick=\"doDelete('"+row.id+"')\"  >删除</a>";
}
function doDelete(id){
	var row=$('#tg').treegrid('find',id);
	console.log(row);
	var jzwjgid=$("#jzwjgid").val();
	var id=row.id;
	var patentId=row._parentId;
	var jgtype=row.jgtype;
	var url="${ctx}/psam/jzwjg/delJzwjgLc";
	var params={};
	if('lc'==jgtype){
		url="${ctx}/psam/jzwjg/delJzwjgLc";
		params={"jzwjgid":jzwjgid,"lcid":id};
	}else {
		url="${ctx}/psam/jzwjg/delJzwjgDy";
		params={"jzwjgid":jzwjgid,"dyid":id};
	}
	
	 alertMsg.confirm("确定要删除数据？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() { alertMsg.close();
					KMAJAX.ajaxTodo(url,params,function(data){
						if(data.statusCode==200){
							alertMsg.info(data.message);
						}else{
							alertMsg.error(data.message);
						}
						$('#tg').treegrid('reload');
						setTimeout(function() {
							if('dy'==jgtype){$('#tg').treegrid('expand',id);}else{$('#tg').treegrid('expand',patentId);}}, 2000);
					});
				}
		});
	 

	
return "<a href='"+row.id+"' target='_blank'>删除</a>";
}
function openBatchAddLc(){
	var row = $('#tg').treegrid('getSelected');
	if(!row){alertMsg.error("请选择要添加楼层所属单元");return;}
	if("lc"==row.jgtype){alertMsg.error("请选择单元数据");return;}
	$("#lc_edit_batch_form").form("clear");
	var ss_dymc=row.mc;
	var ss_dyid=row.id;
	//console.log(ss_dymc);
	//console.log(ss_dyid);
	$("#batch_ss_dymc").val(ss_dymc);
	$("#batch_ss_dyid").val(ss_dyid);
	$("#default_lc_mc").val('楼');
	$("#dlg_lc_batch_edit").dialog("open").dialog("center").dialog("setTitle","楼层信息编辑");
}
function batchSaveLc(){
	var jzwjgid=$("#jzwjgid").val();
	var ss_dyid=$("#batch_ss_dyid").val();
	var max_lc_xh=$("#max_lc_xh").val();
	var default_lc_mc=$("#default_lc_mc").val();
	if(lc_mc.length>10){
		alertMsg.error("楼层名称长度过长");return;
	}
	var params={"jzwjgid":jzwjgid,"jzwjgDyid":ss_dyid,"max_lc_xh":max_lc_xh,"default_lc_mc":default_lc_mc};
	//console.log(params);
	 var ajaxUrl="${ctx}/psam/jzwjg/batchAddJzwjgLc";
	KMAJAX.ajaxTodo(ajaxUrl,params,function(data){
		if(data.statusCode==200){
			alertMsg.info(data.message);
			$("#dlg_lc_batch_edit").dialog("close");
		}else{
			alertMsg.error(data.message);
		}
		$('#tg').treegrid('reload');
		setTimeout(function() {$('#tg').treegrid('expand',ss_dyid);}, 2000);
	});
}
//-------------------
function openAddLcDialog(){
	var row = $('#tg').treegrid('getSelected');
	if(!row){alertMsg.error("请选择要添加楼层所属单元");return;}
	if("lc"==row.jgtype){alertMsg.error("请选择单元数据");return;}
	$("#lc_edit_form").form("clear");
	var ss_dymc=row.mc;
	var ss_dyid=row.id;
	$("#ss_dymc").val(ss_dymc);
	$("#ss_dyid").val(ss_dyid);
	$("#dlg_lc_edit").dialog("open").dialog("center").dialog("setTitle","楼层信息编辑");
}
function genLcMc(){
	var v=$("#lc_xh").val();
	if(!v){
		alertMsg.error("请输入楼层序号");return;
	}
	if(isNaN(v)){
		alertMsg.error("楼层序号必须为数字");return;
	}
	if(v.indexOf(".")>-1){
		alertMsg.error("楼层序号必须为整数");return;
	}
	if(!isNaN(v)&&!(v.indexOf(".")>-1)){
		$("#lc_mc").val(v+"楼");	
	}
}
function saveLc(){
	var jzwjgid=$("#jzwjgid").val();
	var ss_dyid=$("#ss_dyid").val();
	var lc_xh=$("#lc_xh").val();
	var lc_mc=$("#lc_mc").val();
	if(lc_mc.length>10){
		alertMsg.error("楼层名称长度过长");return;
	}
	var params={"jzwjgid":jzwjgid,"jzwjgDyid":ss_dyid,"lc_xh":lc_xh,"lc_mc":lc_mc};
	console.log(params);
	 var ajaxUrl="${ctx}/psam/jzwjg/addJzwjgLc";
	KMAJAX.ajaxTodo(ajaxUrl,params,function(data){
		if(data.statusCode==200){
			alertMsg.info(data.message);
			$("#dlg_lc_edit").dialog("close");
		}else{
			alertMsg.error(data.message);
		}
		$('#tg').treegrid('reload');
		setTimeout(function() {$('#tg').treegrid('expand',ss_dyid);}, 2000);
	});
}
//--------------------------------------------------------------------------------------------------------------------------

function openAddDyDialog(){
	$("#dy_edit_form").form("clear");
	$("#dlg_dy_edit").dialog("open").dialog("center").dialog("setTitle","单元信息编辑");
}
function genDyMc(){
	var v=$("#dy_xh").val();
	if(!v){
		alertMsg.error("请输入单元序号");return;
	}
	if(isNaN(v)){
		alertMsg.error("单元序号必须为数字");return;
	}
	if(v.indexOf(".")>-1){
		alertMsg.error("单元序号必须为整数");return;
	}
	if(!isNaN(v)&&!(v.indexOf(".")>-1)){
		$("#dy_mc").val(v+"单元");	
	}
}
function saveDy(){
	var jzwjgid=$("#jzwjgid").val();
	var dy_xh=$("#dy_xh").val();
	var dy_mc=$("#dy_mc").val();
	var dy_lcs=$("#dy_lcs").val();
	var dy_ms=$("#dy_ms").val();
	if(dy_mc.length>10){
		alertMsg.error("单元名称长度过长");return;
	}
	if(isNaN(dy_ms)){
		alertMsg.error("单元门数必须为数字");return;
	}
	if(dy_ms.indexOf(".")>-1){
		alertMsg.error("单元门数必须为整数");return;
	}
	var params={"jzwjgid":jzwjgid,"dy_xh":dy_xh,"dy_mc":dy_mc,"dy_lcs":dy_lcs,"dy_ms":dy_ms};
	//console.log(params);
	 var ajaxUrl="${ctx}/psam/jzwjg/addJzwjgDy";
	KMAJAX.ajaxTodo(ajaxUrl,params,function(data){
		if(data.statusCode==200){
			alertMsg.info(data.message);
			$("#dlg_dy_edit").dialog("close");
		}else{
			alertMsg.error(data.message);
		}
		$('#tg').treegrid('reload');
	});
}
//--------------------------------------------
var editingId=undefined;
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
	if (editingId != undefined){
		var t = $('#tg');
		t.treegrid('endEdit', editingId);
		t.treegrid('select', editingId);
		var row=t.treegrid('find', editingId);
		editingId = undefined;
		var params={"id":row.id,"xh":row.xh,"mc":row.mc,"jgtype":row.jgtype};
		 var ajaxUrl="${ctx}/psam/jzwjg/updateJzwjgDyLcXh";
		KMAJAX.ajaxTodo(ajaxUrl,params,function(data){
			if(data.statusCode==200){
				alertMsg.info(data.message);
			}else{
				alertMsg.error(data.message);
			}
		});
	}else{
		alertMsg.info("保存成功");
	}
}
function cancel(){
	if (editingId != undefined){
		$('#tg').treegrid('cancelEdit', editingId);
		editingId = undefined;
	}
}
function isInteger(a)
{
    var reg='/^(-|+)?d+$/';
    return reg.test(a);
}


</script>
</div>