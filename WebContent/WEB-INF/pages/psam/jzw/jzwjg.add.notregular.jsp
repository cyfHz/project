<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp"%>
<script type="text/javascript">
	function doSubmit() {
		if (!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)) {
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel() {
		alertMsg.confirm("取消结构采集？",{
			cancelCall:function(){
				alertMsg.close();
			},
			okCall:function(){
				editDialog.close(100);
				alertMsg.close();
			}
		});
	}
	$(function(){
		$("#dys").change(function(){
			var dys=$(this).val();
			$("#dylcfj_div_2").empty();
			dylcfjRule_count=0;
			for(var i=1;i<parseInt(dys)+1;i++){
				appendDefaultDylcfjRule(i);
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
function JzwjgView_bak(dys, lcs, mdyms,dxcs,dxmcms) {//dys, lcs, mdyms, dxcs,dxmcms
	this.dys = dys; //
	this.lcs = lcs;//
	this.mdyms = mdyms;//
	this.dxcs = dxcs;
	this.dxmcms = dxmcms;
	return this;
}
function JzwjgView(dys) {
	this.dys = dys; //
	return this;
}
//------------------------提取建筑物结构数据Jzwjg---------------
function getJzwjg_simple(){
	var dys = $("#dys").val(); 

	if(!checkNumber("1",dys)){
		 alertMsg.error("单元数填写错误，请填写大于0的数字！");
		 return false;
	}
	var jzwjg= new JzwjgView(dys);
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
//地下层数数目
var dx_count=0;
function appendDxcs(){
	dx_count++;
	var divdx=$("<span>地下第"+dx_count+"层　　房间数:</span><input size=3 name=\"dxmcms_"+dx_count+"\"  id=\"dxmcms_"+dx_count+"\" type=\"text\"/>");
	$("#dxfj_div2").append(divdx);
	$("#dxfj_div2").append("<br/><br/>");
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
//-----------------------不规则单元楼层房间设置----------------------
//规则数目
var dylcfjRule_count=0;
//增加单元楼层房间规则
function appendDylcfjRule(){
	dylcfjRule_count++;
	
	var divDyIndex=$("<span>第:</span><input size=3 name=\"dyIndex_"+dylcfjRule_count+"\"  id=\"dyIndex_"+dylcfjRule_count+"\" type=\"text\" />");
	var divLcFrom=$("<span>单元　　　第:</span><input size=3 name=\"lcfrom_"+dylcfjRule_count+"\"  id=\"lcfrom_"+dylcfjRule_count+"\" type=\"text\" />");
	var divLcTo=$("<span>层,　到第:</span><input size=3 name=\"lcto_"+dylcfjRule_count+"\"  id=\"lcto_"+dylcfjRule_count+"\" type=\"text\" />");
	var divMcFjs=$("<span>层　　　每层房间数:</span><input size=6 name=\"mcfjs_"+dylcfjRule_count+"\"  id=\"mcfjs_"+dylcfjRule_count+"\" type=\"text\" />");

	$("#dylcfj_div_2").append(divDyIndex);
	$("#dylcfj_div_2").append(divLcFrom);
	$("#dylcfj_div_2").append(divLcTo);
	$("#dylcfj_div_2").append(divMcFjs);
	$("#dylcfj_div_2").append("<br/><hr>");
}
function appendDefaultDylcfjRule(index){
	dylcfjRule_count++;
	
	var divDyIndex=$("<span>第:</span><input size=3 name=\"dyIndex_"+dylcfjRule_count+"\"  id=\"dyIndex_"+dylcfjRule_count+"\" value=\""+index+"\"  disabled=\"disabled\"  type=\"text\" />");
	var divLcFrom=$("<span>单元　　　第:</span><input size=3 name=\"lcfrom_"+dylcfjRule_count+"\"  id=\"lcfrom_"+dylcfjRule_count+"\" value=\"1\" disabled=\"disabled\" type=\"text\" />");
	var divLcTo=$("<span>层,　到第:</span><input size=3 name=\"lcto_"+dylcfjRule_count+"\"  id=\"lcto_"+dylcfjRule_count+"\" type=\"text\" />");
	var divMcFjs=$("<span>层　　　每层房间数:</span><input size=6 name=\"mcfjs_"+dylcfjRule_count+"\"  id=\"mcfjs_"+dylcfjRule_count+"\" type=\"text\" />");

	$("#dylcfj_div_2").append(divDyIndex);
	$("#dylcfj_div_2").append(divLcFrom);
	$("#dylcfj_div_2").append(divLcTo);
	$("#dylcfj_div_2").append(divMcFjs);
	$("#dylcfj_div_2").append("<br/><hr>");
}
function getDylcfjRules(){
	var dylcfjRules = new Array();
	for(var i=1;i<=dylcfjRule_count;i++){
		var dyIndex = $("#dyIndex_"+i+"").val();
		var lcfrom = $("#lcfrom_"+i+"").val();
		var lcto = $("#lcto_"+i+"").val();
		var mcfjs = $("#mcfjs_"+i+"").val();
		var dylcfjRule = new DylcfjRule(dyIndex,lcfrom,lcto,mcfjs);
		dylcfjRules.push(dylcfjRule);
	}
	return dylcfjRules;
}
//------------------------房间号 前缀设置----------------------
//前缀规则数目
var prefix_count=0;
//增加前缀规则
function appendPrefixRule(){
	prefix_count++;
	
	var divfrom=$("<span>从第:</span><input size=3 name=\"f_lcs"+prefix_count+"\"  id=\"f_lcs"+prefix_count+"\" type=\"text\" />");
	var divto=$("<span>层,　到第:</span><input size=3 name=\"t_lcs"+prefix_count+"\"  id=\"t_lcs"+prefix_count+"\" type=\"text\" />");
	var divvalue=$("<span>层,　前缀为:</span><input size=6 name=\"prefix_value"+prefix_count+"\"  id=\"prefix_value"+prefix_count+"\" type=\"text\" />");
	
	$("#prefix_div").append(divfrom);
	$("#prefix_div").append(divto);
	$("#prefix_div").append(divvalue);
	$("#prefix_div").append("<br/><br/>");
}
	
function getPrefixRules(){
	var prefixRules = new Array();
	for(var i=1;i<=prefix_count;i++){
		var from = $("#f_lcs"+i+"").val();
		var to = $("#t_lcs"+i+"").val();
		var value = $("#prefix_value"+i+"").val();
		var prefixRule = new PrefixRule(from,to,value);
		prefixRules.push(prefixRule);
	}
	return prefixRules;
}
	
//检验房间前缀规则楼层数填写是否错误
function validatePrefixRules(){
	if(prefix_count>0){
		
		var prefixRules = new Array();
		prefixRules = getPrefixRules();
		for(var i=0;i<prefix_count;i++){
			var prefix = prefixRules[i];
			var re = new RegExp("^[0-9]*[1-9][0-9]*$");
			if(prefix.value==""){
				 alertMsg.error("房间号前缀规则定义错误，请填写房间号前缀！");
				 return false;
			}
			if((!re.test(prefix.from))||(!re.test(prefix.to))){
				 alertMsg.error("房间号前缀规则定义错误，楼层数请填写数字！");
				 return false;
			}
			if(parseInt(prefix.from)>parseInt(prefix.to)){
				 alertMsg.error("房间号前缀规则定义错误，楼层填写错误，起始楼层应小于结束楼层！");
				 return false;
			}
			for(var j=i+1;j<prefix_count;j++){
				var prefix2 = prefixRules[j];
				if(((parseInt(prefix.from)>=parseInt(prefix2.from))&&(parseInt(prefix.from)<=parseInt(prefix2.to)))
						||((parseInt(prefix.to)>=parseInt(prefix2.from))&&(parseInt(prefix.to)<=parseInt(prefix2.to)))){
					 alertMsg.error("房间号前缀规则定义混乱，请不要交叉楼层定义！");//+prefix.from+"-"+prefix.to+"||"+prefix2.from+"-"+prefix2.to
					 return false;
				}
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
	//地上楼层单元房间规则排列标志
	var isRegular = "false";
	var dys = $("#dys").val();
	
	//检验房间前缀规则楼层数填写是否错误
	if(!validatePrefixRules()){return;}
	//检测建筑副结构必填数据是否完整
	if(!getJzwjg_simple()){return;}
	var jzwjg;
	//提取建筑物Id
	var jzwId = $("#jzwId").val();
	//提取房间号 前缀设置
	var prefixRules = getPrefixRules();
	
	//提取楼层单元房间规则设置
	var dylcfjRules = getDylcfjRules();
	//提取单元/楼层/房间 后缀设置
	var postfix_dy = $("#postfix_dy").val();
	var postfix_lc = $("#postfix_lc").val();
	var postfix_fj = $("#postfix_fj").val();
	//提取单元号/楼层号/房间号 位数设置
	var width_dy = $("#width_dy").val();
	var width_lc = $("#width_lc").val();
	var width_fj = $("#width_fj").val();
	
	var postfixAndWidthRule = new PostfixAndWidthRule(postfix_dy,postfix_lc,postfix_fj,width_dy,width_lc,width_fj);
	
	var view="";
	//获取地下房间设置
	if(dx_count>0){//地下房间不规则排列
		var dxlc = getDxlc();
		jzwjg = getJzwjg_simple();
		view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":dylcfjRules,"prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"dxlc":dxlc,"type":"2"};
	}else{//无地下室
		jzwjg = getJzwjg_simple();
		view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":dylcfjRules,"prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"0"};
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
		<form id="fm" method="post" >
			<input type="hidden" id="jzwId" name="jzwId" value="${jzwjbxx.dzbm}"/>
			<fieldset>
				<legend><img src="static/images/fromedit.png" style="margin-bottom: -3px;" />建筑物结构采集</legend>
				<table id="formTbale" cellpadding="4">
					<tr>
						<th>建筑物名称</th>
					    <td >
					    	<input type="text" id="jzwmc" value="${jzwjbxx.jzwmc }"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,300]'" />
					    </td>	
					    <th>地址名称</th>
					    <td>
					    	<input type="text" id="dzmc" value="${jzwjbxx.dzmc }"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,300]'" style="width: 450px" />
					    </td>
					    <th>单元楼层房间规则</th>
					    <td>
					        <div id="dylcfj_btn" style="float:right"><input type="button" value="新增规则" onclick="appendDylcfjRule();" /></div>
					    </td>				
					</tr>
					<tr><td colspan="6"><hr></td></tr>
					<!-- 建筑物结构 -->
					<tr>
						<td colspan="6" style="border-width:1px;">
							<div id="dylcfj_div" style="height: 120px;overflow-y: scroll;">
								单元数：<input type="text" id="dys" name="dys" title="单元数" value="0" class="easyui-numberbox easyui-validatebox form-control" min="0" required="required" data-options="validType:'length[1,3]'">
								<hr/>
								<div id="dylcfj_div_2">
									
								</div>
							</div>
						</td>
					</tr>
			
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<td colspan="6"><center><font color="blue">后缀设置</font></center></td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<th>单元后缀</th>
						<td><input type="text" id="postfix_dy" name="postfix_dy" value="单元" class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,3]'"></td>
					
						<th>楼层后缀</th>
						<td><input type="text" id="postfix_lc" name="postfix_lc" value="层"  class="easyui-textbox easyui-validatebox form-control"	data-options="validType:'length[0,3]'"></td>
						
						<th>房间后缀</th>
						<td><input type="text" id="postfix_fj" name="postfix_fj" value="室"  class="easyui-textbox easyui-validatebox form-control" data-options="validType:'length[0,3]'"></td>
						
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<td colspan="6"><center><font color="blue">位数设置</font></center></td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
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
						<td colspan="3">
							<center>
								<font color="blue">房间号前缀设置</font>
								<input type="button" value="新增前缀规则" onclick="appendPrefixRule();" />
							</center>
						</td>
						<td colspan="3">
							<center>
							地下室
					        	<div id="dx_btndiv" style="float:right"><input type="button" value="新增一层" onclick="appendDxcs();" /></div>
							</center>
						</td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
					<tr>
						<td colspan="3" style="border-width:1px;">
							<div id="prefix_div" style="height: 120px;overflow-y: scroll;">
							</div>
						</td>
						<td colspan="3">
							<div id="dx_div" style="height: 120px;overflow-y: scroll;">
								<!-- 地下房间DIV   不规则 -->
								<div id="dxfj_div2" style=""></div>
							</div>
						</td>
					</tr>
					<tr><td colspan="6"><hr/></td></tr>
				</table>
				
			</fieldset>
			<div style="position: absolute;bottom: 5px;right: 10px;">
				<km:widgetTag widgetRulevalue="jzwjbxx.addjzwjg">
					<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="false" onclick="save()">保 存</a>
				</km:widgetTag>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="cancel()">关  闭</a>
			</div>
		</form>
	</div>
</div>
<!-- 地下房间DIV    规则 -->
<div id="dxfj_div1" style="display: none;">
	地下层数:<input type="text" name="dxcs" id="dxcs" size=12 class="rasyui-numberbox easyui-validatebox" min="0" data-options="validType:'length[0,3]'">
</div>

