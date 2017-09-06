package com.kingmon.project.psam.datasync.service.impl;

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
import com.kingmon.project.psam.datasync.mapper.DzDataSyncConfigMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncConfig;
import com.kingmon.project.psam.datasync.service.IDzDataSyncConfigService;
@Service
public class DzDataSyncConfigServiceImpl  extends BaseService implements IDzDataSyncConfigService{
	@Autowired
	private DzDataSyncConfigMapper dzDataSyncConfigMapper;
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DzDataSyncConfig findDzDataSyncConfigById(String id) {
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要操作的数据");
		}
		
		DzDataSyncConfig config=dzDataSyncConfigMapper.selectByPrimaryKey(id);
		
		return config;
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadDzDataSyncConfigDataset(ParamObject po) {
		StringBuilder sql = 
				new StringBuilder(" select ID, BIZ_TABLE, BIZ_FILED, REFER_TABLE, REFER_FILED, ENABLED, ");
					   sql.append(" BIZ_ID, IS_SYNC_SEARCH, SEARCH_TYPE, SEARCH_FIELD,DESCRIBE,EXORDER  "); 
		sql.append(" @from DZ_DATA_SYNC_CONFIG s  ");
		sql.append(" where 1=1 ");
		
		String describe=(String) po.getWebParam("describe");
		if(StringUtils.hasText(describe)){
			sql.append(" and s.DESCRIBE like:describe");
			po.addSQLParam("describe", "%"+describe+"%");
		}
		
		String bizTable=(String) po.getWebParam("bizTable");
		if(StringUtils.hasText(bizTable)){
			sql.append(" and s.BIZ_TABLE like:bizTable");
			po.addSQLParam("bizTable", "%"+bizTable+"%");
		}
		String referTable=(String) po.getWebParam("referTable");
		if(StringUtils.hasText(referTable)){
			sql.append(" and s.REFER_TABLE like:referTable");
			po.addSQLParam("referTable", "%"+referTable+"%");
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("s.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addDzDataSyncConfig(DzDataSyncConfig DzDataSyncConfig) {
		KAssert.hasText(DzDataSyncConfig.getBizTable(), "同步数据所在表不能为空");
		KAssert.hasText(DzDataSyncConfig.getBizFiled(), "同步数据所在的字段不能为空");
		KAssert.hasText(DzDataSyncConfig.getReferTable(), "同步数据关联表不能为空");
		KAssert.hasText(DzDataSyncConfig.getReferFiled(), "同步数据关联字段不能为空");
		KAssert.hasText(DzDataSyncConfig.getDescribe(), "同步数据描述不能为空");
		KAssert.hasText(DzDataSyncConfig.getBizId(), "同步数据所关联的逻辑不能为空");
		DzDataSyncConfig.setEnabled("1");
		dzDataSyncConfigMapper.insertSelective(DzDataSyncConfig);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteDzDataSyncConfigById(String id) {
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要删除的数据");
		}
		dzDataSyncConfigMapper.deleteByPrimaryKey(id);
		
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateDzDataSyncConfig(DzDataSyncConfig DzDataSyncConfig) {
		KAssert.hasText(DzDataSyncConfig.getBizTable(), "同步数据所在表不能为空");
		KAssert.hasText(DzDataSyncConfig.getBizFiled(), "同步数据所在的字段不能为空");
		KAssert.hasText(DzDataSyncConfig.getReferTable(), "同步数据关联表不能为空");
		KAssert.hasText(DzDataSyncConfig.getReferFiled(), "同步数据关联字段不能为空");
		KAssert.hasText(DzDataSyncConfig.getDescribe(), "同步数据描述不能为空");
		KAssert.hasText(DzDataSyncConfig.getBizId(), "同步数据所关联的逻辑不能为空");
		dzDataSyncConfigMapper.updateByPrimaryKeySelective(DzDataSyncConfig);
	}
	@Override
	public List<DzDataSyncConfig> findDzDataSyncConfigByBizId(String syncBizId) {
		if(StringUtils.hasText(syncBizId)){
			return dzDataSyncConfigMapper.selectByBizId(syncBizId);
		}
		return null;
	}
	
	
}
