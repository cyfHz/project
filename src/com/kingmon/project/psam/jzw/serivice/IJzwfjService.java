package com.kingmon.project.psam.jzw.serivice;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.view.JzwfjView;

public interface IJzwfjService {
	
	Jzwfj findJzwfjByJzwfjid(String jzwfjid);
	
	public DataSet loadJzwfjDataSet(Map<String, String> params);

	/**
	 * 加载建筑物DataSet
	 * 
	 * @param paramObject
	 * @return
	 */
	public DataSet loadJzwfjInfoDataSet(ParamObject paramObject);


	
	/**
	 * 根据建筑物结构ID获取房间列表
	 * @param jzwjgId
	 * @return
	 */
	public List<Jzwfj> findJzwfjByJzwjgId(String jzwjgId);

	/**
	 * 根据建筑物结构ID获取房间列表 Map：JZWFJID, FJXH, FJMC,SHOWINFO,SHOWMODE
	 * @param jzwjgId
	 * @return
	 */
	public List<Map<String, Object>> findFjMapByJzwjgId(String jzwjgId);

	/**
	 * 房间拆分
	 * 
	 * @param view
	 * @return
	 */
	public Map<String, Object> chaiFen(JzwfjView view);

	/**
	 * 房间合并
	 * 
	 * @param view
	 * @return
	 */
	public Map<String, Object> heBing(JzwfjView view);

	/**
	 * 添加房间
	 * 
	 * @param view
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public KJSONMSG AddJzwFj(Map view);

	/**
	 * 检测是否可以删除房间
	 * 
	 * @param jzwfjId
	 * @return
	 */
	public Object checkCanDelete(String jzwfjId);

	/**
	 * 删除房间
	 * 
	 * @param jzwfjId
	 */
	public void deleteJzwFj(String jzwfjId);
	
	/**
	 * 计算建筑物宽度和高度
	 * @param list
	 * @return
	 */
	public Map<String, String> calculateJzwWidthAndHeight(List<Map<String, Object>> list);

	KJSONMSG iSjzwfjHaveDpendInfo(String jwzfjId);
	KJSONMSG iSjzwfjHaveDpendInfo(List<String> jwzfjIdList);
}
