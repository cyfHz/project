<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/webuploader.jsp" %>

<script>
$(function(){
    var $ = jQuery,
    $list = $('#thelist'),
    $btn = $('#ctlBtn'),
    state = 'pending',
    uploader;

uploader = WebUploader.create({
    // 不压缩image
    resize: false,
    // swf文件路径
    swf:"${pageContext.request.contextPath}/static/libs/webuploader/Uploader.swf",
    // 文件接收服务端。
    server: "${pageContext.request.contextPath}/common/sys/upload/ajaxUpload?utype=xzjd",
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#picker'
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
    $percent.css( 'width', percentage * 100 + '%' );
});

uploader.on( 'uploadAccept', function( object,ret ) {
	if(ret.statusCode == '200'){
	//	alert("上传成功 "+ret.statusCode);
	//	alert("上传成功 ");
		return true;
	}else{
		alert(ret.message);
		return false;
	}
});
uploader.on( 'uploadSuccess', function( file,reason  ) {
	alert(reason.message);
    $( '#'+file.id ).find('p.state').text('已上传');
});

uploader.on( 'uploadError', function( file,reason  ) {
//	alert("uploadError "+reason.message);
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
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 10px;">
		<div id="uploader" class="wu-example">
		    <!--用来存放文件信息-->
		    <div id="thelist" class="uploader-list"></div>
		    <div class="btns">
		        <div id="picker">选择文件</div><br /><br />
		        <button id="ctlBtn" class="btn btn-default">开始上传</button>
		    </div>
		</div>
	</div>
</div>

