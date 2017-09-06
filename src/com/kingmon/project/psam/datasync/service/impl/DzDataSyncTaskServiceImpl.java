package com.kingmon.project.psam.datasync.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.psam.datasync.mapper.DzDataSyncTaskMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncTask;
import com.kingmon.project.psam.datasync.service.IDzDataSyncTaskService;

@Service
public class DzDataSyncTaskServiceImpl  extends BaseService implements IDzDataSyncTaskService{
	@Autowired
	private DzDataSyncTaskMapper dzDataSyncTaskMapper;
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DzDataSyncTask findDzDataSyncTaskById(String id) {
		
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要操作的数据");
		}
		DzDataSyncTask sysTask=dzDataSyncTaskMapper.selectByPrimaryKey(id);
		return sysTask;
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadDzDataSyncTaskDataset(ParamObject po) {
		StringBuilder sql = new StringBuilder(" select ID, CONFIG_ID, ORIGINAL_VALUE, TARGET_VALUE, STATUS, CREATETIME, CREATEUSER, ENDTIME,DESCRIBE,ENABLED "); 
		sql.append(" @from DZ_DATA_SYNC_TASK s  ");
		sql.append(" where 1=1 ");
		String describe=(String) po.getWebParam("describe");
		if(StringUtils.hasText(describe)){
			sql.append(" and s.DESCRIBE like:describe");
			po.addSQLParam("describe", "%"+describe+"%");
		}
		String beginTime=(String) po.getWebParam("beginTime");
		String endtaskTime=(String) po.getWebParam("endTime");
		if(StringUtils.hasText(beginTime)){
			sql.append(" and s.CREATETIME >=:beginTime");
			po.addSQLParam("beginTime", beginTime);
		}
		if(StringUtils.hasText(endtaskTime)){
			sql.append(" and s.CREATETIME <=:endtaskTime");
			po.addSQLParam("endtaskTime", endtaskTime);
		}
		String status=(String) po.getWebParam("status");
		if(StringUtils.hasText(status)){
			sql.append(" and s.STATUS =:status");
			po.addSQLParam("status", status);
		}
		String enabled=(String) po.getWebParam("enabled");
		if(StringUtils.hasText(enabled)){
			sql.append(" and s.ENABLED =:enabled");
			po.addSQLParam("status", status);
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("s.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int  addDzDataSyncTask(DzDataSyncTask dzDataSyncTask) {
		KAssert.hasText(dzDataSyncTask.getConfigId(), "配置ID不能为空");
		KAssert.hasText(dzDataSyncTask.getOriginalValue(), "要操作的数据不能为空");
//		KAssert.hasText(dzDataSyncTask.getDescribe(), "描述不能为空");
		KAssert.hasText(dzDataSyncTask.getTargetValue(), "操作后的数据不能为空");
		dzDataSyncTask.setCreatetime(new Date());
		dzDataSyncTask.setCreateuser(SecurityUtils.getUserId());
		dzDataSyncTask.setStatus("1");
		dzDataSyncTask.setEnabled("1");
		return dzDataSyncTaskMapper.insertSelective(dzDataSyncTask);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteDzDataSyncTaskById(String id) {
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要删除的数据");
		}
		dzDataSyncTaskMapper.deleteByPrimaryKey(id);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateDzDataSyncTask(DzDataSyncTask dzDataSyncTask) {
		KAssert.hasText(dzDataSyncTask.getConfigId(), "配置ID不能为空");
		KAssert.hasText(dzDataSyncTask.getOriginalValue(), "要操作的数据不能为空");
	//	KAssert.hasText(dzDataSyncTask.getDescribe(), "描述不能为空");
		KAssert.hasText(dzDataSyncTask.getTargetValue(), "操作后的数据不能为空");
		dzDataSyncTaskMapper.updateByPrimaryKeySelective(dzDataSyncTask);
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public List<DzDataSyncTask> findSyncTaskByBizId(String bizId, String enabled, String status) {
		if(bizId==null||bizId.isEmpty()){
			return null;
		}
		return dzDataSyncTaskMapper.selectSyncTaskByBizId(bizId, enabled, status);
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public List<DzDataSyncTask> findSyncTaskByConfigId(String configId, String enabled, String status) {
		if(configId==null||configId.isEmpty()){
			return null;
		}
		return dzDataSyncTaskMapper.selectSyncTaskByConfigId(configId, enabled, status);
	}
}
