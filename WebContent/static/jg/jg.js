var globleWidth=120;
var golbleHeight=70;

//function addLouceng(lcs){
//	for(var i=0;i<lcs;i++){
//		var name=(lcs-(i))+"F";
//		var left=-32;
//		var top=golbleHeight*i;
//		var hei=golbleHeight;
//		var wit=30;
//		var bordera="lc";
//		$("#container").append("<div id=\""+name+"\" class=\""+bordera+"\" style=\"left: "+left+"px;top:"+top+"px;width: "+wit+"px; height:"+hei+"px; \">"+name+"</div>");
//	}
//}
function addDanyuan(lcs,dys,mdyms){
	for(var i=0;i<dys;i++){
		var name=(i+1)+"单元";
		var left=(globleWidth*mdyms)*i;
		//var top=-32;
		var top =lcs*golbleHeight+2;
		
		var hei=30;
		var wit=globleWidth*mdyms-2;
		var bordera="dy";
		$("#container").append("<div id=\""+name+"\" class=\""+bordera+"\" style=\"left: "+left+"px;top:"+top+"px;width: "+wit+"px; height:"+hei+"px; \">"+name+"</div>");
		if(lcs>=10){
			var topx =-32;
			$("#container").append("<div id=\""+name+"\" class=\""+bordera+"\" style=\"left: "+left+"px;top:"+topx+"px;width: "+wit+"px; height:"+hei+"px; \">"+name+"</div>");
		}
	}
}

//-----------------------globle----------------------------------------------------------


function PositionObj(left, top, width, height) {
	this.left = left; //
	this.top = top;//
	this.width = width;//
	this.height = height;//
	return this;
}


function init(){
	$("#container div").each(function(index,element){
		$(this).unbind("click", function(){});
	});
	  $("#container div").click(function(){
		  //单元楼层div不参与选中逻辑
		  /**/
		  if($(this).hasClass("dy")||$(this).hasClass("lc")){
			  return;
		  }
		  if($(this).hasClass("tdx")){
			  removeSelectedCss($(this));
		  }else{
			  addSelectedCss($(this));
		  }
	  });
}


function isHorizontal(){
	posArray=getSelectedDivData();
	for(var i=0;i<posArray.length-1;i++){
		if(posArray[i].top!=posArray[i+1].top){
			return false;
		}
	}
	return true;
}
function isVertical(){
	posArray=getSelectedDivData();
	for(var i=0;i<posArray.length-1;i++){
		if(posArray[i].left!=posArray[i+1].left){
			return false;
		}
	}
	return true;
}
//----------------------------hebing-----------------------------------------------------
function checkHeBing(){
	var posArray=new Array();
	posArray=getSelectedDivData();
	if(!posArray||posArray.length<2){
		// alert("请至少选择两个房屋进行合并");
		 alertMsg.warn("请至少选择两个房屋进行合并");
			return false;
	}
	if(isVertical()){
		//竖直方向
		for(var i=0;i<posArray.length-1;i++){
			if(posArray[i].left!=posArray[i+1].left){
				//alert("检测到位置不正确");
				alertMsg.warn("检测到位置不正确");
				return false;
			}
			if(posArray[i].width!=posArray[i+1].width){
				//alert("检测到房间跨越宽度不同，不可合并");
				alertMsg.warn("检测到房间跨越宽度不同，不可合并");
				return false;
			}
			posArray.sort(function(p1, p2) { return p1.top - p2.top; });
			if(posArray[i].top+posArray[i].height!=posArray[i+1].top){
				//alert("检测到房间位置不连续，不可合并");
				alertMsg.warn("检测到房间位置不连续，不可合并");
				return false;
			}
		}
		return true;
	}else if(isHorizontal()){
		//水平
		for(var i=0;i<posArray.length-1;i++){
			if(posArray[i].top!=posArray[i+1].top){
				//alert("检测到位置不正确");
				alertMsg.warn("检测到位置不正确");
				return false;
			}
			if(posArray[i].height!=posArray[i+1].height){
				//alert("检测到房间跨越宽度不同，不可合并");
				alertMsg.warn("检测到房间跨越宽度不同，不可合并");
				return false;
			}
			posArray.sort(function(p1, p2) { return p1.left - p2.left; })
			if(posArray[i].left+posArray[i].width!=posArray[i+1].left){
				//alert("检测到房间位置不连续，不可合并");
				alertMsg.warn("检测到房间位置不连续，不可合并");
				return false;
			}
		}
		return true;
	}else{
		alertMsg.warn("检测到方向不正确");
	//	alert("方向不正确");
		return false;
	}
	return true;
	
}

//------------------------------------chaifen---------------------------------------------------------------------------------
function checkChaiFen(){
	var inxx=0;
	$("#container div.tdx").each(function(index,element){
		inxx++;
	});
	if(inxx==0){
		alertMsg.warn("请选择房间");
		//alert("请选择房间"); 
		return false;
	}
	if(inxx>1){
		alertMsg.warn("拆分房间数超过一个");
		//alert("拆分房间数超过一个"); 
		return false;
	}
	return true;
}
function caculateW(initLeft,initTop,Mwidth,Mheigth,count){
	var posArray=new Array();
	if(Mwidth%count==0){
		var everyW=Mwidth/count;
		for(var i=0;i<count;i++){
			var posibj= new PositionObj(initLeft+i*everyW,initTop,everyW,Mheigth);
			posArray.push(posibj);
		}
	}else{
		var mod=Mwidth%count;
		var everyWA=(Mwidth-mod)/count;
		var everyWB=everyWA+mod;
		for(var i=0;i<count;i++){
			var positibj= new PositionObj(initLeft+i*everyWA,initTop,(i<count-1)?everyWA:everyWB,Mheigth);
			posArray.push(positibj);
		}
	}
	return posArray;
}
	function caculateH(initLeft,initTop,Mwidth,Mheigth,count){
		var posArray=new Array();
		if(Mheigth%count==0){
			var everyH=Mheigth/count;
			for(var i=0;i<count;i++){
				var posij=new PositionObj(initLeft,initTop+i*everyH,Mwidth,everyH);
				posArray.push(posij);
			}
		}else{
			var mod=Mheigth%count;
			var everyHA=(Mheigth-mod)/count;
			var everyHB=everyHA+mod;
			for(var i=0;i<count;i++){
				var ponObj=new PositionObj(initLeft,initTop+i*everyHA,Mwidth,(i<count-1)?everyHA:everyHB);
				posArray.push(ponObj);
			}
		}
		return posArray;
} 
	
//-----------------------------------tools--------------------------------------------------------------------------------
	//getSelectedDivData
	function getSelectedDivData(){
		var posArray=new Array();
		$("#container div.tdx").each(function(index,element){
			  var left=$(this).position().left;
			  var top = $(this).position().top;
			  var width =$(this).width();
			  var height = $(this).height();
			  var posibj= new PositionObj(left,top,width,height);
			 posArray.push(posibj);
		 });
		return posArray;
	}
	
	function getSelectedDivIds(){
		var targetIdArray=new Array();
		 $("#container div").each(function(index,element){
			  if($(this).hasClass("tdx")){
				  var id=$(this).attr("id");
				  if(id!=undefined){
					  targetIdArray.push(id);
				  }
				  
				 
			  }
		  });
		 return targetIdArray;
	}
	
	function getAfterHeBingDivInfo(){
		var minLeft=9999999999;
		var minTop=9999999999;
		var width=0;
		var height=0;
		 $("#container div.tdx").each(function(index,element){
			 //left
			 var Y = $(this).position().left; 
			  if(minLeft>Y){
				  minLeft=Y;
			 }
			  //top
			  var X = $(this).position().top; 
			  if(minTop>X){
				  minTop=X;
			 }
			  //width
			 if(isHorizontal()){
				 width+=$(this).width();
			  }else{
				  width=$(this).width();
			  }
			// height
			  if(isVertical()){
				  height+=$(this).height();
			  }else{
				  height=$(this).height();
			  }
		  });
		 var posibj= new PositionObj(minLeft,minTop,width,height);
		return posibj;
	}
//-----------------------------------------------------
	
	function addClickClass(domId){
		$(domId).click(function(){
			  if($(this).hasClass("tdx")){
				  removeSelectedCss($(this));
			  }else{
				  addSelectedCss($(this));
			  }
		  });
	}
	 function addSelectedCss(domId){
		  $("#container div").each(function(index,element){
			  if($(this).attr("id")==domId.attr("id")){
				  $(this).addClass("tdx");
				  window.parent.loadFjInfo($(this).attr("id"));
//				  $('#jzwfjInfo',parent.document).html( $(this).html()); 
			  }
		  });
	}
	function removeSelectedCss(domId){
		 $("#container div").each(function(index,element){
			  if($(this).attr("id")==domId.attr("id")){
				  $(this).removeClass("tdx");
			  }
		  });
	}
	
	function remveDivByIds(targetIdArray){
		var idArray=new Array();
		idArray=targetIdArray;
		for( var i=0;i<idArray.length;i++){
			 var id=idArray[i];
			 $("#"+id+"").remove();
		 }
	}