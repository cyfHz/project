<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div style="margin:2px 0px;">
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()">编辑</a>
	</km:widgetTag>		
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()">保存</a>
	</km:widgetTag>	
	<km:widgetTag widgetRulevalue="jzwjbxx.updateJzwjgDyLcXh">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()">取消</a>
	</km:widgetTag>	
</div>

<table id="tg" class="easyui-treegrid"  style="width:600px;height:450px"
			data-options="
				fit:true,
				rownumbers: true,
				fitColumns: true,
				url: '${ctx}/psam/jzwjg/loadJzwjgDyLcInfo?jzwjgid=${jzwjgid}',
				idField: 'id',
				treeField: 'mc' ">
		<thead>
			<tr>
				<th data-options="field:'mc',width:30,editor:'text'">单元或楼层名称</th>
				<th data-options="field:'xh',width:100,editor:'text'">单元或楼层序号</th>
			</tr>
		</thead>
	</table>
<script type="text/javascript">
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
