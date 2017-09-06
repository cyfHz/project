package com.kingmon.project.psam.datasync.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncTaskError;
@KMapper
public interface DzDataSyncTaskErrorMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(DzDataSyncTaskError record);

    DzDataSyncTaskError selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DzDataSyncTaskError record);

}