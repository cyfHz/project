<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false" title="" style="overflow: scroll;;padding: 5px;">
<div  style="width: 300px; height: 400px; padding:5px,10px,20px,20px" closed="true">
<%-- <input type="hidden" id="showXzjd" value="${showXzjd }">
<input type="hidden" id="showSqjcwh" value="${showSqjcwh }"> --%>
	<ul id="xzqhDialogTree" class="easyui-tree"></ul>
</div>
</div>
<div data-options="region:'south',border:false" title="" style="overflow: hidden;padding: 5px; text-align: right; ">
<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="comfirmData();">确认</a>
</div>
</div>
<script type="text/javascript">
	$(function() {
		$('#xzqhDialogTree').tree($.extend({
			url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:${showXzjd},showSqjcwh:${showSqjcwh}}),
			loadFilter: function(data) {
				if (!data || !data.rows || data.rows.length < 1) {
					return [];
				}
				var nodes = [];
				$(data.rows).each(function(i, row) {
					var node = {};
					node.id = row.NODETYPE+'_'+row.NODEID;//row.
					node.text = row.NODETEXT;
					node.state = row.NODETYPE == 'XZJD' ? "open":"closed";
					node.attributes=row.TYPES;
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
	function comfirmData(){
		var node =$("#xzqhDialogTree").tree("getChecked");
		if(node.length==0){
			alertMsg.warn("请选择数据！");
			return;
		}
		if(node.length>1){
			alertMsg.warn("请选择一个行政区域！");
			return;
		}
		var cid=node[0].id;
		var xtype=cid.substring(0,cid.indexOf('_'));
		var yType=node[0].attributes;//原始属于的行政区划类型  16-08-26加
		cid=cid.substring(cid.indexOf('_')+1);
		var text=node[0].text;
		//var strJSON = "{'id':'"+cid+"','text':'"+text+"'}";
		var strJSON = "{'id':'"+cid+"','text':'"+text+"','xtype':'"+xtype+"','yType':'"+yType+"'}";//
		returnBackDialog.close(100,strJSON);
	}
</script>
