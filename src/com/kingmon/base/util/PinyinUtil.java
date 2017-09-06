package com.kingmon.base.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinyinUtil {
	static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	static {
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	public static void main(String[] args) {
		System.out.println(toPinyin("济微路106号济南大学"));
		System.out.println(toSzm("济微路106号济南大学"));
		System.out.println(toSzm("济南大学11J"));
		System.out.println(toPinyin("王官庄西路1-101#08"));
		System.out.println(toPinyin("执拗"));
		System.out.println(toPinyin("长沙市长"));
	}

	/**
	 * @param 汉字
	 * @return 拼音（非汉字字符不变）
	 */
	public static String toPinyin(String hz) {
		String py = "";
		try {
			char[] charArray = hz.toCharArray();
			for (char c : charArray) {
				if (!isChinese(c)) {
					py += c;
				} else {
					String[] array = PinyinHelper.toHanyuPinyinStringArray(c, format);
					if (array != null && array.length > 0) {
						// System.out.println("|-------|\t"+Arrays.asList(array));
						py += array[0];
					}
				}

			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return py;
	}

	/**
	 * @param 汉字
	 * @return 首字母拼音（非汉字字符不变）
	 */
	public static String toSzm(String hz) {
		if (hz == null || hz.isEmpty()) {
			return "";
		}
		String py = "";
		try {
			char[] charArray = hz.toCharArray();
			for (char c : charArray) {
				if (!isChinese(c)) {
					py += c;
				} else {
					String[] array = PinyinHelper.toHanyuPinyinStringArray(c, format);
					if (array != null && array.length > 0) {
						py += array[0].charAt(0);
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return py;
	}
	
	
	public static boolean isChinese(char a) { 
	     int v = (int)a; 
	     return (v >=19968 && v <= 171941); 
	}
}
