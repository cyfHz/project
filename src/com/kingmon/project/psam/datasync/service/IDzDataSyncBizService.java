package com.kingmon.project.psam.datasync.service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;

public interface IDzDataSyncBizService {
	
	public DzDataSyncBiz findDzDataSyncBizById(String id);
	
	public DataSet loadDzDataSyncBizDataset(ParamObject po);
	
	public void addDzDataSyncBiz(DzDataSyncBiz dzDataSyncBiz);
	
	public void deleteDzDataSyncBizById(String id);
	
	public void updateDzDataSyncBiz(DzDataSyncBiz dzDataSyncBiz);

}
