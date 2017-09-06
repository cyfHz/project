package com.kingmon.project.psam.appVersion.service.Impl;

import org.springframework.stereotype.Service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.appVersion.service.IAppVersionService;
@Service
public class AppVersionServiceImpl extends BaseService implements IAppVersionService {

	@Override
	public DataSet loadAPPGridView() {
		// TODO Auto-generated method stub
		ParamObject po=new ParamObject();
		StringBuilder sql = new StringBuilder("");
		sql.append(" SELECT j.ID, ")
		.append(" j.APP_NAME, ")
		.append(" j.APP_VERS, ")
		.append(" j.APP_NR")
		.append(" @from APP_VERSION j")
		.append(" where 1=1 ");
		 return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}
	

}
