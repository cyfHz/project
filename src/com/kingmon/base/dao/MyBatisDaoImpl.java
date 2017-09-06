package com.kingmon.base.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.support.SqlSessionDaoSupport;
public class MyBatisDaoImpl extends SqlSessionDaoSupport implements MyBatisDao {

	public Connection getConnection() {
		return getSqlSession().getConnection();
	}

	@Override
	public <T> int batisDelete(String statement_key, T obj) {
		return getSqlSession().delete(statement_key, obj);
	}

	@Override
	public <T> int batisInsert(String statement_key, T obj) {
		return getSqlSession().insert(statement_key, obj);
	}

	@Override
	public <T> int batisUpdate(String statement_key, T obj) {
		return getSqlSession().update(statement_key, obj);
	}

	@Override
	public <T> List<T> batisSelectList(String statement_key, Map<String, Object> _params) {
		return getSqlSession().selectList(statement_key, _params);
	}

	@Override
	public <T> List<T> batisSelectList(String statement_key, Object _params) {
		return getSqlSession().selectList(statement_key, _params);
	}

	@Override
	public Object batisSelectOne(String statement_key, Object object) {
		return getSqlSession().selectOne(statement_key, object);
	}
}
