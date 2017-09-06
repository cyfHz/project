package com.kingmon.project.psam.jzw.serivice;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;

public interface IJzwjbxxService {

	public DataSet loadJzwjbxxDataSet(Map<String, String> params);

	public DataSet loadJzwQrDataSet(Map<String,String> params,long num);
	/**
	 * 根据地址编码查询建筑物的基本信息
	 * 
	 * @param dzbm
	 * @return
	 */
	public Jzwjbxx getJzwJbxxById(String dzbm);


	/**
	 * 增加建筑物信息采集信息
	 * 
	 * @param view
	 */
	public String addInfoAcquisition(Map<?,?> view);

//	/**
//	 * 增加建筑物基本信息
//	 * 
//	 * @param jzwjbxx
//	 */
//	public void addJzwJbxx(Jzwjbxx jzwjbxx);

	/**
	 * 修改建筑物基本信息
	 * 
	 * @param jzwjbxx
	 */
	public void updateJzwjbxx(Jzwjbxx jzwjbxx);

	/**
	 * 批量注销（启用）建筑物基本信息
	 * 
	 * @param ids
	 * @param flag
	 */
	public void cancelJzw(String[] ids, String flag);

	public void updateJzwLocation(String dzbm, GeoPoint pgis2Point);
	public void updateJzwLocationOnlySelf(String dzbm, GeoPoint point);

	public void activeJzw(String[] ids, String flag);

	
	
	public List<Map<String,Object>> openApiFj(String jzwid);
    /**
     * 统计采集建筑物的个数
     * @param map
     * @return
     */
	public long loadWorkJzwCount(Map<String, Object> map);
	/**
	 * 查询今天统计建筑物的基本信息
	 * @param map
	 * @return
	 */

	public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map);
	
	public List<Map<String,Object>> selectJzwForMlph(String mlph);
}
