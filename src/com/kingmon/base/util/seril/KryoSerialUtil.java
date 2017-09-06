/**
 * Copyright (c) 2015 https://github.com/zhaohuatai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.kingmon.base.util.seril;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
/**
 * redis 存取 序列化工具类 
 *  <br>序列化，反序列化方式采用jdk自带API ，
 *    后期意向改成thrift 或google protobuf
* @ClassName :SerializeUtil     
* @Description :   
* @createTime :2015年5月7日  上午9:28:36   
* @author ：zhaohuatai   
* @version :1.0
 */
public class KryoSerialUtil {
	
	 public final static Kryo kryo = new Kryo();
	 static {
	        kryo.setMaxDepth(4);
	    }
	/**
	 * 序列化
	 * @param value
	 * @return
	 */
    public static byte[] serialize(Object value) {
		byte[] buffer = new byte[2048];
		Output output=null;
		try{
			 output = new Output(buffer);
			kryo.writeClassAndObject(output, value);
			buffer=output.toBytes();
			return buffer;
		} catch (Exception e) {
		}finally{
			if(output!=null){
				output.close();
			}
			
		}
		return buffer;
    }

    /**
     * 反序列化
     * @param in
     * @return
     */
	public static Object deserialize(byte[] in) {
		Input input =null;
		try {
			 input = new Input(in);
			Object obj=kryo.readClassAndObject(input);
			input.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(input!=null){
				input.close();
			}
		}
		return kryo;
    }
  

}
