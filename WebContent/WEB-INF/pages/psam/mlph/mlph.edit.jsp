<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<style>
fieldset{
	margin-top:5px;
}
input{
	margin: 2px;
}
.easyui-textbox{
    width: 140px;
}
</style>
<t:DataDict code="mlphlx" var="mlphlxDict"></t:DataDict>
<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 5px;">
		  <input  type="hidden" id="text_sszdyjxzqy_xtype" name="sszdyjxzqy_xtype"/>
		<form id="fm" method="post" action="${pageContext.request.contextPath}/psam/mlph/${empty mlph.YWLSH?'add':'save'}">
			<input type="hidden" name="YWLSH" value="${mlph.YWLSH}" />
			<input type="hidden" id="text_ssxqdm" name="SSXQDM" value="${mlph.SSXQDM}">
			<input type="hidden" id="text_sszdyjxzqy_dzbm" name="SSZDYJXZQY_DZBM" value="${mlph.SSZDYJXZQY_DZBM}">
			<input type="hidden" id="text_jwqbh" name="JWQBH" value="${mlph.JWQBH}">
			<input type="hidden" id="text_ssjlxxq_dzbm" name="SSJLXXQ_DZBM" value="${mlph.SSJLXXQ_DZBM}">
			<input type="hidden" name="SQRID" value="${sqrxx.sqrid}" />
			<input type="hidden" name="DZBM" value="${mlph.DZBM}" />
			<input type="hidden" id="zxdhzb" name="ZXDHZB" value="" />
			<input type="hidden" id="zxdzzb" name="ZXDZZB" value="" />
			<input type="hidden" id="orgCodeSubSix" value='<c:out value="${xzqh.dzbm }"></c:out>'></input>
			<fieldset>
				<legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />门楼牌号详细信息
				</legend>
				<table class="detail-table">

					<tr>
						<th>省市县（区）:</th>
						<td>
							<input name="SSXQMC" value="${mlph.SSXQMC}" id="text_ssxqmc" onclick="openXzqhTreeDialog('setSsxqdm')" class="easyui-textbox easyui-validatebox" data-options="required:true" readonly="readonly" />
						</td>
						<th>所属最低一级行政区划:</th>
						<td>
							 <input id="text_sszdyjxzqymc" onclick="openXzqhTreeDialog('setZdxzqhbm')" name="SJXZQYMC"  value="${mlph.SJXZQYMC}" class="easyui-textbox easyui-validatebox" data-options="required:true" readonly="readonly" />
						</td>
						<th>警务区名称:</th>
						<td>
							<input onclick="openLookBackjwq();" id="text_jwqmc" type="text" name="JWQMC" value="${mlph.JWQMC}" class="easyui-textbox easyui-validatebox" data-options="required:true,validType:'length[1,200]'" readonly="readonly" />
						</td>
						
					</tr>
					
					<tr>
						<th>所属街路巷(小区)名称:</th>
						<td>
							<input onclick="openLookBack_jlx()" id="text_ssjlxxq_jlxxqmc" class="easyui-textbox easyui-validatebox" type="text" name="SSJLXXQ_JLXXQMC" value="${mlph.SSJLXXQ_JLXXQMC}" readonly="readonly" data-options="required:true"/>
						</td>
						<th>门(楼)牌号:</th>
						<td>
							<input type="text" name="MLPH" value="${mlph.MLPH}" class="easyui-textbox easyui-validatebox" data-options="required:true ,validType:'length[1,150]'"  />
						</td>
						<th>临时门（楼）牌标识:</th>
						<td>
							<input id="lsmlpbs" dict="yesornoDict" class="easyui-textbox easyui-validatebox" name="LSMLPBS" value="${mlph.LSMLPBS}" data-options="editable:false" />
						</td>
					</tr>
					
					<tr>
						<th>门楼牌号类型:</th>
						<td>
							<input id="mlphlxid" dict="mlphlxDict" class="easyui-textbox easyui-validatebox" name="MLPHLXID" value="${mlph.MLPHLXID}" data-options="editable:false"/>
						</td>
						<th>中心点横坐标:</th>
						<td>
							<input type="text" class="easyui-textbox easyui-numberbox easyui-validatebox" id="ZXDHZB" data-options="required:true,min:113,max:125,precision:8"  value="${mlph.ZXDHZB}" readonly="readonly" onclick="openEditMark()"/>
						</td>
						<th>中心点纵坐标:</th>
						<td>
							<input type="text" class="easyui-textbox easyui-numberbox easyui-validatebox" id="ZXDZZB" data-options="required:true,min:34,max:39,precision:8"  value="${mlph.ZXDZZB}" readonly="readonly" onclick="openEditMark()"/>
						</td>
					</tr>
					<tr>
						<th>地址元素类型:</th>
						<td>
							<input id="dzyslxdm" dict="dzyslxDict" class="easyui-textbox easyui-validatebox" name="DZYSLXDM" value="${mlph.DZYSLXDM}" data-options="editable:false"/>
						</td>
					</tr>
					<tr>
						<th>地址:</th>
						<td colspan="5">
							<textarea  name="DZMC" class="easyui-textbox easyui-validatebox" data-options="required:true ,validType:'length[1,200]'"  value="" style="height:20px; width:600px">${mlph.DZMC}</textarea>
						</td>
					</tr>
					<tr>
						<th>区划内详细地址:</th>
						<td colspan="5">
							<textarea type="text" name="QHNXXDZ" value="" class="easyui-textbox easyui-validatebox" data-options="required:true ,validType:'length[1,200]'" style="height:20px; width:600px">${mlph.QHNXXDZ}</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			
			<fieldset>
				<legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />申请人信息
				</legend>
				<table class="detail-table">
					<tr>
						<th>申请人姓名:</th>
						<td>
							<input type="text" name="sqrxm" value="${sqrxx.sqrxm}" class="easyui-validatebox" data-options="required:false ,validType:'length[1,50]'" />
						</td>
						<th>申请人身份证号:</th>
						<td>
							<input type="text" name="sqrgmsfzhm" id="sqrgmsfzhm" value="${sqrxx.sqrgmsfzhm}"  class="easyui-validatebox" data-options="required:false ,validType:'length[1,18]'"  />
						</td>
						<th>申请人联系电话:</th>
						<td>
							<input type="text" name="sqrlxdh" value="${sqrxx.sqrlxdh}"  class="easyui-validatebox" data-options="required:false ,validType:'length[1,50]'"  />
						</td>
					</tr>
					<tr>
						<th>申请单位名称:</th>
						<td>
							<input type="text" name="sqdwmc" value="${sqrxx.sqdwmc}"  class="easyui-validatebox" data-options="required:false ,validType:'length[1,100]'"  />
						</td>
						<th>申请单位联系电话:</th>
						<td colspan="3">
							<input type="text" name="sqdwlxdh" value="${sqrxx.sqdwlxdh}"  class="easyui-validatebox" data-options="required:false ,validType:'length[1,50]'"  />
						</td>
					</tr>
				</table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="mlph.add">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="saveMlph()">保 存</a>
				</km:widgetTag>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
			</div>
		</form>
	</div>

</div>
<div id="dlg_mlph_zb_mark_pgis_edit"  class="easyui-dialog" style="width: 1000px; height: 500px; padding: 0px 0px" onClose="closeGuihuaDialog()" maximizable="true"  modal="true" closed="true" closable="true"  buttons="#dlg_mlph_zb_mark_buttons_edit">
	<div id="mymapEdit" class="map" style="height: 100%;"></div>
	<div id="dlg_mlph_zb_mark_buttons_edit">
		  <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editMark();">坐标标注</a>&nbsp;
		  <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeDia()">关闭 </a>&nbsp;
	</div>	
</div>	
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/mlph/km.mlph.mark.js"></script> --%>
<script type="text/javascript" >
function closeDia(){
	  var hzb=$("#zxdhzb").val();
	  var zzb=$("#zxdzzb").val();
	  $("#ZXDHZB").val(hzb);
	  $("#ZXDZZB").val(zzb);
	  $("#ZXDHZB").validatebox('validate');
	  $("#ZXDZZB").validatebox('validate');
	$('#dlg_mlph_zb_mark_pgis_edit').dialog('close');
}

function editMark(){
	removeAllMarker();
	_MapApp.changeDragMode('drawPoint', null, null,function(s) {
		var arr = s.split(",");
		if(!isInperminssion(arr[0],arr[1])){
			alertMsg.warn("所选点不在管辖区域内,请重新选取");
			return;
		}
		addMarker(s);
		alertMsg.confirm("经纬度为："+s+" 是否保存？", {
			cancelCall : function() {alertMsg.close();	
			  var hzb=$("#zxdhzb").val();
			  var zzb=$("#zxdzzb").val();
			  $("#ZXDHZB").val(hzb);
			  $("#ZXDZZB").val(zzb);
			removeAllMarker();},
			okCall : function() {alertMsg.close();
			     $("#ZXDHZB").val(arr[0]);
			     $("#ZXDZZB").val(arr[1]); 
			     
			     $("#zxdhzb").val(arr[0]);//存入隐藏域
				 $("#zxdzzb").val(arr[1]);
				 
				 $("#ZXDHZB").validatebox('validate');
				 $("#ZXDZZB").validatebox('validate');
			    /*  $("#ZXDHZB").attr("value",arr[0]);
			     $("#ZXDZZB").attr("value",arr[1]); */
			     $('#dlg_mlph_zb_mark_pgis_edit').dialog('close');
				}
			});
		
	});
}
function openEditMark(){
	$("#dlg_mlph_zb_mark_pgis_edit").dialog('open').dialog('setTitle','门楼牌号坐标点标注');
	onLoad();
}
/* function cancel(){var a=$("#mlphlxid").val();
	alertMsg.confirm("取消？",{
		cancelCall:function(){
			alertMsg.close();
		},
		okCall:function(){
			$('#edit-dialog').dialog('close');
			alertMsg.close();
		}
	});
	
} */
function cancel(){
	$('#edit-dialog').dialog('close');
}

function doneAndCloseDlg(json){
		if(json.statusCode == KMCORE.statusCode.error) {
			if(json.message && alertMsg){
				alertMsg.error(json.message);
			}
		} else if(json.statusCode == KMCORE.statusCode.serverError) {
			if(json.message && alertMsg){
				alertMsg.error(json.message);
			}
		} else if (json.statusCode == KMCORE.statusCode.timeout) {
			if(alertMsg) {
				alertMsg.error(json.message || KMCORE.msg("sessionTimout"), {okCall:KMCORE.loadLogin});
			}
			else {
				KMCORE.loadLogin();
			}
		} else {
			if(json.message && alertMsg){
				alertMsg.correct(json.message);
				$('#edit-dialog').dialog('close');
				
				setTimeout(function(){
					loaddata("reload");
				},2000);
			}
		}
}

function saveMlph() {
	var sqrgmsfzhm=$("#sqrgmsfzhm").val();
	var hzb=$("#ZXDHZB").val();
	var zzb=$("#ZXDZZB").val();
	$("#zxdhzb").val(hzb);
	$("#zxdzzb").val(zzb);
	if(PUBUtil.isStrHavaValue(sqrgmsfzhm)){
		if(isCardNo(sqrgmsfzhm)==false){
			alertMsg.warn("申请人身份证号格式不正确");return;
		}
	}
	//校验
	if(!KMAJAX.validateFromCallback($("#fm"),doneAndCloseDlg)){
		alertMsg.warn("数据校验不通过!");
	}
}


function setSsxqdm(node,ntype){
	$('#text_ssxqdm').val(node.id);
	$('#text_ssxqmc').val(node.text);
	$('#text_ssxqmc').focus();
}

function setZdxzqhbm(node,ntype){
	$('#text_sszdyjxzqy_dzbm').val(node.id);
	$('#text_sszdyjxzqymc').val(node.text);
	$('#text_sszdyjxzqymc').focus();
	$('#text_sszdyjxzqy_xtype').val(ntype.toLowerCase());
}
function openLookBackjwq(){
	var url="${ctx}/psam/jwq/loadBingbackjwqPage";
	 var options={title:"查找带回",width:700,height:500, url:url,
			 onClosed:function(data){
				var resjson=PUBUtil.jsonEval(data);
				if(resjson){
					var id=resjson.jwqid;
					var bh=resjson.jwqbh;
					var text=resjson.jwqmc;
					$("#text_jwqbh").val(bh);
					$("#text_jwqmc").val(text);
					$("#text_jwqmc").focus();
				}
				}};
			 returnBackDialog.open(options);
}
function openLookBack_jlx(){
	var  sszdyjxzqy_dzbm = $('#text_sszdyjxzqy_dzbm').val();
	var  sszdyjxzqy_xtype  = $('#text_sszdyjxzqy_xtype').val();
	var orgCodeSubSix=$("#orgCodeSubSix").val();//当前登录人行政区划
	var url="${ctx}/psam/jlx/loadBingbackJlxPage";
	var params = {
			sszdyjxzqy_dzbm:orgCodeSubSix,
			sszdyjxzqy_xtype:"XZQH",
			//sszdyjxzqy_xtype:sszdyjxzqy_xtype,
			isLoadFromSuperXzqy:2
	};
	
	var options={title:"查找带回",width:800,height:400, url:url,params:params,
	 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var dzbm=resjson.dzbm;
				var jlxxqmc=resjson.jlxxqmc;
				 $("#text_ssjlxxq_dzbm").val(dzbm);
				 $("#text_ssjlxxq_jlxxqmc").val(jlxxqmc);
				 $("#text_ssjlxxq_jlxxqmc").focus();
				 $("#text_sszdyjxzqymc").val(resjson.zdyjxzqhmc);
				/*  $("#text_sszdyjxzqymc").focus(); */
				 $("#text_sszdyjxzqy_dzbm").val(resjson.zdyjxzqhdzbm);
				
				 $("#text_sszdyjxzqymc").validatebox('validate');
// 				setTimeout(function(){
// 					$("#text_sszdyjxzqymc").focus();
// 				}, 300);
				/*  var ajaxUrl = "${ctx}/psam/jlx/selectMcBySszdyjxzqyDzbm";
				 var param = { "dzbm" : dzbm};
				 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
					 $("#text_sszdyjxzqymc").val(data["MC"]);
					}); */
			}
			}
	};
	 returnBackDialog.open(options);
}

function isCardNo(card){
	   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
	   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	   if(reg.test(card) === false)
	   {
	       //alert("身份证输入不合法");
	       return  false;
	   }
	}
	
/*---------------加载地图----------------*/
var _MapApp=undefined;
var targetMarkMlphId=undefined;
var allPolygonBjzb=new Array();
		
	function addMarker(s,title){
		removeAllMarker();
		var arr = s.split(",");
		var p = new Point(arr[0], arr[1]);
		var pIcon=new Icon();
		pIcon.image=ctx+"/static/images/marker.png";
		pIcon.height=16;
		pIcon.width=16;
		var marker = new Marker(p,pIcon,new Title(s));
		_MapApp.addOverlay(marker);
		voArray.push(marker);
	}
	var voArray=new Array();
	function removeAllMarker(){
		if(voArray!=null&&voArray.length>0){
			for(var i=0;i<voArray.length;i++){
				_MapApp.removeOverlay(voArray[i], false);
			}
			voArray=[];
		}
	}
	function isInperminssion(lon,lat){
    	var pointX=lon;
    	var pointY=lat;
    	if(allPolygonBjzb==null){
    		return false;	
    	}
    	for(var k=0;k<allPolygonBjzb.length;k++){
    		if(""!=allPolygonBjzb[k]&&"null"!=allPolygonBjzb[k]&&"undefined"!=allPolygonBjzb[k]&&undefined!=allPolygonBjzb[k]){
	    		var flag=isPermissionbyPoint(allPolygonBjzb, pointX, pointY);
	        	if(!flag){
	        		 return false;	
	        	}
    		}
    	}
    	return true;
 }
    function onLoad(){
    	if(_compatIE()){
    		setTimeout(function(){
	            _MapApp = new EzMap(document.getElementById("mymapEdit"));
	            _MapApp.showMapControl();
	            _MapApp.centerAndZoom(new Point(116.99655, 36.66345), 11);
	            var uOverview=new OverView();// 构造一个鹰眼对象
	            uOverview.width=200;// 设置鹰眼视窗的宽度
	            uOverview.height=200;// 设置鹰眼视窗的高度
	            uOverview.minLevel=5;// 设置鹰眼视窗中最小显示地图级别
	            uOverview.maxLevel=12;// 设置鹰眼视窗中最大显示地图级别
	            _MapApp.addOverView(uOverview);// 添加鹰眼对象
	            loadManageBianjie();
    		}, 100);
    	}else if(_MapApp==null){
    		var pEle=document.getElementById("mymapEdit");
    		pEle.innerHTML="<p>目前EzMap地图引擎不支持你使用的浏览器，我们当前支持如下浏览器类型:</p><ul><li><a href='http://www.microsoft.com/windows/ie/downloads/default.asp'>IE</a> 5.5+ (Windows)</li>";
    	}
      }
function loadManageBianjie(ajaxUrl) {
	 ajaxUrl = ctx+"/psam/jwq/loadManageBianjie";
			var param = {};
			KMAJAX.ajaxTodo(ajaxUrl,param,function(data) {
				if (data.statusCode != 200) {
					alertMsg.error(data.message);
					return;
				}
			orgInfoArray = data.data;
			if (orgInfoArray && orgInfoArray != null&& orgInfoArray.length > 0) {
				for (var i = 0; i < orgInfoArray.length; i++) {
					var bjzbzx = orgInfoArray[i].bjzbz;
					if (PUBUtil.isStrHavaValue(bjzbzx)) {
						var everyOrgBjzb = new Array();
						var everyOrgBjzb = bjzbzx.split(";");
						for (var j = 0; j < everyOrgBjzb.length; j++) {
							if (PUBUtil.isStrHavaValue(everyOrgBjzb[j])) {
								allPolygonBjzb.push(everyOrgBjzb[j]);
								var polygonx=new Polygon(everyOrgBjzb[j], "black", 6,0.3, "red");
								_MapApp.addOverlay(polygonx);
								if(i==orgInfoArray.length-1){
									var mbr = polygonx.getMBR();
									var pointxx = mbr.centerPoint();
									_MapApp.centerAndZoom(pointxx,10);
								}
						   }
						}
				   }
				}
			}
		});
	 }
 //------------------------------------------
	//地图初始化

    function resetArray(){
		pPolygons.splice(0,pPolygons.length);//清空已边界数组
		pPolygons= new Array();
    }
    

</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>