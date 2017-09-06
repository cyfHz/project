var _MapApp=undefined;
var targetMarkMlphId=undefined;
var allPolygonBjzb=new Array();
function mark(){
		removeAllMarker();
		_MapApp.changeDragMode('drawPoint', null, null,function(s) {
			var arr = s.split(",");
			if(!isInperminssion(arr[0],arr[1])){
				alertMsg.warn("所选点不在管辖区域内,请重新选取");
				return;
			}
			addMarker(s);
			alertMsg.confirm("经纬度为："+s+" 是否保存？", {
    			cancelCall : function() {alertMsg.close();	removeAllMarker();},
    			okCall : function() {alertMsg.close();
    					var ajaxUrl = ctx+"/psam/jzwjbxx/updateLocMark";
    					var param = {dzbm:targetMarkJzwId,location:s};
    					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
    						if(data.statusCode!=200){
    					  		 alertMsg.error(data.message);
    					  		removeAllMarker();
    					  		 return;
    						}
    						loadJzwjbxxData('reload');
    						alertMsg.info(data.message);
    					});
    				}
    			});
		});
	}
	function addMarker(s,title){
		removeAllMarker();
		var arr = s.split(",");
		var p = new Point(arr[0], arr[1]);
		var pIcon=new Icon();
		pIcon.image=ctx+"/static/images/marker.png";
		pIcon.height=16;
		pIcon.width=16;
		var marker = new Marker(p,pIcon,new Title(s));
		_MapApp.addOverlay(marker);
		voArray.push(marker);
	}
	var voArray=new Array();
	function removeAllMarker(){
		if(voArray!=null&&voArray.length>0){
			for(var i=0;i<voArray.length;i++){
				_MapApp.removeOverlay(voArray[i], false);
			}
			voArray=[];
		}
	}
	function isInperminssion(lon,lat){
    	var pointX=lon;
    	var pointY=lat;
    	if(allPolygonBjzb==null){
    		return false;	
    	}
    	for(var k=0;k<allPolygonBjzb.length;k++){
    		if(""!=allPolygonBjzb[k]&&"null"!=allPolygonBjzb[k]&&"undefined"!=allPolygonBjzb[k]&&undefined!=allPolygonBjzb[k]){
	    		var flag=isPermissionbyPoint(allPolygonBjzb, pointX, pointY);
	        	if(!flag){
	        		 return false;	
	        	}
    		}
    	}
    	return true;
 }
    function onLoad(){
    	if(_compatIE()){
    		setTimeout(function(){
	            _MapApp = new EzMap(document.getElementById("mymap"));
	            _MapApp.showMapControl();
	            _MapApp.centerAndZoom(new Point(116.99655, 36.66345), 11);
	            var uOverview=new OverView();// 构造一个鹰眼对象
	            uOverview.width=200;// 设置鹰眼视窗的宽度
	            uOverview.height=200;// 设置鹰眼视窗的高度
	            uOverview.minLevel=5;// 设置鹰眼视窗中最小显示地图级别
	            uOverview.maxLevel=12;// 设置鹰眼视窗中最大显示地图级别
	            _MapApp.addOverView(uOverview);// 添加鹰眼对象
	            loadManageBianjie();
    		}, 100);
    	}else if(_MapApp==null){
    		var pEle=document.getElementById("mymap");
    		pEle.innerHTML="<p>目前EzMap地图引擎不支持你使用的浏览器，我们当前支持如下浏览器类型:</p><ul><li><a href='http://www.microsoft.com/windows/ie/downloads/default.asp'>IE</a> 5.5+ (Windows)</li>";
    	}
      }
function loadManageBianjie(ajaxUrl) {
	 ajaxUrl = ctx+"/psam/jwq/loadManageBianjie";
			var param = {};
			KMAJAX.ajaxTodo(ajaxUrl,param,function(data) {
				if (data.statusCode != 200) {
					alertMsg.error(data.message);
					return;
				}
			orgInfoArray = data.data;
			var userLevel=data.confirmMsg;
			if (orgInfoArray && orgInfoArray != null&& orgInfoArray.length > 0) {
				for (var i = 0; i < orgInfoArray.length; i++) {
					var bjzbzx = orgInfoArray[i].bjzbz;
					if (PUBUtil.isStrHavaValue(bjzbzx)) {
						var everyOrgBjzb = new Array();
						var everyOrgBjzb = bjzbzx.split(";");
						for (var j = 0; j < everyOrgBjzb.length; j++) {
							if (PUBUtil.isStrHavaValue(everyOrgBjzb[j])) {
								allPolygonBjzb.push(everyOrgBjzb[j]);
								var polygonx=new Polygon(everyOrgBjzb[j], "black", 6,0.3, "red");
								_MapApp.addOverlay(polygonx);
								if(i==orgInfoArray.length-1){
									var mbr = polygonx.getMBR();
									var pointxx = mbr.centerPoint();
//									_MapApp.centerAndZoom(pointxx,10);
									if(userLevel=='2'){
										_MapApp.centerAndZoom(pointxx, 6);
									}else{
										_MapApp.centerAndZoom(pointxx, 10);
									}
								}
						   }
						}
				   }
				}
			}
		});
	 }
 //------------------------------------------
