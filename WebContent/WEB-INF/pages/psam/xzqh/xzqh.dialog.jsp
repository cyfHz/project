<%@ page contentType="text/html; charset=utf-8"%>
<div id="xzqhDialog" class="easyui-dialog" style="width: 280px; height: 400px; padding: 10px 20px" closed="true" buttons="#xzqhDialog-buttons">
	<ul id="xzqhDialogTree"></ul>
</div>
<div id="xzqhDialog-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="selected()" style="width: 90px">选择</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#xzqhDialog').dialog('close')" style="width: 90px">关闭</a>
</div>
<script type="text/javascript">
	var xzqhDlg = {};
	$(function() {
		$('#xzqhDialogTree').tree($.extend({
			url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:'${param.showXzjd}',showSqjcwh:'${param.showSqjcwh}'}),
			loadFilter: function(data) {
				if (!data || !data.rows || data.rows.length < 1) {
					return [];
				}
				var nodes = [];
				$(data.rows).each(function(i, row) {
					var node = {};
					node.id = row.NODETYPE+'_'+row.NODEID;
					node.text = row.NODETEXT;
					node.state = row.NODETYPE == 'SQJCWH' ? "open":"closed";
					nodes.push(node);
				});
				return nodes;
			},
			cascadeCheck:false,
			checkbox:true,
			onLoadSuccess: function() {
				$('#xzqhDialogTree').tree('expand',$('#xzqhDialogTree').tree('getRoot').target);
			}
		},typeof treeDialogOpts === "undefined" ? {}:treeDialogOpts));
	});
	function openXzqhTreeDialog(callback){
		$($('#xzqhDialogTree').tree('getChecked')).each(function(i,n){
			$('#xzqhDialogTree').tree('uncheck',n.target);//取消之前选中的节点
		});
		$('#xzqhDialog').dialog('open').dialog('hcenter').dialog('setTitle','选择行政区划');
		xzqhDlg.callback = callback;
	}
	function selected(){
		var nodex=$('#xzqhDialogTree').tree('getChecked');
		if(nodex.length>1){
			$.messager.alert('提示','只能选择一个行政区划！');return;
		}
		var node = $('#xzqhDialogTree').tree('getChecked')[0];
		if(node&&node.id){
			if(xzqhDlg.callback&&typeof window[xzqhDlg.callback]=='function'){
				//node.id XZQH_34341235 -> 34341235
				//ntype -> XZQH
				var ntype = node.id.substring(0,node.id.indexOf('_'));
				node.id=node.id.substring(node.id.indexOf('_')+1);
				window[xzqhDlg.callback](node,ntype)
			}
			$('#xzqhDialog').dialog('close')
		}else{
			$.messager.alert('提示','请选择一个行政区划！');
		}
	}
</script>
