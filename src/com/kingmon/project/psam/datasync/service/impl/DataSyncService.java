package com.kingmon.project.psam.datasync.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.datasync.mapper.DzDataSyncTaskMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;
import com.kingmon.project.psam.datasync.model.DzDataSyncConfig;
import com.kingmon.project.psam.datasync.model.DzDataSyncTask;
import com.kingmon.project.psam.datasync.service.IDzDataSyncBizService;
import com.kingmon.project.psam.datasync.service.IDzDataSyncConfigService;
import com.kingmon.project.psam.datasync.service.IDzDataSyncTaskService;
import com.kingmon.project.psam.datasync.view.SyncParam;
import com.kingmon.project.psam.datasync.view.SyncParamItem;

@Service
public class DataSyncService extends  BaseService{
	
	@Autowired
	private IDzDataSyncBizService  dzDataSyncBizService;
	
	@Autowired
	private IDzDataSyncConfigService  dzDataSyncConfigService;
	
	@Autowired
	private IDzDataSyncTaskService  dzDataSyncTaskService;
	
	@Autowired
	private DzDataSyncTaskMapper  dzDataSyncTaskMapper;
	
	
	@Async()
	public void createAndExecuteSyncTask(DzDataSyncBiz dzDataSyncBiz,SyncParam syncParam){
		int createCount=createSyncTask(dzDataSyncBiz,syncParam);
		if(createCount>0){
			executeSyncTask(dzDataSyncBiz);
		}
	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	public int createSyncTask(DzDataSyncBiz dzDataSyncBiz,SyncParam syncParam){
		String syncBizId=dzDataSyncBiz.getId();
		List<DzDataSyncConfig> configlist=dzDataSyncConfigService.findDzDataSyncConfigByBizId(syncBizId);
		if(configlist==null||configlist.isEmpty()){
			return 0;
		}
		List<DzDataSyncTask> taskList=Lists.newArrayList();
		for (DzDataSyncConfig syncConfig : configlist) {
			String configId=syncConfig.getId();
			String describe = syncConfig.getDescribe();
			List<SyncParamItem> itemList=syncParam.getSyncParamItemList();
			if(itemList==null||itemList.isEmpty()){
				continue;
			}
			for (SyncParamItem item : itemList) {
				String originalValue=(String)item.getSourceValue();
				String targetValue=(String)item.getTargetValue();
				String updateWhereValue=(String) item.getUpdateWhereValue();
				String createuser=syncParam.getCraeteUserId();
				DzDataSyncTask syncTask=DzDataSyncTask.newDefaultTask(configId,originalValue, targetValue,updateWhereValue, createuser,describe);
				taskList.add(syncTask);
			}
		}
		int count = dzDataSyncTaskMapper.batchInsertSyncTask(taskList);
		return count;
	}
	
	@Transactional(rollbackFor=Exception.class,readOnly=false)
	public void executeSyncTask(DzDataSyncBiz dzDataSyncBiz){
		String syncBizId=dzDataSyncBiz.getId();
		List<DzDataSyncConfig> configlist=dzDataSyncConfigService.findDzDataSyncConfigByBizId(syncBizId);
		if(configlist==null||configlist.isEmpty()){
			return;
		}
		List<DzDataSyncTask> taskList=dzDataSyncTaskService.findSyncTaskByBizId(syncBizId, "1", "1");
		if(taskList==null||taskList.isEmpty()){
			return;
		}
		
		for(DzDataSyncTask task:taskList){
			task.getConfigId();
			DzDataSyncConfig currentConfig=findCurrentConfig(configlist,task);
			if(currentConfig==null){
				continue;
			}
//			String bizTable = currentConfig.getBizTable();
//			String bizFiled = currentConfig.getBizFiled();
			
			String refTable = currentConfig.getReferTable();
			String referFiled = currentConfig.getReferFiled();
			String referUpdateWhereField = currentConfig.getReferUpdateWhereField();
//			String isSyncSearch = currentConfig.getIsSyncSearch();
//			if ("1".equals(isSyncSearch)) {
//				searchType = syncConfig.getSearchType();
//				searchField = syncConfig.getSearchField();
//			}
			String sql=" update "+refTable+" set "+referFiled+" =:targetValue where "+referUpdateWhereField+" =:referUpdateWhereValue ";
			int res=getJdbcBaseDao().jdbcUpdate(sql, ParamObject.new_NP_NC().addSQLParam("targetValue", "").addSQLParam("referUpdateWhereValue", ""));
			
		}
	}
	
	private DzDataSyncConfig findCurrentConfig(List<DzDataSyncConfig> configlist,DzDataSyncTask task){
		if(configlist==null||configlist.isEmpty()||task==null){
			return null;
		}
		for(DzDataSyncConfig config:configlist ){
			if((""+task.getConfigId()).equals(config.getId())){
				return config;
			}
		}
		return null;
	}
	

}
