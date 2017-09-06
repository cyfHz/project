/**
 * @author zhaohuatai@kingmon.com
 * 
 */

var KMCORE = {
	statusCode: {ok:200, error:300, timeout:301, authfailed:401,invalidToken:402,serverError:500},
		
	keyCode: {
		ENTER: 13, ESC: 27, END: 35, HOME: 36,
		SHIFT: 16, TAB: 9,
		LEFT: 37, RIGHT: 39, UP: 38, DOWN: 40,
		DELETE: 46, BACKSPACE:8
	},
	
	msg:function(key, args){
		var _format = function(str,args) {
			args = args || [];
			var result = str || "";
			for (var i = 0; i < args.length; i++){
				result = result.replace(new RegExp("\\{" + i + "\\}", "g"), args[i]);
			}
			return result;
		};
		return _format(this._msg[key], args);
	},
	
	ajaxError:function(xhr, ajaxOptions, thrownError){
		loadMask.close();
		if (alertMsg) {
			alertMsg.error("<div>Http status: " + xhr.status + " " + xhr.statusText + "</div>" 
				+ "<div>ajaxOptions: "+ajaxOptions + "</div>"
				+ "<div>thrownError: "+thrownError + "</div>"
				+ "<div>"+xhr.responseText+"</div>");
		} else {
			alert("Http status: " + xhr.status + " " + xhr.statusText + "\najaxOptions: " + ajaxOptions + "\nthrownError:"+thrownError + "\n" +xhr.responseText);
		}
	},

	ajaxDone:function(json){
		if(json.statusCode == KMCORE.statusCode.error) {
			if(json.message && alertMsg){
				alertMsg.error(json.message);
			}
		}else if(json.statusCode == KMCORE.statusCode.serverError) {
			if(json.message && alertMsg){
				alertMsg.error(json.message);
			}
		} else if (json.statusCode == KMCORE.statusCode.timeout) {
			if(alertMsg) {
				alertMsg.error(json.message || KMCORE.msg("sessionTimout"), {okCall:KMCORE.loadLogin});
			}
			else {
				KMCORE.loadLogin();
			}
		} else {
			if(json.message && alertMsg){
				alertMsg.correct(json.message);
			}
		};
	},
	ajaxDoneAndCloseDialog:function(json){
		if(json.statusCode == KMCORE.statusCode.error) {
			if(json.message && alertMsg){
				alertMsg.error(json.message);
			}
		} else if(json.statusCode == KMCORE.statusCode.serverError) {
			if(json.message && alertMsg){
				alertMsg.error(json.message);
			}
		} else if (json.statusCode == KMCORE.statusCode.timeout) {
			if(alertMsg) {
				alertMsg.error(json.message || KMCORE.msg("sessionTimout"), {okCall:KMCORE.loadLogin});
			}
			else {
				KMCORE.loadLogin();
			}
		} else {
			if(json.message && alertMsg){
				alertMsg.correct(json.message);
				editDialog.close(1000);//0604 17:48
			}
		};
	},
	ajaxDoneForServerError:function(result){
		if(!result){
			alertMsg.error("未返回任何数据");
		}else if(result.statusCode==300){
			alertMsg.error(result.message);
		}else if(result.statusCode==500){
			alertMsg.error(result.message);
		}else if(result.statusCode==401){
			alertMsg.error(result.message);
		}else{
			//KMCORE.loadLogin();
		}
		return result;
	},
	loadLogin:function(){
		alert("loadLogin");
	    var url="${ctx}/login.jsp";
		var options={title:"用户登录",width:'100%',height:'100%', 
				url:url,params:{},onClosed:function(){}};
		editDialog.open(options);
	}
};

