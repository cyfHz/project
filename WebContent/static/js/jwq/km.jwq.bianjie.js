	//加载选中警务区的 边界   
    function loadBianjieByjwqId(){
    	var ajaxUrl=ctx+"psam/jwq/loadJwqBianjie";
    	var param={"jwqid":selectedJwqId};
    	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
    		if(data.statusCode!=200){
    			 alertMsg.error(data.message);
    			 return;
    		}
    		jwqinfo=data.data;
    		var bjzbz=jwqinfo.bjzbz;
    		if(!bjzbz||bjzbz==""||bjzbz==undefined){
    			alertMsg.warn("该警务区没有划分边界");return ;
    		}
    		var zuobiao = new Array();
    		zuobiao=bjzbz.split(";");
    		if(zuobiao&&zuobiao.length>0){
    			 for(var i = 0; i < zuobiao.length; i++){
    				 if(PUBUtil.isStrHavaValue(zuobiao[i])){
    					 var  points= KMPGISTOOL.genOneBorderline(zuobiao[i]);
            			 var strPoints=points.join(",");
            			 pPolygons.push(new Polygon(strPoints,"black", 3,0.2,"red"));
            			 showOneBianjie(pPolygons[i],jwqinfo,i);
    				 }
        		 }
    		}
		}); 
    }   
//red blue black yellow green purple pink Orange lightcyan
function loadJwqBjInSamePsc(){
 	   var ajaxUrl=ctx+"psam/jwq/loadJwqBjInSamePsc";
 	   var param={"jwqid":selectedJwqId};
 		KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
     		if(data.statusCode!=200){alertMsg.error(data.message); return;}
     		var jwqArray=new Array();
     		jwqArray=data.data;
     		if(jwqArray&&jwqArray.length>0){
     			for(var j=0;j<jwqArray.length;j++){
     				var oneJwq=jwqArray[j];
     				var jwqmc=oneJwq.JWQMC;
     				var bjzb=oneJwq.BJZBZ;
     				if(PUBUtil.isStrHavaValue(bjzb)){
     					var zuobiao = new Array();
     		    		zuobiao=bjzb.split(";");
     		    		if(zuobiao&&zuobiao.length>0){
     		    			 for(var i = 0; i < zuobiao.length; i++){
     		    				 if(PUBUtil.isStrHavaValue(zuobiao[i])){
     		    					 var  points= KMPGISTOOL.genOneBorderline(zuobiao[i]);
     		    					 var strPoints=points.join(",");
     		    					 var pppp=new Polygon(strPoints,"black", 3,0.2,"blue");
     		    					 polygonsInSamePcs.push(pppp);
     		    					var mbr=pppp.getMBR();
     		    			    	 var pointxx=mbr.centerPoint();
     		    			    	//_MapApp.addOverlay(new HTMLElementOverLay(1, pointxx, "asdasd"));
     		    			    	var icon=new Icon();
     		    			    	icon.height=0;icon.image=null;icon.leftOffset=0;icon.topOffset=0;icon.width=0;
     		    					 _MapApp.addOverlay(new Marker(pointxx,icon, new Title(jwqmc, 12, 7, "宋体", "WHITE", "#015190", "red", 1)));
     		            			 _MapApp.addOverlay(pppp);
     		    				 }
     		        		 }
     		    		}
     				}
     			}
     		}
     		
 		});
    }
//显示一个边界区域 
    function showOneBianjie(pPolygon,jwqinfo,index){
    	_MapApp.addOverlay(pPolygon);
    	var jwqmc=jwqinfo.jwqmc;
		var jwqbh=jwqinfo.jwqbh;//警务区编号 
		var jwhsl=jwqinfo.jwhsl;//居委会数量
		var jwqmj=jwqinfo.jwqmj;//警务区面积 
		var ncgqsl=jwqinfo.ncgqsl;//农村管区数量
		var jwqxz=jwqinfo.jwqxz;
		var strMsg=" 警务区编号 :"+jwqbh+"<br/>"
				   +"警务区名称:"+jwqmc+"<br/>"
				   +"警务区面积："+jwqmj+"<br/>"
				   +"居委会数量："+jwhsl+"<br/>"
				   +"农村管区数量："+ncgqsl+"<br/>"
				   +"警务区性质："+jwqxz+"<br/>"
				   +"<br/>"
				   +"<a href=\"#\" onclick=\"doDelete("+index+");\" >删除</a>&nbsp;&nbsp;&nbsp;"
				   +"<a href=\"#\" onclick=\"doEdit("+index+");\" >编辑</a>";
    	pPolygon.addListener("click",function(){
    		pPolygon.openInfoWindowHtml(strMsg);
    	 });
    	  pPolygon.setExtendStatus(1,100,1,2);
     	  pPolygon.setInterval(500);
     	 pPolygon.disableEdit();
     	 var mbr=pPolygon.getMBR();
    	 var pointxx=mbr.centerPoint();
    	 if(pointxx){
    		 _MapApp.centerAndZoom(pointxx, 11);
    	 }
    }
 //-----------------------------------
	function initGuihuaDialog(){//打开规划窗口，清除标记
		_MapApp=undefined;
		selectedJwqId=undefined;
		resetArray();
		 var rows = $("#jwqDataGrid").datagrid("getSelections");
		 if(rows.length!=1){ alertMsg.warn("请选择一条要规划的警务区"); return;}
		 if(rows[0].SFYX=='0'){alertMsg.warn("该警务区信息已注销！");return;}
		$("#drawPolygonRes").val("");
	    selectedJwqId=rows[0].JWQID;
		$("#dlg_jwq_pgis").dialog('open').dialog('setTitle','警务区规划------<font color="red">请注意顺时针取点 </font>');
		onLoad();
	}
	function startGuihua(){
		_MapApp.changeDragMode('drawPolygon',drawPolygonRes,null,guihuaCallback);
	}
   //规划回调函数:
	//思路提示：  
	//1.把新规划的区域push到数组pPolygons中，
	//2. clearMap()把地图中标注全部删除，
	//3. 重绘数组pPolygons 中的所有边界区域
	//4.已经删除的在removedIndexes记录中的，在步骤3.中不再重绘。
    function guihuaCallback(res){
    	var zuobiao=res.split(",");
    	if(!zuobiao||zuobiao.length<=6){alertMsg.warn("未选择规划区域");return;}
		if(zuobiao&&zuobiao.length>0){
			var points= KMPGISTOOL.genOneBorderline(res);
			 var strPoints=points.join(",");
			 var polyon=new Polygon(strPoints,"black", 3,0.2,"red");
			 pPolygons.push(polyon);
			 clearMap();
			 for(var i = 0; i < pPolygons.length; i++){
				 var iscontinue=0;
				 for(var j=0;j<removedIndexes.length;j++){
	    			 if(removedIndexes[j]==i){
	    				 iscontinue++; 
	    			 }
	    		 }
				 if(iscontinue>0){
					 continue;
				 }
    			 pPolygons[i].setFillColor("red");
    			 pPolygons[i].setFillOpacity(0.2);
    			 showOneBianjie(pPolygons[i],jwqinfo,i);
    			 pPolygons[i].disableEdit();
    		 }
		}
    }
	//---------------------save----------------
	function savePolygonRes(){
    	var res=$("#drawPolygonRes").val();
    	var jwqid=selectedJwqId;
    	var resStr="";
    	//保存时，已经被删除的 多边形，不会被拼接在提交的数据中
    	 for(var i = 0; i < pPolygons.length; i++){
    		 var iscontinue=0;
    		 for(var j=0;j<removedIndexes.length;j++){
    			 if(removedIndexes[j]==i){
    				 iscontinue++; 
    			 }
    		 }
    		 if(iscontinue>0){
				 continue;
			 }
    		 var pp=pPolygons[i].getPoints();
    		 if(pp=="NaN"||pp==undefined){
    			 continue;
    		 }
    		 resStr=resStr+pp+";";
		 }
    	if(resStr==""||resStr=="NAN,undefined"){
    		var iscomfirm=alertMsg.confirm("该警务区没有边界数据，确认提交？", {
     			cancelCall : function() {alertMsg.close(); return false;},
     			okCall : function() { 
		     			var ajaxUrl=ctx+"psam/jwq/updateBjzbz";
		     	    	 var param={"jwqid":jwqid,"bjzbz":resStr};
		     	    	 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		     				KMCORE.ajaxDone(data);
		     			});
     	    	 }
     		});
     }else{
    	 var ajaxUrl=ctx+"psam/jwq/updateBjzbz";
	    	 var param={"jwqid":jwqid,"bjzbz":resStr};
	    	 KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
				KMCORE.ajaxDone(data);
			});
     }
    	
    }
    function doEdit(index){
    	(pPolygons[index]).closeInfoWindowHtml();
    	(pPolygons[index]).enableEdit();
    }
    //删除一个边界区域,
    function doDelete(index){
    	(pPolygons[index]).closeInfoWindowHtml();
    	 _MapApp.removeOverlay(pPolygons[index],true);
    	 removedIndexes.push(index);//已经删除的在数组removedIndexes中记录
    }
    function clearMap() {
    	if(_MapApp){_MapApp.clear();}
  	}
    
 //------------------------------------------
	//地图初始化
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
	            loadBianjieByjwqId();//加载选中警务区的 边界
	         loadJwqBjInSamePsc();
    		}, 100);
    	}else if(_MapApp==null){
    		var pEle=document.getElementById("mymap");
    		pEle.innerHTML="<p>目前EzMap地图引擎不支持你使用的浏览器，我们当前支持如下浏览器类型:</p><ul><li><a href='http://www.microsoft.com/windows/ie/downloads/default.asp'>IE</a> 5.5+ (Windows)</li>";
    	}
      }
    
    function resetArray(){
		pPolygons.splice(0,pPolygons.length);//清空已边界数组
		removedIndexes.splice(0,removedIndexes.length);//清空已删除的id 数组
		polygonsInSamePcs.splice(0,polygonsInSamePcs.length);//清空已删除的id 数组
		pPolygons= new Array();
		removedIndexes=new Array();
		polygonsInSamePcs=new Array();
    }
    
