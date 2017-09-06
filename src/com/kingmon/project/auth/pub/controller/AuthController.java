package com.kingmon.project.auth.pub.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.common.session.SessionUser;
import com.kingmon.common.session.repository.ISessionUserRepository;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.auth.pub.service.IMenuService;
import com.kingmon.project.auth.pub.views.MenuView;
import com.kingmon.project.auth.util.HttpSSOUtil;
import com.kingmon.project.common.log.service.ILogLoginService;
import com.sdwangge.policecloud.utils.MD5Util;
/**
 * 
* @ClassName :RbacUserController     
* @Description :   
* @createTime :2015年4月3日  下午4:17:50   
* @author ：zhaohuatai   
* @version :1.0
 */
@Controller
@RequestMapping("/")
public class AuthController extends KBaseController{
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IOrganizationUserService organizationUserService;
	@Autowired
	private ILogLoginService logLoginService;
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private ISessionUserRepository sessionUserRepository;
	
	
	@Value("${auth.sso.url}")
	private String  authssoUrl;
	
	@RequestMapping(value="login")
    @ResponseBody
    public Object login(HttpServletRequest request,HttpServletResponse response,Model model,
    		String username,String password) {
		password = MD5Util.EncryptMD5(password);//新网格提供数据库加密算法
    	OrganizationUser organizationUser =organizationUserService.findByLoginnameAndPws(username, password);
    	if(organizationUser==null){
    		return ajaxDoneError("用户名或密码错误");
    	}
    	if(organizationUser.getEnabled()==null||organizationUser.getEnabled().equals("0")){
    		return ajaxDoneError("该用户已禁用，请联系管理人员");
    	}
    	
    	HttpSession session = request.getSession();
    	String sessionId=session.getId();
//    	SessionUser user=organizationUserService.loadUserInfor(organizationUser.getUser_sfzh());
    	SessionUser user=organizationUserService.loadUserInfor(organizationUser.getAppuser_id());
    	if(user==null){
			String msg="用户信息未能完整加载，请联系管理员";
			setDataAttribute(model, msg, "msg");
			return "/pub/info";
		}
    	int userLevel=user.getUserLevel();
    	if(userLevel<0){
    		return ajaxDoneError("用户级别未授权");
    	}
    	user.setSessionId(sessionId);
    	session.setAttribute(KConstants.SESSIONUSER, user);
    	//添加登陆日志
        logLoginService.addLogLogin(user,request,true,null,"0",null);
    	return ajaxDoneSuccess("登录成功");
    }
    @RequestMapping(value="/psamloginCallback")
    public Object psamloginCallback(HttpServletRequest request,HttpServletResponse response,Model model,
    		String session_id) {
    	if(!StringUtils.hasText(session_id)){
			System.out.println("psamloginCallback-2");
			return "redirect:/login.jsp";
		}
		String authInfo=HttpSSOUtil.httpClientRquest(session_id,authssoUrl);
		JSONObject jsonObj=JSON.parseObject(authInfo);
		Boolean jsonObj_success=(Boolean)jsonObj.get("success");
		if(jsonObj_success==null||!jsonObj_success==true){}
		JSONObject jsonObj_msg=jsonObj.getJSONObject("msg");
		String appuser_id=(String) jsonObj_msg.get("id");
		OrganizationUser user=organizationUserService.findByUserId(appuser_id);
		if(user==null){
			String msg="用户不存在";
			setDataAttribute(model, msg, "msg");
			return "/pub/info";
		}
    	HttpSession session = request.getSession();
    	String sessionId=session.getId();
//    	
//    	SessionUser sessionUser=organizationUserService.loadUserInfor(user.getUser_sfzh());
    	SessionUser sessionUser=organizationUserService.loadUserInfor(appuser_id);
    	if(sessionUser==null){
			String msg="用户信息未能完整加载，请联系管理员";
			setDataAttribute(model, msg, "msg");
			return "/pub/info";
		}
    	//sessionUser.c(jsonObj.getString("ssjwq"));
    	//sessionUser.setSsjwqList(ssjwqList);
    	//sessionUser.setSspcs(jsonObj.getString("sspcs"));
    	//sessionUser.setSsfj(jsonObj.getString("ssfj"));
    	//sessionUser.setSssj(jsonObj.getString("sspcs"));
    	sessionUser.setSessionId(sessionId);
    	request.getSession().setAttribute(KConstants.SESSIONUSER, sessionUser);
    	//添加登陆日志
        logLoginService.addLogLogin(sessionUser,request,true,null,"0",null);
		return "redirect:/index.jsp";
    }

    
    @RequestMapping(value="/logout")
    public Object logout(HttpServletRequest request) {
    		SessionUser user=SecurityUtils.getSessionUser();
    		if(user==null){
    			return "redirect:/login.jsp";
    		}
    		String loginLogid= user.getLoginLogId();
    		request.getSession().removeAttribute(KConstants.SESSIONUSER);
	        request.getSession().invalidate();
        	//添加登陆日志
        	logLoginService.addLogLogin(null,request,false,loginLogid,"0",null);
        	return "redirect:/login.jsp";
    }

//-------------------------------------------------------------------------
	
    
	@RequestMapping(value = "/loadMenus")
	@ResponseBody
	public Object loadMenus() {
		MenuView view =menuService.loadMenu();
		return FastjsonUtil.convert(view);
	}
	@RequestMapping(value="/core")
    public Object core(HttpServletRequest request) {
		
		//load other informations
		return "index";  
    }
    

}
