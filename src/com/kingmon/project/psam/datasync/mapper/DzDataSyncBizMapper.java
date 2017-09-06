package com.kingmon.project.psam.datasync.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;
@KMapper
public interface DzDataSyncBizMapper {
	
    int deleteByPrimaryKey(String id);

    int insertSelective(DzDataSyncBiz record);

    DzDataSyncBiz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DzDataSyncBiz record);

}