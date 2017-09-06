package com.kingmon.project.psam.datasync.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncConfig;
@KMapper
public interface DzDataSyncConfigMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(DzDataSyncConfig record);

    DzDataSyncConfig selectByPrimaryKey(String id);
    

    public List<DzDataSyncConfig> selectByBizId(String id);
    
    int updateByPrimaryKeySelective(DzDataSyncConfig record);


	public List<DzDataSyncConfig> findConfigListByBizId(@Param("syncBizId")String syncBizId);


}