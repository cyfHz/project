<%@tag import="com.kingmon.project.common.dictionary.service.IDictionaryService"%>
<%@tag pageEncoding="UTF-8"%>
<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@tag import="org.springframework.context.ApplicationContext"%>
<%@ attribute name="code" required="true" rtexprvalue="true" description="字典代码"%>
<%@ attribute name="subCode" required="false" rtexprvalue="true" description="字典"%>
<%@ attribute name="var" required="true" rtexprvalue="true" description="输出变量名"%>
<%
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	IDictionaryService dicService =  ctx.getBean(IDictionaryService.class);
%>
<script type="text/javascript">
<%
String dicts="";
if(subCode!=null&&!subCode.isEmpty()){
	dicts=dicService.loadByTypeCodeBy(code, subCode);
}else{
	dicts = dicService.getDictByCode(code);
}	
%>
  window.top.<%=var%> = <%=dicts%>;
</script>
