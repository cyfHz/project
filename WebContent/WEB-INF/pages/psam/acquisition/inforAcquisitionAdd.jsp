<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	function doSubmit(){
		var jzwmc=$("#jzwmc").val();
		if(!jzwmc){
			alertMsg.warn("建筑物名称不能为空");return;
		}
		
		if(!KMAJAX.validateFromCallbackJson($("#formss"),function(data){
			if(data.statusCode==200){
				var jzwid=data.data.jzwid;
				var lat=data.data.lat;
				var lon=data.data.lon;
				var jzwmc=data.data.jzwmc;
				var jzwlx=data.data.jzwlx;
				var returnValue=jzwid+"#"+lat+"#"+lon+"#"+jzwmc+"#"+jzwlx;
				window.top.$("#div_lookupbackvarValue_tmp").remove();
				var divsss="<div id=\"div_lookupbackvarValue_tmp\"><input type=\"hidden\" value=\""+returnValue+"\" id=\"bringbacklookupbackvarValue_tmp\"/></div>";
				$(window.top.document.body).append(divsss);
				alertMsg.info(data.message);
				editDialog.close();
			}else{
				alertMsg.error(data.message);return;
			}
		
		})){
			alertMsg.info("请确认校验不通过数据");return;
		}
	}
	
	//-----------------------------------------------------
		//省市县区（区）查找带回
	function openSsxqdmLookBack(){
		var param={"showXzjd":false,"showSqjcwh":false};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:500, url:url,params:param,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				 $("#ssxqdm").val(id);
				 $("#text_ssxqdm").val(text);
				 
// 				 $("#text_ssxqdm").focus();
				 $("#text_ssxqdm").validatebox('validate');
				 selectJzwXzqh();
			}
			}};
		 returnBackDialog.open(options);
	}
		//所属最低一级行政区域 查找带回
	function openSszdyjxzqyLookBack(){
		var param={"showXzjd":true,"showSqjcwh":true};
		var url="${ctx}/psam/xzqh/loadBingbackPage";
		 var options={title:"查找带回",width:320,height:500, url:url,params:param,
		 onClosed:function(data){
			var resjson=PUBUtil.jsonEval(data);
			if(resjson){
				var id=resjson.id;
				var text=resjson.text;
				var xtype=resjson.xtype;
				 $("#sszdyjxzqy_dzbm").val(id);
				 $("#sszdyjxzqy_dzbm_mc").val(text);
				 $("#sszdyjxzqy_xtype").val(xtype);
				 
// 				 $("#sszdyjxzqy_dzbm_mc").focus();
				 $("#sszdyjxzqy_dzbm_mc").validatebox('validate');
			}
			}};
		 returnBackDialog.open(options);
	}
	//街路巷查找带回
	function openLookBackjlx(){
		 var sszdyjxzqy_dzbm=$("#sszdyjxzqy_dzbm").val();
		 var sszdyjxzqy_xtype=$("#sszdyjxzqy_xtype").val();
		 /* if(!sszdyjxzqy_dzbm){
			 alertMsg.warn("请选择最低行政区域");return;
		 } */
		 var ssxqdm_jlx=$("#ssxqdm_jlx").val();
		 sszdyjxzqy_xtype="XZQH";
		 var params={"sszdyjxzqy_dzbm":ssxqdm_jlx,"sszdyjxzqy_xtype":sszdyjxzqy_xtype,"isLoadFromSuperXzqy":2}
		var url="${ctx}/psam/jlx/loadBingbackJlxPage";
		 var options={title:"查找带回",width:800,height:500, url:url,params:params,
				 onClosed:function(data){
					var resjson=PUBUtil.jsonEval(data);
					if(resjson){
						var id=resjson.dzbm;
						var text=resjson.jlxxqmc;
						 $("#ssjlxxq_dzbm").val(id);
						 $("#ssjlxxq_dzbm_mc").val(text);
						 $("#sszdyjxzqy_dzbm_mc").val(resjson.zdyjxzqhmc);
						 $("#sszdyjxzqy_dzbm").val(resjson.zdyjxzqhdzbm);
						 
						 $("#qhnxxdz").validatebox('validate');
						 $("#ssjlxxq_dzbm_mc").validatebox('validate');
					 /*  $("#ssjlxxq_dzbm_mc").focus();
						 setTimeout(function(){
							 $("#qhnxxdz").focus();
							},500); */
// 						 $("#qhnxxdz").click();
// 						 $("#sszdyjxzqy_dzbm_mc").focus();
// 						 $("#jzwmc").focus();
						 /* var ajaxUrl = "${ctx}/psam/jlx/selectMcBySszdyjxzqyDzbm";
						 var param = { "dzbm" : id};
						 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
							 $("#sszdyjxzqy_dzbm_mc").val(data["MC"]);
							 $("#sszdyjxzqy_dzbm").val([data["SSZDYJXZQY_DZBM"]])
							}); */
						 selectJzwJlx();
					}
					}};
				 returnBackDialog.open(options);
	}
	function cancel(){
		editDialog.close(100);
	}
	function focus(){
		$("#qhnxxdz").focus();
	}
	//产生详细地址
	function generateXzhi(value){
		var sszdyjxzqy_dzbm_mc=$("#sszdyjxzqy_dzbm_mc").val();//所属最低一级行政区划
		var ssjlxxq_dzbm_mc=$("#ssjlxxq_dzbm_mc").val();//所属街路巷小区
		var mlphqz=$("#mlphqz").val();
		var mlphbh=$("#mlphbh").val();//建筑物牌号
		var mlphlxid=$("#mlphlxid").combobox("getText");//alert(mlphlxid);建筑物牌号类型
		//var vaas=sszdyjxzqy_dzbm_mc+ssjlxxq_dzbm_mc+mlphqz+mlphbh;
		var vaas=sszdyjxzqy_dzbm_mc+ssjlxxq_dzbm_mc+mlphbh;
		var qhnxxdz=$("#qhnxxdz").val();//区划内详细地址
		var mlphdzmc=$("#mlphdzmc").val();//
		var jzwmc=$("#jzwmc").val();//建筑物名称
		var jzwdzmc=$("#jzwdzmc").val();//地址名称
		var a=value;
		if(a=='1'){
			var mc=mlphbh+mlphlxid;
			if(!jzwmc||jzwmc.length<1){
				$("#jzwmc").val(mc);
			}
		}
		if(a=='2'){
			var dzmc=vaas+mlphlxid;
			if(!jzwdzmc||jzwdzmc.length<1){
				$("#jzwdzmc").val(dzmc);
			}
		}
		if(a=='3'){
			var qhndz=ssjlxxq_dzbm_mc+mlphbh+mlphlxid;
			if(!qhnxxdz||qhnxxdz.length<1){
				$("#qhnxxdz").val(qhndz);
		    }
		}
	}
	function isJZWSelected(){
		  var isJZW = $('#isJZW').combobox('getValue');
		  if(isJZW=='true'){
				$("#divJZW").css("display","");
				$("#divJZW").appendTo("#XCXXXXXXXXXX");
			}else{
				$("#divJZW").css("display","none");
			}
	}
	//省市区县 修改
	function selectJzwXzqh(){
		//var sszdyjxzqy_dzbm_mc=$("#sszdyjxzqy_dzbm_mc").val();//所属最低一级行政区划
		var text_ssxqdm=$("#text_ssxqdm").val();//省市县区
		var ssjlxxq_dzbm_mc=$("#ssjlxxq_dzbm_mc").val();//所属街路巷小区
		var mlphbh=$("#mlphbh").val();//建筑物牌号
		var mlphlxid=$("#mlphlxid").combobox("getText");//建筑物牌号类型
		
		var dzmc=text_ssxqdm+ssjlxxq_dzbm_mc+mlphbh+mlphlxid
		$("#jzwdzmc").val(dzmc);
	}
	//修改街路巷
	function selectJzwJlx(){
		//var sszdyjxzqy_dzbm_mc=$("#sszdyjxzqy_dzbm_mc").val();//所属最低一级行政区划
		var text_ssxqdm=$("#text_ssxqdm").val();//省市县区
		var ssjlxxq_dzbm_mc=$("#ssjlxxq_dzbm_mc").val();//所属街路巷小区
		var mlphbh=$("#mlphbh").val();//建筑物牌号
		var mlphlxid=$("#mlphlxid").combobox("getText");//建筑物牌号类型
		
		var dzmc=text_ssxqdm+ssjlxxq_dzbm_mc+mlphbh+mlphlxid;
		var qhndz=ssjlxxq_dzbm_mc+mlphbh+mlphlxid;
		$("#jzwdzmc").val(dzmc);//地址名称
		$("#qhnxxdz").val(qhndz);//区划内
	}
	//修改牌号
	function selectJzwPh(){
		//var sszdyjxzqy_dzbm_mc=$("#sszdyjxzqy_dzbm_mc").val();//所属最低一级行政区划
		var text_ssxqdm=$("#text_ssxqdm").val();//省市县区
		var ssjlxxq_dzbm_mc=$("#ssjlxxq_dzbm_mc").val();//所属街路巷小区
		var mlphbh=$("#mlphbh").val();//建筑物牌号
		var mlphlxid=$("#mlphlxid").combobox("getText");//建筑物牌号类型
		
		var dzmc=text_ssxqdm+ssjlxxq_dzbm_mc+mlphbh+mlphlxid
		var qhndz=ssjlxxq_dzbm_mc+mlphbh+mlphlxid;
		var mc=mlphbh+mlphlxid;
		$("#jzwdzmc").val(dzmc);//地址名称
		$("#qhnxxdz").val(qhndz);//区划内
		$("#jzwmc").val(mc);//建筑物名称
		
		$("#jzwmc").validatebox('validate');
		$("#qhnxxdz").validatebox('validate');
		
// 		$("#jzwmc").focus();
// 		setTimeout(function(){
// 			 $("#qhnxxdz").focus();
// 			},300);
		
	}
	//修改牌号类型
	function selectJzwPhlx(){
// 		$("#jzwmc").focus();
// 		setTimeout(function(){
// 			 $("#qhnxxdz").focus();
// 			},300);
		//var sszdyjxzqy_dzbm_mc=$("#sszdyjxzqy_dzbm_mc").val();//所属最低一级行政区划
		var text_ssxqdm=$("#text_ssxqdm").val();//省市县区
		var ssjlxxq_dzbm_mc=$("#ssjlxxq_dzbm_mc").val();//所属街路巷小区
		var mlphbh=$("#mlphbh").val();//建筑物牌号
		var mlphlxid=$("#mlphlxid").combobox("getText");//建筑物牌号类型
		
		var dzmc=text_ssxqdm+ssjlxxq_dzbm_mc+mlphbh+mlphlxid
		var qhndz=ssjlxxq_dzbm_mc+mlphbh+mlphlxid;
		var mc=mlphbh+mlphlxid;
		$("#jzwdzmc").val(dzmc);//地址名称
		$("#qhnxxdz").val(qhndz);//区划内
		$("#jzwmc").val(mc);//建筑物名称
		
		$("#jzwmc").validatebox('validate');
		$("#qhnxxdz").validatebox('validate');
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
<t:DataDict code="DZYSFL" var="dzyslxDict"></t:DataDict>
<t:DataDict code="mlphlx" var="mlphlxDict"></t:DataDict>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="formss"  method="post" action="${ctx}/psam/acquisition/addJzwInfoAcquisition">
		<input type="hidden" name="fromby" value="0"/>
		<input type="hidden" name="jwqid" value="${jwqid }"/>
		<input type="hidden" name="isJZW" value="true"/>
		<input type="hidden" id="mlphdzmc" name="mlphdzmc" />
		<input type="hidden" id="ssxqdm_jlx" value='<c:out value="${xzqh.dzbm }"></c:out>'>
			<fieldset>
				 <table id="formTbale" cellpadding="4">
				  <tr>
				  <th>省市县区（区）</th>
				  	<td>
					 <input id="text_ssxqdm" value='<c:out value="${xzqh.xzqhmc }"></c:out>' class="easyui-textbox easyui-validatebox form-control" onclick="openSsxqdmLookBack();"  data-options="required:true " readonly="readonly"/>
					 <input type="hidden" id="ssxqdm" name="ssxqdm" value='<c:out value="${xzqh.dzbm }"></c:out>' >
					</td>
					<th>所属最低一级行政区域</th>
					<td>
						<input id="sszdyjxzqy_dzbm_mc" name="sszdyjxzqy_dzbm_mc"  class="easyui-textbox easyui-validatebox form-control" onclick="openSszdyjxzqyLookBack();"  style="width:170px;" readonly="readonly" />
						<input type="hidden" id="sszdyjxzqy_dzbm" name="sszdyjxzqy_dzbm" value="">
						<input type="hidden" id="sszdyjxzqy_xtype"  value="">
					</td>	
					
					<th>所属街路巷（小区）</th>
						<td>
						<input type="text" name="ssjlxxq_jlxxqmc" id="ssjlxxq_dzbm_mc"  onclick="openLookBackjlx();" class="easyui-textbox easyui-validatebox form-control" data-options="required:true " readonly="readonly" />
						<input type="hidden" id="ssjlxxq_dzbm" name="ssjlxxq_dzbm">
					</td> 
				 </tr>
				 
				 <tr>
					  <th>地址元素类型</th>
					  <td>
					     <select class="easyui-combobox" data-options="required:true,editable:false"   name="dzyslxdm" value="${jzw.dzyslxdm}"  style="width:170px;" >
					       <option value="50">建筑物</option>
					       <option value="43">单位（住宅）院</option>
					       <option value="44">自然村</option>
					     </select>
					  </td>	
					  <th>建筑物牌号</th>
					 <td><input type="text" id="mlphbh" name="mlph" class="easyui-textbox easyui-validatebox  form-control" data-options="required:true,validType:'length[0,150]'" onblur="selectJzwPh()"/></td>
				   
					 <th>建筑物牌号类型</th>
					 <td> 
					 <input class="easyui-combobox" id="mlphlxid" dict="mlphlxDict" name="mlphlxid" class="form-control"   style="width:173px;" data-options="required:true,editable:false, onSelect:function(){selectJzwPhlx()}"  /></td>			
					 </tr>
					 
				 <tr>
				     
					<!--  <th>门楼牌号前缀</th>
					 <td> <input id="mlphqz"  name="mlphqz" class="easyui-textbox easyui-validatebox  form-control" /></td> -->
				       <th>是否临时建筑物牌号</th>
					 <td>
						 <select id="lsmlpbs" name="lsmlpbs" style="width: 170px;" class="easyui-combobox" data-options="editable:false">
							 <option value="1">否</option>
							 <option value="0">是</option>
						 </select>
					 </td>
					 <th>中心点横坐标</th> 
					  <td><input type="text" id="zxdhzb" value="${jzw.zxdhzb}"  name="zxdhzb" maxLength="11" class="easyui-textbox easyui-validatebox form-control" readonly="readonly" /></td>
					
					   <th>中心点纵坐标</th>
					   <td><input type="text" id="zxdzzb"  value="${jzw.zxdzzb}"   name="zxdzzb" maxLength="11" class="easyui-textbox easyui-validatebox form-control" readonly="readonly"/></td>
				 </tr>
<!-- 				  
                 <tr>
					 <th>所属排</th>
					 <td><input type="text" id="rowline" name="rowline" class="easyui-textbox easyui-validatebox  form-control" data-options="validType:'length[0,30]'" /></td>
				 </tr>
			 
				 <tr>
					  <th>地址名称</th>
					  <td colspan="6">
					    <input type="text" style="width: 700px;" name="mlphdzmc" id="mlphdzmc" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,100]'" onclick="generateXzhi();"/>
					  </td>
				 </tr>
				  -->	
				<!-- <tr>
						<th>是否是建筑物</th>
						<td>
							<select id="isJZW" name="isJZW" style="width: 170px;" class="easyui-combobox form-control" data-options="onSelect:isJZWSelected " >
								<option value="false">否</option>
								<option value="true">是</option>
							</select>
						</td>
					</tr> -->
				   <!-- 申请人基本信息 -->
				    <tr>
	              <th>建筑物名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				  <td colspan="6">
				  	<input style="width: 600px;" type="text" id="jzwmc"  name="jzwmc"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true ,validType:'length[0,150]'" />
				  </td>	
				</tr>	
						
				<tr>
				  <th>地址名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
	               <td colspan="6"> 
	              		<input style="width: 600px;" value='<c:out value="${xzqh.xzqhmc }"></c:out>' type="text" name="dzmc" id="jzwdzmc"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true ,validType:'length[0,100]'" >
	               </td>
	            <tr>
	             <tr>
					  <th>区划内详细地址</th>
					  <td colspan="6">
					    <input type="text" style="width: 700px;" name="qhnxxdz" id="qhnxxdz" class="easyui-textbox easyui-validatebox form-control" data-options="required:true ,validType:'length[0,100]'" />
					  </td>
				 </tr>
	            <tr>
	            	<th>别名简称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
					<td><input type="text" id="bmjc"  name="bmjc"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,200]'" /></td>
				
	               <th>地(住)址存在标识</th>
				   <td>
					     <select name="dzzczbz" style="width: 170px;" class="easyui-combobox form-control" data-options="editable:false">
					        <option value="1">是</option>
					        <option value="0">否</option>
					      </select>
					 </td>
					 <th>地址是否在用标识</th>
					  <td>
					        <select name="dzzzybs" style="width: 170px;" class="easyui-combobox form-control" data-options="editable:false">
						        <option value="1">是</option>
						        <option value="0">否</option>
					        </select>
					  </td>
			   </tr>
				 </table>
				 <!-- <div id="XCXXXXXXXXXX" style="width: 100%"></div> -->
				 <tr><td colspan="6"><hr/></td> </tr>
					<%-- <table id="formTssbale" cellpadding="4">
				     <tr>
					      <th>申请人姓名</th>
					      <td><input type="text" id="sqrxm"  name="sqrxm" value='<c:out value="${orguser.user_name}"></c:out>' class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" /></td>
					      <th>申请人联系电话</th>
					      <td><input type="text" id="sqrlxdh"  name="sqrlxdh"  value='<c:out value="${orguser.user_mobile}"></c:out>' class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" /></td>
					      <th>申请人公民身份证号码</th>
					      <td><input type="text" id="sqrgmsfzhm"  name="sqrgmsfzhm"  value='<c:out value="${orguser.user_sfzh}"></c:out>' class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[18,18]'" /></td>
				     </tr>
					<tr>
					 <th>申请人单位名称</th>
					 <td><input type="text" id="sqdwmc"  name="sqdwmc" value='<c:out value="${org.orgna_name}"></c:out>' class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,100]'" /></td>
					 <th>申请单位联系电话</th>
					 <td> <input type="text" id="sqdwlxdh"  name="sqdwlxdh" value='<c:out value="${org.orgna_tel}"></c:out>'  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" />
					 </td>
					</tr>
				 </table> --%>
			</fieldset>
				<div style="position: absolute;bottom: 5px;right: 10px;">
					<km:widgetTag widgetRulevalue="acquisition.acquisition">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit();">保 存</a>
					</km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel();">关  闭</a>
				</div>
			
		</form>
	</div>
	
	<!-- <div id="divJZW" style="display: none;width: 100%" >
	<hr>
	 	<table cellpadding="4">
	          
		</table>
	</div> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>	
</div>