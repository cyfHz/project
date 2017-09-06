package com.kingmon.base.util;
import org.apache.commons.codec.binary.Base64;

import java.util.UUID;
import java.nio.ByteBuffer;
public class UUIDUtil {
	
	public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
	public static String uuid32() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
	
    public static String base64Uuid() {
        UUID uuid = UUID.randomUUID();
        return base64Uuid(uuid);
    }
 
    protected static String base64Uuid(UUID uuid) {
 
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
 
        return Base64.encodeBase64URLSafeString(bb.array());
    }
 
    public static String encodeBase64Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base64Uuid(uuid);
    }
 
    public static String decodeBase64Uuid(String compressedUuid) {
 
        byte[] byUuid = Base64.decodeBase64(compressedUuid);
 
        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }
 
 
 
 
    
	public static void main(String[] args) {
		System.out.println("---------------------------");
//		for(int i=0;i<100;i++){
//			String s=UUIDUtil.base64Uuid();
//			System.out.println(s);
//			System.out.println(s.length());
//		}
		System.out.println(UUIDUtil.uuid());
	}
}
