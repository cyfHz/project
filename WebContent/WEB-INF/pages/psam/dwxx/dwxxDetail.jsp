<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ include file="/static/meta/easyuipub.jsp" %>
<script type="text/javascript">
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
	<input type="hidden" name="zagldwbm" value="${dwxx.zagldwbm}"/>
	<input type="hidden" name="ssfjbm" value="${dwxx.ssfjbm}"/>
	<input type="hidden" name="sssjbm" value="${dwxx.sssjbm}"/>
	
	<input type="hidden" name="dwdz"  value="${dwxx.dwdz}"   /><!--单位地址，暂用区划内详情  -->
<div class="etc">
	<table class="detail-table " cellpadding="0" cellspacing="0">
   <tr><td colspan="4"><div class="cap">单位基本信息</div></td></tr>
	 
    <tr>
	   <th>单位名称 <!-- <span class="span_main">*</span> --></th>
	   <td>
	     <c:out value="${dwxx.dwmc}"></c:out>
	   </td>
	   <th>营业执照号</th> 
	   <td>
	     <c:out value="${dwxx.yyzzh}"></c:out>
	   </td>
	</tr>
	
	<tr>
		<th>营业执照起始日期</th>
		<td> 
		    <fmt:formatDate value="${dwxx.yyzzyxq_qsrq}" type="both"/>
        </td>
		<th>营业执照截止日期</th>
		<td>
		  <fmt:formatDate value="${dwxx.yyzzyxq_jzrq}" type="both"/>
        </td>
	</tr>
	

	<tr>
	  <th>联系电话</th>
	  <td>
	     <c:out value="${dwxx.lxdh}"></c:out>
       </td>
      <th>注册资金</th>
	  <td>
	   <c:out value="${dwxx.zczb}"></c:out>
      </td>
    </tr>
    
    
    <tr>
      <th>经营面积（平方米）</th>
	  <td> 
	    <c:out value="${dwxx.jymj_mjpfm}"></c:out>
	  </td>
	  <th>经营方式</th>
	  <td>
	     <c:out value="${dwxx.jyfs}"></c:out>
	  </td>
    </tr>
    
    <tr>
	  <th>经营范围（主营）</th>
	  <td>
	    <c:out value="${dwxx.jyfwzy}"></c:out>
	  </td>
	   <th>经营范围（兼营）</th>
	  <td>
	    <c:out value="${dwxx.jyfwjy}"></c:out>
	  </td>
    </tr>
    
    <tr>
    <th>单位地址</th>
	 <td colspan="3">
	    <c:out value="${dwxx.dwdz_qhnxxdz}"></c:out>
	 </td>
	</tr>
	
    <tr>
      <th>网址</th>
	  <td colspan="3"> 
	    <c:out value="${dwxx.wz}"></c:out>
	  </td>
    </tr>
    
<tr><td colspan="4"><div class="cap">法人基本信息</div></td></tr>
	 
	 <tr>
	  <th>法人身份号码 <!-- <span class="span_main">*</span> --></th>
	  <td>
	    <c:out value="${dwxx.fddbr_gmsfhm}"></c:out>
	  </td>
	  <th>法人姓名</th>
	  <td>
	    <c:out value="${dwxx.fddbr_xm}"></c:out>
	  </td>
	</tr>

	<tr>
	   <th>法人外文姓</th> 
	   <td>
	     <c:out value="${dwxx.fddbr_wwm}"></c:out>
	  </td>
	   <th>法人外文名</th> 
	  <td>
	   <c:out value="${dwxx.fddbr_wwx}"></c:out>
	  </td>
	</tr>

	<tr>
	   <th>法人证件种类</th>
	   <td>
	     <km:dataDic dictCode="zjlx" value="${dwxx.fddbr_cyzjdm}"/>
	  </td>
	   <th>法人证件号码</th> 
	  <td>
	   <c:out value="${dwxx.fddbr_zjhm}"></c:out>
	  </td>
	</tr>

	<tr>
	   <th>法人联系电话</th> 
	   <td>
	     <c:out value="${dwxx.fddbr_lxdh}"></c:out>
	  </td>
	</tr>
	
	 
<tr><td colspan="4"><div class="cap">保卫负责人基本信息</div></td></tr>
	 
	 <tr>
	  <th>保卫身份号码<!--  <span class="span_main">*</span> --></th>
	  <td>
	    <c:out value="${dwxx.bwfzr_gmsfhm}"></c:out>
	  </td>
	  <th>保卫姓名</th>
	  <td>
	      <c:out value="${dwxx.bwfzr_xm}"></c:out>
	  </td>
	</tr>
	<tr>
	   <th>保卫联系电话</th> 
	   <td>
	    <c:out value="${dwxx.bwfzr_lxdh}"></c:out>
	  </td>
	</tr>
	
	
	  <tr><td colspan="4"><div class="cap">登记信息</div></td></tr>
	<tr>
      <th>属地派出所</th>
	  <td colspan="3">
	   <c:out value="${dwxx.sspcsmc}"></c:out>
	  </td>
	</tr>
	<tr>
	<th>警务责任区</th>
	<td colspan="3">
	    <c:out value="${dwxx.ssjwqmc}"></c:out>
	</td>
	</tr>
	<tr>
	</tr>
	</table>
</div>
 </form>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>