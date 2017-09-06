<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ include file="/static/meta/easyuipub.jsp" %>
<script type="text/javascript">
	function doSubmit(){
		var fzsfzhm=$("#fzsfzhm").val();
		if(isCardNo(fzsfzhm)==false){
			alertMsg.warn("房主身份证号格式不正确");return;
		}
		
		var tgrsfzhm=$("#tgrsfzhm").val();
		if(PUBUtil.isStrHavaValue(tgrsfzhm)){
			if(isCardNo(tgrsfzhm)==false){
				alertMsg.warn("托管人房主身份证号格式不正确");return;
			}
		}
		
		if(!KMAJAX.validateFromCallback($("#fm_fwinfo"),function(json){
			if(json.statusCode == KMCORE.statusCode.ok){
				alertMsg.correct(json.message);
				$("#dlg_fwInfo").dialog("close");
			}else{
				alertMsg.error(json.message);
			}
		})){
			alertMsg.warn("请确认校验不通过数据");return;
		}
	}
	function cancel(){
		$("#dlg_fwInfo").dialog("close"); 
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
<t:DataDict code="rygx" var="rygxDict"></t:DataDict>
<t:DataDict code="FWLB" var="fwlbDict"></t:DataDict>
<t:DataDict code="FWXZ" var="fwxzDict"></t:DataDict>
<t:DataDict code="FWYT" var="fwytDict"></t:DataDict>
<t:DataDict code="fwlx" var="fwlxDict"></t:DataDict>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<style>
tr{
	line-height: 25px;
}
th{
    width: 20%;
}
</style>

<form id="fm_fwinfo" method="post" action="${pageContext.request.contextPath}/psam/fw/saveFwjbxxAccInfo" >
	<input type="hidden" name="fwid" value="${fwjbxx.fwid}"/>
	<input type="hidden" name="fwbh" value="${fwjbxx.fwbh }"/>
	<input type="hidden" name="lrfs" value="0"/><!--  录入方式 -->
<div class="etc">
	<table class="et" cellpadding="0" cellspacing="0">
	<tr><td colspan="4"><div class="cap">房屋信息</div></td></tr>
	<tr>		
	<th>省市县</th>
	<td>
		<input type="hidden" name="ssxqdm" value="${fwjbxx.ssxqdm }" />
		<input type="text" name="ssxq_mc" value="${fwjbxx.ssxq_mc }" class="easyui-textbox form-control" readonly="readonly" />
	</td>
    <th>房间号:</th> 
	<td>
		<input  type="hidden" name="fjbm" value="${fwjbxx.fjbm }"/>
		<input class="easyui-textbox form-control" type="text" name="fjh"  value="${fwjbxx.fjh }" readonly="readonly" />
	</td>
	
	</tr>
	<tr>
		<th>房屋类别 <span class="span_main">*</span></th>
		<td><input dict="fwlbDict" style="width: 170px" name="fwlb"  value="${fwjbxx.fwlb}" class="form-control"  required="required"  data-options="editable:false"/></td>
		 <th>房屋性质 <span class="span_main">*</span></th> 
		<td>
			<input dict="fwxzDict" style="width: 170px" name="fwxz" value="${fwjbxx.fwxz}" class="form-control"  required="required"  data-options="editable:false"/>
		</td>
    </tr>
	<tr>
		<th>房屋用途 <span class="span_main">*</span></th>
		<td>
			<input dict="fwytDict" style="width: 170px" name="fwyt" value="${fwjbxx.fwyt}" class="form-control"  required="required"  data-options="editable:false"/>
		</td>
		<th>房屋类型 <span class="span_main">*</span></th>
		<td>
			<input dict="fwlxDict" style="width: 170px" name="fwlx" value="${fwjbxx.fwlx}" class="form-control"  required="required"  data-options="editable:false"/>
		</td>
	</tr>
	
	<tr>
		
		<th>房屋产权证号 <span class="span_main">*</span></th> 
		<td><input type="text" name="fwcqzh"  value="${fwjbxx.fwcqzh}" class="easyui-textbox   easyui-validatebox form-control"  data-options="required:true,validType:'length[1,50]'"/></td>
		
		<th>是否出租房屋</th>
		<td>
			<select name="sfczfw" id="sfczfw" style="width: 170px" class="easyui-combobox" data-options="editable:false">
			 		<option value="0"  <c:if test="${'0' eq fwjbxx.fwcqzh  }">selected = "selected"</c:if>>否</option>
					<option value="1"  <c:if test="${'1' eq fwjbxx.fwcqzh  }">selected = "selected"</c:if>>是</option>
						
			</select>
		</td>
	</tr>
	
   <tr>
    <th>户型（*室*厅）<span class="span_main">*</span></th> 
	<td><input type="text" name="hx" value="${ fwjbxx.hx}" data-options="required:true,validType:'length[1,36]'" class="easyui-textbox   easyui-validatebox  form-control" /></td>
	<th>房屋间数</th>
	<td><input type="text" name="fwjs" style="width: 170px"  value="${ fwjbxx.fwjs}" class="easyui-numberbox form-control" data-options="" /></td>
	
	</tr>
   <tr>
     <th>所属街路巷(小区)</th>
	 <td colspan="3">
		<input type="hidden" name="ssjlxxq_dzbm" value="${fwjbxx.ssjlxxq_dzbm }" />
		<input type="text" name="ssjlxxq_mc" value="${fwjbxx.ssjlxxq_mc }"  class="easyui-textbox easyui-validatebox form-control" readonly="readonly" />
	 </td>
   </tr>
   <tr>
     <th>房屋地址详细地址</th>
	 <td colspan="3">
		<input type="text" name="fwdz" value="${fwjbxx.fwdz }" class="easyui-textbox easyui-validatebox form-control" data-options="required:true,validType:'length[1,200]'"   style="width: 400px;"/>
	 </td>
   </tr>
   
   <tr><td colspan="4"><div class="cap">房主信息</div></td></tr>
	 
    <tr>
	  <th>房主姓名 <span class="span_main">*</span></th>
	  <td><input type="text" name="fzxm" value="${fwjbxx.fzxm}" class="easyui-textbox  easyui-validatebox form-control" data-options="required:true,validType:'length[1,50]'" /></td>
	  <th>房主身份证号码 <span class="span_main">*</span></th>
	  <td><input type="text" id="fzsfzhm" name="fzsfzhm" value="${fwjbxx.fzsfzhm}"  class="easyui-textbox  easyui-validatebox form-control"  data-options="required:true,validType:'length[15,18]'"/></td>
	</tr>
	<tr>
	   <th>房主联系电话</th> 
	  <td><input  type="text" name="fzlxdh" value="${ fwjbxx.fzlxdh}" data-options="validType:'length[0,20]'" class="easyui-textbox form-control"/></td>
	</tr>
	 <tr><td colspan="4"><div class="cap">托管人信息</div></td></tr>
	<tr>
      <th>托管人姓名</th>
	  <td><input type="text" name="tgrxm" value="${fwjbxx.tgrxm}" data-options="validType:'length[0,50]'"  class="easyui-textbox  easyui-validatebox  form-control" /></td>
	  <th>托管人身份证号码</th>
	  <td><input type="text" name="tgrsfzhm" id="tgrsfzhm" value="${fwjbxx.tgrsfzhm}"  class="easyui-textbox  easyui-validatebox form-control" data-options="validType:'length[0,18]'" /></td>
    </tr>
    <tr>
	  <th> 托管人联系电话</th>
	  <td><input type="text" name="tgrlxdh"  value="${fwjbxx.tgrlxdh}"  data-options="validType:'length[0,20]'"  class="easyui-textbox form-control" /></td>
      <th>与房主关系</th>
	  <td> 
	  <input dict="rygxDict" name="yfzgx"  class="form-control" data-options="editable:false" style="width: 170px;"/>
	  </td>
    </tr>
    
    <tr><td colspan="4"><div class="cap">房屋所属单位信息</div></td></tr>
    
    <tr>
	  <th>房屋所属单位编码</th>
	  <td colspan="3"><input type="text" style="width: 400px" name="fwssdwbm" value="${fwjbxx.sdpcs}"  class="easyui-textbox  easyui-validatebox  form-control" data-options="required:true,validType:'length[1,36]'" /></td>
	</tr>
	<tr> 
	  <th>房屋所属单位名称</th>
	  <td colspan="3"><input type="text" name="fwssdwmc" style="width: 400px;"  value="${fwjbxx.sdpcs_mc}" class="easyui-textbox   easyui-validatebox form-control" data-options="required:true,validType:'length[1,200]'"></input></td>
	</tr>
	
	  <tr><td colspan="4"><div class="cap">登记信息</div></td></tr>
	<tr>
      <th>属地派出所</th>
	  <td colspan="3">
	  <input type="hidden" name="sdpcs" value="${fwjbxx.sdpcs}" />
	  <input type="text" name="sdpcs_mc" value="${fwjbxx.sdpcs_mc}" readonly="readonly" class="easyui-textbox  form-control" style="width: 400px;"/>
	  </td>
	</tr>
	<tr>
	<th>警务责任区</th>
	<td colspan="3">
		<input type="hidden" name="jwzrq" value="${fwjbxx.jwzrq}" />
		<input type="text" name="jwzrq_mc"  value="${fwjbxx.jwzrq_mc}" class="easyui-textbox" style="width: 400px"/>
	</td>
	</tr>
	<tr>
	<th>信息备注</th>
	<td colspan="3"><input type="text" name="bz" value="${fwjbxx.bz}"  data-options="validType:'length[0,500]'" class="easyui-textbox  form-control" style="width: 400px;height: 30px"/></td>
	</tr>
	</table>
</div>
 </form>
	<div id="dlg_fwInfo_buttons" style="text-align: right;">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="doSubmit()" style="width: 90px">确认</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_fwInfo').dialog('close')" style="width: 90px">关闭</a>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>