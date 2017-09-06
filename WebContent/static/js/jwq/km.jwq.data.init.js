	function enterAddJwq(){
		var url=ctx+"psam/jwq/enterAddJwq";
		var options={title:"警务区信息添加页面",width:800,height:400, url:url,params:{},onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
	}

	function openeditJwq(){
		 var rows = $("#jwqDataGrid").datagrid("getSelections");
		 if(rows.length != 1){
			alertMsg.warn("请选择一条要编辑的警务区");return;
		 }
		 if(rows[0].SFYX=='0'){
			 alertMsg.warn("该警务区信息已注销，不允许修改！");return;
		 }
	    var url=ctx+"psam/jwq/enterUpdateJwq";
	    var param={jwqid:rows[0].JWQID};
		var options={title:"警务区信息编辑",width:800,height:400, url:url,params:param,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
   }
	function openDetailDialog(){
		var rows = $("#jwqDataGrid").datagrid("getSelections");
		if(rows.length != 1){
			alertMsg.warn("请选择一个要查看的警务区");return;
		}
		var url=ctx+"psam/jwq/enterDetailJwq";
		var param={jwqid:rows[0].JWQID};
		var options={title:"警务区详细信息",width:900,height:400, url:url,params:param,onClosed:function(){loaddata('reload');}};
		editDialog.open(options);
	}
   function cancelJwq(){
	   var rows = $("#jwqDataGrid").datagrid("getSelections");
	   if(rows.length!=1){
		   alertMsg.warn("请选择一条要注销的警务区");
		   return;
	   }
	   if(rows[0].SFYX=='0'){
			 alertMsg.warn("该警务区信息已注销！");
			 return;
		 }
	   var idArray =KMEASYUtil.rowsIdToArray(rows,"JWQID");
	   alertMsg.confirm("确定要注销该警务区？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() {
					alertMsg.close();
					var ajaxUrl =  ctx+"psam/jwq/cancelJwq";
					var param = { "id" : rows[0].JWQID};
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						KMCORE.ajaxDone(data);
						loaddata('reload');
					});
				}
		});
   }
	  function activateJwq(flag){
	  var rows = $("#jwqDataGrid").datagrid("getSelections");
	   if(rows.length!=1){
		   alertMsg.warn("请选择一条要启用的警务区");
		   return;
	   }
	   if(rows[0].SFYX=='1'){
			 alertMsg.warn("该警务区正在使用！");
			 return;
		 }
	   var idArray =KMEASYUtil.rowsIdToArray(rows,"JWQID");
	   alertMsg.confirm("确定要启用该警务区？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() {
					alertMsg.close();
					var ajaxUrl = ctx+"psam/jwq/activateJwq";
					var param = { "id" : rows[0].JWQID};
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						KMCORE.ajaxDone(data);
						loaddata('reload');
					});
				}
		});
   }
    function assignedJy(){
    	var rows=$("#jwqDataGrid").datagrid("getSelections");
    	if(rows.length==0){
    		alertMsg.warn("请选择要分配警员的警务区");
    		return;
    	}
    	if(rows.length>1){
    		alertMsg.warn("请选择一条数据");
    		return;
    	}
    	 if(rows[0].SFYX=='0'){
			 alertMsg.warn("该警务区信息已注销，不允许分配人员！");return;
		 }
    	var url=ctx+"psam/jwqyJygx/enterJyAssign";
  	    var param={jwqid:rows[0].JWQID};
  		var options={title:"警务区人员分配",width:1200,height:630, url:url,params:param,onClosed:function(){loaddata('reload');}};
  		editDialog.open(options);	
    }

//----------------------------------------------------  
function initDataGrid(loadurl){
    	  treeDrid = $("#jwqDataGrid").datagrid({
				title:"警务区管理",
				nowrap: true,//设置为true，当数据长度超出列宽时将会自动截取
				rownumbers: true, fitColumns: true,//滚动条
				animate:true, collapsible:false,//显示可折叠按钮
				striped:true, singleSelect: true,//为true时只能选择单行
				pagination : true, rownumbers : true,//行数
				pageSize: 10,
				url:ctx+"psam/jwq/jwqList",
				checkbox:false,
				idField:'JWQID',//分页保留选中
				toolbar:"#toolbarDiv",
				columns:[[
					{field:'JWQBH',title:'警务区编号',width:130,sortable:true},
					{field:'JWQMC',title:'警务区名称',width:200,sortable:true},
					{field:'ORGNA_NAME',title:'所属派出所',width:150},
					{field:'SFYX',title:'是否有效 ',width:100,formatter:formatEnable},
					{field:'JWQJJ',title:'警务区简介',width:100},
					{field:'JWQMJ',title:'警务区面积',width:100},
					{field:'NCGQSL',title:'农村管区数量',width:100},
					{field:'JWQXZ',title:'警务区性质 ',width:100,formatter:dictFormat('jwqxzDict')},
				
					{field:'QYRQ',title:'启用日期 ',width:100},
					{field:'SXRQ',title:'失效日期 ',width:100}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				}
			});
    }
	function loaddata(reload) { 
	  	var queryParams =$("#jwqDataGrid").datagrid("options").queryParams;
	  	KMEASYUtil.genQueryParams(queryParams, $("#jwqSearchform").form().serializeArray());
		$("#jwqDataGrid").datagrid(reload);
		$("#jwqDataGrid").datagrid("clearSelections");
	}
    //------------------------------
    function loadZzjgTree(){
    	$('#zzjgTree').tree({
			url: ctx+"auth/organization/loadOrganTree",
			cascadeCheck:false,
			loadFilter: function(data) {
				if (!data || !data.rows || data.rows.length < 1) {
					return [];
				}
				var nodes = [];
				$(data.rows).each(function(i, row) {
					var node = {};
					node.id = row.NODEID;
					node.text = row.NODETEXT;
					//node.state = "closed";
					node.state = row.NODETYPE == '3' ? "open":"closed";
					nodes.push(node);
				});
				return nodes;
			} , onClick: function(node) {
               $('#orgid').val(node.id);
               loaddata('load');
            },onLoadSuccess: function() {
               $('#zzjgTree').tree('expand',$('#zzjgTree').tree('getRoot').target);
            }
		});
    }