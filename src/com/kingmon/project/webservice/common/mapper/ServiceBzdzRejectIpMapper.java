package com.kingmon.project.webservice.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzRejectIp;

@KMapper
public interface ServiceBzdzRejectIpMapper {

	/**
     * 加载全部信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> rejectIpList(Map<String, String> params);
    
    public Long rejectIpListCount(Map<String, String> params);
    
    public ServiceBzdzRejectIp selectById(String ipid);
    
    /**
     * 添加
     * @param 
     * @return
     */
    public void addIp(Map<String, Object> params);
    
    /**
     * 修改
     * @param 
     * @return
     */
    public void saveIp(Map<String, Object> params);
    
    /**
     * 限制Ip是否有效
     * @param ipid
     */
    public void removeIpState(Map<String, Object> params);
    
    /**
     * 修改限制接口
     * @param params
     */
    public void saveXzjkRejectIp(Map<String,Object> params);
    
    /**
     * WEB 根据IP查看限制信息
     * @param ip
     * @return
     */
    public ServiceBzdzRejectIp selectBdIp(String ip);
    
    public ServiceBzdzRejectIp selectBdLongIp(Long longip);
    
//    public void updateTokenId(@Param("systemid")String systemid,@Param("tokenId")String tokenId);
}
