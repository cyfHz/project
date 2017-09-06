//--------------------Search----------------------------------------------------------- 
	function doSearch(){
		var currDragMode =drowModex;
		if ("drawRect" == currDragMode) {
			doRectangleSeach(geoStr);
		} else if ("drawCircle" == currDragMode) {
			doCircleSearch(geoStr);
		}else{
			inViewSeach();
		}
	}
	function doCircleSearch(str) {
		var seacrch_param=$("#seacrch_param").searchbox("getValue");
		if (!str) {alertMsg.warn("未正确选取圆形");return;}
		geoStr=str;
		var rectanglePoints = new Array();
		rectanglePoints = str.split(",");
		var  lon= rectanglePoints[0];//纬度
		var  lat= rectanglePoints[1];//经度
		var radius = rectanglePoints[2];
		var scale=_MapApp.getCurrentMapScale();//1:50000
		var meter=_MapApp.getMeter(new Point(lon,lat), parseFloat(radius));
		if (radius == 0) {alertMsg.warn("未正确选取圆形");return;}
		var searchFrom=$("#seacrch_param").searchbox("getName");
		var matchPhrase= ($("#matchPhrase").attr("checked")=="checked")?"1":"0";
		var queryParams = $("#queryResGrid").datagrid("options").queryParams;
			queryParams["lat"]=lat;
			queryParams["lon"]=lon;
			queryParams["radius"]=meter;
			queryParams["type"]="circle";
			queryParams["val"]=seacrch_param;
			queryParams["searchFrom"]=searchFrom;
			queryParams["matchPhrase"]=matchPhrase;
			$("#queryResGrid").datagrid("reload");
			$("#queryResGrid").datagrid("clearSelections");
	}
	function doRectangleSeach(str) {
		var seacrch_param=$("#seacrch_param").searchbox("getValue");
		if (!str) {alertMsg.warn("未正确选取矩形");return;}
		var rectanglePoints = new Array();
		rectanglePoints = str.split(",");
		var  ltlon= rectanglePoints[0];
		var  ltlat = rectanglePoints[3];
		
		var  rblon= rectanglePoints[2];
		var  rblat= rectanglePoints[1];
		if ((ltlat == rblat) && (ltlon == rblon)) {
			alertMsg.warn("未正确选取矩形");
			return;
		}
		geoStr=str;
		var searchFrom=$("#seacrch_param").searchbox("getName");
		var matchPhrase= ($("#matchPhrase").attr("checked")=="checked")?"1":"0";
		var queryParams = $("#queryResGrid").datagrid("options").queryParams;
			queryParams["ltlat"]=ltlat;
			queryParams["ltlon"]=ltlon;
			queryParams["rblat"]=rblat;
			queryParams["rblon"]=rblon;
			queryParams["type"]="react";
			queryParams["val"]=seacrch_param;
			queryParams["searchFrom"]=searchFrom;
			queryParams["matchPhrase"]=matchPhrase;
			$("#queryResGrid").datagrid("reload");
			$("#queryResGrid").datagrid("clearSelections");
	}
	
	function inViewSeach() {
		removeAllOverlay();
		var seacrch_param=$("#seacrch_param").searchbox("getValue");
		var mLine = _MapApp.getBoundsLatLng();
		var str = mLine.minX.toString() + "," + mLine.minY.toString() + ","
				+ mLine.maxX.toString() + "," + mLine.maxY.toString();
		rectanglePoints = str.split(",");
		var  ltlon= rectanglePoints[0];
		var  ltlat = rectanglePoints[3];
		var  rblon= rectanglePoints[2];
		var  rblat= rectanglePoints[1];
		var searchFrom=$("#seacrch_param").searchbox("getName");
		var matchPhrase= ($("#matchPhrase").attr("checked")=="checked")?"1":"0";
		var queryParams = $("#queryResGrid").datagrid("options").queryParams;
			queryParams["ltlat"]=ltlat;
			queryParams["ltlon"]=ltlon;
			queryParams["rblat"]=rblat;
			queryParams["rblon"]=rblon;
			queryParams["type"]="react";
			queryParams["val"]=seacrch_param;
			queryParams["searchFrom"]=searchFrom;
			queryParams["matchPhrase"]=matchPhrase;
			$("#queryResGrid").datagrid("load");
			$("#queryResGrid").datagrid("clearSelections");
	}
	
