package com.kingmon.project.psam.datasync.service;

import java.util.List;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.datasync.model.DzDataSyncConfig;

public interface IDzDataSyncConfigService {
	
	public DzDataSyncConfig findDzDataSyncConfigById(String id);
	
	public List<DzDataSyncConfig> findDzDataSyncConfigByBizId(String syncBizId);
	
	public DataSet loadDzDataSyncConfigDataset(ParamObject po);
	
	public void addDzDataSyncConfig(DzDataSyncConfig DzDataSyncConfig);
	
	public void deleteDzDataSyncConfigById(String id);

	public void updateDzDataSyncConfig(DzDataSyncConfig DzDataSyncConfig);

}
