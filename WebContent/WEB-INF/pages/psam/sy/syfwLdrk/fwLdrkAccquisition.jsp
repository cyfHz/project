<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<style type="text/css">
th{
    width: 20%;
}
</style>
 <script src="${pageContext.request.contextPath}/static/libs/jquery/jquery-form.js"></script>
<script type="text/javascript">
  function doSubmit(){
	  var fzsfzhm=$("#sfzh").val();
		if(isCardNo(fzsfzhm)==false){
			alertMsg.warn("身份证号格式不正确");return;
		}
	var formx=$("#fm_ldrkinfo");
	if(!formx.form('validate')){
		alertMsg.warn("请确认校验不通过数据");
		return false;
	}
	var options= {    
		   
            url:"${ctx}/psam/sy/syfwLdrk/saveSyFwLdrkAccInfo",    
            type:'post', 
            dataType:"json",
            success:function(data){
            	loadMask.close();
            	if(data.statusCode=='200'){
            		alertMsg.info(data.message);
            		 $("#dlg_ldrk_accqui").dialog("close");
            	
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
	$("#dlg_ldrk_accqui").dialog("close"); 
}
function loadLdrkInfoBySfzh(){
	
     var sfzh=$("#sfzh").val();
		if(isCardNo(sfzh)==false){
			alertMsg.warn("身份证号格式不正确");return;
		}
    var jzwfjid=$("#fjbm").val();
	var ajaxUrl = "${ctx}/psam/sy/syfwLdrk/loadLdrkInfo";
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
			
			var ddbdsj=data.data.ddbdrq;
	        if(ddbdsj!=null && ddbdsj.length>10){
	        	ddbdsj=ddbdsj.substr(0, 10);
	        } 
			$("#fm_ldrkinfo").form("load",{
				"xm":data.data.xm,
				"bm":data.data.bm,
				"xb":data.data.xb,
				"csrq":csrq, 
				"mz":data.data.mz,
				"lxfsSj":data.data.lxfsSj,
				"fbyqk":data.data.fbyqk,
			    "zzmm":data.data.zzmm,
			    "xl":data.data.xl,
			    "hyzk":data.data.hyzk,
			    "jzsy":data.data.jzsy,
			    "njzqx":data.data.njzqx,
			    "ddbdrq":ddbdsj 
			    
			});
			imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=ldrk&"+Math.floor(Math.random()*100);
			$("#showimg2").hide().attr("src",imageUrl).fadeIn();
		}
		if(data.statusCode=='202'){
			var csrq=data.data.CSRQ;
			if(csrq!=null && csrq.length>10){
				csrq = csrq.substr(0, 10);
			} 
			$("#fm_ldrkinfo").form("load",{
				"xb":data.data.XB,
				"bm":data.data.CYM,
				"byzk":data.data.BYZK,
				"hyzk":data.data.HYZK,
				"xl":data.data.WHCD,
				"mz":data.data.MZ,
				"xm":data.data.XM,
			 	"csrq":csrq 
				
			});
			imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=ldrk&"+Math.floor(Math.random()*100);
			$("#showimg2").hide().attr("src",imageUrl).fadeIn();
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
	
function setImagePreview() {
	 var docObj=document.getElementById("imgfile_ldrk");
	 var imgObjPreview=document.getElementById("showimg2");
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
</script>
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="HYZK" var="hyzkDict"></t:DataDict>
<t:DataDict code="XL" var="xlDict"></t:DataDict>
<t:DataDict code="JZSY" var="jzsyDict"></t:DataDict>
<t:DataDict code="NJZQX" var="njzqxDict"></t:DataDict>
 <t:DataDict code="xb" var="xbDict"></t:DataDict> 
   <t:DataDict code="byzk" var="byzkDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px; padding-left:25px">
		<form id="fm_ldrkinfo" method="post" action="${ctx}/psam/sy/syfwLdrk/saveSyFwLdrkAccInfo"  enctype="multipart/form-data">
			<fieldset>
				 <input type="hidden" name="fjbm"  id="fjbm" value="${ldrk.fjbm}"/>
			       <input type="hidden" name="jzwid" value="${ldrk.jzwid}"/>
		        	<input type="hidden" name="dzbm" value="${ldrk.dzbm}">
		       	 	<%-- <input type="hidden" name="ldid" id="ldid" value="${ldrk.ldid}"> --%>
		       	 	<%-- <input type="hidden" name="xlh" value="${ldrk.xlh}"> --%>
		       	 	<input type="hidden" name="lrfs" value="0">
				 <table id="formTbale" class="et" cellpadding="0" cellspacing="0">
				  <tr><th colspan="4"><div class="cap"> 个人信息
				     </div></th></tr> 
				<!--  <tr><th>个人信息</th><th colspan="5"><hr></th></tr> -->
				    <tr>
				        <th>公民身份证号 <span class="span_main">*</span></th>
						<td><input type="text" name="sfzh" id="sfzh" value="${ldrk.sfzh}"   class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,20]'" onchange="loadLdrkInfoBySfzh()"/></td>
					 	<th>照片</th>
					 	 <td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" id="localImag">
					 			<img name="showimg" id="showimg2" src="" width="116px" height="144px" alt="人口照片" /> 
					 		</div>
					 			<input name="imgfile" type="file" id="imgfile_ldrk"  onchange="setImagePreview();" size="3" style="margin-left: 30px;" /> 
					 	       <div style="color: red;font-size: 10px;">(照片格式：jpg,jpeg,png,gif,不能超过200K)</div>
					 	 </td>
					 	
					 	
					 	
					 </tr>
					 <tr>
					  <th>姓名 <span class="span_main">*</span></th>
						<td><input type="text" name="xm" id="xm"  value="${ldrk.xm}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
					 	
					 </tr>
					 <tr>
					     <th>别名</th>
					 	<td><input type="text" name="bm" id="bm"  value="${ldrk.bm}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>	
					     </tr>
					 <tr>
					     <th>性别</th>
					       <td >  <input dict="xbDict" name="xb" value="${ldrk.xb}" class="form-control"  style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					  <tr>
					 <th>民族 <span class="span_main">*</span></th>
					    <td><input dict="mzDict" name="mz"  value="${ldrk.mz}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 
					      <th>政治面貌 <span class="span_main">*</span></th>
					     	<td><input dict="zzmmDict" name="zzmm"   value="${ldrk.zzmm}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					 <tr>
					   <th>出生日期</th>
						<td><input type="text" id="csrq" name="csrq"   value="${ldrk.csrq}"  class="easyui-datebox easyui-validatebox form-control " style="width:170px;" data-options="editable:false"></td> 				
                        <th>手机</th>
						<td><input type="text" id="lxfsSj" name="lxfsSj"  value="${ldrk.lxfsSj}" class="easyui-numberbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" style="width:170px;" ></td> 
                      </tr>
                       <tr>
                      <th>到达本地日期</th> 
						<td><input type="text" id="ddbdrq" name="ddbdrq" value="${ldrk.ddbdrq}" class="easyui-datebox easyui-validatebox form-control " style="width:170px;" data-options="editable:false"  /></td>  
                       <th>是否申领居住证</th>
						<td>
						<select name="sfsljzz" style="width:170px;" class="form-control easyui-combobox" data-options="editable:false">
						<option value="1" <c:if test="${'1' eq ldrk.sfsljzz  }">selected = "selected"</c:if>>是</option>
						<option value="0" <c:if test="${'0' eq ldrk.sfsljzz  }">selected = "selected"</c:if>>否</option>
						</select>
						</td>
                    </tr>
					 <tr>
					  <th>婚姻状况 <span class="span_main">*</span></th>
					      <td><input dict="hyzkDict" name="hyzk" value="${ldrk.hyzk}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					  <th>学历 <span class="span_main">*</span></th>
					  	<td><input dict="xlDict" name="xl" value="${ldrk.xl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					  
					 </tr>
					 <tr>
					  <th>服兵役情况</th>
					   	<td><input dict="byzkDict" name="fbyqk" value="${ldrk.fbyqk}" class="form-control"  style="width: 170px;"data-options="editable:false" /></td>
					 </tr>
					  <tr>
					  <th>居住事由 <span class="span_main">*</span></th>
					     <td><input dict="jzsyDict" name="jzsy"  class="form-control" value="${ldrk.jzsy}"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 <th>拟居住期限 <span class="span_main">*</span></th>
					 
					   <td><input dict="njzqxDict" name="njzqx" value="${ldrk.njzqx}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
						<%-- <td><input type="text" id="njzqx" name="njzqx" value="${ldrk.njzqx}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,20]'" /></td> --%>
					 </tr>
					  <tr ><th colspan="4"><div class="cap"> 地址信息
				     </div></th></tr> 
					 	 <tr>
					<%--   <th>乡镇街道</th>
					 <td><input type="text" name="xzjd"  id="xzjd" value="${ldrk.xzjd}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,200]'" /></td> --%>
					 <%-- <th>社区居村委会</th> 
						<td><input type="text" id="sqjcwh" name="sqjcwh" value="${ldrk.sqjcwh}" class="easyui-textbox easyui-validatebox form-control"  data-options=" validType:'length[0,200]'" /></td>  --%>
					  <th>街路巷小区</th> 
						<td><input type="text" id="jlxxq" name="jlxxq" value="${ldrk.jlxxq}" class="easyui-textbox easyui-validatebox form-control" readonly="readonly" data-options=" validType:'length[0,200]'" /></td> 
						<th>单元号</th> 
						<td><input type="text" id="dyh" name="dyh" value="${ldrk.dyh}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly" data-options=" validType:'length[0,200]'" /></td> 
					 </tr>
					  	 <tr>
					  	
					  <th>楼号</th>
					 <td><input type="text" name="lh"  id="lh" value="${ldrk.lh}" class="easyui-textbox easyui-validatebox form-control" readonly="readonly" data-options="validType:'length[0,200]'" /></td>
					 <th>房号</th> 
						<td><input type="text" id="fh" name="fh" value="${ldrk.fh}" class="easyui-textbox easyui-validatebox form-control" readonly="readonly" data-options=" validType:'length[0,200]'" /></td> 
					  
					 </tr>
					 <tr>
					  <th>详细地址</th> 
					      <td colspan="5"><input type="text"  id="xxdz" name="xxdz"  value="${ldrk.xxdz}" class="easyui-textbox easyui-validatebox form-control" readonly="readonly" data-options="validType:'length[0,200]'" style="width: 470px;"/></td>
					 </tr>
					 <tr>
					 <th>所属警务区</th>
					<td>
						<input type="hidden" name="ssjwq" value="${ldrk.ssjwq}" />
					 <input type="text" id="ssjwq_mc" name="ssjwq_mc" value="${ldrk.ssjwq_mc}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" />
					 </td>
					 <th>所属派出所</th>
					<td>
					 	<input type="hidden" name="sspcs" value="${ldrk.sspcs}" />
					 <input type="text" id="sspcs_mc" name="sspcs_mc"  value="${ldrk.sspcs_mc}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" />
					 </td>
					 </tr> 
				 </table>
			</fieldset>
		 <div style="text-align: right;" ><!-- style="position: absolute;bottom: 5px;right: 10px;" -->
				<km:widgetTag widgetRulevalue="syfwLdrk.saveFwjbxxAccInfo"></km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()" style="width: 90px" >保 存</a>
				
				<km:widgetTag widgetRulevalue="syfwLdrk.saveFwjbxxAccInfo"></km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()" style="width: 90px">取 消</a>
			</div> 
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>