package com.kingmon.project.webservice.demo.impl;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.demo.HelloWorld;

public class HelloWorldImpl implements HelloWorld {
	@Resource    
	private WebServiceContext wsContext;  

	
	@Override
	public Object sayHello(String token,String name) {
		Map<String, Object> resp = new HashMap<String, Object>();
		
//		MessageContext mc = wsContext.getMessageContext();     
//    	HttpSession session = (HttpSession) ((javax.servlet.ServletContext) 
//    			mc.get(MessageContext.SERVLET_CONTEXT)).getAttribute(KConstants.WEB_SERVICE_AUTH_SESSION); 
//    	SessionUser user=(SessionUser) session.getAttribute(KConstants.SESSIONUSER);
		if(token==null||token.isEmpty()){
			resp.put("result", "0");
			resp.put("msg", "token 为空");
			resp.put("data", "");
			return resp;
		}
		AuthToken autoken =ServiceAuthUtil.getAuthToken(token);
		if(autoken==null){
			resp.put("result", "0");
			resp.put("msg", "未登录");
			resp.put("data", "Hello "+name);
			return resp;
		}
		resp.put("result", "1");
		resp.put("msg", "success");
		resp.put("data", "Hello "+name);
		return resp;
    	
	}

}
