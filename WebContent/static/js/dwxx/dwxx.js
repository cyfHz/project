$(function(){
	
	setTimeout(function(){
		var jzwfjid=$("#jzwfjid").val();
		var params={"jzwfjid":jzwfjid};
		var zagldwbm;
		var url;
		
		$("#tt_dwxx").tabs({
			onSelect:function(title,id){
				if(id=='0'){ //单位基本信息
					url= ctx+"psam/dwxx/loadDwjbxxAccInfo?jzwfjid="+
					jzwfjid;
					$("#dwxx_jbxx_iframe").attr("src",url);
				}else if(id=='1'){  //九小场所
					alertMsg.info("该功能正在开发中。。。");return;
					zagldwbm=$("#zagldwbm").val();
					url= "";//http://zacloud.pcp.sd/newmain.do?id=b659e280-08bd-4e0e-9077-542461df002d&readoms=0.23453191318549216
					$("#dwxx_jxcs_iframe").attr("src",url);
//					doSubmit();
				}else if(id=='2'){ //娱乐场所
					alertMsg.info("该功能正在开发中。。。");return;
					zagldwbm=$("#zagldwbm").val();
//					url= "http://zacloud.pcp.sd/newmain.do?id=6c0b1157-8f36-482a-a375-c6ebf692543e&readoms=0.41766326990909874";
                    url="http://10.48.12.147:8080/JavaAuthFrame/allDwBase/list5.do";
                    $("#dwxx_ylggcs_iframe").attr("src",url);
				}else if(id=='3'){//其他公共场所
					alertMsg.info("该功能正在开发中。。。");return;
					zagldwbm=$("#zagldwbm").val();
					url= "";
					$("#dwxx_qtggcs_iframe").attr("src",url);
				}else if(id=='4'){//重点单位
					alertMsg.info("该功能正在开发中。。。");return;
					zagldwbm=$("#zagldwbm").val();
					url= ctx+"psam/dwxx/loadDwjbxxAccInfo?jzwfjid="+
					jzwfjid;
					$("#dwxx_zddw_iframe").attr("src",url);
				}else if(id=='5'){//特种行业
					alertMsg.info("该功能正在开发中。。。");return;
					zagldwbm=$("#zagldwbm").val();
					url= ctx+"psam/dwxx/loadDwjbxxAccInfo?jzwfjid="+
					jzwfjid;
					$("#dwxx_tzhy_iframe").attr("src",url);
				}else if(id=='6'){//保安服务
					alertMsg.info("该功能正在开发中。。。");return;
					zagldwbm=$("#zagldwbm").val();
					url= ctx+"psam/dwxx/loadDwjbxxAccInfo?jzwfjid="+
					jzwfjid;
					$("#dwxx_bafwgs_iframe").attr("src",url);
				}
//				enterDwxxJbxx(url);
			}
		})
		
	}, 100);
})

function enterDwxxJbxx(url){
	var tab = $('#tt_dwxx').tabs('getSelected');  
	$('#tt_dwxx').tabs('update', {
		tab: tab,
		options: {
			href: url 
		}
	});
}

function enterdwxxKy(url){
	$.ajax(url,{
		'dataType':'jsonp'
	})
}








