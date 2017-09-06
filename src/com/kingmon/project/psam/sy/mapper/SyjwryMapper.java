package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.Syjwry;
@KMapper
public interface SyjwryMapper {

    int insertSelective(Syjwry record);

    Syjwry selectByPrimaryKey(String jwryid);

    int updateByPrimaryKeySelective(Syjwry record);

    
	public List<Map<String,Object>> selectSyjwrylist(Map<String,String> params);
	public Long selectSyjwryCount(Map<String,String> params);
    /**
     * 根据建筑物房间Id加载境外人员信息
     * @param jzwfjid
     * @return
     */
	public Syjwry selectSyjwryByjzwfjId(String jzwfjid);
     /**
      * 根据证件号码加载境外人员信息
      * @param zjhm
      * @return
      */
	public Syjwry selectsyJwrybyzjhm(String zjhm);
     /**
      * 注销境外人员信息
      * @param map
      */
	public void cancelsyJwry(Map<String, Object> map);
     /**
      * 启用境外人员信息
      * @param map
      */
	 public  void activatesyJwry(Map<String, Object> map);
     /**
      * 根据房间Id加载境外人员信息
      * @param jzwfjid
      * @return
      */
	public List<Syjwry> selectSyJwryInfoByJzwfjId(String jzwfjid);
	
	/**
     * 根据房间Id加载全部境外人员全部信息
     * @param jzwfjid
     * @return
     */
	public List<Syjwry> selectSyJwryInfoByJzwfjIdKey(String jzwfjid);
    /**
     * 统计采集境外人员的个数
     * @param map
     * @return
     */
	long loadjwryWorkCount(Map<String, Object> map);
    /**
     * 加载采集境外人员的详细信息
     * @param map
     * @return
     */
	List<Map<String, Object>> loadjwryWorkDetail(Map<String, Object> map);
	
	
	 /**
	    * 根据房间ID查询  当前房屋 内 境外人员信息
	    * 2016-08-11
	    * @param jzwfjid
	    * @return
	    */
	public List<Syjwry> selectSyJwryInfoFwByJzwfjIdKey(String jzwfjid);
	
//--------------------------------------------
	
	public List<Map<String,Object>> selectSyJwrkForFw(Map<String, String> map);
	
	public Long selectSyJwrkForFwCount(Map<String, String> map);
	
	public List<Map<String,Object>> selectSyJwrkForFwLsry(Map<String, String> map);
	
	public Long selectSyJwrkForFwLsryCount(Map<String, String> map);
}