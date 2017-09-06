package com.kingmon.project.common.log.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.IpUtil;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.session.SessionUser;
import com.kingmon.common.session.repository.impl.RedisSessionUserRepository;
import com.kingmon.project.common.log.mapper.LogLoginMapper;
import com.kingmon.project.common.log.model.LogLogin;
import com.kingmon.project.common.log.service.ILogLoginService;

@Service
public class LogLoginServiceImpl extends BaseService implements ILogLoginService{
	
	@Autowired
	private LogLoginMapper loginMapper;
	
	//@Async(value="logExecutor")
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addLogLogin(SessionUser user, HttpServletRequest request,boolean islogin,String loginLogid,String loginType,String imei) {
		try {
			Date date=new Date();			
			if(islogin){//登陆
				String uuid=UUIDUtil.uuid();
		        LogLogin logLogin = new  LogLogin();
		        logLogin.setId(uuid);//登陆日志id
		        logLogin.setIp(IpUtil.getClientIpAddr(request));//ip
		        logLogin.setLogintime(date);//登陆时间
		        logLogin.setLogintime1(ZDateUtil.dateToString(date, ZDateStyle.YYYY_MM_DD_HH_MM_SS));
		        logLogin.setOrgna_id(user.getOrganizationId());//组织机构ID
		        logLogin.setUser_id(user.getUserId());
		        logLogin.setUser_name(user.getName());
		        //-------------------------------------------------------???????
		        logLogin.setGps_x(null); // #?? 设备坐标x
		        logLogin.setGps_y(null);//#?? 设备坐标y  
//		        logLogin.setLoginclient("0"); //#?? 终端类型  
		        logLogin.setLoginclient(loginType);//终端类型  --0:PC端  1：移动端
		        logLogin.setLogintype("密码登录");// #?? 登录方式 
		        logLogin.setMemo(""); //备注
		        logLogin.setSbid(imei);//#?? 设备id  -- 同imei号一致 0708 
		        logLogin.setImei(imei);//终端登录添加登录日志  IMEI号  20160708
		        logLogin.setUser_type(Short.valueOf("1")); //#?? 定义 1管理员 2民警用户 3企业用户  
				loginMapper.addLogLogin(logLogin);
				HttpSession session=request.getSession();
				//System.out.println("addLogLogin: "+session.getId());
				SessionUser sessionUser=(SessionUser) session.getAttribute(KConstants.SESSIONUSER);
				if(sessionUser!=null){
					sessionUser.setLoginLogId(uuid);
				}
				session.setAttribute(KConstants.SESSIONUSER, sessionUser);
				
			}else if(!islogin){//退出--session中获取登录的logid
				 if(StringUtils.hasText(loginLogid)){
					 LogLogin logLogin=new LogLogin();
					 logLogin.setLogouttime(new Date()); //退出时间
				     logLogin.setLogouttime1(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
				     logLogin.setId(loginLogid);
				     loginMapper.updateLogoutTime(logLogin);
				 }
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Override
	public DataSet loadLogLoginDataSet(Map<String, String> params) {
		PaginationUtil.initPageAndSort(params);
		String user_loginname = params.get("user_loginname");
		if(user_loginname!=null&&!user_loginname.isEmpty()){
			params.put("user_loginname", "%"+user_loginname+"%");
		}else{
			params.remove("user_loginname");
		}
		String start_time = params.get("start_time");
		if (start_time != null && !start_time.isEmpty()) {
			params.put("start_time",start_time);
		}else{
			params.remove("start_time");
		}
		String end_time = params.get("end_time");
		if (end_time != null && !end_time.isEmpty()) {
			params.put("end_time",  end_time);
		}else{
			params.remove("end_time");
		}
		return new DataSet(loginMapper.selectLogLoginCount(params),loginMapper.selectLogLoginList(params));
	}


	@Override
	public LogLogin findLogLoginByid(String id) {
		if(StringUtils.hasText(id)){
			return loginMapper.selectByPrimaryKey(id);
		}
		return null;
	}
}
