<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v = "urn:schemas-microsoft-com:vml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ include file="/static/meta/includeall.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
  <%-- <script src="http://<spring:message code="pgis.url"/>/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></script> --%>
<script src="http://<spring:message code="pgis.url"/>/TileMap/js/EzMapAPI.js" type="text/javascript" charset="GB2312"></script><!-- 0622改 -->
<!-- <SCRIPT src="http://10.48.1.227:9081/PGIS_S_TileMap/js_UTF-8/EzMapAPI.js" type="text/javascript"></SCRIPT> -->
<SCRIPT src="${pageContext.request.contextPath}/static/js/pgis/km.pgis.points.js" type="text/javascript"></SCRIPT>
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
<title>警务区管理</title>
</head>
<body class="easyui-layout" oncontextmenu="oncontextmenu();" >
  <t:DataDict code="JWQXZ" var="jwqxzDict"></t:DataDict>
    <div data-options="region:'west',title:'组织机构',split:true" style="width: 200px;">
        <ul id="zzjgTree" class="easyui-tree"></ul> <!-- -->
    </div>
    <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',border:false" style="height: 50px; background-color: #F4F4F4;overflow: hidden;">
                <form class="form-inline query-form form-horizontal" id="jwqSearchform">
                
                 <input type="hidden" id="xzqhdzbm" name="webParams[xzqhdzbm]"></input>
                  <input type="hidden" id="orgid" name="webParams[orgid]" ></input>
                    <div class="form-group">
                        <th>警务区名称:</th>
                        <input name="webParams['jwqmc']" type="text" class="easyui-textbox form-control"></input>
                    </div>
                    
                    <div class="form-group">
                        <th>警务区编号:</th>
                        <input name="webParams['jwqbh']" type="text"  value="" class="easyui-textbox form-control"></input>
                    </div>
                    <div class="form-group">
                       <km:widgetTag widgetRulevalue="jwq.jwqList">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="loaddata('load');;">查 询</a>
						</km:widgetTag>
                    </div>
                </form>
            </div>
            <div data-options="region:'center',border:false">
            <table id="jwqDataGrid" data-options="fit:true" ></table>
		    <div id="toolbarDiv" class="easyui-toolbar" style="padding:4px;height:auto">
		        <km:widgetTag widgetRulevalue="jwq.addJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="enterAddJwq()">添加</a>
 				</km:widgetTag>
 				 <km:widgetTag widgetRulevalue="jwq.updateJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openeditJwq()">修改</a>
 				</km:widgetTag>
 				 <km:widgetTag widgetRulevalue="jwq.cancelJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="cancelJwq()">注销</a>
 				</km:widgetTag>
 				<km:widgetTag widgetRulevalue="jwq.activateJwq">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="activateJwq()">启用</a>
 				</km:widgetTag>
 				<km:widgetTag widgetRulevalue="jwq.enterDetailJwq">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openDetailDialog()">详细信息</a>
				</km:widgetTag>
 				 <km:widgetTag widgetRulevalue="jwq.guihua">
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="initGuihuaDialog()">规划</a>
 				</km:widgetTag>
 				  <km:widgetTag widgetRulevalue="jwq.enterJyAssign"> 
		         	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="assignedJy()">警员分配</a>
 			 	</km:widgetTag> 
 			 		 <!-- <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="startJwqChaiFen()">拆分</a>
 			 		 <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="startJwqHeBing()">合并</a> -->
			</div>	
            </div>
        </div>
    </div>
    <div id="dlg_jwq_pgis"  class="easyui-dialog" style="width: 1000px; height: 500px; padding: 0px 0px" onClose="closeGuihuaDialog()" maximizable="true"  modal="true" closed="true" closable="true"  buttons="#dlg-buttons">
	    	<div id="dlg-buttons">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="startGuihua();">添加区域</a>&nbsp;
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="savePolygonRes()">提交</a>&nbsp;
		    	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:$('#dlg_jwq_pgis').dialog('close')">关闭 </a>&nbsp;
	    	<input type="hidden" id="drawPolygonRes" value=""></input>
			<input type="hidden"  id="drawPolygonRes_jwqid" value=""></input>
			</div>
    		 <div id="mymap" class="map" style="width:100%; height:100%;" ></div> <!---->
     </div>
<!-- ################################################################################################################################## -->   
<div id="dlg_chaiFenJwqDataGrid" class="easyui-dialog" closable="true" style="width: 450px; height: 550px; padding: 1px 1px" modal="true" closed="true" buttons="#dlg_chaiFenOrgDataGrid_buttons">
	<table id="chaiFenJwqDataGrid" data-options="fit:true" ></table>
</div>
<!-- ################################################################################################################################## --> 
<div id="dlg_heBingJwqDataGrid"  class="easyui-dialog"  closed="true" style="width: 750px; height: 450px; padding: 1px 1px" modal="true"  buttons="#dlg_heBingOrgDataGrid_buttons">
	<div style="width: 100%;height: 100%">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:true" title="要合并的警务区" style="width:300px;">
				<table id="fromHebingJwqDataGrid"  data-options="fit:true" ></table>
			</div>
			<div data-options="region:'center',title:'请选择要合并到的目标警务区'" style="width:450px;">
				<table id="toHebingJwqDataGrid"  data-options="fit:true" ></table>
			</div>
		</div>
	</div>
</div>
<div id="dlg_heBingJwqDataGrid_buttons">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_heBingJwqDataGrid').dialog('close')" style="width: 90px">关闭</a>
</div>
<!-- ################################################################################################################################## -->  
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jwq/km.jwq.data.init.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jwq/km.jwq.bianjie.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/km.pgis.tool.js"></script> 

 <script type="text/javascript">
 //function document.oncontextmenu(){alertMsg.warn("当前区域不支持右键编辑");}
//-----------------------------------------------------------------------------
	var pPolygons= new Array();//多个边界区域
	var polygonsInSamePcs=new Array();
	var jwqinfo;
	var removedIndexes=new Array();//存放已经被删除的
	var _MapApp=undefined;
	var selectedJwqId=undefined;
//--------------------------基本逻辑---------------------------------------------------
   $(function() {
    	loadZzjgTree();
    	initDataGrid();
   });

    </script>

</body>

</html>