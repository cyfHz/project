<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title> 
<link href="jg.css" rel="stylesheet" type="text/css"></link>
<script src="jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="jg.js" type="text/javascript"></script>
</head>
<body>
<br/>
楼层数static:<input id="lcs" value="10" type="text"  />
单元数:<input id="dys" value="4" type="text"  />
每单元房间数:<input id="fjs" value="2" type="text"  />
<input type="button" value="生成" onclick="showA();" /><br/><br/>
方向：
<select id="dir">
<option value="0"> 横向</option>
<option value="1"> 纵向</option>
</select>
&nbsp;&nbsp;
数量:<input id="count" value="2" type="text"  />
<input type="button" value="拆分" onclick="chaifen();" />
<input type="button" value="合并" onclick="hebing();" />
<br/><br/>
<div id="container" style="position: absolute;left:100px;top:100px border-color: red;border-width: 0px">
</div> 
</body> 
</html> 