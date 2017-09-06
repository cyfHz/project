 function startJwqChaiFen(){
		var rows = $("#jwqDataGrid").datagrid("getSelections");
		if(rows.length !=1 ){
			alertMsg.warn("请选择一条要拆分的数据");return;
		}
		var fromJwqId=rows[0].JWQID;
		 $("#dlg_chaiFenJwqDataGrid").dialog("open").dialog("center").dialog("setTitle","选择要拆分到的目标警务区");
		 $("#chaiFenJwqDataGrid").datagrid({
				rownumbers: true,//j.JWQID, j.PCSID,j.JWQBH,j.JWQMC,j.SFYX
				url:"${ctx}/psam/jwq/loadJwqChaiFenDataGrid?fromJwqId="+fromJwqId,
				idField:'JWQID',
				columns:[[
				    {field:'JWQBH',title:'警务区编号',width:100},
				    {field:'JWQMC',title:'警务区名称',width:240}
				]],
		        onBeforeLoad:function(){ 
		        	$(this).datagrid("clearSelections");
		        },loadFilter:function(data,parentId){
					return KMCORE.ajaxDoneForServerError(data);
				},toolbar:[{text:"确认",iconCls:'icon-ok',handler:function(){processChaifen();}}
				           ,'-',
				           {text:"取消",iconCls:'icon-cancel',handler:function(){$('#dlg_chaiFenJwqDataGrid').dialog('close');}
				}]
			});
		 $("#chaiFenOrgDataGrid").datagrid("clearSelections");
	}
 
 function processChaifen(){
		var toRows = $("#chaiFenJwqDataGrid").datagrid("getSelections");
		if(toRows.length <= 1){
			alertMsg.warn("请选择多余一条要拆分到的目标警务区");return;
		}
		var fromRow = $("#jwqDataGrid").datagrid("getSelections");
		var fromId=fromRow[0].JWQID;
		var toIds = KMEASYUtil.rowsIdToArray(toRows, "JWQID");
		
		var url="${ctx}/psam/jwq/processJwqChaiFen";
		var param={"fromId":fromId,"toIds":toIds};
		
		KMAJAX.ajaxTodo(url, param, function(data) {
			if(data.statusCode!=200){
				 alertMsg.error(data.message);return;
			}
			alertMsg.info(data.message);
			loaddata('reload');
		});
	}
 //-------------------JwqHeBing------------------------------------------
function startJwqHeBing() {
		var rows = $("#jwqDataGrid").datagrid("getSelections");
		if (rows.length < 2) {
			alertMsg.warn("请选择多于一条要合并的数据");
			return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "JWQID");
		var ajaxUrl = "${ctx}/psam/jwq/checkJwqHebing";
		var param = {"jwqIds" : idArray};
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}
			if (data.statusCode == 200) {
				$("#dlg_heBingJwqDataGrid").dialog("open").dialog("center").dialog("setTitle", "警务区合并");
				$("#fromHebingJwqDataGrid").datagrid({
					//j.JWQID, j.PCSID,j.JWQBH,j.JWQMC,j.SFYX 
					rownumbers : true,
					idField : 'JWQID',
					columns : [ [ {field : 'JWQBH',title : '警务区编码',width : 100}, {field : 'JWQMC',title : '警务区名称',width : 160} ] ],
					onClickRow : function(rowIndex, rowData) {$("#fromHebingJwqDataGrid").datagrid('unselectAll');}
				});
				$("#fromHebingJwqDataGrid").datagrid('loadData', rows);
				//------------------------------------------------------------------
				$("#toHebingJwqDataGrid").datagrid({
					singleSelect : true,rownumbers : true,fitColumns : true,
					idField : 'JWQID',
					columns : [ [ {field : 'JWQBH',title : '警务区编号',width : 120},
								// {field:'PCSID',title:'派出所ID',hidden:true},
								{field : 'JWQMC',title : '警务区名称',width : 260}
								//{field:'SFYX',title:'是否有效',width:200},
					] ],
					onBeforeLoad : function() {$(this).datagrid("clearSelections");},
					loadFilter : function(data) {return KMCORE.ajaxDoneForServerError(data);},
					toolbar : [  {text : "确认",iconCls : 'icon-ok',handler : function() {processHebing();}}
					             ,'-',
					             {text:"取消",iconCls:'icon-cancel',handler:function(){$('#dlg_heBingJwqDataGrid').dialog('close');}}
					]
				});/* */
				$("#toHebingJwqDataGrid").datagrid('loadData', data.data);
			}
		});
	}
function processHebing() {
	var fromRows = $("#fromHebingJwqDataGrid").datagrid("getRows");
	var toRows = $("#toHebingJwqDataGrid").datagrid("getSelections");
	var fromIdArray = KMEASYUtil.rowsIdToArray(fromRows, "JWQID");
	if (toRows.length != 1) {
		alertMsg.warn("请选择一条要合并到的警务区数据");
		return;
	}
	var toId = toRows[0].JWQID;
	var param = { "fromIds" : fromIdArray,"toId" : toId};
	var ajaxUrl = "${ctx}/psam/jwq/processJwqHebing";
	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		if (data.statusCode != 200) {
			alertMsg.info(data.message);
			return;
		}
		alertMsg.info(data.message);
		$("#dlg_heBingJwqDataGrid").dialog("close");
		$("#jwqDataGrid").datagrid('reload');
	});

}