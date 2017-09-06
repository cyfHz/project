	var centerMainWorkSpaceTabs;
	var tabsRightKeyMenus;
	$(function() {
		tabsRightKeyMenus = $('#tabsRightKeyMenus').menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('type');
				if (type === 'refresh') {
					refreshTab(curTabTitle);
					return;
				}
				if (type === 'close') {
					var t = centerMainWorkSpaceTabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						centerMainWorkSpaceTabs.tabs('close', curTabTitle);
					}
					return;
				}
				var allTabs = centerMainWorkSpaceTabs.tabs('tabs');
				var closeTabsTitle = [];

				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				for ( var i = 0; i < closeTabsTitle.length; i++) {
					centerMainWorkSpaceTabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});

		centerMainWorkSpaceTabs = $('#centerMainWorkSpaceTabs').tabs({
			fit : true,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				tabsRightKeyMenus.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			}
		});
		
	});
	function addCenterTab(tabTitle, tabUrl, iconCls) {
		if (centerMainWorkSpaceTabs.tabs('exists',tabTitle)) {
			centerMainWorkSpaceTabs.tabs('select', tabTitle);
		} else {
				centerMainWorkSpaceTabs.tabs('add', {
					title : tabTitle,
					closable : true,
					//border:false,
					iconCls : iconCls,
					//href : 'error/error.jsp',
					content : "<iframe src="+tabUrl+" frameborder=\"0\" style=\"border:0;width:100%;height:99.7%;overflow: hidden;\"></iframe>",
					tools : [ {
						/*iconCls : 'icon-mini-refresh',
						handler : function() {
							refreshTab(tabTitle);
						}*/
					} ]
				});
			
		}
	}
	function refreshTab(title) {
		var tab = centerMainWorkSpaceTabs.tabs('getTab', title);
		centerMainWorkSpaceTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}
	
	function closeSelectedTab(){
		var index = centerMainWorkSpaceTabs.tabs("getTabIndex", centerMainWorkSpaceTabs.tabs("getSelected"));
		var tab = centerMainWorkSpaceTabs.tabs("getTab", index);
		if(!tab){
			alertMsg.error("未找到该TAB");
			return ;
		}
		if (tab.panel('options').closable) {
			centerMainWorkSpaceTabs.tabs("close", index);
		} else {
			alertMsg.error('[' + tab.panel('options').title + ']  不可以被关闭');
		}
	}
	function reloadSelectedTab(){
		var index =centerMainWorkSpaceTabs.tabs('getTabIndex', centerMainWorkSpaceTabs.tabs('getSelected'));
		var tab = centerMainWorkSpaceTabs.tabs('getTab', index);
		if(!tab){
			alertMsg.error("未找到该TAB");
			return ;
		}
		var href = tab.panel('options').href;
		if (href) {/*说明tab是以href方式引入的目标页面*/
			centerMainWorkSpaceTabs.tabs('getTab', index).panel('refresh');
		} else {   /*说明tab是以content方式引入的目标页面*/
			var panel = centerMainWorkSpaceTabs.tabs('getSelected').panel('panel');
			var frame = panel.find('iframe');
			try {
				if (frame.length > 0) {
					for ( var i = 0; i < frame.length; i++) {
						frame[i].contentWindow.document.write('');
						frame[i].contentWindow.close();
						frame[i].src = frame[i].src;
					}
					//if ($.browser.msie) {
					if(/msie/.test(navigator.userAgent.toLowerCase())){//IE
						CollectGarbage();
					}
				}
			} catch (e) {
			}
		}
	}