<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tool</title>
<link href="${pageContext.request.contextPath}/static/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body, html {
	height: 100%;
}

body {
	font-family: "Source Sans Pro", sans-serif;
	color: #656565;
}

body {
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 14px;
	line-height: 1.52857143;
	color: #515253;
	background-color: #f5f7fa;
}

.content-wrapper {
	padding: 20px;
}

.content-wrapper {
	padding: 15px;
	width: 100%;
	border-top: 1px solid rgba(0, 0, 0, .15);
	margin-top: -1px;
}

.bg-primary {
	background-color: #5d9cec;
	color: #fff !important;
}

.bg-primary-dark {
	background-color: #2f80e7;
	color: #fff !important;
}

.widget .panel, .widget.panel {
	overflow: hidden;
}

.widget {
	margin-bottom: 20px;
	border: 0;
}

.row-table {
	display: table;
	table-layout: fixed;
	height: 100%;
	width: 100%;
	margin: 0;
}

.row-table>[class*=col-] {
	display: table-cell;
	float: none;
	table-layout: fixed;
	vertical-align: middle;
}

.bg-primary-dark {
	background-color: #2f80e7;
	color: #fff !important;
}

.pv-lg {
	padding-top: 15px !important;
	padding-bottom: 15px !important;
}

.bg-purple-dark {
	background-color: #564aa3;
	color: #fff !important;
}

.bg-purple {
	background-color: #7266ba;
	color: #fff !important;
}

.bg-green {
	background-color: #37bc9b;
	color: #fff !important;
}

.bg-green-dark {
	background-color: #2b957a;
	color: #fff !important;
}
.icon-stop{
	color:#d9534f;
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="content-wrapper">
		<div class="row">
			<div class="col-lg-4">
				<div class="panel widget bg-primary">
					<div class="row row-table">
						<div class="col-xs-4 text-center bg-primary-dark pv-lg">
							<img src="${pageContext.request.contextPath}/static/images/mlph.png">
						</div>
						<div class="col-xs-8 pv-lg">
							<div class="h2 mt0">${mlphCnt}</div>
							<div><h4>门楼牌号</h4></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="panel widget bg-purple">
					<div class="row row-table">
						<div class="col-xs-4 text-center bg-purple-dark pv-lg">
							<img src="${pageContext.request.contextPath}/static/images/jzw.png">
						</div>
						<div class="col-xs-8 pv-lg">
							<div class="h2 mt0">${jzwCnt}</div>
							<div><h4>建筑物信息</h4></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="panel widget bg-green">
					<div class="row row-table">
						<div class="col-xs-4 text-center bg-green-dark pv-lg">
							<img src="${pageContext.request.contextPath}/static/images/jwq.png">
						</div>
						<div class="col-xs-8 pv-lg">
							<div class="h2 mt0">${jwqCnt}</div>
							<div><h4>警务区</h4></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-9">
				<div class="row">
					<div class="col-lg-12">
						<div id="panelChart9" class="panel panel-default panel-demo">
							<div class="panel-heading">
								<div class="panel-title">同步任务列表<button data-toggle="tooltip" data-placement="top" title="点击<c:if test="${enableAutoSync}">取消</c:if><c:if test="${!enableAutoSync}">启用</c:if>定时自动同步" type="button" class="close toogle-auto"><span class="glyphicon glyphicon-<c:if test="${enableAutoSync}">off</c:if><c:if test="${!enableAutoSync}">play</c:if>" aria-hidden="true"></span></button></div>
							</div>
							<div class="panel-wrapper collapse in" aria-expanded="true">
								<div class="panel-body">
									<div class="table-responsive clearfix">
										<table class="table table-hover">
											<thead>
												<tr>
													<th><span>任务名称</span></th>
													<th><span>任务类型</span></th>
													<th><span>开始时间</span></th>
													<th><span>结束时间</span></th>
													<th class="text-center"><span>状态</span></th>
													<th>&nbsp;</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${tasks}" var="entry">
												<tr>
													<td>
														<a href="javascript:void(0)" class="task-name" 
															data-taskId="${entry.value.taskId}"
															data-taskName="${entry.value.taskName}"
															data-type="${entry.value.type}"
															data-start="${entry.value.start}"
															data-end="${entry.value.end}"
															data-status="${entry.value.status}" 
															data-successCnt="${entry.value.successCnt}" 
															data-totalCnt="${entry.value.totalCnt}" 
															data-failCnt="${entry.value.failCnt}" 
															data-errorMsg="${entry.value.errorMsg}" 
															data-notIndexedOnly="${entry.value.notIndexedOnly}" 
															data-stopOnError="${entry.value.stopOnError}" 
															data-current="${entry.value.current}" 
															data-currentMsg='${entry.value.currentMsg}'>
															${entry.value.taskName}
														</a>
													</td>
													<td>${entry.value.type}</td>
													<td>${entry.value.start}</td>
													<td>${entry.value.end}</td>
													<td class="text-center">
														<span class='label label-<c:if test="${entry.value.status eq 0}">primary</c:if><c:if test="${entry.value.status eq 1}">success</c:if><c:if test="${entry.value.status eq 2}">default</c:if><c:if test="${entry.value.status eq 3}">danger</c:if>'>
															<c:if test="${entry.value.status eq 0}">
																未开始
															</c:if>
															<c:if test="${entry.value.status eq 1}">
																正在执行
															</c:if>
															<c:if test="${entry.value.status eq 2}">
																已完成
															</c:if>
															<c:if test="${entry.value.status eq 3}">
																异常结束
															</c:if>
														</span>
													</td>
													<td class="text-center">
														<c:if test="${entry.value.status eq 1}">
															<span class="glyphicon glyphicon-off icon-stop" aria-hidden="true"></span>
														</c:if>
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<aside class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<div class="panel-title">工具</div>
					</div>
					<div class="panel-body">
						<div class="span2">
							<a href="#" data-toggle="modal" data-target="#createSyncTaskModal" class="btn btn-primary btn-lg btn-block">创建同步任务</a>
							<hr />
							<a href="http://10.48.144.55:9200/_plugin/head/" target="_blank" class="btn btn-primary btn-lg btn-block">集群管理</a>
							<hr />
							<a target="_blank" href="http://10.48.144.55:9200/_plugin/marvel/sense/index.html" class="btn btn-primary btn-lg btn-block">打开Console</a>
							<hr />
							<a href="#" class="btn btn-primary btn-lg btn-block">常用操作</a>
						</div>
					</div>
				</div>
			</aside>
		</div>
	</div>
	
	
	<div class="modal fade" id="createSyncTaskModal" role="dialog" aria-labelledby="mlabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="mlabel">同步任务</h4>
			</div>
			<div class="modal-body">
				<form id="save-form" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-4 control-label">任务名称</label>
						<div class="input-group col-sm-7">
							<input name="taskName" type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">类型</label>
						<div class="input-group col-sm-7">
							<select class="form-control" name="type">
								<option value="MLPH">同步门楼牌号</option>
								<option value="JZW">同步建筑物信息</option>
								<option value="JWQ">同步警务区</option>
							</select>
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">只同步最新数据</label>
						<div class="input-group col-sm-7">
							<input name="onlyNew" type="checkbox" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">出错后停止任务</label>
						<div class="input-group col-sm-7">
							<input name="errorStop" type="checkbox" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="createSyncTaskBtn" type="button" class="btn btn-primary">创建</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="syncTaskDetailModal" role="dialog" aria-labelledby="mlabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="mlabel">任务信息</h4>
			</div>
			<div class="modal-body">
				<form id="detail-form" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-4 control-label">任务名称</label>
						<div class="input-group col-sm-7">
							<input disabled name="taskName" type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">成功</label>
						<div class="input-group col-sm-7">
							<input disabled name="successCnt" type="text" class="form-control" /><div class="input-group-addon">条</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">需要导入</label>
						<div class="input-group col-sm-7">
							<input disabled name="totalCnt" type="text" class="form-control" /><div class="input-group-addon">条</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">失败</label>
						<div class="input-group col-sm-7">
							<input disabled name="failCnt" type="text" class="form-control" /><div class="input-group-addon">条</div>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-4 control-label">错误信息</label>
						<div class="input-group col-sm-7">
							<input disabled name="errorMsg" type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">正在导入第</label>
						<div class="input-group col-sm-7">
							<input disabled name="current" type="text" class="form-control" /><div class="input-group-addon">条</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">日志</label>
						<div class="input-group col-sm-7">
							<textarea disabled name="currentMsg" class="form-control" rows="10" cols="80"></textarea>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-4 control-label">只同步最新数据</label>
						<div class="input-group col-sm-7">
							<input disabled name="onlyNew" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">出错后停止任务</label>
						<div class="input-group col-sm-7">
							<input disabled name="errorStop" type="text" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="modalLabel">提示</h4>
      </div>
      <div class="modal-body">
        <h4>确定要停止该定时任务吗?</h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" id="stop-btn" class="btn btn-primary" >确定</button>
      </div>
    </div>
  </div>
</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/bootstrap/jquery.1.11.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$('[data-toggle="tooltip"]').tooltip();
			$('.toogle-auto').click(function(){
				$.post("${pageContext.request.contextPath}/psam/elasticSync/toogleAutoSync").success(function(data){
							window.location.href=window.location.href;
				});
			});
			$('#createSyncTaskModal').on('show.bs.modal',function(){
				$('#save-form')[0].reset();
				$('input[name="taskName"]').val('Sync-'+(new Date()).toISOString());
			});
			$('#createSyncTaskBtn').click(function(){
				$.post("${pageContext.request.contextPath}/psam/elasticSync/create",
						$('#save-form').serialize()).success(function(data){
							window.location.href=window.location.href;
				});
			});
			
			$('.icon-stop').click(function(){
				var taskId  = $(this).closest('tr').children('td:first').children('a').attr('data-taskid');
				
				$('#confirmModal').modal('show');
				$('#stop-btn').off("click").click(function() {
					$.post("${pageContext.request.contextPath}/psam/elasticSync/stop",{taskId:taskId}).success(function(data){
								window.location.href=window.location.href;
					});
					$('#confirmModal').modal('hide');
				});
				
			});
			
			$('.task-name').click(function(){
				
				$('#detail-form input[name="taskName"]').val($(this).attr('data-taskName'));
				$('#detail-form input[name="successCnt"]').val($(this).attr('data-successCnt'));
				$('#detail-form input[name="totalCnt"]').val($(this).attr('data-totalCnt'));
				$('#detail-form input[name="failCnt"]').val($(this).attr('data-failCnt'));
				$('#detail-form input[name="errorMsg"]').val($(this).attr('data-errorMsg'));
				$('#detail-form input[name="current"]').val($(this).attr('data-current'));
				var log = $.parseJSON($(this).attr('data-currentMsg'));
				$('#detail-form textarea[name="currentMsg"]').val(log.join('\r\n'));
				$('#detail-form input[name="onlyNew"]').val($(this).attr('data-notIndexedOnly'));
				$('#detail-form input[name="errorStop"]').val($(this).attr('data-stopOnError'));
				
				$('#syncTaskDetailModal').modal('show');
			});
		});//end init
	</script>
</body>
</html>
