package com.kingmon.project.psam.sy.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;


public interface ISyRkglLdrkdjbService {
	
	public DataSet loadSyLdrkDataSet(Map<String,String> params);
	
	public SyRkglLdrkdjb loadSyLdrkDeatil(String ldid);
   /**
    * 修改流动人口信息
    * @param ldrk
 * @param picbytes 
    */
	public void updateSyLdrkAccInfo(SyRkglLdrkdjb ldrk, byte[] picbytes);
    /**
     * 根据建筑物Id加载流动人口信息
     * @param jzwfjid
     * @return
     */
    public DataSet loadSyLdrkDataSet(String jzwfjid);
     /**
      * 注销流动人口
      * @param id
      */
	public void cancelLdrk(String id);
    /**
     * 启用流动人口
     * @param id
     */
	public void activatesyLdrk(String id);
    /**
     * 根据房间号查询流动人口信息
     * @param jzwfjid
     * @return
     */
	public List<SyRkglLdrkdjb> selectSyLdrkInfoByJzwfjId(String jzwfjid);
	
	/**
     * 根据房间号查询流动人口信息
     * @param jzwfjid
     * @return
     */
	public List<SyRkglLdrkdjb> selectSyLdrkInfoByJzwfjIdKey(String jzwfjid);
	
	
    /**
     * 根据证件编号查询流动人口信息
     * @param sfzh
     * @return
     */
	public SyRkglLdrkdjb selectLdrkInfoByZjbh(String sfzh);
	
	   /**
	    * 根据身份证号查询人口信息
	    * @param sfzh
	    * @return
	    */
		public Map<String, String> queryRkInfo(String sfzh);
		  /**
	     * 统计采集流动人口个数
	     * @param map
	     * @return
	     */
	    public long loadldrkWorkCount(Map<String, Object> map);
	    /**
	     * 加载当天采集流动人口详细信息
	     * @param map
	     * @return
	     */

		public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map);
   
		
		/**
	     * 根据房间号查询 当前 房屋内 流动人口信息
	     * 2016-08-11
	     * @param jzwfjid
	     * @return
	     */
		public List<SyRkglLdrkdjb> selectSyLdrkInfoFwByJzwfjIdKey(String jzwfjid);
		
//---------------------------------------------
	    public DataSet selectSyLdrkForFw(Map<String, String> map);
		
		public DataSet selectSyLdrkForFwLsry(Map<String, String> map);
}
