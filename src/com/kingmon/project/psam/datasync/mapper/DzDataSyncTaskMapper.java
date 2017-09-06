package com.kingmon.project.psam.datasync.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncTask;
import com.kingmon.project.psam.jzw.model.Jzwfj;
@KMapper
public interface DzDataSyncTaskMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(DzDataSyncTask record);
    
    public int batchInsertSyncTask(List<DzDataSyncTask> list);
    
    DzDataSyncTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DzDataSyncTask record);

	/**
	 * 
	 * @param bizId : DzDataSyncBiz--id
	 * @param enabled : 1:启用 ,0：禁用
	 * @param status : 状态  1：新添加 2：运行中 3：运行成功 4：运行失败
	 * @return
	 */
	public List<DzDataSyncTask> selectSyncTaskByBizId(@Param("bizId")String bizId,@Param("enabled")String enabled,@Param("status")String status);
	/**
	 * 
	 * @param configId : DzDataSyncConfig--id
	 * @param enabled : 1:启用 ,0：禁用
	 * @param status : 状态  1：新添加 2：运行中 3：运行成功 4：运行失败
	 * @return
	 */
	public List<DzDataSyncTask> selectSyncTaskByConfigId(@Param("configId")String configId, @Param("enabled")String enabled,@Param("status")String status);
	
//	public List<DzDataSyncTask> findTaskByConfigId(String configId);

}