	function loadManageBianjie(ajaxUrl) {
		var param = {};
		KMAJAX.ajaxTodo(ajaxUrl,param,function(data) {
			if (data.statusCode != 200) {
				alertMsg.error(data.message);
				return;
			}
			/* {"confirmMsg":"",
			"data":[ {"bh":"","id":"","type":"jwq","bjzbz":";"}, {"bh":"","id":"","type":"jwq","bjzbz":";"} ],
			"forwardUrl":"","message":"数据加载成功","statusCode":200,"token":""} */
		orgInfoArray = data.data;
		var userLevel=data.confirmMsg;
		if (orgInfoArray && orgInfoArray != null&& orgInfoArray.length > 0) {
			
			//循环把每个警务区
			for (var i = 0; i < orgInfoArray.length; i++) {
				//console.log( i );
				var bjzbzx = orgInfoArray[i].bjzbz;
				if (PUBUtil.isStrHavaValue(bjzbzx)) {
					var everyOrgBjzb = new Array();
					//bjzbzx="116.950038,36.604882,116.950009,36.604487,116.949887,36.604487,116.950499,36.603818,116.951766,36.603669,116.951698,36.603288,116.950997,36.601778,116.950723,36.600779,116.951037,36.600165,116.953393,36.600308,116.955177,36.599701,116.956203,36.59828,116.956389,36.59752,116.956917,36.596435,116.957374,36.596006,116.958588,36.595278,116.958795,36.594989,116.958559,36.594138,116.958666,36.593924,116.960544,36.593189,116.960144,36.592639,116.959809,36.591768,116.959787,36.59149,116.960144,36.590653,116.960158,36.590139,116.959222,36.589583,116.959129,36.589226,116.957973,36.589126,116.957337,36.588755,116.956945,36.588327,116.956953,36.587527,116.955467,36.586314,116.954939,36.586085,116.954496,36.585536,116.954139,36.585314,116.953347,36.584965,116.952448,36.58475,116.947816,36.583983,116.946705,36.584048,116.945924,36.584355,116.94594,36.584567,116.945148,36.584834,116.945342,36.585715,116.943993,36.585655,116.943326,36.585861,116.943769,36.590639,116.943423,36.590611,116.943239,36.591847,116.941723,36.591933,116.941368,36.592095,116.940898,36.592588,116.938846,36.592874,116.93818,36.592767,116.937644,36.592446,116.93715,36.592321,116.93655,36.592321,116.935788,36.592588,116.935351,36.593031,116.937426,36.595698,116.939889,36.597661,116.940412,36.599137,116.940662,36.600588,116.945302,36.602444,116.946733,36.603434,116.947295,36.604167,116.948972,36.603945,116.948994,36.604823,116.949643,36.604757,116.949672,36.604906,116.950038,36.604882;";
					//bjzbzx+="116.95525,36.60259,116.96905,36.6065,116.95782,36.61626,116.94695,36.61028,116.96355,36.59246,116.96941,36.60662,116.96844,36.60686,116.95525,36.60259;";
					var everyOrgBjzb = bjzbzx.split(";");
					for (var j = 0; j < everyOrgBjzb.length; j++) {
						if (PUBUtil.isStrHavaValue(everyOrgBjzb[j])) {
							allPolygons.push(everyOrgBjzb[j]);
							//var points = constructOneBianjie(everyOrgBjzb[j]);
							//var strPoints = points.join(",");
							var polygonx=new Polygon(everyOrgBjzb[j], "black", 6,0.3, colors[i%13]);
							//pPolygons.push(polygonx);
							//showOneBianjie(pPolygons[i + j],orgInfoArray[i], j);
							//showOneBianjie(polygonx,orgInfoArray[i], j);
							_MapApp.addOverlay(polygonx);
							if(i==orgInfoArray.length-1){
								var mbr = polygonx.getMBR();
								var pointxx = mbr.centerPoint();
								if(userLevel=='2'){
									_MapApp.centerAndZoom(pointxx, 3);
								}else{
									_MapApp.centerAndZoom(pointxx, 11);
								}
							}
					   }
					}
			   }
			}
		}
	});
 }
	//[{type： "1",id:"";bh:"",mc:"",bjzbz:""}]
	function showOneBianjie(pPolygon, orginfo, index) {
		//console.log(orginfo);
		try{
			_MapApp.addOverlay(pPolygon);
		//var mbr = pPolygon.getMBR();
		//var pointxx = mbr.centerPoint();
		//if (pointxx) {
			//_MapApp.centerAndZoom(pointxx, 11);
	//	}
	//	var mc = orginfo.mc;
	//	var bh = orginfo.bh;
	//	var type = orginfo.type;
	//	var strMsg = " 编号 :" + bh + "<br/>" + "名称:" + mc + "<br/>" + "机构类型："+ type + "";
		/* pPolygon.addListener("click", function() {
			pPolygon.openInfoWindowHtml(strMsg);
		}); */
		//pPolygon.setExtendStatus(1, 100, 1, 2);
		//pPolygon.setInterval(500);
		//pPolygon.disableEdit();
		}catch (e) {
//			console.log("----------------S------------>");
//			console.log(orginfo);
//			console.log("<---------------E-------------");
		}
	}
	
    function constructOneBianjie(data) {
    	var points = new Array();
    	var arrx = new Array();//纵坐标
    	arrx = data.split(",");
    	for (var i = 0; i < arrx.length - 1; i = i + 2) {
    		points.push(new Point(arrx[i], arrx[i + 1]));
    	}
    	return points;
    }