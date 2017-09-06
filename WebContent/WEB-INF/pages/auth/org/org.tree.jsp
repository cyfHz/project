<%@ page contentType="text/html; charset=utf-8"%>
<ul id="xzqhTree" class="easyui-tree"></ul>
<script type="text/javascript">
	var showXzjd =  '${param.showXzjd}';
	var showSqjcwh  = '${param.showSqjcwh}';
	var checkbox = '${param.checkbox}';
	$(function() {
		$('#xzqhTree').tree($.extend({
			url: '${pageContext.request.contextPath}/psam/xzqh/tree?'+$.param({showXzjd:showXzjd,showSqjcwh:showSqjcwh}),
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
			checkbox:checkbox=='true' ? true:false,
			onLoadSuccess: function() {
				$('#xzqhTree').tree('expand',$('#xzqhTree').tree('getRoot').target);
			}
		},typeof treeOpts === "undefined" ? {}:treeOpts));
	});
	
</script>
