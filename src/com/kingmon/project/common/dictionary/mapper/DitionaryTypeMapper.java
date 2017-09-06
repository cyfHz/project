package com.kingmon.project.common.dictionary.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.common.dictionary.model.DitionaryType;
@KMapper
public interface DitionaryTypeMapper {
	
    int deleteByPrimaryKey(String TYPE_ID);

    int insert(DitionaryType record);

    int insertSelective(DitionaryType record);

    DitionaryType selectByPrimaryKey(String TYPE_ID);

    int updateByPrimaryKeySelective(DitionaryType record);

    int updateByPrimaryKey(DitionaryType record);
}