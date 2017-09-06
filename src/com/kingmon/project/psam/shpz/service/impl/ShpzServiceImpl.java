package com.kingmon.project.psam.shpz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.project.psam.shpz.mapper.ShpzMapper;
import com.kingmon.project.psam.shpz.model.Shpz;
import com.kingmon.project.psam.shpz.service.IShpzService;
@Service
public class ShpzServiceImpl extends BaseService implements IShpzService{
	
	@Autowired
	private ShpzMapper shpzMapper;
	
	@Cacheable(value="defaultQueryResultCache",key="'ShpzServiceImpl_selectShpzById'+#pzid")
	@Transactional(rollbackFor=Exception.class,readOnly=true)
    public Shpz selectShpzById(String pzid){
    	if(StringUtils.hasText(pzid)){
    		return  shpzMapper.selectByPrimaryKey(pzid);
    	}
    	return null; 
    }
	
	@Transactional(rollbackFor=Exception.class)
    public int deleteShpzById(String pzid){
    	if(StringUtils.hasText(pzid)){
    		return  shpzMapper.deleteByPrimaryKey(pzid);
    	}
    	return 0; 
    }
	@Transactional(rollbackFor=Exception.class)
    public int addShpz(Shpz record){
    	KAssert.hasText(record.getPzlx(), "配置类型不能空");
    	KAssert.hasText(record.getPzdm(), "配置字典项不能空");
    	KAssert.hasText(record.getOrganCode(), "配置单位不能空");
    	return  shpzMapper.insertSelective(record);
    }

	@Transactional(rollbackFor=Exception.class)
    public int updateShpz(Shpz record){
    	KAssert.hasText(record.getPzlx(), "配置类型不能空");
    	KAssert.hasText(record.getPzdm(), "配置字典项不能空");
    	KAssert.hasText(record.getOrganCode(), "配置单位不能空");
    	return  shpzMapper.updateByPrimaryKeySelective(record);
    }

	@Override
	public Shpz findShpzByLxAndOrgCode(String pzlx, String orgCode) {
		if(!StringUtils.hasText(pzlx)){
			return null;
		}
		if(!StringUtils.hasText(orgCode)){
			return null;
		}
		return shpzMapper.selectShpz(pzlx, orgCode);
	}

	@Override
	public DataSet loadShpzDataSet(ParamObject po) {
		StringBuilder sql = new StringBuilder("SELECT  PZID, PZLX, PZDM, ORGAN_CODE from DZ_SHPZ x where 1=1 ");
		
		String pzlx = (String) po.getWebParam("pzlx");
		if (!SubApStrUtil.isEmptyAfterTrimE(pzlx)) {
			sql.append(" and x.XZJDDM =:pzlx ");
			po.addSQLParam("pzlx", pzlx);
		}
		
		String xzqh_code_shi = (String) po.getWebParam("xzqh_code_shi");
		if (!SubApStrUtil.isEmptyAfterTrimE(xzqh_code_shi)) {
			sql.append(" and x.ORGAN_CODE =:xzqh_code_shi ");
			po.addSQLParam("xzqh_code_shi",xzqh_code_shi);
		}
		
		if (po.hasOrder()) {
			sql.append(" order by ").append("x.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	@Cacheable(value="defaultQueryResultCache",key="'ShpzServiceImpl_findShpzDm'+#pzlx+#orgCode")
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	@Override
	public String findShpzDm(String pzlx, String orgCode) {
		if(pzlx==null||orgCode==null||orgCode.length()<6){
			return null;
		}
		orgCode=orgCode.substring(0, 6);
		Shpz shpz= shpzMapper.selectShpz(pzlx, orgCode);
		if(shpz==null){
			return null;
		}
		return shpz.getPzdm();
	}

}