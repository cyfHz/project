package com.kingmon.base.dao;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;

public interface JdbcBaseDao{
	public static final String oracle_page_sql = " select * from (select kmzht_$table.*,rownum as kmzht_$rownum from ({0}) kmzht_$table where rownum <= {1} ) where kmzht_$rownum>{2} ";
//---------------------------------------------------------------------------------------------------------
	 DataSet jdbcLoadDataSet(String querySQL,ParamObject paramObject);
	
	 List<Map<String, Object>> jdbcQueryMapList(String querySQL,ParamObject paramObject);
	 
	 long jdbcQueryCount(String countSQL,ParamObject paramObject);
	 
	 Object  jdbcQueryOneRowByUnque(String queryStr,ParamObject paramObject);
	 
	 public Object jdbcQueryObject(String querySQL, ParamObject paramObject,Class<?> calzz) ;
	 
	 public int jdbcInsert(String updateSQL, ParamObject paramObject);
	 
	 public int[] jdbcBatchInsert(String batchInsertSQL,Map<String,?>[] paramObject);
	 
	 int jdbcUpdate(String updateSQL,ParamObject paramObject);
	
	 int[] jdbcBatchUpdate(String updateSQL, Map<String,?>[] paramObject);
	
	 int jdbcDelete(String updateSQL,ParamObject paramObject);
	
	 int[] jdbcBatchDelete(String updateSQL, Map<String,?>[] paramObject);
	 
	 String queryUniqueOneColumn(String updateSQL,ParamObject paramObject);
	
}
