package com.kingmon.project.util;

import com.kingmon.base.util.PinyinUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
/**
 * @ClassName: Pinyin4jUtil 
 * <p>Title: 汉字转换位汉语拼音，英文字符不变  </p>
 * <p>Description: </p>
 * <p>Company: UJN </p> 
 * @version: v1.0
 * @author: 蒋金敏 
 * @date: 2015年10月13日 下午8:01:28
 */
public class Pinyin4jUtil {
	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String convertToFirstSpell(String chines) {
		StringBuilder pinyin_s = new StringBuilder("");
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyin_s.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0));
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyin_s.append(nameChar[i]);
			}
		}
		return pinyin_s.toString();
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chinese
	 *            汉字
	 * @return 拼音
	 */
	public static String convertToSpell(String chinese) {
		StringBuilder pinyin_s = new StringBuilder("");
		char[] nameChar = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyin_s.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyin_s.append(nameChar[i]);
			}
		}
		return pinyin_s.toString();
	}

	public static void main(String[] args) {
		System.out.println(convertToSpell("22欢迎来到最棒的Java中文社区"));
		System.out.println(PinyinUtil.toPinyin("22欢迎来到最棒的Java中文社区"));
	}
}
