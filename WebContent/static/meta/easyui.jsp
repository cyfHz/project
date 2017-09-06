<script src="${pageContext.request.contextPath}/static/libs/easyui/jquery-1.8.0.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/libs/easyui/themes/icon.css" rel="stylesheet" type="text/css"></link>
<%
		String easyuiThemeName="default";
		Cookie cookies[] =request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for(Cookie cookie : cookies){
				if (cookie.getName().equals("easyuiThemeName")) {
					easyuiThemeName = cookie.getValue();
					break;
				}
			}
		}
%>
<link id="easyuiTheme"  href="${pageContext.request.contextPath}/static/libs/easyui/themes/<%=easyuiThemeName %>/easyui.css" rel="stylesheet" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css"></link>

<script src="${pageContext.request.contextPath}/static/libs/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/libs/easyui/jquery.cookie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/libs/easyui/easyloader.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/libs/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/static/js/json2.min.js" type="text/javascript"></script> 