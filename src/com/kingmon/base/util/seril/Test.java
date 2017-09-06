//package com.kingmon.base.util.seril;
//
//public class Test {
//	 private static final String redis_shiro_cache = "rc";
//	public static void main(String[] args) {
////		byte[] byteKey = JdkSerialUtil.serialize("1e7acc9a-3b03-42b3-87e7");
////		byte[] byteKey = ProtstuffSerialUtil.serialize(new U("1e7acc9a-3b03-42b3-87e7"));
//		byte[] byteKey = ProtstuffSerialUtil.serialize("1e7acc9a-3b03-42b3-87e7");
//		System.out.println(new String(byteKey));
//		System.out.println(ProtstuffSerialUtil.deserialize(byteKey,Object.class));
//	}
//	
//	
//	static void test_2(){
////		byte[] asda="1e7acc9a-3b03-42b3-87e7".getBytes();
////		byte[] sdsa=ProtstuffSerialUtil.serialize(new U("1e7acc9a-3b03-42b3-87e7"));
////		byte[] sdsaA=ProtstuffSerialUtil.serialize("1e7acc9a-3b03-42b3-87e7");
////		byte[] sdsaB=JdkSerialUtil.serialize("1e7acc9a-3b03-42b3-87e7");
////		System.out.println(asda.length+" "+sdsa.length+" "+sdsaA.length+" "+sdsaB.length);
//
//	}
//	  private static String buildCacheKey(Object key) {
//	        return redis_shiro_cache +":"+ "sac" + ":" + key;
//	  }
//	  static class U{
//			String s;
//
//			public U(String s) {
//				super();
//				this.s = s;
//			}
//			
//		}
//
//}
