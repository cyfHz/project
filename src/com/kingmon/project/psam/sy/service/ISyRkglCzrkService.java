package com.kingmon.project.psam.sy.service;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.Syjwry;


public interface ISyRkglCzrkService {
	
	public DataSet loadSyCzrkDataSet(Map<String,String> params);
	
	public SyRkglCzrk loadSyCzrkDeatil(String rkid);
   /**
    * 修改常住人口信息
    * @param czrk
 * @param picbytes 
    */
	public void updateSyCzrkAccInfo(SyRkglCzrk czrk, byte[] picbytes);
    /**
     * 根据建筑物房间Id加载 常住人口信息
     * @param jzwfjid：建筑房间Id
     * @return
     */
   public DataSet loadSyCzrkDataSet(String jzwfjid);
    /**
     * 注销常住人口信息
     * @param id
     */
	public void cancelCzrk(String id);
   /**
    * 启用常住人口信息
    * @param id
    */
	public void activatesyCzrk(String id);
    /**
     * 根据身份证号和房间号查询常住人员信息
     * @param sfzh
     * @param jzwfjid
     * @return
     */
   public SyRkglCzrk validateCzrk(String sfzh, String jzwfjid);
    /**
     * 根据房间Id查询常住人口信息
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
	
    /**
     * 根据证件编号查询常住人口信息
     * @param sfzh
     * @return
     */
	public SyRkglCzrk selectCzrkInfoByZjbh(String sfzh);
   /**
    * 根据身份证号查询人口信息
    * @param sfzh
    * @return
    */
	public Map<String, String> queryRkInfo(String sfzh);
  
	
	/**
	 * 根据房间ID 查询当前 房屋内 常住人口信息
	 *   2016-08-11
	 * @param jzwfjid
	 * @return
	 */
	public List<SyRkglCzrk> selectSyCzrkInfoFwByJzwfjIdKey(String jzwfjid);
	
	
//---------------------------------------------
    public DataSet selectSyCzrkForFw(Map<String, String> map);
	
	public DataSet selectSyCzrkForFwLsry(Map<String, String> map);
}
