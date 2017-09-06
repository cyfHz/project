package com.kingmon.project.psam.sy.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kingmon.base.data.KJSONMSG;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;

public interface ISyFwCzrkService {
  
	
	/**
	 * 加载常住人口管理信息
	 * @param jzwfjid
	 * @return
	 */
	public SyRkglCzrk loadFwCzrkAccInfo(String jzwfjid);
	/**
	 * 增加常住人口信息
	 * @param czrk
	 * @param picbytes 
	 */
	public void savefwCzrkAccInfo(SyRkglCzrk czrk, byte[] picbytes);
	
	public List<String> loadCzrkFjByJzwId(String jzwId);
	/**
	 * 跟据证件号码和房间ID加载
	 * @param sfzh
	 * @param jzwfjid
	 * @return
	 */
	public SyFwCzrk validateCzrk(String sfzh, String jzwfjid);
	
	public KJSONMSG loadFwCzrkAccInfoApp(String jzwfjid);
	/**
	 * 统计一个人一天的工作量个数
	 * @param map
	 * @return
	 */
	public long loadTodayworkCount(Map<String, Object> map);
	/**
	 * 统计一个一天采集的常主人口详细信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map);
	/**
	 * 根据条件统计采集常住人口工作量
	 * @param workInfo
	 * @return
	 */
	public long loadDayCZRKWorkCount(Map<String, Object> workInfo);
	/**
	 * 加载
	 * @param workInfo
	 * @return
	 */
	public List<Map<String, Object>> loadFwCzrkList(Map<String, Object> workInfo);
	
}
