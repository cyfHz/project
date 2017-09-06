package com.kingmon.base.dao;

import java.util.List;
import java.util.Map;

public interface MyBatisDao {
	
	public <T> int batisInsert(String statement_key, T obj);

	public <T> int batisUpdate(String statement_key, T obj);

	public <T> int batisDelete(String statement_key, T obj);

	public <T> List<T> batisSelectList(String statement_key, Map<String, Object> params);

	public <T> List<T> batisSelectList(String statement_key, Object params);

	public Object batisSelectOne(String statement_key, Object object);
	
}
