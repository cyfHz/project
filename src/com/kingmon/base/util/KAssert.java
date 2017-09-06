package com.kingmon.base.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class KAssert {
	/**
	 * 如内容不是true 则抛出ServiceLogicalException
	 * @param expression ：待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression)
			AlertSLEUtil.Error(message);
	}
	
	/**
	 * 如内容不是False 则抛出ServiceLogicalException
	 * @param expression ：待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void isFalse(boolean expression, String message) {
		if (expression)
			AlertSLEUtil.Error(message);
	}
//	/**
//	 * 如内容不是true则抛出ServiceLogicalException
//	 * @param expression ：待判断的内容
//	 */
//	public static void isTrue(boolean expression) {
//		isTrue(expression, "[Assertion failed] - this expression must be true");
//	}
	
//	public static void state(boolean expression, String message) {
//		if (!expression)
//			AlertSLEUtil.Error(message);
//	}
//
//	public static void state(boolean expression) {
//		state(expression,"[Assertion failed] - this state invariant must be true");
//	}
	
	/**
	 * 判断内容是否 null ，如果不是null 则抛出ServiceLogicalException
	 * 
	 * @param object  ：待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void isNull(Object object, String message) {
		if (object != null)
			AlertSLEUtil.Error(message);
	}

//	public static void isNull(Object object) {
//		isNull(object, "[Assertion failed] - the object argument must be null");
//	}
	
	/**
	 * 判断内容是否  不是 null ，如果是null 则抛出ServiceLogicalException
	 * 
	 * @param object  ：待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void notNull(Object object, String message) {
		if (object == null)
			AlertSLEUtil.Error(message);
	}

//	public static void notNull(Object object) {
//		notNull(object,"[Assertion failed] - this argument is required; it must not be null");
//	}

	/**
	 * 
	 * 判断字符串是否  不是 null 而且不为空， 如果是null或空字符  ，则抛出ServiceLogicalException<br/> <br/> 
	 * 
	 * (str != null) && (str.length() > 0);
	 * @param text ：待判断的内容
	 * @param message  : 抛出异常信息
	 */
	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text))
			AlertSLEUtil.Error(message);
	}
	
//	/**
//	 * it must not be null or empty
//	 * @param text
//	 */
//	public static void hasLength(String text) {
//		hasLength(text,"[Assertion failed] - this String argument must have length; it must not be null or empty");
//	}

	/**
	 * 
	 * 判断字符串是否  不是 null 、不为空、且不是Whitespace ，<br/> <br/> 
	 *  如果是null 或 空字符 或者全是 Whitespace (blank)  ，则抛出ServiceLogicalException  (blank) <br/> <br/> 
	 * 
	 * @param text  : 待判断的内容
	 * @param message  : 抛出异常信息
	 */
	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text))
			AlertSLEUtil.Error(message);
	}
	
	public static void hasText(Object text, String message) {
		if(text==null){
			AlertSLEUtil.Error(message);
		}
		if (!StringUtils.hasText(String.valueOf(text))){
			AlertSLEUtil.Error(message);
		}
			
	}
	public static void bigThanZero(Object text, String message) {
		if(text==null){
			AlertSLEUtil.Error(message);
		}
		try{
			if(Long.parseLong(text.toString().trim())<=0){
				AlertSLEUtil.Error(message);
			}
		}catch(NumberFormatException e){
			AlertSLEUtil.Error(message);
		}
	}
	public static void bigThan(Object text,long comparevalue, String message) {
		if(text==null){
			AlertSLEUtil.Error(message);
		}
		try{
			if(Long.parseLong(text.toString().trim())<=comparevalue){
				AlertSLEUtil.Error(message);
			}
		}catch(NumberFormatException e){
			AlertSLEUtil.Error(message);
		}
	}
	public static void main(String[] args){
		KAssert.bigThanZero("0","谁打谁看了");
	}
//	/**
//	 * it must not be null, empty, or blank
//	 * @param text
//	 */
//	public static void hasText(String text) {
//		hasText(text,"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
//	}

	/**
	 * textToSearch 不能包含substring， 如果包含则抛出ServiceLogicalException
	 * @param textToSearch  : 待判断的内容
	 * @param substring  : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void doesNotContain(String textToSearch, String substring,String message) {
		if ((StringUtils.hasLength(textToSearch))
				&& (StringUtils.hasLength(substring))
				&& (textToSearch.contains(substring))) {
			AlertSLEUtil.Error(message);
		}
	}

	
	
//	public static void doesNotContain(String textToSearch, String substring) {
//		doesNotContain(textToSearch,substring,
//				new StringBuilder()
//						.append("[Assertion failed] - this String argument must not contain the substring [")
//						.append(substring).append("]").toString());
//	}

	/**
	 * 数组array 必须不能为空，必须至少包含一个元素；否则抛出 ServiceLogicalException
	 * @param array : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array))
			AlertSLEUtil.Error(message);
	}

//	public static void notEmpty(Object[] array) {
//		notEmpty(array,"[Assertion failed] - this array must not be empty: it must contain at least 1 element");
//	}

	/**
	 * 数组array 必须为null 或者 空，否则抛出 ServiceLogicalException
	 * @param array : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void emptyArray(Object[] array, String message) {
		if (!ObjectUtils.isEmpty(array))
			AlertSLEUtil.Error(message);
	}
	
	/**
	 * 数组array 必须不能为空，且不能包含null 元素 ；否则抛出 ServiceLogicalException
	 * @param array : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null)
			for (Object element : array)
				if (element == null)
					AlertSLEUtil.Error(message);
	}

//	public static void noNullElements(Object[] array) {
//		noNullElements(array,"[Assertion failed] - this array must not contain any null elements");
//	}

	/**
	 * 集合collection必须不能为空，必须至少包含一个元素；否则抛出 ServiceLogicalException
	 * @param collection  : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection))
			AlertSLEUtil.Error(message);
	}

//	public static void notEmpty(Collection<?> collection) {
//		notEmpty(collection,"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
//	}
	
	/**
	 * 集合collection必须null或者空；否则抛出 ServiceLogicalException
	 * @param collection  : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void empty(Collection<?> collection, String message) {
		if (!CollectionUtils.isEmpty(collection))
			AlertSLEUtil.Error(message);
	}
	
	
	/**
	 * map必须不能为空，必须至少包含一个元素；否则抛出 ServiceLogicalException
	 * @param collection  : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map))
			AlertSLEUtil.Error(message);
	}
	
	/**
	 * Map必须为null 或者 空；否则抛出 ServiceLogicalException
	 * @param collection  : 待判断的内容
	 * @param message : 抛出异常信息
	 */
	public static void empty(Map<?, ?> map, String message) {
		if (!CollectionUtils.isEmpty(map))
			AlertSLEUtil.Error(message);
	}
//	public static void notEmpty(Map<?, ?> map) {
//		notEmpty(map,"[Assertion failed] - this map must not be empty; it must contain at least one entry");
//	}

	

	/**
	 * 判断个对象实例是否是一个类或接口的或其子类子接口的实例，否则抛出 ServiceLogicalException
	 * @param type :  待判断的类型
	 * @param obj :  待判断的对象
	 * @param message : 抛出异常信息
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "待判断的类型参数不能为空");
		if (!type.isInstance(obj)) {
			AlertSLEUtil.Error(
					new StringBuilder()
							.append(StringUtils.hasLength(message) ?new StringBuilder().append(message).append(" ").toString(): "")
							.append("Object of class [")
							.append(obj != null ? obj.getClass().getName(): "null")
							.append("] must be an instance of ").append(type).toString());
		}
	}
	
//	public static void isInstanceOf(Class<?> clazz, Object obj) {
//		isInstanceOf(clazz, obj, "");
//	}
	


	/**
	 * 判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口，否则抛出 ServiceLogicalException
	 * @param superType :  待判断的上级类型
	 * @param subType :  待判断的子类型
	 * @param message : 抛出异常信息
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType,String message) {
		
		notNull(superType, "待判断的类型参数不能为空");
		
		if ((subType == null) || (!superType.isAssignableFrom(subType)))
			AlertSLEUtil.Error(new StringBuilder()
					.append(message).append(subType)
					.append(" is not assignable to ").append(superType).toString());
	}

	public static void hasValidLength(int min, int max, String xzqhdm, String message) {
		if(xzqhdm==null){
			AlertSLEUtil.Error(message);
		}
		if(xzqhdm.length()<min||xzqhdm.length()>max){
			AlertSLEUtil.Error(message);
		}
	}

	public static void isNumber(String xzqhdm, String message) {
		if(xzqhdm==null){
			AlertSLEUtil.Error(message);
		}
		if(!NumberUtils.isDigits(xzqhdm)){
			AlertSLEUtil.Error(message);
		}
		
	}
	
//	public static void isAssignable(Class<?> superType, Class<?> subType) {
//		isAssignable(superType, subType, "");
//	}

}
