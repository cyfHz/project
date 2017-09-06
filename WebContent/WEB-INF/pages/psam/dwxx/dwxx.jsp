<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/TableNew.css"></link>
<style>
tr{
	line-height: 20px;
}
</style>
<script type="text/javascript">
	function doSubmit(){
		if(!KMAJAX.validateFromCallback($("#fm"),KMCORE.ajaxDoneAndCloseDialog)){
			alertMsg.warn("请确认校验不通过数据");
		}
	}
	function cancel(){
		editDialog.close();
	}
</script>
<t:DataDict code="qzzl" var="qzzlDict"></t:DataDict>
<t:DataDict code="zjlx" var="zjlxDict"></t:DataDict>
<t:DataDict code="gj" var="gjDict"></t:DataDict>
<t:DataDict code="ZZMM" var="zzmmDict"></t:DataDict>
<t:DataDict code="MZ" var="mzDict"></t:DataDict>
<t:DataDict code="xb" var="xbDict"></t:DataDict> 
<t:DataDict code="FWLB" var="fwlbDict"></t:DataDict>
<t:DataDict code="FWXZ" var="fwxzDict"></t:DataDict>
<t:DataDict code="FWYT" var="fwytDict"></t:DataDict>
<t:DataDict code="fwlx" var="fwlxDict"></t:DataDict>
<t:DataDict code="rygx" var="rygxDict"></t:DataDict>
<div class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',border:false" title="" style="overflow-y:scroll; padding: 5px; height: 500px;" class="container">
	<input type="hidden" id="jzwfjid" name="jzwfjid" value="${jzwfjid}"/>
	<div id="tt_dwxx" class="easyui-tabs" style="fit: true;">  
   
      <div title="单位基本信息采集" id="div_dwxx" style="padding:10px;" >  
         <iframe id="dwxx_jbxx_iframe" name="dwxx_iframe" src="" style="padding:2px;width:98%; ;height: 560px;overflow: scroll;"></iframe>
      </div>  
    
      <div title="九小场所"  style="overflow:auto;fit: true;padding: 10px;" >  
          <iframe id="dwxx_jxcs_iframe" name="dwxx_iframe" src="" style="width:98% ;height: 560px;overflow: scroll;"></iframe>
      </div> 
      
      <div title="娱乐公共场所"  style="overflow:auto;fit: true;padding: 10px;" >  
          <iframe id="dwxx_ylggcs_iframe" name="dwxx_iframe" src="" style="width:98% ;height: 560px;overflow: scroll;"></iframe>
      </div> 
    
      <div title="其他公共场所"  style="overflow:auto;fit: true;padding: 10px;" >  
          <iframe id="dwxx_qtggcs_iframe" name="dwxx_iframe" src="" style="width:98% ;height: 560px;overflow: scroll;"></iframe>
      </div> 
      
      <div title="重点单位"  style="overflow:auto;fit: true;padding: 10px;" >  
          <iframe id="dwxx_zddw_iframe" name="dwxx_iframe" src="" style="width:98% ;height: 560px;overflow: scroll;"></iframe>
      </div> 
      
      <div title="特种行业"  style="overflow:auto;fit: true;padding: 10px;" >  
          <iframe id="dwxx_tzhy_iframe" name="dwxx_iframe" src="" style="width:98% ;height: 560px;overflow: scroll;"></iframe>
      </div> 
      
      <div title="保安服务公司"  style="overflow:auto;fit: true;padding: 10px;" >  
          <iframe id="dwxx_bafwgs_iframe" name="dwxx_iframe" src="" style="width:98% ;height: 500px;overflow: scroll;"></iframe>
      </div> 
</div>  
	

	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dict.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/dwxx/dwxx.js"></script>
<script type="text/javascript">
  
</script>
</div>
