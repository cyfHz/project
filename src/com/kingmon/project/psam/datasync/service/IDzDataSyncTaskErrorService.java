package com.kingmon.project.psam.datasync.service;


import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.datasync.model.DzDataSyncTaskError;

public interface IDzDataSyncTaskErrorService {
	
	public DzDataSyncTaskError findDzDataSyncTaskErrorById(String id);
	
	public DataSet loadDzDataSyncTaskErrorDataset(ParamObject po);
	
	public void addDzDataSyncTaskError(DzDataSyncTaskError DzDataSyncTaskError);
	
	public void deleteDzDataSyncTaskErrorById(String id);

	public   void updateDzDataSyncTaskError(DzDataSyncTaskError DzDataSyncTaskError);

}
