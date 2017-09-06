package com.kingmon.base.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

//import org.zht.common.log.util.LogUtil;
/**
 * 系统Properties 配置加载工具
 * @author zhaohuatai
 *
 */
public class ConfigUtil {
	public static final String defaultConfigDir="properties";

	/**
	 * 从系统ClassPath:/properties/目录开始查找文件
	 * @param configfile 
	 * @param config
	 * @return
	 */
	public static String getConfigFromDefaultDir(String configfile,String configKey ) {
		java.io.InputStream inputstream = ConfigUtil.class.getResourceAsStream("/"+defaultConfigDir+File.separatorChar + configfile);
		Properties properties = new Properties();
		try {
			properties.load(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.genErrorLog(ConfigUtil.class,"getConfig",e.getMessage(), e);
		}finally{
			try {
				inputstream.close();
			} catch (IOException e) {
			}
		}
		String s = properties.getProperty(configKey, null);
		return s;
	}
	/**
	 * 从系统ClassPath:/properties/目录开始查找文件
	 * @param configfile
	 * @param configName
	 * @param defaultValue
	 * @return
	 */
	public static String getConfigFromDefaultDir(String configfile,String configKey, String defaultValue) {
		java.io.InputStream inputstream = ConfigUtil.class.getResourceAsStream("/"+defaultConfigDir+File.separatorChar + configfile);
		Properties properties = new Properties();
		try {
			properties.load(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.genErrorLog(ConfigUtil.class,"getConfig",e.getMessage(), e);
		}finally{
			try {
				inputstream.close();
			} catch (IOException e) {
			}
		}
		String s = properties.getProperty(configKey,defaultValue);
		return s;
	}
	
	/**
	 * 从系统ClassPath:/目录开始查找文件,若有目录，参数请带有全路径
	 * @param configfile :系统ClassPath:/目录开始查找文件
	 * @param config
	 * @return
	 */
	public static String getConfigFromFullPath(String configfile,String configKey ) {
		java.io.InputStream inputstream = ConfigUtil.class.getResourceAsStream("/" + configfile);
		Properties properties = new Properties();
		try {
			properties.load(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.genErrorLog(ConfigUtil.class,"getConfig",e.getMessage(), e);
		}finally{
			try {
				inputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String s = properties.getProperty(configKey, null);
		return s;
	}
	/**
	 * 从系统ClassPath:/目录开始查找文件,若有目录，参数请带有全路径
	 * @param configfile :系统ClassPath:/目录开始查找文件
	 * @param configKey
	 * @param defaultValue
	 * @return
	 */
	public static String getConfigFromFullPath(String configfile,String configKey, String defaultValue) {
		java.io.InputStream inputstream = ConfigUtil.class.getResourceAsStream("/" + configfile);
		Properties properties = new Properties();
		try {
			properties.load(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.genErrorLog(ConfigUtil.class,"getConfig",e.getMessage(), e);
		}finally{
			try {
				inputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String s = properties.getProperty(configKey, defaultValue);
		return s;
	}
	public static void main(String[] args) {

	}
}
