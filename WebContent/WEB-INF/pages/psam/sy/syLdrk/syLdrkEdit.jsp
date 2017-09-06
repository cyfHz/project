<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="/static/meta/taglib.jsp" %> 
 <script src="${pageContext.request.contextPath}/static/libs/jquery/jquery-form.js"></script>
<script type="text/javascript">
$(function() {
	 loadPic();
});


function doSubmit(){
	var formx = $("#fm_ldrkinfo");
	if (!formx.form('validate')) {
		alertMsg.warn("请确认校验不通过数据");
		return false;
	}
	
	var options  = {    
           url:"${ctx}/psam/sy/syLdrk/updateSyLdrkAccInfo",    
           type:'post', 
           dataType:"json",
           success:function(data){
           	/* loadMask.close(); */
           	if(data.statusCode=='200'){
           		alertMsg.info(data.message);
           		editDialog.close(100);
           	}else{
           		 alertMsg.error(data.message);
           	}
           	
           },
           error: KMCORE.ajaxError
       };   
	/* loadMask.open(); */ 
	formx.ajaxSubmit(options);
}
function viewmypic(mypic) { 
	var ivalue=$("#imgfile").val();
	if (ivalue){ 
		mypic.src=ivalue; 
		mypic.style.display=""; 
		mypic.border=1; 
	} 
	}
function loadPic(){
 
	var sfzh=$("#sfzh").val();
	imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=ldrk&"+Math.floor(Math.random()*100);
	$("#showimg").hide().attr("src",imageUrl).fadeIn();
}
function cancel(){
	editDialog.close(100);
}
	function setImagePreview() {
	   	 var docObj=document.getElementById("imgfile");
	   	 var imgObjPreview=document.getElementById("showimg");
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
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px;">
		<form id="fm_ldrkinfo"  method="post" action="${ctx}/psam/sy/syLdrk/updateSyLdrkAccInfo" enctype="multipart/form-data">
			<fieldset>
				 <input type="hidden" name="fjbm"  id="fjbm" value="${ldrk.fjbm}"/>
			     <input type="hidden" name="jzwid" value="${ldrk.jzwid}"/>
		        	<input type="hidden" name="dzbm" value="${ldrk.dzbm}">
		       	 	<input type="hidden" name="ldid" value="${ldrk.ldid}">
		       	 	<input type="hidden" name="xlh" value="${ldrk.xlh}">
		       	 	<input type="hidden" name="lrfs" value="0">
				 <table id="formTbale" cellpadding="4">
				 <tr><th>个人信息</th><th colspan="5"><hr></th></tr>
				    <tr>
				        <th>公民身份证号</th>
						<td><input type="text" name="sfzh" id="sfzh" value="${ldrk.sfzh}" readonly="readonly"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,20]'" onblur="loadLdrkInfoBySfzh()"/></td>
					 	 <th>姓名</th>
						<td><input type="text" name="xm" id="xm"  value="${ldrk.xm}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
					 	
					 	<th>照片</th>
					 <!-- 	 <td rowspan="4">
					 		<div style="width: 120px;height: 128px;border: 1px solid red; margin-left: 30px;padding: 2px;" >
					 			<img name="showimg" id="showimg" src="" width="116px" height="125px" alt="人口照片"/> 
					 		</div>
					 			<input name="imgfile" type="file" id="imgfile"  onchange="viewmypic(showimg);" size="3" style="margin-left: 30px;" /> 
					 	 </td> -->
					 	 	 <td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" id="localImag" >
					 			<img name="showimg" id="showimg" src="" width="116px" height="144px" alt="人口照片"  class="showimg" /> 
					 		</div>
					 		<input name="imgfile" type="file" id="imgfile" class="imgfile" onchange="setImagePreview();"  size="3" style="margin-left: 30px;" />
					 	    <div style="color: red;font-size: 10px;">(照片格式：jpg,jpeg,png,gif,不能超过200K)</div>
					 	 </td>
					 	
					 </tr>
					 <tr>
					     <th>别名</th>
					 	<td><input type="text" name="bm" id="bm"  value="${ldrk.bm}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>	
					     <th>性别</th>
						  
						  <td><input dict="xbDict" name="xb"  value="${ldrk.xb}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td> 
					 </tr>
					  <tr>
					 <th>民族</th>
					    <td><input dict="mzDict" name="mz"  value="${ldrk.mz}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					      <th>政治面貌</th>
					     	<td><input dict="zzmmDict" name="zzmm"   value="${ldrk.zzmm}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					 <tr>
					   <th>出生日期</th>
						<td><input type="text" id="csrq" name="csrq"   value="${ldrk.csrq}"  class="easyui-datebox easyui-validatebox form-control"  data-options="editable:false" style="width:170px;" data-options="editable:false"></td> 				
                        <th>手机</th>
						<td><input type="text" id="lxfsSj" name="lxfsSj"  value="${ldrk.lxfsSj}" class="easyui-numberbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" style="width:170px;" ></td> 
                      </tr>
                       <tr>
                      <th>到达本地日期</th> 
						<td><input type="text" id="ddbdrq" name="ddbdrq" value="${ldrk.ddbdrq}" class="easyui-datebox easyui-validatebox form-control "  style="width:170px;" data-options="editable:false"/></td>  
                       <th>是否申领居住证</th>
						<td>
						<select name="sfsljzz" style="width:170px;" class="form-control" data-options="editable:false">
						<option value="1" <c:if test="${'1' eq ldrk.sfsljzz  }">selected = "selected"</c:if>>是</option>
						<option value="0" <c:if test="${'0' eq ldrk.sfsljzz  }">selected = "selected"</c:if>>否</option>
						</select>
						</td>
                    </tr>
					 <tr>
					  <th>婚姻状况</th>
					      <td><input dict="hyzkDict" name="hyzk" value="${ldrk.hyzk}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					  <th>学历</th>
					  	<td><input dict="xlDict" name="xl" value="${ldrk.xl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					   <th>服兵役情况</th>
                     <td>
                     <input dict="byzkDict" name="fbyqk" value="${ldrk.fbyqk}" class="form-control" style="width: 170px;" data-options="editable:false"/>
					 </tr>
					  <tr>
					  <th>居住事由</th>
					     <td><input dict="jzsyDict" name="jzsy"  class="form-control" value="${ldrk.jzsy}"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 <th>拟居住期限</th>
					 
					   <td><input dict="njzqxDict" name="njzqx" value="${ldrk.njzqx}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					  <tr><th>地址信息</th><th colspan="5"><hr></th></tr>
					 	 <tr>
					<%--   <th>乡镇街道</th>
					 <td><input type="text" name="xzjd"  id="xzjd" value="${ldrk.xzjd}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,200]'" /></td> --%>
					 <%-- <th>社区居村委会</th> 
						<td><input type="text" id="sqjcwh" name="sqjcwh" value="${ldrk.sqjcwh}" class="easyui-textbox easyui-validatebox form-control"  data-options=" validType:'length[0,200]'" /></td>  --%>
					  <th>街路巷小区</th> 
						<td><input type="text" id="jlxxq" name="jlxxq" value="${ldrk.jlxxq}" class="easyui-textbox easyui-validatebox form-control"  data-options=" validType:'length[0,200]'" /></td> 
						<th>单元号</th> 
						<td><input type="text" id="dyh" name="dyh" value="${ldrk.dyh}" class="easyui-textbox easyui-validatebox form-control"   data-options=" validType:'length[0,200]'" /></td> 
					 </tr>
					  	 <tr>
					  	
					  <th>楼号</th>
					 <td><input type="text" name="lh"  id="lh" value="${ldrk.lh}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,200]'" /></td>
					 <th>房号</th> 
						<td><input type="text" id="fh" name="fh" value="${ldrk.fh}" class="easyui-textbox easyui-validatebox form-control"  data-options=" validType:'length[0,200]'" /></td> 
					  
					 </tr>
					 <tr>
					  <th>详细地址</th> 
					      <td colspan="5"><input type="text"  id="xxdz" name="xxdz"  value="${ldrk.xxdz}" class="easyui-textbox easyui-validatebox form-control" required="required" data-options="validType:'length[0,200]'" style="width: 470px;"/></td>
					 </tr>
				 </table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="syLdrk.updateSyLdrkAccInfo">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="syLdrk.updateSyLdrkAccInfo">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
				</km:widgetTag>
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>