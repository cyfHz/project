<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/static/meta/includeall.jsp" %>
<title></title>
<link rel="stylesheet" id="buttons-css" href="./static/loginsource/buttons.min.css" type="text/css" media="all">
<link rel="stylesheet" id="dashicons-css" href="./static/loginsource/dashicons.min.css" type="text/css" media="all">
<link rel="stylesheet" id="login-css" href="./static/loginsource/login.min.css" type="text/css" media="all">
<script type="text/javascript">
$(function(){
	$("#username").bind("keyup", function(event){
		   if (event.keyCode=="13"){
		      $("#password").focus();
		   }
		});
		
		$("#password").bind("keyup", function(event){
			if (event.keyCode=="13"){
				 $("#loginButton").focus();
				
		   }
		});
});

function reloadValidateCode(){
    $("#validateCodeImg").hide().attr("src","${ctx}/jcaptcha/jcaptcha.jpg?" + Math.floor(Math.random()*100)).fadeIn();
}
function convertPassword(username,password){
	//pwxwT1d6SMiYXYZ0ARGFhg+_94DABGioQOq2tTUO0AXYow
	var salt=username+"@zhtframework_94DABGioQOq2tTUO0AXYow";
//	var salt=username;
	//return hex_md5(password+"{"+salt+"}");
	return hex_md5(salt+password);
}
function dologin(){
	var username = $("#username");
	var password = $("#password");
	if(username.val().length < 1){
		$("#login_error").empty();
		$("#login_error").append(" <strong>错误</strong>： 请输入帐号");
		$("#login_error").css('display','block'); 
		username.focus();
		return;
	}
	if(password.val().length < 3){
		
		$("#login_error").empty();
		$("#login_error").append("<strong>错误</strong>：  密码至少3位");
		$("#login_error").css('display','block'); 
		password.focus();
		return;
	}
	//$("#password").val(convertPassword(username.val().trim(),password.val().trim()));
	KMAJAX.validateFromCallback($("#loginForm"), function(json){
		if(json.statusCode == KMCORE.statusCode.error) {
			$("#password").val("");
			if(json.message && alertMsg){
				$("#login_error").empty();
				$("#login_error").append("<strong>错误</strong>：  "+json.message);
				$("#login_error").css('display','block'); 
			}
		}else if(json.statusCode == KMCORE.statusCode.serverError) {
			$("#password").val("");
			if(json.message && alertMsg){
				$("#login_error").empty();
				$("#login_error").append("<strong>错误</strong>：  "+json.message);
				$("#login_error").css('display','block'); 
			}
		}else {
			$("#password").val("");
			if(json.message && alertMsg){
				setTimeout(function(){
					window.top.location.href="${ctx}/core";
				},500);
				
			}
		};
	});
}
function gotoIndex(){
	window.top.location.href="${ctx}/core";
}
</script>
</head>
<body class="login login-action-login wp-core-ui  locale-zh-cn">
<div id="login">
<div id="login_error" style="display: none;"></div>
<!--
<h1><a href="" title="" tabindex="-1"></a></h1>
  -->
<form name="loginform" id="loginForm" action="${ctx}/login" method="post">
	<p>
		<label for="user_login">用户名<br>
		<input type="text" id="username" name="username"  class="input" value="" size="20"></label>
	</p>
	<p>
		<label for="user_pass">密码<br>
		<input type="password" name="password" id="password" class="input" value="" size="20"></label>
	</p>
	<!-- 
	<p>
	<label for="jcaptchaCode">验证码<br>
		 <input type="text"  style="width:160px;height: 40px" id="jcaptchaCode"  name="jcaptchaCode" class="input" value="" maxlength="18" size="10">
		 <img style="margin: 0px;border: 0px" border="0" width="90" height="35" id="validateCodeImg" src="${pageContext.request.contextPath}/jcaptcha/jcaptcha.jpg" onclick="javascript:reloadValidateCode();" />
	</p>
	 -->
	<link href="./static/loginsource/login.css" rel="stylesheet" media="all">

<ul class="iteblog-open">
	<li>
		<a id="loginButton" class="zg-btn-blue" href="#" onclick="dologin();">
		</span>登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</a>
		</a>
	</li>
</ul>
</form>

<p id="nav">
<!-- 
	<a href="" title="找回密码">忘记密码？</a>
	 -->
</p>

<script type="text/javascript">
function wp_attempt_focus(){
setTimeout( function(){ try{
d = document.getElementById('user_login');
d.focus();
d.select();
} catch(e){}
}, 200);
}

wp_attempt_focus();
if(typeof wpOnload=='function')wpOnload();
</script>

	</div>
		<div class="clear"></div>
	</body></html>