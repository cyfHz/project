	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="/static/meta/taglib.jsp" %> 
<script type="text/javascript">
$(function() {
    loadPic();
});
function loadPic(){
	var sfzh=$("#zjhm").val();
	imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=jwry&"+Math.floor(Math.random()*100);
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
<t:DataDict code="gj" var="gjDict"></t:DataDict>
<t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
<t:DataDict code="qzzl" var="qzzlDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 2px;">
		<form id="fm_jwryinfo" class="form-horizontal" method="post" action="${ctx}/psam/sy/syFwJwry/saveSyFwJwryAccInfo" enctype="multipart/form-data">
			<fieldset>
			 <input type="hidden" name="fjbm" id="fjbm" value="${jwry.fjbm}"/>
			     <input type="hidden" name="jzwid" id="jzwid" value="${jwry.jzwid}"/>
		       	<input type="hidden" name="dzbm" id="dzbm" value="${jwry.dzbm}">
		       	<input type="hidden" name="zjhm" id="zjhm" value="${jwry.zjhm}">
		       	<input type="hidden" name="lrfs" value="0">
		       	<legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />个人信息
				  </legend>
				   <!-- <tr><th>人员信息</th><th colspan="5"><hr></th></tr> -->
				 <table id="formTbale" cellpadding="4"  class="detail-table">
				 <tr></tr>
				 <tr>
				        <th>证件号码</th>
				        <td ><c:out value="${jwry.zjhm}" /></td>
				       <th>英文名</th>
				        <td ><c:out value="${jwry.ywm}" /></td>
				        	<th rowspan="4">照片</th>
					 	 	 <td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" >
					 			<img name="showimg1" id="showimg1" src="" width="116px" height="144px" alt="人口照片"/> 
					 		</div>
					 	 </td>
				 </tr>
				 	 <tr>
				 	   <th>英文姓</th>
				 	     <td ><c:out value="${jwry.ywx}"/></td>
				       <th>中文名</th>
				        <td ><c:out value="${jwry.zwm}"/></td>
				 </tr>
				 <tr>
				 <th>出生日期</th>
				  <td ><c:out value="${jwry.csrq}"/></td>
				  <th>证件种类</th>
					   <td>
					     <div class="form-group">
					          <km:dataDic dictCode="zjlx" value="${jwry.zjzl}"/>
								<%-- <input id="zjzl" dict="zjlxDict" disabled name="zjzl" value="${jwry.zjzl}" class="form-control"/> --%>
							</div> 
							</td>
				 </tr>
				  <tr>
				       <th>签证种类</th>
				        <td>
					     <div class="form-group">
					          <km:dataDic dictCode="qzzl" value="${jwry.qzzl}"/>
								<%-- <input id="qzzl" dict="qzzlDict" disabled name="qzzl" value="${jwry.qzzl}" class="form-control"/> --%>
							</div> 
							</td>
					     <th>签证编号</th>
					        <td ><c:out value="${jwry.qzbh}"/></td>
				 </tr>
				 <tr>
				        <th>国籍</th>
				        <td>
					     <div class="form-group">
					          <km:dataDic dictCode="gj" value="${jwry.gj}"/>
								<%-- <input id="gj" dict="gjDict" disabled name="gj" value="${jwry.gj}" class="form-control"/> --%>
							</div> 
							</td>
				       <th>境外人员联系电话</th>
				       <td ><c:out value="${jwry.jwrylxdh}"/></td>
				         <th>停留有效期</th>
				         <td >
				           <c:out value="${jwry.tlyxq}"/>
				         <%-- <input type="text" disabled name="tlyxq" value="${jwry.tlyxq}" class=" easyui-datebox form-control"/> --%>
				         </td>
				    </tr>
				    <tr></tr>
				    </table>
				    </fieldset>   
				    <br />
				     
				     <fieldset>
				        <legend>
					         <img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />房屋房主信息
				         </legend>
				 <table id="formTbale" cellpadding="4"  class="detail-table">
				   <!-- <tr><th>房屋房主信息</th><th colspan="5"><hr></th></tr> -->
					  <tr></tr>
					   <tr>
					   <th>房屋房主姓名</th>
					   <td ><c:out value="${jwry.fwfzxm}"/></td>
					      <th>房主身份证号</th>
					      	   <td ><c:out value="${jwry.fzsfzh}"/></td>
					 </tr>
					 <tr></tr>
					 </table>
				    </fieldset>  
				    <br />
				      
				     <fieldset>
				        <legend>
					         <img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />地址信息
				         </legend>
				 <table id="formTbale" cellpadding="4"  class="detail-table">
				<!--   <tr><th>地址信息</th><th colspan="5"><hr></th></tr> -->
					  <tr></tr>
					  <tr>
					    <th>房间号</th>
					    <td width="200px"><c:out value="${jwry.fjh}"/></td>
					    <th>现居住地详细地址</th>
					    <td><c:out value="${jwry.xxdz}"/></td>
					 </tr>
					 <tr></tr>
				 </table> 
			</fieldset>
			
		</form>
	</div>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>