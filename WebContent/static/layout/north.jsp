<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<style>
.bgpic {
    background-image: url('static/images/banner.png');
    background-size: cover;
    filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(
    src='static/images/banner.png',
    sizingMethod='scale');
}
</style>
<div class="bgpic" style="width:100%;height: 60px;background-image: url('static/images/banner.png');background-repeat: repeat-x;;background-size:100% 100%;">
 <span style="margin-left:2px;height:40px;display:block;padding-top: 0px;">

<!--<font style="font-family: SimHei;font-weight:bold;font-size: 3em;margin-bottom: 0px;display:block;">
标准地（住）址管理系统
</font>-->
<img alt="" src="static/images/logo.png">
</span> 

</div>
<div style="position: absolute; right: 0px; bottom: 0px; width:180px; float: left;">
	<a href="javascript:void(0)" class="easyui-menubutton" data-options="plain:false"  menu="#layout_north_mkqhMenu_R4LxQY6CR4CFEnZso71mrg" iconCls="icon-help" >控制面板</a>
	<a id="btnHideNorth" onclick="KMEASYUtil.hiddenNorth();" class="easyui-linkbutton"  data-options="plain: true, iconCls: 'layout-button-up'"></a>
</div>
<div id="layout_north_mkqhMenu_R4LxQY6CR4CFEnZso71mrg" style="width: 100px; display: none;">
	<div onclick="showUserInfo();">个人信息</div>
	<div onclick="showChangePassWord();">修改密码</div>
	<div><a href="${pageContext.request.contextPath}/logout" target="_self">退出登录</a></div>
	
	<div class="menu-sep"></div>
	<div>
		<span>更换主题</span>
		<div style="width: 120px;">
			<div onclick="KMEASYUtil.changeTheme('default');">default</div>
			<div onclick="KMEASYUtil.changeTheme('gray');">gray</div>
			<div onclick="KMEASYUtil.changeTheme('bootstrap');">bootstrap</div>
		<!-- 	
			<div onclick="KMEASYUtil.changeTheme('black');">black</div>
			<div onclick="KMEASYUtil.changeTheme('metro');">metro</div>
			
			<div onclick="KMEASYUtil.changeTheme('metro-blue');">metro-blue</div>
			<div onclick="KMEASYUtil.changeTheme('metro-gray');">metro-gray</div>
			<div onclick="KMEASYUtil.changeTheme('metro-green');">metro-green</div>
			<div onclick="KMEASYUtil.changeTheme('metro-orange');">metro-orange</div>
			<div onclick="KMEASYUtil.changeTheme('metro-red');">metro-red</div>
			<div onclick="KMEASYUtil.changeTheme('ui-cupertino');">ui-cupertino</div>
			<div onclick="KMEASYUtil.changeTheme('ui-dark-hive');">ui-dark-hive</div>
			<div onclick="KMEASYUtil.changeTheme('ui-pepper-grinder');">ui-pepper-grinder</div>
			<div onclick="KMEASYUtil.changeTheme('ui-sunny');">ui-sunny</div>
			 -->
		</div>
	</div>

</div>
<script type="text/javascript">
function showUserInfo(){
	var ajaxUrl="${pageContext.request.contextPath}/auth/organizationUser/loadUserInfo";
	var param={};
	/* KMAJAX.ajaxTodo(ajaxUrl,param,function(data){
		KMCORE.ajaxDone(data);
	}); */
	var options={title:"个人信息",width:400,height:400, url:ajaxUrl,onClosed:function(){}};
	editDialog.open(options);
}
function showChangePassWord(){
	var url="${pageContext.request.contextPath}/auth/organizationUser/enterChangePassWord";
	var options={title:"密码修改",width:400,height:400, url:url,onClosed:function(){}};
	editDialog.open(options);

}
</script>	
