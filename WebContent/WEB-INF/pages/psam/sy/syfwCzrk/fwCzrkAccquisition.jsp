<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<style type="text/css">
th{
    width: 20%;
}
</style>
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="HYZK" var="hyzkDict"></t:DataDict>
<t:DataDict code="XL" var="xlDict"></t:DataDict>
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
<t:DataDict code="rygx" var="rygxDict"></t:DataDict> 
<t:DataDict code="xb" var="xbDict"></t:DataDict> 
<t:DataDict code="byzk" var="byzkDict"></t:DataDict>

<div class="easyui-layout" data-options="fit:true,border:false">
<script src="${pageContext.request.contextPath}/static/libs/jquery/jquery-form.js"></script>

<script type="text/javascript">
	function doSubmit(){
		  var fzsfzhm=$("#gmsfhm").val();
		 
			if(isCardNo(fzsfzhm)==false){
				alertMsg.warn("身份证号格式不正确");return;
			}
		var formx = $("#fm_czrkinfo");
		if (!formx.form('validate')) {
			alertMsg.warn("请确认校验不通过数据");
			return false;
		}
		var options  = {    
	            url:"${ctx}/psam/sy/syfwCzrk/savefwCzrkAccInfo",    
	            type:'post', 
	            dataType:"json",
	            success:function(data){
	            	loadMask.close();
	            	if(data.statusCode=='200'){
	            		alertMsg.info(data.message);
	            		$("#dlg_czrk_accqui").dialog("close"); 
	            	}else{
	            		 alertMsg.error(data.message);
	            	}
	            	
	            },
	            error: KMCORE.ajaxError
	        };   
		loadMask.open(); 
		formx.ajaxSubmit(options);
	}
	function cancel(){
		$("#dlg_czrk_accqui").dialog("close"); 
		//$("#dlg_czrk_accqui").dialog("destroy");
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
    	
	function loadCzrkInfoBySfzh(){
		
		  var sfzh=$("#gmsfhm").val();
	
			if(isCardNo(sfzh)==false){
				alertMsg.warn("身份证号格式不正确");return;
			}
		    var jzwfjid=$("#jzwfjid").val();
			var ajaxUrl = "${ctx}/psam/sy/syfwCzrk/loadCzrkInfo";
			var param = { "sfzh" : sfzh,"jzwfjid":jzwfjid};
			loadMask.open();
			KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
				loadMask.close();
				if(data.statusCode=='300'){
					 alertMsg.error(data.message);
				}
				if(data.statusCode=='200'){
					/* $("#fm_czrkinfo").form("load",data.data); */
					$("#fm_czrkinfo").form("load",{
						"xm":data.data.xm,
						"zym":data.data.zym,
						"xb":data.data.xb,
						"csrq":data.data.csrq,
						"mz":data.data.mz,
						"lxfssj":data.data.lxfssj,
						"byzk":data.data.byzk,
					    "zzmm":data.data.zzmm,
					    "hyzk":data.data.hyzk,
					    "xl":data.data.xl,
					    "xxbcQq":data.data.xxbcQq,
					    "xxbcWx":data.data.xxbcWx,
					    "xxbcWb":data.data.xxbcWb,
					    "xxbcDzyx":data.data.xxbcDzyx,
					    "yhzgx":data.data.yhzgx,
					     "zy":data.data.zy,
					     "xxbcWb":data.data.xxbcWb,
					     "xjzdzz":data.data.xjzdzz,
					     "xjzdzzfjh":data.data.xjzdzzfjh,
					     "xxbcSfrhfl":data.data.xxbcSfrhfl,
					     "dzmc":data.data.dzmc
					});
					imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=czrk&"+Math.floor(Math.random()*100);
					$("#showimg").hide().attr("src",imageUrl).fadeIn();
				}
				if(data.statusCode=='202'){
					$("#fm_czrkinfo").form("load",{
						"xb":data.data.XB,
						"zym":data.data.CYM,
						"byzk":data.data.BYZK,
						"hyzk":data.data.HYZK,
						"xl":data.data.WHCD,
						"mz":data.data.MZ,
						"xm":data.data.XM,
						"csrq":data.data.CSRQ
						
					});
					imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=czrk&"+Math.floor(Math.random()*100);
					$("#showimg").hide().attr("src",imageUrl).fadeIn();
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

	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px;padding-left:25px;">
		<form id="fm_czrkinfo"  method="post" action="${ctx}/psam/sy/syfwCzrk/savefwCzrkAccInfo"  enctype="multipart/form-data">
			<input type="hidden" name="jzwfjid" id="jzwfjid" value="${czrk.jzwfjid}"/>
			 <input type="hidden" name="jzwid" value="${czrk.jzwid}"/>
			<input type="hidden" name="dzbm" value="${czrk.dzbm}">
		   	<input type="hidden" name="lrfs" value="0">
			<%-- <input type="hidden" name="rkid" value="${czrk.rkid}"> --%>
			<fieldset>
				  <table id="formTbale" cellpadding="4" >
				     
				  <tr colspan="4"><th colspan="4"><div class="cap"> 个人信息
				     </div></th></tr> 
				     <tr>
				   				     
					    <th>身份证号 <span class="span_main">*</span></th>
						<td><input type="text" name="gmsfhm" id="gmsfhm" value="${czrk.gmsfhm}"  class="easyui-validatebox  easyui-textbox  form-control" data-options="required:true,validType:'length[15,18]'" onchange="loadCzrkInfoBySfzh()" /></td>
					      <th>照片</th>
					 	 <td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" id="localImag" >
					 			<img name="showimg" id="showimg" src="" width="116px" height="144px" alt="人口照片"  class="showimg" /> 
					 		</div>
					 		<input name="imgfile" type="file" id="imgfile" class="imgfile" onchange="setImagePreview();"  size="3" style="margin-left: 30px;" />
					 	    <div style="color: red;font-size: 10px;">(照片格式：jpg,jpeg,png,gif,不能超过200K)</div>
					 	 </td>
					    
					   </tr>
					<tr>
					 	<th>姓名 <span class="span_main">*</span></th>
						<td><input type="text" name="xm" id="xm" value="${czrk.xm}"  class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
					</tr>
					<tr>	
						  <th>曾用名</th>
						<td><input type="text" name="zym" id="zym" value="${czrk.zym}" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>
					
					 </tr>
					 
					 <tr>
					 
					   <th>性别</th>
					 
					  <td >  <input dict="xbDict" name="xb" value="${czrk.xb}" class="form-control"  style="width: 170px;" data-options="editable:false"/></td>
					   	</tr>
					<tr>
					    <th>兵役状况</th>
				     <td>
				      <input dict="byzkDict" name="byzk" value="${czrk.byzk}" class="form-control"  style="width: 170px;" data-options="editable:false"/>
				     </td>
					   	<th>出生日期</th>
						<td><input type="text" name="csrq" id="csrq" value="${czrk.csrq}" class="easyui-datebox easyui-validatebox form-control"  style="width:170px;"  data-options="editable:false"/></td>
					   	</tr>
					<tr>
					     <th>民族 <span class="span_main">*</span></th>
						  <td><input dict="mzDict" name="mz"  class="form-control" value="${czrk.mz}"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					      <th>政治面貌 <span class="span_main">*</span></th>
						<td><input dict="zzmmDict" name="zzmm" value="${czrk.zzmm}"  class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					 </tr>
					   <tr>
                      	
						<th>婚姻状况 <span class="span_main">*</span></th>
						<td><input dict="hyzkDict" name="hyzk" value="${czrk.hyzk}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					  	 <th>电子邮箱</th>
						<td><input type="text" id="xxbcDzyx" name="xxbcDzyx" value="${czrk.xxbcDzyx}"  class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'email'" /></td>
					  </tr>
					  <tr>
					  <th>学历 <span class="span_main">*</span></th>
						<td><input dict="xlDict" name="xl" id="xl" value="${czrk.xl}" class="form-control"  required="required" style="width: 170px;" data-options="editable:false"/></td>
					     <th>职业</th>
					<td><input type="text" id="zy" name="zy" value="${czrk.zy}"class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" ></td> 
					  </tr>
					  <tr>
				   <th>手机</th>
					<td><input type="text" id="lxfssj" name="lxfssj"  value="${czrk.lxfssj}" class="easyui-numberbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" style="width:170px;"></td> 
				      <th>QQ</th>
					<td><input type="text" id="xxbcQq" name="xxbcQq" value="${czrk.xxbcQq}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" ></td> 
				  </tr>
				 
					 	 <tr>
					 <th>微信</th>
					 <td><input type="text" id="xxbcWx" name="xxbcWx" value="${czrk.xxbcWx}"  class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,36]'" /></td> 
				       	<th>微博</th>
						<td><input type="text" id="xxbcWb" name="xxbcWb"  value="${czrk.xxbcWb}"   class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td> 
					 </tr>
					<tr>
					  <th>与户主关系</th>
					 <td>
					 <input dict="rygxDict" name="yhzgx" value="${czrk.yhzgx}" class="form-control"  style="width: 170px;" data-options="editable:false"/></td>
					 <th>是否人户分离</th>
					 <td>
                     <select name="xxbcSfrhfl" id="xxbcSfrhfl" style="width: 170px" class="easyui-combobox" data-options="editable:false">
			 		<option value="0"  <c:if test="${'0' eq czrk.xxbcSfrhfl  }">selected = "selected"</c:if>>否</option>
					<option value="1"  <c:if test="${'1' eq czrk.xxbcSfrhfl  }">selected = "selected"</c:if>>是</option>
					 </select>
					 </td>
					 </tr>
					 <tr>
					
					 <th>现居住地址</th>
					 <td><input type="text" id="xjzdzz" name="xjzdzz" value="${czrk.xjzdzz}" class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,500]'" /></td> 
				       	<th>现居住地住址房间号</th>
						<td><input type="text" id="xjzdzzfjh" name="xjzdzzfjh" value="${czrk.xjzdzzfjh}"   class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,50]'" /></td> 
					 
					 </tr>
					 <tr>
					  <th>地址名称</th>
						<td >
							<input type="text" name="dzmc" value="${czrk.dzmc}"  class="easyui-textbox easyui-validatebox  form-control" data-options="validType:'length[0,200]'"/>
						</td>
					  <th>门楼牌号</th>
					   <td><input type="text" id="mlph" name="mlph" value="${czrk.mlph}"  class="easyui-textbox easyui-validatebox form-control" readonly="readonly"  data-options="validType:'length[0,200]'" ></td> 
					 </tr>
					 
				<!-- 	<tr><th>地址信息</th><th colspan="4"><hr size=10 color=#5151A2></th></tr> -->
				      <tr colspan="4"><th colspan="4"><div class="cap"> 地址信息 </div></th></tr> 
					 <tr>
					 <th>所属县区</th>
					 <td>
					 <input type="hidden" name="ssxq" value="${czrk.ssxq}" />
					 <input type="text" id="ssxq_mc" name="ssxq_mc" value="${czrk.ssxq_mc}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly" >
					 </td>
					  <th>街路巷(村)</th>
					    <input type="hidden" name="jlxc" value="${czrk.jlxc}" />
					   <td><input type="text" id="jlxc_mc" name="jlxc_mc" value="${czrk.jlxc_mc }"   class="easyui-textbox easyui-validatebox form-control"  readonly="readonly"></td>  
					
					
					 
					 </tr>
					 <tr>
					   <th>门(楼)详址</th>
					   <td colspan="5"><input type="text"  id="mlxz" name="mlxz"  value="${czrk.mlxz}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly"  data-options="validType:'length[0,200]'" style="width: 600px;"/></td>
					 </tr>
					
					<tr>
					    <th>所属派出所</th>
						<td>
						<input type="hidden" name="sspcs" value="${czrk.sspcs}" />
						<input type="text" name="pcs_mc" value="${czrk.pcs_mc}" readonly="readonly" class="easyui-textbox  form-control" />
						
						</td>
						<th>警务责任区</th>
						<td>
							<input type="hidden" name="jwzrqdm" value="${czrk.jwzrqdm}" />
							<input type="text" name="jwzrq_mc"  value="${czrk.jwzrq_mc}" class="easyui-textbox form-control" />
						</td>
					</tr>  
				  </table>
			</fieldset>
			<div  style="text-align: right;" >
				<km:widgetTag widgetRulevalue="syfwCzrk.saveFwjbxxAccInfo"></km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="doSubmit()" style="width: 90px" >保 存</a>
				
				<km:widgetTag widgetRulevalue="syfwCzrk.saveFwjbxxAccInfo"></km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()" style="width: 90px">取 消</a>
				
			</div>
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>