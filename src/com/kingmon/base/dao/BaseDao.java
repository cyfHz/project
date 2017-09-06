package com.kingmon.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class BaseDao<T> extends NamedParameterJdbcDaoSupport implements RowMapper<T> {
	protected String GET_BY_ID_SQL;
	protected String INSERT_SQL;
	protected String UPDATE_SQL;
	protected String GET_ALL_SQL;
	protected String DEL_BY_ID_SQL;

	public BaseDao(DataSource dataSource) {
		setDataSource(dataSource);
	}

	/**
	 * model to map
	 * 
	 * @param t
	 * @return
	 */
	protected abstract HashMap<String, Object> toMap(final T t);

	@SuppressWarnings("unchecked")
	private Map<String, Object>[] toMaps(final List<T> list) {
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
		if (list == null || list.isEmpty()) {
			return new Map[0];
		}
		for (T t : list) {
			maps.add(toMap(t));
		}
		return maps.toArray(new Map[list.size()]);
	};

	public T getById(Long id) {
		try {
			return this.getJdbcTemplate().queryForObject(GET_BY_ID_SQL, new Object[] { id }, this);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<T> getAll() {
		return this.getJdbcTemplate().query(GET_ALL_SQL, this);
	}

	public int delById(Long id) {
		return this.getJdbcTemplate().update(DEL_BY_ID_SQL, id);
	}

	public void update(final T t) {
		this.getNamedParameterJdbcTemplate().update(UPDATE_SQL, toMap(t));
	}

	public void batchUpdate(final List<T> list) {
		this.getNamedParameterJdbcTemplate().batchUpdate(UPDATE_SQL, toMaps(list));
	}

	/**
	 * @param t
	 * @param pkName
	 *            主键名称
	 * @return 自动生成主键
	 */
	public long add(final T t, String pkName) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource namedParameters = new MapSqlParameterSource(toMap(t));
		this.getNamedParameterJdbcTemplate().update(INSERT_SQL, namedParameters, keyHolder, new String[] { pkName });
		return keyHolder.getKey().longValue();
	}

	/**
	 * 
	 * @param t
	 */
	public void add(final T t) {
		this.getNamedParameterJdbcTemplate().update(INSERT_SQL, new MapSqlParameterSource(toMap(t)));
	}

	public void batchAdd(final List<T> list) {
		this.getNamedParameterJdbcTemplate().batchUpdate(INSERT_SQL, toMaps(list));
	}
}
