package com.kingmon.project.webservice.common.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.webservice.common.model.ServiceBzdzRejectIp;

public interface BzdzRejectIpService {

	/**
     * 加载全部信息
     * @param params
     * @return
     */
	DataSet rejectIpList(Map<String, String> params);
    
    ServiceBzdzRejectIp selectById(String ip);
    
    /**
     * 添加
     * @param 
     * @return
     */
    public void addIp(Map<String, Object> params );
    
    /**
     * 修改
     * @param 
     * @return
     */
    public void saveIp(Map<String, Object> params );
    
    /**
     * 
     * @param ipid
     */
    public void updateIpState(String ipid);
    
    /**
     * 限制Ip是否有效
     * @param ipid
     */
    public void removeIp(String ipid,String sfyy,String ip);
    
    /**
     * 修改限制接口
     * @param params
     */
    public void saveXzjkRejectIp(Map<String,Object> params);
    
    /**
     * WEB 通过IP查看限制信息
     * @param ip
     * @return
     */
    public ServiceBzdzRejectIp selectBdIp(String ip);
    
    /**
     * 根据LongIP 查询 信息
     * @param longip
     * @return
     */
    public ServiceBzdzRejectIp selectBdLongIp(Long longip);
    
    
}
