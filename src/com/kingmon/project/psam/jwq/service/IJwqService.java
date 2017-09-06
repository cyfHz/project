package com.kingmon.project.psam.jwq.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.jwq.model.Jwq;

public interface IJwqService {


	/**
	 * 根据条件查询警务区信息
	 * 
	 * @param paramObject
	 *            查询条件<br/>
	 *            乡镇街道代码 XZJDDM 类型：String;<br/>
	 *            乡镇街道名称 XZJDMC 类型：String;<br/>
	 *            使用状态 SYZTDM 类型：String;<br/>
	 * 
	 * @return DataSet: 返回值：乡镇街道集合DataSet
	 */
	public DataSet loadJwqDataSet(ParamObject paramObject);
	/**
	 * 增加警务区信息
	 * @param jwq 警务区信息
	 */
	public void addJwq(Jwq jwq);
	/**
	 *根据警务区Id查询警务区信息
	 * @param jwqid警务区Id
	 * @return
	 */
	public Jwq getJwqById(String jwqid);
	
	public Jwq loadJwqByIdForUpdate(String jwqid);
	/**
	 * 更新警务区信息
	 * @param jwq: 警务区信息 
	 */
	public void updatejwq(Jwq jwq);
	/**
	 * 注销警务区信息
	 * @param id ：警务区Id
	 */
	public void cancelJwq(String id);
	/**
	 * 启用警务区信息
	 * @param id ：警务区Id
	 */
	public void activateJwq(String id);

	/**
	 * 更新边界坐标
	 * @param jwqId
	 * @param bjzbz
	 */
	public void updateBjzbz(String jwqId,String bjzbz);
	
	/**
	 * 加载用户警务区, [{type： "1",id:"";bh:"",mc:"",bjzbz:""}]
	 * @return
	 */
	public List<Map<String,Object>> loadUserJwqBianjie(String app_userId);
	
	public List<Jwq> loadJwqBjInSamePsc(String jwqid);
	
	/**
	 * 查找坐标点所在警务区
	 * @param lat
	 * @param lng
	 * @return 	JWQMC : value,<br>
	 * 			JWQHB : value<br>
	 * 			JWQID : value<br>
	 * 			SFYX  : value<br>
	 */
	public List<Map<String,Object>> findJwqByPoint(double lat,double lng);
	
	public List<Map<String,Object>> findJwqByPointAndUserPerm(String app_userid,double lat, double lng);
	
	
	
	//--------------------------------------------------------------------------
	public Object checkJwqHebing(String[] jwqIds);
	public void processJwqHeBing(String[] fromIds, String toId);
	
	public DataSet loadJwqChaiFenDataGrid(String fromJwqId);
	public void processJwqChaiFen(String fromId,String[] toIds);

}
