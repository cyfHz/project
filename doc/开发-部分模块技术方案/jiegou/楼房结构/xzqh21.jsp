<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">
<title></title>
<%@ include file="/static/meta/includeall.jsp" %>
<style type="text/css">
/*分类栏目通用样式*/
.one{ float:left; width:100px; height:100px; border:3px solid #000;border-color: blue; } 
.onehalf{ float:left; width:40px; height:100px; border:3px solid #000;border-color: blue; } 
.onehalf-v{ float:left; width:100px; height:40px; border:3px solid #000;border-color: blue; } 
.onehalf-v-x{ width:100px;position:absolute; height:50px; border:3px solid #000;border-color: blue; }
.two{ clear:both; } 
</style>
<script>
  $(function (){
	  init();
  });

  function init(){
	  
	  $("#container div").click(function(){
		  if($(this).hasClass("tdx")){
			  removeSelectedCss($(this))
		  }else{
			  addSelectedCss($(this));
		  }
	  });
	  $("#container div.onehalf-v").click(function(){
		  if($(this).hasClass("tdx")){
			  removeSelectedCss($(this))
		  }else{
			  addSelectedCss($(this));
		  }
	  });
	  $("#container div.onehalf-h").click(function(){
		  if($(this).hasClass("tdx")){
			  removeSelectedCss($(this))
		  }else{
			  addSelectedCss($(this));
		  }
	  });
  }  
  function addSelectedCss(domId){
	  $("#container div.one").each(function(index,element){
		  if($(this).attr("id")==domId.attr("id")){
			  $(this).addClass("tdx");
		  }
	  });
  }
 function removeSelectedCss(domId){
	// alert(domId.attr("id"));
	// domId.removeClass("tdx");
	 $("#container div.one").each(function(index,element){
		  if($(this).attr("id")==domId.attr("id")){
			  $(this).removeClass("tdx");
		  }
	  });
  }
  function hebing(){
	  $("#container div.one").each(function(index,element){
		  if($(this).hasClass("tdx")){
			  $(this).attr({id:"lili"});
			  alert($(this).text());
		  }
	  })
  }
  function checkHebing(){
	  
  }
  function show(){
	 //var ids= $("#lili").attr("id");
	 //alert(ids);
	 alert($("#container").html());
	}
  function chaifen(){
	  if(-1==checkChaifen()){
		  alert("请选择要拆分的房屋");	  return ;

	  } 
	  if(!checkChaifen()){
		  alert("选中超过一户，不可拆分"); return ;
	  }
	  $('#dlg').dialog('open');
	  var fx=$().val("#fx");
	  var hs=$().val("#hs");
	  alert(fx+" "+hs);
  }
function checkChaifen(){
	var ids=[];
	$("#container div.one").each(function(index,element){
		  if($(this).hasClass("tdx")){
			  ids.push($(this).attr("id"));
		  }
	  });
	if(ids.length==0){
		return -1;
	}
	if(ids.length==1){
		return true;
	}
	for(var i =0; i<ids.length-1; i++){
		if(ids[i]!=ids[i+1]){
			return false;
		}
	}
	return true;
}
</script>
<style type="text/css">
.tdx{
background-color: #CD0000;
}

</style>
</head>

<body><br/>
<input type="button" value="显示" onclick="show();" />
<input type="button" value="合并" onclick="hebing();" />
<input type="button" value="拆分" onclick="chaifen();" />
<br/><br/>
<div id="container">
<div class="two">
   <div id="2-1" class="one">2-1</div>
   <div id="2-2" class="one">2-2</div>
   <div id="2-3" class="one" >2-3</div>
   <div id="2-4" class="one" >2-4</div>
   <div id="2-5" class="one">2-5</div>
   <div id="2-6" class="one">2-6</div>
</div>
<div class="two">
   <div id="1-1" class="one" >1-1</div>
   <div id="1-2" class="one" >1-2</div>
    <div id="1-3" class="one" >
	  	<div id="1-3-1" class="onehalf" >1-3-1</div>
		<div id="1-3-2" class="onehalf">1-3-2</div>
	</div>
	 <div id="1-4" class="one" >1-4</div>
</div>
<!-- -->
<div class="two">
   <div id="0-1" class="one" >0-1</div>
   <div id="0-2" class="one" >0-2</div>
   <div id="0-3" class="one" >0-3</div>
   <div id="0-4" class="one" >0-4</div>
   <div id="0-5" class="one" >
  	<div id="0-5-1" class="onehalf-v" >0-5-1</div>
	 <div id="0-5-2" class="onehalf-v">0-5-2</div>		
 </div>
</div>
 
</div>

<div id="dlg" class="easyui-dialog" title="Basic Dialog" data-options="iconCls:'icon-save',closed: true" style="width:400px;height:400px;padding:10px">
		<form id="xzqhfm" class="form-horizontal" method="post" novalidate>
			<div class="form-group">
				<input type="hidden"  name="DZBM">
				<label>拆分方向:</label> <input id="fx" name="fx" class="form-control" required="true" />
			</div>
			<div class="form-group">
				<label>拆分户数:</label> <input id="hs" name="hs" class="form-control" required="true" />
			</div>
		</form>
</div>
</body>
</html>