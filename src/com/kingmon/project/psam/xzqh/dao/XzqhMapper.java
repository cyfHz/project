package com.kingmon.project.psam.xzqh.dao;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;

@KMapper
public interface XzqhMapper {
	
	public List<Map<String,Object>> xzqhList(Map<String,String> params);
	public Long xzqhListCount(Map<String,String> params);
	
	public List<Map<String, Object>> getChild(String id);
	public Long getChildCount(String id);
	
	public List<Map<String, Object>> getDictByCode(String code);
	
	public Long getXzjdCount(String id);
	public List<Map<String, Object>> getXzjd(String id);
	public Long getSqcjwhCount(String id);
	public List<Map<String, Object>> getSqcjwh(String id);
	
	
	/**
	 * 添加
	 * @param params
	 */
	public void addXzqh(Map<String, Object> params);
	
	/**
	 * 更新
	 * @param params
	 */
	public void saveXzqh(Map<String, Object> params);
	
	/**
	 * 注销
	 * @param params
	 */
	public void cancelXzqh(Map<String, Object> params);
	
	/**
	 * 启用
	 * @param params
	 */
	public void activateXzqh(Map<String, Object> params);
	
	/**
	 * 根据id 查询代码
	 * @param id
	 * @return
	 */
	public String selectXzqhdmById(String id);
	
	public Xzqh selectXzqhBydm(String xzqhdm);
	public List<Xzqh> selectXzqhBydmList(String xzqhdm);
	
	/**
	 * 
	 * @param DZBM dzbm
	 * @return
	 */
	public String selectSjxzqyDzbmByKey(String dzbm);
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	public String selectDzbmByDm(String xzqhdm);
	
	
	public List<Map<String, Object>> selectXzqhByXzqhdm(String xzqhdm);
	public Xzqh selectXzqhByDzbm(String dzbm);
	
	/**
	 * 行政区划查询服务
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> xzqhListForWebService(Map<String, String> params);
	/**
	 * 行政区划查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
	
	
	List<Map<String,String>>selectXzqyMapByDzbm(String dzbm);
	
	public List<Map<String, Object>> getXzqhXzjd(String id);
	public Map<String, Object> getXzqh(String id);
	public Map<String, Object> getXzqhJd(String id);
	public Map<String, Object> getXzqhXzsqj(String id);
	public Long getCount(String id);
	public Map<String, Object> getXzqhSqj(String id);
	public Map<String, Object> getXzjdMap(String id);
	public List<Map<String, Object>> selectXzqhBydzbm(String id);
	public List<Map<String, Object>> selectXzqhBydzbmSQJ(String id);
	public Map<String, Object> getXzqhJdSqj(String id);
	
	public Map<String,Object> selectDetailByPrimaryKey(String dzbm);
}
