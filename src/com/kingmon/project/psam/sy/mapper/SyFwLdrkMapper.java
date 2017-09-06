package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
@KMapper
public interface SyFwLdrkMapper {
    int deleteByPrimaryKey(String id);


    int insertSelective(SyFwLdrk record);

    SyFwLdrk selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyFwLdrk record);
    
    public List<String> selectLdrkFjByJzwId(String jzwId);

    public List<SyFwLdrk> selectSyFwLdrkByjzwfjId(String jzwfjid);
    
    /**
     * 根据人员Id和房号查询流动人口信息
     * @param id
     * @param fh
     * @return
     */
	 public SyFwLdrk selectfwldrkByIdandfh(String id, String fh);
     /**
      * 根居证件号码和房间Id查询流动人口信息
      * @param sfzh
      * @param jzwfjid
      * @return
      */
    
	public SyFwLdrk selectFwLdrkByfjbhAndZjhm(String sfzh, String jzwfjid);

    /**
     * 加载当天统计流动人口的数目
     * @param workInfo
     * @return
     */
	long loadFwLdrkCount(Map<String, Object> workInfo);
    /**
     * 加载采集流动人口的详细
     * @param workInfo
     * @return
     */

	List<Map<String, Object>> loadFwLdrkList(Map<String, Object> workInfo);
	
	/**
	 * 删除当前房屋所住人员
	 * 2016-0811
	 * @param params
	 */
	public void revokeFwRyxx(Map<String, Object> params);
}