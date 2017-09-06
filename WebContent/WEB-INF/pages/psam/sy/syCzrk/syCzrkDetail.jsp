<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<script type="text/javascript">
	$(function() {
	    loadPic();
	});
	function loadPic(){
		var sfzh=$("#gmsfhm").val();
		imageUrl="${ctx}/psam/sy/syRkglPic/loadRkglPic?zjhm="+sfzh+"&"+"rklx=czrk&"+Math.floor(Math.random()*100);
		$("#showimg1").hide().attr("src",imageUrl).fadeIn();
	}

</script>
<style>

table tr {
border-color:#9EB5D0;
border-collapse:collapse;
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
	<div data-options="region:'center',border:false" title="" style="overflow-y:scroll;padding: 10px;">
		<form id="formss" class="form-horizontal" method="post" action="${ctx}/psam/sy/syfwCzrk/savefwCzrkAccInfo">
			<input type="hidden" name="jzwfjid" id="jzwfjid" value="${czrk.jzwfjid}"/>
			 <input type="hidden" name="jzwid" id="jzwid" value="${czrk.jzwid}"/>
			<input type="hidden" name="dzbm"  id="dzbm" value="${czrk.dzbm}">
			<input type="hidden" name="gmsfhm" id="gmsfhm" value="${czrk.gmsfhm}">
			<input type="hidden" name="rkid" id="rkid" value="${czrk.rkid}">
			<fieldset>
				  <table id="formTbale" cellpadding="4" class="detail-table" >
				  <legend>
					<img src="${ctx}/static/images/fromedit.png" style="margin-bottom: -3px;" />个人信息
				  </legend>
				     <tr>
				    <!--  <th>个人信息</th><th colspan="5"><hr></th> -->
				     </tr>
					 <tr>
					 	<th>姓名</th>
					 	 <td ><c:out value="${czrk.xm}" /></td>
					 	<th>曾用名</th>
					 	<td ><c:out value="${czrk.zym}" /></td>
			       	<th rowspan="4">照片</th> 
					 	<td rowspan="4">
					 		<div style="width: 120px;height: 148px;border: 1px solid red; margin-left: 30px;padding: 2px;" >
					 			<img name="showimg1" id="showimg1" src="" width="116px" height="144px" alt="人口照片"/> 
					 		</div>
					 	 </td>
					
					 </tr>
					 <tr>
					   <th>身份证号</th>
					   	<td ><c:out value="${czrk.gmsfhm }"/></td>
					   <th>性别</th>
					   <td>
					     <div class="form-group">
					         <km:dataDic dictCode="xb" value="${czrk.xb }"/>
								<%-- <input id="xb" dict="xbDict" disabled name="xb" value="${czrk.xb }" class="form-control"/> --%>
							</div> 
					   </td>
					  
					 </tr>
					  <tr>
					   	<th>出生日期</th>
					   	<td ><c:out  value="${czrk.csrq }"/></td>
					     <th>民族</th>
						  <td>
						   <div class="form-group">
						       <km:dataDic dictCode="MZ" value="${czrk.mz }"/>
								<%-- <input id="mz" dict="mzDict" disabled name="mz" value="${czrk.mz}" class="form-control"/> --%>
							</div> 
						  </td>
					 </tr>
					  <tr>
				   <th>手机</th>
				   	<td ><c:out  value="${czrk.lxfssj}"/></td>
				     <th>兵役状况</th>
				      <td>
				       <div class="form-group">
				             <km:dataDic dictCode="byzk" value="${czrk.byzk }"/>
								<%-- <input id="byzk" dict="byzkDict" disabled name="byzk" value="${czrk.byzk}" class="form-control"/> --%>
							</div>
				     </td>
				  </tr>
				  <tr>
                      	<th>政治面貌</th>
                      	  <td>
						   <div class="form-group">
						       <km:dataDic dictCode="ZZMM" value="${czrk.zzmm}"/>
								<%-- <input id="zzmm" dict="zzmmDict" disabled name="zzmm" value="${czrk.zzmm}" class="form-control"/> --%>
							</div> 
						  </td>
						<th>婚姻状况</th>
						<td>
						   <div class="form-group">
						       <km:dataDic dictCode="HYZK" value="${czrk.hyzk}"/>
								<%-- <input id="hyzk" dict="hyzkDict" disabled name="hyzk" value="${czrk.hyzk}" class="form-control"/> --%>
							</div> 
						  </td>
					  	<th>学历</th>
					  	<td>
						   <div class="form-group">
						        <km:dataDic dictCode="XL" value="${czrk.xl}"/>
								<%-- <input id="XL" dict="xlDict" disabled name="xl" value="${czrk.xl}" class="form-control"/> --%>
							</div> 
						  </td>
					  </tr>
					 	 <tr>
					  <th>QQ</th>
					  <td ><c:out  value="${czrk.xxbcQq}"/></td>
					 <th>微信</th>
					 <td ><c:out  value="${czrk.xxbcWx}"/></td>
				       	<th>微博</th>
				       	<td ><c:out  value="${czrk.xxbcWb}"/></td>
					 </tr>
					 <tr>
					   <th>电子邮箱</th>
					   <td ><c:out value="${czrk.xxbcDzyx}"/></td>
					 <th>与户主关系</th>
					  	<td>
						   <div class="form-group">
						        <km:dataDic dictCode="rygx" value="${czrk.yhzgx}"/>
								<%-- <input id="yhzgx" dict="rygxDict" disabled name="yhzgx" value="${czrk.yhzgx}" class="form-control"/> --%>
							</div> 
						  </td>
					  <th>职业</th>
					  <td ><c:out  value="${czrk.zy}"/></td>
					</tr>
					 <tr>
					
					 <th>现居住地址</th>
					 <td ><c:out  value="${czrk.xjzdzz}"/></td>
				       	<th>现居住地住址房间号</th>
				       	<td ><c:out  value="${czrk.xjzdzzfjh}"/></td>
					  <th>地址名称</th>
					  <td ><c:out  value="${czrk.dzmc}"/></td>
					 </tr>
					 <tr>
					 
					 </tr>
					  <%-- <tr>
					 
					  	<th>PGIS经度</th>
					  	<td><input type="text" name="zbx" value="${czrk.zbx}" class="easyui-textbox easyui-validatebox  form-control"  readonly="true" /></td>
					  	<th>PGIS纬度</th>
					  	<td><input type="text" name="zby" value="${czrk.zby}" class="easyui-textbox easyui-validatebox  form-control"  readonly="readonly" /></td>
						
					</tr> --%>
					<%-- <tr><th>地址信息</th><th colspan="5"><hr></th></tr>
					 <tr>
					 <th>所属县区</th>
					 <td>
					 <input type="hidden" name="ssxq" value="${czrk.ssxq }" />
					 <input type="text" id="ssxq_mc" name="ssxq_mc" value="${czrk.ssxq_mc}" class="easyui-textbox easyui-validatebox form-control"  readonly="readonly" >
					 </td>
					  <th>街路巷(村)</th>
					    <input type="hidden" name="jlxc" value="${czrk.jlxc }" />
					   <td><input type="text" id="jlxc_mc" name="jlxc_mc" value="${czrk.jlxc_mc }"   class="easyui-textbox easyui-validatebox form-control"  readonly="readonly"></td>  
					
					 <th>乡镇街道</th>
					 <input type="hidden" name="xzjd" value="${czrk.xzjd }" />
					 <td><input type="text" id="xzjd_mc" name="xzjd_mc" value="${czrk.xzjd_mc }"  class="easyui-textbox easyui-validatebox form-control"   readonly="readonly"  data-options="validType:'length[0,36]'" ></td> 
					 
					 </tr>
					 <tr>
					   <th>门楼牌号</th>
					   <td><input type="text" id="mlph" name="mlph" value="${czrk.mlph }"  class="easyui-textbox easyui-validatebox form-control"  data-options="validType:'length[0,200]'" ></td> 
					   <th>门(楼)详址</th>
					   <td><input type="text" id="mlxz" name="mlxz" value="${czrk.mlxz }"  class="easyui-textbox easyui-validatebox form-control"   readonly="readonly"  data-options="validType:'length[0,200]'" ></td> 
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
					</tr>   --%>
				  </table>
			</fieldset>
		
			
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
</div>