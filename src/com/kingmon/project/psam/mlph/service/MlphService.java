package com.kingmon.project.psam.mlph.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.psam.mlph.model.Mlph;


public  interface MlphService {

//	DataSet jlxList(Map<String, String> params);

	DataSet mlphList(Map<String, String> params);

	DataSet mlphQrList(Map<String, String> params,long num);
	void addMlph(Map<String, Object> params);

	void saveMlph(Map<String, Object> params);

	void settag(Map<String, Object> params);
	/**
	 * 提交添加申请
	 * @param dzbm
	 * @param pzlx
	 */
	public void applyUseMlph(List<String> ywlshs,String pzlx);
	/**
	 * 审批
	 * @param dzbm
	 */
	public void reviewMlph(String ywlsh,String flag);
	
	/**
	 * 
	 * @param ywlsh
	 * @return
	 */
	public Mlph findMlphByYwlsh(String ywlsh);

	public void updateMlphLocation(String ywlsh, GeoPoint pgis2Point);
	public void updateMlphLocationOnlySelf(String ywlsh, GeoPoint point);
	/**
	 * @param params
	 * @return 缺少经纬度的门楼牌号
	 */
	DataSet markList(Map<String, String> params);

	void cancel(Map<String, Object> params);

	void enable(Map<String, Object> params);

	/**
	 * 查找门楼牌号所属建筑物，<br>
	 * 
	 * @param ywlsh
	 * @return
	 */
	public String findSsJzwDzbm(String ywlsh);
	
	public Map<String,Object> selectUsers(Map<String,Object> mlph);
}
