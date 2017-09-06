package com.kingmon.project.webservice.common.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.webservice.common.model.ServiceBzdzUser;


public interface BzdzUserService {

	/**
	 * 加载列表
	 * @param params
	 * @return
	 */
	public DataSet bzdzuserList(Map<String, String> params);
	
	/**
	 * 主键查询
	 * @param systemid
	 * @return
	 */
	public ServiceBzdzUser selectByPrimaryKey(String systemid);
	
	/**
	 * 添加
	 * @param params
	 */
	public void addBzdzUser(ServiceBzdzUser serviceBzdzUser);
	
	/**
	 * 更新
	 * @param params
	 */
	public void updateBzdzUser(ServiceBzdzUser serviceBzdzUser);
	
	/**
	 * 删除
	 * @param systemid
	 */
	public void deleteBzdzUser(String systemid);
	
	/**
	 * 审批
	 * @param dzbm
	 */
	public void reviewUser(String systemid,String spzt);
	
	/**
	 * 解锁
	 * @param systemid
	 */
	public void unlockedUser(Map<String, Object> params);
	
	/**
	 * 锁定
	 * @param systemid
	 */
	public void lockedUser(Map<String, Object> params);
}
