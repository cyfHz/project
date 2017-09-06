package com.dragonsoft.commons.util;

public class TransactionMonth
{
  private String _$6974;
  private String _$6975;
  
  public TransactionMonth(String transMonth, String fromFormater, String toFormater)
  {
    this._$6974 = _$6979(transMonth, fromFormater);
    this._$6975 = _$6979(transMonth, toFormater);
    this._$6974 = (_$6979(transMonth, fromFormater) + "000000");
    this._$6975 = (_$6979(transMonth, toFormater) + "999999");
  }
  
  public TransactionMonth(String fromFormater, String toFormater)
  {
    this(DateUtils.getYearMonthStr(1), fromFormater, toFormater);
  }
  
  private static String _$6979(String month, String monthRangeFormater)
  {
    if ((month == null) || (monthRangeFormater == null)) {
      throw new IllegalArgumentException("月份参数不能这空");
    }
    if (month.length() != 6) {
      throw new IllegalArgumentException("月份字符串必须是6位，格式为yyyymm");
    }
    if (!_$6982(monthRangeFormater)) {
      throw new IllegalArgumentException("月份时间段格式定义字串格式不正确，正确的格式必须为[0|-1|1]:XX,其中xx为01~31之间的数");
    }
    int detal = Integer.parseInt(monthRangeFormater.substring(0, monthRangeFormater.indexOf(":")));
    
    String monthDate = monthRangeFormater.substring(monthRangeFormater.indexOf(":") + 1);
    

    return DateUtils.add(month, 1, detal, 2) + monthDate;
  }
  
  private static boolean _$6982(String monthRangeFormater)
  {
    if (monthRangeFormater == null) {
      return false;
    }
    if (monthRangeFormater.length() < 4) {
      return false;
    }
    int monthDetal = 0;
    try
    {
      monthDetal = Integer.parseInt(monthRangeFormater.substring(0, monthRangeFormater.indexOf(":")));
    }
    catch (NumberFormatException ex)
    {
      return false;
    }
    if ((monthDetal != -1) && (monthDetal != 0) && (monthDetal != 1)) {
      return false;
    }
    String strDate = monthRangeFormater.substring(monthRangeFormater.indexOf(":") + 1);
    try
    {
      int intDate = Integer.parseInt(strDate);
      if ((intDate < 1) || (intDate > 31)) {
        return false;
      }
    }
    catch (NumberFormatException ex1)
    {
      return false;
    }
    return true;
  }
  
  public String getFromDateStr()
  {
    return this._$6974;
  }
  
  public String getToDateStr()
  {
    return this._$6975;
  }
}
