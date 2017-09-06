package com.kingmon.project.webservice.common.mapper;

import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.webservice.common.model.ServiceBzdzUsersd;
@KMapper
public interface ServiceBzdzUsersdMapper {
    int deleteByPrimaryKey(String sdid);

    int insertSelective(ServiceBzdzUsersd record);

    ServiceBzdzUsersd selectByPrimaryKey(String sdid);

    int updateByPrimaryKeySelective(ServiceBzdzUsersd record);

    public void addUsersd(Map<String, Object> params);
}