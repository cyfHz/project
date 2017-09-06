package com.kingmon.project.psam.sjjy.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.project.elastic.model.GeoPoint;

public interface CheckMlphService {
	/**
	 * 检验楼门牌号数据列表
	 * @param params
	 * @return
	 */
	DataSet mlphList(Map<String, String> params);
//	/**
//	 * 修改标记坐标数据
//	 * @param ywlsh
//	 * @param point
//	 */
//	boolean updateLocation(String ywlsh, GeoPoint point);
	
}
