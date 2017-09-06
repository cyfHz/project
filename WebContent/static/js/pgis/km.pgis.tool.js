var toolxx='<div style="padding:1px;border:1px solid #ddd;">'+
	'<a href="#"  onclick="zclearOverlay();" class="easyui-linkbutton" data-options="iconCls:\'icon-km-mapdelete\',plain:true,toggle:true,group:\'g1\'">清除标注</a>'+
	'<a href="#"  onclick="zchangeDragModePan();" class="easyui-linkbutton" data-options="iconCls:\'icon-km-mapmove\',plain:true,toggle:true,group:\'g1\'">平移地图</a>'+
	'<a href="#"  onclick="zdrawPoint();" class="easyui-linkbutton" data-options="iconCls:\'icon-kmxsearchpan\',plain:true,toggle:true,group:\'g1\'">信息采集</a>'+
	'<a href="#"  onclick="zdrawRect();" class="easyui-linkbutton" data-options="iconCls:\'icon-kmxsearchreact\',plain:true,toggle:true,group:\'g1\'">矩形检索</a>'+
	'<a href="#"  onclick="zdrawCircle();"  class="easyui-linkbutton" data-options="iconCls:\'icon-kmxsearchcircle\',plain:true,toggle:true,group:\'g1\'">圆形检索</a>'+
	'<a href="#"  onclick="zpointAndCenter();"  class="easyui-linkbutton" data-options="iconCls:\'icon-km-mapzooncenter\',plain:true,toggle:true,group:\'g1\'">选点放大</a>'+
	'</div>';
var drowModex="";
var geoStr="";//全局的 多变相边界
function zclearOverlay(){
	var t = _MapApp.getDragMode();
	 if('drawPoint'==t){
		_MapApp.changeDragMode('drawRect', dataInputx, dataInputy,doRectangleSeach);
	}else if('drawRect'==t){
		_MapApp.changeDragMode('drawCircle', dataInputx, dataInputy,doCircleSearch);
	}else if('drawCircle'==t){
		_MapApp.changeDragMode('drawRect', dataInputx, dataInputy,doRectangleSeach);
	}
	removeAllOverlay();
	_MapApp.changeDragMode('drawPoint', dataInputx, dataInputy,doRectangleSeach);
	_MapApp.changeDragMode('pan',dataInputx, dataInputy,doRectangleSeach);
	drowModex='pan'
}
function zchangeDragModePan(){
	//drowModex='pan'
	_MapApp.changeDragMode('pan',dataInputx, dataInputy,doRectangleSeach);
}
function zdrawRect(){
	drowModex='drawRect'
	removeAllOverlay();
	_MapApp.changeDragMode('drawRect', dataInputx, dataInputy,doRectangleSeach);
}
function zdrawCircle(){
	drowModex='drawCircle'
	removeAllOverlay();
	_MapApp.changeDragMode('drawCircle', dataInputx, dataInputy,doCircleSearch);
}
function zdrawPoint(){
	drowModex='drawPoint'
	removeAllOverlay();
	_MapApp.changeDragMode('drawPoint', dataInputx, dataInputy,xinXiCaiJiTypeSelect);
}
function zpointAndCenter(){
	drowModex='drawPoint'
	_MapApp.changeDragMode('drawPoint', dataInputx, dataInputy,function (str){
			var xyArray = str.split(",");
			var pointX = xyArray[0];
			var pointY = xyArray[1];
			var pointxx=new Point(pointX,pointY);
			var level=_MapApp.getZoomLevel();
			if(level+3<15){
				level+=3;
			}else{
				level=15;
			}
			_MapApp.centerAndZoom(pointxx, level);
	});
}
(function ($) {
    $.fn.extend({
    	drawingTool: function (options) {
            var settings = $.extend({}, options);
            return this.each(function () {
                var control = $(this);
                var panel = $(toolxx).appendTo(control);
            });
        }
    });
})(jQuery);
//-----------------------------------------------------------------------------
function addMapStateChangeFunc(){
	_MapApp.addMapChangeListener(function() {
			var level = _MapApp.getZoomLevel();
			if (level >= 14) {
				isGt = true;
				if (isGt) {
					showSearchOnMap();
				}
			}
			if (level < 14) {
				isGt = false;
			}
		});
}

function delMapStateChangeFunc() {
		//_MapApp.removeMapChangeListener(_ArcImg.hander);
		//_MapApp.removeMapChangeListener(testMapChange);
}

function initDrawToolBar() {
	$("#drawing-toolbar").drawingTool({});
}
function clearMap() {
	_MapApp.clear();
}

function showText(iPos, pointx, titleMsg, detailMsg) {
	if (typeof iPos == "undefined" || iPos == null)iPos=7;
	var pTitle = new Title(titleMsg, 14, 7, "宋体", "blue", "white","red", 1);
	pTitle.setPoint(pointx);
	pTitle.addListener("click", function() {
			_MapApp.centerAtLatLng(pointx);
			pTitle.openInfoWindowHtml(detailMsg);
	});
	voArray.push(pTitle);
	_MapApp.addOverlay(pTitle);
}
function showText2(iPos, pointx, titleMsg, detailMsg) {
	if (typeof iPos == "undefined" || iPos == null)iPos=7;
	var pTitle = new Title(titleMsg, 14, 7, "宋体", "blue", "white","red", 1);
	pTitle.setPoint(pointx);
	pTitle.addListener("click", function() {
		pTitle.openInfoWindowHtml(detailMsg);
	});
	//_MapApp.centerAtLatLng(pointx);
	voArray.push(pTitle);
	_MapApp.addOverlay(pTitle);
}

function showText3(iPos, pointx, titleMsg, detailMsg,dzbm) {
	var num="";
	if(dzbm!=null){
		num=selectJzwjg(dzbm);
	}
	//var num=1;
	if (typeof iPos == "undefined" || iPos == null)iPos=7;
	if(num==1){
		var pTitle = new Title(titleMsg, 14, 7, "宋体", "red", "white","red", 1);
	}else{
		var pTitle = new Title(titleMsg, 14, 7, "宋体", "blue", "white","red", 1);
	}
	pTitle.setPoint(pointx);
	pTitle.addListener("click", function() {
		if(num==1){
			jzwJgLx(dzbm);
		}else{
			_MapApp.centerAtLatLng(pointx);
			pTitle.openInfoWindowHtml(detailMsg);
		}
	});
	voArray.push(pTitle);
	_MapApp.addOverlay(pTitle);
}

function removeAllOverlay() {
	if (voArray != null && voArray.length > 0) {
		for (var i = 0; i < voArray.length; i++) {
			_MapApp.removeOverlay(voArray[i], false);
		}
		voArray = [];
	}
}

//-----------------------------------------------------------------------------
