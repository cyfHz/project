
$(function(){
	
	setTimeout(function(){
		var fw_fwbh=$("#fw_fwbh").val();
		var params={"jzwfjid":fw_fwbh};
		//历史人员信息
		$("#tt_lsry").tabs({
//			onSelect:function(title){
//				if(title=='常住人口信息'){
//					loadLsryCzrk(params);
//				}else if(title=='流动人口信息'){
//					loadLsryLdrk(params);
//				}else if(title=='境外人员信息'){
//					loadLsryJwry(params);
//				}
//			}
			onSelect:function(title,id){
				if(id=='0'){   //常住人口信息
					loadLsryCzrk(params);
				}else if(id=='1'){   //流动人口信息
					loadLsryLdrk(params);
				}else if(id=='2'){   //境外人员信息
					loadLsryJwry(params);
				}
			}
		});
		
		//房屋人员信息
		$("#tt_ryxx").tabs({
//			onSelect:function(title){
//				if(title=='房屋常住人口信息'){
//					loadJzwFwCzrkJbxx(params);
//				}else if(title=='房屋流动人口信息'){
//					loadJzwFwLdrkJbxx(params);
//				}else if(title=='房屋境外人员信息'){
//					loadJzwFwJwryJbxx(params);
//				}
//			}
			onSelect:function(title,id){
				if(id=='0'){   //房屋常住人口信息
					loadJzwFwCzrkJbxx(params);
				}else if(id=='1'){   //房屋流动人口信息
					loadJzwFwLdrkJbxx(params);
				}else if(id=='2'){   //房屋境外人员信息
					loadJzwFwJwryJbxx(params);
				}
			}
		});
		
		$("#tt").tabs({
			onSelect:function(title,id){
				if(id=='3'){ //单位信息
					loadJzwFwDwxx(params);
				}else if(id=='4'){  //历史单位信息
					loadJzwFwLsDwxx(params)
				}
			}
		})
		
	}, 1000);
	
	
})

//-----------------------------2016-0810----查询房屋内历史人员记录-------------------------------
function loadLsryCzrk(params){
	var loadurl=ctx+"/psam/fw/loadJzwFwLsCzrk";
	$("#lsry_czrk_detail").datagrid({
		title : "常住人口信息列表",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'RKID',//分页保留选中
		toolbar : "",
		columns : [ [ 
		 {field:'XM',title:'姓名',width:200},
		 {field : 'GMSFHM',title : '身份证号',width : 300},
		 {field : 'RKID',title : '操作',width : 160,formatter:formattersForLs}
		] ]
	});
}

function loadLsryLdrk(params){
	var loadurl=ctx+"/psam/fw/loadJzwFwLsLdrk";
	$("#lsry_ldrk_detail").datagrid({
		title : "流动人口信息列表",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'RKID',//分页保留选中
		toolbar : "",
		columns : [ [ 
		 {field:'XM',title:'姓名',width:200},
		 {field : 'SFZH',title : '身份证号',width : 300},
		 {field : 'RKID',title : '操作',width : 160,formatter:formattersForLs}
		] ]
	});
}

function loadLsryJwry(params){
	var loadurl=ctx+"/psam/fw/loadJzwFwLsJwry";
	$("#lsry_jwry_detail").datagrid({
		title : "境外人员信息列表",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'RKID',//分页保留选中
		toolbar : "",
		columns : [ [ 
		 {field:'YWM',title:'英文名',width:200},
		 {field : 'ZJHM',title : '证件号码',width : 300},
		 {field : 'RKID',title : '操作',width : 160,formatter:formattersForLs}
		] ]
	});
}

//-----------------------------2016-0810----查询房屋内历史人员记录-end------------------------------

//-----------------------------2016-0810----查询房屋内当前所住人员信息-------------------------------
function loadJzwFwCzrkJbxx(params){
	var loadurl=ctx+"/psam/fw/loadJzwFwCzrkJbxx";
	$("#fwry_czrk_detail").datagrid({
		title : "常住人口信息列表",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'RKID',//分页保留选中
		toolbar : "",
		columns : [ [ 
		 {field:'XM',title:'姓名',width:200},
		 {field : 'GMSFHM',title : '身份证号',width : 300},
		 {field : 'RKID',title : '操作',width : 160,formatter:formatters}
		] ]
	});
}

function loadJzwFwLdrkJbxx(params){
	var loadurl=ctx+"/psam/fw/loadJzwFwLdrkJbxx";
	$("#fwry_ldrk_detail").datagrid({
		title : "流动人口信息列表",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'RKID',//分页保留选中
		toolbar : "",
		columns : [ [ 
		 {field:'XM',title:'姓名',width:200},
		 {field : 'SFZH',title : '身份证号',width : 300},
		 {field : 'RKID',title : '操作',width : 160,formatter:formatters}
		] ]
	});
}

function loadJzwFwJwryJbxx(params){
	var loadurl=ctx+"/psam/fw/loadJzwFwJwryJbxx";
	$("#fwry_jwry_detail").datagrid({
		title : "境外人员信息列表",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'RKID',//分页保留选中
		toolbar : "",
		columns : [ [ 
		 {field:'YWM',title:'英文名',width:200},
		 {field : 'ZJHM',title : '证件号码',width : 300},
		 {field : 'RKID',title : '操作',width : 160,formatter:formatters}
//		 {field : 'rkid',title : '操作',width : 300,formatter:function(value,index,row){
//			 var a = "<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\" plain=\"true\" onclick=\"editCzrk('czrk','${czrklist.gmsfhm }')\">修改</a>"
//												+ "<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"deleteCzrk('czrk','${czrklist.gmsfhm }')\">删除</a>";
//										return a;
//		 }}
		] ]
	});
}

//-----------------------------2016-0810----查询房屋内当前所住人员信息-end------------------------------

function formattersForLs(value,row,index){
	var tab = $('#tt_lsry').tabs('getSelected');
	var index = $('#tt_lsry').tabs('getTabIndex',tab);
	var rklb;var sfzh;var rkid;var xm;
	if(index==0){
		rklb="czrk";
		sfzh=row.GMSFHM;
		rkid=row.RKID;
		 xm=row.XM;
	}else if(index==1){
		rklb="ldrk";
		sfzh=row.SFZH;
		rkid=row.LDID;
		 xm=row.XM;
	}else if(index==2){
		rklb="jwry";
		sfzh=row.ZJHM;
		rkid=row.JWRYID;
		xm=row.YWM;
	}


	var a="<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"loadRyxx('"+rklb+"','"+sfzh+"','"+rkid+"')\">查看</a>";
	return a;
}

function formatters(value,row,index){
	var tab = $('#tt_ryxx').tabs('getSelected');
	var index = $('#tt_ryxx').tabs('getTabIndex',tab);
	var rklb;var sfzh;var rkid;var xm;
//	alert(index);
	if(index==0){
		rklb="czrk";
		sfzh=row.GMSFHM;
		rkid=row.RKID;
		 xm=row.XM;
	}else if(index==1){
		rklb="ldrk";
		sfzh=row.SFZH;
		rkid=row.LDID;
		 xm=row.XM;
	}else if(index==2){
		rklb="jwry";
		sfzh=row.ZJHM;
		rkid=row.JWRYID;
		xm=row.YWM;
	}


	var a="<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"loadRyxx('"+rklb+"','"+sfzh+"','"+rkid+"')\">查看</a>"+
	         "&nbsp;&nbsp;"+
	       "<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-edit\" plain=\"true\" onclick=\"editRyxx('"+rklb+"','"+sfzh+"','"+rkid+"')\">修改</a>"+
		      "&nbsp;&nbsp;"+
	        "<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"deleteRyxx('"+rklb+"','"+sfzh+"','"+rkid+"','"+xm+"')\">删除</a>";
	return a;
}


function loadRyxx(rylb,sfzh,rkid){
	if (rkid == null || rkid == undefined || rkid == '' || rkid == "undefined") {
		alertMsg.warn("请选择一条要查看的数据！");
		return;
	}
	var url;var param;
	if (rylb == 'czrk') {
		url = ctx + "psam/sy/syCzrk/enterDetailSyCzrk";
		param = {"rkid" : rkid};
	} else if (rylb == 'ldrk') {
		url = ctx + "psam/sy/syLdrk/enterDetailSyLdrk";
		param = {"rkid" : rkid};
	} else if (rylb == 'jwry') {
		url = ctx + "psam/sy/syJwry/enterDetailSyJwry";
		param = {"jwryid" : rkid};
	}

	 
	var options = {
		title : "人口详细信息",
		width : 900,
		height : 500,
		url : url,
		params : param,
		onClosed : function() {
			loadFwRyxxData(rylb, 'reload');
		}
	};
	editDialog.open(options);
}

function editRyxx(rylb,sfzh,rkid){
	if (rkid == null || rkid == undefined || rkid == '' || rkid == "undefined") {
		alertMsg.warn("请选择一条要修改的数据！");
		return;
	}
	var url;var param;
	if (rylb == 'czrk') {
		url = ctx + "psam/sy/syCzrk/enterUpdateCzrkAccInfo";
		param = {"rkid" : rkid};
	} else if (rylb == 'ldrk') {
		url = ctx + "psam/sy/syLdrk/enterUpdateLdrkAccInfo";
		param = {"rkid" : rkid};
	} else if (rylb == 'jwry') {
		url = ctx + "psam/sy/syJwry/enterUpdateSyJwryAccInfo";
		param = {"jwryid" : rkid};
	}

	var options = {
		title : "人口信息修改",
		width : 900,
		height : 500,
		url : url,
		params : param,
		onClosed : function() {
			loadFwRyxxData(rylb, 'reload');
		}
	};
	editDialog.open(options);
}

   function deleteRyxx(rylb,sfzh,rkid,xm){
	   if(rkid==null || rkid==undefined || rkid=='' || rkid=="undefined"){
			alertMsg.warn("请选择要操作的数据！");
			return;
	   }
	   var ajaxUrl=ctx+"psam/fw/revokeFwRyxx";
//	   var ajaxUrl=ctx+"device/deleteFwRyxx";
//	   if(rylb=='czrk'){
//		   ajaxUrl ;
//		}else if(rylb=='ldrk'){
//		   ajaxUrl =ctx+"psam/sy/syfwCzrk/revokeFwRyxx";
//		}else if(rylb=='jwry'){
//		   ajaxUrl =ctx+"psam/sy/syfwCzrk/revokeFwRyxx";
//		}
	   var fw_fwbh=$("#fw_fwbh").val();
		alertMsg.confirm("确定"+xm+"已经不在该房间居住？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() {alertMsg.close();
					var param = {"rylb":rylb,"rkid":rkid,"sfzh":sfzh,"fjbm":fw_fwbh};
					console.log(param)
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						if(data.statusCode!=200){
					  		 alertMsg.error(data.message);
					  		 return;
						}
						alertMsg.info(data.message);
						 setTimeout(function(){
							 loadFwRyxxData(rylb,'reload');
							 reloadRkLb();
						 },2000);
						
					});
				}
			});
}



function loadFwRyxxData(rylb,reload){
	var dataGrid;
	if(rylb=='czrk'){
		dataGrid=$("#fwry_czrk_detail");
	}else if(rylb=='ldrk'){
		dataGrid=$("#fwry_ldrk_detail");
	}else if(rylb=='jwry'){
		dataGrid=$("#fwry_jwry_detail");
	}
	
	var queryParams = dataGrid.datagrid("options").queryParams;
//	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
	dataGrid.datagrid(reload);
	dataGrid.datagrid("clearSelections");
}



//---------------------------2016-08-15 单位信息--------------------

function loadJzwFwDwxx(params){
	var loadurl=ctx+"/psam/dwxx/loadJzwFwDwxxForFjid";
	$("#fwdw_dwxx_detail").datagrid({
		title : "单位信息",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'ZAGLDWBM',//分页保留选中
		toolbar : "",
		columns : [ [ 
//		 {field : 'zagldwbm',title : '编码',width : 300},
		 {field:'DWMC',title:'单位名称',width:240},
		 {field:'DJSJ',title:'登记时间',width:200},
		 {field : 'ZAGLDWBM',title : '操作',width : 180,formatter:formatterDwxx}
		] ]
	});
}


function loadJzwFwLsDwxx(params){
	var loadurl=ctx+"/psam/dwxx/loadJzwFwLsDwxxForFjid";
	$("#lsry_dwxx_detail").datagrid({
		title : "历史单位信息",
		nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取
		rownumbers : true, fitColumns : false,//滚动条
		animate : false, striped : true,//设置为true将交替显示行背景。
		singleSelect : true,//为true时只能选择单行
		pagination : true,rownumbers : true,//行数
		pageSize : 10,
		queryParams:params,
		url : loadurl,
		checkbox : false,
		idField : 'ZAGLDWBM',//分页保留选中
		toolbar : "",
		columns : [ [ 
//		 {field : 'ZAGLDWBM',title : '编码',width : 300},
		 {field:'DWMC',title:'单位名称',width:200},
		 {field:'DJSJ',title:'登记时间',width:180},
		 {field:'DELTIME',title:'撤销时间',width:180},
		 {field : 'ZAGLDWBM',title : '操作',width : 120,formatter:formatterLsDwxx}
		] ]
	});
}

function formatterDwxx(value,row,index){console.log(row)
	var dwid=row.ZAGLDWBM;
	var dwmc=row.DWMC;
	var a="<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"loadDwxx('"+dwid+"')\">查看</a>"+
              "&nbsp;&nbsp;"+
               "<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"deleteDwxx('"+dwid+"','"+dwmc+"')\">删除</a>";
return a;
}


function formatterLsDwxx(value,row,index){
	var dwid=row.ZAGLDWBM;
	var a="<a href=\"#\" class=\"easyui-linkbutton\" style=\"color: black;\" iconCls=\"icon-remove\" plain=\"true\" onclick=\"loadDwxx('"+dwid+"')\">查看</a>";
return a;
}


function loadDwxx(dwid){
	if (dwid == null || dwid == undefined || dwid == '' || dwid == "undefined") {
		alertMsg.warn("请选择一条要操作的数据！");
		return;
	}
	var param = {"dwid" : dwid};
	var url=ctx+"psam/dwxx/enterFwDwxxDetail"
	var options = {
		title : "单位详细信息",
		width : 900,
		height : 500,
		url : url,
		params : param,
		onClosed : function() {
			loadFwDwxxData('reload');
		}
	};
	editDialog.open(options);
}

function deleteDwxx(dwid,dwmc){
	 if(dwid==null || dwid==undefined || dwid=='' || dwid=="undefined"){
			alertMsg.warn("请选择要操作的数据！");
			return;
	   }
	   var ajaxUrl=ctx+"psam/dwxx/revokeFwDwxx";
	   
	   var fw_fwbh=$("#fw_fwbh").val();
		alertMsg.confirm("确定删除"+dwmc+"  单位？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() {alertMsg.close();
					var param = {"dwid":dwid,"fjbm":fw_fwbh};
					console.log(param)
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						if(data.statusCode!=200){
					  		 alertMsg.error(data.message);
					  		 return;
						}
						alertMsg.info(data.message);
						setTimeout(function(){
							loadFwDwxxData('reload');
							reloadRkLb();
						},2000);
					});
				}
			});
}


function loadFwDwxxData(reload){
	var queryParams = $("#fwdw_dwxx_detail").datagrid("options").queryParams;
//	KMEASYUtil.genQueryParams(queryParams, $("#searchform").form().serializeArray());
	$("#fwdw_dwxx_detail").datagrid(reload);
	$("#fwdw_dwxx_detail").datagrid("clearSelections");
}






