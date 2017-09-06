
$.commonEditDialogFront = function(options) {
		var opts = $.extend({
			width : 840,
			height : 600,
            maximizable: false,
			modal : true
		}, options);
		opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
		var winddd=window.top.$("<div id=\"commonEditDialog_front_div\" class=\"easyui-dialog\"><div/>");
		if(winddd){
			winddd.empty();
		}
		var targetDialog=window.top.$("<div id=\"commonEditDialog_front_div\" class=\"easyui-dialog\"><div/>").dialog(opts);
	//	$.parser.parse(targetDialog);
		return $.commonEditDialog.handler =targetDialog;
};


editDialogFront = {
	close: function(timeout){
		if(timeout){
			setTimeout(function(){
				var tmp=window.top.$("#commonEditDialog_front_div");
				if(tmp){
					window.top.$("#commonEditDialog_front_div").dialog('close');
					window.top.$("#commonEditDialog_front_div").dialog('destroy');
					window.top.$("#commonEditDialog_front_div").remove();
				}
				
			}, timeout);
		}else{
			var tmp=window.top.$("#commonEditDialog_front_div");
			if(tmp){
				window.top.$("#commonEditDialog_front_div").dialog('close');
				window.top.$("#commonEditDialog_front_div").dialog('destroy');
				window.top.$("#commonEditDialog_front_div").remove();
			}
		}

		
	},
	open: function(options) {
		var tmp=window.top.$("#commonEditDialog_front_div");
		if(tmp){
			window.top.$("#commonEditDialog_front_div").dialog('destroy');
			window.top.$("#commonEditDialog_front_div").remove();
		}
		var title= options.title;
		var height= options.height;
		var width= options.width;
		var paramsStr=this.getParams(options.params);
		var url= options.url;
		if(paramsStr&&paramsStr.toString().length>0){
			url+="?"+paramsStr;
		}
		 $.commonEditDialogFront({
				title : title,
				height:height,
				width:width,
				href : url,
				method:"post",
				onClose :function(){
					var tmp=window.top.$("#commonEditDialog_front_div");
					if(tmp){
						window.top.$("#commonEditDialog_front_div").dialog('destroy');
						window.top.$("#commonEditDialog_front_div").remove();
					}
					options.onClosed();
				}
			});
	},
	getParams:function(params){
		if(!params||params==undefined){
			return "";
		}
		var _str = ""; 
		 for(var obj in params){ 
			 _str += obj + "=" + params[obj] + "&";
		 }
		 _str = _str.substring(0, _str.length-1); 
		 return _str;
	}
};

