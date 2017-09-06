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
import com.kingmon.project.psam.datasync.mapper.DzDataSyncBizMapper;
import com.kingmon.project.psam.datasync.model.DzDataSyncBiz;
import com.kingmon.project.psam.datasync.service.IDzDataSyncBizService;
@Service
public class DzDataSyncBizServiceImpl  extends BaseService implements IDzDataSyncBizService{
	@Autowired
	private DzDataSyncBizMapper dzDataSyncBizMapper;
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DzDataSyncBiz findDzDataSyncBizById(String id) {
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要操作的数据");
		}
		DzDataSyncBiz syBiz=dzDataSyncBizMapper.selectByPrimaryKey(id);
		return syBiz;
	}
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public DataSet loadDzDataSyncBizDataset(ParamObject po) {
		StringBuilder sql = new StringBuilder("SELECT ID, LOGICMETHOD, ENABLED, DESCRIBE ");
		
		sql.append(" @from DZ_DATA_SYNC_BIZ s  ");
		sql.append(" where 1=1 ");
		String describe=(String) po.getWebParam("describe");
		if(StringUtils.hasText(describe)){
			sql.append(" and s.DESCRIBE like:describe");
			po.addSQLParam("describe", "%"+describe+"%");
		}
		if (po.hasOrder()) {
			sql.append(" order by ").append("s.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addDzDataSyncBiz(DzDataSyncBiz dzDataSyncBiz) {
		KAssert.hasText(dzDataSyncBiz.getClass(), "同步数据类不能为空");
		KAssert.hasText(dzDataSyncBiz.getLogicmethod(), "同步数据所在的方法不能为空");
		KAssert.hasText(dzDataSyncBiz.getDescribe(), "同步数据描述不能为空");
		dzDataSyncBiz.setEnabled("1");//1为启用
		dzDataSyncBizMapper.insertSelective(dzDataSyncBiz);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateDzDataSyncBiz(DzDataSyncBiz dzDataSyncBiz) {
		KAssert.hasText(dzDataSyncBiz.getClass(), "同步数据类不能为空");
		KAssert.hasText(dzDataSyncBiz.getLogicmethod(), "同步数据所在的方法不能为空");
		KAssert.hasText(dzDataSyncBiz.getDescribe(), "同步数据描述不能为空");
		dzDataSyncBizMapper.updateByPrimaryKeySelective(dzDataSyncBiz);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteDzDataSyncBizById(String id) {
		if(!StringUtils.hasText(id)){
			AlertSLEUtil.Error("请选择要删除的数据");
		}
		dzDataSyncBizMapper.deleteByPrimaryKey(id);
	}
	
}
