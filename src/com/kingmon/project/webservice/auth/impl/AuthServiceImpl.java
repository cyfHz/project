package com.kingmon.project.webservice.auth.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.IpUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.auth.AuthService;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzLogMapper;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzRejectIpMapper;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzUserMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzLog;
import com.kingmon.project.webservice.common.model.ServiceBzdzUser;
import com.kingmon.project.webservice.common.token.AuthToken;
public class AuthServiceImpl implements AuthService{
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IOrganizationUserService organizationUserService;
	
	@Autowired
	private ServiceBzdzUserMapper serviceBzdzUserMapper;
	@Autowired
	private ServiceBzdzRejectIpMapper serviceBzdzRejectIpMapper;
	@Autowired
	ServiceBzdzLogMapper serviceBzdzLogMapper;
	 @Resource    
	 private WebServiceContext wsContext;  
    public Object login(String loginName,String password) {
    	Map<String, Object> resp = new HashMap<String, Object>();
    	MessageContext mc = wsContext.getMessageContext();     
		if(!StringUtils.hasText(loginName)||!StringUtils.hasText(password)){
			resp.put("result", "1");
			resp.put("msg", "用户名或密码为空");
			resp.put("token", "");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
			//return false;
		}
		ServiceBzdzUser user=serviceBzdzUserMapper.selectByNameAndPwd(loginName, password);
    	if(user==null){
    		resp.put("result", "2");
			resp.put("msg", "用户名或密码错误");
			resp.put("token", "");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
    		//return false;
    	}
    	HttpServletRequest request=(javax.servlet.http.HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
    	String ip=IpUtil.getClientIpAddr(request);
    	if(!(""+ip).equalsIgnoreCase(user.getBdip())){
    		resp.put("result", "3");
			resp.put("msg", "未绑定IP");
			resp.put("token", "");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
    	}
    	if("0".equals(user.getSfsh())){
    		resp.put("result", "4");
			resp.put("msg", "用户未审核");
			resp.put("token", "");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
    	}
    	if("1".equals(user.getSfsd())){
    		resp.put("result", "5");
			resp.put("msg", "用户已锁定");
			resp.put("token", "");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
    	}
    	if("1".equals(user.getDeltag())){
    		resp.put("result", "6");
			resp.put("msg", "用户已删除");
			resp.put("token", "");
			return JSON.toJSONString(resp,KConstants.serializerFeatures);
    	}
    	
    	String tokenId=UUIDUtil.uuid();
//    	String tokenId=user.getSystemid();
    	AuthToken autoken=new AuthToken(
    			tokenId, 
    			user.getSystemid(),
    			user.getUsername(), 
    			user.getSfsh(),
    			user.getBdip(),
    			user.getSfsd(), 
    			user.getDeltag());
    	autoken.setRejectCount(user.getRejectCount()==null?Long.MAX_VALUE:user.getRejectCount());
    	ServiceAuthUtil.saveAuthToken(autoken);
    	resp.put("result", "0");
		resp.put("msg", "认证成功");
		resp.put("token", tokenId);
		
		ServiceBzdzLog log=new ServiceBzdzLog(
				 UUIDUtil.uuid(),
				 autoken.getUsername(), 
				 autoken.getBdip(),
				 new Date(0),  
				 autoken.getSystemid());
		serviceBzdzUserMapper.updateTokenId(user.getSystemid(),tokenId);//保存tokenId
		serviceBzdzLogMapper.insertSelective(log);
		
		return JSON.toJSONString(resp,KConstants.serializerFeatures);
    }

}
