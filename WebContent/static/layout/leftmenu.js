$(function() {
	// 导航菜单绑定初始化
	var $ma=$("#leftSideMenuAccordion");
	 $ma.accordion({animate:true,fit:true,border:false,collapsible: true});
	 var data;// =accdata;
	 var axajurl="loadMenus";//"menu.json"//loadMenus
	 KMAJAX.ajaxTodo(axajurl, {}, function(dataxxs){
		 statuscode=dataxxs.statusCode;
		 if(statuscode&&statuscode!=200){
			 alertMsg.error(dataxxs.message);
		 }else{
				data=dataxxs.data;
				addNav(data);
				$ma.resize();
				initLeftMenu(data); 
		 }
	
	 });
	 
});

// 初始化左侧
function initLeftMenu(data) {
	//hoverMenuItem();
	$('#leftSideMenuAccordion li a').live('click', function() {
		var tabTitle = $(this).children('.nav').text();
		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(data,menuid);
		addCenterTab(tabTitle, url, icon);
	});
}
//获取左侧导航的图标
function getIcon(data,menuid) {
	var icon = 'icon ';
	$.each(data, function(j, o) {
		$.each(o.menus, function(k, m){
			if (m.menuid == menuid) {
				icon += m.icon;
				return false;
			}
		});
	});
	return icon;
}

function addNav(data) {
	$.each(data, function(i, sm) {
		var menulist = "";
		menulist += '<ul>';
		$.each(sm.menus, function(j, o) {
		//	var tabUrl=(o.url).substr(1);
			var tabUrl=(o.url);
			if(tabUrl&&"/"==tabUrl.substring(0,1)){
				 tabUrl=(o.url).substr(1);
			}
			menulist += '<li><div class="enterout"><a ref="' + o.menuid + '" href="#" rel="'+ tabUrl + '" ><span class="icon ' + o.icon + ' " >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="nav">' + o.menuname+ '</span></a></div></li> ';
		});
		menulist += '</ul>';
		$('#leftSideMenuAccordion').accordion('add', {
			title : sm.menuname,
			content : menulist,
			iconCls : 'icon ' + sm.icon,
			selected: false
		    //active: 0
		});
	});
}
function clearLeftNav() {
	var accpanels = $('#leftSideMenuAccordion').accordion('panels');
	$.each(accpanels, function(i, node) {
		if (node) {
			var t = node.panel('options').title;
			$('#leftSideMenuAccordion').accordion('remove', t);
		}
	});
	accpanels = $('#leftSideMenuAccordion').accordion('getSelected');
	if (pp) {
		var title = pp.panel('options').title;
		$('#leftSideMenuAccordion').accordion('remove', title);
	}
}

function hoverMenuItem() {
//	$('#leftSideMenuAccordion li div').hover(function() {
//		alert("mouseenter ");
//		$(this).addClass("hover");
//	}, function() {
//		alert("mouseleave ");
//		$(this).removeClass("hover");
//	});
//	$('#leftSideMenuAccordion li a').hover(function() {
//		alert("mouseenter ");
//		$(this).parent().addClass("hover");
//	}, function() {
//		alert("mouseleave  ");
//		$(this).parent().removeClass("hover");
//	});
//	$('.enterout').hover(function() {
//		alert("mouseenter ");
//		$(this).addClass("hover");
//	}, function() {
//		alert("mouseleave ");
//		$(this).removeClass("hover");
//	});
////	
//	$('#leftSideMenuAccordion').hover(function(e){
//		var target = e.target;
//		console.log('x');
//		console.log($(target).hasClass('enterout'));
//	});
}
