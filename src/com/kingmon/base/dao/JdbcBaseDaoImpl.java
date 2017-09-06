package com.kingmon.base.dao;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.DaoException;
import com.kingmon.base.util.SubApStrUtil;
public class JdbcBaseDaoImpl extends NamedParameterJdbcDaoSupport implements JdbcBaseDao{
	
	
	public JdbcBaseDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public DataSet jdbcLoadDataSet(String queryStr,ParamObject paramObject){
		if(queryStr==null||!(queryStr.contains("@from")||queryStr.contains("@FROM"))){
			throw new DaoException("查询语句错误,或未找到[@from]标识");
		}
		int rows=paramObject.getRows();
		int page=paramObject.getPage();
		
		Long count=0L;
		List<Map<String, Object>> list=Collections.emptyList();
		
		queryStr=queryStr.replace("@FROM", "@from");
		String queryCountStr=" select count(1) from "+SubApStrUtil.substringAfter(queryStr, "@from");
		queryStr=queryStr.replace("@from", "from");
		
		 switch (paramObject.getInitType()) { 
		 	case S_O_C:{
						long first = (page - 1) * rows;
						long last = first+rows;
						Object[] formatParam = new Object[3];
						formatParam[0] = queryStr;
						formatParam[1] = ""+last;
						formatParam[2] = ""+first;
						queryStr = MessageFormat.format(oracle_page_sql, formatParam);
				 		list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
				 		count=getNamedParameterJdbcTemplate().queryForObject(queryCountStr, paramObject,Long.class);
			 		}
	            break; 
		 	case S_O_NC:{
		 				long first = (page - 1) * rows;
		 				long last = first+rows;
						Object[] formatParam = new Object[3];
						formatParam[0] = queryStr;
						formatParam[1] = ""+last;
						formatParam[2] = ""+first;
						queryStr = MessageFormat.format(oracle_page_sql, formatParam);
				 		list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
			 		}
	            break;
		 	case S_NO_C:{
			 			list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
			 			count=getNamedParameterJdbcTemplate().queryForObject(queryCountStr, paramObject,Long.class);
		 		}
	            break;
		 	case S_NO_NC: {
		 				list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
		 		}
	            break;
	        default:
	        	throw new DaoException("未知类型的查询") ;
		 }
		 System.out.println("queryStr: "+queryStr);
		 return new DataSet(count,(List<Map<String, Object>>) (list==null?Collections.emptyList():list));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> jdbcQueryMapList(String queryStr, ParamObject paramObject) {
		queryStr=queryStr.replace("@from", "from").replace("@FROM", "FROM");
		List<Map<String, Object>> list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
		return (List<Map<String, Object>>) (list==null?Collections.emptyList():list);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object jdbcQueryObject(String querySQL, ParamObject paramObject,Class<?> clazz) {
		Object obj =null;
		try{
			 obj = getNamedParameterJdbcTemplate().queryForObject(querySQL, paramObject,
					new BeanPropertyRowMapper(clazz));
		}catch(EmptyResultDataAccessException e){
		}
//		List<?> list=getNamedParameterJdbcTemplate().queryForList(querySQL, paramObject);
//		if(list!=null&&list.size()>0){
//			return clazz.cast(list.get(0));
//		}
		return obj;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public long jdbcQueryCount(String CountSql,ParamObject paramObject) {
		Long count=0L;
		count=getNamedParameterJdbcTemplate().queryForObject(CountSql, paramObject,Long.class);
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int jdbcUpdate(String updateStr, ParamObject paramObject) {
		return getNamedParameterJdbcTemplate().update(updateStr, paramObject);
	}
	@Override
	public int[] jdbcBatchUpdate(String batchUpdateSql, Map<String,?>[] paramObject) {//Map<String,?>[]
		return getNamedParameterJdbcTemplate().batchUpdate(batchUpdateSql, paramObject);
	}
	
	@Override
	public int jdbcDelete(String deleteSql,ParamObject paramObject){
		return jdbcUpdate(deleteSql, paramObject);
	}
	@Override
	public int[] jdbcBatchDelete(String batchDeleteSql, Map<String,?>[] paramObject){
		return jdbcBatchUpdate(batchDeleteSql, paramObject);
	}

	@Override
	public int jdbcInsert(String inserSql, ParamObject paramObject) {
		return jdbcUpdate(inserSql, paramObject);
	}
	@Override
	public int[] jdbcBatchInsert(String batchInsertSql,Map<String,?>[] paramObject) {
		return getNamedParameterJdbcTemplate().batchUpdate(batchInsertSql, paramObject);
	}

	@Override
	public Object jdbcQueryOneRowByUnque(String queryStr, ParamObject paramObject) {
		queryStr=queryStr.replace("@from", "from").replace("@FROM", "FROM");
		List<Map<String, Object>> list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
		if(list!=null&&!list.isEmpty()){
			Map<String, Object> map=list.get(0);
//			Set<?> set=list.get(0).entrySet();
			if(map!=null&&!map.isEmpty()){
				Collection<Object> values = map.values();
				if(values!=null&&!values.isEmpty()){
					return values.iterator().next();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryUniqueOneColumn(String queryStr, ParamObject paramObject) {
		queryStr=queryStr.replace("@from", "from").replace("@FROM", "FROM");
		List<Map<String, Object>> list=getNamedParameterJdbcTemplate().queryForList(queryStr, paramObject);
		if(list!=null&&!list.isEmpty()){
			Map<String, Object> map=list.get(0);
			if(map==null||map.isEmpty()){
				return null;
			}
			Collection<Object> values = map.values();
			if(values== null||values.isEmpty()){
				return null;
			}
			return (String) values.iterator().next();
		}
		return null;
	
	}
	
}
