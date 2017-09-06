package com.kingmon.project.psam.xzqh.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.xzqh.model.Xzqh;

public  interface XzqhService {
	/**
	 * 获取行政区划列表
	 * @param params
	 * @return
	 */
	public abstract DataSet xzqhList(Map<String,String> params);
	/**
	 * 获取下级信息
	 * @param id
	 * @param showXzjd
	 * @param showSqjcwh
	 * @param type
	 * @return
	 */
	public abstract DataSet getChild(String id, boolean showXzjd, boolean showSqjcwh, String type);
//	/**
//	 * 获取数据字典
//	 * @param code
//	 * @return
//	 */
//	public abstract String getDictByCode(String code);
	/**
	 * 添加行政区划
	 * @param params
	 */
	public void addXzqh(Map<String, Object> params) ;
	/**
	 * 保存行政区划
	 * @param params
	 */
	public abstract void saveXzqh(Map<String, Object> params);
	/**
	 * 注销行政区划
	 * @param params
	 */
	public abstract void cancelXzqh(Map<String, Object> params);
	/**
	 * 启用行政区划
	 * @param params
	 */
	public abstract void activateXzqh(Map<String, Object> params);
	
	
	public Xzqh selectXzqhBydm(String xzqhdm);
	public List<Xzqh> selectXzqhBydmList(String xzqhdm);
	
	Xzqh selectXzqhByDzbm(String dzbm);
	/**
	 * 根据地址编码查询所属行政区域代码，名称信息
	 * @param dzbm
	 * @return
	 */
	public List<Map<String,String>> selectXzqyMapByDzbm(String dzbm);
	
	public Map<String,Object> selectDetailByPrimaryKey(String dzbm);
}
