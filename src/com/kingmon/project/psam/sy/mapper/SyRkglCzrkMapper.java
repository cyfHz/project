package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
@KMapper
public interface SyRkglCzrkMapper {

    int insertSelective(SyRkglCzrk record);

    SyRkglCzrk selectByPrimaryKey(String rkid);

    int updateByPrimaryKeySelective(SyRkglCzrk record);

    
	public List<Map<String,Object>> selectSyCzrklist(Map<String,String> params);
	
	public Long selectSyCzrkCount(Map<String,String> params);
    /**
     * 根据建筑物房间Id查询建筑
     * @param jzwfjid
     * @return
     */
	public SyRkglCzrk selectSyRkglCzrkByJZWFJID(String jzwfjid);
    /**
     * 根据身份证号查询常住人口管理表中的人口信息
     * @param gmsfhm
     * @return
     */
	public SyRkglCzrk selectRkglCzrkById(String gmsfhm);
    
    /**
     * 注销常住人口信息
     * @param map
     */
	public void cancelCzrk(Map<String, Object> map);
    /**
     * 启用常住人口信息
     * @param map
     */
	public void activatesyCzrk(Map<String, Object> map);
     /**
      * 根据身份证号和房间Id查询常住人口信息
      * @param sfzh
      * @param jzwfjid
      * @return
      */
	public SyRkglCzrk selectCzrkbysfzhandjzwfj(String sfzh, String jzwfjid);
     /**
      * 根据建筑物房间Id查询常住人口信息
      * @param jzwfjid
      * @return
      */
	public List<SyRkglCzrk> selectSyCzrkInfoByJzwfjId(String jzwfjid);
	
	/**
	 * 根据房间ID 查询全部常住人口信息
	 * @param jzwfjid
	 * @return
	 */
	public List<SyRkglCzrk> selectSyCzrkInfoByJzwfjIdKey(String jzwfjid);

	 public  SyRkglCzrk selectCzrkInfoByZjbh(String sfzh);
    /**
     * 查询工作量
     * @param map
     * @return
     */
	long selectSyCzrkWorkCount(Map<String, Object> map);
	
     /**
      * 查询一个人当天的工作量
      */
	List<Map<String, Object>> selectSyCzrkWorkDetail(Map<String, Object> map);
	
	
	/**
	 * 根据房间ID 查询当前 房屋内 常住人口信息
	 *   2016-08-11
	 * @param jzwfjid
	 * @return
	 */
	public List<SyRkglCzrk> selectSyCzrkInfoFwByJzwfjIdKey(String jzwfjid);
	
	
//-----------------------------------16-08-16-------------
	public List<Map<String,Object>> selectSyCzrkForFw(Map<String, String> map);
	
	public Long selectSyCzrkForFwCount(Map<String, String> map);
	
	public List<Map<String,Object>> selectSyCzrkForFwLsry(Map<String, String> map);
	
	public Long selectSyCzrkForFwLsryCount(Map<String, String> map);
}