package com.kingmon.base.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

	private static final Pattern IPV4_PATTERN = 
    		Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
 
    private static final Pattern IPV6_STD_PATTERN = 
    		Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = 
    		Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    
    public static String getClientIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static long ipStrToLong(String ipaddress) {    
        long[] ip = new long[4];  
        int i = 0;  
        for(String ipStr : ipaddress.split("\\.")){  
            ip[i] = Long.parseLong(ipStr);  
            i++;  
        }    
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];    
    } 
    public static void main(String[] sd){
    	 System.out.println(ipStrToLong("127.0.0.1"));
		 System.out.println(iplongToIp(2130706433));
	 }
    public static String iplongToIp(long ipaddress) {    
        StringBuffer sb = new StringBuffer("");  
        sb.append(String.valueOf((ipaddress >>> 24)));  
        sb.append(".");  
        sb.append(String.valueOf((ipaddress & 0x00FFFFFF) >>> 16));  
        sb.append(".");  
        sb.append(String.valueOf((ipaddress & 0x0000FFFF) >>> 8));  
        sb.append(".");  
        sb.append(String.valueOf((ipaddress & 0x000000FF)));  
        return sb.toString();  
    }  
    public static String iplongToIp(Long ipaddr) { 
    	if(ipaddr==null){
    		return "";
    	}
        long y = ipaddr % 256; 
        long m = (ipaddr - y) / (256 * 256 * 256); 
        long n = (ipaddr - 256 * 256 *256 * m - y) / (256 * 256); 
        long x = (ipaddr - 256 * 256 *256 * m - 256 * 256 *n - y) / 256; 
        return m + "." + n + "." + x + "." + y; 
    } 
    
    
    
    /**
     * 验证ip是否合法
     * 
     * @param text
     *            ip地址
     * @return 验证信息
     */
    public static String ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return text + "\n是一个合法的IP地址！";
            } else {
                // 返回判断信息
                return text + "\n不是一个合法的IP地址！";
            }
        }
        // 返回判断信息
        return "请输入要验证的IP地址！";
    }






//    public static void main(String[] ar){
//    	String addr="0.168.1";
//    	System.out.println(isIPv4Address(addr));
//    	System.out.println(ipCheck(addr));
//    }

    
    public static boolean isIPv4Address(final String input) {
    	return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
    	return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
    	return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
    	return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input); 
    }

}
