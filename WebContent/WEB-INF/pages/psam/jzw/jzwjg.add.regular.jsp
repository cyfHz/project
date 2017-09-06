<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<style>
.container
{
    padding:5px;
    background-color:#E4EEF0;
}
.mainTable
{
    margin: 0 auto;
    text-align: center;
    border-collapse: collapse;
    border:1px solid #B7D0D5;
    table-layout: auto;
    text-align: center;
    width:94%;
}

.mainTable td{ border:1px solid #B7D0D5; line-height:2; }
.mainTable_LeftTd
{
    text-align: right;
    font-size: 12px;
    background-color: White;
}
.mainTable_RightTd
{
    text-align: left;
    width: 38%;
    padding-left: 5px;
    text-align: left;
    font-size: 12px;
    background-color: White;
}
.mainTable_TitleTd
{
    color: #000000;
    font-size: 12px;
    font-weight: bold;
    height: 21px;
    text-decoration: none;
    text-align: center;
}
.cap {
	font: bold 14px/2.5 'Microsoft Yahei', sans-serif;
	color: #202C44;
	background-color: #E7F0FF;
	text-align: center;
	border-bottom: 1px solid #A1B7CE;
}
tr{
	line-height: 20px;
}
</style>
<script type="text/javascript">
	function cancel() {
		editDialog.close(100);
	}
	$(function(){
		$("#dxfj_select").change(function(){
			var select_value=$(this).val();
// 			if(select_value=='true'){
// 				$("#dxfj_div2").css("display","none");
// 				$("#dx_btndiv").css("display","none");
// 				$("#dxfj_div1").css("display","");
// 				$("#dxfj_div1").appendTo("#dx_div");
// 			}else{
// 				$("#dxfj_div1").css("display","none");
// 				$("#dx_btndiv").css("display","");
// 				$("#dxfj_div2").css("display","");
// 				$("#dxfj_div2").appendTo("#dx_div");
// 			}
			if(select_value=='false'){
				$("#dxfj_div1").css("display","none");
				$("#dx_btndiv").css("display","");
				$("#dxfj_div2").css("display","");
				$("#dxfj_div2").appendTo("#dx_div");
			}else{
				$("#dxfj_div2").css("display","none");
				$("#dx_btndiv").css("display","none");
				$("#dxfj_div1").css("display","");
				$("#dxfj_div1").appendTo("#dx_div");
			}
		});
	});
//-------------------    tools  ------------------------------
//检验所传入数值是否是 正整数、非负整数
function checkNumber(type,value){
	var re = new RegExp("^[0-9]*[1-9][0-9]*$");//正整数 
	var re2 = new RegExp("^\\d+$");//非负整数（正整数   + 0）   
	var flag = false;
	if(type=='1'){
		if(re.test(value)){
			flag=true;
		}
	}else if(type=='0'){
		if(re2.test(value)){
			flag=true;
		}
	}
	return flag;
}

//-------------------后缀规则及位数规则对象 PostFixAndWidthRule------------
//单元/楼层/房间 后缀 ,提取单元号/楼层号/房间号 位数
function PostfixAndWidthRule(p_dy,p_lc,p_fj,w_dy,w_lc,w_fj){
	this.postfix_dy = p_dy;
	this.postfix_lc = p_lc;
	this.postfix_fj = p_fj;
	this.width_dy = w_dy;
	this.width_lc = w_lc;
	this.width_fj = w_fj;

	return this;
}
//------------------  前缀规则对象PrefixRule -------------------------
function PrefixRule(from,to,value){
	this.from = from;
	this.to = to;
	this.value = value;
	return this;
}
//----------------单元楼层房间规则对象DylcfjRule-------------------------
function DylcfjRule(dyIndex,lcfrom,lcto,mcfjs){
	this.dyIndex = dyIndex;
	this.lcfrom = lcfrom;
	this.lcto = lcto;
	this.mcfjs = mcfjs;
} 
//--------------------地下房间层数及房间数 对象---------------------------
function Dxlc(dxlcs,mcms){//一个对象对应一层地下房间设置 （楼层数，房间数）
	this.dxlcs = dxlcs;
	this.mcms = mcms;
	return this;
}
//------------------   建筑物结构对象Jzwjg  ---------------------------
function JzwjgView(dys, lcs, mdyms,dxcs,dxmcms) {//dys, lcs, mdyms, dxcs,dxmcms
	this.dys = dys; //
	this.lcs = lcs;//
	this.mdyms = mdyms;//
	this.dxcs = dxcs;
	this.dxmcms = dxmcms;
	return this;
}
//------------------------提取建筑物结构数据Jzwjg---------------
function getJzwjg_simple(){
	var dys = $("#dys").val(); 
	var lcs = $("#lcs").val(); 
	var mdyms = $("#mdyms").val(); 

	if(!checkNumber("1",dys)){
		 alertMsg.error("单元数填写错误，请填写大于0的数字！");
		 return false;
	}
	if(!checkNumber("1",lcs)){
		 alertMsg.error("楼层数填写错误，请填写大于0的数字！");
		 return false;
	}
	if(!checkNumber("1",mdyms)){
		 alertMsg.error("每层门数/房间数填写错误，请填写大于0的数字！");
		 return false;
	}
	var jzwjg= new JzwjgView(dys, lcs, mdyms,"","");
	return jzwjg;
}
function getJzwjg_all(){
	
	var dys = $("#dys").val(); 
	var lcs = $("#lcs").val(); 
	var mdyms = $("#mdyms").val(); 
	var dxcs = $("#dxcs").val(); 
	var dxmcms = $("#mdyms").val(); //地下房间分布和楼上部分一样

	if(!checkNumber("1",dys)){
		 alertMsg.error("单元数填写错误，请填写大于0的数字！");
		 return false;
	}
	if(!checkNumber("1",lcs)){
		 alertMsg.error("楼层数填写错误，请填写大于0的数字！");
		 return false;
	}
	if(!checkNumber("1",mdyms)){
		 alertMsg.error("每层门数/房间数填写错误，请填写大于0的数字！");
		 return false;
	}
 	if(!checkNumber("1",dxcs)){
		 alertMsg.error("地下层数填写错误，请填写大于0的数字！");
		 return false;
	}
	var jzwjg= new JzwjgView(dys, lcs, mdyms, dxcs,dxmcms);
	
	return jzwjg;
}
//----------------------地下室动态添加  层数/房间数---------------
//下地下层数数目
var dx_count=0;
function appendDxcs(){
	dx_count++;
	var divdx=$("<span>地下第"+dx_count+"层　房间数:</span><input size=3 name=\"dxmcms_"+dx_count+"\"  id=\"dxmcms_"+dx_count+"\" type=\"text\"/>");
	var div_dx_lc_line=$("<div id=\"dx_lc_line_"+dx_count+"\"></div>");
	div_dx_lc_line.append(divdx);
	div_dx_lc_line.append("&nbsp;&nbsp;&nbsp;&nbsp;");
	div_dx_lc_line.append("<hr/>");
	$("#dxfj_div2").append(div_dx_lc_line);
}
function remove_div_dx_lc_line(){
	$("#dx_lc_line_"+dx_count+"").remove();
	dx_count--;
	if(dx_count<0){dx_count=0;}

}
//获取非规则地下房间设置
function getDxlc(){
	var dxlcArray = new Array();
	for(var i=1;i<=dx_count;i++){
		var dxmcms = $("#dxmcms_"+i+"").val();
		var dxlc = new Dxlc(i,dxmcms);
		dxlcArray.push(dxlc);
	}
	return dxlcArray;
}
//------------------------房间号 前缀设置----------------------
//前缀规则数目
var prefix_count=0;
//增加前缀规则
function appendPrefixRule(){
	prefix_count++;
	var divfrom=$("<span>从第:</span><input size=1 name=\"f_lcs"+prefix_count+"\"  id=\"f_lcs"+prefix_count+"\" type=\"text\" />");
	var divto=$("<span>层,到第:</span><input size=1 name=\"t_lcs"+prefix_count+"\"  id=\"t_lcs"+prefix_count+"\" type=\"text\" />");
	var divvalue=$("<span>层,前缀为:</span><input size=2 name=\"prefix_value"+prefix_count+"\"  id=\"prefix_value"+prefix_count+"\" type=\"text\" />");
	var operate=$("<input type=\"button\" value=\"删除\" onclick=\"remove_div_prefix_line(PrefixRule_"+prefix_count+");\" />");
	var div_prefix_line=$("<div id=\"PrefixRule_"+prefix_count+"\"></div>");
	div_prefix_line.append(divfrom);
	div_prefix_line.append(divto);
	div_prefix_line.append(divvalue);
	div_prefix_line.append("&nbsp;");
	div_prefix_line.append(operate);
	div_prefix_line.append("<hr/>");
	$("#prefix_div").append(div_prefix_line);
}
function remove_div_prefix_line(idx){
	$("#prefix_div").find(idx).remove();
}	
function getPrefixRules(){
	var prefixRules = new Array();
	for(var i=1;i<=prefix_count;i++){
		var from = $("#f_lcs"+i+"").val();
		var to = $("#t_lcs"+i+"").val();
		var value = $("#prefix_value"+i+"").val();
		if(!PUBUtil.isStrHavaValue(from)||!PUBUtil.isStrHavaValue(to)||!PUBUtil.isStrHavaValue(value)){
			if(!(from==undefined&&to==undefined&&value==undefined)){
				 alertMsg.error("房间号前缀规则存在不完整记录，请检查！");return -1;
			}
		}
		if(PUBUtil.isStrHavaValue(from)&&PUBUtil.isStrHavaValue(to)&&PUBUtil.isStrHavaValue(value)){
			var prefixRule = new PrefixRule(from,to,value);
			prefixRules.push(prefixRule);
		}
	}
	return prefixRules;
}
//检验房间前缀规则楼层数填写是否错误
function validatePrefixRules(prefixRules){
	if(!prefixRules||prefixRules.length==0){
		return true;
	}
		var isRegular = $("#isRegular").val();
		var lcs = $("#lcs").val();
		for(var i=0;i<prefixRules.length;i++){
			var prefix = prefixRules[i];
			//console.log(prefix);
			var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
			if(!PUBUtil.isStrHavaValue(prefix.value)){
				 alertMsg.error("房间号前缀规则定义错误，请填写房间号前缀！");
				 return false;
			}
			if((!reg.test(prefix.from))||(!reg.test(prefix.to))){
				 alertMsg.error("房间号前缀规则定义错误，楼层数请填写数字！");
				 return false;
			}
			if(parseInt(prefix.from)>parseInt(prefix.to)){
				 alertMsg.error("房间号前缀规则定义错误，楼层填写错误，起始楼层应小于结束楼层！");
				 return false;
			}
			for(var j=0;j<prefixRules.length;j++){
				if(j==i){continue;}
				var prefix2 = prefixRules[j];
				if(((parseInt(prefix.from)>=parseInt(prefix2.from))&&(parseInt(prefix.from)<=parseInt(prefix2.to)))
						||((parseInt(prefix.to)>=parseInt(prefix2.from))&&(parseInt(prefix.to)<=parseInt(prefix2.to)))){
					 alertMsg.error("房间号前缀规则定义混乱，请不要交叉楼层定义！");//+prefix.from+"-"+prefix.to+"||"+prefix2.from+"-"+prefix2.to
					 return false;
				}
			}
			if(isRegular=="true"){
				if(parseInt(prefix.to)>parseInt(lcs)){
					 alertMsg.error("房间号前缀规则定义错误，楼层数最大到"+lcs+"层！");
					 return false;
				}
			}
		}
		return true;
}

//------------------------提交数据-------------------------------------
function save(){
	if(!($("#fm").form('validate'))){
		 alertMsg.error("表单填写错误，请检查数据！");
	}
	//是否地上楼层单元房间规则排列
	var isRegular = $("#isRegular").val();
	var dys = $("#dys").val();
	//提取建筑物Id
	var jzwId = $("#jzwId").val();

	//检测建筑副结构必填数据是否完整
	if(!getJzwjg_simple()){return;}
	var jzwjg;
	
	//提取房间号 前缀设置
	var prefixRules = getPrefixRules();
	if(prefixRules==-1){
		return;
	}
	//检验房间前缀规则楼层数填写是否错误
	var sd=validatePrefixRules(prefixRules);
	//console.log(sd);
	if(!validatePrefixRules(prefixRules)){return;}
	
	//提取单元/楼层/房间 后缀设置
	var postfix_dy = $("#postfix_dy").val();
	var postfix_lc = $("#postfix_lc").val();
	var postfix_fj = $("#postfix_fj").val();
	if(PUBUtil.isSpecialCharacter(postfix_dy)){
		$.messager.alert('提示',"单元后缀中不能包含特殊字符");	
		return;
	}
	if(PUBUtil.isSpecialCharacter(postfix_lc)){
		$.messager.alert('提示',"楼层后缀中不能包含特殊字符");	
		return;
	}
	if(PUBUtil.isSpecialCharacter(postfix_fj)){
		$.messager.alert('提示',"房间后缀中不能包含特殊字符");	
		return;
	}
	//提取单元号/楼层号/房间号 位数设置
	var width_dy = $("#width_dy").val();
	var width_lc = $("#width_lc").val();
	var width_fj = $("#width_fj").val();
	
	var postfixAndWidthRule = new PostfixAndWidthRule(postfix_dy,postfix_lc,postfix_fj,width_dy,width_lc,width_fj);
	
	var view="";
	//获取地下房间设置
	var select_value = $("#dxfj_select").val();
	
	//房间序号 是否连续2016-0808
	var sflx=$("#dy_lx_div").combobox("getValue");
	
	if(select_value=='true'){//地下房间按单元规则排列
		jzwjg = getJzwjg_all();
		view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"1","sflx":sflx};
	}else if(select_value=='false'){//地下房间不规则排列
		var dxlc = getDxlc();
		jzwjg = getJzwjg_simple();
		view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"dxlc":dxlc,"type":"2"};
	}else{//无地下室
		jzwjg = getJzwjg_simple();
		view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"0","sflx":sflx};
	}
	var url="${pageContext.request.contextPath}/psam/jzwjg/addJzwjg";
	loadMask.open();
	KMAJAX.ajaxTodoJson(url,  JSON.stringify(view), function(data){
		loadMask.close();
		 if(data.statusCode!=200){
			 alertMsg.error(data.message);
			 return ;
		 }else{
			//清除前缀规则div中的内容
			$("#prefix_div").empty();
			editDialog.close(100);
			//结构保存完成，跳转结构信息页面
			var url =encodeURI("${ctx}/psam/jzwjg/enterShowJzwjg?jzwId="+jzwId);
			window.open(url,"_blank");
		 }
	 });
}
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<form id="fm" method="post" class=" ">
			<input type="hidden" id="jzwId" name="jzwId" value="${jzwjbxx.dzbm}"/>
			<input type="hidden" id="isRegular" value="true"/>
			<fieldset>
				<table id="formTbale" cellpadding="2" style="width: 890px;" class="mainTable">
					<tr>
						<th>建筑物名称</th>
					    <td >
					    	<input type="text" id="jzwmc" value="${jzwjbxx.jzwmc }"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,300]'" />
					    </td>	
					    <th>房间号是否连续</th>
					    <td >
				            <select id="dy_lx_div" style="width: 170px;" class="easyui-combobox" editable="false">
					           <option value="01">不连续</option>
					           <option value="02">连续</option>
				            </select>
					    </td>
					    <th>地址名称</th>
					    <td >
					    	<input type="text" id="dzmc" value="${jzwjbxx.dzmc }"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,300]'"  />
					    </td>
					</tr>
					    <tr><td colspan="6"><div class="cap">单元楼层房间设置</div></td></tr>
					<tr>
						<th>单元数/行数</th>
						<td><input type="text" id="dys" name="dys" title="单元数" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" required="required" data-options="validType:'length[1,3]'"></td>
						<th>楼层数</th>
						<td><input type="text" id="lcs" name="lcs" title="楼层数" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" required="required" data-options="validType:'length[1,3]'"></td>
						<th>每层门数/房间数</th>
						<td><input type="text" id="mdyms" name="mdyms" title="每层房间数" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" required="required" data-options="validType:'length[1,3]'"></td>
					</tr>
					
					    <tr><td colspan="6"><div class="cap">后缀设置</div></td></tr>
					<tr>
						<th>单元后缀</th>
						<td><input type="text" id="postfix_dy" name="postfix_dy" value="单元" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,3]'"></td>
					
						<th>楼层后缀</th>
						<td><input type="text" id="postfix_lc" name="postfix_lc" value="层"  class="easyui-textbox easyui-validatebox form-control"	data-options="validType:'length[0,3]'"></td>
						
						<th>房间后缀</th>
						<td><input type="text" id="postfix_fj" name="postfix_fj" value="室"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,3]'"></td>
						
					</tr>
					
					<tr><td colspan="6"><div class="cap">位数设置</div></td></tr>
					<tr>
						<th>单元号位数</th>
						<td><input type="text" id="width_dy" name="width_dy" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" required="required" data-options="validType:'length[0,3]'"></td>
					
						<th>楼层号位数</th>
						<td><input type="text" id="width_lc" name="width_lc" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" required="required" data-options="validType:'length[0,3]'"></td>
						
						<th>房间号位数</th>
						<td><input type="text" id="width_fj" name="width_fj" value="2" class="easyui-numberbox easyui-validatebox form-control" min="1" required="required" data-options="validType:'length[0,3]'"></td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<td colspan="3" align="center">
						        <span class="cap" >房间号前缀设置</span>
								<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="appendPrefixRule();">新增前缀规则</a>
						</td>
						<td colspan="3">
								<font color="blue" class="cap"><font color="red">地下楼层: </font>地下房间是否按单元分布</font>
								<select id="dxfj_select" name="dxfj_select" data-options="editable:false"  style="width: 160px;">
								   <option value="">请选择</option>
							       <option value="false">否</option>
								   <option value="true">是</option>
					        	</select>
					        	<div id="dx_btndiv" style="display:none;">
					        		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="appendDxcs();">新增</a>
					        		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="remove_div_dx_lc_line();">删除</a>
					        	</div>
						</td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<td colspan="3" style="border-width:1px;">
							<div id="prefix_div" style="height: 160px;overflow-y: scroll;"></div>
						</td>
						<td colspan="3">
							<div id="dx_div" style="height: 160px;overflow-y: scroll;"></div>
						</td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
				</table>
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="jzwjbxx.addjzwjg">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="save()">保 存</a>
				</km:widgetTag>
				<km:widgetTag widgetRulevalue="">
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">关  闭</a>
				</km:widgetTag>
			</div>
		</form>
	</div>
</div>
<!-- 地下房间DIV    规则 -->
<div id="dxfj_div1" style="display: none;">
	地下层数:<input type="text" name="dxcs" id="dxcs" size=12 class="rasyui-numberbox easyui-validatebox" min="0" data-options="validType:'length[0,3]'">
</div>
<!-- 地下房间DIV   不规则 -->
<div id="dxfj_div2" style="display: none;"></div>