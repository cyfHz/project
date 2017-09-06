package com.kingmon.project.psam.jwq.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jwq.model.JwqChange;
@KMapper
public interface JwqChangeMapper {


    int insertSelective(JwqChange record);

    JwqChange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JwqChange record);

}