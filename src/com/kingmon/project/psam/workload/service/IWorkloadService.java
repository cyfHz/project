package com.kingmon.project.psam.workload.service;

import java.util.Map;

import com.kingmon.base.data.DataSet;

public interface IWorkloadService {
	/**
	 * @param params
	 *             jzwjbxx mlph
	 * @return
	 */
//	public DataSet loadDzWorkLoadDataSet(String porg_code);
	
	public DataSet loadDzWorkLoadDataFromEs(String porg_code);
	
	public DataSet loadDzWorkLoadFromDb(String porg_code);
}
