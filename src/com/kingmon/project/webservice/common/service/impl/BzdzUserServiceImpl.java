package com.kingmon.project.webservice.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.IpUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.webservice.ServiceAuthUtil;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzUserMapper;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzUserjsMapper;
import com.kingmon.project.webservice.common.mapper.ServiceBzdzUsersdMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzUser;
import com.kingmon.project.webservice.common.service.BzdzUserService;
import com.kingmon.project.webservice.common.token.AuthToken;

@Service
public class BzdzUserServiceImpl extends BaseService implements BzdzUserService {

	@Autowired
	private ServiceBzdzUserMapper serviceBzdzUserMapper;
	@Autowired
	private ServiceBzdzUserjsMapper serviceBzdzUserjsMapper;
	@Autowired
	private ServiceBzdzUsersdMapper serviceBzdzUsersdMapper;

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public DataSet bzdzuserList(Map<String, String> params) {
		PaginationUtil.initPageAndSort(params);
		String username = params.get("username");
		if(username!=null && !username.isEmpty()){
			params.put("username", "%"+username+"%");
		}else{
			params.remove("username");
		}
		if("".equals(params.get("sort"))||params.get("sort")==null){
			params.put("sort", "registertime");//默认按注册时间排序
		}
		if("".equals(params.get("order"))||params.get("order")==null){
			params.put("order", "desc");//
		}
		return new DataSet(serviceBzdzUserMapper.userListCount(params), serviceBzdzUserMapper.userList(params));
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public ServiceBzdzUser selectByPrimaryKey(String systemid){
		if(!StringUtils.hasText(systemid)){
			return null;
		}
		return serviceBzdzUserMapper.selectByPrimaryKey(systemid);
	}

	/**
	 * 添加
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addBzdzUser(ServiceBzdzUser bzdzUser){
		validateUser(bzdzUser);
		Date date=new Date();
		bzdzUser.setSystemid(UUIDUtil.uuid());
		//登记信息
		bzdzUser.setDjsj(date);
//		bzdzUser.setDjr(SecurityUtils.getSessionUser().getUserId());
//		bzdzUser.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());
		bzdzUser.setDjrmc(SecurityUtils.getUserName());
		bzdzUser.setDjdwmc(SecurityUtils.getUserOrgName());
		
		bzdzUser.setSfsh("0");//是否审核 0未审核，1已审核
		bzdzUser.setSfsd("0");//是否锁定 0 未锁定 1 已锁定
		serviceBzdzUserMapper.insertSelective(bzdzUser);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateBzdzUser(ServiceBzdzUser bzdzUser){
		validateUser(bzdzUser);
		Date a =bzdzUser.getRegistertime();
		bzdzUser.setRegistertime(a);
		//修改信息
		bzdzUser.setGxsj(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD_HH_MM_SS));
		bzdzUser.setXgr(SecurityUtils.getSessionUser().getUserId());
		bzdzUser.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		bzdzUser.setXgrmc(SecurityUtils.getUserName());
		bzdzUser.setXgdwmc(SecurityUtils.getUserOrgName());
		serviceBzdzUserMapper.updateByPrimaryKeySelective(bzdzUser);
		
		deleteTokenId(bzdzUser.getSystemid());
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteBzdzUser(String systemid){		
		if(systemid.isEmpty()){
			AlertSLEUtil.Error("未找到相关项！");
		}
		ServiceBzdzUser bzdzUser=new ServiceBzdzUser();
		bzdzUser.setSystemid(systemid);
		bzdzUser.setDeltime(new Date());
		bzdzUser.setDeluser(SecurityUtils.getUserName());
		bzdzUser.setDeltag("1");//删除标记 0 正常 1 已删除
		serviceBzdzUserMapper.delBzdzUser(bzdzUser);
		
		deleteTokenId(systemid);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void reviewUser(String systemid,String spzt){
		if(systemid.isEmpty()){
			AlertSLEUtil.Error("未找到相关项！");
		}
		if(spzt==null||spzt.isEmpty()){
			spzt="1";
		}
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("systemid", systemid);
		params.put("sfsh", spzt);
		params.put("shr", SecurityUtils.getUserName());
		params.put("shtime", new Date());
		serviceBzdzUserMapper.reviewUser(params);
		
		deleteTokenId(systemid);
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void validateUser(ServiceBzdzUser bzdzUser){
		KAssert.hasText(bzdzUser.getUsername(), "用户名不能为空！");
		KAssert.hasText(bzdzUser.getBdip(), "绑定ip不能为空！");
		KAssert.hasText(bzdzUser.getUserpassword(), "用户密码不能为空！");
		if(!IpUtil.isIPv4Address(bzdzUser.getBdip())){
			AlertSLEUtil.Error("当前输入的IP格式不对或者不合法,请查证！");
		}
		List<ServiceBzdzUser> user=serviceBzdzUserMapper.selectByUserNameAndIp(bzdzUser.getBdip(),bzdzUser.getUsername());
		if(user!=null || user.size()>0){
			AlertSLEUtil.Error("该用户名和IP已经绑定");
		}
		
//		if(bzdzUser.getUsername()==null){
//			AlertSLEUtil.Error("");
//		}
	}
	
	/**
	 * 解锁
	 * @param params
	 */
	@Transactional(rollbackFor = Exception.class)
	public void unlockedUser(Map<String, Object> params){
		String systemid =(String) params.get("systemid");
		if(systemid.isEmpty()){
			AlertSLEUtil.Error("未找到相关项！");
		}
		params.put("sfsd", 0);
		serviceBzdzUserMapper.unlockedUser(params);
		
		//添加解锁原因，时间信息
		ServiceBzdzUser bzdzUser=serviceBzdzUserMapper.selectByPrimaryKey(systemid);
		params.put("jsr", SecurityUtils.getUserName());
		params.put("jssj", new Date());
		params.put("jsuserid", bzdzUser.getSystemid());
		params.put("jsid", UUIDUtil.uuid());
		serviceBzdzUserjsMapper.addUserjs(params);
		
		deleteTokenId(systemid);
	}
	
	/**
	 * 锁定
	 * @param params
	 */
	@Transactional(rollbackFor = Exception.class)
	public void lockedUser(Map<String, Object> params){
		String systemid =(String) params.get("systemid");
		if(systemid.isEmpty()){
			AlertSLEUtil.Error("未找到相关项！");
		}
		params.put("sfsd", 1);
		params.put("sdr", SecurityUtils.getUserName());
		params.put("sdtime", new Date());
		serviceBzdzUserMapper.lockedUser(params);
		
		//添加锁定原因，时间信息
		ServiceBzdzUser bzdzUser=serviceBzdzUserMapper.selectByPrimaryKey(systemid);
		params.put("sdr", SecurityUtils.getUserName());
		params.put("sdsj", new Date());
		params.put("sduser", bzdzUser.getSystemid());
		params.put("sdid", UUIDUtil.uuid());
		serviceBzdzUsersdMapper.addUsersd(params);
		
		deleteTokenId(systemid);
	}
	
	
	/**
	 * 修改,审核,解锁,锁定时  更新 token
	 * @param systemid
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteTokenId(String systemid){
		ServiceBzdzUser user=serviceBzdzUserMapper.selectByPrimaryKey(systemid);
		String tokenId=user.getTokenId();
		if(tokenId==null||tokenId.isEmpty()){
			return ;
		}
		AuthToken autoken=ServiceAuthUtil.getAuthToken(tokenId);
		if(autoken==null){
			return ;
		}
		autoken.setSfsd(user.getSfsd());//是否锁定
		autoken.setSfsh(user.getSfsh());//是否审核
		autoken.setDeltag(user.getDeltag());//删除标记
		autoken.setRejectCount(user.getRejectCount());//每分钟最大请求次数
		
		ServiceAuthUtil.saveAuthToken(autoken);
//		ServiceAuthUtil.deleteAuthToken(tokenId);
	}
}
