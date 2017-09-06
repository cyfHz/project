<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<SCRIPT src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></SCRIPT>
<SCRIPT src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.bianjie.js" type="text/javascript"></SCRIPT>
<title></title>
<style type="text/css">
  v\:* {
        BEHAVIOR: url(#default#VML)
      }
    <!--[if gte IE 8]> 
    <script type="text/javascript">
        document.namespaces.add('vml', 'urn:schemas-microsoft-com:vml');
        document.createStyleSheet().cssText =
            'vml\\:fill, vml\\:path, vml\\:shape, vml\\:stroke' +
                '{ behavior:url(#default#VML); } ';
    </script>
    <![endif]-->
</style>
</head>
<body style="overflow: hidden;" class="easyui-layout">
<t:DataDict code="ORGNA_TYPE" var="orgTypeDict"></t:DataDict>
<t:DataDict code="ORGNA_PEROPERTY" var="orgPeropertyDict"></t:DataDict>
<t:DataDict code="ORGNA_LEVEL" var="orgLevelDict"></t:DataDict>
   <div data-options="region:'west',title:'组织机构',split:true" style="width: 250px;">
        <ul id="zzjgTree" class="easyui-tree"></ul> <!-- -->
    </div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" >
		   <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
			<form class="form-inline query-form" name="searchform" id="searchform">
					<div class="form-group">
					 <form class="form-inline query-form form-horizontal" id="searchform" >
		                  <input type="hidden" id="porgid" name="webParams[porgid]" ></input>
		                    <div class="form-group">
		                        <label>机构名称:</label>
		                        <input name="webParams['orgnaName']" type="text" class="easyui-textbox form-control"></input>
		                    </div>
		                    
		                    <div class="form-group">
		                        <label>机构编号:</label>
		                        <input name="webParams['orgnaCode']" type="text"  value="" class="easyui-textbox form-control"></input>
		                    </div>
		               
						<km:widgetTag widgetRulevalue="orgPgisArea.loadOrganizationGrid">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
						</km:widgetTag>
					</form>
					</div>		
		  	</form>
		</div>
	  <div data-options="region:'center',border:false">
		<table id="organizationDataGrid" style="margin-bottom: 0px;vertical-align: bottom;" data-options="fit:true" ></table>
		<div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		
			 <km:widgetTag widgetRulevalue="organization.addOrganization"></km:widgetTag>
		  		 <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddDialog()">添加</a>  
			
			 <km:widgetTag widgetRulevalue="orgPgisArea.saveOrgPgisArea">
		  		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openGuihuaDialog()">边界规划</a>
			</km:widgetTag>
			<%-- <km:widgetTag widgetRulevalue="organization.orgChaiFen"></km:widgetTag>
			 <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="startOrgChaiFen()">拆分</a>
			 <km:widgetTag widgetRulevalue="organization.orgHeBing"></km:widgetTag>
			  <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="startOrgHeBing()">合并</a> --%>
		</div>	
	  </div>	
	</div>
<!-- ################################################################################################################################## -->   	
<div id="dlg_pgis"  class="easyui-dialog" style="width: 1000px; height: 600px; padding: 0px 0px" modal="true" maximizable=true closable="false" closed="true" buttons="#dlg-buttons">
	    	<div id="dlg-buttons">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="startGuihua();">添加区域</a>&nbsp;
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="savePolygonRes()">提交</a>&nbsp;
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeGuihuaDialog()">关闭 </a>&nbsp;<!--closeGuihuaDialog()  -->
	    	<input type="hidden" id="drawPolygonRes" value=""></input>
			<input type="hidden"  id="org_id" value=""></input>
			</div>
    		<div id="mymap" class="map" style="width:100%; height:100%;" ></div>
</div>
<!-- ################################################################################################################################## -->   
<div id="dlg_chaiFenOrgDataGrid" class="easyui-dialog" closable="true" style="width: 450px; height: 550px; padding: 1px 1px" modal="true" closed="true" buttons="#dlg_chaiFenOrgDataGrid_buttons">
	<table id="chaiFenOrgDataGrid" data-options="fit:true" ></table>
</div>

<div id="dlg_chaiFenProcessOrgDataGrid" class="easyui-dialog" closable="false" style="width: 900px; height: 550px; padding: 1px 1px" modal="true" closed="true" buttons="#dlg_chaiFenOrgDataGrid_buttons">
<div style="width: 100%;height: 100%;">
	<div class="easyui-layout" data-options="fit:true" >
		<div data-options="region:'west',border:true,title:'要拆分到的组织机构列表'" style="width:280px;height:600px; ">
			<table id="chaiFenTargetOrgDataGrid"  data-options="fit:true" ></table>
		</div>
		<div data-options="region:'center',border:true,title:'被拆分的组织机构子机构列表'" style="width:340px;height:600px;">
		     <table id="fromOrgChildOrgDataGrid"  data-options="fit:true" ></table> 
		</div>
	</div>
</div>
</div>
<!-- ################################################################################################################################## --> 
  <div id="dlg_heBingOrgDataGrid"  class="easyui-dialog"  closed="true" style="width: 750px; height: 450px; padding: 1px 1px" modal="true"  buttons="#dlg_heBingOrgDataGrid_buttons">
	<div style="width: 100%;height: 100%">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:true" title="要合并的组织机构" style="width:230px;">
				<table id="tobeHebingOrgDataGrid"  data-options="fit:true" ></table>
			</div>
			<div data-options="region:'center',title:'请选择要合并到的目标组织机构'" style="width:500px;">
				<table id="hebingOrgDataGrid"  data-options="fit:true" ></table>
			</div>
		</div>
	</div>
</div>
<div id="dlg_heBingOrgDataGrid_buttons">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_heBingOrgDataGrid').dialog('close')" style="width: 90px">关闭</a>
</div>
<!-- ################################################################################################################################## -->   
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script>
//---------------------------------------------------------------------------
function startOrgChaiFen(){
	var rows = $("#organizationDataGrid").datagrid("getSelections");
	if(rows.length !=1 ){
		alertMsg.warn("请选择一条要拆分的数据");return;
	}
	var orgType=checkOrg(rows[0].ORGNA_CODE);
	if(orgType==null){
		alertMsg.warn("所选要拆分到的数据,不支持该类型组织机构");return;
	} 
	var fromOrgId=rows[0].ORGNA_ID;
	 $("#dlg_chaiFenOrgDataGrid").dialog("open").dialog("center").dialog("setTitle","选择要拆分到的组织机构");
	 $("#chaiFenOrgDataGrid").datagrid({
			rownumbers: true,
			url:"${ctx}/auth/organization/loadOrgChaiFenDataGrid?fromOrgId="+fromOrgId,
			idField:'ORGNA_ID',
			columns:[[
			    {field:'ORGNA_CODE',title:'机构编号',width:100},
			    {field:'ORGNA_NAME',title:'机构名称',width:240}
			]],
	        onBeforeLoad:function(){ 
	        	$(this).datagrid("clearSelections");
	        },loadFilter:function(data,parentId){
				return KMCORE.ajaxDoneForServerError(data);
			},toolbar:[{text:"确认",iconCls:'icon-ok',handler:function(){processChaifen();}}
			           ,'-',
			           {text:"取消",iconCls:'icon-cancel',handler:function(){$('#dlg_chaiFenOrgDataGrid').dialog('close');}
			}]
		});
	 $("#chaiFenOrgDataGrid").datagrid("clearSelections");
}

function processChaifen(){
	var toRows = $("#chaiFenOrgDataGrid").datagrid("getSelections");
	if(toRows.length <= 1){
		alertMsg.warn("请选择多余一条要拆分到的目标机构");return;
	}
	var fromRow = $("#organizationDataGrid").datagrid("getSelections");
	var fromId=fromRow[0].ORGNA_ID;
	var toIds = KMEASYUtil.rowsIdToArray(toRows, "ORGNA_ID");
	
	var checkChaiFenUrl="${ctx}/auth/organization/checkOrgChaiFen";
	var checkChaiFenParam={"fromId":fromId,"toIds":toIds};
	KMAJAX.ajaxTodo(checkChaiFenUrl, checkChaiFenParam, function(data) {
		if(data.statusCode!=200){
			 alertMsg.error(data.message);return;
		}
		//data.put("type", orgType);//拆分的类型
		//data.put("fromId", fromId);//拆分的哪一个组织机构
		//data.put("targetOrgs", targetOrgs);//拆分到那几个组织机构
		//data.put("sourceChildOrgs", sourceChildOrgs);//要被拆分的组织机构的子机构列表
		//start process
		var type=data.data.type;
		var fromId=data.data.fromId;
		$('#dlg_chaiFenOrgDataGrid').dialog('close');
		$("#dlg_chaiFenProcessOrgDataGrid").dialog("open").dialog("center").dialog("setTitle","拆分组织机构");
		//--------------------------加载拆分目标组织机构-----------------------
		 $("#chaiFenTargetOrgDataGrid").datagrid({//
			 	singleSelect:true,rownumbers: true, idField:'T_ORGNA_ID',
				columns:[[ {field:'T_ORGNA_NAME',title:'机构名称',width:240}]],
				onClickRow: function(rowIndex, rowData){$("#fromOrgChildOrgDataGrid").datagrid('loadData', data.data.sourceChildOrgs);}
		});
		 $("#chaiFenTargetOrgDataGrid").datagrid('loadData', data.data.targetOrgs); 
		//--------------------------加载拆分源组织机构--子机构---------------------
		
		var rowsxxx=$('#chaiFenTargetOrgDataGrid').datagrid("getData").rows;
		//console.log(rowsxxx);
		 $("#fromOrgChildOrgDataGrid").datagrid({//
				rownumbers: true, singleSelect:true,
						idField:'ORGNA_ID',
				columns:[[ {field:'ORGNA_CODE',title:'机构编号',width:140},
				           {field:'ORGNA_NAME',title:'机构名称',width:240},
				           {field:'ORGNA_PID',title:'所属新机构',width:120,
				        	   formatter:function(value,row){for(var i = 0;i<rowsxxx.length;i++){if(rowsxxx[i].T_ORGNA_ID==value){return rowsxxx[i].T_ORGNA_NAME;}}},
				        	   editor:{type:"combobox",options:{data:rowsxxx,valueField:"T_ORGNA_ID",textField:"T_ORGNA_NAME",required:true}}
				           }
				         ]],
		 toolbar:[{text:"提 交",iconCls:'icon-ok',handler:function(){doSubmitChaiFen(type,fromId);}},
		          '-',
		          {text:"取 消",iconCls:'icon-cancel',handler:function(){
								$('#dlg_chaiFenProcessOrgDataGrid').dialog('close'); 
								$("#organizationDataGrid").datagrid('reload');
								editIndex =undefined;
					}
				}],
			onClickRow: function(rowIndex, rowData){beginEdit(rowIndex);}
		});
		 $("#fromOrgChildOrgDataGrid").datagrid('loadData', data.data.sourceChildOrgs); 
	});
}

var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true
	}
	if ($('#fromOrgChildOrgDataGrid').datagrid('validateRow', editIndex)){
		var ed = $('#fromOrgChildOrgDataGrid').datagrid('getEditor', {index:editIndex,field:'ORGNA_PID'});
		var ORGNA_NAMEXX = $(ed.target).combobox('getText');
		$('#fromOrgChildOrgDataGrid').datagrid('getRows')[editIndex]['ORGNA_PID'] = ORGNA_NAMEXX;
		$('#fromOrgChildOrgDataGrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	}else{
		return false;
	}
}

function beginEdit(index) {
	if (editIndex != index) {
		if (endEditing()) {
			$('#fromOrgChildOrgDataGrid').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#fromOrgChildOrgDataGrid').datagrid('selectRow', editIndex);
		}
	}
}
function doSubmitChaiFen(type,fromId){
	if (!$('#fromOrgChildOrgDataGrid').datagrid('validateRow', editIndex)){
		$('#fromOrgChildOrgDataGrid').datagrid('endEdit', editIndex);
		return ;
	}
	$('#fromOrgChildOrgDataGrid').datagrid('endEdit', editIndex);
	$('#fromOrgChildOrgDataGrid').datagrid('acceptChanges');
	
	var fromOrgChildOrgs=$('#fromOrgChildOrgDataGrid').datagrid("getData").rows;
	var validated=true;
		$(fromOrgChildOrgs).each(function(){ 
			if(!PUBUtil.isStrHavaValue(this.ORGNA_PID)){validated=false;}
			//console.log(this.ORGNA_ID+" "+this.ORGNA_CODE+" "+this.ORGNA_NAME+" "+this.ORGNA_PID); 
		}); 
		if(!validated){
			alertMsg.warn("被拆分的组织机构子机构，存在没有父节点的数据，请选择");return ;
		}
	var targetOrgs=$('#chaiFenTargetOrgDataGrid').datagrid("getData").rows;
	//$("#organizationDataGrid").datagrid('reload');
	var url="${ctx}/auth/organization/processOrgChaiFen";
	var param={"type":type,"fromId":fromId,"fromOrgChildOrgs":fromOrgChildOrgs,"targetOrgs":targetOrgs};
	KMAJAX.ajaxTodoJson(url, JSON.stringify(param) , function(data) {
		if(data.statusCode!=200){
			 alertMsg.error(data.message);return;
		}
		alertMsg.info(data.message);
		$("#dlg_chaiFenProcessOrgDataGrid").dialog("close");
		$("#organizationDataGrid").datagrid('reload');
		editIndex =undefined;
	});
}
//---------------------------hebing-----------------------------------------------------------------------
	function startOrgHeBing() {
		var rows = $("#organizationDataGrid").datagrid("getSelections");
		if (rows.length < 2) {
			alertMsg.warn("请选择多于一条要合并的数据");
			return;
		}
		var idArray = KMEASYUtil.rowsIdToArray(rows, "ORGNA_ID");
		var ajaxUrl = "${ctx}/auth/organization/checkOrgHeBing";
		var param = {"oranIds" : idArray};
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}
			if (data.statusCode == 200) {
				$("#dlg_heBingOrgDataGrid").dialog("open").dialog("center").dialog("setTitle", "组织机构合并");
				$("#tobeHebingOrgDataGrid").datagrid({
					rownumbers : true,
					idField : 'ORGNA_ID',
					columns : [ [ {field : 'ORGNA_NAME',title : '机构名称',width : 190} ] ],
					onClickRow : function(rowIndex, rowData) {
						$("#tobeHebingOrgDataGrid").datagrid('unselectAll');
					}
				});
				$("#tobeHebingOrgDataGrid").datagrid('loadData', rows);
				//------------------------------------------------------------------
				$("#hebingOrgDataGrid").datagrid({
					singleSelect : true,rownumbers : true,fitColumns : true,
					idField : 'ORGNA_ID',
					columns : [ [ {field : 'ORGNA_CODE',title : '机构编号',width : 120},
								// {field:'PORGNA_ID',title:'PORGNA_ID',hidden:true},
								{field : 'ORGNA_NAME',title : '机构名称',width : 260}
								//{field:'ORGNA_JC',title:'结构简称',width:200},
					] ],
					onBeforeLoad : function() {$(this).datagrid("clearSelections");},
					loadFilter : function(data) {return KMCORE.ajaxDoneForServerError(data);},
					toolbar : [  {text : "确认",iconCls : 'icon-ok',handler : function() {processHebing();}},
					            '-', {text : "新建",iconCls : 'icon-add',handler : function() {openAddDialogx(ajaxUrl, param);}
					} ]
				});/* */
				$("#hebingOrgDataGrid").datagrid('loadData', data.data);
			}
		});
	}
	function openAddDialogx(reloadAjaxUrl, reloadParam) {
		var rows = $("#hebingOrgDataGrid").datagrid("getRows");
		var porgid = rows[0].PORGNA_ID;
		var url = "${ctx}/auth/organization/enterAddOrganization?porgid="+ porgid;
		var options = {
			title : "组织机构信息添加页面",width : 900,height : 400,url : url,params : {},
			onClosed : function() {
				KMAJAX.ajaxTodo(reloadAjaxUrl, reloadParam, function(data) {
					if (data.statusCode == 300) {
						alertMsg.error(data.message);
						return;
					}
					$("#hebingOrgDataGrid").datagrid('loadData', data.data);
				});
			}
		};
		editDialog.open(options);
	}
	function processHebing() {
		var fromRows = $("#tobeHebingOrgDataGrid").datagrid("getRows");
		var toRows = $("#hebingOrgDataGrid").datagrid("getSelections");
		var fromIdArray = KMEASYUtil.rowsIdToArray(fromRows, "ORGNA_ID");
		if (toRows.length != 1) {
			alertMsg.warn("请选择一条要合并到的数据");
			return;
		}
		var toId = toRows[0].ORGNA_ID;
		var param = { "fromIds" : fromIdArray,"toId" : toId};
		var ajaxUrl = "${ctx}/auth/organization/processOrgHeBing";
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			if (data.statusCode != 200) {
				alertMsg.info(data.message);
				return;
			}
			alertMsg.info(data.message);
			$("#dlg_heBingOrgDataGrid").dialog("close");
			$("#organizationDataGrid").datagrid('reload');
		});

	}

//--------------Add---------------------------------------------------------------------------- 
	function openAddDialog() {
		var url = "${ctx}/auth/organization/enterAddOrganization";
		var options = {
			title : "组织机构信息添加页面",
			width : 350,
			height : 400,
			url : url,
			params : {},
			onClosed : function() {
				loaddata('reload');
			}
		};
		editDialog.open(options);
	}
//-----------------------------------------------------------------------------------------------------
	var _MapApp;
	var pPolygons = new Array();//多区域
	var orgPgisArae;
	var removedIndexes = new Array();//存放已经被删除的

	$(function() {
		initOrgDataGrid();
		loadZzjgTree();
	});

	function openGuihuaDialog() {
		pPolygons.splice(0, pPolygons.length);//清空已边界数组
		removedIndexes.splice(0, removedIndexes.length);//清空已删除的id 数组
		pPolygons = new Array();
		removedIndexes = new Array();
		var rows = $("#organizationDataGrid").datagrid("getSelections");
		if (rows.length != 1) {
			alertMsg.warn("请选择一条要规划组织机构");
			return;
		}
		if (rows[0].ENABLED == '0') {
			alertMsg.warn("该组织机构已禁用！");
			return;
		}
		clearMap();//清除标记
		$("#drawPolygonRes").val("");
		$("#org_id").val();
		var orgid = rows[0].ORGNA_ID;
		$("#org_id").val(orgid);
		$("#dlg_pgis").dialog('open').dialog('center').dialog('setTitle',
				'区域规划------<font color="red">请注意顺时针取点 </font>');
		if (!_MapApp) {
			onLoad();
		}
		loadBianjieById();//加载选中组织区的 边界
	}

	//加载选中警组织区的 边界   
	function loadBianjieById() {
		var ajaxUrl = "${ctx}/psam/orgPgisArea/loadOrgPgisArea";
		var orgid = $("#org_id").val();
		var param = {
			"orgid" : orgid
		};
		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}
			orgPgisArae = data.data;
			/* if(!PUBUtil.isStrHavaValue(orgPgisArae)){
				alertMsg.warn("该组织机构没有划分边界");
				return;
			} */
			var mapArea = orgPgisArae.mapArea;
			var orgPgisAraeid = orgPgisArae.orgPgisAraeid;
			if (!mapArea || mapArea == "" || mapArea == undefined
					|| mapArea == "undefined" || mapArea == "null"
					|| mapArea == null) {
				alertMsg.warn("该组织机构没有划分边界");
				return;
			}
			if (PUBUtil.isStrHavaValue(mapArea)) {
				var zuobiao = new Array();
				zuobiao = mapArea.split(";");
				if (zuobiao && zuobiao.length > 0) {
					for (var i = 0; i < zuobiao.length; i++) {
						if (PUBUtil.isStrHavaValue(zuobiao[i])) {
							var points = constructOneBianjie(zuobiao[i]);
							var strPoints = points.join(",");
							pPolygons.push(new Polygon(strPoints, "black", 3,
									0.2, "red"));
							showOneBianjie(pPolygons[i], orgPgisArae, i);
						}

					}
				}
			}

		});
	}
	function showOneBianjie(pPolygon, orgPgisArae, index) {
		_MapApp.addOverlay(pPolygon);
		var mbr = pPolygon.getMBR();
		var pointxx = mbr.centerPoint();
		if (pointxx) {
			_MapApp.centerAndZoom(pointxx, 11);
		}
		var orgid = orgPgisArae.id;
		var orgname = orgPgisArae.orgname;
		var orgaddres = orgPgisArae.orgaddres;
		var orgcode = orgPgisArae.orgcode;
		var strMsg = " 组织机构名称 :" + orgname + "<br/>" + "组织机构编号：" + orgcode
				+ "<br/>" + "组织机构地址:" + orgaddres + "<br/>" + "<br/>"
				+ "<a href=\"#\" onclick=\"doDelete(" + index
				+ ");\" >删除</a>&nbsp;&nbsp;&nbsp;"
				+ "<a href=\"#\" onclick=\"doEdit(" + index + ");\" >编辑</a>";
		pPolygon.addListener("click", function() {
			pPolygon.openInfoWindowHtml(strMsg);
		});
		pPolygon.setExtendStatus(1, 100, 1, 2);
		pPolygon.setInterval(500);
		// pPolygon.enableEdit();
		pPolygon.disableEdit();

	}
	function doEdit(index) {
		(pPolygons[index]).closeInfoWindowHtml();
		(pPolygons[index]).enableEdit();
	}
	//删除一个边界区域,
	function doDelete(index) {
		(pPolygons[index]).closeInfoWindowHtml();
		_MapApp.removeOverlay(pPolygons[index], true);
		removedIndexes.push(index);//已经删除的在数组removedIndexes中记录
	}
	function closeGuihuaDialog() {
		clearMap();
// 		_MapApp.changeDragMode('pan',null,null,null);
		$("#drawPolygonRes").val("");
		$("#org_id").val();
		$("#dlg_pgis").dialog('close');

	}
	function clearMap() {
		if (_MapApp) {
			_MapApp.clear();
		}
	}
	function startGuihua() {
		_MapApp.changeDragMode('drawPolygon', drawPolygonRes, org_id,
				guihuaCallback);
	}
	//规划回调函数:
	//思路提示：  
	//1.把新规划的区域push到数组pPolygons中，
	//2. clearMap()把地图中标注全部删除，
	//3. 重绘数组pPolygons 中的所有边界区域
	//4.已经删除的在removedIndexes记录中的，在步骤3.中不再重绘。
	function guihuaCallback(res) {

		var zuobiao = res.split(",");
		if (!zuobiao || zuobiao.length == 6) {
			alertMsg.warn("未选择规划区域");
			return;
		}
		if (zuobiao && zuobiao.length > 0) {
			var points = constructOneBianjie(res);
			var strPoints = points.join(",");
			var polyon = new Polygon(strPoints, "black", 3, 0.2, "red");
			pPolygons.push(polyon);
			clearMap();
			for (var i = 0; i < pPolygons.length; i++) {
				var iscontinue = 0;
				for (var j = 0; j < removedIndexes.length; j++) {
					if (removedIndexes[j] == i) {
						iscontinue++;
					}
				}
				if (iscontinue > 0) {
					continue;
				}
				pPolygons[i].setFillColor("red");
				pPolygons[i].setFillOpacity(0.2);
				showOneBianjie(pPolygons[i], orgPgisArae, i);
				pPolygons[i].disableEdit();
			}
		}
	}

	function savePolygonRes() {
		var res = $("#drawPolygonRes").val();
		var orgid = $("#org_id").val();
		var resStr = "";
		//保存时，已经被删除的 多边形，不会被拼接在提交的数据中
		for (var i = 0; i < pPolygons.length; i++) {
			var iscontinue = 0;
			for (var j = 0; j < removedIndexes.length; j++) {
				if (removedIndexes[j] == i) {
					iscontinue++;
				}
			}
			if (iscontinue > 0) {
				continue;
			}
			var pp = pPolygons[i].getPoints();
			if (pp == "NaN" || pp == undefined) {
				continue;
			}
			resStr = resStr + pp + ";";
		}

		if (resStr == "" || resStr == "NAN,undefined") {
			var iscomfirm = alertMsg.confirm("该警务区没有边界数据，确认提交？", {
				cancelCall : function() {
					alertMsg.close();
					return false;
				},
				okCall : function() {
					//String orgid,String mapArea
					var ajaxUrl = "${ctx}/psam/orgPgisArea/saveOrgPgisArea";
					var param = {
						"orgid" : orgid,
						"mapArea" : resStr
					};
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						KMCORE.ajaxDone(data);
						//closeGuihuaDialog();
						//loaddata('reload');
					});
				}
			});
		} else {
			var ajaxUrl = "${ctx}/psam/orgPgisArea/saveOrgPgisArea";
			var param = {
				"orgid" : orgid,
				"mapArea" : resStr
			};
			KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
				KMCORE.ajaxDone(data);
				//closeGuihuaDialog();
				//loaddata('reload');
			});
		}

	}
	//--------------------------------------------------------------------
	function initOrgDataGrid() {
		$("#organizationDataGrid").datagrid({
			title : "组织机构列表",
			nowrap : true,
			rownumbers : true,
			fitColumns : false,//滚动条
			animate : true,
			striped : true,
			singleSelect : false,
			pagination : true,
			rownumbers : true,
			pageSize : 10,
			url : "${ctx}/psam/orgPgisArea/loadOrganizationGrid",
			checkbox : false,
			idField : 'ORGNA_ID',//分页保留选中
			toolbar : "#toolbarDiv",
			columns : [ [
			//{field:'ORGNA_ID',width:20,checkbox:true},
			{
				field : 'ORGNA_CODE',
				title : '机构编号',
				width : 100
			}, {
				field : 'ORGNA_NAME',
				title : '机构名称',
				width : 200,
				sortable : true
			}, {
				field : 'ORGNA_JC',
				title : '结构简称',
				width : 200,
				sortable : true
			}, {
				field : 'ENABLED',
				title : '是否启用',
				width : 80,
				formatter : function(value) {
					if ('1' == value) {
						return '<span class="tag-success">启用</span>';
					} else {
						return '<span class="tag-normal">禁用</span>';
					}
				}
			},
			//	{field:'ORGNA_TYPE',title:'机构类型',width:123,formatter:dictFormat('orgTypeDict')},
			//{field:'ORGNA_PROPERTY',title:'机构性质',width:100,formatter:dictFormat('orgPeropertyDict')},
			//{field:'ORGNA_LEVEL',title:'机构级别',width:123,sortable:true,formatter:dictFormat('orgLevelDict')},
			{
				field : 'ORGNA_ZIPCODE',
				title : '机构邮编',
				width : 123
			}, {
				field : 'ORGNA_TEL',
				title : '机构电话',
				width : 100
			}, {
				field : 'ORGNA_FAX',
				title : '机构传真',
				width : 123
			}, {
				field : 'ORGNA_EMAIL',
				title : '机构邮箱',
				width : 123
			}, {
				field : 'ORGNA_ADDRESS',
				title : '机构地址',
				width : 200
			},
			/* 	{field:'PORGNA_ID',title:'父组织机构ID',width:123},
				{field:'ENTORGNA_ID',title:'企业组织机构ID',width:100},
				{field:'AREA_ID',title:'应用领域ID',width:123,sortable:true,formatter:formatEnabled}, */
			/* 	{field:'ORGNA_VALIDITY_START',title:'有效时间',width:123,sortable:true},
				{field:'ORGNA_VALIDITY_END',title:'失效时间',width:123}, */
			/* 		{field:'CCH',title:'层次号',width:100},
				    {field:'CCBZ',title:'层次标志',width:123,sortable:true},
					{field:'HZBZ',title:'汇总标志',width:123,sortable:true},
					{field:'OPRATETIME',title:'操作时间',width:123},
					{field:'ORGNA_ORDER',title:'排列序号',width:123,sortable:true},
					{field:'ORGNA_HISCHILDNODE',title:'子节点',width:123,sortable:true}, */
			/* {field:'ORGNA_YXX',title:'有效性',width:123}, */
			{
				field : 'SFZSJ',
				title : '是否直属局',
				width : 100,
				formatter : function(value) {
					if ("1" == value) {
						return "是";
					} else {
						return "否";
					}
				}
			}
			/*    {field:'BZ0',title:'备注',width:123,sortable:true},
			{field:'BZ1',title:'备注1',width:123,sortable:true},
			{field:'BZ2',title:'备注2',width:123} */
			] ],
			onBeforeLoad : function() {
				$(this).datagrid("clearSelections");
			},
			loadFilter : function(data, parentId) {
				return KMCORE.ajaxDoneForServerError(data);
			}
		});
	}
	function loaddata(reload) {
		var queryParams = $("#organizationDataGrid").datagrid("options").queryParams;
		KMEASYUtil.genQueryParams(queryParams, $("#searchform").form()
				.serializeArray());
		$("#organizationDataGrid").datagrid(reload);
		$("#organizationDataGrid").datagrid("clearSelections");
	}

	function loadZzjgTree() {
		$('#zzjgTree').tree(
				{
					url : '${ctx}/auth/organization/loadOrganTree',
					cascadeCheck : false,
					loadFilter : function(data) {
						if (!data || !data.rows || data.rows.length < 1) {
							return [];
						}
						var nodes = [];
						$(data.rows).each(function(i, row) {
							var node = {};
							node.id = row.NODEID;
							node.text = row.NODETEXT;
							//node.state = row.NODETYPE == 'SQJCWH' ? "open":"closed";
							node.state = row.NODETYPE == '3' ? "open":"closed";
							//node.state = "closed"
							nodes.push(node);
						});
						return nodes;
					},
					onClick : function(node) {
						$('#porgid').val(node.id);
						loaddata('load');
					},
					onLoadSuccess : function() {
						$('#zzjgTree').tree('expand',
								$('#zzjgTree').tree('getRoot').target);
					}
				});
	}
	//--------------------------------------------------------------------
	function onLoad() {
		if (_compatIE()) {
			// 构造地图类
			_MapApp = new EzMap(document.getElementById("mymap"));
			// 显示地图左侧比例尺控制控件
			_MapApp.showMapControl();
			// 设置地图对中中心
			_MapApp.centerAndZoom(new Point(116.99655, 36.66345), 11);
			// 添加鹰眼

			var uOverview = new OverView();// 构造一个鹰眼对象
			uOverview.width = 200;// 设置鹰眼视窗的宽度
			uOverview.height = 200;// 设置鹰眼视窗的高度
			uOverview.minLevel = 5;// 设置鹰眼视窗中最小显示地图级别
			uOverview.maxLevel = 12;// 设置鹰眼视窗中最大显示地图级别
			_MapApp.addOverView(uOverview);// 添加鹰眼对象
		} else if (_MapApp == null) {
			var pEle = document.getElementById("mymap");
			pEle.innerHTML = "<p>目前EzMap地图引擎不支持你使用的浏览器，我们当前支持如下浏览器类型:</p><ul><li><a href='http://www.microsoft.com/windows/ie/downloads/default.asp'>IE</a> 5.5+ (Windows)</li>";
		}
	}

	function checkOrg(orgCode) {
		if (orgCode == "null" || orgCode == null || orgCode == ""
				|| orgCode == undefined || orgCode == "undefined") {
			return null;
		}
		var index = ("" + orgCode).length - 1;
		var x = orgCode.charAt(index);
		while (index >= 0 && x == '0') {
			index--;
			x = orgCode.charAt(index);
		}
		switch (index + 1) {
		case 2:
			return "st";
		case 3:
		case 4:
			return "sj";
		case 5:
		case 6:
			return "fj";
		case 7:
		case 8:
		case 9:
			return "pcs";
		case 10:
		case 11:
		case 12:
			//return "jwq";
			return null;
		default:
			return null;
		}
	}
</script>
</body>

</html>