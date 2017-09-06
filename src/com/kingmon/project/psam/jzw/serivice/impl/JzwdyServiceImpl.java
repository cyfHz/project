package com.kingmon.project.psam.jzw.serivice.impl;

import org.springframework.stereotype.Service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.jzw.serivice.IJzwdyService;
@Service
public class JzwdyServiceImpl extends BaseService implements IJzwdyService{

	@Override
	public DataSet loadJzwdyDataSet(ParamObject paramObject) {
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT dy.*,xx.JZWMC as JZWMC ");
		sql.append(" @from DZ_JZWDY dy ");
		sql.append(" left join DZ_JZWJBXX xx ");
		sql.append(" on dy.JZWJGID=xx.DZBM ");
		sql.append(" where 1=1 ");
		
		if (paramObject.hasOrder()) {
			sql.append(" order by ").append("dy.").append(paramObject.getSort()).append(" ").append(paramObject.getOrder()).append(" ");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), paramObject);
	}

}
