package com.kingmon.base.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastjsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	 
    public static final Charset UTF8 = Charset.forName("UTF-8");
     
    private SerializerFeature[] features;  
    private Charset  charset  = UTF8;
    
    
    public SerializerFeature[] getFeatures() {
		return features;
	}

	public void setFeatures(SerializerFeature[] features) {
		this.features = features;
	}

	public FastjsonHttpMessageConverter() {  
//        super(new MediaType("application", "json", UTF8));  
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
    }  
   
    @Override 
    public boolean canRead(Class<?> clazz, MediaType mediaType) {  
        return true;  
    }  
   
    @Override 
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {  
        return true;  
    }  
   
    @Override 
    protected boolean supports(Class<?> clazz) {  
        throw new UnsupportedOperationException();
    }
     
    /**
     * 用完之后关闭 Stream？？？
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//    	 ByteArrayOutputStream baos = new ByteArrayOutputStream();   
//         int i;   
//         while ((i = inputMessage.getBody().read()) != -1) {   
//             baos.write(i);
//         }   
//         return JSON.parseArray(baos.toString(), clazz);
         
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         InputStream in = inputMessage.getBody();
         byte[] buf = new byte[1024];
         for (;;) {
             int len = in.read(buf);
             if (len == -1) {
                 break;
             }

             if (len > 0) {
                 baos.write(buf, 0, len);
             }
         }
         byte[] bytes = baos.toByteArray();
         try{
        	 baos.close();
        	 in.close();
         }catch(Exception e){
        	 
         }
         return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
    }
     
    @Override 
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {  
    	 //JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    	String jsonString = JSON.toJSONString(obj, features); 
        OutputStream out = outputMessage.getBody();  
        out.write(jsonString.getBytes(charset));  
        out.flush();
        try{
        	out.close();
        }catch(Exception e){
       	 
        }
    }  
}
