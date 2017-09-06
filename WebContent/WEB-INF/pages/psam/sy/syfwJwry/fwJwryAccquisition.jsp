	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="/static/meta/taglib.jsp" %> 
  <script src="${pageContext.request.contextPath}/static/libs/jquery/jquery-form.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<script type="text/javascript">
function doSubmit(){
	  var fzsfzhm=$("#fzsfzh").val();
		if(isCardNo(fzsfzhm)==false){
			alertMsg.warn("房主身份证号格式不正确");return;
		}
	var formx=$("#fm_jwryinfo");
	if(!formx.form('validate')){
		alertMsg.warn("请确认校验不通过数据");
		return false;
	}
	var options= {    
		
            url:"${ctx}/psam/sy/syFwJwry/saveSyFwJwryAccInfo",    
            type:'post', 
            dataType:"json",
            success:function(data){
            	loadMask.close();
            	if(data.statusCode=='200'){
            		alertMsg.info(data.message);
            		$("#dlg_jwry_accqui").dialog("close"); 
            	}else{
            		 alertMsg.error(data.message);
            	}
            	
            },
            error:KMCORE.ajaxError
        }; 
	loadMask.open(); 
	formx.ajaxSubmit(options);
}
function cancel(){

	$("#dlg_jwry_accqui").dialog("close"); 
	//$("#dlg_jwry_accqui").empty();
}
function setImagePreview() {
	 var docObj=document.getElementById("imgfile");
	 var imgObjPreview=document.getElementById("showimg1");
	 if(docObj.files && docObj.files[0]){
		  var imgSrcx =docObj.value;
		  if (!/\.(jpg|jpeg|png|JPG|PNG|JPEG|gif|GIF)$/.test(imgSrcx)) {
             alertMsg.warn("您上传的图片格式不正确，请重新选择!");
             return false;
         }
   	  //火狐下，直接设img属性
   	  //imgObjPreview.src = docObj.files[0].getAsDataURL();
   	  //火狐7以上版本不能用上面的getAsDataURL()方式获取
   	  imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	 }else{
	 	 //IE下，使用滤镜
	 	 docObj.select();
   	  var imgSrc = document.selection.createRange().text;
   	  if (!/\.(jpg|jpeg|png|JPG|PNG|JPEG|gif|GIF)$/.test(imgSrc)) {
   		  alertMsg.warn("您上传的图片格式不正确，请重新选择!");
             return false;
         }
   	  var localImagId = document.getElementById("localImag");
   	  //图片异常的捕捉，防止用户修改后缀来伪造图片
   	  try{
   	   localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
   	   localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
   	  }catch(e){
   		  alertMsg.warn("您上传的图片格式不正确，请重新选择!");
   	   return false;
   	  }
   	  imgObjPreview.style.display = 'none';
   	  document.selection.empty();
	 }
	 return true;
	}
function loadJwryInfo(){
   var sfzh=$("#zjhm").val();
    var jzwfjid=$("#fjbm").val();
	var ajaxUrl = "${ctx}/psam/sy/syFwJwry/loadJwryInfo";
	var param = { "sfzh" : sfzh,"jzwfjid":jzwfjid};
	loadMask.open(); 
	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		loadMask.close();
		if(data.statusCode=='300'){
			 alertMsg.error(data.message);
		}
		if(data.statusCode=='200'){
			var csrq=data.data.csrq;
			if(csrq!=null && csrq.length>10){
				csrq = csrq.substr(0, 10);
			}
			var tlyxq=data.data.tlyxq;
			if(tlyxq!=null && tlyxq.length>10){
				tlyxq=tlyxq.substr(0, 10);
			} 
			$("#fm_jwryinfo").form("load",{
				"ywm":data.data.ywm,
				"ywx":data.data.ywx,
				"zwm":data.data.zwm,
				"csrq":csrq, 
				"zjzl":data.data.zjzl,
				"qzzl":data.data.qzzl,
				"qzbh":data.data.qzbh,
			    "gj":data.data.gj,
			    "jwrylxdh":data.data.jwrylxdh,
			    "tlyxq":tlyxq, 
			    "fwfzxm":data.data.fwfzxm,
			    "fzsfzh":data.data.fzsfzh
			    
			});
				 imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=jwry&"+Math.floor(Math.random()*100);
				$("#showimg1").hide().attr("src",imageUrl).fadeIn(); 
		}
		if(data.statusCode=='202'){
		 	var csrq=data.data.CSRQ;
			if(csrq!=null && csrq.length>10){
				csrq = csrq.substr(0, 10);
			}
			$("#fm_jwryinfo").form("load",{
				"csrq":csrq
			}); 
			imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=jwry&"+Math.floor(Math.random()*100);
			$("#showimg1").hide().attr("src",imageUrl).fadeIn();
				
		}
	}); 

   
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
	
</script>
<style type="text/css">
th{
    width: 20%;
}
</style>
<t:DataDict code="gj" var="gjDict"></t:DataDict>
<t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
<t:DataDict code="qzzl" var="qzzlDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px; padding-left:10px">
		<form id="fm_jwryinfo"  method="post" action="${ctx}/psam/sy/syFwJwry/saveSyFwJwryAccInfo" enctype="multipart/form-data">
			<fieldset>
			 <input type="hidden" name="fjbm" id="fjbm" value="${jwry.fjbm}"/>
			     <input type="hidden" name="jzwid" value="${jwry.jzwid}"/>
		       	<input type="hidden" name="dzbm" value="${jwry.dzbm}">
		       	<%-- <input type="hidden" name="jwryid" value="${jwry.jwryid}"> --%>
		       	<input type="hidden" name="lrfs" value="0">
				  
				 <table id="formTbale" cellpadding="4">
				  <!-- <tr><th>人员信息</th><th colspan="5"><hr></th></tr> -->
				    <tr colspan="4"><th colspan="4"><div class="cap"> 人员信息</div></th></tr>
				
				 <tr>
				        <th>证件号码 <span class="span_main">*</span></th>
						<td><input type="text" name="zjhm" id="zjhm" value="${jwry.zjhm}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,50]'" onblur="loadJwryInfo()"/></td>
				     
				        
				        	<th>照片</th>
					 	 	 <td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" id="localImag" >
					 			<img name="showimg" id="showimg1" src="" width="116px" height="144px" alt="人口照片"/> 
					 		</div>
					 			<input name="imgfile" type="file" id="imgfile"  onchange="setImagePreview();" size="3" style="margin-left: 30px;" /> 
					 	      <div style="color: red;font-size: 10px;">(照片格式：jpg,jpeg,png,gif,不能超过200K)</div>
					 	 </td>
				 </tr>
				 	 <tr>
				 	   <th>英文名 <span class="span_main">*</span></th>
						<td><input type="text" name="ywm" id="ywm" value="${jwry.ywm}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
				 	   </tr>
				 	 <tr>
				 	   <th>英文姓 <span class="span_main">*</span></th>
						<td><input type="text" name="ywx" id="ywx" value="${jwry.ywx}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
				       </tr>
				 	 <tr>
				       <th>中文名</th>
						<td><input type="text" name="zwm" id="zwm" value="${jwry.zwm}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>	
				        
				        	
				 </tr>
				 <tr>
				 <th>出生日期 </th>
						<td><input type="text" id="csrq" name="csrq" value="${jwry.csrq}" class="easyui-datebox easyui-validatebox form-control"   style="width:170px;" data-options="editable:false"/></td> 	
				  <th>证件种类 <span class="span_main">*</span></th>
				  <td>
				  <input dict="zjlxDict" name="zjzl" value="${jwry.zjzl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
						<%-- <td><input type="text" name="zjzl" id="zjzl" value="${jwry.zjzl}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,36]'" /></td> --%>
					
				 </tr>
				  <tr>
				       <th>签证种类 <span class="span_main">*</span></th>
				        <td> <input dict="qzzlDict" name="qzzl" value="${jwry.qzzl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
						<%-- <td><input type="text" id="qzzl" name="qzzl"  value="${jwry.qzzl}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td> --%>
					     <th>签证编号</th>
					     <td><input type="text" id="qzbh" name="qzbh"  value="${jwry.qzbh}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td> 
				 </tr>
				 <tr>
				        <th>国籍 <span class="span_main">*</span></th>
				          <td> <input dict="gjDict" name="gj" value="${jwry.gj}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 	 <!-- <td><input type="text" id="gj" name="gj" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" ></td> 	 -->
				       <th>境外人员联系电话</th>
				       <td><input type="text" name="jwrylxdh" id="jwrylxdh" value="${jwry.jwrylxdh}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>
				       </tr>
					<tr>
						
				         <th>停留有效期</th>
						<td><input type="text" id="tlyxq" name="tlyxq" value="${jwry.tlyxq}"  style="width:170px;" class="easyui-datebox easyui-validatebox form-control"  data-options="editable:false" /></td>		
				     <th>房间号</th>
					     <td><input type="text" id="fjh" name="fjh"  value="${jwry.fjh}" class="easyui-textbox easyui-validatebox form-control"  data-options=" required:true,validType:'length[0,36]'" /></td> 
				</tr>
				  <!--  <tr><th>房屋房主信息</th><th colspan="5"><hr></th></tr> -->
				     <tr colspan="4"><th colspan="4"><div class="cap"> 房屋房主信息
				     </div></th></tr> 
					   <tr>
					   <th>房屋房主姓名 <span class="span_main">*</span></th>
						<td><input type="text" id="fwfzxm" name="fwfzxm"  value="${jwry.fwfzxm}" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[0,50]'" /></td>
					      <th>房主身份证号 <span class="span_main">*</span></th>
					     <td><input type="text" id="fzsfzh" name="fzsfzh"  value="${jwry.fzsfzh}" class="easyui-textbox easyui-validatebox form-control"  data-options="required:true,validType:'length[0,20]'" /></td> 
					 </tr>
				<!--   <tr><th>地址信息</th><th colspan="5"><hr></th></tr> -->
				    <tr colspan="4"><th colspan="4"><div class="cap"> 地址信息
				     </div></th></tr> 
				 <tr>
					   <th>省市县（区）</th>
					   
						<td>
						<input type="hidden" name="ssxqdm" value="${jwry.ssxqdm}" />
						<input type="text" id="ssxqdm_mc" name="ssxqdm_mc"  value="${jwry.ssxqdm_mc}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly" />
						</td>
					      <th>所属街路巷(小区)_地址编码</th>
					      <input type="hidden" name="ssjlxxqDzbm" value="${jwry.ssjlxxqDzbm}" />
					     <td><input type="text" id="ssjlxxqDzbm_mc" name="ssjlxxqDzbm_mc"  value="${jwry.ssjlxxqDzbm_mc}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly"  /></td> 
					      
					 
					 </tr>
					 <tr>
					
					  <tr>
					    <th>现居住地详细地址</th>
					  <td colspan="5"><input type="text"  id="xxdz" name="xxdz"  value="${jwry.xxdz}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,200]'" style="width: 600px;"/></td>
					 </tr>
					 
					  <tr>
					 <th>属地派出所</th>
					 <td> 
					 	<input type="hidden" name="sdpcs" value="${jwry.sdpcs}" />
					<input type="text" id="sdpcs_mc" name="sdpcs_mc" value="${jwry.sdpcs_mc}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td>
					 <th>警务责任区</th>
					<td> 
					 <input type="hidden" name="jwzrq" value="${jwry.jwzrq}" />
					<input type="text" id="jwzrq_mc" name="jwzrq_mc"  value="${jwry.jwzrq_mc}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td>
					 </tr>
				 </table> 
			</fieldset>
			<div style="text-align: right;">
				<km:widgetTag widgetRulevalue="syfwJwry.saveSyFwJwryAccInfo">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()" style="width: 90px" >保 存</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="syfwJwry.saveSyFwJwryAccInfo">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()" style="width: 90px">取 消</a>
				</km:widgetTag>
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>