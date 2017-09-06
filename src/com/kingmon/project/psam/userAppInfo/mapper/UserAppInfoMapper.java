package com.kingmon.project.psam.userAppInfo.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.userAppInfo.model.UserAppInfo;
@KMapper
public interface UserAppInfoMapper {
    int insert(UserAppInfo record);

    int insertSelective(UserAppInfo record);
}