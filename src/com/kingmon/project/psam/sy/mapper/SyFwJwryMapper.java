package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyFwJwry;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
@KMapper
public interface SyFwJwryMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(SyFwJwry record);

    SyFwJwry selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyFwJwry record);
    
    public List<String> selectJwryFjByJzwId(String jzwId);
    /**s
     * 根据建筑物房间Id加载境外人员信息
     * @param jzwfjid
     * @return
     */
	public List<Syjwry> selectSyjwryByjzwfjId(String jzwfjid);

    /**
     * 根据境外人员Id 和房间编码查询境外人员信息
     * @param jwryid
     * @param fjbm
     * @return
     */
	SyFwJwry selectFwjwryByryidandfjbh(String jwryid, String fjbm);

     /**
      * 根据证件号码和房间Id查询境外人员信息
      * @param sfzh
      * @param jzwfjid
      * @return
      */
	SyFwJwry selectFwJwryByfjbhAndZjhm(String sfzh, String jzwfjid);


	
	long loadFWJWryCount(Map<String, Object> workInfo);


	  
	List<Map<String, Object>> loadFwJwryList(Map<String, Object> workInfo);

	/**
	 * 删除当前房屋所住人员
	 * 2016-0811
	 * @param params
	 */
	public void revokeFwRyxx(Map<String, Object> params);

	
}