package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.Syjwry;
@KMapper
public interface SyFwCzrkMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(SyFwCzrk record);

    SyFwCzrk selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyFwCzrk record);
    
    public List<String> selectCzrkFjByJzwId(String jzwId);

    public List<SyFwCzrk> selectSyFwCzrkByjzwfjId(String jzwfjid);
    
     /**
      * 根据人员Id 和 建筑物房间Id查询房屋常住人口信息
      * @param rkid :人员ID
      * @param jzwfjid:建筑物房间Id
      * @return
      */
	public SyFwCzrk selectFwCzrkByrkIDAndFjbh(String ryid, String jzwfjid);

    /**
     * 根据证件号码和房间号查询常住人口信息
     * @param gmsfhm
     * @param jzwfjid
     * @return
     */
	public SyFwCzrk selectFwCzrkByfjbhAndZjhm(String gmsfhm, String jzwfjid);
    /**
     * 加载采集常住人口工作量
     * @param workInfo
     * @return
     */

	long loadCZRKCount(Map<String, Object> workInfo);
    

	List<Map<String, Object>> loadFwCzrkList(Map<String, Object> workInfo);
	
	/**
	 * 删除当前房屋所住人员
	 * 2016-0811
	 * @param params
	 */
	public void revokeFwRyxx(Map<String, Object> params);
}