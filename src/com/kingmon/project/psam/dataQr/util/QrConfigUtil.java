package com.kingmon.project.psam.dataQr.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.elasticsearch.common.collect.Maps;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.kingmon.base.exception.ServiceLogicalException;

public class QrConfigUtil {

	/*
	 *  key=显示标题#查询表#查询主键字段#查询标题显示字段
		mlph=门楼牌号#dz_mlph#ywlsh#dzmc
	 */
	private static final Map<String,QrConfigModel> configMap=Maps.newHashMap();
	
	private static void initConfigMap(){
		Resource res=new ClassPathResource("/config/properties/QrType.properties");
		try {
			Properties prop=PropertiesLoaderUtils.loadProperties(res);
			Set<Object> keySet=prop.keySet();
			Iterator<Object> it=keySet.iterator();
			while(it.hasNext()){
				String key=(String)it.next();
				String value=prop.getProperty(key);
				String[] valueArray=value.split("#");
				QrConfigModel configModel=new QrConfigModel();
				String showTitle=new String(valueArray[0].getBytes("ISO-8859-1"),"UTF-8");
				configModel.setConfigKey(key).setShowTitle(showTitle).setQueryTable(valueArray[1]).setQueryPrimaryField(valueArray[2]).setQueryShowField(valueArray[3]);
				configMap.put(key, configModel);
			}
		} catch (IOException e) {
//			e.printStackTrace();
			throw new ServiceLogicalException("配置文件错误，请联系管理员");
		}
		if(configMap.isEmpty()){
			configMap.put("", null);
		}
	}
	public static QrConfigModel getConfig(String key){
		if(configMap==null||configMap.isEmpty()){
			initConfigMap();
		}
		return configMap.get(key);
	}
	public static Map<String,QrConfigModel> getAllConfig(){
		if(configMap==null||configMap.isEmpty()){
			initConfigMap();
		}
		return configMap;
	}
	public static void reLoadConfig(String key){
		configMap.clear();
		initConfigMap();
	}
}
