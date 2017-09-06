package com.kingmon.project.webservice.common.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzUserjs;
@KMapper
public interface ServiceBzdzUserjsMapper {
    int deleteByPrimaryKey(String jsid);

    int insertSelective(ServiceBzdzUserjs record);

    ServiceBzdzUserjs selectByPrimaryKey(String jsid);
   
    int updateByPrimaryKeySelective(ServiceBzdzUserjs record);
   
    public void addUserjs(Map<String, Object> params);
}