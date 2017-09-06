var KMAJAX={
		validateFromCallback:function (form, callback ) {
		var $form = $(form);
		if (!$form.form('validate')) {
			return false;
		}
		var _submitFn = function(){
			//loadMask.open();
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || KMCORE.ajaxDone,
				error: KMCORE.ajaxError,
				complete:function(){loadMask.close();}
			});
		};
		_submitFn();
		return true;
	},
	validateFromCallbackJson:function (form, callback ) {
		var $form = $(form);
		var params = $form.serializeArray();
		if (!$form.form('validate')) {
			return false;
		}
		var _serializeObject = function(params){
			var o ={};
			$.each(params,function(){
				if(o[this.name]){
					if(!o[this.name].push){
						o[this.name] = [o[this.name]];
					}
					o[this.name].push(this.value||'');
				}else{
					o[this.name] = this.value||'';
				}
			});
			return o;
		};
		var _submitFn = function(){
			//loadMask.open();
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:JSON.stringify(_serializeObject(params)),
				dataType:"json",
				contentType:"application/json",      
				cache: false,
				success: callback || KMCORE.ajaxDone,
				error: KMCORE.ajaxError,
				complete:function(){loadMask.close();}
			});
		};
		_submitFn();
		return true;
	},
	ajaxTodo : function(url, param, callback) {
		//loadMask.open();
		$.ajax({
			type : 'POST',
			url : url,
			data : $.param(param, true),
			dataType : "json",
			cache : false,
			success : callback,
			error : KMCORE.ajaxError,
			complete : function() {
				loadMask.close();
			}
		});
	},
	ajaxTodo1 : function(url, param, callback) {
		//loadMask.open();
		$.ajax({
			async:false,
			type : 'POST',
			url : url,
			data : $.param(param, true),
			dataType : "json",
			cache : false,
			success : callback,
			error : KMCORE.ajaxError,
			complete : function() {
				loadMask.close();
			}
		});
	},
	ajaxTodoJson:function(url, param, callback){
		$.ajax({
			type : 'POST',
			url : url,
			data : param,
			dataType : "json",
			contentType:"application/json",      
			cache : false,
			success : callback,
			error : KMCORE.ajaxError,
			complete : function() {
				loadMask.close();
			}
		});
	}
	
};

