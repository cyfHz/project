	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="/static/meta/taglib.jsp" %> 
  <script src="${pageContext.request.contextPath}/static/libs/jquery/jquery-form.js"></script>
<script type="text/javascript">
	$(function() {
		 loadPic();
	});
	function doSubmit(){
		var sfzh=$("#zjhm").val();
		var formx = $("#fm_jwryinfo");
		if (!formx.form('validate')) {
			alertMsg.warn("请确认校验不通过数据");
			return false;
		}
		var options  = {  
	            url:"${ctx}/psam/sy/syJwry/updateSyJwryAccInfo",    
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
		var sfzh=$("#zjhm").val();
		imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=jwry&"+Math.floor(Math.random()*100);
		$("#showimg").hide().attr("src",imageUrl).fadeIn();
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
	 function cancel(){
		 editDialog.close(100);
		}
</script>
<t:DataDict code="gj" var="gjDict"></t:DataDict>
<t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
<t:DataDict code="qzzl" var="qzzlDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px;">
		<form id="fm_jwryinfo"  method="post" action="${ctx}/psam/sy/syJwry/updateSyJwryAccInfo" enctype="multipart/form-data">
			<fieldset>
			 <input type="hidden" name="fjbm" id="fjbm" value="${jwry.fjbm}"/>
			     <input type="hidden" name="jzwid" value="${jwry.jzwid}"/>
		       	<input type="hidden" name="dzbm" value="${jwry.dzbm}">
		      <input type="hidden" name="jwryid" value="${jwry.jwryid}">
		       	<input type="hidden" name="lrfs" value="0">
				   
				 <table id="formTbale" cellpadding="4">
				 <tr><th>人员信息</th><th colspan="5"><hr></th></tr>
				 <tr>
				        <th>证件号码</th>
						<td><input type="text" name="zjhm" id="zjhm" value="${jwry.zjhm}" class="easyui-textbox easyui-validatebox form-control" readonly="readonly" data-options="required:true,validType:'length[0,50]'" onblur="loadJwryInfo()"/></td>
				       <th>英文名</th>
						<td><input type="text" name="ywm" id="ywm" value="${jwry.ywm}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
				        
				        	<th>照片</th>
					 	<!--  	 <td rowspan="4">
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
				 	   <th>英文姓</th>
						<td><input type="text" name="ywx" id="ywx" value="${jwry.ywx}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
				       <th>中文名</th>
						<td><input type="text" name="zwm" id="zwm" value="${jwry.zwm}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>	
				        
				        	
				 </tr>
				 <tr>
				 <th>出生日期</th>
						<td><input type="text" id="csrq" name="csrq" value="${jwry.csrq}" class="easyui-datebox easyui-validatebox form-control"  data-options="editable:false" style="width:170px;"></td> 	
				  <th>证件种类</th>
				  <td>
				  <input dict="zjlxDict" name="zjzl" value="${jwry.zjzl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
						<%-- <td><input type="text" name="zjzl" id="zjzl" value="${jwry.zjzl}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[0,36]'" /></td> --%>
					
				 </tr>
				  <tr>
				       <th>签证种类</th>
				        <td> <input dict="qzzlDict" name="qzzl" value="${jwry.qzzl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
						<%-- <td><input type="text" id="qzzl" name="qzzl"  value="${jwry.qzzl}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td> --%>
					     <th>签证编号</th>
					     <td><input type="text" id="qzbh" name="qzbh"  value="${jwry.qzbh}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td> 
				 </tr>
				 <tr>
				        <th>国籍</th>
				          <td> <input dict="gjDict" name="gj" value="${jwry.gj}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 	 <!-- <td><input type="text" id="gj" name="gj" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" ></td> 	 -->
				       <th>境外人员联系电话</th>
						<td><input type="text" name="jwrylxdh" id="jwrylxdh" value="${jwry.jwrylxdh}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>
				         <th>停留有效期</th>
						<td><input type="text" id="tlyxq" name="tlyxq" value="${jwry.tlyxq}" class="easyui-datebox easyui-validatebox form-control"  data-options="editable:false" /></td>		
				   <tr><th>房屋房主信息</th><th colspan="5"><hr></th></tr>
					   <tr>
					   <th>房屋房主姓名</th>
						<td><input type="text" id="fwfzxm" name="fwfzxm"  value="${jwry.fwfzxm}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td>
					      <th>房主身份证号</th>
					     <td><input type="text" id="fzsfzh" name="fzsfzh"  value="${jwry.fzsfzh}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,20]'" /></td> 
					 </tr>
				 
				  <tr><th>地址信息</th><th colspan="5"><hr></th></tr>
					
					  <tr>
					   <th>房间号</th>
					     <td><input type="text" id="fjh" name="fjh"  value="${jwry.fjh}" class="easyui-textbox easyui-validatebox form-control"  data-options=" required:true,validType:'length[0,36]'" /></td> 
					    <th>现居住地详细地址</th>
					  <td colspan="4"><input type="text"  id="xxdz" name="xxdz"  value="${jwry.xxdz}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,200]'" style="width: 480px;"/></td>
					 </tr>
					 
				 </table> 
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="syJwry.updateSyJwryAccInfo">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()">保 存</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="syJwry.updateSyJwryAccInfo">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">取 消</a>
				</km:widgetTag>
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>