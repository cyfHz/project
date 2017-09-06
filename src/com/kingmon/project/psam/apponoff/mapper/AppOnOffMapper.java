package com.kingmon.project.psam.apponoff.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.apponoff.model.AppOnOff;
@KMapper
public interface AppOnOffMapper {
    int deleteByPrimaryKey(String appOnoffId);

    int insert(AppOnOff record);

    int insertSelective(AppOnOff record);

    AppOnOff selectByPrimaryKey(String appOnoffId);

    int updateByPrimaryKeySelective(AppOnOff record);

    int updateByPrimaryKey(AppOnOff record);

	AppOnOff selectStatusByAreaCode(String areaCode);
}