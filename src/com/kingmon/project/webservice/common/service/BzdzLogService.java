package com.kingmon.project.webservice.common.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;

public interface BzdzLogService {

	/**
	 * 加载列表
	 * @param params
	 * @return
	 */
	public DataSet bzdzLogList(Map<String, String> params);
}
