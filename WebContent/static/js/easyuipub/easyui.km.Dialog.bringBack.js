$.returnBackWindow = function(options) {
		var opts = $.extend({
			width : 840,
			height : 600,
            maximizable: true,
			modal : true
		}, options);
		opts.modal = true;
		var winddd=window.top.$("<div id=\"returnBackDialog_div\" class=\"easyui-dialog\"><div/>");
		if(winddd){
			winddd.empty();
		}
		var targetDialog=window.top.$("<div id=\"returnBackDialog_div\" class=\"easyui-dialog\"><div/>");
			targetDialog.dialog(opts);
		return $.commonEditDialog.handler =targetDialog;
};


returnBackDialog = {
	close: function(timeout,returnValue){
		var divsss="<div id=\"div_lookupbackvarValue\"><input type=\"hidden\" value=\""+returnValue+"\" id=\"bringbacklookupbackvarValue\"/></div>";
		$(window.top.document.body).append(divsss); 
		if(timeout){
			setTimeout(function(){
				var tmp=window.top.$("#returnBackDialog_div");
				if(tmp){
					window.top.$("#returnBackDialog_div").dialog('close');
					window.top.$("#returnBackDialog_div").dialog('destroy');
					window.top.$("#returnBackDialog_div").remove();
				}
			}, timeout);
		}else{
			var tmp=window.top.$("#returnBackDialog_div");
			if(tmp){
				window.top.$("#returnBackDialog_div").dialog('close');
				window.top.$("#returnBackDialog_div").dialog('destroy');
				window.top.$("#returnBackDialog_div").remove();
			}
		}
		
	},
	open: function(options) {
		var tmp=window.top.$("#returnBackDialog_div");
		if(tmp){
			window.top.$("#returnBackDialog_div").dialog('close');
			window.top.$("#returnBackDialog_div").dialog('destroy');
			window.top.$("#returnBackDialog_div").remove();
		}
		window.top.$("#div_lookupbackvarValue").remove();
		var title= options.title;
		var height= options.height;
		var width= options.width;
		var paramsStr=this.getParams(options.params);
		var url= options.url;
		if(paramsStr&&paramsStr.toString().length>0){
			url+="?"+paramsStr;
		}
		 $.returnBackWindow({
				title : title,
				height:height,
				width:width,
				href : url,
				method:"post",
				onClose :function(){
					var returndata=window.top.$("#div_lookupbackvarValue").find('input[id="bringbacklookupbackvarValue"]').val();
					window.top.$("#returnBackDialog_div").dialog('destroy');
					window.top.$("#returnBackDialog_div").remove();
					window.top.$("#div_lookupbackvarValue").remove();
					options.onClosed(returndata);
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

