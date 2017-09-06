package com.kingmon.webservice;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.kingmon.project.webservice.demo.HelloWorld;

public class HelloWorldClient {
	
	 public static void main(String[] args) {  
	        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setAddress("http://localhost:8080/psam/services/helloWorldService");  
	        bean.setServiceClass(HelloWorld.class);  
	          
	        HelloWorld world = (HelloWorld) bean.create();  
	        System.out.println(world.sayHello("LCY", null));  
	 }
	 
	
}
