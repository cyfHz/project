<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false" title="" style="overflow: scroll;;padding: 0px;">
<div  style="width: 300px; height: 400px; padding:5px" closed="true">
	<ul id="zzjgTree" class="easyui-tree"></ul>
</div>
</div>
<div data-options="region:'south',border:false" title="" style="overflow: hidden;padding: 10px; text-align: right; ">
<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="comfirmData();">确认</a>
</div>
</div>
<script type="text/javascript">
	$(function() {
		loadZzjgTree();
	});
    function loadZzjgTree(){
    	$('#zzjgTree').tree({
			url: '${ctx}/auth/organization/loadOrganTree',
			cascadeCheck:false,
			checkbox:true,
			loadFilter: function(data) {
				if (!data || !data.rows || data.rows.length < 1) {
					return [];
				}
				var nodes = [];
				$(data.rows).each(function(i, row) {
					var node = {};
					node.id = row.NODEID;
					node.text = row.NODETEXT;
					node.state = row.ORGNA_TYPE == '3' ? "open":"closed";
					nodes.push(node);
				});
				return nodes;
			},onLoadSuccess: function() {
               $('#zzjgTree').tree('expand',$('#zzjgTree').tree('getRoot').target);
            }
		});
    }
	function comfirmData(){
		var node =$("#zzjgTree").tree("getChecked");
		if(node.length==0){
			alertMsg.warn("请选择数据！");
			return;
		}
		if(node.length>1){
			alertMsg.warn("请选择一个组织机构！");
			return;
		}
		var cid=node[0].id;
		cid=cid.substring(cid.indexOf('_')+1);
		var text=node[0].text;
		var strJSON = "{'id':'"+cid+"','text':'"+text+"'}";
		returnBackDialog.close(100,strJSON);
	}
</script>
