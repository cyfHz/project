package com.kingmon.project.psam.jzw.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.JzwfjChange;

@KMapper
public interface JzwfjChangeMapper {


    int insertSelective(JzwfjChange record);

    JzwfjChange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JzwfjChange record);

}