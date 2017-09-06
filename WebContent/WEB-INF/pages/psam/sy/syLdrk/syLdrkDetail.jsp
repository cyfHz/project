	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="/static/meta/taglib.jsp" %> 
<script type="text/javascript">
	$(function() {
		loadPic();
	});
	function loadPic(){
		var sfzh=$("#sfzh").val();
		imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=ldrk&"+Math.floor(Math.random()*100);
		$("#showimg1").hide().attr("src",imageUrl).fadeIn();
	}

	
</script>
<style>

table tr {
border-color:#9EB5D0;
border-collapse:collapse;
}
th{
width: 120px;
}
</style>
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="HYZK" var="hyzkDict"></t:DataDict>
<t:DataDict code="XL" var="xlDict"></t:DataDict>
<t:DataDict code="JZSY" var="jzsyDict"></t:DataDict>
<t:DataDict code="NJZQX" var="njzqxDict"></t:DataDict>
 <t:DataDict code="byzk" var="byzkDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow:scroll;padding: 10px;">
		<form id="formss" class="form-horizontal" method="post" action="${ctx}/psam/sy/syLdrk/updateSyLdrkAccInfo">
			<fieldset>
			     <input type="hidden" name="sfzh" id="sfzh" value="${ldrk.sfzh}">
				 <input type="hidden" name="fjbm" value="${ldrk.fjbm}"/>
			     <input type="hidden" name="jzwid" value="${ldrk.jzwid}"/>
		       	<input type="hidden" name="dzbm" value="${ldrk.dzbm}">
		       	 	<input type="hidden" name="ldid" value="${ldrk.ldid}">
		       	 	<input type="hidden" name="xlh" value="${ldrk.xlh}">
		       	 <legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />个人信息
				  </legend>
				 <table id="formTbale" cellpadding="4" class="detail-table">
				<!--  <tr><th>个人信息</th><th colspan="5"><hr></th></tr> -->
				    <tr></tr>
				    <tr>
					 	 <th>姓名</th>
					 	 <td ><c:out value="${ldrk.xm}" /></td>
					 	<th>别名</th>
					 	<td ><c:out value="${ldrk.bm}" /></td>
					 	<th rowspan="4">照片</th>
					 	 	<td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" >
					 			<img name="showimg1" id="showimg1" src="" width="116px" height="144px" alt="人口照片"/> 
					 		</div>
					 	 </td>
					 	
					 </tr>
					 <tr>
					 <th>公民身份证号</th>
					 <td ><c:out value="${ldrk.sfzh}" /></td>
					     <th>性别</th>
					        <td>
					     <div class="form-group">
					          <km:dataDic dictCode="xb" value="${ldrk.xb }"/>
								<%-- <input id="xb" dict="xbDict" disabled name="xb" value="${ldrk.xb }" class="form-control"/> --%>
							</div> 
					   </td>
					 </tr>
					  <tr>
					 <th>民族</th>
					      <td>
					     <div class="form-group">
					          <km:dataDic dictCode="mz" value="${ldrk.mz }"/>
								<%-- <input id="MZ" dict="mzDict" disabled name="mz" value="${ldrk.mz }" class="form-control"/> --%>
							</div> 
					   </td>
					      <th>政治面貌</th>
					          <td>
					     <div class="form-group">
					          <km:dataDic dictCode="ZZMM" value="${ldrk.zzmm }"/>
								<%-- <input id="zzmm" dict="zzmmDict" disabled name="zzmm" value="${ldrk.zzmm }" class="form-control"/> --%>
							</div> 
					   </td>
					 </tr>
					 <tr>
					   <th>出生日期</th>
					    <td ><c:out value="${ldrk.csrq}" /></td>
                        <th>手机</th>
                         <td ><c:out value="${ldrk.lxfsSj}" /></td>
                      </tr>
                       <tr>
                      <th>到达本地日期</th> 
                        <td ><c:out value="${ldrk.ddbdrq}" /></td>
                       <th>是否申领居住证</th>
						<td>
						    <c:if test="${'1' eq ldrk.sfsljzz  }">是</c:if>
						    <c:if test="${'0' eq ldrk.sfsljzz  }">否</c:if>
						<%-- <select name="sfsljzz" style="width:170px;" class="form-control">
						<option value="1" <c:if test="${'1' eq ldrk.sfsljzz  }">selected = "selected"</c:if>>是</option>
						<option value="0" <c:if test="${'0' eq ldrk.sfsljzz  }">selected = "selected"</c:if>>否</option>
						</select> --%>
						</td>
						<th>服兵役情况</th>
                        <td>
					        <div class="form-group">
					            <km:dataDic dictCode="byzk" value="${ldrk.fbyqk }"/>
								<%-- <input id="fbyqk" dict="byzkDict" disabled name="fbyqk" value="${ldrk.fbyqk}" class="form-control"/> --%>
							</div>
					    </td> 
                    </tr>
					 <tr>
					  <th>婚姻状况</th>
					   <td>
					     <div class="form-group">
					           <km:dataDic dictCode="HYZK" value="${ldrk.hyzk }"/>
								<%-- <input id="hyzk" dict="hyzkDict" disabled name="hyzk" value="${ldrk.hyzk}" class="form-control"/> --%>
							</div> 
					   </td>
					  <th>学历</th>
					          <td>
					     <div class="form-group">
					           <km:dataDic dictCode="XL" value="${ldrk.xl }"/>
								<%-- <input id="xl" dict="xlDict" disabled name="xl" value="${ldrk.xl}" class="form-control"/> --%>
							</div> 
					   </td>
					   <th>拟居住期限</th>
					         <td>
					     <div class="form-group">
					          <c:out value="${ldrk.njzqx}" />
					 			<%-- <input id="njzqx" dict="njzqxDict" disabled name="njzqx" value="${ldrk.njzqx}" class="form-control"/> --%>
							</div> 
					   </td>
					 </tr>
					  <tr>
					  <th>居住事由</th>
					         <td colspan="5">
					     <div class="form-group">
					          <km:dataDic dictCode="JZSY" value="${ldrk.jzsy }"/>
								<%-- <input id="jzsy" dict="jzsyDict" disabled name="jzsy" value="${ldrk.jzsy}" class="form-control"/> --%>
							</div> 
					   </td>
					 
					 </tr>
					 <tr></tr>
					 </table>
			</fieldset>
			<br />
              <fieldset>
					<legend>
					  <img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />地址信息
				    </legend>
				   <table id="formTbale" cellpadding="4" class="detail-table">
					 <!--  <tr><th>地址信息</th><th colspan="5"><hr></th></tr> -->
					  <tr></tr>
					  <tr>
					  	<th width="120px;">单元号</th> 
					  	   <td ><c:out value="${ldrk.dyh}" /></td>
					    <th>楼号</th>
					  	   <td ><c:out value="${ldrk.lh}" /></td>
					    <th>房号</th> 
					       <td ><c:out value="${ldrk.fh}" /></td>
					 </tr>
					 	 <tr>
					  <th>街路巷小区</th> 
					       <td colspan="2"><c:out value="${ldrk.jlxxq}" /></td>
						 <th>详细地址</th>
						   <td colspan="4" ><c:out value="${ldrk.xxdz}" /></td> 
					 </tr>
					  	<tr></tr> 
					 <%-- <tr>
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
					 </tr>  --%>
				 </table>
			</fieldset>
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>