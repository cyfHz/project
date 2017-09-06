<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<style>
tr{
	line-height: 20px;
}
</style>
<script type="text/javascript">
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close();
	}
</script>
<t:DataDict code="qzzl" var="qzzlDict"></t:DataDict>
<t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
<t:DataDict code="gj" var="gjDict"></t:DataDict>
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
<t:DataDict code="xb" var="xbDict"></t:DataDict> 
<t:DataDict code="FWLB" var="fwlbDict"></t:DataDict>
<t:DataDict code="FWXZ" var="fwxzDict"></t:DataDict>
<t:DataDict code="FWYT" var="fwytDict"></t:DataDict>
<t:DataDict code="fwlx" var="fwlxDict"></t:DataDict>
<t:DataDict code="rygx" var="rygxDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',border:false" title="" style="overflow-y:scroll; padding: 5px; " class="container">
	<!-- <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;"> -->
		<form id="fm" method="post" class="form-horizontal" action="${ctx}/psam/fw/updateOrAddFW">
		<%--  <input type="hidden" id="fw_fwbh" name="fwid" value="${fw.fjbm}"/> --%>
		 <input type="hidden" id="fw_fwbh" name="fwid" value="${fwid}"/>
	
	<table class="mainTable" style="width: 100%;">
				<tr>
					<td class="mainTable_TitleTd" colspan="4" align="left">房屋基本信息
					</td>
				</tr>
				<tr>
					<td class="mainTable_LeftTd" width="15%" ><strong>详细地址</strong>
					</td>
					<td class="mainTable_RightTd" colspan="3" >
						<strong> 
							${fw.fwdz}
						</strong>
					</td>
				</tr>
				<tr>
					    <td class="mainTable_LeftTd"><strong>房屋面积(平米)</strong></td>
						<td class="mainTable_RightTd" colspan="3"><strong><c:out value="${fw.fwmj}"></c:out></strong></td>
					 </tr>
	</table>
	<div id="tt" class="easyui-tabs" style="fit: true;">  
    <div title="房屋基本信息" style="padding:10px;fit: true;" >  
				 <table  border="2px solid"  cellspacing="0" cellpadding="0" class="mainTable" style="height: 412px;">
					  <tr><td colspan="4"><div class="cap">房屋基本信息</div></td></tr>
					  <tr>
						<th>房屋号</th>
						<td><c:out value="${fw.fjh}"></c:out></td>
						<th>房屋类型</th>
						<td>
						<km:dataDic dictCode="fwlx" value="${fw.fwlx}"/>
						<%--<input id="fwlx"  dict="fwlxDict" disabled="disabled" name="fwlx" value="${fw.fwlx}" class="form-control"/>  --%>
						</td>
					 </tr>
					  <tr>
					    <th>是否出租房屋</th>
						<td >
						<c:if test="${fw.sfczfw eq '0' }">否 </c:if>
						<c:if test="${fw.sfczfw eq '1' }">是 </c:if>
						<%-- <select id="sfczfw" name="sfczfw"  disabled="disabled" data-options=" ">
								<option value="0" <c:if test="${fw.sfczfw eq '0' }">selected='selected' </c:if>>否</option>
								<option value="1" <c:if test="${fw.sfczfw eq '1' }">selected='selected' </c:if>>是</option>
							</select> --%>
						</td>
					    <th>房屋类别</th>
						<td>
						  <km:dataDic dictCode="FWLB" value="${fw.fwlb}"/>
							<%-- <input id="fwlb" dict="fwlbDict" disabled="disabled" name="fwlb" value="${fw.fwlb}" class="form-control"/> --%>
						</td>
					</tr>
					<tr>
						<th>房屋性质</th>
						<td>
						  <km:dataDic dictCode="FWXZ" value="${fw.fwxz}"/>
							<%-- <input id="fwxz" dict="fwxzDict" disabled="disabled" name="fwxz" value="${fw.fwxz}" class="form-control"/> --%>
						</td>
						<th>房屋用途</th>
						<td>
						  <km:dataDic dictCode="FWYT" value="${fw.fwyt}"/>
							<%-- <input id="fwyt" dict="fwytDict" disabled="disabled" name="fwyt" value="${fw.fwyt}" class="form-control"/> --%>
						</td>
					 </tr>
					 <tr>
					    <th>户型(*室*厅)</th>
						<td><c:out value="${fw.hx}"></c:out></td>
						<th>房屋间数</th>
						<td><c:out value="${fw.fwjs}"></c:out></td>
					 </tr>
					 
					 <tr><td colspan="4"><div class="cap">房主信息</div></td></tr>
					 <tr>
					   	<th>房主姓名</th>
						<td><c:out value="${fw.fzxm}"></c:out></td>
						<th>房屋产权证号</th>
						<td><c:out value="${fw.fwcqzh}"></c:out></td>
					 </tr>
					 <tr>
						<th>房主身份证号</th>
						<td><c:out value="${fw.fzsfzhm}"></c:out></td>
						<th>房主联系电话</th>
						<td><c:out value="${fw.fzlxdh}"></c:out></td>
					</tr>
					
					 <tr><td colspan="4"><div class="cap">托管人信息</div></td></tr>
					 <tr>
					   	<th>托管人姓名</th>
						<td><c:out value="${fw.tgrxm}"></c:out></td>
						<th>托管人身份证号</th>
						<td><c:out value="${fw.tgrsfzhm}"></c:out></td>
					</tr>
					<tr>
					    <th>托管人联系电话</th>
						<td><c:out value="${fw.tgrlxdh}"></c:out></td>
					    <th>与房主关系</th>
						<td>
						  <div class="form-group">
						      <km:dataDic dictCode="rygx" value="${fw.yfzgx}"/>
								<%-- <input id="yfzgx" dict="rygxDict" disabled name="yfzgx" value="${fw.yfzgx}" class="form-control"/> --%>
							</div>
					</td>
					</tr>
					 
				 </table>
    </div>  
    

    
    <div title="房间人员信息"  style="overflow:auto;fit: true;">  
        <div id="tt_ryxx" class="easyui-tabs" >  
               <div title="房屋常住人口信息" id="fwry_czrk" style="padding:10px;"> 
                  <table id="fwry_czrk_detail" style="height: 380px;"></table>
               </div>
               
               <div title="房屋流动人口信息" style="padding:10px;"> 
                   <table id="fwry_ldrk_detail" style="height: 380px;"></table>
               </div>
               
               <div title="房屋境外人员信息" style="padding:10px;"> 
                  <table id="fwry_jwry_detail" style="height: 380px;"></table>
               </div>
        </div>
    </div> 
    
    
    
    <!--历史居住人员信息  -->
    <div title="房间历史人员信息"  style="overflow:auto;height: 500px;" >
       <div id="tt_lsry" class="easyui-tabs" >  
         <div title="常住人口信息" id="lsry_czrk" style="padding:10px;"> 
           <table id="lsry_czrk_detail" style="height: 380px;"></table>
         </div>
           
         <div title="流动人口信息" id="lsry_ldrk" style="padding:10px;" > 
           <table id="lsry_ldrk_detail" style="height: 380px;"></table>
         </div>
         
         <div title="境外人员信息" id="lsry_jwry" style="padding:10px;"> 
            <table id="lsry_jwry_detail"  style="height: 380px;"></table>
         </div>
       </div>
    </div>
    
    <div title="房间单位信息"  style="overflow:auto;height: 500px;" >
        <table id="fwdw_dwxx_detail"  style="height: 430px;"></table>
    </div>
    
     <div title="房间历史单位信息"  style="overflow:auto;height: 500px;" >
         <table id="lsry_dwxx_detail"  style="height: 430px;"></table>
    </div> 
    
</div>  
	

		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jzwjbxx/jzwcj.fwxx.js"></script>
</div>
