<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/meta/taglib.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',border:false" style="width: 650px;height: 100%">
    <div id="two_container" style="min-width:600px; width: 600px; min-height:760px;"></div>
</div>
<div data-options="region:'east',border:false" style="width: 600px;">
    <div id="two_containerpie" style="min-width: 580px; height: 700px; "></div>
</div>
<input type="hidden" id="orgCode2" value="${orgCode}">
<script type="text/javascript">

$(function () {
	initDataSatistic();
}); 
var categoryx="";
var categorx=new Array();
var seriesx=new Array();
var seriesx_totle=new Array();
var seriesx_good=new Array();
var seriesx_not_good=new Array();
function initDataSatistic(){
	var ajaxUrl="${ctx}/psam/dataStatistic/loadNewStatisticData";
	var param = {"orgCode":$("#orgCode2").val(),"isQueryChild":false};
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
			$('#two_container').highcharts({                                           
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
			                    	  for (var i=0; i<len; i++) {
			             				 var item=showdata[i];
			             				 var orgName=item.orgName;
			             				 if(orgName==categoryx){
			             					//console.log(item);
			             					 var count_haveNoJWQ=item.count_haveNoJWQ;
				             				 var count_haveNoZB=item.count_haveNoZB;
				             				 var count_haveNoBoth=item.count_haveNoBoth;
				             				  piedata=[{name: '缺少警务区',y: count_haveNoJWQ},{name: '缺少坐标',y: count_haveNoZB},{name: '两者都缺失',y: count_haveNoBoth}];
			             				 }
			             			 }
			                    	pieData(piedata);
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
	 $('#two_containerpie').highcharts({
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
</div>
