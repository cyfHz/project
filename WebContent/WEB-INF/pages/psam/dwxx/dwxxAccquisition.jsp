<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/easyui2.jsp" %> 
<%@ include file="/static/meta/easyuipub.jsp" %>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ include file="/static/meta/meta.jsp" %> 
<script type="text/javascript">
	function doSubmit(){
		var fddbr_gmsfhm=$("#fddbr_gmsfhm").val();
		if(PUBUtil.isStrHavaValue(fddbr_gmsfhm)){
			if(isCardNo(fddbr_gmsfhm)==false){
				alertMsg.warn("法人身份证号格式不正确");return;
			}
		}
		
		var bwfzr_gmsfhm=$("#bwfzr_gmsfhm").val();
		if(PUBUtil.isStrHavaValue(bwfzr_gmsfhm)){
			if(isCardNo(bwfzr_gmsfhm)==false){
				alertMsg.warn("保卫负责人身份证号格式不正确");return;
			}
		}
		
		if(!KMAJAX.validateFromCallback($("#fm_fwinfo"),function(json){
			if(json.statusCode == KMCORE.statusCode.ok){
				alertMsg.correct(json.message);
// 				$("#dlg_DwxxInfo").dialog("close");
			}else{
				alertMsg.error(json.message);
			}
		})){
			alertMsg.warn("请确认校验不通过数据");return;
		}
	}
	function cancel(){
// 		var a =$("#dlg_DwxxInfo",parent.document).click();
        var a=window.parent;
        var aa=a.document.getElementById("dlg_DwxxInfo");
        
		console.log(aa);
// 		$("#dlg_DwxxInfo").dialog("close"); 
	}
	
	function frSfzhIsCardNo(){
		var fddbr_gmsfhm=$("#fddbr_gmsfhm").val();
		if(PUBUtil.isStrHavaValue(fddbr_gmsfhm)){
			if(isCardNo(fddbr_gmsfhm)==false){
				alertMsg.warn("法人身份证号格式不正确");return;
			}
		}
	}
	
	function bwSfzhIsCardNo(){
		var bwfzr_gmsfhm=$("#bwfzr_gmsfhm").val();
		if(PUBUtil.isStrHavaValue(bwfzr_gmsfhm)){
			if(isCardNo(bwfzr_gmsfhm)==false){
				alertMsg.warn("保卫负责人身份证号格式不正确");return;
			}
		}
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
<t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<style>
tr{
	line-height: 25px;
}
th{
    width: 20%;
}
</style>

<form id="fm_fwinfo" method="post" action="${pageContext.request.contextPath}/psam/dwxx/saveDwjbxxAccInfo" >
	<input type="hidden" name="fjid" value="${dwxx.fjid}"/>
	<input type="hidden" name="jzwid" value="${dwxx.jzwid}"/>
	<input type="hidden" id="zagldwbm" name="zagldwbm" value="${dwxx.zagldwbm}"/>
	<input type="hidden" name="ssfjbm" value="${dwxx.ssfjbm}"/>
	<input type="hidden" name="sssjbm" value="${dwxx.sssjbm}"/>
	
	<input type="hidden" name="dwdz"  value="${dwxx.dwdz}"   /><!--单位地址，暂用区划内详情  -->
<div class="etc">
	<table class="et" cellpadding="0" cellspacing="0">
	<tr><td colspan="4"><div class="cap">房屋信息</div></td></tr>
	<tr>		
	<th>省市县</th>
	<td>
		<input type="hidden" name="dwdz_ssxqdm" value="${dwxx.dwdz_ssxqdm }" />
		<input type="text" name="ssxq_mc" value="${dwxx.ssxq_mc}" class="easyui-textbox form-control" readonly="readonly" />
	</td>
	<%-- <th>房间号:</th> 
	<td>
		<input class="easyui-textbox form-control" type="text" name="fjh"  value="${dwxx.fjh }" readonly="readonly" />
	</td> --%>
	</tr>
	
  <%--  <tr>
     <th>所属街路巷(小区)</th>
	 <td colspan="3">
		<input type="hidden" name="ssjlxxq_dzbm" value="${fwjbxx.ssjlxxq_dzbm }" />
		<input type="text" name="ssjlxxq_mc" value="${fwjbxx.ssjlxxq_mc }"  class="easyui-textbox easyui-validatebox form-control" readonly="readonly" />
	 </td>
   </tr> --%>
   <tr>
     <th>区划内地址详细地址</th>
	 <td colspan="3">
		<input type="text" name="dwdz_qhnxxdz" value="${dwxx.dwdz_qhnxxdz}" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,200]'"   style="width: 400px;"/>
	 </td>
   </tr>
   
   <tr><td colspan="4"><div class="cap">单位基本信息</div></td></tr>
	 
    <tr>
	   <th>单位名称 <!-- <span class="span_main">*</span> --></th>
	   <td><input type="text" name="dwmc" value="${dwxx.dwmc}" class="easyui-textbox  easyui-validatebox form-control" data-options="required:true,validType:'length[1,200]'" /></td>
	   <th>营业执照号</th> 
	   <td><input  type="text" name="yyzzh" value="${dwxx.yyzzh}" data-options="validType:'length[0,100]'" class="easyui-textbox form-control"/></td>
	</tr>
	
	<tr>
		<th>营业执照起始日期</th>
		<td><input type="text" id="yyzzyxq_qsrq" name="yyzzyxq_qsrq" value="<fmt:formatDate value="${dwxx.yyzzyxq_qsrq}" type="both"/>" class="easyui-datetimebox easyui-validatebox form-control"  data-options="editable:false" style="width:170px;"></td> 	
		<th>营业执照截止日期</th>
		<td>
		<input type="text" id="yyzzyxq_jzrq" name="yyzzyxq_jzrq" value="<fmt:formatDate value="${dwxx.yyzzyxq_jzrq}" type="both"/>" class="easyui-datetimebox easyui-validatebox form-control"  data-options="editable:false" style="width:170px;"></td>
	</tr>
	
	<%-- <tr>
	   <th>单位英文名称<span class="span_main">*</span></th>
	   <td><input type="text" id="dwywmc" name="dwywmc" value="${dwxx.dwywmc}"  class="easyui-textbox  easyui-validatebox form-control"  data-options="validType:'length[15,18]'"/></td>
	   <th>单位英文缩写</th> 
	   <td><input  type="text" name="dwywsx" value="${dwxx.dwywsx}" data-options="validType:'length[0,20]'" class="easyui-textbox form-control"/></td>
	</tr> --%>

	<tr>
	  <%-- <th>单位地址</th>
	  <td><input type="text" name="dwdz"  value="${dwxx.dwdz}"  data-options="validType:'length[0,20]'"  class="easyui-textbox form-control" /></td> --%>
	  <th>联系电话</th>
	  <td><input type="text" name="lxdh" id="lxdh" value="${dwxx.lxdh}"  class="easyui-textbox  easyui-validatebox form-control" data-options="validType:'length[0,50]'" /></td>
      <th>注册资金</th>
	  <td><input type="text" name="zczb" value="${dwxx.zczb}" data-options="validType:'length[0,50]'"  class="easyui-textbox  easyui-validatebox  form-control" /></td>
    </tr>
    
    
    <tr>
      <th>经营面积（平方米）</th>
	  <td> 
	   <input type="text" name="jymj_mjpfm"  value="${dwxx.jymj_mjpfm}"  data-options="validType:'length[0,20]'"  class="easyui-textbox form-control" />
	  </td>
	  <th>经营方式</th>
	  <td><input type="text" name="jyfs"  value="${dwxx.jyfs}"  data-options="validType:'length[0,100]'"  class="easyui-textbox form-control" /></td>
    </tr>
    
    <tr>
	  <th>经营范围（主营）</th>
	  <td><input type="text" name="jyfwzy"  value="${dwxx.jyfwzy}"  data-options="validType:'length[0,50]'"  class="easyui-textbox form-control" /></td>
	   <th>经营范围（兼营）</th>
	  <td><input type="text" name="jyfwjy"  value="${dwxx.jyfwjy}"  data-options="validType:'length[0,50]'"  class="easyui-textbox form-control" /></td>
    </tr>
    
    <tr>
      <th>网址</th>
	  <td> 
	   <input type="text" name="wz"  value="${dwxx.wz}"  data-options="validType:'length[0,100]'"  class="easyui-textbox form-control" />
	  </td>
    </tr>
    
<tr><td colspan="4"><div class="cap">法人基本信息</div></td></tr>
	 
	 <tr>
	  <th>法人身份号码 <!-- <span class="span_main">*</span> --></th>
	  <td><input type="text" id="fddbr_gmsfhm" name="fddbr_gmsfhm" value="${dwxx.fddbr_gmsfhm}" onblur="frSfzhIsCardNo()" class="easyui-textbox  easyui-validatebox form-control" data-options="validType:'length[15,18]'" /></td>
	  <th>法人姓名</th>
	  <td><input type="text" id="fddbr_xm" name="fddbr_xm" value="${dwxx.fddbr_xm}"  class="easyui-textbox  easyui-validatebox form-control"  data-options="validType:'length[0,50]'"/></td>
	</tr>

	<tr>
	   <th>法人外文姓</th> 
	   <td><input  type="text" name="fddbr_wwm" value="${dwxx.fddbr_wwm}" data-options="validType:'length[0,20]'" class="easyui-textbox form-control"/></td>
	   <th>法人外文名</th> 
	  <td><input  type="text" name="fddbr_wwx" value="${dwxx.fddbr_wwx}" data-options="validType:'length[0,20]'" class="easyui-textbox form-control"/></td>
	</tr>

	<tr>
	   <th>法人证件种类</th>
	   <td><input   dict="zjlxDict" name="fddbr_cyzjdm" value="${ dwxx.fddbr_cyzjdm}" data-options="editable:false,validType:'length[0,50]'" style="width: 170px;" class=" form-control"/></td>
	   <th>法人证件号码</th> 
	  <td><input  type="text" name="fddbr_zjhm" value="${dwxx.fddbr_zjhm}" data-options="validType:'length[0,50]'" class="easyui-textbox form-control"/></td>
	</tr>

	<tr>
	   <th>法人联系电话</th> 
	   <td><input  type="text" name="fddbr_lxdh" value="${dwxx.fddbr_lxdh}" data-options="validType:'length[0,50]'" class="easyui-textbox form-control"/></td>
	</tr>
	
	 
<tr><td colspan="4"><div class="cap">保卫负责人基本信息</div></td></tr>
	 
	 <tr>
	  <th>保卫身份号码<!--  <span class="span_main">*</span> --></th>
	  <td><input type="text" id="bwfzr_gmsfhm" onblur="bwSfzhIsCardNo()" name="bwfzr_gmsfhm" value="${dwxx.bwfzr_gmsfhm}" class="easyui-textbox  easyui-validatebox form-control" data-options="validType:'length[15,18]'" /></td>
	  <th>保卫姓名</th>
	  <td><input type="text" id="bwfzr_xm" name="bwfzr_xm" value="${dwxx.bwfzr_xm}"  class="easyui-textbox  easyui-validatebox form-control"  data-options="validType:'length[0,50]'"/></td>
	</tr>
	<tr>
	   <th>保卫联系电话</th> 
	   <td><input  type="text" name="bwfzr_lxdh" value="${dwxx.bwfzr_lxdh}" data-options="validType:'length[0,50]'" class="easyui-textbox form-control"/></td>
	</tr>
	
	
	  <tr><td colspan="4"><div class="cap">登记信息</div></td></tr>
	<tr>
      <th>属地派出所</th>
	  <td colspan="3">
	  <input type="hidden" name="sspcsbm" value="${dwxx.sspcsbm}" />
	  <input type="text" name="sspcsmc" value="${dwxx.sspcsmc}" readonly="readonly" class="easyui-textbox  form-control" style="width: 400px;"/>
	  </td>
	</tr>
	<tr>
	<th>警务责任区</th>
	<td colspan="3">
		<input type="hidden" name="ssjwqbm" value="${dwxx.ssjwqbm}" />
		<input type="text" name="ssjwqmc"  value="${dwxx.ssjwqmc}" class="easyui-textbox" style="width: 400px"/>
	</td>
	</tr>
	<tr>
	</tr>
	</table>
</div>
 </form>
	<div id="dlg_fwInfo_buttons" style="text-align: right;">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="doSubmit()" style="width: 90px">确认</a>
<!-- 		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()" style="width: 90px">关闭</a> -->
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>