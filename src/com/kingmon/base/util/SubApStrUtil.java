package com.kingmon.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;


public class SubApStrUtil extends org.apache.commons.lang3.StringUtils{

	public static String removeLastChars(String target,String lastChar){
		if(target==null||"".equals(target)){
			return target;
		}
		while(target.endsWith(lastChar)){
			target=substringBeforeLast(target, lastChar);
		}
		return target;
	}
	

	public static String UnitMoreSpan(String str)    {
		if(str==null){
			return "";
		}
		return str.replaceAll("\\[s]+", "");
    }
	public static Object trimToNullIfStr(Object obj){
		if(obj==null){
			return null;
		}
		if(obj instanceof java.lang.String){
			String str=String.valueOf(obj);
			if(str==null||"".equals(str.trim())){
				return null;
			}else{
				return str.trim();
			}
		}
		return obj;
	}
	public static Object trimToEmptyIfStr(Object obj){
		if(obj==null){
			return null;
		}
		if(obj instanceof java.lang.String){
			String str=String.valueOf(obj);
			if(str==null||"".equals(str.trim())){
				return "";
			}else{
				return str.trim();
			}
		}
		return obj;
	}
	public static boolean isEmptyAfterTrimE(String str){
		return isEmpty(trimToEmpty(str));
	}
	public static boolean isNullAfterTrimN(String str){
		return  null==trimToNull(str);
	}
	//------------------------------------------------------------
	public static String parseToSplitStr(List<?> value) {
		if(value==null){
			return null;
		}
		if(value.size()==0){
			return "";
		}
		List<Object> target=new ArrayList<Object>();
		for(Object obj:value){
			if(obj!=null){
				target.add(obj);
			}
		}
		String str=SubApStrUtil.join(target,",");
		return str;
	}
	
	public static String parseToSplitStr(Object[] value) {
		if(value==null){
			return null;
		}
		if(value.length==0){
			return "";
		}
		List<Object> target=new ArrayList<Object>();
		for(Object obj:value){
			if(obj!=null){
				target.add(obj);
			}
		}
		String str=SubApStrUtil.join(target,",");
		return str;
	}
	//1,2,3
	public static Long[] parseToLongArray(String value) {
		String[] objectArray = value.split(",");
		if (objectArray != null && objectArray.length > 0) {
			Long[] longArray = new Long[objectArray.length];
			for (int i = 0; i < objectArray.length; i++) {
				longArray[i] = Long.parseLong(objectArray[i]);
			}
			return longArray;
		}
		return null;
	}

	/**
	 * 判断字符串是否符合   正则表达式
	 * @param input
	 * @param regex
	 * @return
	 */
	public static boolean verifyWord(String input, String regex) {
		if (input == null)
			input = "";
		if (regex == null)
			regex = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		boolean flag = m.matches();
		return flag;
	}
	//------------------------------------------------------------	
	/**
	 * 字符串首字母大写
	 * @param str
	 * @return
	 */
	public static String toUpCaseFirst(String str) {
		if (str == null || "".equals(str))
			return str;
		boolean flag = verifyWord(str, "^[A-Za-z0-9]+$");
		if (flag) {
			char temp[] = str.toCharArray();
			temp[0] = str.toUpperCase().toCharArray()[0];
			str = String.valueOf(temp);
		}
		return str;
	}
	public static String toLowerCaseFirst(String str){
		if(str==null||str.length()==0){
			return str;
		}
		String first = str.substring(0, 1).toLowerCase();
		String rest = str.substring(1, str.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}
	
	/**
	 * 字符串数组中每个元素首字母大写
	 * @param str
	 * @return
	 */
	public static String[] toUpCaseFirst(String str[]) {
		if (str == null || str.length == 0)
			return str;
		String result[] = new String[str.length];
		for (int i = 0; i < result.length; i++)
			result[i] = toUpCaseFirst(str[i]);

		return result;
	}
	//------------------------------------------------------------

	/**
	 * 返回指定长度的随机的数字字符串。
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		String ret = "";
		for (int i = 0; i < length; i++) {
			ret += RandomStringUtils.random(9);
		}
		return ret;
	}

	/**
	 * 是否是降序或者升序
	 * @param str
	 * @param ascOrDesc
	 * @return
	 */
	public static boolean isAscDesc(String str, int ascOrDesc) {
		char last = (char) (str.charAt(0) - ascOrDesc);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c != (last + ascOrDesc)) {
				return false;
			}
			last = c;
		}
		return true;
	}
	public static Boolean convertToBool(String str,boolean defautlt){
		Boolean res=false;
		try{
			 res=Boolean.parseBoolean(str);
		}catch(Exception e){
			return defautlt;
		}
		return res;
	}
	public static String coverChars(String str,char targetChar,int length){
		if(str==null||str.length()==0){
			return null;
		}
		if(str.length()>length){
			return str.substring(0, length);
		}
		String res=str;
		char[] xx=new char[length-str.length()];
		for(int i=0;i<length-str.length();i++){
			xx[i]=targetChar;
		}
		res+=new String(xx);
		
//		for(int i=str.length();i<length;i++){
//			res+=targetChar;
//		}
		return res;
	}
	public static void main(String[] sd){
		String xx="370";
		String res=coverChars(xx,'0',6);
	 System.out.println(res);
	}
}
