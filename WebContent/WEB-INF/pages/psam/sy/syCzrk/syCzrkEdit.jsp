<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script src="${pageContext.request.contextPath}/static/libs/jquery/jquery-form.js"></script>
<script type="text/javascript">
	 $(function() {
		  loadPic();			 
	});
 
	function doSubmit(){
		var formx = $("#fm_czrkinfo");
		if (!formx.form('validate')) {
			alertMsg.warn("请确认校验不通过数据");
			return false;
		}
		var options  = {    
	            url:"${ctx}/psam/sy/syCzrk/updateSyCzrkAccInfo",    
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
		var sfzh=$("#gmsfhm").val();
		//alert(sfzh);
	imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=czrk&"+Math.floor(Math.random()*100);
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
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="HYZK" var="hyzkDict"></t:DataDict>
<t:DataDict code="XL" var="xlDict"></t:DataDict>
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
 <t:DataDict code="rygx" var="rygxDict"></t:DataDict> 
  <t:DataDict code="xb" var="xbDict"></t:DataDict> 
<t:DataDict code="byzk" var="byzkDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px;">
		<form id="fm_czrkinfo"  method="post" action="${ctx}/psam/sy/syCzrk/updateSyCzrkAccInfo"  enctype="multipart/form-data">
			<input type="hidden" name="jzwfjid" id="jzwfjid" value="${czrk.jzwfjid}"/>
			 <input type="hidden" name="jzwid" value="${czrk.jzwid}"/>
			<input type="hidden" name="dzbm" value="${czrk.dzbm}">
		   	<input type="hidden" name="lrfs" value="0">
			 <input type="hidden" name="rkid" value="${czrk.rkid}">
			<fieldset>
				  <table id="formTbale" cellpadding="4">
				     <tr><th>个人信息</th><th colspan="5"><hr></th></tr>
					 <tr><th>身份证号</th>
						<td><input type="text" name="gmsfhm" id="gmsfhm" value="${czrk.gmsfhm}" readonly="readonly"  class="easyui-textbox easyui-validatebox  form-control" data-options="required:true,validType:'length[15,18]'" onchange="loadCzrkInfoBySfzh()" /></td>
					 	<th>姓名</th>
						<td><input type="text" name="xm" id="xm" value="${czrk.xm}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
					 	
						<th>照片</th>
					 <td rowspan="4">
					 	<!-- 	<div style="width: 120px;height: 128px;border: 1px solid red; margin-left: 30px;padding: 2px;" >
					 			<img name="showimg" id="showimg" src="" width="116px" height="125px" alt="人口照片"/> 
					 		</div> -->
					 			 <!-- <input name="imgfile" type="file" id="imgfile"  onchange="setImagePreview();" size="3" style="margin-left: 30px;" />  --> 
					 	         <div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" id="localImag" >
					 			<img name="showimg" id="showimg" src="" width="116px" height="144px" alt="人口照片"  class="showimg" /> 
					 		</div>
					 	          <input name="imgfile" type="file" id="imgfile" class="imgfile" onchange="setImagePreview();"  size="3" style="margin-left: 30px;" />
					 	<div style="color: red;font-size: 10px;">(照片格式：jpg,jpeg,png,gif,不能超过200K)</div>
					 	 </td>
					 </tr>
					 <tr>
					   <th>曾用名</th>
						<td><input type="text" name="zym" id="zym" value="${czrk.zym}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>
					   <th>性别</th>
					  <td >  <input dict="xbDict" name="xb" value="${czrk.xb}" class="form-control"  style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					  <tr>
					   	<th>出生日期</th>
						<td><input type="text" name="csrq" id="csrq" value="${czrk.csrq}" class="easyui-datebox easyui-validatebox form-control"  data-options="editable:false" style="width:170px;"/></td>
					     <th>民族</th>
						  <td><input dict="mzDict" name="mz"  class="form-control" value="${czrk.mz}"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					  <tr>
				   <th>手机</th>
					<td><input type="text" id="lxfssj" name="lxfssj"  value="${czrk.lxfssj}" class="easyui-numberbox easyui-validatebox form-control"  data-options="validType:'length[0,11]'" style="width:170px;"></td> 
				     <th>兵役状况</th>
					 <td>
				      <input dict="byzkDict" name="byzk" value="${czrk.byzk}" class="form-control"  style="width: 170px;" data-options="editable:false"/>
				     </td>
				  </tr>
				  <tr>
                      	<th>政治面貌</th>
						<td><input dict="zzmmDict" name="zzmm" value="${czrk.zzmm}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
						<th>婚姻状况</th>
						<td><input dict="hyzkDict" name="hyzk" value="${czrk.hyzk}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					  	<th>学历</th>
						<td><input dict="xlDict" name="xl" id="xl" value="${czrk.xl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					  </tr>
					 	 <tr>
					  <th>QQ</th>
					<td><input type="text" id="xxbcQq" name="xxbcQq" value="${czrk.xxbcQq}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" ></td> 
					 <th>微信</th>
					 <td><input type="text" id="xxbcWx" name="xxbcWx" value="${czrk.xxbcWx}"  class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td> 
				       	<th>微博</th>
						<td><input type="text" id="xxbcWb" name="xxbcWb"  value="${czrk.xxbcWb}"   class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td> 
					 </tr>
					 <tr>
					   <th>电子邮箱</th>
						<td><input type="text" id="xxbcDzyx" name="xxbcDzyx" value="${czrk.xxbcDzyx}"  class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'email'" /></td>
					 <th>与户主关系</th>
					 <td>
					 <input dict="rygxDict" name="yhzgx" value="${czrk.yhzgx}" class="form-control"  style="width: 170px;" data-options="editable:false"/></td>
					  <th>职业</th>
					<td><input type="text" id="zy" name="zy" value="${czrk.zy}"class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" ></td> 
					</tr>
					 <tr>
					
					 <th>现居住地址</th>
					 <td><input type="text" id="xjzdzz" name="xjzdzz" value="${czrk.xjzdzz}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,500]'" /></td> 
				       	<th>现居住地住址房间号</th>
						<td><input type="text" id="xjzdzzfjh" name="xjzdzzfjh" value="${czrk.xjzdzzfjh}"   class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td> 
					  <th>地址名称</th>
						<td >
							<input type="text" name="dzmc" value="${czrk.dzmc}"  class="easyui-textbox easyui-validatebox  form-control" data-options="validType:'length[0,200]'"/>
						</td>
					 </tr>
					 <tr>
					 
					   <th>门楼牌号</th>
					   <td><input type="text" id="mlph" name="mlph" value="${czrk.mlph}"  class="easyui-textbox easyui-validatebox form-control" readonly="readonly"  data-options="validType:'length[0,200]'" ></td> 
					   <th>门(楼)详址</th>
					   <td colspan="4"><input type="text"  id="mlxz" name="mlxz"  value="${czrk.mlxz}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly"  data-options="validType:'length[0,200]'" style="width: 480px;"/></td>
					 </tr>
					
				  </table>
			</fieldset>
			<div style=" position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="syCzrk.updateSyCzrkAccInfo"></km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()"  >保 存</a>
				
				<km:widgetTag widgetRulevalue="syCzrk.updateSyCzrkAccInfo"></km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()"  >取 消</a>
				
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>