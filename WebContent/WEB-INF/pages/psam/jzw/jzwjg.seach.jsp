<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<%@ include file="/static/meta/webuploader.jsp" %>
<div id="layoutPageDiv" class="easyui-layout" data-options="fit:true,border:false" style="overflow: hidden;">
<input id="jzwjgId" type="hidden" value="${jzwjgId}">
	<div data-options="region:'west',title:'建筑物信息',split:true" style="width: 175px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false,collapsible:false" style="height: 300px;">
				<div style="height: 20px;text-align: center;">&nbsp;</div>
				<div  id="jzwjgInfo" style="height: 70px;text-align: center;" >
				建筑物详细信息
				</div>
				<div id="jzwjgPicTitle" style="height: 25px;text-align: center;">建筑物照片</div>
				<div style="height: 120px;text-align: center;" >
					<img  alt="建筑物照片,点击查看大图"  id="jzwjgPic" src=""  width="120px" height="120px" onclick="openBigPic('jg');"/>
				</div>
				<br/>
				<div style="height: 20px;text-align: center;"> 
					<a href="javascript:void(0)" onclick="loadJzwPic('front');" class="easyui-linkbutton">上一张</a>
					&nbsp;
			 	    <a href="javascript:void(0)" onclick="loadJzwPic('next');" class="easyui-linkbutton">下一张</a>
			   </div>
			</div>
			
			<div data-options="region:'center',title:'房间信息',split:true"  style="width: 220px;">
				<div style="height: 20px;text-align: center;">&nbsp;</div>
				<div id="jzwfjInfo" style="height: 80px;text-align: center;"  >
					房间详细信息
				</div>
				<div id="jzwfjPicTitle" style="height: 25px;text-align: center;">房间照片</div>
				<div style="height: 120px;text-align: center;" >
					<img  alt="建筑物房间,点击查看大图"  id="jzwfjPic" src=""  width="120px" height="120px" onclick="openBigPic('fj');"/>
				</div>
				<br/>
				<div style="height: 20px;text-align: center;"> 
					<a href="javascript:void(0)" onclick="loadJzwFjPic('front');" class="easyui-linkbutton">上一张</a>
					&nbsp;
			 	    <a href="javascript:void(0)" onclick="loadJzwFjPic('next');" class="easyui-linkbutton">下一张</a>
			   </div>
			</div>
		</div>
		
<div id="dlg_picShow" class="easyui-dialog" data-options="modal:true" style="width: 960px; height: 700px; padding: 10px 10px" closed="true">
		<img id="bigPicShow" alt="显示图片" width="800px" height="600px" src="" >	
		<br>
<button class="btn btn-default" onclick="picDelete();">删除照片</button>
&nbsp;&nbsp;
<button id="ctlBtn" class="btn btn-default" >开始上传</button>
<hr/>
<div id="uploader" >
	<div id="thelist" class="uploader-list"></div>
		<div id="uploadButtons" class="btns"></div>
</div>
</div>
</div>

<div data-options="region:'center',border:false">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" style="height: 40px;overflow: hidden;"  >
			<div class="easyui-panel" style="height:40px;padding:5px;border-width: 0px;margin: 0px;width: 730px;" >
				<input id="jzwjgId" type="hidden" value="${jzwjgId}">
			<%-- <km:widgetTag widgetRulevalue="jzwfj.heBing">	
				 <a href="javascript:void(0)"  onclick="startHeBing();" data-options="iconCls:'icon-kmedit',plain:true"  class="easyui-linkbutton">合并房间</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="jzwfj.chaiFen">			
                 <a href="javascript:void(0)" onclick="startChaiFen();"  data-options="iconCls:'icon-kmedit',plain:true"  class="easyui-linkbutton">拆分房间</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="jzwfj.deleteJzwfj">		
                  <a href="javascript:void(0)" onclick="startDeleteFj()"  data-options="iconCls:'icon-kmedit',plain:true"  class="easyui-linkbutton">删除房间</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="jzwfj.addjzwfj">	
                <a href="javascript:void(0)" onclick="startAddFj();"  data-options="iconCls:'icon-kmedit',plain:true"  class="easyui-linkbutton">添加房间</a>
             </km:widgetTag> --%>
              <km:widgetTag widgetRulevalue="fw.enterDetailFw">
				<a href="#" class="easyui-linkbutton" iconCls="icon-kmedit" plain="true" onclick="startFjInfo()">房屋信息</a>
			</km:widgetTag>
<%-- 			<km:widgetTag widgetRulevalue="fw.saveFwjbxxAccInfo">					
                 <a href="javascript:void(0)" onclick="loadFwjbxxAcc();" data-options="iconCls:'icon-kmedit',plain:true"  class="easyui-linkbutton">房屋信息采集</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="syfwrk.saveRkInfoAccInfo">	
                 <a href="javascript:void(0)" onclick="startRkAccquisition();"data-options="iconCls:'icon-kmedit',plain:true"   class="easyui-linkbutton">人口采集</a>
			</km:widgetTag>
			<km:widgetTag widgetRulevalue="syfwrk.loadsyfwrkInfo">		
                 <a href="javascript:void(0)" onclick="startRkInfo();" data-options="iconCls:'icon-kmedit',plain:true"  class="easyui-linkbutton">人口信息</a>
             </km:widgetTag>	 --%>		
              </div>
		</div>
		
		<div data-options="region:'center',border:false" id="jzwjgIframeContainer" >
		  <iframe src="" name="jzwjgIframe" id="jzwjgIframe" style="width:99.4%;height:99.4%; overflow: scroll;"> </iframe> 
		</div>
	
	</div>
</div>

<div data-options="region:'east',border:true,title:'功能区',collapsed:false" style="width: 160px;"  >
		<!-- <div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height: 200px;  padding:2px;overflow: hidden;">
			<ul id="tt"></ul>
			</div> -->
		<ul id="tt"></ul>
</div>
<script type="text/javascript">
//--------------child-----------------------------------------------
function startHeBing(){
	window.frames["jzwjgIframe"].startHeBing();
}
function startChaiFen(){
	window.frames["jzwjgIframe"].startChaiFen();
}
function startDeleteFj(){
	window.frames["jzwjgIframe"].startDeleteFj();
}
function startAddFj(){
	window.frames["jzwjgIframe"].startAddFj();
}

function loadFwjbxxAcc(){
	window.frames["jzwjgIframe"].loadFwjbxxAcc();

}
function startRkAccquisition(){
	window.frames["jzwjgIframe"].startRkAccquisition();
}
function startRkInfo(){
	window.frames["jzwjgIframe"].startLoadRkInfo();
}
function startFjInfo(){
	window.frames["jzwjgIframe"].startLoadFjInfo();
}
//----------------------------------------------------------------
function initRktree(){
	$('#tt').tree({
		//url:"${ctx}/static/json/jzwf-jquery-metadata.json",
		data:[{
			"id":1,
			"text":"功能选项",
			"children":[{
				"id":11,
				"text":"人口管理",
				"children":[{
					"id":"/psam/sy/syfwCzrk/loadCzrkFj",
					"valuex":"czrk",
					"iconCls":"icon-add",
					"text":"常住人口",
					"checked":true  
				},{
					"id":"/psam/sy/syfwLdrk/loadLdrkFj",
					"valuex":"ldrk",
					"text":"流动人口",
					"checked":true  
				},{
					"id":"/psam/sy/syFwJwry/loadJwryFj",
					"valuex":"jwry",
					"text":"境外人员",
					"checked":true  
				}]
			}]
		}],
		checkbox:true,
		onlyLeafCheck:true,
		onCheck:function(node,checked){
			var urlArray=new Array();
			var nodes = $('#tt').tree('getChecked');
			if(nodes!=null&&nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					urlArray.push(nodes[i].id);
				}
				window.frames["jzwjgIframe"].rkSearch(urlArray);
			}else{
				window.frames["jzwjgIframe"].clearFjMark();
			}
		}
	});
}
//-------------------------------------------------------------
var jzwjgId=$("#jzwjgId").val();
var jzwfjid="";
var openType="";
$(function(){
	var iframeUrl="${ctx}/psam/jzwjg/enterJzwjgIframe?jzwjgId="+jzwjgId;
	setTimeout(function(){
		$("#jzwjgIframe").attr("src",iframeUrl);
	}, 50);
	
	uploaderUrl="${ctx}/psam/jzwfjPic/uploadJzwFjPic?jzwfjid="+jzwfjid;
	initUploader(uploaderUrl);
	initRktree();
	loadMask.open();
	setTimeout(function(){
		loadjzwinfo();
	}, 1000);
});
var isLoadOnce=false;
$(function(){
	 var iframe = document.getElementById('jzwjgIframe');
	if (iframe.attachEvent){
	    iframe.attachEvent("onload", function(){
	    	setTimeout(function(){onTreeLoadSearchRk();}, 1000);
	    });
	} else {
	    iframe.onload = function(){
	    
	    	if(isLoadOnce==false){
	    		setTimeout(function(){onTreeLoadSearchRk();}, 3000);
	    		isLoadOnce=true;
	    		//alert("Local iframe is now loaded+++++++++++++++++++.");
	    	}
	        //alert("Local iframe is now loaded+++++++++++++++++++.");
	    };
	}
})
//进入结构展示 加载每个房间人口类别
function onTreeLoadSearchRk(){
	var urlArray=new Array();
	var nodes = $('#tt').tree('getChecked');
	if(nodes!=null&&nodes.length>0){
		for(var i=0;i<nodes.length;i++){
			urlArray.push(nodes[i].id);
		}
		if(window.frames){
			 window.frames["jzwjgIframe"].rkSearch(urlArray);
		}
	}else{
		//window.frames["jzwjgIframe"].clearFjMark();
	}
}
//-------------------------------------------------------------
//-------------------------------------------------------------------------------------------
var jzwjgPicIds=new Array();
var jzwjgPicIndex=0;
var jzwjgPicCount=0;

function loadjzwinfo(){
	var ajaxUrl="${ctx}/psam/jzwjbxx/loadJzwInfo";
	var param={"jzwjgId":jzwjgId};
	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		var jzw=data.data.jzw;
		if(jzw!=null){
			$("#jzwjgInfo").html(jzw.dzmc);
		}
		jzwjgPicIds=data.data.jzwjgPicList;
		 if(jzwjgPicIds!=null){
			 loadJzwPic("");
		 }
	});
}
function loadJzwPic(flag){

	if("front"==flag){
		jzwjgPicIndex=jzwjgPicIndex-1;
		if(jzwjgPicIndex<=0){
			jzwjgPicIndex=0;
		}
	}else if("next"==flag){
		jzwjgPicIndex=jzwjgPicIndex+1;
		if(jzwjgPicIds!=null){
			if(jzwjgPicIndex>jzwjgPicIds.length-1){
				jzwjgPicIndex=jzwjgPicIds.length-1;
			}
		}else{
			jzwjgPicIndex=0;
		}
	}else{
		jzwjgPicIndex=0;
	}
	if(jzwjgPicIds!=null){
		jzwjgPicCount=jzwjgPicIds.length;
	}
	
	$("#jzwjgPicTitle").html("建筑物照片,共"+jzwjgPicCount+"张，第"+(jzwjgPicIndex>=jzwjgPicCount?jzwjgPicCount:jzwjgPicIndex+1)+"张");
    var imageUrl;
   if(jzwjgPicIds==null||jzwjgPicIds==""||jzwjgPicIds==undefined||jzwjgPicIds.length==0){
	   imageUrl="static/images/nopic.png";
    }else{
       imageUrl="${ctx}/psam/jzwjgPic/loadJzwJgPic?jzwjgPicId="+jzwjgPicIds[jzwjgPicIndex]+"&"+Math.floor(Math.random()*100); 
    	
    }
	$("#jzwjgPic").hide().attr("src",imageUrl).fadeIn();
	
}

//------------jzwfjPic--------start-----------------------------------
var jzwfjPicIds=new Array();//fjpicIds
var jzwfjPicIndex=0;
var jzwfjPicCount=0;
function loadFjInfo(fjId){
	var ajaxUrl="${ctx}/psam/fw/findSYFwjbxxByFjbm";
	var param={"fjbm":fjId};
	jzwfjid=fjId;
	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		var fwxx=data.data.fwxx;
		 if(fwxx!=null){
			$("#jzwfjInfo").html(fwxx.fwdz);
		}
		 jzwfjPicIds=data.data.fjpicIds;
		 if(jzwfjPicIds!=null){
			 loadJzwFjPic("");
		 }
	});
}
function loadJzwFjPic(flag){
	if("front"==flag){
		jzwfjPicIndex=jzwfjPicIndex-1;
		if(jzwfjPicIndex<=0){
			jzwfjPicIndex=0;
		}
	}else if("next"==flag){
		jzwfjPicIndex=jzwfjPicIndex+1;
		if(jzwfjPicIds!=null){
			if(jzwfjPicIndex>=jzwfjPicIds.length-1){
				jzwfjPicIndex=jzwfjPicIds.length-1;
			}
		}else{
			jzwfjPicIndex=0;
		}
	}else{
		jzwfjPicIndex=0;
	}
	if(jzwfjPicIds!=null){
		jzwfjPicCount=jzwfjPicIds.length;
	}
	$("#jzwfjPicTitle").html("房间照片,共"+jzwfjPicCount+"张，第"+(jzwfjPicIndex>=jzwfjPicCount?jzwfjPicCount:jzwfjPicIndex+1)+"张");
	var imageUrl;
	if(jzwfjPicIds==null||jzwfjPicIds==""||jzwjgPicIds==undefined||jzwjgPicIds.length==0){
		 imageUrl="static/images/nopic.png";
	}else{
		imageUrl="${ctx}/psam/jzwfjPic/loadJzwFjPic?jzwfjPicId="+jzwfjPicIds[jzwfjPicIndex]+"&"+Math.floor(Math.random()*100);
	}
	$("#jzwfjPic").hide().attr("src",imageUrl).fadeIn();
}
//----------------------------------------------------------------------------
/**
 * 
 */
function openBigPic(jgOrfj){
	var picId="";
	var uploaderUrl="";
	var bigImageUrl="";
	if("fj"==jgOrfj){
		openType="fj";
		picId=jzwfjPicIds[jzwfjPicIndex];
		 if(picId==null||picId==""||picId==undefined){
			 bigImageUrl="static/images/nopic.png"
		 }else{
			 bigImageUrl="${ctx}/psam/jzwfjPic/loadJzwFjPic?jzwfjPicId="+picId+"&"+Math.floor(Math.random()*100); 
		 }
		uploaderUrl="${ctx}/psam/jzwfjPic/uploadJzwFjPic?jzwfjid="+jzwfjid;
		
	}else if("jg"==jgOrfj){
		openType="jg";
		picId=jzwjgPicIds[jzwjgPicIndex];
		if(picId==null||picId==""||picId==undefined){
			 bigImageUrl="static/images/nopic.png";
		}else{
			bigImageUrl="${ctx}/psam/jzwjgPic/loadJzwJgPic?jzwjgPicId="+picId+"&"+Math.floor(Math.random()*100);
		}
		uploaderUrl="${ctx}/psam/jzwjgPic/uploadJzwJgPic?jzwjgid="+jzwjgId;
		
	}
	$("#bigPicShow").hide().attr("src",bigImageUrl).fadeIn();
	
	$("#dlg_picShow").dialog("open").dialog("center").dialog("setTitle","照片信息");
	setTimeout(function(){
		initUploader(uploaderUrl);
	}, 500);
	
}

function picDelete(jgOrfj){
	var picId="";
	var param;
	var ajaxUrl="";
	if(openType=="fj"){
		picId=jzwfjPicIds[jzwfjPicIndex];
		param={"jzwfjPicId":picId};
		ajaxUrl="${ctx}/psam/jzwfjPic/deleteJzwFjpic";
	}else if(openType=="jg"){
		picId=jzwjgPicIds[jzwjgPicIndex];
		param={"jzwjgPicId":picId};
		ajaxUrl="${ctx}/psam/jzwjgPic/deleteJzwJgPic";
	}
	if(PUBUtil.isStrNull(picId)){
		alertMsg.warn("该房间未上传照片");return ;
	}
	 alertMsg.confirm("确定要删除该照片？", {
			cancelCall : function() {alertMsg.close();},
			okCall : function() { alertMsg.close();
					KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
						if(openType=="fj"){
							loadFjInfo(jzwfjid);
						}else if(openType=="jg"){
							loadjzwinfo();
						}
						$("#dlg_picShow").dialog("close");
						KMCORE.ajaxDone(data);
					});
				}
		});
}
//---------------------初始化上传组件-------------------------------------------------
function initUploader(uploaderUrl){
    var $ = jQuery,
    $list = $('#thelist'),
    $btn = $('#ctlBtn'),
    state = 'pending',
    uploader=null;
    if(uploader&&uploader!=null){
    	uploader.destroy();
    }
    $('#uploadButtons').empty();
    $('#uploadButtons').append("<div id=\"picker\">选择文件</div>");
    $list.empty();
uploader = WebUploader.create({
    resize: true,
    swf:"${ctx}/static/libs/webuploader/Uploader.swf",
    server:uploaderUrl,
    pick:{id :'#picker'},
    refresh:true,
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});

// 当有文件添加进来的时候
uploader.on( 'fileQueued', function( file ) {
    $list.append( '<div id="' + file.id + '" class="item">' +
        '<h4 class="info">' + file.name + '</h4>' +
        '<p class="state">等待上传...</p>' +
    '</div>' );
});
// 文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }
    $li.find('p.state').text('上传中');
   // $percent.css( 'width', percentage * 100 + '%' );
});
//回调判定上传成功是否
uploader.on( 'uploadAccept', function( object,ret ) {
	if(ret.statusCode == '200'){
		var picId=ret.message;
		if(openType=="fj"){
			loadFjInfo(jzwfjid);
			bigImageUrl="${ctx}/psam/jzwfjPic/loadJzwFjPic?jzwfjPicId="+picId+"&"+Math.floor(Math.random()*100);
			$("#bigPicShow").hide().attr("src",bigImageUrl).fadeIn();
		}else if(openType=="jg"){
			loadjzwinfo();
			bigImageUrl="${ctx}/psam/jzwjgPic/loadJzwJgPic?jzwjgPicId="+picId+"&"+Math.floor(Math.random()*100);
			$("#bigPicShow").hide().attr("src",bigImageUrl).fadeIn();
		}
		return true;
	}else{
		alert(ret.message);
		return false;
	}
});
uploader.on( 'uploadSuccess', function( file,reason  ) {
	
    $( '#'+file.id ).find('p.state').text('已上传');
});

uploader.on( 'uploadError', function( file,reason  ) {
    $( '#'+file.id ).find('p.state').text('上传出错');
});

uploader.on( 'uploadComplete', function( file ) {
    $( '#'+file.id ).find('.progress').fadeOut();
});

uploader.on( 'all', function( type ) {
    if ( type === 'startUpload' ) {
        state = 'uploading';
    } else if ( type === 'stopUpload' ) {
        state = 'paused';
    } else if ( type === 'uploadFinished' ) {
        state = 'done';
    }

    if ( state === 'uploading' ) {
        $btn.text('暂停上传');
    } else {
        $btn.text('开始上传');
    }
});

$btn.on( 'click', function() {
    if ( state === 'uploading' ) {
        uploader.stop();
    } else {
        uploader.upload();
    }
}); 
}
</script>

</div>