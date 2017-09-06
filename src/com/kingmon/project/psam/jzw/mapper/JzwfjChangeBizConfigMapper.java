package com.kingmon.project.psam.jzw.mapper;

import java.util.List;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.JzwfjChange;
import com.kingmon.project.psam.jzw.model.JzwfjChangeBizConfig;

@KMapper
public interface JzwfjChangeBizConfigMapper {


    int insertSelective(JzwfjChangeBizConfig record);

    JzwfjChangeBizConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JzwfjChangeBizConfig record);
    
    List<JzwfjChangeBizConfig> selectAll();

}