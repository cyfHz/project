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
	function doSubmit() {
		if (!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)) {
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel() {
		editDialog.close(100);
	}
	$(function(){
		$("#dxfj_select").change(function(){
			var select_value=$(this).val();
			
			if(select_value=='true'){
				$("#dxfj_div2").css("display","none");
				$("#dx_btndiv").css("display","none");
				$("#dxfj_div1").css("display","");
				$("#dxfj_div1").appendTo("#dx_div");
			}else{
				$("#dxfj_div1").css("display","none");
				$("#dx_btndiv").css("display","");
				$("#dxfj_div2").css("display","");
				$("#dxfj_div2").appendTo("#dx_div");
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

//------------------------提交数据-------------------------------------
function save(){
	if(!($("#fm").form('validate'))){
		 alertMsg.error("表单填写错误，请检查数据！");
	}
	//是否地上楼层单元房间规则排列
	var isRegular = $("#isRegular").val();
	var dys = $("#dys").val();
	
	//检测建筑副结构必填数据是否完整
	if(!getJzwjg_simple()){return;}
	var jzwjg;
	//提取建筑物Id
	var jzwId = $("#jzwId").val();
	//提取房间号 前缀设置
	var prefixRules = getPrefixRules();
	
	//提取楼层单元房间规则设置
//	var dylcfjRules = getDylcfjRules();
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
	jzwjg = getJzwjg_simple();
	view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"0"};
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
					    <th>地址名称</th>
					    <td colspan="3">
					    	<input type="text" id="dzmc" value="${jzwjbxx.dzmc }"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,300]'" style="width: 450px" />
					    </td>
					</tr>
					<tr><td colspan="6"><div class="cap">单元楼层房间设置</div></td></tr>
					<tr>
						<th>单元数/行数</th>
						<td><input type="text" id="dys" name="dys" title="单元数" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" readonly="readonly" required="required" data-options="validType:'length[1,3]'"></td>
						<th>楼层数</th>
						<td><input type="text" id="lcs" name="lcs" title="楼层数" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" readonly="readonly" required="required" data-options="validType:'length[1,3]'"></td>
						<th>每层门数/房间数</th>
						<td><input type="text" id="mdyms" name="mdyms" title="每层房间数" value="1" class="easyui-numberbox easyui-validatebox form-control" min="1" readonly="readonly" required="required" data-options="validType:'length[1,3]'"></td>
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
