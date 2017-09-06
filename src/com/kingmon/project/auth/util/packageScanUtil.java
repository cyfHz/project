package com.kingmon.project.auth.util;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.kingmon.base.util.LoadClassUtil;

public class packageScanUtil {
	
	public static Set<Class<?>> scanAllController(String basePakage){
		if(!StringUtils.hasText(basePakage)){
			return null;
		}
		String[] str=new String[]{basePakage};
		LoadClassUtil loadPackageClasses=new LoadClassUtil(str,Controller.class );
		Set<Class<?>> calssSet=new LinkedHashSet<Class<?>>();
		try {
			calssSet=loadPackageClasses.getClassSet();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return calssSet;
	}
}
