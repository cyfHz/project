(function ($) {
    $.fn.extend({
    	drawingTool: function (options) {
            var settings = $.extend({}, options);
            var map = settings.map;
            return this.each(function () {
                var control = $(this);
                var panel = $('<div class="drawing-panel"></div>').appendTo(control);
                //var drawTypes =  ['pan',  'drawPoint', 'drawRect' , 'drawCircle','drawPolyline','drawPolygon'];
                var drawTypes =  ['pan',  'drawPoint', 'drawRect' , 'drawCircle'];
                
                $(drawTypes).each(function(i,t){
                	var titleX="";
                	if('pan'==t){
                		titleX="平移地图";
                	}else if('drawPoint'==t){
                		titleX="信息采集";
                	}else if('drawRect'==t){
                		titleX="矩形检索";
                	}else if('drawCircle'==t){
                		titleX="圆形检索";
                	}
                	
                	var box = $('<a title="'+titleX+'" class="drawing-toolbox tool-'+t+(t=='drawPolygon' ? ' tool-last':'')+' " drawingtype="'+t+'" href="javascript:void(0)"  onfocus="this.blur()"></a>').appendTo(panel);
               	 	box.click(function(){
               	 	if('pan'!=t){
               	 		removeAllOverlay();
               	 	}
               	 	
	               	 	$('.drawing-panel a').each(function(i, v) {
	            			v.className = v.className.replace(/-hover/, "");
	            		});
	               		 var currDragMode = map.getDragMode();
	               		 
	               		 if (t != currDragMode) {
	            				$(this).removeClass('tool-' + t);
	            				$(this).addClass('tool-' + t + '-hover');
	            				map.changeDragMode(t, dataInputx, dataInputy, settings['on_'+t]||function(){});
	            			} else {
	            				$(this).addClass('tool-' + t);
	            				$(this).removeClass('tool-' + t + '-hover');
	            				map.changeDragMode('pan');
	            			}
	               	 });
                });
                
            });
        }
    });
})(jQuery);