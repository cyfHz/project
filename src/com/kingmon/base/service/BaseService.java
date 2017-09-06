package com.kingmon.base.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.kingmon.base.dao.JdbcBaseDao;
import com.kingmon.base.dao.MyBatisDao;

public class BaseService {
	
	@Autowired
	protected JdbcBaseDao jdbcBaseDao;

	protected JdbcBaseDao getJdbcBaseDao() {
		return jdbcBaseDao;
	}
	
	@Autowired
	protected MyBatisDao myBatisDao;

	public MyBatisDao getMyBatisDao() {
		return myBatisDao;
	}
	
}
