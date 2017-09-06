package com.kingmon.project.psam.sy.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
public interface ISyFwjbxxService {
	public DataSet loadSYFwjbxxDataSet(Map<String,String> params);
	/**
	 * 
	 * @param params
	 * @return
	 */
	public SyFwjbxx loadSYFwjbxxByFjbm(String fjbm);
	
	public void saveFwjbxxAccInfo(SyFwjbxx syFwjbxx);
	
    /**
     * 更新房屋信息
     * @param params
     */
	public void updateFw(Map<String, Object> params);
    /**
     * 增加房屋信息
     * @param params
     */
	public void addFw(Map<String, Object> params);
	
	/**
	 * 根据房屋ID查询房屋
	 * 
	 * @param fwid
	 *            房屋Id
	 * @return SYFwjbxx 房屋信息
	 */
	SyFwjbxx getFwByfwid(String fwid);
	
	
	SyFwjbxx loadFwjbxxAccInfo(String jzwfjid);
	/**
	 * 修改房屋信息
	 * @param fw
	 */
	public void updateFwInfo(SyFwjbxx fw);
	
	public void revokeFwRyxx(Map<String, Object> params);
	
	//移动端 删除人口
	public void deleteFwRyxx(Map<String, Object> params);
}
