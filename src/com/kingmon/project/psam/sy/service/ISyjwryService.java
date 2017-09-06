package com.kingmon.project.psam.sy.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.sy.model.Syjwry;


public interface ISyjwryService {
	
	public DataSet loadSyjwryDataSet(Map<String,String> params);
	
	public Syjwry loadSyjwryDeatil(String jwryid);
    /**
     * 修改境外人员信息采集信息
     * @param jwry
     * @param picbytes 
     */
	public void updateSyJwryAccInfo(Syjwry jwry, byte[] picbytes);
    /**
     * 根据建筑物Id加载境外人员信息
     * @param jzwfjid
     * @return
     */
	public DataSet loadSyJwryDataSet(String jzwfjid);
    /**
     * 注销境外人员Id
     * @param id
     */
	public void cancelsyJwry(String id);
    /**
     * 启用境外人员信息
     * @param id
     */
	public void activatesyJwry(String id);
   /**
    * 根据房间ID查询境外人员信息
    * @param jzwfjid
    * @return
    */
	public List<Syjwry> selectJwryInfoByJzwfjId(String jzwfjid);
	
	  /**
	    * 根据房间ID查询境外人员信息
	    * @param jzwfjid
	    * @return
	    */
	public List<Syjwry> selectJwryInfoByJzwfjIdKey(String jzwfjid);
	
	
    /**
     * 根据证件号码
     * @param sfzh
     * @return
     */
   

	public Syjwry selectJwryInfoByZjbh(String sfzh);
	/**
	    * 根据身份证号查询人口信息
	    * @param sfzh
	    * @return
	    */
		public Map<String, String> queryRkInfo(String sfzh);
   /**
    * 加载 采集境外人员一天的工作量
    * @param map
    * @return
    */
	 public long loadTodayWorkCount(Map<String, Object> map);
    /**
     * 加载 统计境外人员的详细信息
     * @param map
     * @return
     */
     public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map);
   
     
     /**
	    * 根据房间ID查询  当前房屋 内 境外人员信息
	    * 2016-08-11
	    * @param jzwfjid
	    * @return
	    */
	public List<Syjwry> selectJwryInfoFwByJzwfjIdKey(String jzwfjid);
	
	
//---------------------------------------------
    public DataSet selectSyJwrkForFw(Map<String, String> map);
	
	public DataSet selectSyJwrkForFwLsry(Map<String, String> map);
}
