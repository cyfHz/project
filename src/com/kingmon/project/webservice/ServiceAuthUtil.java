package com.kingmon.project.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpSession;
//import javax.xml.ws.WebServiceContext;
//import javax.xml.ws.handler.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;

//import com.alibaba.fastjson.JSON;
//import com.itextpdf.text.log.SysoCounter;
//import com.kingmon.base.common.KConstants;
import com.kingmon.base.dao.JdbcBaseDao;
import com.kingmon.base.util.DBTimeUtil;
//import com.kingmon.base.util.IpUtil;
import com.kingmon.base.util.SpringBeanFacUtil;
//import com.kingmon.common.session.SessionUser;
//import com.kingmon.common.session.repository.ISessionUserRepository;
import com.kingmon.project.webservice.common.model.ServiceBzdzRejectIp;
import com.kingmon.project.webservice.common.service.BzdzRejectIpService;
import com.kingmon.project.webservice.common.service.impl.BzdzRejectIpServiceImpl;
import com.kingmon.project.webservice.common.token.AuthToken;
import com.kingmon.project.webservice.common.token.repository.ITokenRepository;

public class ServiceAuthUtil {
	
//	@Autowired
//	private ISessionUserRepository sessionUserRepository;
	@Autowired
	private ITokenRepository tokenRepository;
	@Autowired
	private JdbcBaseDao jdbcBaseDao;

	public static Map<String,Object>  validateAuthToken(String token){
		Map<String,Object> resp = new HashMap<String, Object>();
		if(token==null){
			resp.put("result", "1");
			resp.put("msg", "token不合法！");
			resp.put("data", new ArrayList<Object>());
			resp.put("sumNum", "0");
			return resp;
		}	
		ITokenRepository tokenRep=SpringBeanFacUtil.getBean("redisTokenRepositoryImpl");
		AuthToken authToken=tokenRep.getAuthToken(token);
		
		if(authToken==null){
			resp.put("result", "1");
			resp.put("msg", "token不合法！");
			return resp;
		}
		if("0".equals(authToken.getSfsh())){
    		resp.put("result", "2");
			resp.put("msg", "用户未审核");
			return resp;
    	}
		if("1".equals(authToken.getSfsd())){
    		resp.put("result", "3");
			resp.put("msg", "用户已锁定");
			return resp;
    	}
    	if("1".equals(authToken.getDeltag())){
    		resp.put("result", "4");
			resp.put("msg", "用户已删除");
			return resp;
    	}
    	//step2: ipreject
		String ip=authToken.getBdip();
//		long longip=IpUtil.ipStrToLong(ip);
		BzdzRejectIpService bzdzRejectIpService=SpringBeanFacUtil.getBean(BzdzRejectIpServiceImpl.class);
//		ServiceBzdzRejectIp rejectIp = bzdzRejectIpService.selectBdLongIp(longip);
		ServiceBzdzRejectIp rejectIp = bzdzRejectIpService.selectBdIp(ip);
		if(rejectIp!=null && "0".equals(rejectIp.getSfyy())){
			resp.put("result", "5");
			resp.put("msg", "当前IP已被限制！");
			return resp;
		}
		
		//step3: 次数限制
		long lastAccessTime=authToken.getLastAccessTime();
		Date date=DBTimeUtil.getDBTime();
		long now=date.getTime();
//		System.out.println("lastAccessTime: "+lastAccessTime);
//		System.out.println("now: "+now);
		long count=1L;
		if(lastAccessTime==0L){
			authToken.setLastAccessTime(now);
			authToken.setCount(count);
			saveAuthToken(authToken);
		}else{
			long count1=authToken.getCount();
//			System.out.println("count1 "+count1);
//			System.out.println("(now-lastAccessTime)/1000D: "+(now-lastAccessTime)/1000D);
			if((now-lastAccessTime)/1000D<60){
				count1++;
				authToken.setCount(count1);
				saveAuthToken(authToken);
				if(count1>authToken.getRejectCount()){
					resp.put("result", "6");
					resp.put("msg", "请求频率超过系统规定！");
					return resp;
				}else{
					//authToken.setLastAccessTime(now);
					//authToken.setCount(0L);
					//saveAuthToken(authToken);
				}
				
			}else{
				authToken.setLastAccessTime(now);
				authToken.setCount(0L);
				saveAuthToken(authToken);
			}
		}
		resp.put("result", "0");
		resp.put("msg", "校验通过！");
	
		//update 
		
    	return resp;
	}
	
	public static AuthToken getAuthToken(String token){
		if(token==null){
			return null;
		}	
		ITokenRepository tokenRep=SpringBeanFacUtil.getBean("redisTokenRepositoryImpl");
		AuthToken authToken=tokenRep.getAuthToken(token);
    	return authToken;
	}
	
	public static void saveAuthToken(AuthToken token){
		if(token==null){
			return ;
		}	
		ITokenRepository tokenRep=SpringBeanFacUtil.getBean("redisTokenRepositoryImpl");
		tokenRep.saveAuthToken(token);
	}
	
	public static void deleteAuthToken(String token){
		if(token==null){
			return ;
		}	
		ITokenRepository tokenRep=SpringBeanFacUtil.getBean("redisTokenRepositoryImpl");
		tokenRep.deleteAuthToken(token);
	}
}
