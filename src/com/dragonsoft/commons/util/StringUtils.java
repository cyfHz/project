package com.dragonsoft.commons.util;

import java.text.DecimalFormat;

public class StringUtils
{
  public static int getInt(String intStr, int intDef)
  {
    if ((null == intStr) || ("".equals(intStr.trim()))) {
      return intDef;
    }
    int intRetu = intDef;
    
    Double db = new Double(intStr);
    intRetu = db.intValue();
    return intRetu;
  }
  
  public static int getInt(String intStr)
  {
    return getInt(intStr, 0);
  }
  
  public static double getDouble(String dbstr, double dbDef)
  {
    if ((null == dbstr) || ("".equals(dbstr.trim()))) {
      return dbDef;
    }
    double dbRetu = dbDef;
    Double db = new Double(dbstr);
    dbRetu = db.doubleValue();
    return dbRetu;
  }
  
  public static double getDouble(String dbstr)
  {
    return getDouble(dbstr, 0.0D);
  }
  
  public static long getLong(String longstr, long longDef)
  {
    if ((null == longstr) || ("".equals(longstr.trim()))) {
      return longDef;
    }
    long longRetu = longDef;
    
    Double db = new Double(longstr);
    longRetu = db.longValue();
    
    return longRetu;
  }
  
  public static long getLong(String longstr)
  {
    return getLong(longstr, 0L);
  }
  
  public static boolean getBoolean(String booleanstr, boolean booleanDef)
  {
    if (null == booleanstr) {
      return booleanDef;
    }
    boolean booleanRetu = booleanDef;
    if ("true".equalsIgnoreCase(booleanstr.trim())) {
      booleanRetu = true;
    }
    return booleanRetu;
  }
  
  public static boolean getBoolean(String booleanstr)
  {
    return getBoolean(booleanstr, false);
  }
  
  public static String getNumFormat(double db, String fmt)
  {
    String retu = "";
    if ((null == fmt) || ("".equals(fmt.trim()))) {
      return Double.toString(db);
    }
    try
    {
      DecimalFormat decimalformat = new DecimalFormat(fmt);
      retu = decimalformat.format(db);
      decimalformat = null;
    }
    catch (IllegalArgumentException iaex)
    {
      retu = Double.toString(db);
    }
    return retu;
  }
  
  public static String getNumFormat(double db)
  {
    return getNumFormat(db, "0.00");
  }
  
  public static String getNumFormat(String numStr, String fmt)
  {
    double db = getDouble(numStr, 0.0D);
    return getNumFormat(db, fmt);
  }
  
  public static String getNumFormat(String numStr)
  {
    return getNumFormat(numStr, "0.00");
  }
  
  public static String htmlEncode(String str)
  {
    String retu = null;
    if ((null == str) || ("".equals(str.trim())))
    {
      retu = str;
    }
    else
    {
      String html = str;
      StringUtils tool = new StringUtils();
      html = replaceString(html, "&", "&amp;");
      html = replaceString(html, "<", "&lt;");
      html = replaceString(html, ">", "&gt;");
      html = replaceString(html, "\r\n", "\n");
      html = replaceString(html, "\n", "<br>");
      html = replaceString(html, "\t", "    ");
      html = replaceString(html, " ", "&nbsp;");
      html = replaceString(html, "\"", "&quot;");
      retu = html;
      html = null;
    }
    return retu;
  }
  
  public static String replaceString(String sourceStr, String oldStr, String newStr, boolean isIgnoreCase)
  {
    if ((sourceStr == null) || (oldStr == null) || (oldStr.equals(""))) {
      return null;
    }
    String str_RetStr = sourceStr;
    int pos = str_RetStr.indexOf(oldStr);
    while (pos != -1)
    {
      str_RetStr = str_RetStr.substring(0, pos) + newStr + str_RetStr.substring(pos + oldStr.length());
      
      pos = str_RetStr.indexOf(oldStr, pos + newStr.length());
    }
    return str_RetStr;
  }
  
  public static String replaceString(String sourceStr, String oldStr, String newStr)
  {
    return replaceString(sourceStr, oldStr, newStr, false);
  }
  
  public static String[] splitString(String sourceStr, String splitStr, boolean isTrim)
  {
    if ((sourceStr == null) || (splitStr == null)) {
      return null;
    }
    if (isTrim)
    {
      while (sourceStr.indexOf(splitStr) == 0) {
        sourceStr = sourceStr.substring(splitStr.length());
      }
      while (sourceStr.indexOf(splitStr, sourceStr.length() - splitStr.length()) > -1) {
        sourceStr = sourceStr.substring(0, sourceStr.length() - splitStr.length());
      }
    }
    if ((sourceStr.equals("")) || (splitStr.equals(""))) {
      return null;
    }
    return splitString(sourceStr, splitStr);
  }
  
  public static String[] splitString(String sourceStr, String splitStr)
  {
    if ((sourceStr == null) || (splitStr == null)) {
      return null;
    }
    if ((sourceStr.equals("")) || (splitStr.equals(""))) {
      return null;
    }
    int int_ArraySize = subStringCount(sourceStr, splitStr);
    if (int_ArraySize == -1) {
      return null;
    }
    sourceStr = sourceStr + splitStr;
    
    String[] str_RetArr = new String[int_ArraySize + 1];
    int int_pos = sourceStr.indexOf(splitStr);
    int int_ArrayPos = 0;
    while (int_pos != -1)
    {
      str_RetArr[(int_ArrayPos++)] = sourceStr.substring(0, int_pos);
      sourceStr = sourceStr.substring(int_pos + splitStr.length());
      int_pos = sourceStr.indexOf(splitStr);
    }
    return str_RetArr;
  }
  
  public static int subStringCount(String sourceStr, String subStr)
  {
    if ((sourceStr == null) || (subStr == null)) {
      return -1;
    }
    if ((sourceStr.equals("")) || (subStr.equals(""))) {
      return -1;
    }
    int result = 0;
    int int_pos = sourceStr.toUpperCase().indexOf(subStr.toUpperCase());
    while (int_pos != -1)
    {
      result++;
      int_pos = sourceStr.toUpperCase().indexOf(subStr.toUpperCase(), int_pos + subStr.length());
    }
    return result;
  }
  
  public static String arrayToString(String[] array, String splitStr)
  {
    if ((null == array) || (0 == array.length)) {
      return "";
    }
    if (null == splitStr) {
      splitStr = "";
    }
    StringBuffer strBuf = new StringBuffer("");
    int Len = array.length;
    for (int i = 0; i < Len - 1; i++) {
      strBuf.append(null == array[i] ? "" : array[i]).append(splitStr);
    }
    strBuf.append(null == array[(Len - 1)] ? "" : array[(Len - 1)]);
    
    return strBuf.toString();
  }
  
  public static String arrayToString(String[] array)
  {
    return arrayToString(array, "|");
  }
  
  public static boolean isNullBlank(String str)
  {
    return (null == str) || ("".equals(str.trim()));
  }
  
  public static String decomposeString(String sourceStr, String splitStr, int pos)
  {
    String retu = "";
    if ((null == sourceStr) || ("".equals(sourceStr.trim()))) {
      return "";
    }
    if (pos <= 0) {
      return "";
    }
    if ((null == splitStr) || ("".equals(splitStr))) {
      return sourceStr;
    }
    sourceStr = sourceStr + splitStr;
    String tmpStr = sourceStr;
    
    int splitLen = splitStr.length();
    int tmpLen = tmpStr.length();
    for (int i = 0; i < pos - 1; i++)
    {
      int tmpPosi = tmpStr.indexOf(splitStr);
      if ((tmpPosi < 0) || (tmpPosi + splitLen >= tmpLen))
      {
        tmpStr = splitStr;
        break;
      }
      tmpStr = tmpStr.substring(tmpPosi + splitLen);
    }
    retu = tmpStr.substring(0, tmpStr.indexOf(splitStr));
    return retu;
  }
  
  public static String decomposeString(String sourceStr, int pos)
  {
    return decomposeString(sourceStr, "|", pos);
  }
  
  public static String trim(String sourceStr, char removeChar)
  {
    if (sourceStr == null) {
      return null;
    }
    sourceStr = sourceStr.trim();
    int loInt_begin = 0;int loInt_end = 0;
    int loInt_len = sourceStr.length();
    for (int i = 0; i < loInt_len; i++)
    {
      if (sourceStr.charAt(i) != removeChar) {
        break;
      }
      loInt_begin++;
    }
    for (int i = 0; i < loInt_len; i++)
    {
      if (sourceStr.charAt(loInt_len - 1 - i) != removeChar) {
        break;
      }
      loInt_end++;
    }
    return sourceStr.substring(loInt_begin, loInt_len - loInt_end);
  }
  
  public static String substring(String sourceStr, int len, String appendStr)
  {
    if ((null == sourceStr) || ("".equals(sourceStr))) {
      return sourceStr;
    }
    if (len <= 0) {
      return "";
    }
    if (null == appendStr) {
      appendStr = "";
    }
    int sourceLen = sourceStr.length();
    if (len >= sourceLen) {
      return sourceStr;
    }
    return sourceStr.substring(0, len) + appendStr;
  }
  
  public static String random(int length)
  {
    String retu = "";
    
    char[] letters = _$4328();
    int[] numbers = _$4330();
    for (int i = 0; i < length; i++)
    {
      int d1 = (int)(Math.random() * 10.0D) % 2;
      if (d1 == 0)
      {
        int d2 = (int)(Math.random() * 100.0D) % 52;
        retu = retu + letters[d2];
      }
      else if (d1 == 1)
      {
        retu = retu + (int)(Math.random() * 10.0D);
      }
    }
    return retu;
  }
  
  public static String randomString(int length)
  {
    String retu = "";
    
    char[] letters = _$4328();
    for (int i = 0; i < length; i++)
    {
      int d2 = (int)(Math.random() * 100.0D) % 52;
      retu = retu + letters[d2];
    }
    return retu;
  }
  
  public static String randomNumber(int length)
  {
    String retu = "";
    int[] numbers = _$4330();
    for (int i = 0; i < length; i++) {
      retu = retu + (int)(Math.random() * 10.0D);
    }
    return retu;
  }
  
  private static char[] _$4328()
  {
    char[] ca = new char[52];
    for (int i = 0; i < 26; i++) {
      ca[i] = ((char)(65 + i));
    }
    for (int i = 26; i < 52; i++) {
      ca[i] = ((char)(71 + i));
    }
    return ca;
  }
  
  private static int[] _$4330()
  {
    int[] na = new int[10];
    for (int i = 0; i < 10; i++) {
      na[i] = i;
    }
    return na;
  }
  
  public static String encrypt(String source, boolean flag)
  {
    if ((null == source) || ("".equals(source.trim()))) {
      return source;
    }
    String LS_KEY1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz~!@#$%^&*(),.;[]{}";
    String LS_KEY2 = "&*()AD;[BFLCGH]{PQ}EI!JKRYZ1MSTXNO23UV,.Wcd45ef6lm7g@h0a#$nij8ob%^ptvz9ku~qrswxy";
    
    char[] ch_source = source.toCharArray();
    
    StringBuffer strBuf = new StringBuffer(ch_source.length);
    

    int i = 0;
    for (int Len = ch_source.length; i < Len; i++) {
      if (flag)
      {
        int pos = LS_KEY1.indexOf(ch_source[i]);
        if (pos >= 0) {
          strBuf.append(LS_KEY2.substring(pos, pos + 1));
        } else {
          strBuf.append(ch_source[i]);
        }
      }
      else
      {
        int pos = LS_KEY2.indexOf(ch_source[i]);
        if (pos >= 0) {
          strBuf.append(LS_KEY1.substring(pos, pos + 1));
        } else {
          strBuf.append(ch_source[i]);
        }
      }
    }
    return strBuf.toString();
  }
  
  public static String toVisualString(String sourceStr)
  {
    if ((sourceStr == null) || (sourceStr.equals(""))) {
      return "";
    }
    return sourceStr;
  }
  
  public static String pad(String sourceStr, int length, char withChar, boolean isPadToEnd)
  {
    if (sourceStr == null) {
      return null;
    }
    if (sourceStr.length() >= length) {
      return sourceStr;
    }
    StringBuffer sb = new StringBuffer(sourceStr);
    for (int i = 0; i < length - sourceStr.length(); i++) {
      if (isPadToEnd) {
        sb.append(withChar);
      } else {
        sb.insert(0, withChar);
      }
    }
    return sb.toString();
  }
  
  public static void main(String[] args)
  {
    String[] ss = splitString("1000000360,", ",", true);
    for (int i = 0; i < ss.length; i++) {
      System.out.println("ss" + ss[i]);
    }
  }
}
