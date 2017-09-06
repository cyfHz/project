<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="km" uri="http://www.ujn.edu.cn/kingmon"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />  
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<script>
var ctx="${ctx}"+"/";
$(function(){setTimeout(function() {$("form").each(function(){$(this).form('validate'); $(".tooltip .tooltip-right .validatebox-tip").hide();}); $(".tooltip.tooltip-right").hide();}, 500);});

</script>