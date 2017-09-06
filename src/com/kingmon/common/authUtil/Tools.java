package com.kingmon.common.authUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tools {
	public static int getMin(List<Integer> ins){
		if(ins==null||ins.size()==0){
			return -1;
		}
		Object[] objs = ins.toArray();
		Arrays.sort(objs);
		return Integer.valueOf(String.valueOf(objs[0]));
	}
	public static List<Integer> convertToInt(List<String> list){
		 List<Integer> resList=new ArrayList<Integer>();
		if(list==null||list.size()==0){
			return null;
		}
		for(String str:list){
			try{
				if("all".equalsIgnoreCase(str)){
					resList.add(2);
				}else{
					int x=Integer.parseInt(str);
					resList.add(x);
				}
			}catch(Exception e){
				
			}
		}
		return resList;
	}
}
