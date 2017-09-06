
$.loadMaskDialog = function(options) {
	//if (window.top.find("#commonEditDialog_div")==false) {// 避免重复弹出
		var opts = $.extend({
			title:"",
			width : 150,
			height : 46,
            maximizable: true,
			modal : true
		}, options);
		opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
		var winddd=window.top.$("<div id=\"loadMaskDialog_div\" class=\"easyui-dialog\"><div/>");
		if(winddd){
			winddd.empty();
		}
		var targetDialog=window.top.$("<div id=\"loadMaskDialog_div\" style=\"text-align:center;line-height:30px\" class=\"easyui-dialog\"><a class=\"easyui-linkbutton\" href=\"javascript:void(0)\" plain=\"true\">数据处理中...</a><div/>").dialog(opts);
			$.parser.parse(targetDialog);
		return $.loadMaskDialog.handler =targetDialog;
	//}
};


loadMask = {
	close: function(timeout){
		if(timeout){
			setTimeout(function(){
				var tmp=window.top.$("#loadMaskDialog_div");
					if(tmp){
						window.top.$("#loadMaskDialog_div").dialog('close');
						window.top.$("#loadMaskDialog_div").dialog('destroy');
						window.top.$("#loadMaskDialog_div").remove();
					}
//				window.top.$("#loadMaskDialog_div").dialog('close');
//				window.top.$("#loadMaskDialog_div").dialog('destroy');
//				window.top.$("#loadMaskDialog_div").remove();
			}, timeout);
		}else{
			var tmp=window.top.$("#loadMaskDialog_div");
			if(tmp){
				window.top.$("#loadMaskDialog_div").dialog('close');
				window.top.$("#loadMaskDialog_div").dialog('destroy');
				window.top.$("#loadMaskDialog_div").remove();
			}
//			window.top.$("#loadMaskDialog_div").dialog('close');
//			window.top.$("#loadMaskDialog_div").dialog('destroy');
//			window.top.$("#loadMaskDialog_div").remove();
		}

		
	},
	open: function() {
		var tmp=window.top.$("#loadMaskDialog_div");
		if(tmp){
			window.top.$("#loadMaskDialog_div").dialog('destroy');
			window.top.$("#loadMaskDialog_div").remove();
		}
//		window.top.$("#loadMaskDialog_div").dialog('destroy');
//		window.top.$("#loadMaskDialog_div").remove();
		$.loadMaskDialog();
	}
};

