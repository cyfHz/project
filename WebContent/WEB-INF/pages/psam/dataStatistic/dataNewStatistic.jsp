<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<%@ include file="/static/meta/includeall.jsp" %>
<title>数据统计</title>
 <style type="text/css">
</style>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',border:false" style="width: 550px;height: 100%">
    <div id="container" style="min-width:540px; width: 540px; height: 760px; min-height:760px;padding-top:5px;"></div>
</div>

<div data-options="region:'east',border:false" style="width: 500px;">
    <div id="containerpie" style="min-width: 500px; height: 500px; "></div>
     <div id="fj_data_button" style="padding:5px;border:1px solid #ddd">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showFjData();">查看下级数据统计</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="showCheckData();">查看数据不完整情况统计</a>
	</div>
</div>
 <div id="dlg_czrk_accqui" class="easyui-dialog" style="width: 900px; height: 540px; padding: 10px 10px"  onClose="asdasd();" closed="true" modal="true" ></div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/highcharts/exporting.js"></script>
 <script type="text/javascript">
//--------------------------基本逻辑---------------------------------------------------
function showFjData(){
	var url =encodeURI("${ctx}/psam/dataStatistic/enterNewOneDataStatistic?orgCode="+orgCode1);
	//var param = {"sjName" : categoryx};
	var param = {};
 	wid=window.top.document.body.clientWidth;
	hei=window.top.document.body.clientHeight;
	var options = {
		title : categoryx+"下级数据",
		width : wid, height : hei,
		url : url, params : param,
		onClosed : function() {}
	};
	editDialog.open(options);
}

function showCheckData(){
	var url =encodeURI("${ctx}/psam/sjjy/");
	//var param = {"sjName" : categoryx};
	/* var param = {};
 	wid=window.top.document.body.clientWidth;
	hei=window.top.document.body.clientHeight;
	var options = {
		title : "数据详情",
		width : wid, height : hei,
		url : url, params : param,
		onClosed : function() {}
	};
	editDialog.open(options); */
	window.open(url,"_blank");
}
$(function () {
	initDataSatistic();
	document.getElementById("fj_data_button").style.display="none";     
}); 
/* 
var categorx=['泰安', '潍坊', '青岛', '济南','泰安x', '潍坊x', '青岛x', '济南x','泰安', '潍坊', '青岛', '济南','泰安x', '潍坊x', '青岛x', '济南x', '济南x'];
var seriesx=[{ 
	name: '不完全匹配数据数量',                                             
    data: [113, 106, 150, 41,113, 106, 150, 41,113, 106, 150, 41,113, 106, 150, 41, 41] 
                                    
}, {                                                               
    name: '完全匹配数据数量',                                             
    data: [27, 44, 50, 69,27, 44, 50, 69,27, 44, 50, 69,27, 44, 50, 69, 69]                                  
}, {                                                               
	name: '实际上报数据数量',                                             
	data: [140, 150, 200, 110,140, 150, 200, 110,140, 150, 200, 110,140, 150, 200, 110, 110] 
}]; */
var categoryx="";
var orgCode1 = "";
var categorx=new Array();
var seriesx=new Array();
var seriesx_totle=new Array();
var seriesx_good=new Array();
var seriesx_not_good=new Array();
function initDataSatistic(){
	var ajaxUrl="${ctx}/psam/dataStatistic/loadNewStatisticData";
	var param = {"orgCode":"","isQueryChild":true};
	KMAJAX.ajaxTodo(ajaxUrl, param, function(data) {
		if (data.statusCode != 200) {
			alertMsg.error(data.message);
			return;
		}
		if(data.statusCode == 200){
			categorx.splice(0,categorx.length);  
			seriesx.splice(0,categorx.length); 
			seriesx_totle.splice(0,categorx.length); 
			seriesx_good.splice(0,categorx.length); 
			seriesx_not_good.splice(0,categorx.length);
			 var showdata=data.data;
			 var len = showdata.length;  
			 for (var i=0; i<len; i++) {
				 var item=showdata[i];
				// console.log(item);
				 var orgName=item.orgName;
				 var orgCode=item.orgCode;
				 categorx.push(orgName);
				 
				 var count_totle=item.count_totle;
				 var count_good=item.count_good;
				 var count_not_good=item.count_not_good;
				 seriesx_totle.push(count_totle);
				 seriesx_good.push(count_good);
				 seriesx_not_good.push(count_not_good);
				 
				 // var count_haveNoJWQ=item.count_haveNoJWQ;
				//  var count_haveNoZB=item.count_haveNoZB;
				 //console.log(item.orgName);
			 }
			 seriesx.push({name: '不完全匹配数据数量', data: seriesx_not_good});
			 seriesx.push({name: '完全匹配数据数量', data: seriesx_good});
			 seriesx.push({name: '实际上报数据数量', data: seriesx_totle});
			// console.log(seriesx);
			$('#container').highcharts({                                           
		        chart: {type: 'bar' },                                                                 
		        title: { text: '标准地址采集进度统计'},                                                                 
		        xAxis: {
		        	categories: categorx,
		        	//title: {text: null},
		        	labels: {
		                formatter: function () {
		                    return this.value;
		                }
		            }
		     	},                                                                 
		        yAxis: {min: 0,                                                        
		            title: {                                                       
		                align: 'high',
		                text:"统计值"
		            },
		            labels: {  //在报表上显示的一些文本                                                     
		            	  formatter: function () {
			                    return this.value;
			                }                                      
		            }                                                               
		        },                                                                 
		        tooltip: {valueSuffix: ''},                                                                 
		        plotOptions: {                                                     
		            bar: {                                                         
		                dataLabels: {                                              
		                    enabled: true //是否在点的旁边显示数据                                         
		                },
		                events:{//监听点的鼠标事件  
			                      click: function(event) {
			                    	   categoryx=event.point.category;
			                    	 // console.log(categoryx);
			                    	  var piedata=new Array();
			                    	  var code = "";
			                    	  for (var i=0; i<len; i++) {
			             				 var item=showdata[i];
			             				 var orgName=item.orgName;
			             				 code = item.orgCode;
			             				 if(orgName==categoryx){
			             					 orgCode1 = code;
			             					 var count_haveNoJWQ=item.count_haveNoJWQ;
				             				 var count_haveNoZB=item.count_haveNoZB;
				             				 var count_haveNoBoth=item.count_haveNoBoth;
				             				 piedata=[{name: '缺少警务区',y: count_haveNoJWQ},{name: '缺少坐标',y: count_haveNoZB},{name: '两者都缺失',y: count_haveNoBoth}];
			             				 }
			             			 }
			                    	pieData(piedata);
			                    	document.getElementById("fj_data_button").style.display=""; 
			                     }
			                 }
		            }
		        },                                                                 
		        legend: {//图例说明是包含图表中数列标志和名称的容器。                                                          
		           layout: 'horizontal', //图例说明布局（垂直显示,默认横向显示）                                            
		            align: 'center',                                                
		            verticalAlign: 'bottom',                                          
		            floating: true                                                
		        },                                                                 
		        credits: {//图表版权信息                                                        
		            enabled: false 
		        },                                                                 
		        series:seriesx                                                               
		    });  
		}
		
	});
	 
}

function pieData(datax){
	 $('#containerpie').highcharts({
		 credits: {enabled: false }, 
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            type: 'pie'
	        },
	        title: { text: '不完全匹配数据类型占比'},
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            name: '百分比',
	            colorByPoint: true,
	            data:datax
	           /*  data: [{
	                name: '缺少警务区',
	                y: 0.5
	            }, {
	                name: '缺少坐标',
	                y: 0.5,
	            }] */
	        }]
	    });
	}
    </script>
</body>
</html>