package com.kingmon.project.psam.jzw.serivice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.serivice.IJzwlcService;
@Service
public class JzwlcServiceImpl extends BaseService implements IJzwlcService{

	@Autowired
	private JzwlcMapper jzwlcMapper;
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public DataSet loadJzwlcDataSet(ParamObject paramObject) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT lc.*,xx.JZWMC as JZWMC ,dy.DYMC as DYMC ");
		sql.append(" @from DZ_JZWLC lc ");
		sql.append(" left join DZ_JZWJBXX xx ");
		sql.append(" on lc.JZWJGID=xx.DZBM ");
		sql.append(" left join DZ_JZWDY dy ");
		sql.append(" on lc.JZWDYID=dy.JZWDYID ");
		sql.append(" where 1=1 ");
		
		if (paramObject.hasOrder()) {
			sql.append(" order by ").append("lc.").append(paramObject.getSort()).append(" ").append(paramObject.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), paramObject);
	}

	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public List<Jzwlc> loadJzwlcByJzwJgid(String jzwjgID) {
		if(!StringUtils.hasText(jzwjgID)){
			return null;
		}
		return 	jzwlcMapper.selectDistinctSortedLcxhByJzwJgid(jzwjgID);
	}

}
