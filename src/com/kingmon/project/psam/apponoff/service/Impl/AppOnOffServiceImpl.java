package com.kingmon.project.psam.apponoff.service.Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingmon.base.dao.JdbcBaseDaoImpl;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.project.psam.apponoff.mapper.AppOnOffMapper;
import com.kingmon.project.psam.apponoff.model.AppOnOff;
import com.kingmon.project.psam.apponoff.service.IAppOnOffService;
@Service
public class AppOnOffServiceImpl extends BaseService implements IAppOnOffService {
	@Autowired
	private AppOnOffMapper  appOnOffMapper;

	@Override
	public AppOnOff findStatusByAreaCode(String areaCode) {
		// TODO Auto-generated method stub
		KAssert.hasText(areaCode, "区域代码不能为空");
		AppOnOff appOnOff=appOnOffMapper.selectStatusByAreaCode(areaCode);
		return appOnOff;
	}


	/**
	 * 移动端登录时 验证IMEI号
	 */
	public Long countImei(String imei,String sfzh){
		String sql="select count(1) from zdyz  where  imei =:imei and deltag = 0 ";//and sfzh=:sfzh
		long count=jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
				.addSQLParam("imei", imei));
				//.addSQLParam("sfzh", sfzh));
		return count;
	}

}
