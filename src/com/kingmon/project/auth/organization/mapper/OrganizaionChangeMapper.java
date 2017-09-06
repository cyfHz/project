package com.kingmon.project.auth.organization.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.auth.organization.model.OrganizaionChange;
@KMapper
public interface OrganizaionChangeMapper {

    int insertSelective(OrganizaionChange record);

    OrganizaionChange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrganizaionChange record);

}