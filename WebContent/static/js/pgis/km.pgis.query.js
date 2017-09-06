	//---------------------search-initDataGrid-----------------------

	function initDataGrid(queryGridAjaxUrl) {
		var loadurl = queryGridAjaxUrl;
		queryResGrid = $("#queryResGrid").datagrid({
							title : "",nowrap : true,
							rownumbers : true,fitColumns : true,
							animate : true,collapsible : false,
							fit : true,striped : true,
							singleSelect : true,pagination : true,
							rownumbers : true,pageSize : 50,
							url : loadurl,
							idField : 'DZBM',
							columns : [ [ 
							              {field : 'DZBM',title : 'ID',hidden : true},
										//{field : 'YWLSH',title : 'YWLSH称',hidden:true},
										  {field : '_type',title : '类型',hidden : true}, 
										  {field : 'DZMC',title : '地址名称',width : 200},
										//{field : 'JZWMC',title : '建筑物名称',hidden:true}
							] ],
							loadFilter : function(data) {
								var pager = $("#queryResGrid").datagrid("getPager");
								pager.pagination({showPageList : false,showRefresh : false,displayMsg : ''});
								return data;
							},
							onSelect : function(rowIndex, rowData) {
								var loStr = rowData.LOCATION;
								var lo = new Array();
								var lat = 0.0;
								var lon = 0.0;
								if (loStr && loStr != undefined) {
									lo = loStr.split(",");
									lat = parseFloat(lo[0]);
									lon = parseFloat(lo[1]);
								} else {
									alertMsg.warn("经纬度错误");
									return;
								}
								var _type = rowData._type;
								if ("jzwjbxx" == _type) {
									var jzwmc = rowData.JZWMC;
									var dzbm = rowData.DZBM;
									var dzmc = rowData.DZMC;
									if (jzwmc == "undefined"|| jzwmc == undefined|| jzwmc == "" || jzwmc == null|| jzwmc == "null") {
										jzwmc = dzmc;
									}
									var titleMsg = jzwmc;
									var isPerm = isInperminssion(lon, lat);
									var detailMsg = "";
									if (isPerm) {
										detailMsg = "类型:建筑物<br/>建筑物名称: "+ jzwmc+ "<br/><br/>详细地址: "+ dzmc
												+ "<br/><br/><a href=\"#\" onclick=\"enterJzwjgPage('"+ dzbm + "');\" >结构信息</a>"
												+ "&nbsp;&nbsp;<a href=\"#\" onclick=\"editJzwJbxx('"+ dzbm + "');\" >修改建筑物信息</a>";
									} else {
										detailMsg = "类型:建筑物<br/>建筑物名称: "+ jzwmc + "<br/><br/>详细地址: "+ dzmc + "";

									}
									//var detailMsg = "类型:建筑物<br/>建筑物名称: "+jzwmc+"<br/><br/>详细地址: "+dzmc+"<br/><br/><a href=\"#\" onclick=\"enterJzwjgPage('"+dzbm+"');\" >结构信息</a>";
									var pointx = new Point(lon, lat);
									showText3(7, pointx, titleMsg, detailMsg,dzbm);
								} else {
									var ywlsh = rowData.YWLSH;
									var dzmc = rowData.QHNXXDZ;
									var titleMsg = rowData.QHNXXDZ;
									var detailMsg = "类型:门楼牌号<br/>详细地址: " + dzmc+ "<br/> <br/>";
									var pointx = new Point(lon, lat);
									showText3(7, pointx, titleMsg, detailMsg);
								}
								_MapApp.centerAndZoom(pointx, 11);
							}
						});
	}
	function showSearchOnMap() {
		var searData = queryResGrid.datagrid("getData");
		var rows = searData.rows;
		for (var i = 0; i < rows.length; i++) {
			var rowData = rows[i];
			var loStr = rowData.LOCATION;
			var lo = new Array();
			var lat = 0.0;
			var lon = 0.0;
			if (!loStr || loStr == undefined || loStr == "undefined") {
				continue;
			}
			if (loStr && loStr != undefined) {
				lo = loStr.split(",");
				lat = parseFloat(lo[0]);
				lon = parseFloat(lo[1]);
			}
			var _type = rowData._type;
			if ("jzwjbxx" == _type) {
				var jzwmc = rowData.JZWMC;
				var dzbm = rowData.DZBM;
				var dzmc = rowData.DZMC;
				if (!PUBUtil.isStrHavaValue(jzwmc)) {
					jzwmc = dzmc;
				}
				var titleMsg = jzwmc;
				var isPerm = isInperminssion(lon, lat);
				var detailMsg = "";
				if (isPerm) {
					detailMsg = "类型:建筑物<br/>建筑物名称: "+ jzwmc+ "<br/><br/>详细地址: "+ dzmc
							+ "<br/><br/><a href=\"#\" onclick=\"enterJzwjgPage('"+ dzbm + "');\" >结构信息</a>";
				} else {
					detailMsg = "类型:建筑物<br/>建筑物名称: " + jzwmc+ "<br/><br/>详细地址: " + dzmc + "";

				}
				var pointx = new Point(lon, lat);
//				showText2(7, pointx, titleMsg, detailMsg);
				showText3(7, pointx, titleMsg, detailMsg,dzbm);//沒有结构 pgis 显示红色字体
			} else {
				var ywlsh = rowData.YWLSH;
				var dzmc = rowData.DZMC;
				var titleMsg = rowData.DZMC;
				var detailMsg = "类型:门楼牌号<br/>详细地址: " + dzmc + "<br/> <br/>";
				var pointx = new Point(lon, lat);
				showText2(7, pointx, titleMsg, detailMsg);
			}
		}
	}
	
	function isInperminssion(lon, lat) {
		var pointX = lon;
		var pointY = lat;
		if (allPolygons == null) {
			return false;
		}
		for (var k = 0; k < allPolygons.length; k++) {
			if ("" != allPolygons[k] && "null" != allPolygons[k]&& "undefined" != allPolygons[k]&& undefined != allPolygons[k]) {
				var flag = isPermissionbyPoint(allPolygons, pointX, pointY);
				if (!flag) {
					return false;
				}
			}

		}
		return true;
	}