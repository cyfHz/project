$(function() {
	$('input[dict]').each(function() {
		var dict = $(this).attr('dict');
		$(this).combobox({
			data : window.top[dict],
			valueField : 'DICT_CODE',
			textField : 'DICT_MC'
		});
		$(this).removeAttr('dict');
	});
});
function dictFormat(dict){
	return function(value){
		if(window.top[dict]){
			for(var i=0; i<window.top[dict].length; i++){
				if (window.top[dict][i].DICT_CODE == value) return window.top[dict][i].DICT_MC;
			}
		}
		return value;
	}
}