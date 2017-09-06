package com.kingmon.project.psam.datasync.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.project.psam.datasync.mapper.DzDataSyncTaskErrorMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncTaskError;
import com.kingmon.project.psam.datasync.service.IDzDataSyncTaskErrorService;

@Service
public class DzDataSyncTaskErrorServiceImpl  extends BaseService implements IDzDataSyncTaskErrorService{
	@Autowired
	private DzDataSyncTaskErrorMapper dzDataSyncTaskErrorMapper;
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DzDataSyncTaskError findDzDataSyncTaskErrorById(String id) {
			
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要操作的数据");
		}
		DzDataSyncTaskError taskerror=dzDataSyncTaskErrorMapper.selectByPrimaryKey(id);
		return taskerror;
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadDzDataSyncTaskErrorDataset(ParamObject po) {
		StringBuilder sql = new StringBuilder("ID, TASK_ID, ERRORTIME,DESCRIBE "); 
		sql.append(" @from DZ_DATA_SYNC_TASK_ERROR s  ");
		sql.append(" where 1=1 ");
		String describe=(String) po.getWebParam("describe");
		if(StringUtils.hasText(describe)){
			sql.append(" and s.DESCRIBE like:describe");
			po.addSQLParam("describe", "%"+describe+"%");
		}
		String beginTime=(String) po.getWebParam("beginTime");
		String endtaskTime=(String) po.getWebParam("endTime");
		if(StringUtils.hasText(beginTime)){
			sql.append(" and s.ERRORTIME >=:beginTime");
			po.addSQLParam("beginTime", beginTime);
		}
		if(StringUtils.hasText(endtaskTime)){
			sql.append(" and s.ERRORTIME <=:endtaskTime");
			po.addSQLParam("endtaskTime", endtaskTime);
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("s.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addDzDataSyncTaskError(DzDataSyncTaskError DzDataSyncTaskError) {
		KAssert.hasText(DzDataSyncTaskError.getTaskId(), "出错数据的任务不能为空");
		KAssert.hasText(DzDataSyncTaskError.getErrortime(), "出错时间不能为空");
		KAssert.hasText(DzDataSyncTaskError.getDescribe(), "出错描述不能为空");
		dzDataSyncTaskErrorMapper.insertSelective(DzDataSyncTaskError);
		
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteDzDataSyncTaskErrorById(String id) {
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要删除的数据");
		}
		dzDataSyncTaskErrorMapper.deleteByPrimaryKey(id);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateDzDataSyncTaskError(DzDataSyncTaskError DzDataSyncTaskError) {
		KAssert.hasText(DzDataSyncTaskError.getTaskId(), "出错数据的任务不能为空");
		KAssert.hasText(DzDataSyncTaskError.getErrortime(), "出错时间不能为空");
		KAssert.hasText(DzDataSyncTaskError.getDescribe(), "出错描述不能为空");
		dzDataSyncTaskErrorMapper.updateByPrimaryKeySelective(DzDataSyncTaskError);
	}
	
	
}
