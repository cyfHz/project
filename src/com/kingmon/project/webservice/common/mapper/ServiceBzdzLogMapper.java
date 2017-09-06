package com.kingmon.project.webservice.common.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzLog;
@KMapper
public interface ServiceBzdzLogMapper {
	
    int deleteByPrimaryKey(String loginid);

    int insertSelective(ServiceBzdzLog record);

    ServiceBzdzLog selectByPrimaryKey(String loginid);

    int updateByPrimaryKeySelective(ServiceBzdzLog record);
    
    /**
     * 加载全部信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> logList(Map<String, String> params);
    
    public Long logListCount(Map<String, String> params);
}