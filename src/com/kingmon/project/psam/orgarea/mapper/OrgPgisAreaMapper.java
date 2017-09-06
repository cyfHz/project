package com.kingmon.project.psam.orgarea.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.orgarea.model.OrgPgisArea;

@KMapper
public interface OrgPgisAreaMapper {
    int deleteByPrimaryKey(String orgnaId);

    int insertSelective(OrgPgisArea record);

    OrgPgisArea selectByPrimaryKey(String orgnaId);

    int updateByPrimaryKeySelective(OrgPgisArea record);

}