package com.kingmon.project.psam.dwxx.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.dwxx.model.Dwxx;

@KMapper
public interface DwxxMapper {
	/**
	 * 查询单位信息表数据列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> dwxxList(Map<String, String> params);
	/**
	 * 查询单位信息表数据数量
	 * @param params
	 * @return
	 */
	Long dwxxListCount(Map<String, String> params);
	
	/**
	 * 批量修改单位信息表中ES同步标志 is_indexed = 1
	 * @param map
	 */
	void batchUpdateIndex(Map<String, Object> map);
	/**
	 * 根据单位Id查询单位信息
	 * @param dwid
	 * @return
	 */
	Dwxx selectDwXxById(String dwid);
    /**
     * 更新单位信息
     * @param dwxx
     */
	void updateByPrimaryKeySelective(Dwxx dwxx);
	/**
	 * 插入单位信息
	 * @param dwxx
	 */
	void insertSelective(Dwxx dwxx);
	/**
	 * 根据单位Id查询人员信息
	 * @param zagldwbm
	 * @return
	 */
	List<Map<String, Object>> selectDwCyryxx(String zagldwbm);
	/**
	 * 根据单位名称查询单位信息
	 * @param dwmc
	 * @return
	 */
	List<Dwxx> selectDwxxListByDwMc(String dwmc);
	
	/**
	 * 根据房屋id查询 单位信息
	 * @param fjid
	 * @return
	 */
	public Dwxx selectDwxxForFjid(String fjid);
	
	/**
	 * 根据单位编码  查询单位的记录
	 * @param zagldwbm
	 * @return
	 */
	public Long selectCountByDwxxKey(String zagldwbm);
	
//----------------查询房屋单位信息-----------------------
	public List<Map<String, Object>> loadJzwFwDwxxForFjid(Map<String,String> params);
	
//	public List<Dwxx> loadJzwFwLsDwxxForFjid(String fjid);
	public List<Map<String, Object>> loadJzwFwLsDwxxForFjid(Map<String,String> params);
	
	public Long CountJzwFwDwxxForFjid(Map<String,String> params);
	public Long CountJzwFwLsDwxxForFjid(Map<String,String> params);
	
}
