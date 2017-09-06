package com.kingmon.base.util.seril;

import org.nustaq.serialization.FSTConfiguration;

public class FSTSerialUtil {
	
	static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();
	public static byte[] serialize(Object obj) {
		if(obj==null) {
	    	return null;
	    }
		return configuration.asByteArray(obj);
	}

	public static Object deserialize(byte[] sec) {
		if(sec==null) {
	    	return null;
	    }
		return configuration.asObject(sec);
	}
	
	public static void main(String[] args) {
//		byte[] byteKey = FSTSerialUtil.serialize("2582fac2-0067-40c6-9c1d-d963ddff9808");
//		System.out.println(new String(byteKey));
//		System.out.println(FSTSerialUtil.deserialize(byteKey));
		
	}
}
