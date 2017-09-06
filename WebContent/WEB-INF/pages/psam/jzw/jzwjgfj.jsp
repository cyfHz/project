<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title></title> 
<%@ include file="/static/meta/easyui2.jsp" %> 
<%@ include file="/static/meta/easyuipub.jsp" %>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ include file="/static/meta/meta.jsp" %> 
<style type="text/css">
.bordera {
	position: absolute;
	border: 0px none;
	text-align: center;
	line-height: 70px;
	white-space: nowrap;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	overflow: hidden;
	background-image: url("${pageContext.request.contextPath}/static/jg/back120701.png");
	width: 120px;
	height: 70px;
	background-size: cover;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='${pageContext.request.contextPath}/static/jg/back120701.png',
		sizingMethod='scale');
		-moz-background-size: 100% 100%;
	background-size: 100% 100%;
}

.borderb {
	position: absolute;
	width: 120px;
	height: 70px;
	border: 0px none;
	text-align: center;
	line-height: 70px;
	white-space: nowrap;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	overflow: hidden;
	background-image: url("${pageContext.request.contextPath}/static/jg/back120702.png");
	background-size: cover;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='${pageContext.request.contextPath}/static/jg/back120702.png',
		sizingMethod='scale');
	-moz-background-size: 100% 100%;
	background-size: 100% 100%;
}

.tdx {
	background-image: url("${pageContext.request.contextPath}/static/jg/back2.png");
	background-size: cover;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader( src='${pageContext.request.contextPath}/static/jg/back2.png',
		sizingMethod='scale');
	-moz-background-size: 100% 100%;
	background-size: 100% 100%;
}

.lc {
	color: white;
	font-weight: bold;
	position: absolute;
	width: 100px;
	height: 100px;
	border: 0px none;
	text-align: center;
	line-height: 70px;
	background-image: url("${pageContext.request.contextPath}/static/jg/lcback.png");
	background-size: cover;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(
    src='${pageContext.request.contextPath}/static/jg/lcback.png', sizingMethod='scale');
	-moz-background-size: 100% 100%;
	background-size: 100% 100%;
}
.dy {
	color: white;
	font-weight: bold;
	position: absolute;
	width: 100px;
	height: 100px;
	border: 0px none;
	text-align: center;
	line-height: 30px;
	background: url("${pageContext.request.contextPath}/static/jg/dyback.png") repeat-x;
}
.subdiv {
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/subdiv.png") repeat-x;/**/
}
.subdiv-lou{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/subdiv.png") repeat-x;
}
.subdiv-czrk{
	 position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-czrk.png") repeat-x;
}
.subdiv-ldrk{
	 position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-ldrk.png") repeat-x;
}
.subdiv-jwry{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-jwry.png") repeat-x;
}

.subdiv-zdrk{
    position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-zdrk.png") repeat-x;
}

.subdiv-jxcs{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-jxcs.png") repeat-x;
}
.subdiv-dwxx{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-dwxx.png") repeat-x;
}
.subdiv-jxcs{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-jxcs.png") repeat-x;
}
.subdiv-ylfwcs{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-ylcs.png") repeat-x;
}
.subdiv-qtggcs{
	position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-qtggcs.png") repeat-x;
}
.subdiv-zddw{
    position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-zddw.png") repeat-x;
}
.subdiv-tzhy{
    position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-tzhy.png") repeat-x;
}
.subdiv-bafwgs{
    position: absolute;
	width: 20px;
	height: 20px;
	background: url("${pageContext.request.contextPath}/static/jg/fj-mark-bafwgs.png") repeat-x;
}
</style>
<script src="${pageContext.request.contextPath}/static/jg/jg.js" type="text/javascript"></script>
</head>
<body>
<input id="jzwjgId" type="hidden" value="${jzwjgId}"></input>
<input id="jzwId" type="hidden" value="${jzwId}"></input>
<div id="container" style="position: absolute;left:50px;top:100px;border:none;border-width: 0px"></div>
<t:DataDict code="FWYT" var="fwytdic"></t:DataDict>
<!-- #################################dlg_hebing######################################################### -->
<div id="dlg_hebing" class="easyui-dialog" style="width: 350px; height: 260px; padding: 10px 10px" closed="true" modal="true" buttons="#dlg_hebing_buttons">
			<div class="form-group">
				<div class="form-group">
				<label>合并后房间序号:</label> 
				<input name="fjxh" id="fjxh" type="text" onblur="genHbfjmc();"></input>
			</div><br/>
				<label>合并后房间名称:</label> 
				<input name="fjmc" id="fjmc" type="text" ></input>
			</div>
		
			<div id="dlg_hebing_buttons">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="processHeBing()" style="width: 90px">确认</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_hebing').dialog('close')" style="width: 90px">关闭</a>
		</div>
		<script>
function genHbfjmc(){
	var v=$("#fjxh").val();
	if(!v){alertMsg.error("请输入房间序号");return;}
	
	 if(isNaN(v)){
		alertMsg.error("房间序号必须为数字");return;
	 }
	 if(v.indexOf(".")>-1){
		alertMsg.error("房间序号必须为整数");return;
	 }
		
	$("#fjmc").val(v+"室");
}
</script>
</div>
<!-- ##############################dlg_chaifen############################################################ -->
<div id="dlg_chaifen" class="easyui-dialog" style="width: 450px; height: 460px; padding: 10px 10px" closed="true"  modal="true" buttons="#dlg_chaifen_buttons">
			<form id="fm_chaifen" class="form-horizontal" method="post" >
				<div class="form-group">
					<label>拆分方向:</label> 
					<select name="chaiFenFangxiang" id="chaiFenFangxiang" style="width: 170px">
					 <option value="0" selected = "selected">横向</option>
					 <option value="1">纵向</option>
					</select>
				</div><br/>
				<div class="form-group">
					<label>拆分个数:</label> 
					<input name="chaiFenCount" id="chaiFenCount" type="text" class="easyui-validatebox numberbox" precision="0" ></input>
					<input type="button" value="生成" onclick="appendChaiFenFjInfo();" ></input>
				</div>
				<br/>
				<div id="chaifenFormContent">
				</div>
				<div id="dlg_chaifen_buttons">
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="processChaiFen()" style="width: 90px">确认</a>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_chaifen').dialog('close')" style="width: 90px">关闭</a>
			</div>
		</form>
</div>
<!-- ##############################dlg_addfj############################################################ -->
<div id="dlg_addfj" class="easyui-dialog" style="width: 350px; height: 180px; padding: 10px 10px" modal="true" closed="true" buttons="#dlg_addfj_buttons">
			<form id="fm_addfj" class="form-horizontal" method="post" >
			<div class="form-group">
				<label>房间序号:</label> 
				<input name="addfjxh" id="addfjxh" type="text" class="easyui-validatebox form-control" onblur="genAddfjmc();"></input>
			</div><br/>
			<div class="form-group">
				<label>房间名称:</label> 
				<input name="addfjmc" id="addfjmc" type="text" class="easyui-validatebox form-control"></input>
			</div>
			<div id="dlg_addfj_buttons">
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="processAdd()" style="width: 90px">确认</a>
				<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_addfj').dialog('close')" style="width: 90px">关闭</a>
		</div>
		</form>
<script>
function genAddfjmc(){
	var v=$("#addfjxh").val();
	if(!v){alertMsg.error("请输入房间序号");return;}
	
	if(isNaN(v)){
		alertMsg.error("房间序号必须为数字");return;
	}
	if(v.indexOf(".")>-1){
		alertMsg.error("房间序号必须为整数");return;
	}
	
	$("#addfjmc").val(v+"室");
}
</script>
</div>
<!-- ##############################dlg_add_upOrDown############################################################ -->
<div id="dlg_add_upOrDown" class="easyui-dialog" style="width: 350px; height: 150px; padding: 10px 10px" modal="true" closed="true" buttons="#dlg_add_upOrDown_buttons">
	<div class="form-group">
		<label>添加房间:</label> 
		<select name="isUpDown" id="isUpDown" style="width: 170px">
		 	<option value="true"  selected = "selected">上</option>
		 	<option value="false">下</option>
		</select>
	</div>
	<div id="dlg_add_upOrDown_buttons">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="validateAddFj()" style="width: 90px">确认</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_add_upOrDown').dialog('close')" style="width: 90px">关闭</a>
	</div>
</div>

<!-- ##############################dlg_fwInfo############################################################ -->
<div id="dlg_fwInfo" ></div>

<!-- ############################## rk采集dlg_rk_type ############################################################ -->
<div id="dlg_rk_type" class="easyui-dialog" style="width: 450px; height: 150px; padding: 10px 10px" modal="true" closed="true" buttons="#dlg_rk_type_buttons">
	<div class="form-group">
		<label>人员类别:</label> 
		<select name="rkType" id="rkType" style="width: 170px">
		 	<option value="czrk">常住人口</option>
		 	<option value="ldrk">流动人口</option>
		 	<option value="jwry">境外人员</option>
		</select>
	</div>
</div>
<div id="dlg_rk_type_buttons">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="processRkAccquisition()" style="width: 90px">确认</a>
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_rk_type').dialog('close')" style="width: 90px">关闭</a>
</div>
<!-- #######################################人口信息管理############################################# -->
<div id="dlg_rkInfo_type" class="easyui-dialog" style="width: 450px; height: 150px; padding: 10px 10px" modal="true" closed="true" buttons="#dlg_rkInfo_type_buttons">
	<div class="form-group">
		<label>人员类别:</label> 
		<select name="rkInfoType" id="rkInfoType" style="width: 170px">
		 	<option value="czrk">常住人口</option>
		 	<option value="ldrk">流动人口</option>
		 	<option value="jwry">境外人员</option>
		</select>
	</div>
</div>
<div id="dlg_rkInfo_type_buttons">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="processLoadRKInfo()" style="width: 90px">确认</a>
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_rkInfo_type').dialog('close')" style="width: 90px">关闭</a>
</div>
<!-- ############################## rk采集 ############################################################ -->	
<!-- <div id="dlg_ldrk_accqui" class="easyui-dialog" style="width: 900px; height: 520px; padding: 10px 10px" closed="true" modal="true" > -->

<!-- </div> -->
<!-- <div id="dlg_czrk_accqui" class="easyui-dialog" style="width: 900px; height: 540px; padding: 10px 10px"  onClose="asdasd();" closed="true" modal="true" ></div>
 -->
<!--  <div id="dlg_jwry_accqui" class="easyui-dialog" style="width: 900px; height: 520px; padding: 10px 10px" closed="true" modal="true" ></div> -->
<div id="dlg_rk_info" class="easyui-dialog" style="width: 960px; height: 520px; padding: 10px 10px" closed="true" modal="true" ></div> 

<div id="dlg_DwxxInfo" ></div>
<script type="text/javascript">

var cssMarkArray=new Array();
$(function(){
	loadJzwJg();
	initCssMarkArray();
// 	setTimeout(function(){
// 		reloadRkLb();
// 		}, 1000);
});
function initCssMarkArray(){
	cssMarkArray.push("subdiv");
	cssMarkArray.push("subdiv-lou");
	cssMarkArray.push("subdiv-czrk");
	cssMarkArray.push("subdiv-ldrk");
	cssMarkArray.push("subdiv-jwry");
	cssMarkArray.push("subdiv-dwxx");
	cssMarkArray.push("subdiv-jxcs");
	cssMarkArray.push("subdiv-ylfwcs");
	cssMarkArray.push("subdiv-qtggcs");
	cssMarkArray.push("subdiv-zddw");
	cssMarkArray.push("subdiv-tzhy");
	cssMarkArray.push("subdiv-bafwgs");
	cssMarkArray.push("subdiv-zdrk");
}
//-------------------------------init ----S---------------------------------------------------
function loadJzwJg(){
	var jzwjgId=$("#jzwjgId").val();
	var url="${ctx}/psam/jzwjg/loadJzwjg";
	var params={"jzwjgId":jzwjgId};
	KMAJAX.ajaxTodo(url, params, function(data){
		if(data.statusCode!=200){
  			 alertMsg.error(data.message);
  			 return;
			}
			var jzwjgdata=data.data.jzwjg;
			var dys=jzwjgdata.dys;
			var lcs=jzwjgdata.lcs;
			var dxlcs=jzwjgdata.dxcs;
			var mdyms=jzwjgdata.mdyms;
			var jzwfjdata=data.data.jzwfj;
			var hh=0;
			var ww=0;
			var lcIndex=0;
			var dyIndex=0;
			var index=0;
				$.each(jzwfjdata,function(index,item){
					var showinfo=item.SHOWINFO;
					if(showinfo==null){
						return;
					}
					var id=item.JZWFJID;
					var infoArray=new Array();
					infoArray=showinfo.split(",");
					var left=infoArray[0];
					var top=infoArray[1];
					var wit=infoArray[2];
					var hei=infoArray[3];
					var name=item.FJMC;
					hh+=hei;
					ww+=wit;
					var cssx="";
					if(((top)/(golbleHeight)%2==0)){
						cssx="bordera";
					}else{
						cssx="borderb";
					}
					$("#container").append("<div id=\""+id+"\" class=\""+cssx+"\" style=\"left: "+left+"px;top:"+top+"px;width: "+wit+"px; height:"+hei+"px; \">"+name+"</div>");
				});
				init();
				addDanyuan(lcs+dxlcs,dys,mdyms);
				loadJzwlc();
			
	});
}

function loadJzwlc(){
	var url="${ctx}/psam/jzwlc/loadJzwlcByJzwJgid";
	var jzwjgId=$("#jzwjgId").val();
	var params={"jzwjgId":jzwjgId};
	KMAJAX.ajaxTodo(url, params, function(data){
		var i=0;
		$.each(data, function(index, item) {
			 //item.LCMC, item.LCXH,item.JZWLCID
			var left=-32;
			var top=golbleHeight*i;
			 i++;
			var wit=30;
			var hei=golbleHeight;
			var bordera="lc";
			var name = item.lcxh;
			$("#container").append("<div class=\""+bordera+"\" style=\"left: "+left+"px;top:"+top+"px;width: "+wit+"px; height:"+hei+"px; \">"+name+"</div>");
		});
	});
}
//--------------------检索结果 渲染---------S----------------------------------------------------------
function reloadRkLb(){
	var urlArray=new Array();
//	var nodes=window.parent.getElementById('tt').tree('getChecked');
	var nodes=$('#tt',window.parent.document).tree('getChecked');
	//console.log(nodes)
	if(nodes!=null&&nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			var textNode=nodes[i].target;//.attr("NamedNodeMap")[1].nodeValue;
			var domNode=$(textNode);
			var nodeid=domNode.attr("node-id");
			urlArray.push(nodeid);
		}
		rkSearch(urlArray);
	}
}
function rkSearch(urlArray){
	clearFjMark();
	var jzwjgId=$("#jzwjgId").val();
	var jzwId=$("#jzwId").val();
	//--------------------
	var params={"jzwjgId":jzwjgId,"jzwId":jzwId};
	for(var i=0;i<urlArray.length;i++){
		var leftx=parseInt(5+(20*0)+2);
		var topx=parseInt(6+(20*2)+2);
		var url="${ctx}"+urlArray[i];
		KMAJAX.ajaxTodo(url, params, function(data){
				if (data.statusCode != 200) {
					alertMsg.error(data.message);
					return;
				}
				var datax=data.data;
			
				var type=datax.resType;
				var fjidArray=new Array();
				 fjidArray=datax.fjidList;
				 var bottomx=parseInt(10); 
// 				 var markdiv="subdiv";
				 if("czrk"==type){
					 markdiv="subdiv-czrk";
					 leftx=parseInt((20*0)+2);
					 topx=parseInt(6+(20*2)+2);
				 }else if("ldrk"==type){
					 markdiv="subdiv-ldrk";
					 leftx=parseInt((20*1)+2);
					 topx=parseInt(6+(20*2)+2);
				 }else if("jwry"==type){
					 markdiv="subdiv-jwry";
					 leftx=parseInt((20*2)+2);
					 topx=parseInt(6+(20*2)+2);
				 }
				 
				 else if("zdrk"==type){
					 markdiv="subdiv-zdrk";
					 leftx=parseInt((20*3)+2);
					 topx=parseInt(6+(20*2)+2);
				 }
				 
				 else if("dwxx"==type){//单位基本信息
					 markdiv="subdiv-dwxx";
					 leftx=parseInt((20*5)-2);
					 topx=parseInt(6+(20*2)+2);
				 }else if("jxcs"==type){//九小场所
					 markdiv="subdiv-jxcs";
					 leftx=parseInt((20*0)+2);
					 topx=parseInt((20*0)+2);
				 }else if("ylfwcs"==type){//娱乐服务场所
					 markdiv="subdiv-ylfwcs";
					 leftx=parseInt((20*1)+2);
					 topx=parseInt((20*0)+2);
				 }else if("qtggcs"==type){//其他公共场所
					 markdiv="subdiv-qtggcs";
					 leftx=parseInt((20*2)+2);
					 topx=parseInt((20*0)+2);
				 }else if("zddw"==type){//重点单位
					 markdiv="subdiv-zddw";
					 leftx=parseInt((20*3)+2);
					 topx=parseInt((20*0)+2);
				 }else if("tzhy"==type){//特种行业
					 markdiv="subdiv-tzhy";
					 leftx=parseInt((20*4)+2);
					 topx=parseInt((20*0)+2);
				 }else if("bafwgs"==type){//保安服务公司
					 markdiv="subdiv-bafwgs";
					 leftx=parseInt((20*5)+2);
					 topx=parseInt((20*0)+2);
				 }
				 
				  $.each(fjidArray,function(index,item){
					  $("#"+item+"").append("<div class=\""+markdiv+"\" style=\"left: "+leftx+"px;bottom:"+bottomx+"px;top: "+topx+"px;\"></div>");
				  });
			});
		}
	
}

function clearFjMark(){
	$("#container div").each(function(index,element){
		for(var i=0;i<cssMarkArray.length;i++){
			if($(this).hasClass(cssMarkArray[i])){
				$(this).remove();
			}
		}
	});
}
//-------------------------------init ----E---------------------------------------------------
//房屋信息采集
 function loadFwjbxxAcc(){
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 var jzwfjid=targetIdArray[0];
	 var url1="${ctx}/psam/fw/validateLoadFwjbxxAccInfo";
	 var params={"jzwfjid":jzwfjid};
	 KMAJAX.ajaxTodo(url1, params, function(data){
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}else{
				 var url="${ctx}/psam/fw/loadFwjbxxAccInfo?jzwfjid="+jzwfjid;
				  $("#dlg_fwInfo").dialog({
					 title:"房屋信息",
					 href:url,
					 width:820,height:580,
				 	 modal:true
				 });
				 $("#dlg_fwInfo").dialog("open"); 
			}
		});
} 
//----------------人口采集--------------------------------------
function startRkAccquisition(){
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 $("#dlg_rk_type").dialog("open").dialog("center").dialog("setTitle","人口类别选择");
}

function processRkAccquisition(){
	
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 var persontype = $("#rkType").val();

	 var jzwfjid=targetIdArray[0];
	 var url;
	 if("czrk"==persontype){
		 url1="${ctx}/psam/sy/syfwCzrk/valiateSyFwCzrkAccInfo";
			var params={"jzwfjid":jzwfjid};
			KMAJAX.ajaxTodo(url1, params, function(data){
				if (data.statusCode != 200) {
					alertMsg.error(data.message);
					return;
				}else{
					 url="${ctx}/psam/sy/syfwCzrk/enterSyFwCzrkAccInfo?jzwfjid="+jzwfjid;
					 $('<div></div>').dialog({
			             id : 'dlg_czrk_accqui',title : '常住人口采集',
			             width : 780, height : 600,
			             closed : false, cache : false,modal : true,  href : url, 
			             onClose : function() {$(this).dialog('destroy');reloadRkLb(); }
					 });
				}
			});
		
		 ///  $("#dlg_czrk_accqui").dialog('destroy');
		//url="${ctx}/psam/sy/syfwCzrk/enterSyFwCzrkAccInfo?jzwfjid="+jzwfjid;
		// $("#dlg_czrk_accqui").dialog({ title:"常住人口采集", href:url, modal:true });
		// $("#dlg_czrk_accqui").dialog("open");
	 }else if("ldrk"==persontype){
	    url1="${ctx}/psam/sy/syfwLdrk/valiateSyFwLdrkAccInfo";
		var params={"jzwfjid":jzwfjid};
		KMAJAX.ajaxTodo(url1, params, function(data){
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}else{
				url="${ctx}/psam/sy/syfwLdrk/enterSyFwLdrkAccInfo?jzwfjid="+jzwfjid;
				 $('<div></div>').dialog({
		             id : 'dlg_ldrk_accqui',title : '流动人口采集',
		             width : 780, height : 600,
		             closed : false, cache : false,modal : true,  href : url, 
		             onClose : function() {$(this).dialog('destroy');reloadRkLb(); }
				 });
			}
		});
	
			
		 
	   /*  url="${ctx}/psam/sy/syfwLdrk/enterSyFwLdrkAccInfo?jzwfjid="+jzwfjid; 
		 $("#dlg_ldrk_accqui").dialog({ title:"流动人口采集", href:url, modal:true });
		 $("#dlg_ldrk_accqui").dialog("open"); */
	 }else if("jwry"==persontype){
		 url1="${ctx}/psam/sy/syFwJwry/valiateSyFwJwryAccInfo";
		 var params={"jzwfjid":jzwfjid};
		 
		 KMAJAX.ajaxTodo(url1, params, function(data){
				if (data.statusCode != 200) {
					alertMsg.error(data.message);
					return;
				}else{
					url="${ctx}/psam/sy/syFwJwry/enterSyFwJwryAccInfo?jzwfjid="+jzwfjid;
					 $('<div></div>').dialog({
			             id : 'dlg_jwry_accqui',title : '境外人员采集',
			             width : 780, height : 600,
			             closed : false, cache : false,modal : true,  href : url, 
			             onClose : function() {$(this).dialog('destroy'); reloadRkLb();}
					 });
				}
			});
		 
		 
		 /* url="${ctx}/psam/sy/syFwJwry/enterSyFwJwryAccInfo?jzwfjid="+jzwfjid; 
		 $("#dlg_jwry_accqui").dialog({ title:"境外人员采集", href:url, modal:true });
		 $("#dlg_jwry_accqui").dialog("open"); */
	 }
	 reloadRkLb();
	 $("#dlg_rk_type").dialog("close");
}
//开始加载人口信息
function startLoadRkInfo(){
	targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 $("#dlg_rkInfo_type").dialog("open").dialog("center").dialog("setTitle","人口类别选择");
}
//处理人口信息加载
function processLoadRKInfo(){
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 var persontype = $("#rkInfoType").val();
	 var jzwfjid=targetIdArray[0];
	 var url;
	 if("czrk"==persontype){
		url="${ctx}/psam/sy/syCzrk/entersyCzrkInfoPage?jzwfjid="+jzwfjid;
		 $("#dlg_rk_info").dialog({ title:"常住人口信息", href:url, modal:true });
		 $("#dlg_rk_info").dialog("open");
	 }else if("ldrk"==persontype){
	    url="${ctx}/psam/sy/syLdrk/enterSyLdrkInfoPage?jzwfjid="+jzwfjid; 
		 $("#dlg_rk_info").dialog({ title:"流动人口信息", href:url, modal:true });
		 $("#dlg_rk_info").dialog("open");
	 }else if("jwry"==persontype){
		 url="${ctx}/psam/sy/syJwry/enterSyjwryInfoPage?jzwfjid="+jzwfjid; 
		 $("#dlg_rk_info").dialog({ title:"境外人员信息", href:url, modal:true });
		 $("#dlg_rk_info").dialog("open");
	 }
}
//--------------------删除房间----------S----------------------------------------------------------
/**
 * 删除房间
 */
function startDeleteFj(){
	var targetIdArray=getSelectedDivIds();
	if(!targetIdArray||targetIdArray.length!=1){
		alertMsg.error("请选择一个房间");return;
	}
	var jzwfjId=targetIdArray[0];
	var url="${ctx}/psam/jzwfj/checkCanDelete";
	var parm={"jzwfjId":jzwfjId};
	KMAJAX.ajaxTodo(url, parm, function(data){
		if(data.statusCode!=200){
	  		 alertMsg.error(data.message);
	  		 return;
		}
		alertMsg.confirm("确定要删除该房间吗？",{
			cancelCall:function(){
				alertMsg.close();
			},
			okCall:function(){
				alertMsg.close();
				//如果房屋有人员信息，给出提示6-24 改
				var urlSel="${ctx}/psam/jzwfj/selcetRkForCfHb";
				var param={"jzwfjIds":targetIdArray};
				KMAJAX.ajaxTodo(urlSel, param, function(data){
					if(data.statusCode==200){
						var message= data.message;
						alertMsg.confirm(message+",确定要删除该房间吗？",{
							cancelCall:function(){
								alertMsg.close();
							},
							okCall:function(){
								alertMsg.close();
								delete_fw(jzwfjId);
								setTimeout(function(){
								      reloadRkLb();
									}, 1000);
							}
						});
					}else{
						delete_fw(jzwfjId);
						setTimeout(function(){
						      reloadRkLb();
							}, 1000);
					}
				});
			}
		});
	/* 	alertMsg.confirm("确定要删除该房间吗？",{
			cancelCall:function(){
				alertMsg.close();
			},
			okCall:function(){
				alertMsg.close();
				var url_delete="${ctx}/psam/jzwfj/deleteJzwfj";
				var param={"jzwfjId":jzwfjId};
				KMAJAX.ajaxTodo(url_delete, param, function(data){
					if(data.statusCode!=200){
				  		 alertMsg.error(data.message);
				  		 return;
					}
					$("#container").empty();
					loadJzwJg();
					loadJzwlc();
				});
			}
			
		}); */
	});
}
function delete_fw(jzwfjId){
	var url_delete="${ctx}/psam/jzwfj/deleteJzwfj";
	var param={"jzwfjId":jzwfjId};
	KMAJAX.ajaxTodo(url_delete, param, function(data){
		if(data.statusCode!=200){
	  		 alertMsg.error(data.message);
	  		 return;
		}
		$("#container").empty();
		loadJzwJg();
		loadJzwlc();
	});
}
//--------------------添加房间----------S----------------------------------------------------------
/**
 * 开始添加房间
 */
function startAddFj(){
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个参照房间");return;
	 }
	$("#dlg_add_upOrDown").dialog("open").dialog("center").dialog("setTitle","房间方位选择");
}
/**
 * 添加校验
 */
function validateAddFj(){
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 var isUpDown = $("#isUpDown").val();
	 var url="${ctx}/psam/jzwjg/checkCanAdd";
	 var jzwjgId=$("#jzwjgId").val();
	 var parm={"refJzwFjId":targetIdArray[0] ,"jzwId":jzwjgId,"isUpDown":isUpDown};
	 KMAJAX.ajaxTodo(url, parm, function(data){
			if(data.statusCode!=200){
	  			 alertMsg.error(data.message);
	  			 return;
			}
			$("#dlg_add_upOrDown").dialog("close");
			$("#fm_addfj").form("clear");
			$("#dlg_addfj").dialog("open").dialog("center").dialog("setTitle","房间添加");
			
	 });
}


/**
 * 添加房间
 */
function processAdd(){
	 targetIdArray=getSelectedDivIds();
	 var isUpDown = $("#isUpDown").val();
	 var jzwjgId=$("#jzwjgId").val();
	 var addfjmc=$("#addfjmc").val();
	 var addfjxh=$("#addfjxh").val();
	 
	//房间序号数字校验
	 if(isNaN(addfjxh)){
		alertMsg.error("房间序号必须为数字");return;
	 }
	 if(addfjxh.indexOf(".")>-1){
		alertMsg.error("房间序号必须为整数");return;
	 }
	 
	 var url="${ctx}/psam/jzwfj/addJzwFj";
	 var jzwjgId=$("#jzwjgId").val();
	 var parm={"refJzwFjId":targetIdArray[0] ,"jzwjgId":jzwjgId,"addfjmc":addfjmc,"addfjxh":addfjxh,"isUpDown":isUpDown};
	 KMAJAX.ajaxTodo(url, parm, function(data){
			if(data.statusCode!=200){
	  			 alertMsg.error(data.message);
	  			 return;
			}
			 $("#dlg_addfj").dialog("close");
			 $("#container").empty();
			 loadJzwJg();
			 loadJzwlc();
			 setTimeout(function(){
			      reloadRkLb();
				}, 1000);
	 });
	 
}
//--------------------添加房间----------E----------------------------------------------------------

//--------------房间合并---------S----------------------------------------------------------------
var basekeyPrefix="basekeyPrefix";
function startHeBing(){
	var check=checkHeBing();
	if(!check){
		return;
	}
	$("#dlg_hebing").dialog("open").dialog("center").dialog("setTitle","房间合并信息");
	 var hbhFjxh=$("#fjxh").val("");
	 var hbhFjmc=$("#fjmc").val("");
}

function processHeBing(){
	 var hbhFjxh=$("#fjxh").val();
	 var hbhFjmc=$("#fjmc").val();
	 if(PUBUtil.isStrNull(hbhFjxh)||PUBUtil.isStrNull(hbhFjxh)){
		 alertMsg.warn("请填写合并信息");return;
		 //alertMsg.warn("请填写合并信息");
		 return;
	 }
	 //房间序号数字校验
	 if(isNaN(hbhFjxh)){
		alertMsg.error("房间序号必须为数字");return;
	 }
	 if(hbhFjxh.indexOf(".")>-1){
		alertMsg.error("房间序号必须为整数");return;
	 }
	 var targetIdArray=new Array();
	 targetIdArray=getSelectedDivIds();
	 var poInfo=getAfterHeBingDivInfo();
	 var showinfo=""+poInfo.left+","+poInfo.top+","+poInfo.width+","+poInfo.height+"";
	 var jzwfjView={"oldJzwfjIds":targetIdArray,"jzwfjInfoList":[{"key":basekeyPrefix,"fjxh":hbhFjxh,"fjmc":hbhFjmc,"showInfo":showinfo}]};
	 var url="${pageContext.request.contextPath}/psam/jzwfj/heBing";
	 //如果房屋有人员信息，给出提示6-24 改
		var urlSel="${ctx}/psam/jzwfj/selcetRkForCfHb";
		var param={"jzwfjIds":targetIdArray};
		KMAJAX.ajaxTodo(urlSel, param, function(data){
			if(data.statusCode==200){
				var message= data.message;
				alertMsg.confirm(message+",确定要合并该房间吗？",{
					cancelCall:function(){
						alertMsg.close();
						$("#dlg_hebing").dialog("close");
					},
					okCall:function(){
						alertMsg.close();
						fw_hb(url,jzwfjView,targetIdArray);
						setTimeout(function(){
						      reloadRkLb();
							}, 1000);
						$("#dlg_hebing").dialog("close");
					}
				});
			}else{
				fw_hb(url,jzwfjView,targetIdArray);
				setTimeout(function(){
				      reloadRkLb();
					}, 1000);
			}
		}); 
	 
	 /* var url="${pageContext.request.contextPath}/psam/jzwfj/heBing";
	 KMAJAX.ajaxTodoJson(url, JSON.stringify(jzwfjView), function(data){
		 if(data.statusCode!=200){
			 alertMsg.error(data.message);
			 return ;
		 }else{
			 remveDivByIds(targetIdArray);
			 $("#container").empty();
			 loadJzwJg();
			 $("#dlg_hebing").dialog("close");
			 var newFjData=data.data;
			 var newFjId=newFjData.basekeyPrefix;
			 var newFjxh=hbhFjxh;
			 var newFjmc=hbhFjmc;
			 var divv=$("<div id=\""+newFjId+"\" class=\"bordera\" style=\"left: "+poInfo.left+"px;top:"+poInfo.top+"px;width: "+poInfo.width+"px; height:"+poInfo.height+"px; \">"+newFjmc+"</div>");
			 $("#container").append(divv);
			 addClickClass(divv);
			 remveDivByIds(targetIdArray); 
		 }
	 });  */
}
//执行房屋合并06-24
function fw_hb(url,jzwfjView,targetIdArray){
	KMAJAX.ajaxTodoJson(url, JSON.stringify(jzwfjView), function(data){
		 if(data.statusCode!=200){
			 alertMsg.error(data.message);
			 return ;
		 }else{
			 remveDivByIds(targetIdArray);
			 $("#container").empty();
			 loadJzwJg();
			 $("#dlg_hebing").dialog("close");
			/*  var newFjData=data.data;
			 var newFjId=newFjData.basekeyPrefix;
			 var newFjxh=hbhFjxh;
			 var newFjmc=hbhFjmc;
			 var divv=$("<div id=\""+newFjId+"\" class=\"bordera\" style=\"left: "+poInfo.left+"px;top:"+poInfo.top+"px;width: "+poInfo.width+"px; height:"+poInfo.height+"px; \">"+newFjmc+"</div>");
			 $("#container").append(divv);
			 addClickClass(divv);
			 remveDivByIds(targetIdArray); */
		 }
	 });
}
//--------------房间合并---------E----------------------------------------------------------------

//--------------房间拆分---------S----------------------------------------------------------------
function startChaiFen(){
	var check=checkChaiFen();
	if(!check){
		return;
	}
	$("#dlg_chaifen").dialog("open").dialog("center").dialog("setTitle","房间拆分信息");
	 $("#chaifenFormContent").empty();
	$("#fm_chaifen").form("clear");
	
}
function processChaiFen(){
	var dir=$("#chaiFenFangxiang").val();
	if(!dir){
		 alertMsg.error("请选择拆分方向");
		 return;
	}
	var count=$("#chaiFenCount").val();
	if(!count||count<2){
		 alertMsg.error("请填写拆分个数");return;
	}
	var ex = /^\d+$/;
	if (!ex.test(count)) {
		alertMsg.error("请正确填写拆分个数"); return;
	}
	
	var fjmcArray=new Array();
	var fjxhArray=new Array();
	for(var i=0;i<count;i++){
		var fjmc=$("#c_fjmc"+i+"").val();
		var fjxh=$("#c_fjxh"+i+"").val();
		if(!fjmc){
			 alertMsg.error("房间名称不能为空");return;
		}
		if(!fjxh){
			 alertMsg.error("房间序号不能为空");return;
		}
		if(isNaN(fjxh)){
			alertMsg.error("房间序号必须为数字");return;
		}
		if(fjxh.indexOf(".")>-1){
			alertMsg.error("房间序号必须为整数");return;
		}
		fjmcArray.push(fjmc);
		fjxhArray.push(fjxh);
	}
	 var targetIdArray=new Array();
	 targetIdArray=getSelectedDivIds();
	    var Tleft = $("#"+targetIdArray[0]+"").position().left;
	    var Ttop = $("#"+targetIdArray[0]+"").position().top;
	    var Twidth =$("#"+targetIdArray[0]+"").width();
	    var Theight = $("#"+targetIdArray[0]+"").height();
	 var posArray=new Array();
	 if(dir==0){//
		 posArray=caculateW(Tleft,Ttop,Twidth,Theight,count);
	 }else{
		 posArray=caculateH(Tleft,Ttop,Twidth,Theight,count);
	 }
	 var jzwfjInfoList=new Array();
	 for(var i=0;i<posArray.length;i++){
			showinfo=""+posArray[i].left+","+posArray[i].top+","+posArray[i].width+","+posArray[i].height+"";
			jzwfjInfoList[i]={"key":basekeyPrefix+"-"+i,"fjxh":fjxhArray[i],"fjmc":fjmcArray[i],"showInfo":showinfo};
	 } 
	 var url="${pageContext.request.contextPath}/psam/jzwfj/chaiFen";
	 var jzwfjView={"oldJzwfjIds":targetIdArray,"jzwfjInfoList":jzwfjInfoList};
	 
	//如果房屋有人员信息，给出提示6-24 改
		var urlSel="${ctx}/psam/jzwfj/selcetRkForCfHb";
		var param={"jzwfjIds":targetIdArray};
		KMAJAX.ajaxTodo(urlSel, param, function(data){
			if(data.statusCode==200){
				var message= data.message;
				alertMsg.confirm(message+",确定要拆分该房间吗？",{
					cancelCall:function(){
						alertMsg.close();
						$("#dlg_chaifen").dialog("close");
						$("#chaifenFormContent").empty();
					},
					okCall:function(){
						alertMsg.close();
						fw_cf(url,jzwfjView,targetIdArray);
						setTimeout(function(){
						      reloadRkLb();
							}, 1000);
						$("#dlg_chaifen").dialog("close");
						$("#chaifenFormContent").empty();
					}
				});
			}else{
				fw_cf(url,jzwfjView,targetIdArray);
				setTimeout(function(){
				      reloadRkLb();
					}, 1000);
				$("#dlg_chaifen").dialog("close");
				$("#chaifenFormContent").empty();
			}
		});
	 
	 /* var url="${pageContext.request.contextPath}/psam/jzwfj/chaiFen";
	 var jzwfjView={"oldJzwfjIds":targetIdArray,"jzwfjInfoList":jzwfjInfoList};
	 KMAJAX.ajaxTodoJson(url,  JSON.stringify(jzwfjView), function(data){
		 if(data.statusCode!=200){
			 alertMsg.error(data.message);
			 return ;
		 }else{
			
			 remveDivByIds(targetIdArray);
			 $("#container").empty();
			 loadJzwJg();
			 $("#dlg_chaifen").dialog("close");
			 $("#chaifenFormContent").empty();
		 }
	 }); */
}
//执行拆分方法 0624
function fw_cf(url,jzwfjView,targetIdArray){
	 KMAJAX.ajaxTodoJson(url,  JSON.stringify(jzwfjView), function(data){
		 if(data.statusCode!=200){
			 alertMsg.error(data.message);
			 return ;
		 }else{
			 remveDivByIds(targetIdArray);
			 $("#container").empty();
			 loadJzwJg();
		 }
	 });
}

function appendChaiFenFjInfo(){
	$("#chaifenFormContent").empty();
	var count=$("#chaiFenCount").val();
	if(!count){
		 alertMsg.error("请填写拆分个数");
		 return;
	}
	var ex = /^\d+$/;
	if (!ex.test(count)) {
		alertMsg.error("请正确填写拆分个数");
	}
	for(var i=0;i<count;i++){
		var divxh=$("<div ><label>拆分后房间"+(i+1)+"序号:</label><input name=\"c_fjxh"+i+"\"  id=\"c_fjxh"+i+"\" type=\"text\" class=\"easyui-validatebox\" data-options=\"required:true ,validType:'length[1,36]'\" onblur=\"getCfFjMc("+i+")\"/></div>");
		var divmc=$("<div ><label>拆分后房间"+(i+1)+"名称:</label><input name=\"c_fjmc"+i+"\"  id=\"c_fjmc"+i+"\" type=\"text\" class=\"easyui-validatebox\" data-options=\"required:true ,validType:'length[1,36]'\"/></div>");
		$("#chaifenFormContent").append(divxh);
		$("#chaifenFormContent").append(divmc);
		$("#chaifenFormContent").append("<br/>");
	}
	 
}

function getCfFjMc(i){
	var v=$("#c_fjxh"+i).val();
	if(!v){
		alertMsg.error("请输入房间序号");return;
	}
	if(isNaN(v)){
		alertMsg.error("房间序号必须为数字");return;
	}
	if(v.indexOf(".")>-1){
		alertMsg.error("房间序号必须为整数");return;
	}
	if(!isNaN(v)&&!(v.indexOf(".")>-1)){
		$("#c_fjmc"+i).val(v+"室");	
	}
}
//--------------房间拆分---------E----------------------------------------------------------------

//-----------------查看房间信息-------------------
function startLoadFjInfo(){
	 targetIdArray=getSelectedDivIds();
	 if(!targetIdArray||targetIdArray.length!=1){
		 alertMsg.error("请选择一个房间");return;
	 }
	 var jzwfjid=targetIdArray[0];
	 var url="${ctx}/psam/fw/enterDetailFwRk?fwid="+jzwfjid;
	 
	  $("#dlg_fwInfo").dialog({
		 title:"房屋详细信息",
		 href:url,
	 	 closed: false,
		    cache: false,
		    modal: true,
	 	width:760,height:600
	 });
	 $("#dlg_fwInfo").dialog("open"); 
}

//--------------采集单位信息--2016-08-12----------------


	function insertDwxx() {
		targetIdArray = getSelectedDivIds();
		if (!targetIdArray || targetIdArray.length != 1) {
			alertMsg.error("请选择一个房间");
			return;
		}
		var jzwfjid = targetIdArray[0];
		var url1 = "${ctx}/psam/dwxx/validateLoadDwxxAccInfo";
		var params = {
			"jzwfjid" : jzwfjid
		};
		KMAJAX.ajaxTodo(url1, params, function(data) {
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			} else {
// 				var url = "${ctx}/psam/dwxx/loadDwjbxxAccInfo?jzwfjid="
// 						+ jzwfjid;
                var url="${ctx}/psam/dwxx/enterDwxx?jzwfjid="
						+ jzwfjid;
				$("#dlg_DwxxInfo").dialog({
					title : "单位信息",
					href : url,
					width : 900,
					height : 600,
					modal : true,
// 					maximizable:true,
					resizable:true,
					fit:true,
					onClose : function() {reloadRkLb(); }
				});
// 				$("#dlg_DwxxInfo").dialog("open");
			}
		});
	}

</script>
</body> 
</html> 