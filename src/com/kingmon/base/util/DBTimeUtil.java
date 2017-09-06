package com.kingmon.base.util;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.kingmon.base.dao.JdbcBaseDao;
import com.kingmon.base.dao.JdbcBaseDaoImpl;
import com.kingmon.base.data.ParamObject;

public class DBTimeUtil {
	
	public static Date getDBTime(){
		JdbcBaseDao jdbcBaseDao=SpringBeanFacUtil.getBean(JdbcBaseDaoImpl.class);
		String sql="select sysdate as ddate from dual";
		List<Map<String, Object>> mapList=jdbcBaseDao.jdbcQueryMapList(sql, ParamObject.new_NP_NC() );
		if(mapList!=null&&mapList.size()>0){
			Date date=(Date) mapList.get(0).get("ddate");
			return date;
		}
		return null;
	}
}
