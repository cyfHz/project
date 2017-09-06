package com.kingmon.project.webservice.demo;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService  
public interface HelloWorld {
	@WebMethod
	public Object sayHello(String token,String name);  
}
