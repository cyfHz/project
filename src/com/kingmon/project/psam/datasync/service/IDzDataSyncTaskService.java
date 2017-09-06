package com.kingmon.project.psam.datasync.service;

import java.util.List;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.datasync.model.DzDataSyncTask;

public interface IDzDataSyncTaskService {
	
	public DzDataSyncTask findDzDataSyncTaskById(String id);
	
	/**
	 * 
	 * @param bizId : DzDataSyncBiz--id
	 * @param enabled : 1:启用 ,0：禁用
	 * @param status : 状态  1：新添加 2：运行中 3：运行成功 4：运行失败
	 * @return
	 */
	public List<DzDataSyncTask> findSyncTaskByBizId(String bizId,String enabled,String status);
	/**
	 * 
	 * @param configId : DzDataSyncConfig--id
	 * @param enabled : 1:启用 ,0：禁用
	 * @param status : 状态  1：新添加 2：运行中 3：运行成功 4：运行失败
	 * @return
	 */
	public List<DzDataSyncTask> findSyncTaskByConfigId(String configId,String enabled,String status);
	
	
	public DataSet loadDzDataSyncTaskDataset(ParamObject po);
	
	public int addDzDataSyncTask(DzDataSyncTask dzDataSyncTask);
	
	public void deleteDzDataSyncTaskById(String id);

	public  void updateDzDataSyncTask(DzDataSyncTask DzDataSyncTask);

}
