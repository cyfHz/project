package com.kingmon.project.webservice.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
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
import com.kingmon.project.webservice.common.mapper.ServiceBzdzRejectIpMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzRejectIp;
import com.kingmon.project.webservice.common.model.ServiceBzdzUser;
import com.kingmon.project.webservice.common.service.BzdzRejectIpService;
import com.kingmon.project.webservice.common.token.AuthToken;

@Service
public class BzdzRejectIpServiceImpl extends BaseService implements BzdzRejectIpService{

	@Autowired
	private ServiceBzdzRejectIpMapper serviceBzdzRejectIpMapper;
	
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public DataSet rejectIpList(Map<String, String> params){
		PaginationUtil.initPageAndSort(params);
		String ip = params.get("ip");
		if(ip!=null && !ip.isEmpty()){
			params.put("ip", ip+"%");
		}else{
			params.remove("ip");
		}
		if("".equals(params.get("sort"))||params.get("sort")==null){
			params.put("sort", "tjsj");//默认按注册时间排序
		}
		if("".equals(params.get("order"))||params.get("order")==null){
			params.put("order", "desc");//
		}
		return new DataSet(serviceBzdzRejectIpMapper.rejectIpListCount(params), serviceBzdzRejectIpMapper.rejectIpList(params));
	}
	
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public ServiceBzdzRejectIp selectById(String ipid){
		if(!StringUtils.hasText(ipid)){
			return null;
		}
		return serviceBzdzRejectIpMapper.selectById(ipid);
	}

//	@CacheEvict()
	@Transactional(rollbackFor = Exception.class)
    public void addIp(Map<String, Object> params){
		validateUser(params);
		params.put("ipid", UUIDUtil.uuid());
		
		String ip=(String)params.get("ip");//ip地址
		String ipid=(String)params.get("ipid");//主键
		String sql="select count(1) from SERVICE_BZDZ_REJECTIP where  ip=:ip";
		long count = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
				.addSQLParam("ipid", ipid)
				.addSQLParam("ip", ip));
		if (count > 0) {
			AlertSLEUtil.Error("该IP已经存在！");
		}
		//增加人信息
		params.put("tjr", SecurityUtils.getUserName());
		params.put("tjdw", SecurityUtils.getUserOrgName());
		params.put("tjsj", new Date());
		params.put("ipxzjk", "001");//IP限制接口 001 表示全部限制
		params.put("longip", IpUtil.ipStrToLong(ip));//ip地址转化long型存储
		serviceBzdzRejectIpMapper.addIp(params);
    }
    
	@Transactional(rollbackFor = Exception.class)
    public void saveIp(Map<String, Object> params ){
		validateUser(params);

		String ip=(String)params.get("ip");//ip
		String ipid=(String)params.get("ipid");//主键
		String sql="select count(1) from SERVICE_BZDZ_REJECTIP where ipid=:ipid and ip=:ip";
		long count = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
				.addSQLParam("ipid", ipid)
				.addSQLParam("ip", ip));
		if (count > 0) {
			AlertSLEUtil.Error("该IP已经存在！");
		}
		//修改人信息
		Date date=new Date();
		params.put("xgr", SecurityUtils.getUserName());
		params.put("xgsj", date);
		params.put("xgdw", SecurityUtils.getUserOrgName());
		params.put("longip", IpUtil.ipStrToLong(ip));//ip地址转化long型存储
		serviceBzdzRejectIpMapper.saveIp(params);
    }
	
	@CacheEvict(value="defaultQueryResultCache",key="'BzdzRejectIpServiceImpl_selectBdIp'+#ip")
	@Transactional(rollbackFor = Exception.class)
    public void removeIp(String ipid,String sfyy,String ip){
		ServiceBzdzRejectIp BzdzRejectIp= serviceBzdzRejectIpMapper.selectById(ipid);
		if(BzdzRejectIp==null){
			AlertSLEUtil.Error("未找到有关数据！");
		}
		Map<String, Object> params=new HashMap<String,Object>();
		//修改人信息
		Date date=new Date();
		params.put("xgr", SecurityUtils.getUserName());
		params.put("xgsj", date);
		params.put("xgdw", SecurityUtils.getUserOrgName());
		params.put("sfyy", sfyy);//是否有效 0 有效 1 无效
		params.put("ipid", ipid);
		serviceBzdzRejectIpMapper.removeIpState(params);
		
		
    }
	
	@Transactional(rollbackFor = Exception.class)
	private void validateUser(Map<String, Object> params){
		String ip=(String)params.get("ip");//ip
		
		KAssert.hasText(ip, "IP不能为空！");
		if(!IpUtil.isIPv4Address(ip)){
			AlertSLEUtil.Error("当前输入的IP格式不对或者不合法,请查证！");
		}
//		KAssert.hasText(bzdzUser.getBdip(), "绑定ip不能为空！");
//		KAssert.hasText(bzdzUser.getUserpassword(), "用户密码不能为空！");
//		if(bzdzUser.getUsername()==null){
//			AlertSLEUtil.Error("");
//		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void saveXzjkRejectIp(Map<String,Object> params){
		String ipid=(String)params.get("ipid");
		if(ipid==null||ipid.isEmpty()){
			AlertSLEUtil.Error("当前修改限制接口信息不存在！");
		}
		//修改人信息
		Date date=new Date();
		params.put("xgr", SecurityUtils.getUserName());
		params.put("xgsj", date);
		params.put("xgdw", SecurityUtils.getUserOrgName());
		serviceBzdzRejectIpMapper.saveXzjkRejectIp(params);
	}
	
	@Transactional(rollbackFor = Exception.class)
	 public void updateIpState(String ipid){
		 if(!StringUtils.hasText(ipid)){
			 AlertSLEUtil.Error("未查到相关IP信息,请查实！");
			}
		 ServiceBzdzRejectIp bzdzUserIp= serviceBzdzRejectIpMapper.selectById(ipid);
		 Map<String, Object> params = new HashMap<String,Object>();
		 params.put("ip", bzdzUserIp.getIp());
		 params.put("ipid", ipid);
		 params.put("ipxzjk", bzdzUserIp.getIpxzjk());//
		 params.put("sfyy", "1");//1 限制有用  0 不限制无用
		 
	 }
	 
	 @Cacheable(value="defaultQueryResultCache",key="'BzdzRejectIpServiceImpl_selectBdIp'+#ip")
	 @Transactional(rollbackFor = Exception.class, readOnly = true)
	 public ServiceBzdzRejectIp selectBdIp(String ip){
		 return serviceBzdzRejectIpMapper.selectBdIp(ip);
	 }
	 
	 @Transactional(rollbackFor = Exception.class, readOnly = true)
	 public ServiceBzdzRejectIp selectBdLongIp(Long longip){
		 if(longip==null||longip<=0){
			 return null;
			}
		return serviceBzdzRejectIpMapper.selectBdLongIp(longip);
	 }
	 
}
